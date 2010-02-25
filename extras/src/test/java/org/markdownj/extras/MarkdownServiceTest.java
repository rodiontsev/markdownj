package org.markdownj.extras;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.markdownj.extras.MarkdownService;
import org.markdownj.extras.MarkdownServiceImpl;

import static org.junit.Assert.*;

/**
 *
 */
public class MarkdownServiceTest {
	
	MarkdownService service;
	
	URL headerUrl;
	
	URL footerUrl;

	URL winEncodingHeaderUrl;
	
	String markdownContent = "# This is an H1";
	String htmlContent = "<h1>This is an H1</h1>";
	
	String expected;

	@Before
	public void initData() {
		service = new MarkdownServiceImpl();
		headerUrl = this.getClass().getResource("/site/templates/header.html");
		winEncodingHeaderUrl = this.getClass().getResource("/site/templates/header-win.html");
		footerUrl = this.getClass().getResource("/site/templates/footer.html");
		expected = String.format("<html>%s%s%s%s</html>%s", MarkdownService.EOL, htmlContent, MarkdownService.EOL, MarkdownService.EOL, MarkdownService.EOL);
		service.setContent(markdownContent);
	}

	@Test
	public void testUsingPaths() {
		service.setHeaderPath(headerUrl.getPath());
		service.setFooterPath(footerUrl.getPath());
		assertEquals(expected, service.process());
	}

	@Test
	public void testUsingUrls() {
		service.setHeaderUrl(headerUrl);
		service.setFooterUrl(footerUrl);
		assertEquals(expected, service.process());
	}

	@Test
	public void testUsingStrings() {
		service.setHeader(String.format("<html>%s", MarkdownService.EOL));
		service.setFooter(String.format("%s</html>%s", MarkdownService.EOL, MarkdownService.EOL));
		assertEquals(expected, service.process());
	}

	@Test
	public void testOnlyContent() {
		String expectedForContentOnly = htmlContent + MarkdownService.EOL;
		assertEquals(expectedForContentOnly, service.process());
	}
	@Test
	public void testWinEncoding() {
		service.setHeaderUrl(winEncodingHeaderUrl);
		service.setFooterUrl(footerUrl);
		assertEquals(expected, service.process());
	}
}