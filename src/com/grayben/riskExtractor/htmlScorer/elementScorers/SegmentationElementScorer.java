package com.grayben.riskExtractor.htmlScorer.elementScorers;

import org.jsoup.nodes.Element;

/**
 * Score the segmentation of an {@link org.jsoup.nodes.Element} based upon the {@link org.jsoup.parser.Tag} it contains.
 */
public class SegmentationElementScorer implements ElementScorer<Element> {
	
	
	TagScorer tagScorer;

	@Override
	public int score(Element input) {
		// TODO Auto-generated method stub
		return 0;
	}

}
