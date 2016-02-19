package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation;

import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementListSupplier;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetFunction;
import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.function.Function;

/**
 * Created by Ben Gray on 17/02/2016.
 */
public class AnnotatedElementFunction implements Function<Set<ElementScorerSetFunction.Content>, AnnotatedElement> {

    private final Function<Set<ElementScorerSetFunction.Content>, Set<Scorer<Element>>> scorersFunction;

    public AnnotatedElementFunction() {
        this.scorersFunction = new ElementScorerSetFunction();
    }

    @Override
    public AnnotatedElement apply(Set<ElementScorerSetFunction.Content> contents) {

        return new AnnotatedElement.TreeAssembler(
                new ElementListSupplier(scorersFunction.apply(contents)).get(),
                scorersFunction.apply(contents)
        ).getRootAnnotation();
    }
}
    