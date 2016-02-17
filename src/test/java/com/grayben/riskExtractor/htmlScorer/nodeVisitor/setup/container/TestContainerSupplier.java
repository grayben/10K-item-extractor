package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementScorersSupplier;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation.AnnotatedElement;
import com.grayben.tools.testOracle.testContainer.TestContainer;

import java.util.function.Supplier;

/**
 * For a nominated test configuration: generates the test input, SUT and expected output.
 * <p>
 *
 */
public class TestContainerSupplier implements Supplier<TestContainer<AnnotatedElement, ScoredText>> {

    private final SystemUnderTestFunction systemUnderTestFunction;
    private final ElementScorersSupplier elementScorersSupplier;
    private final ActiveOracleSupplier activeOracleSupplier;

    public TestContainerSupplier(SystemUnderTestFunction systemUnderTestFunction, ElementScorersSupplier elementScorersSupplier, ActiveOracleSupplier activeOracleSupplier) {
        this.systemUnderTestFunction = systemUnderTestFunction;
        this.elementScorersSupplier = elementScorersSupplier;
        this.activeOracleSupplier = activeOracleSupplier;
    }

    @Override
    public TestContainer<AnnotatedElement, ScoredText> get() {

        return new TestContainer.Builder<AnnotatedElement, ScoredText>()
                .begin()
                .systemUnderTest(systemUnderTestFunction.apply(elementScorersSupplier.get()))
                .oracle(activeOracleSupplier.get())
                .build();

    }
}