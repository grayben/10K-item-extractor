package com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers;

import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
import org.jsoup.nodes.Attribute;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TagAndAttributeScorerTest
        extends MapScorerTest<TagAndAttribute> {

    TagAndAttributeScorer tagAndAttributeScorerSUT;

    @Mock
    public TagAndAttribute tagAndAttributeToBeScoredMock;

    @Before
    public void setUp() throws Exception {
        tagAndAttributeScorerSUT = new TagAndAttributeScorer(
                TagAndAttributeScorer.defaultMap()
        );
        super.setArgumentToBeScoredMock(tagAndAttributeToBeScoredMock);
        super.setMapScorerSUT(tagAndAttributeScorerSUT);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    public void test_InitThrowsNullPointerException_WhenMapParamIsNull() throws Exception {
        Map<TagAndAttribute, Integer> nullMap = null;

        thrown.expect(NullPointerException.class);

        tagAndAttributeScorerSUT = new TagAndAttributeScorer(nullMap);
    }

    @Override
    public void test_ScoreReturnsInteger_WhenArgumentIsNotEmpty() throws Exception {
        Tag tagMock = Mockito.mock(Tag.class);
        Mockito.when(tagMock.getName()).thenReturn("font");
        Mockito.when(tagMock.isEmpty()).thenReturn(false);

        Attribute attributeMock = Mockito.mock(Attribute.class);
        Mockito.when(attributeMock.getKey()).thenReturn("foo");
        Mockito.when(attributeMock.getValue()).thenReturn("bar");
        Mockito.when(attributeMock.html()).thenReturn("<foo>bar</foo>");

        Mockito.when(tagAndAttributeToBeScoredMock.getAttribute())
                .thenReturn(attributeMock);
        Mockito.when(tagAndAttributeToBeScoredMock.getTag())
                .thenReturn(tagMock);

        Integer returned
                = tagAndAttributeScorerSUT.score(tagAndAttributeToBeScoredMock);

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