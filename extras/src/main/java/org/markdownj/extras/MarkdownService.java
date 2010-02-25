package org.markdownj.extras;

import java.net.URL;

public interface MarkdownService {
	
	/**
	 * The standard line ending for Markdownj
	 */
	public static final String EOL = "\n";
	
	void setHeaderPath(String path);
	void setContentPath(String path);
	void setFooterPath(String path);
	
	void setHeaderUrl(URL url);
	void setContentUrl(URL url);
	void setFooterUrl(URL url);
	
	void setHeader(String header);
	void setContent(String content);
	void setFooter(String footer);

	/**
	 * Transforms the markdown content in html, adding header and footer.
	 * @return the html string.
	 */
	String process();
}
