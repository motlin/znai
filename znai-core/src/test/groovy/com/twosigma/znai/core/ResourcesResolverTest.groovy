package com.twosigma.znai.core

import org.junit.Test

import java.awt.image.BufferedImage
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

class ResourcesResolverTest {
    @Test
    void "auxiliary file should use provided url as deploy path if the actual file is outside of docs file structure"() {
        def resolver = new OutsideDocsResolver()

        def auxiliaryFile = resolver.runtimeAuxiliaryFile("path/image.png")
        auxiliaryFile.getDeployRelativePath().toString().should == "path/image.png"
    }

    @Test
    void "auxiliary file should use relative to docs path if the actual file is inside of docs file structure"() {
        def resolver = new InsideDocsResolver()

        def auxiliaryFile = resolver.runtimeAuxiliaryFile("path/image.png")
        auxiliaryFile.getDeployRelativePath().toString().should == "nested/path/image.png"
    }

    static class OutsideDocsResolver implements ResourcesResolver {
        @Override
        void initialize(Stream<String> filteredLookupPaths) {
        }

        @Override
        boolean supportsLookupPath(String lookupPath) {
            return true
        }

        @Override
        boolean canResolve(String path) {
            return false
        }

        @Override
        List<String> listOfTriedLocations(String path) {
            return []
        }

        @Override
        String textContent(String path) {
            return ""
        }

        @Override
        BufferedImage imageContent(String path) {
            return null
        }

        @Override
        Path fullPath(String path) {
            return Paths.get(path)
        }

        @Override
        Path docRootRelativePath(Path path) {
            return path
        }

        @Override
        boolean isInsideDoc(Path path) {
            return false
        }

        @Override
        boolean isLocalFile(String path) {
            return true
        }
    }

    static class InsideDocsResolver implements ResourcesResolver {
        @Override
        void initialize(Stream<String> filteredLookupPaths) {
        }

        @Override
        boolean supportsLookupPath(String lookupPath) {
            return true
        }

        @Override
        boolean canResolve(String path) {
            return true
        }

        @Override
        List<String> listOfTriedLocations(String path) {
            return []
        }

        @Override
        String textContent(String path) {
            return ""
        }

        @Override
        BufferedImage imageContent(String path) {
            return null
        }

        @Override
        Path fullPath(String path) {
            return Paths.get("/doc-root/nested").resolve(path)
        }

        @Override
        Path docRootRelativePath(Path path) {
            Paths.get("/doc-root").relativize(path)
        }

        @Override
        boolean isInsideDoc(Path path) {
            return true
        }

        @Override
        boolean isLocalFile(String path) {
            return true
        }
    }
}