package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import org.jsoup.nodes.Element;

/**
 * Score the emphasis of an {@link org.jsoup.nodes.Element} based upon the Tag and Attributes it contains.
 */
public class EmphasisElementScorer extends Scorer<Element> {

	public final static String SCORE_LABEL = "element-emphasis";
	
	TagEmphasisScorer tagEmphasisScorer;
	TagAndAttributeScorer comboScorer;

	public EmphasisElementScorer() {
		super(SCORE_LABEL);
	}

	@Override
	public int score(Element input) {
        validateScoreInput(input);
        return 0;
	}

    private void validateScoreInput(Element input){
        if (input == null){
            throw new NullPointerException(
                    "The input cannot be null"
            );
        }
    }

	@Override
	public String getScoreLabel() {
		return null;
	}

}
