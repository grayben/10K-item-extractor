package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

/**
 * For a nominated test configuration: generates the test input, SUT and expected output.
 * <p/>
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeVisitorOracle {

    ////////////////////////////////////////////////////////////////////////////////////////
    // INSTANCE VARIABLES
    ////////////////////////////////////////////////////////////////////////////////////////

    private Set<Scorer<Element>> sutParams;
    private ScoringAndFlatteningNodeVisitor sut;

    private AnnotatedElementTreeAssembler.Configuration config;

    private AnnotatedElement rootAnnotation;

    private ScoredText expectedOutput;

    Random random;

    ////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////////////////////////////////

    NodeVisitorOracle(AnnotatedElementTreeAssembler.Configuration config) {
        validateInitParams(config);
        this.config = config;
        random = new Random(System.currentTimeMillis());
        generateArtifacts();
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // PACKAGE INTERFACE
    ////////////////////////////////////////////////////////////////////////////////////////
    Element getInput() {
        return this.rootAnnotation;
    }

    ScoredText getExpectedOutput() {
        return expectedOutput;
    }

    ScoringAndFlatteningNodeVisitor getSUT(){
        return this.sut;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // HIGH LEVEL
    ////////////////////////////////////////////////////////////////////////////////////////

    private void validateInitParams(AnnotatedElementTreeAssembler.Configuration config) {
        switch (config) {
            case MIXED_TREE:
                break;
            default:
                throw new IllegalArgumentException("The option was not recognised");
        }
    }

    private void generateArtifacts() {
        generateSutParams();
        generateSut();
        generateAnnotatedInput();
        determineExpectedOutput();
    }

    private void generateSut() {
        this.sut = new ScoringAndFlatteningNodeVisitor(this.sutParams);
    }

    private void generateSutParams() {
        Set<Scorer<Element>> elementScorers = null;
        switch (config){
            case MIXED_TREE:
                elementScorers = new HashSet<>();
                Scorer<Element> segmentationElementScorer = new SegmentationElementScorer(
                        new TagSegmentationScorer(TagSegmentationScorer.defaultMap())
                );
                elementScorers.add(segmentationElementScorer);
                Scorer<Element> emphasisElementScorer = new EmphasisElementScorer(
                        new TagEmphasisScorer(TagEmphasisScorer.defaultMap()),
                        new TagAndAttributeScorer(TagAndAttributeScorer.defaultMap())
                );
                elementScorers.add(emphasisElementScorer);
                break;
        }
        this.sutParams = elementScorers;
    }

    private void generateAnnotatedInput() {
        List<Element> elementList = generateElements();
        AnnotatedElementTreeAssembler annotatedElementTreeAssembler = new AnnotatedElementTreeAssembler(elementList, config, this.sutParams);
        rootAnnotation = annotatedElementTreeAssembler.getRootAnnotation();
    }

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
                            new ScoredTextElement(annotatedElement.ownText(), annotatedElement.scores)
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
}

