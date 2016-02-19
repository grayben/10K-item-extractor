package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.tools.testOracle.SystemUnderTest;
import org.jsoup.nodes.Element;
import org.jsoup.select.NodeTraversor;

import java.util.Set;
import java.util.function.Function;

/**
 * Created by Ben Gray on 16/02/2016.
 */
public class SystemUnderTestFunction
        implements Function<Set<Scorer<Element>>, SystemUnderTest<Element, ScoredText>> {

    @Override
    public SystemUnderTest<Element, ScoredText> apply(Set<Scorer<Element>> elementScorerSet) {

        return element -> {
            final ScoringAndFlatteningNodeVisitor nodeVisitor
                    = new ScoringAndFlatteningNodeVisitor(elementScorerSet);
            final NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitor);
            nodeTraversor.traverse(element);
            return nodeVisitor.getFlatText();
        };
    }
}
