package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetSupplier;
import com.grayben.tools.math.function.parametric.ParametricEquation;
import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * For a nominated test configuration: generates the test input, SUT and expected output.
 * <p>
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeVisitorTestContainerConfigurer {

    public static TestContainer<Config, ScoredText> getConfiguredTestContainer(Config config){
        switch (config){

            // TestContainerSupplier<Config, ScoredText> extends Supplier<TestContainer<Config, ScoredText>>
            //      transformInput: Config -> File
            //      get: () -> TestContainer<Config, ScoredText> {
            //          //
            //          //
            //      }
            //      :TestContainer<File, ScoredText>













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
        }
        TestContainer<Config, ScoredText> testContainer = new TestContainer.Builder<>().begin()
        throw new NotImplementedException("This method is not implemented");
    }

    public enum Config {
        DEFAULT
    }

    public static class Factory {
        public static NodeVisitorTestContainerConfigurer getInstance(){
            new TestContainer.Builder().begin().systemUnderTest(effectiveSystemUnderTestFunction.apply()).oracle(e)
        }

        private static Function<Config, AnnotatedElement> inputAdapter() {
            return null;
        }

        private static ParametricEquation<AnnotatedElement, Pair<ScoringAndFlatteningNodeVisitor, File>, ScoredText> parametricEquation() {
            return null;
        }

        private static class InputAdapterSupplier implements Supplier<Function<Config, AnnotatedElement>> {

            @Override
            public Function<Config, AnnotatedElement> get() {
                Function<Config, List<Element>> elementListSupplier
                        = new ElementListSupplier(null);
                Function<Config, AnnotatedElementTreeAssembler.Configuration> configurationAdapter
                        = new ConfigurationAdapter();
                Function<Config, Set<? extends Scorer<Element>>> elementScorerSetSupplier
                        = new ElementScorerSetSupplier();

                return config -> new AnnotatedElementTreeAssembler(
                        elementListSupplier.apply(config),
                        configurationAdapter.apply(config),
                        elementScorerSetSupplier.apply(config)).getRootAnnotation();
            }


            private static class ElementListSupplier implements Function<Config, List<Element>> {

                private enum Config {
                    DEFAULT
                }

                private final Config config;

                private ElementListSupplier(Config config) {
                    this.config = config;
                }

                @Override
                public List<Element> apply(NodeVisitorTestContainerConfigurer.Config config) {
                    return null;
                }
            }

            private class ConfigurationAdapter implements Function<Config, AnnotatedElementTreeAssembler.Configuration> {

                @Override
                public AnnotatedElementTreeAssembler.Configuration apply(Config config) {
                    return null;
                }
            }

            private class ElementScorerSetSupplier implements Function<Config, Set<? extends Scorer<Element>>> {

                @Override
                public Set<? extends Scorer<Element>> apply(Config config) {
                    return null;
                }
            }
        }

        private static class ParametricEquationSupplier
                implements Supplier<ParametricEquation<AnnotatedElement, File, ScoredText>> {

            @Override
            public ParametricEquation<AnnotatedElement, File, ScoredText> get() {
                return null;
            }
        }
    }

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

    private void instantiateGenerators() {
        Set<ElementScorerSetSupplier.Content> contents = new HashSet<>();
        contents.add(ElementScorerSetSupplier.Content.SEGMENTATION_ELEMENT_SCORER);
        contents.add(ElementScorerSetSupplier.Content.EMPHASIS_ELEMENT_SCORER);
        this.elementScorerSetProducer = new ElementScorerSetSupplier(contents);
    }

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

    private void determineExpectedOutput() {
        class OracleNodeVisitor implements NodeVisitor {

            ScoredText scoredText;

            ScoredText getScoredText() {
                return scoredText;
            }

            OracleNodeVisitor() {
                this.scoredText = new ScoredText();
            }

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
                if (node.getClass().equals(AnnotatedElement.class)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        OracleNodeVisitor nv = new OracleNodeVisitor();
        NodeTraversor nt = new NodeTraversor(nv);
        nt.traverse(this.rootAnnotation);
        this.expectedOutput = nv.getScoredText();
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // ENCAPSULATED HELPERS
    ////////////////////////////////////////////////////////////////////////////////////////

    /*

    private List<Element> generateElements() {
        List<Element> targetElements = null;
        switch (this.config){
            case MIXED_TREE:
                targetElements = new ArrayList<>();
                targetElements.addAll(getEmphasisedTargetElementsAndScores(this.getSUT()).keySet());
                targetElements.addAll(getSegmentedTargetElementsAndScores(this.getSUT()).keySet());
                targetElements.addAll(generateAndScoreRandomElements(100).keySet());
                for(Element element : targetElements){
                    element.text(randomString());
                }
                break;
        }
        Collections.shuffle(targetElements, random);
        return targetElements;
    }

    private String randomString(){
        int number = random.nextInt();
        return Integer.toString(number);
    }


    static Map<Element, Integer>
    getSegmentedTargetElementsAndScores(ScoringAndFlatteningNodeVisitor nodeVisitor){
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Map<Element, Integer> targetMap = null;

        Iterator<Scorer<Element>> it
                = nodeVisitor.getElementScorers().iterator();

        while (targetMap == null && it.hasNext()) {
            Scorer<Element> nextScorer = it.next();
            if (nextScorer.getScoreLabel().equals(scoreLabel)) {
                targetMap = new HashMap<>();
                Map<Tag, Integer> tagScoresMap =
                        ((TagSegmentationScorer)
                                (
                                        (SegmentationElementScorer) nextScorer
                                ).getTagScorer()
                        ).getScoresMap();
                for (Map.Entry<Tag, Integer> entry :
                        tagScoresMap.entrySet()) {
                    targetMap.put(
                            new Element(
                                    entry.getKey(), "some string"
                            ),
                            entry.getValue()
                    );
                }
            }
        }

        if (targetMap == null)
            throw new IllegalArgumentException("Couldn't find any segmented elements");

        return targetMap;
    }

    static Map<Element, Integer>
    getEmphasisedTargetElementsAndScores(ScoringAndFlatteningNodeVisitor nodeVisitor){
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Map<Element, Integer> targetMap = null;

        Iterator<Scorer<Element>> it
                = nodeVisitor.getElementScorers().iterator();

        while (targetMap == null && it.hasNext()) {
            Scorer<Element> nextScorer = it.next();
            if (nextScorer.getScoreLabel().equals(scoreLabel)) {
                targetMap = new HashMap<>();

                //tag only emphasis
                Map<Tag, Integer> tagScoresMap
                        = ((EmphasisElementScorer) nextScorer)
                        .getTagEmphasisScorer()
                        .getScoresMap();
                for (Map.Entry<Tag, Integer> entry :
                        tagScoresMap.entrySet()) {
                    targetMap.put(
                            new Element(
                                    entry.getKey(),
                                    "some string"
                            ),
                            entry.getValue()
                    );
                }
                tagScoresMap = null;

                //tag and attribute emphasis
                Map<TagAndAttribute, Integer> tagAndAttributeScoresMap
                        =((EmphasisElementScorer)nextScorer)
                        .getTagAndAttributeScorer()
                        .getScoresMap();
                for(Map.Entry<TagAndAttribute, Integer> entry : tagAndAttributeScoresMap.entrySet()){
                    Attributes attributes = new Attributes();
                    attributes.put(entry.getKey().getAttribute());
                    targetMap.put(
                            new Element(entry.getKey().getTag(), "some string", attributes),
                            entry.getValue()
                    );
                }
            }
        }
        if (targetMap == null)
            throw new IllegalArgumentException("Couldn't find any emphasised elements");
        return targetMap;
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

    */
}

