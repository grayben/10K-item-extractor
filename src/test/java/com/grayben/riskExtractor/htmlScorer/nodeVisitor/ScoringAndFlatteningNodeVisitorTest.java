package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.jsoup.helper.Validate.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
@Ignore
public class ScoringAndFlatteningNodeVisitorTest
        extends NodeVisitorTest {

    private ScoringAndFlatteningNodeVisitor nodeVisitorSUT;

    @Mock
    public Scorer<Element>[] elementScorersMock;

    @Before
    @Override
    public void setUp() throws Exception {
        List<Scorer<Element>> elementScorers
                = new ArrayList<>();

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
                = new ScoringAndFlatteningNodeVisitor(
                (Scorer<Element>[]) elementScorers.toArray()
        );
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
        fail("Test not implemented");
    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenAnyElementScorerIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsNonNull_ImmediatelyAfterSUTIsInitialised
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_ImmediatelyAfterSUTIsInitialised
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_AfterSingleVisitToNonElementNode
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsEmpty_AfterSingleVisitToElementWithNoText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsNotEmpty_AfterSingleVisitToElementNodeWithText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedText_AfterSingleVisitToElementWithText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedScores_AfterSingleVisitToElementWithText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedText_AfterVisitsToManyElementsWithText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_GetScoredTextReturnsExpectedScores_AfterVisitsToManyElementsWithText
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_EmphasisScoreIsZero_ImmediatelyAfterInit
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_EmphasisScoreIsZero_AfterScoringNonEmphasisElement
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_EmphasisScoreIsGreaterThanZero_AfterScoringEmphasisElement
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_EmphasisScoreIsGreaterThanZero_AfterScoringEmphasisElementThenNonEmphasisElement
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_SegmentationScoreIsZero_ImmediatelyAfterInit
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_SegmentationScoreIsZero_AfterScoringNonEmphasisElement
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_SegmentationScoreIsGreaterThanZero_AfterScoringEmphasisElement
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_SegmentationScoreIsGreaterThanZero_AfterScoringEmphasisElementThenNonEmphasisElement
            () throws Exception {
        fail("Test not implemented");
    }
}