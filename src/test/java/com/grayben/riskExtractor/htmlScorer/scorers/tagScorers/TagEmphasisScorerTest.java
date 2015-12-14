package com.grayben.riskExtractor.htmlScorer.scorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.scorers.ScorerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by beng on 28/11/2015.
 */
@Ignore
public class TagEmphasisScorerTest
        extends ScorerTest {

    public TagEmphasisScorer tagEmphasisScorerSUT;

    @Before
    public void setUp() throws Exception {
        this.tagEmphasisScorerSUT = new TagEmphasisScorer
                (TagEmphasisScorer.defaultMap());
        super.setUp(tagEmphasisScorerSUT);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Override
    @Test
    public void test_ScoreReturnsInteger_WhenArgumentIsNonNull() throws Exception {

    }

    @Test
    public void testScore() throws Exception {

    }

    @Test
    public void testUseDefaultMap() throws Exception {

    }
}