package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.NodeTraversor;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScoringAndFlatteningNodeVisitorTest
        extends NodeVisitorTest {

    private ScoringAndFlatteningNodeVisitor nodeVisitorSUT;

    @Before
    @Override
    public void setUp() throws Exception {
        Set<Scorer<Element>> elementScorers
                = new HashSet<>();

        TagEmphasisScorer tagEmphasisScorer
                = new TagEmphasisScorer(TagEmphasisScorer.defaultMap());
        TagAndAttributeScorer tagAndAttributeScorer
                = new TagAndAttributeScorer(TagAndAttributeScorer.defaultMap());
        EmphasisElementScorer emphasisElementScorer
                = new EmphasisElementScorer(
                        tagEmphasisScorer,
                        tagAndAttributeScorer
                );
        elementScorers.add(emphasisElementScorer);

        TagSegmentationScorer tagSegmentationScorer
                = new TagSegmentationScorer(TagSegmentationScorer.defaultMap());
        SegmentationElementScorer segmentationElementScorer
                = new SegmentationElementScorer(tagSegmentationScorer);
        elementScorers.add(
                segmentationElementScorer
        );

        this.nodeVisitorSUT
                = new ScoringAndFlatteningNodeVisitor(elementScorers);
        super.setNodeVisitorSUT(nodeVisitorSUT);
        super.setUp();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }



    ///////////////////////////////////////////////////////////////////////////
    // Constructor tests //////////////////////////////////////////////////////
    @Test
    public void
    test_InitThrowsNullPointerException_WhenElementScorersIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);

        Set<Scorer<Element>> elementScorers = null;

        this.nodeVisitorSUT = new ScoringAndFlatteningNodeVisitor(elementScorers);
    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenAnyElementScorerIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);

        Set<Scorer<Element>> elementScorers = new HashSet<>();
        elementScorers.add(
                new SegmentationElementScorer(
                        new TagSegmentationScorer(
                                TagSegmentationScorer.defaultMap()
                        )
                )
        );
        elementScorers.add(null);

        this.nodeVisitorSUT = new ScoringAndFlatteningNodeVisitor(elementScorers);
    }

    @Test
    public void
    test_GetScoredTextReturnsNonNull_ImmediatelyAfterSUTIsInitialised
            () throws Exception {
        Object returned = nodeVisitorSUT.getFlatText();

        assertNotNull(returned);
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_ImmediatelyAfterSUTIsInitialised
            () throws Exception {
        ScoredText scoredText = nodeVisitorSUT.getFlatText();

        assertTrue(scoredText.toString().isEmpty());
    }



    ///////////////////////////////////////////////////////////////////////////
    // Method tests (without NodeTraversor) ///////////////////////////////////
    @Test
    public void
    test_EmphasisScoreIsZero_ImmediatelyAfterInit
            () throws Exception {
        Integer expected = 0;

        Integer returned
                = nodeVisitorSUT.getCurrentScores()
                .get(EmphasisElementScorer.SCORE_LABEL);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_EmphasisScoreIsZero_AfterHeadOnNonEmphasisElement
            () throws Exception {
        Integer expected = 0;

        Element notEmphasised = new Element(Tag.valueOf("foo"), "a-base-uri");

        nodeVisitorSUT.head(notEmphasised, 1);

        Integer returned
                = nodeVisitorSUT.getCurrentScores()
                .get(EmphasisElementScorer.SCORE_LABEL);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_EmphasisScoreIsZero_AfterTailOnNonEmphasisElement
            () throws Exception {
        Integer expected = 0;

        Element notEmphasised = new Element(Tag.valueOf("foo"), "a-base-uri");

        nodeVisitorSUT.tail(notEmphasised, 1);

        Integer returned
                = nodeVisitorSUT.getCurrentScores()
                .get(EmphasisElementScorer.SCORE_LABEL);

        assertEquals(expected, returned);
    }

    private Map<Element, Integer>
    getSegmentedTargetElements(
            ScoringAndFlatteningNodeVisitor nodeVisitor
    ) throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Map<Element, Integer> targetMap = null;

        Iterator<Scorer<Element>> it
                = nodeVisitor.getElementScorers().iterator();

        while(targetMap == null && it.hasNext()){
            Scorer<Element> nextScorer = it.next();
            if(nextScorer.getScoreLabel().equals(scoreLabel)){
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
            throw new Exception("Couldn't find any segmented elements");

        return targetMap;
    }

    private Map<Element, Integer>
    getEmphasisedTargetElements(
            ScoringAndFlatteningNodeVisitor nodeVisitor
    ) throws Exception {
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Map<Element, Integer> targetMap = null;

        Iterator<Scorer<Element>> it
                = nodeVisitor.getElementScorers().iterator();

        while(targetMap == null && it.hasNext()){
            Scorer<Element> nextScorer = it.next();
            if(nextScorer.getScoreLabel().equals(scoreLabel)){
                targetMap = new HashMap<>();
                Map<Tag, Integer> tagScoresMap
                        = ((EmphasisElementScorer)nextScorer)
                        .getTagEmphasisScorer()
                        .getScoresMap();
                for (Map.Entry<Tag, Integer> entry:
                        tagScoresMap.entrySet()) {
                    targetMap.put(
                            new Element(
                                    entry.getKey(),
                                    "some string"
                            ),
                            entry.getValue()
                    );
                }
            }
        }
        if (targetMap == null)
            throw new Exception("Couldn't find any emphasised elements");
        return targetMap;
    }

    @Test
    public void
    test_EmphasisScoreIsGreaterThanZero_AfterHeadOnEmphasisElement
            () throws Exception {
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it = getEmphasisedTargetElements(nodeVisitorSUT)
                .entrySet().iterator();
        Map.Entry<Element, Integer> emphasisedElementAndScore
                = it.next();
        Element emphasisedElement = emphasisedElementAndScore.getKey();
        Integer expected = emphasisedElementAndScore.getValue();

        nodeVisitorSUT.head(emphasisedElement, 1);

        Integer returned = nodeVisitorSUT.getCurrentScores().get(scoreLabel);

        assert expected > 0;

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_EmphasisScoreIsGreaterThanZero_AfterHeadOnEmphasisElementThenNonEmphasisElement
            () throws Exception {
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it = getEmphasisedTargetElements(nodeVisitorSUT)
                .entrySet().iterator();
        Map.Entry<Element, Integer> emphasisedElementAndScore
                = it.next();
        Element emphasisedElement = emphasisedElementAndScore.getKey();
        Integer expected = emphasisedElementAndScore.getValue();

        Element notEmphasisedElement
                = new Element(Tag.valueOf("foo-bar"), "some-string");

        nodeVisitorSUT.head(emphasisedElement, 1);
        nodeVisitorSUT.head(notEmphasisedElement, 2);

        Integer returned = nodeVisitorSUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_EmphasisScoreIsZero_AfterHeadAndTailOnEmphasisElement
            () throws Exception {
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Integer expected = 0;

        Iterator<Map.Entry<Element, Integer>> it = getEmphasisedTargetElements(nodeVisitorSUT)
                .entrySet().iterator();
        Map.Entry<Element, Integer> emphasisedElementAndScore
                = it.next();
        Element emphasisedElement = emphasisedElementAndScore.getKey();

        nodeVisitorSUT.head(emphasisedElement, 1);
        nodeVisitorSUT.tail(emphasisedElement, 1);

        Integer returned = nodeVisitorSUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_EmphasisScoreIsGreaterThanZero_AfterHeadOn2EmphasisElementsAndTailOnLatterElement
            () throws Exception {
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it = getEmphasisedTargetElements(nodeVisitorSUT)
                .entrySet().iterator();
        Map.Entry<Element, Integer> emphasisedElementAndScore;

        emphasisedElementAndScore = it.next();
        Element emphasisedElement1 = emphasisedElementAndScore.getKey();
        Integer expected = emphasisedElementAndScore.getValue();

        Element emphasisedElement2
                = it.next().getKey();

        nodeVisitorSUT.head(emphasisedElement1, 1);
        nodeVisitorSUT.head(emphasisedElement2, 2);
        nodeVisitorSUT.tail(emphasisedElement2, 1);

        Integer returned = nodeVisitorSUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_ScoredTextContainsTextWithEmphasisScoreEqualToZero_AfterHeadAndTailOnNonEmphasisElementWithText
            () throws Exception {
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        helper_ScoredTextContainsTextWithXScoreEqualToZero_AfterHeadAndTailOnNonXElement(scoreLabel);
    }

    @Test
    public void
    test_ScoredTextContainsTextWithEmphasisScoreGreaterThanZero_AfterHeadAndTailOnEmphasisElementWithText
            () throws Exception {
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it = getEmphasisedTargetElements(nodeVisitorSUT)
                .entrySet().iterator();
        Map.Entry<Element, Integer> emphasisedElementAndScore;

        emphasisedElementAndScore = it.next();
        Element emphasisedElement = emphasisedElementAndScore.getKey();
        Integer expectedScore = emphasisedElementAndScore.getValue();

        String elementText = "This is some text contained by the element.";
        emphasisedElement.text(elementText);

        nodeVisitorSUT.head(emphasisedElement, 1);
        nodeVisitorSUT.tail(emphasisedElement, 1);

        ScoredTextElement scoredTextElement
                = nodeVisitorSUT.getFlatText().getList().iterator().next();
        Integer returnedScore = scoredTextElement.getScores().get(scoreLabel);

        assertEquals(expectedScore, returnedScore);
    }

    @Test
    public void
    test_SegmentationScoreIsZero_ImmediatelyAfterInit
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Integer expected = 0;

        Integer returned
                = nodeVisitorSUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_SegmentationScoreIsZero_AfterHeadOnNonSegmentationElement
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Integer expected = 0;

        Element nonSegElem = new Element(
                Tag.valueOf("foobar"),
                "some string"
        );

        nodeVisitorSUT.head(nonSegElem, 1);

        Integer returned = nodeVisitorSUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_SegmentationScoreIsZero_AfterTailOnNonSegmentationElement
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Integer expected = 0;

        Element nonSegElem = new Element(
                Tag.valueOf("foobar"),
                "some string"
        );

        nodeVisitorSUT.tail(nonSegElem, 1);

        Integer returned = nodeVisitorSUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_SegmentationScoreIsGreaterThanZero_AfterHeadOnSegmentationElement
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it
                = getSegmentedTargetElements(nodeVisitorSUT)
                .entrySet().iterator();
        Map.Entry<Element, Integer> segmentedElementAndScore
                = it.next();
        Element segmentedElement = segmentedElementAndScore.getKey();
        Integer expected = segmentedElementAndScore.getValue();
        assert expected > 0;

        nodeVisitorSUT.head(segmentedElement, 1);

        Integer returned = nodeVisitorSUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_SegmentationScoreIsGreaterThanZero_AfterHeadOnSegmentationElementThenNonSegmentationElement
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it
                = getSegmentedTargetElements(nodeVisitorSUT)
                .entrySet().iterator();
        Map.Entry<Element, Integer> segmentedElementAndScore
                = it.next();
        Element segmentedElement = segmentedElementAndScore.getKey();
        Integer expected = segmentedElementAndScore.getValue();
        assert expected > 0;

        Element nonSegmentedElement
                = new Element(
                Tag.valueOf("foobar"),
                "some string"
        );

        nodeVisitorSUT.head(segmentedElement, 1);
        nodeVisitorSUT.head(nonSegmentedElement, 1);

        Integer returned = nodeVisitorSUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_SegmentationScoreIsZero_AfterHeadAndTailOnSegmentationElement
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Integer expected = 0;

        Element segmentedElement
                = getSegmentedTargetElements(nodeVisitorSUT)
                .entrySet().iterator().next().getKey();

        nodeVisitorSUT.head(segmentedElement, 1);
        nodeVisitorSUT.tail(segmentedElement, 1);

        Integer returned = nodeVisitorSUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_SegmentationScoreIsGreaterThanZero_AfterHeadOn2SegmentationElementsAndTailOnLatterElement
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it
                = getSegmentedTargetElements(nodeVisitorSUT)
                .entrySet().iterator();
        Map.Entry<Element, Integer> segmentedElementAndScore
                = it.next();
        Element segmentedElement1 = segmentedElementAndScore.getKey();
        Integer expected = segmentedElementAndScore.getValue();
        assert expected > 0;

        Element segmentedElement2 = it.next().getKey();

        nodeVisitorSUT.head(segmentedElement1, 1);
        nodeVisitorSUT.head(segmentedElement2, 1);
        nodeVisitorSUT.tail(segmentedElement2, 1);

        Integer returned = nodeVisitorSUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    private void helper_ScoreElementAndExpectScore
            (Element element, String scoreLabel, Integer expectedScore){
        nodeVisitorSUT.head(element, 1);
        nodeVisitorSUT.tail(element, 1);

        ScoredTextElement scoredTextElement
                = nodeVisitorSUT.getFlatText().getList().iterator().next();

        Integer actualScore = scoredTextElement.getScores().get(scoreLabel);

        assertEquals(expectedScore, actualScore);
    }

    private void
    helper_ScoredTextContainsTextWithXScoreEqualToZero_AfterHeadAndTailOnNonXElement
            (String scoreLabel) throws Exception {
        Element element = new Element(Tag.valueOf("foobar"), "some string");
        String elementText = "This is some text contained by the element.";
        element.text(elementText);

        Integer expectedScore = 0;

        helper_ScoreElementAndExpectScore(element, scoreLabel, expectedScore);
    }

    @Test
    public void
    test_ScoredTextContainsTextWithSegmentationScoreEqualToZero_AfterHeadAndTailOnNonSegmentationElementWithText
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        helper_ScoredTextContainsTextWithXScoreEqualToZero_AfterHeadAndTailOnNonXElement(scoreLabel);
    }

    @Test
    public void
    test_ScoredTextContainsTextWithSegmentationScoreGreaterThanZero_AfterHeadAndTailOnSegmentationElementWithText
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it = getSegmentedTargetElements(nodeVisitorSUT)
                .entrySet().iterator();
        Map.Entry<Element, Integer> segmentedElementAndScore;

        segmentedElementAndScore = it.next();
        Element segmentedElement = segmentedElementAndScore.getKey();
        Integer expectedScore = segmentedElementAndScore.getValue();

        String elementText = "This is some text contained by the element.";
        segmentedElement.text(elementText);

        nodeVisitorSUT.head(segmentedElement, 1);
        nodeVisitorSUT.tail(segmentedElement, 1);

        ScoredTextElement scoredTextElement
                = nodeVisitorSUT.getFlatText().getList().iterator().next();
        Integer returnedScore = scoredTextElement.getScores().get(scoreLabel);

        assertEquals(expectedScore, returnedScore);
    }


    
    ///////////////////////////////////////////////////////////////////////////
    // Method tests (with NodeTraversor) //////////////////////////////////////
    @Test
    public void
    test_GetScoredTextReturnsEmpty_AfterSingleVisitToNonElementNode
            () throws Exception {
        Node nonElementNode = new Comment(
                "my-comment",
                "http://www.istonyabbottstillpm.com");

        NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitorSUT);

        nodeTraversor.traverse(nonElementNode);

        ScoredText scoredText = nodeVisitorSUT.getFlatText();

        assertTrue(scoredText.toString().isEmpty());
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_AfterSingleVisitToElementWithNoText
            () throws Exception {

        Tag tag = Tag.valueOf("a-tag-name");

        Node elementNode = new Element(tag, "http://www.baseURI.com");

        NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitorSUT);
        nodeTraversor.traverse(elementNode);

        ScoredText scoredText = nodeVisitorSUT.getFlatText();

        assertTrue(scoredText.toString().isEmpty());
    }

    @Test
    public void
    test_GetScoredTextReturnsNotEmpty_AfterSingleVisitToElementNodeWithText
            () throws Exception {
        Tag tag = Tag.valueOf("a-tag-name");

        Element element = new Element(tag, "a-base-URI");

        element.text("Some text is here.");

        NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitorSUT);
        nodeTraversor.traverse(element);

        ScoredText scoredText = nodeVisitorSUT.getFlatText();

        assertFalse(scoredText.getList().isEmpty());
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedText_AfterSingleVisitToElementWithText
            () throws Exception {
        String expectedText = "This is the text we expect to see present in the list.";
        Tag tag = Tag.valueOf("a-tag-name");

        Element element = new Element(tag, "a-base-uri");

        element.text(expectedText);

        NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitorSUT);
        nodeTraversor.traverse(element);

        String output = nodeVisitorSUT.getFlatText().toString();

        assertEquals(expectedText, output);
    }

    @Ignore
    @Test
    public void
    test_GetScoredTextReturnsExpectedText_AfterVisitsToManyElementsWithText
            () throws Exception {
        fail("Test not implemented");
    }

    @Ignore
    @Test
    public void
    test_GetScoredTextReturnsExpectedScores_AfterVisitsToManyElementsWithText
            () throws Exception {
        fail("Test not implemented");
    }

    private Element attachElements(Element currentElement, Iterable<Element> elementsToAttach){

        boolean afterNotChild = false;

        for (Element element :
                elementsToAttach) {
            afterNotChild = ! afterNotChild;
            if(afterNotChild){
                currentElement.after(element);
            } else {
                currentElement.appendChild(element);
            }
            currentElement = element;
        }
        return currentElement;
    }

    @Test
    public void
    test_AllScoresAreZero_AfterTraversalOfManyTargetElements
            () throws Exception {
        
        //fail("Test not implemented");

        Map<Element, Integer> segmentationTargets
                = getSegmentedTargetElements(nodeVisitorSUT);
        Map<Element, Integer> emphasisTargets
                = getEmphasisedTargetElements(nodeVisitorSUT);
        
        Element headElement = new Element(Tag.valueOf("foobar"), "a base URI");
        Element currentElement = new Element(Tag.valueOf("bazaar"), "something else");
        headElement.appendChild(currentElement);

        currentElement = attachElements(currentElement, segmentationTargets.keySet());
        currentElement = attachElements(currentElement, emphasisTargets.keySet());

        NodeTraversor nt = new NodeTraversor(this.nodeVisitorSUT);
        nt.traverse(headElement);
        Map<String, Integer> sutScores = nodeVisitorSUT.getCurrentScores();

        for (String key :
                sutScores.keySet()) {
            assertEquals(new Integer(0), sutScores.get(key));
        }
    }


}