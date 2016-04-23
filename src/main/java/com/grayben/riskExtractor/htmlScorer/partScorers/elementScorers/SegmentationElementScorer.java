package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

/**
 * Score the segmentation of an {@link org.jsoup.nodes.Element} based upon the {@link org.jsoup.parser.Tag} it contains.
 * <p>
 * Created by Ben Gray, 2015.
 */
public class SegmentationElementScorer extends Scorer<Element> {

    /**
     * The string (uniquely) labelling this class.
     * <p>
     * Used to identify this class in a collection of {@link Scorer}s.
     */
	public final static String SCORE_LABEL = "element-segmentation";

    /**
     * The delegate tag scorer for this object to use in scoring elements.
     */
	Scorer<Tag> tagScorer;

    /**
     * @return the delegate {@link Scorer} used by this object.
     */
    public Scorer<Tag> getTagScorer() {
        return tagScorer;
    }

    /**
     * Constructor accepting a delegate scorer.
     * @param tagScorer the delegate tag scorer to use
     */
    public SegmentationElementScorer(TagSegmentationScorer tagScorer){
		super(SCORE_LABEL);
		this.tagScorer = tagScorer;
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
     * Score the specified element.
     * @param input the element to score
     * @return the score of the specified element; this is the highest score given by the delegate scorers
     * specified at construction of this object
     */
	@Override
	public Integer apply(Element input) {
		validateScoreInput(input);
		int score = tagScorer.score(input.tag());
		return score;
	}
}
