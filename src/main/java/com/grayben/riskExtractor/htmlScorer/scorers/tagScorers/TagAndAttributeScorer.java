package com.grayben.riskExtractor.htmlScorer.scorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.TagAndAttribute;
import com.grayben.riskExtractor.htmlScorer.scorers.Scorer;

public class TagAndAttributeScorer
		extends Scorer<TagAndAttribute> {

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
