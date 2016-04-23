package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.parser.Tag;

import java.util.HashMap;
import java.util.Map;

/**
 * Scores the segmentation of {@link Tag}s passed in to {@link #score(Tag)}.
 * <p>
 * Created by Ben Gray, 2015.
 */
public class TagSegmentationScorer
extends MapScorer<Tag> {

    /**
     * The string (uniquely) labelling this class.
     * <p>
     * Used to identify this class in a collection of {@link Scorer}s.
     */
    public static final String SCORE_LABEL = "tag-segmentation";

    /**
     * Construct with the specified map.
     * @param tagScores the map with which to construct this instance.
     */
    public TagSegmentationScorer(Map<Tag, Integer> tagScores) {
        super(SCORE_LABEL, tagScores);
    }

    /**
     * @return the default score map for this class
     */
    public static final Map<Tag, Integer> defaultMap() {

        Map<Tag, Integer> tagScoreMap = new HashMap<>();
        tagScoreMap.put(Tag.valueOf("br"), 1);
        tagScoreMap.put(Tag.valueOf("dd"), 1);
        tagScoreMap.put(Tag.valueOf("dl"), 1);
        tagScoreMap.put(Tag.valueOf("dt"), 1);
        tagScoreMap.put(Tag.valueOf("form"), 1);
        tagScoreMap.put(Tag.valueOf("hr"), 1);
        tagScoreMap.put(Tag.valueOf("li"), 1);
        tagScoreMap.put(Tag.valueOf("p"), 1);
        tagScoreMap.put(Tag.valueOf("pre"), 1);
        tagScoreMap.put(Tag.valueOf("table"), 1);
        tagScoreMap.put(Tag.valueOf("tbody"), 1);
        tagScoreMap.put(Tag.valueOf("td"), 1);
        tagScoreMap.put(Tag.valueOf("th"), 1);
        tagScoreMap.put(Tag.valueOf("title"), 1);
        tagScoreMap.put(Tag.valueOf("tr"), 1);
        tagScoreMap.put(Tag.valueOf("ul"), 1);
        tagScoreMap.put(Tag.valueOf("div"), 1);

        return tagScoreMap;

    }

    /**
     * Use the underlying {@link MapScorer} to score the specified input.
     * @param input the input to score
     * @return the score
     */
    @Override
    public int score(Tag input) {
        validateTagInput(input);
        return super.score(input);
    }

    /**
     * @param input the input to validate
     */
    public static void validateTagInput(Tag input){
        if(input.getName().isEmpty()){
            throw new IllegalArgumentException(
                    "Input Tag must have a non-empty name"
            );
        }
    }
}
