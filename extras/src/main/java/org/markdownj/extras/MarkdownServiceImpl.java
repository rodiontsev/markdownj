package org.markdownj.extras;

import java.net.URL;

import com.petebevin.markdown.MarkdownProcessor;

/**
 * Basic implementation of MarkdownService.
 * 
 *
 */
public class MarkdownServiceImpl implements MarkdownService {
	
	/**
	 * The string containing the html used as header.
	 */
	private String header;
	
	/**
	 * The string containing the html used as footer.
	 */
	private String footer;
	
	/**
	 * The string containing the markdown to transform.
	 */
	private String content;

	public MarkdownServiceImpl() {
	}

	public String process() {
		MarkdownProcessor processor = new MarkdownProcessor();
		String hs = (header == null) ? "" : header;
		String fs = (footer == null) ? "" : footer;
		return String.format("%s%s%s", hs, processor.markdown(content), fs);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentPath(String path) {
		content = FileUtils.readFileFromPath(path);
	}

	public void setContentUrl(URL url) {
		content = FileUtils.readFileFromUrl(url);
	}

	public void setFooterPath(String path) {
		footer = FileUtils.readFileFromPath(path);
	}

	public void setFooterUrl(URL url) {
		footer = FileUtils.readFileFromUrl(url);
	}

	public void setHeaderPath(String path) {
		header = FileUtils.readFileFromPath(path);
	}

	public void setHeaderUrl(URL url) {
		header = FileUtils.readFileFromUrl(url);
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}
