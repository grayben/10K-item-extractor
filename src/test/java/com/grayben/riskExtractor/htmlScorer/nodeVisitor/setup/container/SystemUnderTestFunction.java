package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.tools.testOracle.SystemUnderTest;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.jsoup.select.NodeTraversor;

import java.util.Set;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 16/02/2016.
 */
public class SystemUnderTestFunction
        implements Supplier<SystemUnderTest<Pair<Set<Scorer<Element>>, ? extends Element>, ScoredText>> {

    @Override
    public SystemUnderTest<Pair<Set<Scorer<Element>>, ? extends Element>, ScoredText> get() {

        return pair -> {
            final ScoringAndFlatteningNodeVisitor nodeVisitor
                    = new ScoringAndFlatteningNodeVisitor(pair.getLeft());
            final NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitor);
            nodeTraversor.traverse(pair.getRight());
            return nodeVisitor.getFlatText();
        };
    }
}
