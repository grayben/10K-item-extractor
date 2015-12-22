package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.lang.Object;

import static com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.TestHelper.*;
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
                TagSegmentationScorer.defaultMap()
        );
        this.tagToBeScoredMock = stubTag("some-name-to-use");
        super.setArgumentToBeScoredMock(this.tagToBeScoredMock);
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
        fail("Test not implemented");
    }

    @Override
    @Test
    public void test_InitThrowsNullPointerException_WhenMapParamIsNull() throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreThrowsIllegalArgumentException_WhenEmptyInput() throws Exception {
        Mockito.when(tagToBeScoredMock.isEmpty())
                .thenReturn(true);

        thrown.expect(IllegalArgumentException.class);

        tagSegmentationScorerSUT.score(tagToBeScoredMock);
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