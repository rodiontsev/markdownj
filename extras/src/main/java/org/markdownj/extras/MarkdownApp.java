/*
Copyright (c) 2005, Martian Software
Authors: Pete Bevin, John Mutchek
http://www.martiansoftware.com/markdownj

All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

* Redistributions of source code must retain the above copyright notice,
  this list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright
  notice, this list of conditions and the following disclaimer in the
  documentation and/or other materials provided with the distribution.

* Neither the name "Markdown" nor the names of its contributors may
  be used to endorse or promote products derived from this software
  without specific prior written permission.

This software is provided by the copyright holders and contributors "as
is" and any express or implied warranties, including, but not limited
to, the implied warranties of merchantability and fitness for a
particular purpose are disclaimed. In no event shall the copyright owner
or contributors be liable for any direct, indirect, incidental, special,
exemplary, or consequential damages (including, but not limited to,
procurement of substitute goods or services; loss of use, data, or
profits; or business interruption) however caused and on any theory of
liability, whether in contract, strict liability, or tort (including
negligence or otherwise) arising in any way out of the use of this
software, even if advised of the possibility of such damage.

*/
package org.markdownj.extras;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// mvn exec:java -Dexec.mainClass="org.markdownj.extras.MarkdownApp" -Dexec.args="--header src/test/resources/header.html --footer src/test/resources/footer.html --source src/test/resources/site --destination target/markdownj"

/**
 * Markdown app.
 * Transforms in html every file found in source dir, maintaining the original directory structure.
 * 
 * mvn exec:java -Dexec.mainClass="org.markdownj.extras.MarkdownApp"
 * 
 * mvn exec:java -Dexec.mainClass="org.markdownj.extras.MarkdownApp" \
 * -Dexec.args="--header src/test/resources/header.html --footer src/test/resources/footer.html \
 * --source src/test/resources/site --destination target/markdownj"
 * 
 */
public class MarkdownApp
{

    static private Logger logger; @SuppressWarnings("static-access")
    protected Logger log()  { if (this.logger == null) this.logger = LoggerFactory.getLogger(this.getClass()); return this.logger; }

    /**
     * Absolute path of the directory containing markdown files.
     */
	private String source;

	/**
	 * Absolute path of the target directory. 
	 */
	private String destination;
	
	/**
	 * Path to the html file used as header for every transformed markdown file.
	 * Could be absolute or relative.
	 * 
	 */
	private String header;

	/**
	 * Path to the html file used as footer for every transformed markdown file.
	 * Could be absolute or relative.
	 * 
	 */
	private String footer;
	
	private MarkdownService markdown = new MarkdownServiceImpl();

	public static void main(String[] args)
	{
		MarkdownApp app = new MarkdownApp();

		app.log().info("Markdown app starting with args: {}", Arrays.toString(args));
		
		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		options.addOption("s", "source", true, "The source directory for markdown files");
		options.addOption("d", "destination", true, "The destination directory for html files");
		options.addOption("h", "header", true, "The path to the html header file");
		options.addOption("f", "footer", true, "The path to the html footer file");

		HelpFormatter formatter = new HelpFormatter();
		String helpHeader = String.format("%s", MarkdownApp.class.getName());

		try {
			CommandLine line = parser.parse(options, args);
			app.process(line);
		} catch (ParseException e) {
			app.log().warn(e.getMessage(), e);
			formatter.printHelp(helpHeader, options);
		}

	}

	public void process(CommandLine commandLine)
	{
		File sourceFile = null;
		File destinationFile = null;
		if (commandLine.hasOption("source")) {
			String sourceArg = commandLine.getOptionValue("source");
			if (sourceArg == null) {
				log().warn("sourceArg NULL. Exiting");
				return;
			}
			sourceFile = new File(sourceArg);
			setSource(FileUtils.normalizedPath(sourceFile.getAbsolutePath()));
			log().debug("using source path '{}'", source);
			if (!sourceFile.exists()) {
				log().warn("source not found '{}'. Exiting", source);
				return;
			}
		} else {
			log().warn("source argument is mandatory. Exiting");
			return;
		}

		if (commandLine.hasOption("destination")) {
			destinationFile = new File(commandLine.getOptionValue("destination"));
			setDestination(FileUtils.normalizedPath(destinationFile.getAbsolutePath()));
			log().debug("using destination path '{}'", destination);
			if (!destinationFile.exists()) {
				destinationFile.mkdirs();
			}
		}

		if (commandLine.hasOption("header")) {
			setHeader(commandLine.getOptionValue("header"));
		}
		
		if (commandLine.hasOption("footer")) {
			setFooter(commandLine.getOptionValue("footer"));
		}
		process();
	}
	
	public void process()
	{
		File sourceFile = new File(getSource());

		markdown.setHeaderPath(getHeader());
		markdown.setFooterPath(getFooter());

		try {
			traverse(sourceFile);
		} catch (IOException e) {
			log().warn(e.getMessage(), e);
		}		
	}

	public final void traverse(final File f) throws IOException {
		if (f.isDirectory()) {
			final File[] childs = f.listFiles();
			for (File child : childs) {
				traverse(child);
			}
			return;
		}
		processFile(f);
	}

	public void processFile(final File f) {
		String mdFilepath = FileUtils.normalizedPath(f.getAbsolutePath());
		String df = mdFilepath.replaceFirst(source, destination);
		String destinationFile = FileUtils.changeExtension(df, ".html");
		log().debug("process '{}' -> '{}'", mdFilepath, destinationFile);
		String markdownContent = FileUtils.readFileFromPath(mdFilepath);
		markdown.setContent(markdownContent);
		String html = markdown.process();
		try {
			FileUtils.writeFile(destinationFile, html);
		} catch (IOException e) {
			log().warn(e.getMessage(), e);
		}
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

}
