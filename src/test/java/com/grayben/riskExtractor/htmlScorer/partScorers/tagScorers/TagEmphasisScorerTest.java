package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.RiskExtractor;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TagEmphasisScorerTest
        extends MapScorerTest<Tag> {

    public TagEmphasisScorer tagEmphasisScorerSUT;

    @Mock
    public Tag tagToBeScoredMock;

    @Before
    @Override
    public void setUp() throws Exception {
        this.tagEmphasisScorerSUT = new TagEmphasisScorer
                (RiskExtractor.setupTagEmphasisScoreMap());
        super.setMapScorerSUT(tagEmphasisScorerSUT);

        assert this.tagToBeScoredMock != null;
        super.setArgumentToBeScored(tagToBeScoredMock);

        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    public void test_InitThrowsNullPointerException_WhenMapParamIsNull() throws Exception {
        Map<Tag, Integer> tagScoresMap = null;

        thrown.expect(NullPointerException.class);

        tagEmphasisScorerSUT = new TagEmphasisScorer(tagScoresMap);
    }

    @Override
    @Test
    public void
    test_ScoreReturnsInteger_WhenArgumentIsNotEmpty() throws Exception {
        Mockito.when(tagToBeScoredMock.isEmpty())
                .thenReturn(false);
        Mockito.when(tagToBeScoredMock.getName())
                .thenReturn("b");

        Object returned = tagEmphasisScorerSUT.score(tagToBeScoredMock);
        assertEquals(Integer.class, returned.getClass());
    }

    @Override
    public void test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        Map<Tag, Integer> tagScoresMap = new HashMap<>();
        tagScoresMap.put(Tag.valueOf("b"), 1);
        tagScoresMap.put(Tag.valueOf("strong"), 1);
        tagScoresMap.put(Tag.valueOf("h1"), 2);
        tagScoresMap.put(Tag.valueOf("h2"), 1);

        tagEmphasisScorerSUT = new TagEmphasisScorer(tagScoresMap);

        Map<Tag, Integer> expectedResults = new HashMap<>(tagScoresMap);
        assert expectedResults.put(Tag.valueOf("foo"), 0) == null;
        assert expectedResults.put(Tag.valueOf("bar"), 0) == null;
        assert expectedResults.put(Tag.valueOf("baz"), 0) == null;

        super.testHelper_ScoreGivesExpectedResult_WhenSimpleInput(
                tagEmphasisScorerSUT,
                expectedResults);
    }

    @Test
    public void
    test_ScoreThrowsIllegalArgumentException_WhenTagHasNoName() throws Exception {
        Mockito.when(tagToBeScoredMock.isEmpty())
                .thenReturn(false);

        Mockito.when(tagToBeScoredMock.getName())
                .thenReturn("");

        thrown.expect(IllegalArgumentException.class);

        tagEmphasisScorerSUT.score(tagToBeScoredMock);
    }
}