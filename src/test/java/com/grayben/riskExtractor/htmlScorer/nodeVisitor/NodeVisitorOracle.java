package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
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

    enum Configuration {
        SEQUENTIAL
    }
    private Configuration config;

    ScoringAndFlatteningNodeVisitor getSUT(Set<Scorer<Element>> elementScorers){
        return new ScoringAndFlatteningNodeVisitor(elementScorers);
    }

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
    Element getInput(){
        return this.rootElement;
    }

    ScoredText getExpectedOutput() {
        return expectedOutput;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // HIGH LEVEL
    ////////////////////////////////////////////////////////////////////////////////////////

    private void validateInitParams(Configuration config){
        switch(config) {
            case SEQUENTIAL:
                break;
            default:
                throw new IllegalArgumentException("The option was not recognised");
        }
    }

    private void generateArtifacts() {
        generateSutParams();
        generateAnnotatedInput();
        determineExpectedOutput();
    }

    private void generateSutParams() {
        Set<Scorer<Element>> elementScorers = new HashSet<>();
        Scorer<Element> segmentationElementScorer =  new SegmentationElementScorer(
                new TagSegmentationScorer(
                        TagSegmentationScorer.defaultMap()
                )
        );
    }

    private void generateAnnotatedInput() {
        Element element = new Element(Tag.valueOf("foobar"), "aBaseURI");
        assembleInTree(element, generateElements());
    }

    private void determineExpectedOutput(){
        class OracleNodeVisitor implements NodeVisitor {

            ScoredText scoredText;

            ScoredText getScoredText() {
                return scoredText;
            }

            OracleNodeVisitor(){
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

            private void validateNode(Node node){
                if(node.getClass().equals(AnnotatedElement.class) == false){
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

        public AnnotatedElement(Element element, Map<String, Integer> scores){
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

    private List<Element> generateElements(){
        //TODO: implement this method
        return null;
    }



    private Map<String, Integer> score(Element element) {
        Map<String, Integer> scores = new HashMap<>();

        for (Scorer<Element> scorer : sutParams) {
            scores.put(scorer.getScoreLabel(), scorer.score(element));
        }
        return scores;
    }

    private void incrementScores(Map<String, Integer> destination, Map<String, Integer> source){
        for(Map.Entry<String, Integer> scoreEntry : source.entrySet()){
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

    private void assembleInTree(
            Element startElement,
            Iterable<Element> elementsToAttach) {
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
}