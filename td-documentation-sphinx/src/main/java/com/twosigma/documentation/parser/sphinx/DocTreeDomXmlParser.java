package com.twosigma.documentation.parser.sphinx;

import com.twosigma.documentation.core.ComponentsRegistry;
import com.twosigma.documentation.extensions.PluginParams;
import com.twosigma.documentation.extensions.PluginResult;
import com.twosigma.documentation.parser.ParserHandler;
import com.twosigma.documentation.parser.sphinx.python.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.nio.file.Path;

import static com.twosigma.documentation.parser.sphinx.XmlUtils.getAttributeText;
import static com.twosigma.documentation.parser.sphinx.XmlUtils.parseXml;

/**
 * @author mykola
 */
class DocTreeDomXmlParser {
    private ComponentsRegistry componentsRegistry;
    private Path filePath;
    private ParserHandler parserHandler;

    DocTreeDomXmlParser(ComponentsRegistry componentsRegistry,
                        Path filePath,
                        ParserHandler parserHandler) {
        this.componentsRegistry = componentsRegistry;
        this.filePath = filePath;
        this.parserHandler = parserHandler;
    }

    public void parse(String xml) {
        Document document = parseXml(xml);
        NodeList nodes = document.getChildNodes();

        parseNodeList(nodes);
    }

    private boolean parseNodeList(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node item = nodes.item(i);
            parseNode(item);
        }

        return true;
    }

    private boolean parseNode(Node node) {
        switch (node.getNodeName()) {
            case "document":
                return parseChildren(node);
            case "section":
                return parseSection(node);
            case "paragraph":
                return parseParagraph(node);
            case "emphasis":
                return parseEmphasis(node);
            case "strong":
                return parseStrong(node);
            case "literal":
                return parseLiteral(node);
            case "literal_block":
                return parseSnippet(node);
            case "bullet_list":
                return parseBulletList(node);
            case "enumerated_list":
                return parseOrderedList(node);
            case "list_item":
                return parseListItem(node);
            case "reference":
                return parseReference(node);
            case "desc":
                return parseDesc(node);
            case "#text":
                return parseText(node);
            case "title":
                return false;
        }

        return false;
    }

    private boolean parseReference(Node node) {
        parserHandler.onLinkStart(getAttributeText(node, "refuri"));
        parseChildren(node);
        parserHandler.onLinkEnd();

        return true;
    }

    private boolean parseSection(Node node) {
        parserHandler.onSectionStart(extractTitle(node));
        parseChildren(node);
        parserHandler.onSectionEnd();

        return true;
    }

    private boolean parseParagraph(Node node) {
        parserHandler.onParagraphStart();
        parseChildren(node);
        parserHandler.onParagraphEnd();

        return true;
    }

    private boolean parseEmphasis(Node node) {
        parserHandler.onEmphasisStart();
        parseChildren(node);
        parserHandler.onEmphasisEnd();

        return true;
    }

    private boolean parseStrong(Node node) {
        parserHandler.onStrongEmphasisStart();
        parseChildren(node);
        parserHandler.onStrongEmphasisEnd();

        return true;
    }

    private boolean parseLiteral(Node node) {
        parserHandler.onInlinedCode(node.getTextContent());
        return true;
    }

    private boolean parseSnippet(Node node) {
        parserHandler.onSnippet(PluginParams.EMPTY, getAttributeText(node, "language"),
                "", node.getTextContent());

        return true;
    }

    private boolean parseBulletList(Node node) {
        parserHandler.onBulletListStart(getAttributeText(node, "bullet").charAt(0), false);
        parseChildren(node);
        parserHandler.onBulletListEnd();

        return true;
    }

    private boolean parseOrderedList(Node node) {
        parserHandler.onOrderedListStart(getAttributeText(node, "suffix").charAt(0), 1);
        parseChildren(node);
        parserHandler.onOrderedListEnd();

        return true;
    }

    private boolean parseListItem(Node node) {
        parserHandler.onListItemStart();
        parseChildren(node);
        parserHandler.onListItemEnd();

        return true;
    }

    private boolean parseDesc(Node node) {
        String descType = XmlUtils.getAttributeText(node, "desctype");

        switch (descType) {
            case "class":
                return parseClass(node);
            case "function":
                return parseFunction(node);
            default:
                return false;
        }
    }

    private boolean parseFunction(Node node) {
        PythonFunction pythonFunction = new PythonFunctionXmlParser().parse(node);
        parserHandler.onGlobalAnchor(pythonFunction.getRefId());

        PythonFunctionIncludePlugin includePlugin = new PythonFunctionIncludePlugin();

        PluginResult pluginResult = includePlugin.process(componentsRegistry, parserHandler, filePath,
                new PluginParams(includePlugin.id(), pythonFunction.toMap()));

        parserHandler.onIncludePlugin(includePlugin, pluginResult);

        return false;
    }

    private boolean parseClass(Node node) {
        PythonClassXmlParser xmlParser = new PythonClassXmlParser();
        PythonClass pythonClass = xmlParser.parseClass(node);

        parserHandler.onGlobalAnchor(pythonClass.getRefId());
        pythonClass.getMethods().forEach(m -> parserHandler.onGlobalAnchor(m.getRefId()));

        PythonClassIncludePlugin includePlugin = new PythonClassIncludePlugin();
        PluginResult pluginResult = includePlugin.process(componentsRegistry, parserHandler, filePath,
                new PluginParams(includePlugin.id(), pythonClass.toMap()));

        parserHandler.onIncludePlugin(includePlugin, pluginResult);

        return true;
    }

    private boolean parseText(Node node) {
        String textContent = node.getTextContent();
        if (textContent.startsWith("\n")) {
            return false;
        }

        parserHandler.onSimpleText(textContent);
        return true;
    }

    private boolean parseChildren(Node node) {
        return parseNodeList(node.getChildNodes());
    }

    private String extractTitle(Node node) {
        Element element = (Element) node;
        return element.getElementsByTagName("title").item(0).getTextContent();
    }
}
