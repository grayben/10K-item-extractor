package com.grayben.riskExtractor.helpers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Element;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by beng on 8/01/2016.
 */
public class ElementScorerSetFunction implements Function<Set<ElementScorerSetFunction.Content>, Set<Scorer<Element>>> {

    public enum Content {
        SEGMENTATION_ELEMENT_SCORER,
        EMPHASIS_ELEMENT_SCORER
    }

    private void validateParams(Set<Content> contents) {
        if (contents == null) {
            throw new NullPointerException(
                    "Set<Content> contents was null"
            );
        }
        if (contents.isEmpty()) {
            throw new IllegalArgumentException(
                    "Set<Content> contents was empty"
            );
        }
        for (Content content : contents) {
            switch (content) {
                case EMPHASIS_ELEMENT_SCORER:
                    break;
                case SEGMENTATION_ELEMENT_SCORER:
                    break;
                default:
                    throw new IllegalArgumentException(
                            "The content " + content + "was not recognised, despite it being defined. Sorry!"
                    );
            }
        }
    }

    @Override
    public Set<Scorer<Element>> apply(Set<Content> contents) {
        validateParams(contents);
        Set<Scorer<Element>> elementScorers = new HashSet<>();
        for (Content content : contents) {
            switch (content) {
                case EMPHASIS_ELEMENT_SCORER:
                    Scorer<Element> emphasisElementScorer = new EmphasisElementScorer(
                            new TagEmphasisScorer(TagEmphasisScorer.defaultMap()),
                            new TagAndAttributeScorer(TagAndAttributeScorer.defaultMap())
                    );
                    elementScorers.add(emphasisElementScorer);

                    break;

                case SEGMENTATION_ELEMENT_SCORER:
                    Scorer<Element> segmentationElementScorer = new SegmentationElementScorer(
                            new TagSegmentationScorer(TagSegmentationScorer.defaultMap())
                    );
                    elementScorers.add(segmentationElementScorer);

                    break;
            }
        }
        return elementScorers;
    }
}
