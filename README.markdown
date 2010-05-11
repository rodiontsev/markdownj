README
======

MarkdownJ is the pure Java port of Markdown (a text-to-html conversion tool written by John Gruber.)

This repository is an updated fork of myabc/markdownj.

Differences:

  * Milestone releases to remove the need for a 'SNAPSHOT' dependency. 

  * It's possible set the code-block template in MarkdownProcessor.

To build the last milestone (from commits to May 9 2010):

    git clone git://github.com/enr/markdownj-extras.git
    git checkout 0.4.M20100509
    mvn clean install
    
Now you can use the library adding to your pom.xml:

    <dependency>
      <groupId>com.github.enr.markdownj</groupId>
      <artifactId>markdownj-core</artifactId>
      <version>0.4.M20100509</version>
    </dependency>


