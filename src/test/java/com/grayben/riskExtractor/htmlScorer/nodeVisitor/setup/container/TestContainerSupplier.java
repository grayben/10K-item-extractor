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

    @Override
    public TestContainer<AnnotatedElement, ScoredText> get() {

        return new TestContainer.Builder<AnnotatedElement, ScoredText>()
                .begin()
                .systemUnderTest(new SystemUnderTestFunction().apply(new ElementScorersSupplier().get()))
                .oracle(new ActiveOracleSupplier().get())
                .build();

    }
}