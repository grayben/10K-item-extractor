package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
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



        //TODO: think about wildcard generics: how to solve problem of SUT(Element) and Oracle(AnnotatedElement): Element = AnnotatedElement
        return new TestContainer.Builder<AnnotatedElement, ScoredText>()
                .begin()
                .systemUnderTest(new SystemUnderTestSupplier().get())
                .oracle(new ActiveOracleSupplier().get())
                .build();

    }
}