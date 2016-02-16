package com.grayben.riskExtractor.htmlScorer.nodeVisitor.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * For a nominated test configuration: generates the test input, SUT and expected output.
 * <p>
 *
 */
public class TestContainerSupplier implements Supplier<TestContainer<TestContainerSupplier.Config, ScoredText>> {

    public enum Config {
        DEFAULT(SetupHelpers.NewConfig.DEFAULT);

        public SetupHelpers.NewConfig getNewConfig() {
            return newConfig;
        }

        SetupHelpers.NewConfig newConfig;

        Config(SetupHelpers.NewConfig config) {
            this.newConfig = config;
        }
    }

    @Override
    public TestContainer<Config, ScoredText> get() {
        Function<Config, Set<Scorer<Element>>> configElementScorerSetFunction
                = config1 -> SetupHelpers.configureElementScorerSet(config1.getNewConfig());

        Function<Config, AnnotatedElement> configAnnotatedElementFunction
                = config1 -> SetupHelpers.configureAnnotatedElement(config1.getNewConfig());

        Supplier<SystemUnderTest<Config, ScoredText>> systemUnderTestSupplier = () -> {

            Function<Config, ScoringAndFlatteningNodeVisitor> scoringAndFlatteningNodeVisitorFunction
                    = config1 -> new ScoringAndFlatteningNodeVisitor(configElementScorerSetFunction.apply(config1));

            return config1 -> {
                Element rootElement = SetupHelpers.configureAnnotatedElement(config1.getNewConfig());
                ScoringAndFlatteningNodeVisitor nodeVisitor = scoringAndFlatteningNodeVisitorFunction.apply(config1);
                NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitor);
                nodeTraversor.traverse(rootElement);
                return nodeVisitor.getFlatText();
            };
        };

        Supplier<PassiveOracle<Config, ScoredText>> passiveOracleSupplier = () -> {

            Function<Config, ScoredText> configScoredTextFunction = config1 -> {

                Function<AnnotatedElement, ScoredText> annotatedElementScoredTextFunction = annotatedElement -> {

                    ScoredText scoredText = new ScoredText();

                    NodeVisitor nodeVisitor = new NodeVisitor() {

                        @Override
                        public void head(Node node, int i) {
                            if (isAnnotatedElement(node)) {
                                AnnotatedElement annotatedElement = (AnnotatedElement) node;
                                scoredText.add(
                                        new ScoredTextElement(annotatedElement.ownText(), annotatedElement.getScores())
                                );
                            }
                        }

                        @Override
                        public void tail(Node node, int i) {
                            isAnnotatedElement(node);
                        }

                        private boolean isAnnotatedElement(Node node) {
                            return node.getClass().equals(AnnotatedElement.class);
                        }
                    };

                    NodeTraversor nt = new NodeTraversor(nodeVisitor);
                    nt.traverse(annotatedElement);
                    return scoredText;
                };
                return configAnnotatedElementFunction.andThen(annotatedElementScoredTextFunction).apply(config1);
            };

            return (config1, scoredText) -> configScoredTextFunction.apply(config1).equals(scoredText);
        };

        return new TestContainer.Builder<Config, ScoredText>()
                .begin()
                .systemUnderTest(systemUnderTestSupplier.get())
                .oracle(passiveOracleSupplier.get())
                .build();

    }
}