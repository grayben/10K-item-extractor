package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation;

import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementListSupplier;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 17/02/2016.
 */
public class AnnotatedElementSupplier implements Supplier<AnnotatedElement> {

    private final Supplier<Set<Scorer<Element>>> scorersSupplier;

    public AnnotatedElementSupplier(Supplier<Set<Scorer<Element>>> scorersSupplier) {
        this.scorersSupplier = scorersSupplier;
    }

    @Override
    public AnnotatedElement get() {

        return new AnnotatedElement.TreeAssembler(
                new ElementListSupplier(scorersSupplier.get()).get(),
                scorersSupplier.get()
        ).getRootAnnotation();
    }
}
