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

    enum Configuration {
        SEQUENTIAL
    }

    private Configuration config;

    private AnnotatedElement rootElement;

    private ScoredText expectedOutput;

    ////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////////////////////////////////

    NodeVisitorOracle(Configuration config) {
        validateInitParams(config);
        this.config = config;
        generateArtifacts();
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // PACKAGE INTERFACE
    ////////////////////////////////////////////////////////////////////////////////////////
    Element getInput() {
        return this.rootElement;
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

    private void validateInitParams(Configuration config) {
        switch (config) {
            case SEQUENTIAL:
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
            case SEQUENTIAL:
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
        Element element = new Element(Tag.valueOf("foobar"), "aBaseURI");
        assembleInTree(element, generateElements());
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
                validateNode(node);
                AnnotatedElement annotatedElement = (AnnotatedElement) node;
                scoredText.add(
                        new ScoredTextElement(annotatedElement.ownText(), annotatedElement.scores)
                );
            }

            @Override
            public void tail(Node node, int i) {
                validateNode(node);
            }

            private void validateNode(Node node) {
                if (node.getClass().equals(AnnotatedElement.class) == false) {
                    throw new IllegalArgumentException(
                            "Tried to pass in a Node which was not an AnnotatedElement"
                    );
                }
            }
        }
        OracleNodeVisitor nv = new OracleNodeVisitor();
        NodeTraversor nt = new NodeTraversor(nv);
        nt.traverse(this.rootElement);
        this.expectedOutput = nv.getScoredText();
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // ENCAPSULATED HELPERS
    ////////////////////////////////////////////////////////////////////////////////////////

    private class AnnotatedElement extends Element {

        Map<String, Integer> scores;

        public AnnotatedElement(Element element, Map<String, Integer> scores) {
            this(element.tag(), element.baseUri(), element.attributes(), scores);
        }

        public AnnotatedElement(Tag tag, String baseUri, Attributes attributes, Map<String, Integer> scores) {
            super(tag, baseUri, attributes);
            this.scores = scores;
        }

        public AnnotatedElement(Tag tag, String baseUri, Map<String, Integer> scores) {
            super(tag, baseUri);
            this.scores = scores;
        }
    }

    private List<Element> generateElements() {
        List<Element> targetElements = null;
        switch (this.config){
            case SEQUENTIAL:
                targetElements = new ArrayList<>();
                targetElements.addAll(getEmphasisedTargetElements(this.getSUT()).keySet());
                targetElements.addAll(getSegmentedTargetElements(this.getSUT()).keySet());
                break;
        }
        return targetElements;
    }


    private Map<String, Integer> score(Element element) {
        Map<String, Integer> scores = new HashMap<>();

        for (Scorer<Element> scorer : sutParams) {
            scores.put(scorer.getScoreLabel(), scorer.score(element));
        }
        return scores;
    }

    private void incrementScores(Map<String, Integer> destination, Map<String, Integer> source) {
        for (Map.Entry<String, Integer> scoreEntry : source.entrySet()) {
            String key = scoreEntry.getKey();
            Integer oldValue;
            if (destination.containsKey(key)) oldValue = destination.get(key);

                //TODO: refactor so that value can be retrieved from a registry mapping scorer to scoreLabel s
            else oldValue = 0;
            Integer incoming = scoreEntry.getValue();
            Integer newValue = oldValue + incoming;
            destination.put(key, newValue);
        }
    }

    private void assembleInTree(Element startElement, Iterable<Element> elementsToAttach) {
        Map<String, Integer> parentScores = new HashMap<>();
        Map<String, Integer> currentScores = new HashMap<>();
        boolean afterNotChild = false;

        AnnotatedElement currentElement = new AnnotatedElement(startElement, score(startElement));

        for (Element element : elementsToAttach) {
            afterNotChild = !afterNotChild;

            if (afterNotChild) {
                //since we're moving to a sibling, need to know the parent's scores
                currentScores = parentScores;
            } else {
                parentScores = currentScores;
                currentScores = parentScores;
            }
            incrementScores(currentScores, score(element));
            currentElement = new AnnotatedElement(element, currentScores);
        }
        this.rootElement = currentElement;
    }

    static Map<Element, Integer>
    getSegmentedTargetElements(ScoringAndFlatteningNodeVisitor nodeVisitor){
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
    getEmphasisedTargetElements(ScoringAndFlatteningNodeVisitor nodeVisitor){
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
}

