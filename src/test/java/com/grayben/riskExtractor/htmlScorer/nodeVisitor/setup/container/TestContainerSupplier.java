package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation.AnnotatedElement;
import com.grayben.tools.testOracle.testContainer.TestContainer;

import java.util.function.Supplier;

/**
 * For a nominated test configuration: generates the test input, SUT and expected output.
 * <p>
 *
 */
public class TestContainerSupplier implements Supplier<TestContainer<AnnotatedElement, ScoredText>> {

    private final SystemUnderTestSupplier systemUnderTestSupplier;
    private final ActiveOracleSupplier activeOracleSupplier;

    public TestContainerSupplier(SystemUnderTestSupplier systemUnderTestSupplier,
                                 ActiveOracleSupplier activeOracleSupplier) {
        this.systemUnderTestSupplier = systemUnderTestSupplier;
        this.activeOracleSupplier = activeOracleSupplier;
    }

    @Override
    public TestContainer<AnnotatedElement, ScoredText> get() {

        return new TestContainer.Builder<AnnotatedElement, ScoredText>()
                .begin()
                .systemUnderTest(systemUnderTestSupplier.get())
                .oracle(activeOracleSupplier.get())
                .build();

    }
}