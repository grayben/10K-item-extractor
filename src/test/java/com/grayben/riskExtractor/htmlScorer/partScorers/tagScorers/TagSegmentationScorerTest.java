package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by beng on 22/12/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TagSegmentationScorerTest extends MapScorerTest<Tag> {

    TagSegmentationScorer tagSegmentationScorerSUT;

    @Mock public Tag tagToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        this.tagSegmentationScorerSUT
                = new TagSegmentationScorer(
                TagSegmentationScorer.defaultMap()
        );
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

    }

    @Override
    @Test
    public void test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {

    }

    @Override
    @Test
    public void test_InitThrowsNullPointerException_WhenMapParamIsNull() throws Exception {

    }
}