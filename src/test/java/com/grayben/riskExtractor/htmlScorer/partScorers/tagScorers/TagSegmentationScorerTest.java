package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.RiskExtractor;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.lang.Object;
import java.util.HashMap;
import java.util.Map;

import static com.grayben.riskExtractor.helpers.TestHelper.*;
import static org.junit.Assert.*;

/**
 * Created by beng on 22/12/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TagSegmentationScorerTest extends MapScorerTest<Tag> {

    TagSegmentationScorer tagSegmentationScorerSUT;

    public Tag tagToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        this.tagSegmentationScorerSUT
                = new TagSegmentationScorer(
                RiskExtractor.setupTagSegmentationScoreMap()
        );
        this.tagToBeScoredMock = stubTag("some-name-to-use");
        super.setArgumentToBeScored(this.tagToBeScoredMock);
        super.setMapScorerSUT(this.tagSegmentationScorerSUT);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    @Test
    public void test_ScoreReturnsInteger_WhenArgumentIsNotEmpty() throws Exception {
        Object returned = tagSegmentationScorerSUT.score(tagToBeScoredMock);

        assertEquals(Integer.class, returned.getClass());
    }

    @Override
    @Test
    public void test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        Map<Tag, Integer> tagScoresMap = new HashMap<>();
        tagScoresMap.put(Tag.valueOf("table"), 1);
        tagScoresMap.put(Tag.valueOf("li"), 1);
        tagScoresMap.put(Tag.valueOf("tr"), 2);
        tagScoresMap.put(Tag.valueOf("th"), 1);

        tagSegmentationScorerSUT = new TagSegmentationScorer(tagScoresMap);

        Map<Tag, Integer> expectedResults = new HashMap<>(tagScoresMap);
        assert expectedResults.put(Tag.valueOf("foo"), 0) == null;
        assert expectedResults.put(Tag.valueOf("bar"), 0) == null;
        assert expectedResults.put(Tag.valueOf("baz"), 0) == null;

        super.testHelper_ScoreGivesExpectedResult_WhenSimpleInput(
                tagSegmentationScorerSUT,
                expectedResults);
    }

    @Override
    @Test
    public void test_InitThrowsNullPointerException_WhenMapParamIsNull() throws Exception {
        thrown.expect(NullPointerException.class);

        new TagSegmentationScorer(null);
    }

    @Test
    public void
    test_ScoreThrowsIllegalArgumentException_WhenTagHasNoName() throws Exception {
        Mockito.when(tagToBeScoredMock.isEmpty())
                .thenReturn(false);

        Mockito.when(tagToBeScoredMock.getName())
                .thenReturn("");

        thrown.expect(IllegalArgumentException.class);

        tagSegmentationScorerSUT.score(tagToBeScoredMock);
    }
}