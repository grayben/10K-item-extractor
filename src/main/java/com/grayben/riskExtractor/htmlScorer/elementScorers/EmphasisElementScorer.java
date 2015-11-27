package com.grayben.riskExtractor.htmlScorer.elementScorers;

import org.jsoup.nodes.Element;

/**
 * Score the emphasis of an {@link org.jsoup.nodes.Element} based upon the Tag and Attributes it contains.
 */
public class EmphasisElementScorer implements ElementScorer<Element> {
	
	TagScorer tagScorer;
	TagAndAttributeScorer comboScorer;
	
	@Override
	public int score(Element input) {
		// TODO Auto-generated method stub
		return 0;
	}

}
