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

package com.twosigma.znai.website.markups

import com.twosigma.znai.parser.MarkupParsingConfigurations
import com.twosigma.znai.parser.MarkupTypes
import org.junit.Test

import static com.twosigma.webtau.Ddjt.code
import static com.twosigma.webtau.Ddjt.throwException

class MarkupParsingConfigurationsTest {
    @Test
    void "finds registered configuration by name"() {
        def markdownConfig = MarkupParsingConfigurations.byName(MarkupTypes.MARKDOWN)
        assert markdownConfig instanceof MarkdownParsingConfiguration

        def sphinxConfig = MarkupParsingConfigurations.byName(MarkupTypes.SPHINX)
        assert sphinxConfig instanceof SphinxParsingConfiguration
    }

    @Test
    void "lists available configurations when wrong name is specified"() {
        code {
            MarkupParsingConfigurations.byName('unknown')
        } should throwException("can't find markup configuration for <unknown>. Available configurations: \n" +
                "markdown(class com.twosigma.znai.website.markups.MarkdownParsingConfiguration)\n" +
                "sphinx(class com.twosigma.znai.website.markups.SphinxParsingConfiguration)")
    }
}