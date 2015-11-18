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
	public ArrayListScoredText scoreHtml(File htmlFile, String charsetName); 
}
