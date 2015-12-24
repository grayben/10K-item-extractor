package com.grayben.riskExtractor.htmlScorer;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.MapScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

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

    private <T extends Object> MapScorer<T>
    stubScorerMethods(Class<T> clazz, MapScorer<T> scorerMock, Map<T, Integer> scoreMap){
        //TODO: check that the label was defined (I didn't construct the object!)
        Mockito.doCallRealMethod().when(scorerMock.getScoreLabel());

        Mockito.doAnswer(invocationOnMock -> {
            T argument = (T)(invocationOnMock.getArguments())[0];
            return scoreMap.get(argument);
        }).when(scorerMock.score(Mockito.any(clazz)));

        return scorerMock;
    }

    private TagEmphasisScorer stubTagEmphasisScorer(Map<Tag, Integer> scoreMap){
        TagEmphasisScorer scorer = Mockito.mock(TagEmphasisScorer.class);
        scorer = stubScorerMethods(Tag.class, scorer, scoreMap);
        return scorer;
    }

    @Before
    public void setUp() throws Exception {
        Set<Scorer<Element>> elementScorers = new HashSet<>();
        Map<Tag, Integer> tagEmphasisScores = TagEmphasisScorer.defaultMap();
        TagEmphasisScorer tagEmphasisScorer
                = new Mockito().mock(TagEmphasisScorer.class);



        Mockito.doCallRealMethod().when(tagEmphasisScorer.getScoreLabel());
        NodeVisitor nv = new ScoringAndFlatteningNodeVisitor()
        NodeTraversor nt = new NodeTraversor()
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