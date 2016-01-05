package com.grayben.riskExtractor.htmlScorer;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TreeHtmlScorerTest
        extends HtmlScorerTest {

    TreeHtmlScorer treeHtmlScorerSUT;

    @Before
    public void setUp() throws Exception {
        Set<Scorer<Element>> elementScorers = new HashSet<>();
        Map<Tag, Integer> tagEmphasisScores = TagEmphasisScorer.defaultMap();
        TagEmphasisScorer tagEmphasisScorer
                = new Mockito().mock(TagEmphasisScorer.class);



        Mockito.doCallRealMethod().when(tagEmphasisScorer.getScoreLabel());
        NodeVisitor nv = new ScoringAndFlatteningNodeVisitor(null);
        NodeTraversor nt = new NodeTraversor(nv);
        setHtmlScorerSUT(new TreeHtmlScorer());
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        setHtmlScorerSUT(null);
    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenNodeTraversorIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Override
    @Test
    public void
    test_ScoreHtmlReturnsExpected_WhenTextInputIsSimple
            () throws Exception {
        fail("Test not implemented");
    }

    @Override
    @Test
    public void
    test_ScoreHtmlReturnsExpected_WhenTextInputIsComplicated
            () throws Exception {
        fail("Test not implemented");
    }
}