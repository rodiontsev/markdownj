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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.petebevin.markdown.MarkdownProcessor;

@RunWith(value = Parameterized.class)
public class MarkupFileTester extends AbstractMarkupFileTester {

    private final static String[] TEST_FILENAMES = new String[] {
        "/dingus.txt",
        "/paragraphs.txt",
        "/snippets.txt",
        "/lists.txt"
    };

    public MarkupFileTester(TestResultPair pair) {
        super(pair);
        this.pair = pair;
    }

    @Parameters
    public static Collection<Object[]> testResultPairs() throws IOException {
        List<TestResultPair> fullResultPairList = new ArrayList<TestResultPair>();
        for (String filename : TEST_FILENAMES) {
            fullResultPairList.addAll(newTestResultPairList(filename));
        }

        Collection<Object[]> testResultPairs = new ArrayList<Object[]>();
        for (TestResultPair p : fullResultPairList) {
            testResultPairs.add(new Object[] { p });
        }
        return testResultPairs;
    }
    
    @Test
    public void runTest() {
        MarkdownProcessor markup = new MarkdownProcessor();
        assertEquals(pair.toString(), pair.getResult().trim(), markup.markdown(pair.getTest()).trim());
    }
}
