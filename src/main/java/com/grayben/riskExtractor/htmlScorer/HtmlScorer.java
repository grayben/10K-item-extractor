/**
 * 
 */
package com.grayben.riskExtractor.htmlScorer;

import java.io.File;
import java.io.IOException;

/**
 * A class implementing this interface is able to process HTML from a number of sources
 * and output a representation of the readable text in that HTML, with scores attached to
 * elements of this text.
 * <p>
 * Created by Ben Gray, 2015.
 */
public interface HtmlScorer {

    /**
     * Score HTML stored locally.
     *
     * @param htmlFile the file containing the HTML to score
     * @param charsetName the name of the charset used to encode the file
     * @return the scored text
     */
	ScoredText scoreHtml(File htmlFile, String charsetName);

    /**
     * Score HTML stored remotely.
     *
     * @param url the URL of the file containing the HTML to score
     * @return the scored text
     * @throws IOException
     */
	ScoredText scoreHtml(String url) throws IOException;
}
