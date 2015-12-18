package com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.ScorerTest;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.fail;

/**
 * Created by beng on 28/11/2015.
 */

@RunWith(MockitoJUnitRunner.class)
public class EmphasisElementScorerTest extends ScorerTest<Element> {

    EmphasisElementScorer emphasisElementScorerSUT;

    @Mock
    public Element elementToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        this.emphasisElementScorerSUT = new EmphasisElementScorer();
        super.setScorerSUT(this.emphasisElementScorerSUT);
        super.setArgumentToBeScoredMock(elementToBeScoredMock);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    private void stubElementToBeScoredMock(Tag tag, Attributes attributes){
        Mockito.when(elementToBeScoredMock.tag())
                .thenReturn(tag);
        Mockito.when(elementToBeScoredMock.attributes())
                .thenReturn(attributes);
    }

    private Tag stubTag(){
        Tag tag = Mockito.mock(Tag.class);
        Mockito.when(tag.isEmpty()).thenReturn(false);
        Mockito.when(tag.getName()).thenReturn("font");

        return tag;
    }

    private Attribute stubAttribute(String key, String value){
        Attribute attribute = Mockito.mock(Attribute.class);
        Mockito.when(attribute.getKey()).thenReturn(key);
        Mockito.when(attribute.getValue()).thenReturn(value);

        return attribute;
    }

    private Attributes stubAttributes(){
        Attributes attributes = new Attributes();
        attributes.put(stubAttribute("style", "bold"));
        attributes.put(stubAttribute("foo", "bar"));

        return attributes;
    }

    @Override
    @Test
    public void
    test_ScoreReturnsInteger_WhenArgumentIsNotEmpty() throws Exception {
        Tag tag = stubTag();
        Attributes attributes = stubAttributes();

        stubElementToBeScoredMock(tag, attributes);

        Integer returned = emphasisElementScorerSUT.score(elementToBeScoredMock);

        assertNotNull(returned);
    }

    @Override
    @Test
    public void
    test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void
    test_ScoreThrowsIllegalArgumentException_WhenEmptyInput() throws Exception {
        fail("Test not implemented: decide whether appropriate");
    }
}