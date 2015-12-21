package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
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

	//TODO: test init conditions
    public EmphasisElementScorer(
            TagEmphasisScorer tagEmphasisScorer,
            TagAndAttributeScorer tagAndAttributeScorer) {
		super(SCORE_LABEL);
        this.tagEmphasisScorer = tagEmphasisScorer;
        this.comboScorer = tagAndAttributeScorer;
	}

	@Override
	public int score(Element input) {
        validateScoreInput(input);
        int tagScore = tagEmphasisScorer.score(input.tag());
        TagAndAttribute tagAndAttribute = null;
        int tagAndAttributeScore = comboScorer.score(tagAndAttribute);

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
