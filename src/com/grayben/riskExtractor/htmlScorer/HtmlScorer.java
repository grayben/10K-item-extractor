/**
 * 
 */
package com.grayben.riskExtractor.htmlScorer;

import java.io.File;
import java.util.List;

/**
 * @author beng
 *
 */
public interface HtmlScorer {
	public List<ScoredTextElement> scoreHtml(File htmlFile, String charsetName);
	public List<ScoredTextElement> scoreHtml(String url);
}
