package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.tools.testOracle.SystemUnderTest;
import org.jsoup.nodes.Element;
import org.jsoup.select.NodeTraversor;

import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 16/02/2016.
 */
public class SystemUnderTestSupplier
        implements Supplier<SystemUnderTest<Element, ScoredText>> {

    private final Set<Scorer<Element>> elementScorers;

    public SystemUnderTestSupplier(final Set<Scorer<Element>> elementScorers) {
        this.elementScorers = elementScorers;
    }

    @Override
    public SystemUnderTest<Element, ScoredText> get() {

        return element -> {
            final ScoringAndFlatteningNodeVisitor nodeVisitor = new ScoringAndFlatteningNodeVisitor(elementScorers);
            final NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitor);
            nodeTraversor.traverse(element);
            return nodeVisitor.getFlatText();
        };
    }
}
