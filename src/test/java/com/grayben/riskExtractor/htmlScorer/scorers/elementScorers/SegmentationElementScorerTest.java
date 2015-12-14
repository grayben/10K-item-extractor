package com.grayben.riskExtractor.htmlScorer.scorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.scorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.scorers.ScorerTest;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SegmentationElementScorerTest
        extends ScorerTest<Element> {

    SegmentationElementScorer elementScorerSUT;
    @Mock
    Scorer<Tag> tagScorerMock;
    @Mock
    Element elementMock;

    @Before
    public void setUp() throws Exception {
        elementScorerSUT = new SegmentationElementScorer(tagScorerMock);
        super.setScorerSUT(elementScorerSUT);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        elementScorerSUT = null;
        super.tearDown();
    }

}