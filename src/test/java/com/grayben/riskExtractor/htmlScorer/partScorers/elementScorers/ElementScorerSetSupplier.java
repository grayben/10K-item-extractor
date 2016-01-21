package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Element;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by beng on 8/01/2016.
 */
public class ElementScorerSetSupplier implements Supplier<Set<Scorer<Element>>> {

    public enum Content {
        SEGMENTATION_ELEMENT_SCORER,
        EMPHASIS_ELEMENT_SCORER
    }

    private Set<Content> contents = null;

    private Set<Scorer<Element>> elementScorerSet = null;

    public ElementScorerSetSupplier(Set<Content> contents) {
        processInputParams(contents);
    }

    private void processInputParams(Set<Content> contents) {
        validateInputParams(contents);
        this.contents = contents;
    }

    private void validateInputParams(Set<Content> contents) {
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
    public Set<Scorer<Element>> get() {
        if (elementScorerSet == null) {
            generateElementScorers();
        }
        return this.elementScorerSet;
    }

    private void generateElementScorers() {
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
        this.elementScorerSet = elementScorers;
    }
}
