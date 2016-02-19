package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation;

import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementListSupplier;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetFunction;
import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 17/02/2016.
 */
public class AnnotatedElementSupplier implements Supplier<AnnotatedElement> {

    private final Function<Set<ElementScorerSetFunction.Content>, Set<Scorer<Element>>> scorersFunction;
    private final Set<ElementScorerSetFunction.Content> contents;

    public AnnotatedElementSupplier(
            Function<Set<ElementScorerSetFunction.Content>, Set<Scorer<Element>>> scorersFunction,
            Set<ElementScorerSetFunction.Content> contents
    ) {
        this.scorersFunction = scorersFunction;
        this.contents = contents;
    }

    @Override
    public AnnotatedElement get() {

        return new AnnotatedElement.TreeAssembler(
                new ElementListSupplier(scorersFunction.apply(contents)).get(),
                scorersFunction.apply(contents)
        ).getRootAnnotation();
    }
}
