package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Score the emphasis of an {@link org.jsoup.nodes.Element} based upon the Tag and Attributes it contains.
 */
public class EmphasisElementScorer extends Scorer<Element> {

	public final static String SCORE_LABEL = "element-emphasis";
	
	TagEmphasisScorer tagEmphasisScorer;

    public TagEmphasisScorer getTagEmphasisScorer() {
        return tagEmphasisScorer;
    }

    public TagAndAttributeScorer getTagAndAttributeScorer() {
        return tagAndAttributeScorer;
    }

    TagAndAttributeScorer tagAndAttributeScorer;

	//TODO: test init conditions
    public EmphasisElementScorer(
            TagEmphasisScorer tagEmphasisScorer,
            TagAndAttributeScorer tagAndAttributeScorer) {
		super(SCORE_LABEL);
        validateConstructorParams(tagEmphasisScorer, tagAndAttributeScorer);
        this.tagEmphasisScorer = tagEmphasisScorer;
        this.tagAndAttributeScorer = tagAndAttributeScorer;
	}

    private boolean validateConstructorParams(
            TagEmphasisScorer tagEmphasisScorer,
            TagAndAttributeScorer tagAndAttributeScorer){
        if (tagEmphasisScorer == null) {
            throw new NullPointerException(
                    "tagEmphasisScorer was null"
            );
        }
        if (tagAndAttributeScorer == null) {
            throw new NullPointerException(
                    "tagAndAttributeScorer was null"
            );
        }
        return true;
    }

	@Override
	public int score(Element input) {
        List<Integer> subScores = new ArrayList<>();
        validateScoreInput(input);
        subScores.add(tagEmphasisScorer.score(input.tag()));

        List<TagAndAttribute> tagAndAttributes
                = TagAndAttribute.fromElement(input);
        for (TagAndAttribute item :
                tagAndAttributes) {
            subScores.add(tagAndAttributeScorer.score(item));
        }

        Collections.sort(subScores);
        return subScores.get(subScores.size() - 1);
    }

    private void validateScoreInput(Element input){
        if (input == null){
            throw new NullPointerException(
                    "The input cannot be null"
            );
        }
        if(input.attributes() == null){
            throw new NullPointerException(
                    "the input.attributes cannot be null"
            );
        }
    }
}
