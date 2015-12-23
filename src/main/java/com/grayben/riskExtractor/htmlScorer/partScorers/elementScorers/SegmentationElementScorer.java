package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

/**
 * Score the segmentation of an {@link org.jsoup.nodes.Element} based upon the {@link org.jsoup.parser.Tag} it contains.
 */
public class SegmentationElementScorer extends Scorer<Element> {

	public final static String SCORE_LABEL = "element-segmentation";

	Scorer<Tag> tagScorer;

	public SegmentationElementScorer(Scorer<Tag> tagScorer){
		super(SCORE_LABEL);
		this.tagScorer = tagScorer;
	}

	@Override
	public int score(Element input) {
		int score = tagScorer.score(input.tag());
		return score;
	}

	@Override
	public String getScoreLabel() {
		return null;
	}


}
