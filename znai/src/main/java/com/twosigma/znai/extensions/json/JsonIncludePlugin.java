package com.twosigma.znai.extensions.json;

import com.jayway.jsonpath.JsonPath;
import com.twosigma.znai.core.AuxiliaryFile;
import com.twosigma.znai.core.ComponentsRegistry;
import com.twosigma.znai.core.ResourcesResolver;
import com.twosigma.znai.extensions.PluginParams;
import com.twosigma.znai.extensions.PluginParamsOpts;
import com.twosigma.znai.extensions.PluginResult;
import com.twosigma.znai.extensions.include.IncludePlugin;
import com.twosigma.znai.parser.ParserHandler;
import com.twosigma.utils.JsonUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class JsonIncludePlugin implements IncludePlugin {
    private String fileName;
    private ResourcesResolver resourcesResolver;
    private Path pathsFilePath;

    @Override
    public String id() {
        return "json";
    }

    @Override
    public IncludePlugin create() {
        return new JsonIncludePlugin();
    }

    @Override
    public PluginResult process(ComponentsRegistry componentsRegistry,
                                ParserHandler parserHandler,
                                Path markupPath,
                                PluginParams pluginParams) {
        resourcesResolver = componentsRegistry.resourceResolver();

        fileName = pluginParams.getFreeParam();
        String json = resourcesResolver.textContent(fileName);

        String jsonPath = pluginParams.getOpts().get("include", "$");
        Object content = JsonPath.read(json, jsonPath);

        Map<String, Object> props = pluginParams.getOpts().toMap();
        props.put("data", content);
        props.put("paths", extractPaths(pluginParams.getOpts()));

        return PluginResult.docElement("Json", props);
    }

    @Override
    public Stream<AuxiliaryFile> auxiliaryFiles(ComponentsRegistry componentsRegistry) {
        Stream<AuxiliaryFile> pathsFile = pathsFilePath == null ?
                Stream.empty() :
                Stream.of(AuxiliaryFile.builtTime(pathsFilePath));

        return Stream.concat(pathsFile,
                Stream.of(AuxiliaryFile.builtTime(componentsRegistry.resourceResolver().fullPath(fileName))));
    }

    @SuppressWarnings("unchecked")
    private List<String> extractPaths(PluginParamsOpts opts) {
        if (opts.has("pathsFile")) {
            String filePath = opts.get("pathsFile");
            pathsFilePath = resourcesResolver.fullPath(filePath);

            return (List<String>) JsonUtils.deserializeAsList(resourcesResolver.textContent(filePath));
        }

        return opts.getList("paths");
    }
}