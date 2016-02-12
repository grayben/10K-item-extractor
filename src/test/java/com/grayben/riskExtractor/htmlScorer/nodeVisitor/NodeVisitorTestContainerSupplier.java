package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetSupplier;
import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * For a nominated test configuration: generates the test input, SUT and expected output.
 * <p>
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeVisitorTestContainerSupplier implements Supplier<TestContainer<NodeVisitorTestContainerSupplier.Config, ScoredText>> {

    public enum Config {
        DEFAULT
    }

    @Override
    public TestContainer<Config, ScoredText> get() {
        Function<Config, Set<Scorer<Element>>> configElementScorerSetFunction = config1 -> {

            Set<ElementScorerSetSupplier.Content> contents = new HashSet<>();

            switch (config1) {
                case DEFAULT:
                    contents.add(ElementScorerSetSupplier.Content.SEGMENTATION_ELEMENT_SCORER);
                    contents.add(ElementScorerSetSupplier.Content.EMPHASIS_ELEMENT_SCORER);
                    return new ElementScorerSetSupplier(contents).get();
                default:
                    throw new IllegalArgumentException("The input option was not recognised");
            }
        };

        Function<Config, AnnotatedElement> configAnnotatedElementFunction = config1 -> {

            Function<Config, AnnotatedElementTreeAssembler.Configuration> configConfigurationFunction = config11 -> {
                switch (config11) {
                    case DEFAULT:
                        return AnnotatedElementTreeAssembler.Configuration.MIXED_TREE;
                    default:
                        throw new IllegalArgumentException("The input option was not recognised");
                }
            };

            BiFunction<Config, Set<Scorer<Element>>, List<Element>> configElementListFunction = (config11, scorers) -> {
                List<Element> targetElements = null;
                switch (configConfigurationFunction.apply(config11)) {
                    case MIXED_TREE:
                        targetElements = new ArrayList<>();
                        targetElements.addAll(UtilsThatShouldBeRefactored.getEmphasisedTargetElementsAndScores(scorers).keySet());
                        targetElements.addAll(UtilsThatShouldBeRefactored.getSegmentedTargetElementsAndScores(scorers).keySet());
                        targetElements.addAll(UtilsThatShouldBeRefactored.generateAndScoreRandomElements(scorers, 100).keySet());
                        for (Element element : targetElements) {
                            element.text(UtilsThatShouldBeRefactored.randomString());
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("The input option was not recognised");
                }
                Collections.shuffle(targetElements, new Random());
                return targetElements;
            };

            Set<Scorer<Element>> elementScorers = configElementScorerSetFunction.apply(config1);

            return new AnnotatedElementTreeAssembler(
                    configElementListFunction.apply(config1, elementScorers),
                    configConfigurationFunction.apply(config1),
                    elementScorers
            ).getRootAnnotation();
        };

        Supplier<SystemUnderTest<Config, ScoredText>> systemUnderTestSupplier = () -> {

            Function<Config, ScoringAndFlatteningNodeVisitor> scoringAndFlatteningNodeVisitorFunction
                    = config1 -> new ScoringAndFlatteningNodeVisitor(configElementScorerSetFunction.apply(config1));

            Function<AnnotatedElement, Element> annotatedElementToElementFunction = annotatedElement -> annotatedElement;

            return config1 -> {
                Element rootElement = configAnnotatedElementFunction.andThen(annotatedElementToElementFunction).apply(config1);
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