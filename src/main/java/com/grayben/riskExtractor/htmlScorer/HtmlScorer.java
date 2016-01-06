/**
 * 
 */
package com.grayben.riskExtractor.htmlScorer;

import java.io.File;
import java.io.IOException;

/**
 * @author beng
 *
 */
public interface HtmlScorer {
	ScoredText scoreHtml(File htmlFile, String charsetName);
	ScoredText scoreHtml(String url) throws IOException;
}
