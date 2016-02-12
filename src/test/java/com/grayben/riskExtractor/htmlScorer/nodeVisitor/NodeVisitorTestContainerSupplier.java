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
import org.jsoup.parser.Tag;
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

            switch (config1){
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
                switch (config11){
                    case DEFAULT:
                        return AnnotatedElementTreeAssembler.Configuration.MIXED_TREE;
                    default:
                        throw new IllegalArgumentException("The input option was not recognised");
                }
            };

            BiFunction<Config, Set<Scorer<Element>>, List<Element>> configElementListFunction = (config11, scorers) -> {
                List<Element> targetElements = null;
                switch (configConfigurationFunction.apply(config11)){
                    case MIXED_TREE:
                        targetElements = new ArrayList<>();
                        targetElements.addAll(UtilsThatShouldBeRefactored.getEmphasisedTargetElementsAndScores(scorers).keySet());
                        targetElements.addAll(UtilsThatShouldBeRefactored.getSegmentedTargetElementsAndScores(scorers).keySet());
                        targetElements.addAll(generateAndScoreRandomElements(100).keySet());
                        for(Element element : targetElements){
                            element.text(randomString());
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("The input option was not recognised");
                }
                Collections.shuffle(targetElements, random);
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
                            if(isAnnotatedElement(node)) {
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

    // TestContainerSupplier<Config, ScoredText> extends Supplier<TestContainer<Config, ScoredText>>
    //      -
    //      - transformToUnderlyingInput: Config -> Element {}
    //      - buildUnderlyingSUT: Element -> ScoredText {}
    //      - buildSUT: () -> SUT<Config, ScoredText> {
    //      |   return () -> transformToUnderlyingInput.andThen(buildUnderlyingSUT);
    //      }
    //      - buildPassiveOracle: () -> PassiveOracle<Config, ScoredText> {
    //      |   Element element = transformToUnderlyingInput.apply(config);
    //      |   ScoredText scoredText =
    //      |   return () -> {
    //          |   underlyingPassiveOracle.test(
    //      + get: () -> TestContainer<Config, ScoredText> {
    //          return new TestContainer<>.Builder().build().sut(
    //
    //      }
    //      :TestContainer<Element, ScoredText>

    // determine adapters
        // determineScorers: Config -> Set<Scorer<Element>>
        // configToSeed: Config -> Tree<AnnotatedElement>
            // determineScorers.apply(config) -> Set<Scorer<Element>>
            // determineElements: Config -> List<Element>
            // adaptConfiguration: Config -> AnnotatedElementTreeAssembler.Config
            // AnnotatedElementTreeAssembler.getRootAnnotation:
                // List<Element>
                // * Set<Scorer<Element>>
                // * AnnotatedElementTreeAssembler.Config
                //      -> Tree<AnnotatedElement>
        // annotationTreeToElementTree: Tree<AnnotatedElement> -> Tree<Element>
        // annotationTreeToFile

    // input generator: Tree<AnnotatedElement> -> File
        // annotationTreeToElementTree.apply(annotationTree) -> Tree<Element>
        // writeToFile: Tree<Element> -> File

    // determine SUT: SystemUnderTest<Config, ScoredText>
        //
        // configToSeed.apply(config) -> Tree<AnnotatedElement>
        // setup nv: Set<Scorer<Element>> -> ScoringAndFlatteningNodeVisitor
        // setup nt: ScoringAndFlatteningNodeVisitor -> NodeTraversor
        // apply nt to input: Tree<Element> * ScoringAndFlatteningNodeVisitor -> ScoredText
    // determine PassiveOracle<Config, ScoredText>
        // configToSeed.apply(config) -> Tree<AnnotatedElement>
            // determine INPUT: Tree<AnnotatedElement> -> File

            // determine EXPECTED OUTPUT: Tree<AnnotatedElement> -> ScoredText


    ////////////////////////////////////////////////////////////////////////////////////////
    // NEW SCAFFOLDING  /\
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    // OLD CRAP         \/
    ////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////
    // INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////

    private Set<Scorer<Element>> sutParams;
    private ScoringAndFlatteningNodeVisitor sut;

    private ElementScorerSetSupplier elementScorerSetProducer;

    private AnnotatedElementTreeAssembler.Configuration config;

    private AnnotatedElement rootAnnotation;

    private ScoredText expectedOutput;

    Random random;



    ////////////////////////////////////////////////////////////////////////////////////////
    /// HIGH LEVEL
    ////////////////////////////////////////////////////////////////////////////////////////

    /*

    private void generateArtifacts() {
        instantiateGenerators();
        generateSutParams();
        generateSut();
        generateAnnotatedInput();
        determineExpectedOutput();
    }

    */



    private void generateSut() {
        this.sut = new ScoringAndFlatteningNodeVisitor(this.sutParams);
    }

    private void generateSutParams() {
        this.sutParams = elementScorerSetProducer.get();
    }

    /*

    private void generateAnnotatedInput() {
        List<Element> elementList = generateElements();
        AnnotatedElementTreeAssembler annotatedElementTreeAssembler = new AnnotatedElementTreeAssembler(elementList, config, this.sutParams);
        rootAnnotation = annotatedElementTreeAssembler.getRootAnnotation();
    }

    */


    ////////////////////////////////////////////////////////////////////////////////////////
    // ENCAPSULATED HELPERS
    ////////////////////////////////////////////////////////////////////////////////////////



    private String randomString(){
        int number = random.nextInt();
        return Integer.toString(number);
    }


    private Map<Element, Map<String, Integer>> generateAndScoreRandomElements(int numberToGenerate) {
        Map<Element, Map<String, Integer>> result = new HashMap<>();
        while(numberToGenerate-- > 0){
            Element element = new Element(Tag.valueOf(randomString()), randomString());
            Map<String, Integer> scores = new HashMap<>();
            for(Scorer<Element> scorer : sutParams){
                scores.put(scorer.getScoreLabel(), scorer.score(element));
            }
            result.put(element, scores);
        }
        return result;
    }
}

