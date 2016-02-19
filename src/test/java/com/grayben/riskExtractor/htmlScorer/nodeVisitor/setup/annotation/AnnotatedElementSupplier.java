package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation;

import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementListSupplier;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementScorersSupplier;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 17/02/2016.
 */
public class AnnotatedElementSupplier implements Supplier<AnnotatedElement> {
    @Override
    public AnnotatedElement get() {

        Set<Scorer<Element>> elementScorers = new ElementScorersSupplier().get();

        return new AnnotatedElement.TreeAssembler(
                new ElementListSupplier(elementScorers).get(),
                elementScorers
        ).getRootAnnotation();
    }
}
