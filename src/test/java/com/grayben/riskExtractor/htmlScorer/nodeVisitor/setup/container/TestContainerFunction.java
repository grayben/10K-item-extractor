package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation.AnnotatedElement;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetFunction;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.function.Function;

/**
 * For a nominated test configuration: generates the test input, SUT and expected output.
 * <p>
 *
 */
public class TestContainerFunction implements Function<Set<Scorer<Element>>, TestContainer<AnnotatedElement, ScoredText>> {

    private final SystemUnderTestFunction systemUnderTestFunction;
    private final ActiveOracleSupplier activeOracleSupplier;
    private final ElementScorerSetFunction elementScorerSetFunction;

    public TestContainerFunction() {
        this.systemUnderTestFunction = new SystemUnderTestFunction();
        this.activeOracleSupplier = new ActiveOracleSupplier();
        elementScorerSetFunction = new ElementScorerSetFunction();
    }

    @Override
    public TestContainer<AnnotatedElement, ScoredText> apply(Set<Scorer<Element>> elementScorerSet) {

        return new TestContainer.Builder<AnnotatedElement, ScoredText>()
                .begin()
                .systemUnderTest(systemUnderTestFunction.apply(elementScorerSet))
                .oracle(activeOracleSupplier.get())
                .build();

    }
}