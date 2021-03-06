# Command Line Tool

`Znai` comes with a command line tool that:
* Generates a documentation website
* Runs auto-preview on a local server
* Runs documentation server

# Markdown

The fastest way to learn Markdown is to go to [CommonMark](http://commonmark.org/help/) website 
and go through a 60 second cheatsheet or 10 minute tutorial.

# Installation

:include-markdown: {firstAvailable: ["getting-started-installation-override.md", "getting-started-installation.md"]}

# Scaffolding

To create a minimum set of files for your documentation execute 

:include-cli-command: znai --new

    znai    
       |--chapter-one
                 |--page-one.md
                 |--page-two.md
       |--chapter-two
                 |--page-three.md
                 |--page-four.md
       |--toc
       |--lookup-paths
       |--meta.json

:include-markdown: {firstAvailable: ["getting-started-scaffolding-maven-override.md", "getting-started-scaffolding-maven.md"]}
       
# Preview 

Navigate to the newly created directory. Start preview mode by running:

:include-cli-command: znai --preview [--port port-number] 

:include-markdown: {firstAvailable: ["getting-started-preview-maven-override.md", "getting-started-preview-maven.md"]}

The command will output a URL that directs to your preview.  
  
Blue *eye* icon in the top right corner indicates that preview is on.
Open any text editor, modify and save `page-one.md` file.
Changes will be reflected in the browser. 
The default port number is 3333.

# Deploy

:include-markdown: {firstAvailable: ["getting-started-deploy-override.md", "getting-started-deploy.md"]}
