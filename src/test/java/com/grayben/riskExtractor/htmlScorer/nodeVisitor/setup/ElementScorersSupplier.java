package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetSupplier;
import org.jsoup.nodes.Element;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 17/02/2016.
 */
public class ElementScorersSupplier implements Supplier<Set<Scorer<Element>>> {

    @Override
    public Set<Scorer<Element>> get() {
        Set<ElementScorerSetSupplier.Content> contents = new HashSet<>();
        contents.add(ElementScorerSetSupplier.Content.SEGMENTATION_ELEMENT_SCORER);
        contents.add(ElementScorerSetSupplier.Content.EMPHASIS_ELEMENT_SCORER);
        return new ElementScorerSetSupplier(contents).get();
    }
}
