package com.grayben.riskExtractor.HtmlScorer.scorers;

import org.jsoup.nodes.Element;

/**
 * Score the segmentation of an {@link org.jsoup.nodes.Element} based upon the {@link org.jsoup.parser.Tag} it contains.
 */
public class SegmentationElementScorer implements Scorer<Element> {
	
	
	TagScorer tagScorer;

	@Override
	public int score(Element input) {
		// TODO Auto-generated method stub
		return 0;
	}

}
