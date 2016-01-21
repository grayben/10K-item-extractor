package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
import org.jsoup.nodes.Attribute;
import org.jsoup.parser.Tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TagAndAttributeScorer
		extends MapScorer<TagAndAttribute> {

    public final static String SCORE_LABEL
            = "tag-and-attribute-emphasis";

    public TagAndAttributeScorer(Map<TagAndAttribute, Integer> tagScores) {
        super(SCORE_LABEL, tagScores);
    }

    @Override
	public int score(TagAndAttribute input) {
        validateScoreInput(input);
		return super.score(input);
	}

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

	public static final Map<TagAndAttribute, Integer> defaultMap() {

        String[] tagNames = {"font", "div", "p"};
        Set<Tag> tags = new HashSet<>();
        for (String tagName: tagNames) {
            tags.add(Tag.valueOf(tagName));
        }

        Set<Attribute> attributes = new HashSet<>();
        attributes.add(new Attribute("style", "bold"));
        attributes.add(new Attribute("style", "underline"));

        Set<TagAndAttribute> tagAndAttributes = new HashSet<>();
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
