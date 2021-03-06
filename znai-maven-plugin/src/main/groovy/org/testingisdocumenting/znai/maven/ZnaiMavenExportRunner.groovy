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

package org.testingisdocumenting.znai.maven

import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugin.MojoExecutionException
import org.apache.maven.plugin.MojoFailureException
import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugins.annotations.Parameter

@Mojo(name = "export")
class ZnaiMavenExportRunner extends AbstractMojo {
    @Parameter
    private String sourceRoot

    @Parameter(defaultValue = '${project.build.directory}/znai-export')
    private String exportRoot

    @Parameter(defaultValue = "3333")
    private Integer port

    @Override
    void execute() throws MojoExecutionException, MojoFailureException {
        ZnaiMavenRunner.run(new MavenPluginConsoleOuput(getLog()), [
                export: exportRoot,
                port  : port.toString(),
                source: sourceRoot,
        ])
    }
}
