package com.twosigma.documentation.website

import com.twosigma.documentation.core.ResourcesResolver
import com.twosigma.documentation.parser.TestResourceResolver
import org.junit.Test

import java.nio.file.Paths

import static java.util.stream.Collectors.toList

/**
 * @author mykola
 */
class WebSiteExtensionsTest {
    private ResourcesResolver resourcesResolver = new TestResourceResolver(Paths.get('/dummy/root'))

    @Test
    void "should let specify extra web resources"() {
        createExtensions([:]).cssResources(resourcesResolver).collect(toList()).size().should == 0

        def extensions = createExtensions([
                cssResources: ['custom.css', 'another.css'],
                jsResources: ['custom.js', 'components.js'],
                jsClientOnlyResources: ['custom-client.js'],
                additionalFilesToDeploy: ['font1.woff2', 'font2.woff2'],
                htmlResources: ['custom.html']])

        def paths = { name -> extensions."$name"(resourcesResolver).collect(toList()).path }

        paths('cssResources').should == ['custom.css', 'another.css']
        paths('jsResources').should == ['custom.js', 'components.js']
        paths('jsClientOnlyResources').should == ['custom-client.js']
        paths('htmlResources').should == ['custom.html']
        paths('additionalFilesToDeploy').should == ['font1.woff2', 'font2.woff2']
    }

    private static WebSiteExtensions createExtensions(Map definition) {
        return new WebSiteExtensions(new TestResourceResolver(Paths.get('/dummy/root')),  definition)
    }
}
