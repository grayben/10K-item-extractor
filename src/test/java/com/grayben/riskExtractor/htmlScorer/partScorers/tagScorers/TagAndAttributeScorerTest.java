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

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;

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

    private void stubTagAndAttributeMock(){
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
    }

    @Override
    public void test_ScoreReturnsInteger_WhenArgumentIsNotEmpty() throws Exception {
        stubTagAndAttributeMock();

        Integer returned
                = tagAndAttributeScorerSUT.score(tagAndAttributeToBeScoredMock);

        assertNotNull(returned);
    }

    @Override
    @Test
    public void
    test_ScoreGivesExpectedResult_WhenSimpleInput() throws Exception {
        Map expectedOutput = new HashMap<>(TagAndAttributeScorer.defaultMap());
        stubTagAndAttributeMock();
        assert expectedOutput.put(tagAndAttributeToBeScoredMock, 0) == null;

        testHelper_ScoreGivesExpectedResult_WhenSimpleInput
                (tagAndAttributeScorerSUT, expectedOutput);
    }

    @Test
    public void
    test_ScoreThrowsIllegalArgumentException_WhenEmptyInput() throws Exception {
        thrown.expect(IllegalArgumentException.class);

        stubTagAndAttributeMock();

        Mockito.when(tagAndAttributeToBeScoredMock.getAttribute().getValue())
                .thenReturn("");

        tagAndAttributeScorerSUT.score(tagAndAttributeToBeScoredMock);
    }
}