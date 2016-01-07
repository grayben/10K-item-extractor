package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

/**
 * Score the segmentation of an {@link org.jsoup.nodes.Element} based upon the {@link org.jsoup.parser.Tag} it contains.
 */
public class SegmentationElementScorer extends Scorer<Element> {

	public final static String SCORE_LABEL = "element-segmentation";

	Scorer<Tag> tagScorer;

    public Scorer<Tag> getTagScorer() {
        return tagScorer;
    }

    public SegmentationElementScorer(TagSegmentationScorer tagScorer){
		super(SCORE_LABEL);
		this.tagScorer = tagScorer;
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

	@Override
	public Integer apply(Element input) {
		validateScoreInput(input);
		int score = tagScorer.score(input.tag());
		return score;
	}
}
