package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.MapScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;

public class TagAndAttributeScorer
		extends MapScorer<TagAndAttribute> {

    public final static String SCORE_LABEL
            = "tag-and-attribute-emphasis";

    protected TagAndAttributeScorer() {
        super(SCORE_LABEL);
    }

    @Override
	public int score(TagAndAttribute input) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getScoreLabel() {
		return null;
	}


}
