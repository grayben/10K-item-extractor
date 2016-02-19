package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation;

import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementListSupplier;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 17/02/2016.
 */
public class AnnotatedElementFunction implements Function<Supplier<Set<Scorer<Element>>>, AnnotatedElement> {

    @Override
    public AnnotatedElement apply(Supplier<Set<Scorer<Element>>> scorersSupplier) {

        return new AnnotatedElement.TreeAssembler(
                new ElementListSupplier(scorersSupplier.get()).get(),
                scorersSupplier.get()
        ).getRootAnnotation();
    }
}
