package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementScorersSupplier;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.annotation.AnnotatedElement;
import com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.container.TestContainerFunction;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.ElementScorerSetFunction;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import org.apache.commons.lang3.NotImplementedException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.NodeTraversor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementListSupplier.getEmphasisedTargetElementsAndScores;
import static com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup.ElementListSupplier.getSegmentedTargetElementsAndScores;
import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScoringAndFlatteningNodeVisitorTest
        extends NodeVisitorTest {

    private ElementScorersSupplier elementScorersSupplier = new ElementScorersSupplier();
    private ElementScorerSetFunction elementScorersSetFunction = new ElementScorerSetFunction();

    private TestContainerFunction testContainerFunction = new TestContainerFunction();
    private Set<Scorer<Element>> validElementScorerSet;
    private ScoringAndFlatteningNodeVisitor nodeVisitorOUT;

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

        this.validElementScorerSet = elementScorers;
        this.nodeVisitorOUT = new ScoringAndFlatteningNodeVisitor(validElementScorerSet);
        super.setNodeVisitorSUT(nodeVisitorOUT);
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

        new ScoringAndFlatteningNodeVisitor((Set<Scorer<Element>>) null);
    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenAnyElementScorerIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);

        Set<Scorer<Element>> elementScorersWithNull = new HashSet<>(this.validElementScorerSet);
        elementScorersWithNull.add(null);

        new ScoringAndFlatteningNodeVisitor(elementScorersWithNull);
    }

    @Test
    public void
    test_GetScoredTextReturnsNonNull_ImmediatelyAfterSUTIsInitialised
            () throws Exception {
        Object returned = nodeVisitorOUT.getFlatText();

        assertNotNull(returned);
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_ImmediatelyAfterSUTIsInitialised
            () throws Exception {
        ScoredText scoredText = nodeVisitorOUT.getFlatText();

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
                = nodeVisitorOUT.getCurrentScores()
                .get(EmphasisElementScorer.SCORE_LABEL);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_EmphasisScoreIsZero_AfterHeadOnNonEmphasisElement
            () throws Exception {
        Integer expected = 0;

        Element notEmphasised = new Element(Tag.valueOf("foo"), "a-base-uri");

        nodeVisitorOUT.head(notEmphasised, 1);

        Integer returned
                = nodeVisitorOUT.getCurrentScores()
                .get(EmphasisElementScorer.SCORE_LABEL);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_EmphasisScoreIsZero_AfterTailOnNonEmphasisElement
            () throws Exception {
        Integer expected = 0;

        Element notEmphasised = new Element(Tag.valueOf("foo"), "a-base-uri");

        nodeVisitorOUT.tail(notEmphasised, 1);

        Integer returned
                = nodeVisitorOUT.getCurrentScores()
                .get(EmphasisElementScorer.SCORE_LABEL);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_EmphasisScoreIsGreaterThanZero_AfterHeadOnEmphasisElement
            () throws Exception {


        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it = getEmphasisedTargetElementsAndScores(nodeVisitorOUT.getElementScorers())
                .entrySet().iterator();
        Map.Entry<Element, Integer> emphasisedElementAndScore
                = it.next();

        TestContainer<Map.Entry<Element, Integer>, Integer> testContainer
                = new TestContainer.Builder<Map.Entry<Element, Integer>, Integer>()
                .begin()
                .systemUnderTest(
                        myEntry -> {
                            nodeVisitorOUT.head(myEntry.getKey(), 1);
                            return nodeVisitorOUT.getCurrentScores().get(scoreLabel);
                        }
                ).oracle((myEntry, myInteger) -> myEntry.getValue().equals(myInteger))
                .build();

        assertTrue(testContainer.verify(emphasisedElementAndScore));
    }

    @Test
    public void
    test_EmphasisScoreIsGreaterThanZero_AfterHeadOnEmphasisElementThenNonEmphasisElement
            () throws Exception {
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it = getEmphasisedTargetElementsAndScores(nodeVisitorOUT.getElementScorers())
                .entrySet().iterator();
        Map.Entry<Element, Integer> emphasisedElementAndScore
                = it.next();
        Element emphasisedElement = emphasisedElementAndScore.getKey();
        Integer expected = emphasisedElementAndScore.getValue();

        Element notEmphasisedElement
                = new Element(Tag.valueOf("foo-bar"), "some-string");

        nodeVisitorOUT.head(emphasisedElement, 1);
        nodeVisitorOUT.head(notEmphasisedElement, 2);

        Integer returned = nodeVisitorOUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_EmphasisScoreIsZero_AfterHeadAndTailOnEmphasisElement
            () throws Exception {
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Integer expected = 0;

        Iterator<Map.Entry<Element, Integer>> it = getEmphasisedTargetElementsAndScores(nodeVisitorOUT.getElementScorers())
                .entrySet().iterator();
        Map.Entry<Element, Integer> emphasisedElementAndScore
                = it.next();
        Element emphasisedElement = emphasisedElementAndScore.getKey();

        nodeVisitorOUT.head(emphasisedElement, 1);
        nodeVisitorOUT.tail(emphasisedElement, 1);

        Integer returned = nodeVisitorOUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_EmphasisScoreIsGreaterThanZero_AfterHeadOn2EmphasisElementsAndTailOnLatterElement
            () throws Exception {
        String scoreLabel = EmphasisElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it = getEmphasisedTargetElementsAndScores(nodeVisitorOUT.getElementScorers())
                .entrySet().iterator();
        Map.Entry<Element, Integer> emphasisedElementAndScore;

        emphasisedElementAndScore = it.next();
        Element emphasisedElement1 = emphasisedElementAndScore.getKey();
        Integer expected = emphasisedElementAndScore.getValue();

        Element emphasisedElement2
                = it.next().getKey();

        nodeVisitorOUT.head(emphasisedElement1, 1);
        nodeVisitorOUT.head(emphasisedElement2, 2);
        nodeVisitorOUT.tail(emphasisedElement2, 1);

        Integer returned = nodeVisitorOUT.getCurrentScores().get(scoreLabel);

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

        Iterator<Map.Entry<Element, Integer>> it = getEmphasisedTargetElementsAndScores(nodeVisitorOUT.getElementScorers())
                .entrySet().iterator();
        Map.Entry<Element, Integer> emphasisedElementAndScore;

        emphasisedElementAndScore = it.next();
        Element emphasisedElement = emphasisedElementAndScore.getKey();
        Integer expectedScore = emphasisedElementAndScore.getValue();

        String elementText = "This is some text contained by the element.";
        emphasisedElement.text(elementText);

        nodeVisitorOUT.head(emphasisedElement, 1);
        nodeVisitorOUT.tail(emphasisedElement, 1);

        ScoredTextElement scoredTextElement
                = nodeVisitorOUT.getFlatText().getList().iterator().next();
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
                = nodeVisitorOUT.getCurrentScores().get(scoreLabel);

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

        nodeVisitorOUT.head(nonSegElem, 1);

        Integer returned = nodeVisitorOUT.getCurrentScores().get(scoreLabel);

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

        nodeVisitorOUT.tail(nonSegElem, 1);

        Integer returned = nodeVisitorOUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_SegmentationScoreIsGreaterThanZero_AfterHeadOnSegmentationElement
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it
                = getSegmentedTargetElementsAndScores(nodeVisitorOUT.getElementScorers())
                .entrySet().iterator();
        Map.Entry<Element, Integer> segmentedElementAndScore
                = it.next();
        Element segmentedElement = segmentedElementAndScore.getKey();
        Integer expected = segmentedElementAndScore.getValue();
        assert expected > 0;

        nodeVisitorOUT.head(segmentedElement, 1);

        Integer returned = nodeVisitorOUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_SegmentationScoreIsGreaterThanZero_AfterHeadOnSegmentationElementThenNonSegmentationElement
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it
                = getSegmentedTargetElementsAndScores(nodeVisitorOUT.getElementScorers())
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

        nodeVisitorOUT.head(segmentedElement, 1);
        nodeVisitorOUT.head(nonSegmentedElement, 1);

        Integer returned = nodeVisitorOUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_SegmentationScoreIsZero_AfterHeadAndTailOnSegmentationElement
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Integer expected = 0;

        Element segmentedElement
                = getSegmentedTargetElementsAndScores(nodeVisitorOUT.getElementScorers())
                .entrySet().iterator().next().getKey();

        nodeVisitorOUT.head(segmentedElement, 1);
        nodeVisitorOUT.tail(segmentedElement, 1);

        Integer returned = nodeVisitorOUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_SegmentationScoreIsGreaterThanZero_AfterHeadOn2SegmentationElementsAndTailOnLatterElement
            () throws Exception {
        String scoreLabel = SegmentationElementScorer.SCORE_LABEL;

        Iterator<Map.Entry<Element, Integer>> it
                = getSegmentedTargetElementsAndScores(nodeVisitorOUT.getElementScorers())
                .entrySet().iterator();
        Map.Entry<Element, Integer> segmentedElementAndScore
                = it.next();
        Element segmentedElement1 = segmentedElementAndScore.getKey();
        Integer expected = segmentedElementAndScore.getValue();
        assert expected > 0;

        Element segmentedElement2 = it.next().getKey();

        nodeVisitorOUT.head(segmentedElement1, 1);
        nodeVisitorOUT.head(segmentedElement2, 1);
        nodeVisitorOUT.tail(segmentedElement2, 1);

        Integer returned = nodeVisitorOUT.getCurrentScores().get(scoreLabel);

        assertEquals(expected, returned);
    }

    private void helper_ScoreElementAndExpectScore
            (Element element, String scoreLabel, Integer expectedScore){
        nodeVisitorOUT.head(element, 1);
        nodeVisitorOUT.tail(element, 1);

        ScoredTextElement scoredTextElement
                = nodeVisitorOUT.getFlatText().getList().iterator().next();

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

        Iterator<Map.Entry<Element, Integer>> it = getSegmentedTargetElementsAndScores(nodeVisitorOUT.getElementScorers())
                .entrySet().iterator();
        Map.Entry<Element, Integer> segmentedElementAndScore;

        segmentedElementAndScore = it.next();
        Element segmentedElement = segmentedElementAndScore.getKey();
        Integer expectedScore = segmentedElementAndScore.getValue();

        String elementText = "This is some text contained by the element.";
        segmentedElement.text(elementText);

        nodeVisitorOUT.head(segmentedElement, 1);
        nodeVisitorOUT.tail(segmentedElement, 1);

        ScoredTextElement scoredTextElement
                = nodeVisitorOUT.getFlatText().getList().iterator().next();
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

        NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitorOUT);

        nodeTraversor.traverse(nonElementNode);

        ScoredText scoredText = nodeVisitorOUT.getFlatText();

        assertTrue(scoredText.toString().isEmpty());
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_AfterSingleVisitToElementWithNoText
            () throws Exception {

        Tag tag = Tag.valueOf("a-tag-name");

        Node elementNode = new Element(tag, "http://www.baseURI.com");

        NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitorOUT);
        nodeTraversor.traverse(elementNode);

        ScoredText scoredText = nodeVisitorOUT.getFlatText();

        assertTrue(scoredText.toString().isEmpty());
    }

    @Test
    public void
    test_GetScoredTextReturnsNotEmpty_AfterSingleVisitToElementNodeWithText
            () throws Exception {
        Tag tag = Tag.valueOf("a-tag-name");

        Element element = new Element(tag, "a-base-URI");

        element.text("Some text is here.");

        NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitorOUT);
        nodeTraversor.traverse(element);

        ScoredText scoredText = nodeVisitorOUT.getFlatText();

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

        NodeTraversor nodeTraversor = new NodeTraversor(nodeVisitorOUT);
        nodeTraversor.traverse(element);

        String output = nodeVisitorOUT.getFlatText().toString();

        assertEquals(expectedText, output);
    }


    @Test
    public void
    test_AllScoresAreZero_AfterTraversalOfManyTargetElements
            () throws Exception {

        NodeTraversor nt = new NodeTraversor(this.nodeVisitorOUT);
        nt.traverse(Jsoup.parse(new URL("http://google.com"), 2000).body());

        Map<String, Integer> sutScores = nodeVisitorOUT.getCurrentScores();

        for (String key :
                sutScores.keySet()) {
            assertEquals(new Integer(0), sutScores.get(key));
        }
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedText_AfterVisitsToManyElementsWithText
            () throws Exception {
        AnnotatedElement annotatedElement = null;
        //testContainerSupplier.get().verify(annotatedElement);
        throw new NotImplementedException("This test is not implemented");
    }/*

    @Test
    public void
    test_GetScoredTextReturnsExpectedScores_AfterVisitsToManyElementsWithText
            () throws Exception {
        TestContainerFunction oracle = new TestContainerFunction(TreeAssembler.Configuration.MIXED_TREE);
        this.nodeVisitorOUT = oracle.getSUT();
        Element input = oracle.getInput();
        NodeTraversor nt = new NodeTraversor(this.nodeVisitorOUT);

        nt.traverse(input);

        List<ScoredTextElement> expectedOutput = oracle.getExpectedOutput().getList();
        List<ScoredTextElement> actualOutput = nodeVisitorOUT.getFlatText().getList();

        assert expectedOutput.size() == actualOutput.size();

        for(int i = 0; i < expectedOutput.size(); i++){
            assertEquals(expectedOutput.get(i).getScores(), actualOutput.get(i).getScores());
        }
    }
    */

    @Test
    public void
    test_GetScoredTextReturnsExpected_AfterVisitsToManyElementsWithText
            () throws Exception {
        //isn't this the same test now?
        test_GetScoredTextReturnsExpectedText_AfterVisitsToManyElementsWithText();
    }

}