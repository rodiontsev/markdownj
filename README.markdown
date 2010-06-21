README
======

MarkdownJ is the pure Java port of Markdown (a text-to-html conversion tool written by John Gruber.)

This repository is an updated fork of myabc/markdownj with some modifications, needed from enr/markdownj-extras and enr/xite projects.

Differences from MarkdownJ upstream:

  * Milestone releases to remove the need for a 'SNAPSHOT' dependency. 

  * You can set the code-block template in MarkdownProcessor.
  
To build the last milestone (from commits to June 21 2010), get the code cloning the repo

    git clone git://github.com/enr/markdownj.git
    git checkout 0.4.M20100621
    
or downloading the zip archive

    wget 'http://github.com/enr/markdownj/tarball/0.4.M20100519'

and build it:    

    mvn clean install
    
Now you can use the library adding to your pom.xml:

    <dependency>
      <groupId>com.github.enr.markdownj</groupId>
      <artifactId>markdownj-core</artifactId>
      <version>0.4.M20100621</version>
    </dependency>


