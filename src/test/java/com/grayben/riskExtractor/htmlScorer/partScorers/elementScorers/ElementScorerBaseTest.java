package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.ScorerTest;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static com.grayben.riskExtractor.helpers.TestHelper.dummyAttributes;
import static com.grayben.riskExtractor.helpers.TestHelper.stubElement;
import static com.grayben.riskExtractor.helpers.TestHelper.stubTag;

/**
 * Created by beng on 23/12/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class ElementScorerBaseTest extends ScorerTest<Element> {

    private Scorer<Element> elementScorerSUT;

    @Override
    protected void setScorerSUT(Scorer<Element> elementScorer){
        this.elementScorerSUT = elementScorer;
        super.setScorerSUT(this.elementScorerSUT);

    }

    private Element elementToBeScored;

    @Override
    protected void setArgumentToBeScored(Element elementToBeScored){
        this.elementToBeScored = elementToBeScored;
        super.setArgumentToBeScored(this.elementToBeScored);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void
    test_ScoreThrowsIllegalArgumentException_WhenTagIsEmpty() throws Exception {

        Tag tagStub = stubTag("");
        Mockito.when(tagStub.isEmpty()).thenReturn(true);

        Attributes attributes = dummyAttributes();

        elementToBeScored = stubElement(tagStub, attributes);

        thrown.expect(IllegalArgumentException.class);

        elementScorerSUT.score(elementToBeScored);
    }


}
