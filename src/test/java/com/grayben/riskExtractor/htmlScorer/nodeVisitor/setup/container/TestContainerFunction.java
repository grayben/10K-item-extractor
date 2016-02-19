package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation.AnnotatedElement;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.jsoup.select.NodeTraversor;

import java.util.Set;
import java.util.function.Supplier;

/**
 * For a nominated test configuration: generates the test input, SUT and expected output.
 * <p>
 *
 */
public class TestContainerFunction
        implements
        Supplier<TestContainer<Pair<Set<Scorer<Element>>, AnnotatedElement>, ScoredText>> {

    private final SystemUnderTestFunction systemUnderTestFunction;
    private final ActiveOracleSupplier activeOracleSupplier;

    public TestContainerFunction() {
        this.systemUnderTestFunction = new SystemUnderTestFunction();
        this.activeOracleSupplier = new ActiveOracleSupplier();
    }

    @Override
    public TestContainer<Pair<Set<Scorer<Element>>, AnnotatedElement>, ScoredText> get() {

        return new TestContainer.Builder<Pair<Set<Scorer<Element>>, AnnotatedElement>, ScoredText>()
                .begin()
                .systemUnderTest(systemUnderTestFunction.get())
                .oracle(activeOracleSupplier.get())
                .build();

    }

    /**
     * Created by Ben Gray on 16/02/2016.
     */
    public static class SystemUnderTestFunction
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
}