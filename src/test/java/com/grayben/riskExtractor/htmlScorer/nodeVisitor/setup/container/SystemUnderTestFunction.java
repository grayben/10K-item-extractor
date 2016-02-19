package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetFunction;
import com.grayben.tools.testOracle.SystemUnderTest;
import org.jsoup.nodes.Element;
import org.jsoup.select.NodeTraversor;

import java.util.Set;
import java.util.function.Function;

/**
 * Created by Ben Gray on 16/02/2016.
 */
public class SystemUnderTestFunction
        implements Function<Set<ElementScorerSetFunction.Content>, SystemUnderTest<Element, ScoredText>> {

    private final ElementScorerSetFunction elementScorerSetFunction;

    public SystemUnderTestFunction() {
        elementScorerSetFunction = new ElementScorerSetFunction();
    }

    @Override
    public SystemUnderTest<Element, ScoredText> apply(Set<ElementScorerSetFunction.Content> contents) {

        return element -> {
            final ScoringAndFlatteningNodeVisitor nodeVisitor
                    = new ScoringAndFlatteningNodeVisitor(elementScorerSetFunction.apply(contents));
            final NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitor);
            nodeTraversor.traverse(element);
            return nodeVisitor.getFlatText();
        };
    }
}
