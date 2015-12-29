package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

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
        elementScorers.add(
                new EmphasisElementScorer(
                        tagEmphasisScorer,
                        tagAndAttributeScorer
                )
        );

        elementScorers.add(
                new SegmentationElementScorer(
                        new TagSegmentationScorer(
                                TagSegmentationScorer.defaultMap()
                        )
                )
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

    private void visitNode(Node node, int depth){
        nodeVisitorSUT.head(node, depth);
        nodeVisitorSUT.tail(node, depth);
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_AfterSingleVisitToNonElementNode
            () throws Exception {
        Node nonElementNode = new Comment(
                "my-comment",
                "http://www.istonyabbottstillpm.com");

        visitNode(nonElementNode, 0);

        ScoredText scoredText = nodeVisitorSUT.getFlatText();

        assertTrue(scoredText.toString().isEmpty());
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_AfterSingleVisitToElementWithNoText
            () throws Exception {

        Tag tag = Tag.valueOf("a-tag-name");

        Node elementNode = new Element(tag, "http://www.baseURI.com");

        visitNode(elementNode, 0);

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

        visitNode(element, 0);

        ScoredText scoredText = nodeVisitorSUT.getFlatText();

        assertFalse(scoredText.toString().isEmpty());
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedText_AfterSingleVisitToElementWithText
            () throws Exception {
        String expectedText = "This is the text we expect to see present in the list.";
        Tag tag = Tag.valueOf("a-tag-name");

        Element element = new Element(tag, "a-base-uri");

        element.text(expectedText);

        visitNode(element, 0);

        String output = nodeVisitorSUT.getFlatText().toString();

        assertEquals(expectedText, output);
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedScores_AfterSingleVisitToElementWithText
            () throws Exception {
        fail("Test not implemented");
    }
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
    test_EmphasisScoreIsZero_AfterScoringNonEmphasisElement
            () throws Exception {
        Integer expected = 0;

        Element notEmphasised = new Element(Tag.valueOf("foo"), "a-base-uri");

        visitNode(notEmphasised, 1);

        Integer returned
                = nodeVisitorSUT.getCurrentScores()
                .get(EmphasisElementScorer.SCORE_LABEL);

        assertEquals(expected, returned);
    }

    @Test
    public void
    test_EmphasisScoreIsGreaterThanZero_AfterScoringEmphasisElement
            () throws Exception {
        Integer expectGreaterThan = 0;

        //Element emphasised = new Element(EmphasisElementScorer.)
    }

    @Test
    public void
    test_EmphasisScoreIsGreaterThanZero_AfterScoringEmphasisElementThenNonEmphasisElement
            () throws Exception {
        fail("Cannot access ScoredText::scores");
    }

    @Test
    public void
    test_SegmentationScoreIsZero_ImmediatelyAfterInit
            () throws Exception {
        fail("Cannot access ScoredText::scores");
    }

    @Test
    public void
    test_SegmentationScoreIsZero_AfterScoringNonSegmentationElement
            () throws Exception {
        fail("Cannot access ScoredText::scores");
    }

    @Test
    public void
    test_SegmentationScoreIsGreaterThanZero_AfterScoringSegmentationElement
            () throws Exception {
        fail("Cannot access ScoredText::scores");
    }

    @Test
    public void
    test_SegmentationScoreIsGreaterThanZero_AfterScoringSegmentationElementThenNonSegmentationElement
            () throws Exception {
        fail("Cannot access ScoredText::scores");
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedText_AfterVisitsToManyElementsWithText
            () throws Exception {
        fail("Cannot access ScoredText::scores");
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedScores_AfterVisitsToManyElementsWithText
            () throws Exception {
        fail("Cannot access ScoredText::scores");
    }

}