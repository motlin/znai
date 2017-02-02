package com.twosigma.documentation.parser.docelement;

import java.nio.file.Path;
import java.util.*;

import com.twosigma.documentation.ComponentsRegistry;
import com.twosigma.documentation.extensions.include.IncludeParams;
import com.twosigma.documentation.extensions.include.IncludePlugin;
import com.twosigma.documentation.extensions.include.IncludePlugins;
import com.twosigma.documentation.extensions.ReactComponent;
import com.twosigma.documentation.AuxiliaryFile;
import com.twosigma.documentation.parser.ParserHandler;
import com.twosigma.documentation.structure.PageSectionIdTitle;
import com.twosigma.utils.CollectionUtils;
import com.twosigma.utils.StringUtils;

/**
 * @author mykola
 */
public class DocElementCreationParserHandler implements ParserHandler {
    private final ComponentsRegistry componentsRegistry;
    private Path path;
    private List<AuxiliaryFile> auxiliaryFiles;

    private List<DocElement> paragraphs;

    private final DocElement docElement;
    private final Deque<DocElement> elementsStack;

    public DocElementCreationParserHandler(ComponentsRegistry componentsRegistry, Path path) {
        this.componentsRegistry = componentsRegistry;
        this.path = path;
        this.paragraphs = new ArrayList<>();
        this.auxiliaryFiles = new ArrayList<>();

        this.docElement = new DocElement("page");
        this.elementsStack = new ArrayDeque<>();
        this.elementsStack.add(docElement);
    }

    public DocElement getDocElement() {
        return docElement;
    }

    public List<AuxiliaryFile> getAuxiliaryFiles() {
        return auxiliaryFiles;
    }

    @Override
    public void onSectionStart(String title) {
        start(DocElementType.SECTION, "title", title, "id", new PageSectionIdTitle(title).getId());
    }

    @Override
    public void onSectionEnd() {
        end();
    }

    @Override
    public void onHardLineBreak() {
        append(DocElementType.HARD_LINE_BREAK);
    }

    @Override
    public void onSoftLineBreak() {
        append(DocElementType.SOFT_LINE_BREAK);
    }

    @Override
    public void onParagraphStart() {
        start(DocElementType.PARAGRAPH);
    }

    @Override
    public void onParagraphEnd() {
        DocElement paragraph = end();
        paragraphs.add(paragraph);
    }

    @Override
    public void onBulletListStart(char bulletMarker, boolean tight) {
        start(DocElementType.BULLET_LIST, "bulletMarker", bulletMarker, "tight", tight);
    }

    @Override
    public void onBulletListEnd() {
        end();
    }

    @Override
    public void onOrderedListStart(char delimiter, int startNumber) {
        start(DocElementType.ORDERED_LIST, "delimiter", delimiter, "startNumber", startNumber);
    }

    @Override
    public void onOrderedListEnd() {
        end();
    }

    @Override
    public void onListItemStart() {
        start(DocElementType.LIST_ITEM);
    }

    @Override
    public void onListItemEnd() {
        end();
    }

    @Override
    public void onEmphasisStart() {
        start(DocElementType.EMPHASIS);
    }

    @Override
    public void onEmphasisEnd() {
        end();
    }

    @Override
    public void onStrongEmphasisStart() {
        start(DocElementType.STRONG_EMPHASIS);
    }

    @Override
    public void onStrongEmphasisEnd() {
        end();
    }

    @Override
    public void onBlockQuoteStart() {
        start(DocElementType.BLOCK_QUOTE);
    }

    @Override
    public void onBlockQuoteEnd() {
        end();
    }

    @Override
    public void onSimpleText(String value) {
        append(DocElementType.SIMPLE_TEXT, "text", value);
    }

    @Override
    public void onLink(String label, String anchor) {
        append(DocElementType.LINK, "label", label, "anchor", anchor);
    }

    @Override
    public void onImage(String title, String destination, String alt) {
        append(DocElementType.IMAGE, "title", title, "destination", destination, "alt", alt, "inlined", true);

        if (! destination.startsWith("http")) {
            auxiliaryFiles.add(AuxiliaryFile.runTime(componentsRegistry.includeResourceResolver().fullPath(destination)));
        }
    }

    @Override
    public void onSnippet(String lang, String lineNumber, String snippet) {
        append(DocElementType.SNIPPET, "lang", lang, "lineNumber", lineNumber, "snippet", snippet,
                "maxLineLength", StringUtils.maxLineLength(snippet));
    }

    @Override
    public void onThematicBreak() {
        append(DocElementType.THEMATIC_BREAK);
    }

    @Override
    public void onInclude(final String pluginId, final String value) {
        try {
            IncludePlugin includePlugin = IncludePlugins.byId(pluginId);
            IncludeParams includeParams = new IncludeParams(value);
            ReactComponent reactComponent = includePlugin.process(componentsRegistry, path, includeParams);

            includePlugin.filesPluginDependsOn(componentsRegistry, includeParams).forEach(auxiliaryFiles::add);

            DocElement customComponent = new DocElement(DocElementType.CUSTOM_COMPONENT);
            customComponent.addProp("componentName", reactComponent.getName());
            customComponent.addProp("componentProps", reactComponent.getProps());

            append(customComponent);
        } catch (Exception e) {
            throw new RuntimeException("failure during processing include plugin '" + pluginId + "': " + e.getMessage(), e);
        }
    }

    @Override
    public void onParsingEnd() {
        paragraphs.forEach(this::convertParagraphWithSingleImageToWideImage);
    }

    private void convertParagraphWithSingleImageToWideImage(DocElement paragraph) {
        if (paragraph.getContent().size() > 1 || paragraph.getContent().size() == 0) {
            return;
        }

        DocElement singleElement = paragraph.getContent().get(0);
        if (! singleElement.getType().equals(DocElementType.IMAGE)) {
            return;
        }

        paragraph.setType(singleElement.getType());

        singleElement.getProps().forEach(paragraph::addProp);
        paragraph.addProp("inlined", false);

        paragraph.removeChild(singleElement);
    }

    private void start(String type, Object... propsKeyValue) {
        DocElement element = new DocElement(type);
        addProps(element, propsKeyValue);

        appendAndPush(element);
    }

    private DocElement end() {
        return elementsStack.removeLast();
    }

    private void append(String type, Object... propsKeyValue) {
        DocElement element = new DocElement(type);
        addProps(element, propsKeyValue);

        append(element);
    }

    private void append(DocElement element) {
        elementsStack.peekLast().addChild(element);
    }

    private void addProps(DocElement element, Object... propsKeyValue) {
        Map<String, Object> props = CollectionUtils.createMap(propsKeyValue);
        props.forEach(element::addProp);
    }

    private void appendAndPush(DocElement element) {
        elementsStack.peekLast().addChild(element);
        elementsStack.add(element);
    }
}

