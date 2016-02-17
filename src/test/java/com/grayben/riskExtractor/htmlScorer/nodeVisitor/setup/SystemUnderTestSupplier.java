package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.tools.testOracle.SystemUnderTest;
import org.jsoup.nodes.Element;
import org.jsoup.select.NodeTraversor;

import java.util.function.Supplier;

/**
 * Created by Ben Gray on 16/02/2016.
 */
public class SystemUnderTestSupplier
        implements Supplier<SystemUnderTest<Element, ScoredText>> {


    @Override
    public SystemUnderTest<Element, ScoredText> get() {

        Supplier<ScoringAndFlatteningNodeVisitor> scoringAndFlatteningNodeVisitorSupplier
                = () -> new ScoringAndFlatteningNodeVisitor(new ElementScorersSupplier().get());

        return element -> {
            ScoringAndFlatteningNodeVisitor nodeVisitor = scoringAndFlatteningNodeVisitorSupplier.get();
            NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitor);
            nodeTraversor.traverse(element);
            return nodeVisitor.getFlatText();
        };
    }
}
