package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementScorersSupplier;
import com.grayben.tools.testOracle.SystemUnderTest;
import org.jsoup.nodes.Element;
import org.jsoup.select.NodeTraversor;

import java.util.function.Supplier;

/**
 * Created by Ben Gray on 16/02/2016.
 */
public class SystemUnderTestSupplier
        implements Supplier<SystemUnderTest<Element, ScoredText>> {

    private final ElementScorersSupplier elementScorersSupplier;

    public SystemUnderTestSupplier(ElementScorersSupplier elementScorersSupplier) {
        this.elementScorersSupplier = elementScorersSupplier;
    }

    @Override
    public SystemUnderTest<Element, ScoredText> get() {

        return element -> {
            final ScoringAndFlatteningNodeVisitor nodeVisitor
                    = new ScoringAndFlatteningNodeVisitor(elementScorersSupplier.get());
            final NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitor);
            nodeTraversor.traverse(element);
            return nodeVisitor.getFlatText();
        };
    }
}
