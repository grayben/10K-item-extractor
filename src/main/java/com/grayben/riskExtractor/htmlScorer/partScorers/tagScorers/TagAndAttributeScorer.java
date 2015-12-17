package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.MapScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;

import java.util.Map;

public class TagAndAttributeScorer
		extends MapScorer<TagAndAttribute> {

    public final static String SCORE_LABEL
            = "tag-and-attribute-emphasis";

    protected TagAndAttributeScorer(Map<TagAndAttribute, Integer> tagScores) {
        super(SCORE_LABEL, tagScores);
    }

    @Override
	public int score(TagAndAttribute input) {
		return super.score(input);
	}

	@Override
	public String getScoreLabel() {
		return null;
	}


}
