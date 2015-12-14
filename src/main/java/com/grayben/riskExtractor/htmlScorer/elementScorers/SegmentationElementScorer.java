package com.grayben.riskExtractor.htmlScorer.elementScorers;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

/**
 * Score the segmentation of an {@link org.jsoup.nodes.Element} based upon the {@link org.jsoup.parser.Tag} it contains.
 */
public class SegmentationElementScorer implements Scorer<Element> {

	Scorer<Tag> tagScorer;

	public SegmentationElementScorer(Scorer<Tag> tagScorer){
		super();
		this.tagScorer = tagScorer;
	}

	@Override
	public int score(Element input) {
		Integer integer = null;
		return integer;
	}

	@Override
	public String getScoreLabel() {
		return null;
	}


}
