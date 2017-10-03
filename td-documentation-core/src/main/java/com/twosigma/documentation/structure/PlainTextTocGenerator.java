package com.twosigma.documentation.structure;

import java.util.Arrays;
import java.util.List;

/**
 * @author mykola
 */
public class PlainTextTocGenerator implements TocGenerator {
    @Override
    public TableOfContents generate(String textContent) {
        return new Parser(textContent).parse();
    }

    private static class Parser {
        public static final String INDENTATION = "    ";
        private List<String> nestedLines;
        private String currentSection;
        private TableOfContents toc;

        public Parser(final String nestedText) {
            nestedLines = Arrays.asList(nestedText.replace("\r", "").split("\n"));
            toc = new TableOfContents();
        }

        public TableOfContents parse() {
            nestedLines.forEach(this::parse);
            return toc;
        }

        private void parse(final String line) {
            String trimmedLine = line.trim();
            if (trimmedLine.isEmpty())
                return;

            if (line.startsWith(INDENTATION)) {
                handlePageEntry(trimmedLine);
            } else if (line.startsWith(" ")) {
                handleSyntaxError();
            } else {
                handleSectionEntry(trimmedLine);
            }
        }

        private void handleSyntaxError() {
            throw new IllegalArgumentException(
                    "toc line should either start with " + INDENTATION.length() + " spaces to denote " +
                            "section name, or start without spaces to denote page file name");
        }

        private void handleSectionEntry(final String trimmedLine) {
            currentSection = trimmedLine;
        }

        private void handlePageEntry(final String line) {
            if (currentSection == null) {
                throw new IllegalArgumentException(
                        "section is not specified, use a line without indentation to specify a section");
            } else {
                toc.addTocItem(currentSection, line);
            }
        }
    }
}