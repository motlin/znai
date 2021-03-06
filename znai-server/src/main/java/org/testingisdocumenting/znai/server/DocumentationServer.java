/*
 * Copyright 2019 TWO SIGMA OPEN SOURCE, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.testingisdocumenting.znai.server;

import org.testingisdocumenting.znai.console.ConsoleOutputs;
import org.testingisdocumenting.znai.console.ansi.AnsiConsoleOutput;
import org.testingisdocumenting.znai.console.ansi.Color;
import org.testingisdocumenting.znai.html.HtmlPage;
import org.testingisdocumenting.znai.html.reactjs.HtmlReactJsPage;
import org.testingisdocumenting.znai.html.reactjs.ReactJsBundle;
import org.testingisdocumenting.znai.server.remove.DocumentationRemoveHandler;
import org.testingisdocumenting.znai.server.remove.OnRemoveFinishedServerHandlers;
import org.testingisdocumenting.znai.server.docpreparation.DocumentationPreparationHandlers;
import org.testingisdocumenting.znai.server.docpreparation.DocumentationPreparationTestHandler;
import org.testingisdocumenting.znai.server.docpreparation.DocumentationPreparationWebSocketHandler;
import org.testingisdocumenting.znai.server.docpreparation.NoOpDocumentationPreparationProgress;
import org.testingisdocumenting.znai.server.landing.LandingDocEntriesProviders;
import org.testingisdocumenting.znai.server.landing.LandingDocEntry;
import org.testingisdocumenting.znai.server.landing.LandingUrlContentHandler;
import org.testingisdocumenting.znai.server.sockets.JsonWebSocketHandler;
import org.testingisdocumenting.znai.server.sockets.JsonWebSocketHandlerComposition;
import org.testingisdocumenting.znai.server.upload.DocumentationUploadHandler;
import org.testingisdocumenting.znai.server.upload.OnUploadFinishedServerHandlers;
import org.testingisdocumenting.znai.server.upload.UnzipTask;
import org.testingisdocumenting.znai.server.urlhandlers.UrlContentHandlers;
import org.testingisdocumenting.znai.utils.FileUtils;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.impl.RoutingContextDecorator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DocumentationServer {
    private final JsonWebSocketHandlerComposition socketHandlers;
    private final Vertx vertx;
    private final ReactJsBundle reactJsBundle;
    private final Path deployRoot;

    public DocumentationServer(ReactJsBundle reactJsBundle, Path deployRoot) {
        this.reactJsBundle = reactJsBundle;
        this.deployRoot = deployRoot;

        System.setProperty("vertx.cwd", deployRoot.toString());
        System.setProperty("file.encoding","UTF-8");

        FileUtils.symlinkAwareCreateDirs(deployRoot);
        vertx = Vertx.vertx();

        socketHandlers = new JsonWebSocketHandlerComposition();
        DocumentationPreparationWebSocketHandler socketHandler = new DocumentationPreparationWebSocketHandler(vertx);

        socketHandlers.add(socketHandler);
        OnUploadFinishedServerHandlers.add(this::unzip);
        OnRemoveFinishedServerHandlers.add(this::deleteCachedDocumentation);
    }

    public void addSocketHandler(JsonWebSocketHandler socketHandler) {
        socketHandlers.add(socketHandler);
    }

    public HttpServer create() {
        HttpServer server = vertx.createHttpServer(new HttpServerOptions().setCompressionSupported(true));

        Router router = Router.router(vertx);

        StaticHandler pagesStaticHandler = StaticHandler.create(".").
                setFilesReadOnly(false).setCachingEnabled(false);

        StaticHandler staticCommonResources =
                StaticHandler.create("static").setMaxAgeSeconds(600);

        // delegate each documentation static resources to a central copy
        // to make existing docs served centrally upgrade all at once
        router.get("/:docId/static/*").handler(ctx -> {
            MultiMap params = ctx.request().params();

            staticCommonResources.handle(new RoutingContextDecorator(
                    router.route("/" + params.get("docId") + "/static"), ctx));
        });

        router.delete("/:docId").handler(ctx -> {
            MultiMap params = ctx.request().params();
            String docId = params.get("docId");
            String actor = params.get("actor");
            new DocumentationRemoveHandler(docId, actor).handle(ctx.request());
        });

        router.route("/upload/:docId").handler(ctx -> {
            MultiMap params = ctx.request().params();
            String docId = params.get("docId");
            String actor = params.get("actor");
            new DocumentationUploadHandler(vertx, docId, deployRoot, actor).handle(ctx.request());
        });

        router.get("/static/*").handler(staticCommonResources);

        registerCustomHandlers(router);
        registerPagesHandler(router, pagesStaticHandler);

        server.websocketHandler(socketHandlers);
        server.requestHandler(router::accept);

        return server;
    }

    private void registerCustomHandlers(Router router) {
        UrlContentHandlers.urlContentHandlers()
                .forEach(urlContentHandler ->
                        router.get(urlContentHandler.url())
                                .handler(ctx -> ctx.response().end(urlContentHandler.buildContent(ctx, reactJsBundle))));
    }

    private void registerPagesHandler(Router router, StaticHandler pagesStaticHandler) {
        router.get("/*").handler(ctx -> {
            if (needToEndWithTrailingSlash(ctx)) {
                redirectToTrailingSlash(ctx);
                return;
            }

            String docId = extractDocId(ctx);
            if (DocumentationPreparationHandlers.isReady(docId)) {
                pagesStaticHandler.handle(ctx);
            } else if (isNotDocPageRequest(ctx)) {
                // znai documentations are single page apps
                // page refresh won't happen during navigation from one page to another
                // but images will still be requested if they are present on a page
                // in that case we will call documentation preparation before serving content
                vertx.executeBlocking(future -> {
                    DocumentationPreparationHandlers.prepare(docId, NoOpDocumentationPreparationProgress.INSTANCE);
                    future.complete();
                }, false, (res) -> pagesStaticHandler.handle(ctx));
            } else {
                serveDocumentationPreparationPage(ctx, docId);
            }
        });
    }

    private void redirectToTrailingSlash(RoutingContext ctx) {
        ctx.response().putHeader("location", ctx.request().uri() + "/").setStatusCode(301).end();
    }

    private void serveDocumentationPreparationPage(RoutingContext ctx, String docId) {
        HtmlReactJsPage htmlReactJsPage = new HtmlReactJsPage(reactJsBundle);
        Map<String, Object> props = new LinkedHashMap<>();

        props.put("docId", docId);
        props.put("statusMessage", "checking documentation cache");
        props.put("progressPercent", 0);
        props.put("keyValues", Collections.emptyList());

        HtmlPage htmlPage = htmlReactJsPage.create("Preparing " + docId,
                "DocumentationPreparationScreen", props, () -> "", FavIcons.DEFAULT_ICON_PATH);

        ctx.response().end(htmlPage.render(docId));
    }

    private static boolean needToEndWithTrailingSlash(RoutingContext ctx) {
        if (isNotDocPageRequest(ctx)) {
            return false;
        }

        return !ctx.request().uri().endsWith("/");
    }

    private static String extractDocId(RoutingContext ctx) {
        String uri = ctx.request().uri();
        String[] parts = uri.split("/");

        return parts.length < 2 ? "" : parts[1];
    }

    private static boolean isNotDocPageRequest(RoutingContext ctx) {
        String uri = ctx.request().uri();
        int dotIdx = uri.lastIndexOf('.');
        if (dotIdx == -1) {
            return false;
        }

        int slashIdx = uri.lastIndexOf('/');
        if (slashIdx > dotIdx) {
            return false;
        }

        String extension = uri.substring(dotIdx);
        return !extension.equals("html");
    }

    private void deleteCachedDocumentation(String docId, String actor) {
        Path docPath = deployRoot.resolve(docId);

        ConsoleOutputs.out(Color.BLUE, "deleting docs: ", Color.PURPLE, docId, Color.BLACK, " at ",
                Color.PURPLE, docPath);
        try {
            File docDirectory = docPath.toFile();
            if (docDirectory.exists()) {
                org.apache.commons.io.FileUtils.deleteDirectory(docDirectory);
            }
            ConsoleOutputs.out(Color.BLUE, "deleted docs: ", Color.PURPLE, docPath);
        } catch (IOException e) {
            ConsoleOutputs.out(Color.BLUE, "failed to delete docs: ", Color.PURPLE, docPath);
        }
    }

    private void unzip(String docId, Path path, String actor) {
        Path unzipDest = deployRoot.resolve(docId);

        ConsoleOutputs.out(Color.BLUE, "unzipping docs: ", Color.PURPLE, path, Color.BLACK, " to ",
                Color.PURPLE, unzipDest);

        UnzipTask unzipTask = new UnzipTask(deployRoot.resolve(path), unzipDest);
        unzipTask.execute();
        ConsoleOutputs.out(Color.BLUE, "unzipped docs: ", Color.PURPLE, unzipDest);
    }

    // this is entry point for local development and testing
    // official znai start is through org.testingisdocumenting.znai.cli.ZnaiCliApp
    public static void main(String[] args) {
        ConsoleOutputs.add(new AnsiConsoleOutput());

        ReactJsBundle reactJsBundle = new ReactJsBundle();

        DocumentationPreparationHandlers.add(new DocumentationPreparationTestHandler());

        LandingDocEntriesProviders.add(() -> Stream.of(
                new LandingDocEntry("znai", "Znai", "http://custom","Documentation", "test desc")
        ));
        UrlContentHandlers.add(new LandingUrlContentHandler("Company", "Guides"));

        HttpServer server = new DocumentationServer(reactJsBundle, Paths.get("")).create();
        server.listen(3333);
        System.out.println("test server started");
    }
}
