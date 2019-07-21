package com.twosigma.znai.cpp.extensions;

import com.twosigma.znai.codesnippets.CodeSnippetsProps;
import com.twosigma.znai.codesnippets.CodeTokenizer;
import com.twosigma.znai.core.AuxiliaryFile;
import com.twosigma.znai.core.ComponentsRegistry;
import com.twosigma.znai.cpp.parser.CodePart;
import com.twosigma.znai.cpp.parser.CppSourceCode;
import com.twosigma.znai.extensions.PluginParams;
import com.twosigma.znai.extensions.PluginParamsOpts;
import com.twosigma.znai.extensions.PluginResult;
import com.twosigma.znai.extensions.include.IncludePlugin;
import com.twosigma.znai.parser.MarkupParser;
import com.twosigma.znai.parser.MarkupParserResult;
import com.twosigma.znai.parser.ParserHandler;
import com.twosigma.znai.parser.docelement.DocElement;
import com.twosigma.znai.parser.docelement.DocElementType;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CppIncludePlugin implements IncludePlugin {
    private MarkupParser markupParser;
    private Path cppPath;
    private CodeTokenizer codeTokenizer;
    private String fileName;

    @Override
    public String id() {
        return "cpp";
    }

    @Override
    public IncludePlugin create() {
        return new CppIncludePlugin();
    }

    @Override
    public PluginResult process(ComponentsRegistry componentsRegistry,
                                ParserHandler parserHandler,
                                Path markupPath,
                                PluginParams pluginParams) {
        markupParser = componentsRegistry.defaultParser();
        fileName = pluginParams.getFreeParam();
        cppPath = componentsRegistry.resourceResolver().fullPath(this.fileName);

        PluginParamsOpts opts = pluginParams.getOpts();
        String commentsType = opts.has("comments") ? opts.get("comments") : "";

        String text = componentsRegistry.resourceResolver().textContent(fileName);

        String snippet = extractSnippet(text, opts);

        List<CodePart> codeParts = commentsType.equals("inline") ?
                CppSourceCode.splitOnComments(snippet):
                Collections.singletonList(new CodePart(false, snippet));

        return PluginResult.docElements(codeParts.stream().flatMap(this::convertToDocElement));
    }

    private Stream<DocElement> convertToDocElement(CodePart codePart) {
        return codePart.isComment() ?
                parseComments(codePart.getData()) :
                createSnippet(codePart.getData());
    }

    @Override
    public Stream<AuxiliaryFile> auxiliaryFiles(ComponentsRegistry componentsRegistry) {
        return Stream.of(AuxiliaryFile.builtTime(cppPath));
    }

    private Stream<DocElement> parseComments(String data) {
        MarkupParserResult parserResult = markupParser.parse(cppPath, data);
        return parserResult.getDocElement().getContent().stream();
    }

    private Stream<DocElement> createSnippet(String snippet) {
        DocElement docElement = new DocElement(DocElementType.SNIPPET);
        Map<String, Object> props = CodeSnippetsProps.create("cpp", snippet);
        props.forEach(docElement::addProp);

        return Stream.of(docElement);
    }

    private String extractSnippet(String text, PluginParamsOpts opts) {
        String entry = opts.get("entry");
        if (entry == null) {
            return text;
        }

        Boolean bodyOnly = opts.get("bodyOnly");
        return bodyOnly != null && bodyOnly ?
                CppSourceCode.entryBodyOnly(text, entry):
                CppSourceCode.entryDefinition(text, entry);
    }
}