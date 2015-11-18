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
	public ScoredText scoreHtml(File htmlFile); 
}
