/*
Copyright (c) 2005, Pete Bevin.
<http://markdownj.petebevin.com>

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

package com.petebevin.markdown.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.petebevin.markdown.Entities;
import com.petebevin.markdown.MarkdownProcessor;

public class HtmlEntitiesEncoding {
    MarkdownProcessor m;

    @Before
    public void createProcessor() {
        m = new MarkdownProcessor();
    }

    @Test
    public void testDefaultBehaviour() {
    	String md = "This is a normal paragraph with àccents!";
    	String expected = "<p>This is a normal paragraph with àccents!</p>\n";
    	String html = m.markdown(md);
        assertEquals(expected, html);
    }

    @Test
    public void testHtmlEntitiesCustomSetting() {
        Map<Character, String> htmlEntities = new HashMap<Character, String>();
        htmlEntities.put(Character.valueOf((char)224), "&agrave;");
        String md = "This is a normal paragraph & àccènts!";
        String expected = "<p>This is a normal paragraph &amp; &agrave;ccènts!</p>\n";
    	m.setHtmlEntities(htmlEntities);
    	String html = m.markdown(md);
        assertEquals(expected, html);
    }

    @Test
    public void testHtmlEntities() {
        String md = "XXXI paid 2 £ this nòrmal paragraph & <àccènts>!";
        String expected = "<p>XXXI paid 2 &#163; this n&#242;rmal paragraph &amp; &lt;&#224;cc&#232;nts>!</p>\n";
        m.setHtmlEntities(Entities.HTML_401_NO_TAG);
        String html = m.markdown(md);
        assertEquals(expected, html);
    }

    @Test
    public void testCodeBlock() {
        String md = "fìrst linè\n\n\tthìs ìs còde\n";
        String expected = "<p>f&#236;rst lin&#232;</p>\n\n<pre><code>th&#236;s &#236;s c&#242;de\n</code></pre>\n";
        m.setHtmlEntities(Entities.HTML_401_NO_TAG);
        String html = m.markdown(md);
        assertEquals(expected, html);
    }
    @Test
    public void testAdvancedSyntaxAndStrictHtmlEncoding() {
        String md = "XXXAnother <http://example.tld> link";
        String expected = "<p>XXXAnother <a href=\"http://example.tld\">http://example.tld</a> link</p>\n";
        m.setHtmlEntities(Entities.HTML_401_NO_TAG);
        String html = m.markdown(md);
        assertEquals(expected, html);
    }
}
