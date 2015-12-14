package com.grayben.riskExtractor.htmlScorer.scorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.scorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.scorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.scorers.tagScorers.TagScorer;
import org.jsoup.nodes.Element;

/**
 * Score the emphasis of an {@link org.jsoup.nodes.Element} based upon the Tag and Attributes it contains.
 */
public class EmphasisElementScorer implements Scorer<Element> {
	
	TagScorer tagScorer;
	TagAndAttributeScorer comboScorer;
	
	@Override
	public int score(Element input) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getScoreLabel() {
		return null;
	}

}
