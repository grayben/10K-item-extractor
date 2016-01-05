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

    private AnnotatedElement rootAnnotation;

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
        List<Element> elementList = null;
        switch (config) {
            case SEQUENTIAL:
                elementList = new ArrayList<>();
                elementList.addAll(getEmphasisedTargetElementsAndScores(this.getSUT()).keySet());
                elementList.addAll(getSegmentedTargetElementsAndScores(this.getSUT()).keySet());
                break;
        }
        TreeAssembler treeAssembler = new TreeAssembler(elementList, config, this.sutParams);
        rootAnnotation = treeAssembler.getRootAnnotation();
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
        nt.traverse(this.rootAnnotation);
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
                targetElements.addAll(getEmphasisedTargetElementsAndScores(this.getSUT()).keySet());
                targetElements.addAll(getSegmentedTargetElementsAndScores(this.getSUT()).keySet());
                for(Element element : targetElements){
                    element.text("some text");
                }
                break;
        }
        return targetElements;
    }


    private class TreeAssembler {

        //input fields
        private List<Element> elementsToAttach;

        private Configuration configuration;
        private Set<Scorer<Element>> elementScorers;

        //output fields
        private AnnotatedElement rootAnnotation;

        public AnnotatedElement getRootAnnotation() {
            return rootAnnotation;
        }

        //internal working fields
        private Element currentElement;
        private HashMap<String, Integer> currentIsolatedScores;

        private AnnotatedElement parentAnnotation;
        private HashMap<String, Integer> parentCumulativeScores;

        private AnnotatedElement childAnnotation;
        private HashMap<String, Integer> childCumulativeScores;



        TreeAssembler(List<Element> elementsToAttach, Configuration configuration, Set<Scorer<Element>> elementScorers){
            validateInitParams(elementsToAttach, configuration, elementScorers);
            this.elementsToAttach = elementsToAttach;
            this.configuration = configuration;
            this.elementScorers = elementScorers;
            initialiseMaps();
            //setup the first parent/child AnnotatedElement pair
            plantSeedling();
            assembleInTree();
        }

        private void validateInitParams(
                List<Element> elements,
                Configuration configuration,
                Set<Scorer<Element>> elementScorers
        ) {
            if(elements == null){
                throw new NullPointerException(
                        "The input elements list was null"
                );
            }
            if(elements.size() < 2){
                throw new IllegalArgumentException(
                        "The input element list did not have at least 2 elements"
                );
            }
            if(configuration == null){
                throw new NullPointerException(
                        "The input configuration option was null"
                );
            }
            switch (configuration){
                case SEQUENTIAL: break;
                default: throw new IllegalArgumentException(
                        "The input configuration option was not recognised"
                );
            }
            if (elementScorers == null) {
                throw new NullPointerException(
                        "The input element scorers set was null"
                );
            }
            if (elementScorers.isEmpty()){
                throw new IllegalArgumentException(
                        "The input element scorers set was empty"
                );
            }
        }

        private void initialiseMaps() {
            //make sure all the fields are instantiated, contain keys and default values
            currentIsolatedScores = new HashMap<>();
            parentCumulativeScores = new HashMap<>();
            childCumulativeScores = new HashMap<>();

            for(Scorer<Element> scorer : this.elementScorers){
                currentIsolatedScores.put(scorer.getScoreLabel(), scorer.DEFAULT_SCORE);
                parentCumulativeScores.put(scorer.getScoreLabel(), scorer.DEFAULT_SCORE);
                childCumulativeScores.put(scorer.getScoreLabel(), scorer.DEFAULT_SCORE);
            }
        }

        private void plantSeedling() {
            //parent

            currentElement = elementsToAttach.remove(0);
            currentIsolatedScores = isolatedScore(currentElement);
            parentCumulativeScores = copyScores(currentIsolatedScores);

            parentAnnotation = new AnnotatedElement(currentElement, parentCumulativeScores);

            //child
            currentElement = elementsToAttach.remove(0);
            currentIsolatedScores = isolatedScore(currentElement);
            childCumulativeScores = addScores(parentCumulativeScores, currentIsolatedScores);

            childAnnotation = new AnnotatedElement(currentElement, childCumulativeScores);
            parentAnnotation.appendChild(childAnnotation);

            //set root of hierarchy to be built
            rootAnnotation = parentAnnotation;
        }

        private AnnotatedElement assembleInTree() {

            boolean childNotSibling = false;
            while(elementsToAttach.isEmpty() == false){
                currentElement = elementsToAttach.remove(0);
                currentIsolatedScores = isolatedScore(currentElement);

                childNotSibling = !childNotSibling;
                if (childNotSibling){
                    parentCumulativeScores = childCumulativeScores;
                    childCumulativeScores = null;
                    parentAnnotation = childAnnotation;
                    childAnnotation = null;
                }
                childCumulativeScores = addScores(parentCumulativeScores, currentIsolatedScores);
                childAnnotation = new AnnotatedElement(currentElement, childCumulativeScores);

                parentAnnotation.appendChild(childAnnotation);
            }
            return rootAnnotation;
        }

        private HashMap<String, Integer> copyScores(HashMap<String, Integer> source){
            HashMap<String, Integer> newMap = (HashMap<String, Integer>)source.clone();
            return newMap;
        }

        private HashMap<String, Integer> isolatedScore(Element element) {
            HashMap<String, Integer> scores = new HashMap<>();

            for (Scorer<Element> scorer : sutParams) {
                scores.put(scorer.getScoreLabel(), scorer.score(element));
            }
            return scores;
        }

        private HashMap<String, Integer> addScores(Map<String, Integer> map1, Map<String, Integer> map2) {

            assert map1.keySet().equals(map2.keySet());

            HashMap<String, Integer> mapSum = new HashMap<>();

            for (Map.Entry<String, Integer> entry1 : map1.entrySet()) {
                String key = entry1.getKey();
                Integer score1 = entry1.getValue();
                Integer score2 = map2.get(key);
                Integer scoreSum = score1 + score2;

                mapSum.put(key, scoreSum);
            }

            return mapSum;
        }

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
}

