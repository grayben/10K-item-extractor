package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation;

import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementListSupplier;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.function.Function;

/**
 * Created by Ben Gray on 17/02/2016.
 */
public class AnnotatedElementFunction implements Function<Set<Scorer<Element>>, AnnotatedElement> {

    @Override
    public AnnotatedElement apply(Set<Scorer<Element>> scorers) {

        return new AnnotatedElement.TreeAssembler(
                new ElementListSupplier(scorers).get(),
                scorers
        ).getRootAnnotation();
    }
}
