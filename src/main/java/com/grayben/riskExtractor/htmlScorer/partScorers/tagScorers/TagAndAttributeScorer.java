package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
import org.jsoup.nodes.Attribute;
import org.jsoup.parser.Tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Scores {@link TagAndAttribute} objects.
 * <p>
 * Created by Ben Gray, 2015.
 */
public class TagAndAttributeScorer extends MapScorer<TagAndAttribute> {

    /**
     * The string (uniquely) labelling this class.
     * <p>
     * Used to identify this class in a collection of {@link Scorer}s.
     */
    public final static String SCORE_LABEL = "tag-and-attribute-emphasis";

    /**
     * Construct with the specified map.
     * @param tagScores the map with which to construct this instance.
     */
    public TagAndAttributeScorer(Map<TagAndAttribute, Integer> tagScores) {
        super(SCORE_LABEL, tagScores);
    }

    /**
     * Use the underlying {@link MapScorer} to score the specified input.
     * @param input the input to score
     * @return the score
     */
    @Override
	public int score(TagAndAttribute input) {
        validateScoreInput(input);
		return super.score(input);
	}

    /**
     * @param input the input to validate
     */
    private void validateScoreInput(TagAndAttribute input){
        if(input == null){
            throw new NullPointerException(
                    "The input cannot be null"
            );
        }
        if (input.getAttribute() == null){
            throw new NullPointerException(
                    "The input cannot have null Attribute"
            );
        }
        if (input.getTag() == null){
            throw new NullPointerException(
                    "The input cannot have null Tag"
            );
        }
        if(input.getAttribute().getKey().isEmpty()){
            throw new IllegalArgumentException(
                    "The input cannot have an empty Attribute"
            );
        }
        if(input.getTag().getName().isEmpty()){
            throw new IllegalArgumentException(
                    "The input cannot have an empty Tag"
            );
        }
    }

    /**
     * @return the default score map for this class
     */
	public static Map<TagAndAttribute, Integer> defaultMap() {

        String[] tagNames = {"font", "div", "p"};
        Set<Tag> tags = new HashSet<>();
        /**
         * {@link tags} := ("font", "div", "p")
         */
        for (String tagName: tagNames) {
            tags.add(Tag.valueOf(tagName));
        }

        Set<Attribute> attributes = new HashSet<>();
        /**
         * {@link attributes} := ("style" => "bold", "style" => "underline")
         */
        attributes.add(new Attribute("style", "bold"));
        attributes.add(new Attribute("style", "underline"));

        Set<TagAndAttribute> tagAndAttributes = new HashSet<>();
        /**
         * {@link tagAndAttributes} := cartesianProduct({@link tags}, {@link attributes})
         */
        for (Tag tag: tags)
            for (Attribute attribute : attributes)
                tagAndAttributes.add(
                        new TagAndAttribute(
                                tag,
                                attribute
                        )
                );

        Map<TagAndAttribute, Integer> scoresMap = new HashMap<>();
        for (TagAndAttribute tagAndAttribute : tagAndAttributes) {
            scoresMap.put(tagAndAttribute, 1);
        }

        return scoresMap;

	}
}
