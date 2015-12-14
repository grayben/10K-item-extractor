package com.grayben.riskExtractor.htmlScorer.scorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.scorers.ScorerTest;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by beng on 28/11/2015.
 */
public class TagEmphasisScorerTest
        extends ScorerTest<Tag> {

    public TagEmphasisScorer tagEmphasisScorerSUT;

    @Before
    public void setUp() throws Exception {
        this.tagEmphasisScorerSUT = new TagEmphasisScorer
                (TagEmphasisScorer.defaultMap());
        super.setScorerSUT(tagEmphasisScorerSUT);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testScore() throws Exception {

    }

    @Test
    public void testUseDefaultMap() throws Exception {

    }
}