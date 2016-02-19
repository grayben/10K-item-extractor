package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation.AnnotatedElement;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;

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
}