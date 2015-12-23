package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import org.jsoup.parser.Tag;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by beng on 22/12/2015.
 */
public class TagSegmentationScorer
extends MapScorer<Tag> {
    public static final String SCORE_LABEL = "tag-emphasis";

    public TagSegmentationScorer(Map<Tag, Integer> tagScores) {
        super(SCORE_LABEL, tagScores);
    }

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

    @Override
    public int score(Tag input) {
        validateTagInput(input);
        return super.score(input);
    }

    public static void validateTagInput(Tag input){
        if(input.getName().isEmpty()){
            throw new IllegalArgumentException(
                    "Input Tag must have a non-empty name"
            );
        }
    }
}
