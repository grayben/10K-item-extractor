/**
 * 
 */
package com.grayben.riskExtractor.htmlScorer;

import java.io.File;

/**
 * @author beng
 *
 */
public interface HtmlScorer {
	ScoredText scoreHtml(File htmlFile, String charsetName);
	ScoredText scoreHtml(String url);
}
