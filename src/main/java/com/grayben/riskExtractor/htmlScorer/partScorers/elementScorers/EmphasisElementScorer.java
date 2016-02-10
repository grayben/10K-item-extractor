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
 * Score the emphasis of an {@link org.jsoup.nodes.Element} based upon the
 * {@link org.jsoup.parser.Tag} and {@link org.jsoup.nodes.Attributes} it contains.
 * <p>
 * Created by Ben Gray in 2015.
 */
public class EmphasisElementScorer extends Scorer<Element> {

    /**
     * The string (uniquely) labelling this class.
     * <p>
     * Used to identify this class in a collection of {@link Scorer}s.
     */
	public final static String SCORE_LABEL = "element-emphasis";

    /**
     * The delegate tag scorer for this object to use in scoring elements.
     */
	private final TagEmphasisScorer tagEmphasisScorer;

    /**
     * The delegate tag/attribute combo scorer for this object to use in scoring elements.
     */
    private final TagAndAttributeScorer tagAndAttributeScorer;

    /**
     * @return the delegate {@link TagEmphasisScorer} used by this object.
     */
    public TagEmphasisScorer getTagEmphasisScorer() {
        return tagEmphasisScorer;
    }

    /**
     * @return the delegate {@link TagAndAttributeScorer} used by this object.
     */
    public TagAndAttributeScorer getTagAndAttributeScorer() {
        return tagAndAttributeScorer;
    }

	//TODO: test init conditions

    /**
     * Constructor taking in delegate scorers.
     * @param tagEmphasisScorer the delegate tag scorer to use
     * @param tagAndAttributeScorer the delegate tag/attribute combo scorer to use
     */
    public EmphasisElementScorer(
            TagEmphasisScorer tagEmphasisScorer,
            TagAndAttributeScorer tagAndAttributeScorer) {
		super(SCORE_LABEL);
        validateConstructorParams(tagEmphasisScorer, tagAndAttributeScorer);
        this.tagEmphasisScorer = tagEmphasisScorer;
        this.tagAndAttributeScorer = tagAndAttributeScorer;
	}

    /**
     * Ensure no constructor arguments were null.
     * @param tagEmphasisScorer the first argument to validate
     * @param tagAndAttributeScorer the second argument to validate
     */
    private void validateConstructorParams(
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
    }

    /**
     * @param input the input to validate
     */
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

    /**
     * Return same as {@link #score(Element)}.
     * @param input the element to score
     * @return the score of the specified element
     */
    @Override
    final public Integer apply(Element input){
        return score(input);
    }

    /**
     * Score the specified element.
     * @param input the element to score
     * @return the score of the specified element; this is the highest score given by the delegate scorers
     * specified at construction of this object
     */
    public int score(Element input) {
        /**
         * Store the scores of each delegate scorer.
         */
        List<Integer> subScores = new ArrayList<>();
        validateScoreInput(input);

        /**
         * Store the score from {@link #tagEmphasisScorer}.
         */
        subScores.add(tagEmphasisScorer.score(input.tag()));

        List<TagAndAttribute> tagAndAttributes
                = TagAndAttribute.fromElement(input);
        for (TagAndAttribute item :
                tagAndAttributes) {
            /**
             * Store the scores from {@link #tagAndAttributeScorer} on each attribute paired with the tag.
             */
            subScores.add(tagAndAttributeScorer.score(item));
        }

        /**
         * Sort the delegate scores so that the highest score can be retrieved.
         */
        Collections.sort(subScores);

        /**
         * Now that {@link subScores} is sorted, return the highest score.
         */
        return subScores.get(subScores.size() - 1);
    }
}
