package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import org.jsoup.nodes.Element;
import org.jsoup.select.NodeTraversor;

import java.util.Set;
import java.util.function.Supplier;

/**
 * For a nominated test configuration: generates the test input, SUT and expected output.
 * <p>
 *
 */
public class TestContainerSupplier implements Supplier<TestContainer<AnnotatedElement, ScoredText>> {

    @Override
    public TestContainer<AnnotatedElement, ScoredText> get() {
        Supplier<Set<Scorer<Element>>> configElementScorerSetSupplier
                = () -> SetupHelpers.configureElementScorerSet();

        Supplier<AnnotatedElement> configAnnotatedElementSupplier
                = () -> SetupHelpers.configureAnnotatedElement();

        Supplier<SystemUnderTest<AnnotatedElement, ScoredText>> systemUnderTestSupplier = () -> {

            Supplier<ScoringAndFlatteningNodeVisitor> scoringAndFlatteningNodeVisitorSupplier
                    = () -> new ScoringAndFlatteningNodeVisitor(configElementScorerSetSupplier.get());

            return config1 -> {
                Element rootElement = SetupHelpers.configureAnnotatedElement();
                ScoringAndFlatteningNodeVisitor nodeVisitor = scoringAndFlatteningNodeVisitorSupplier.get();
                NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitor);
                nodeTraversor.traverse(rootElement);
                return nodeVisitor.getFlatText();
            };
        };

        //TODO: use AnnotatedElement as test input!
        return new TestContainer.Builder<AnnotatedElement, ScoredText>()
                .begin()
                .systemUnderTest(systemUnderTestSupplier.get())
                .oracle(new ActiveOracleSupplier().get())
                .build();

    }
}