package com.grayben.riskExtractor.htmlScorer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScoredTextTest {

    private ScoredText scoredTextSUT;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    public ScoredTextElement scoredTextElementMock;

    public Map<String, Integer> scoresPrototype;

    public String stringPrototype;

    @Before
    public void setUp() throws Exception {
        scoredTextSUT = new ScoredText();
        scoresPrototype = new HashMap<>();
        scoresPrototype.put("swag", 9001);
        stringPrototype = "foo";
        Mockito.when(scoredTextElementMock.getScores())
                .thenReturn(new HashMap<>(scoresPrototype));
        Mockito.when(scoredTextElementMock.getTextElement())
                .thenReturn(new String(stringPrototype));
    }

    @After
    public void tearDown() throws Exception {
        scoredTextSUT = null;
    }

    @Test
    public void
    test_ToStringReturnsNonNull_WhenNothingWasAdded
            () throws Exception {
        scoredTextSUT = new ScoredText();

        assertNotNull(scoredTextSUT.toString());
    }

    @Test
    public void
    test_AddChangesWhatToStringReturns
            () throws Exception {
        String beforeAdd = scoredTextSUT.toString();
        scoredTextSUT.add(scoredTextElementMock);
        String afterAdd = scoredTextSUT.toString();

        assertNotSame(beforeAdd, afterAdd);
    }

    @Test
    public void
    test_AddThrowsNullPointerException_WhenParamIsNull
            () throws Exception {
        ScoredTextElement element = null;

        thrown.expect(NullPointerException.class);

        scoredTextSUT.add(element);
    }

    @Test
    public void
    test_AddThrowsNullPointerException_WhenParamScoresInNull
            () throws Exception {
        Mockito.when(scoredTextElementMock.getScores())
                .thenReturn(null);

        thrown.expect(NullPointerException.class);

        scoredTextSUT.add(scoredTextElementMock);
    }

    @Test
    public void
    test_AddThrowsNullPointerException_WhenParamTextIsNull
            () throws Exception {
        Mockito.when(scoredTextElementMock.getTextElement()).thenReturn(null);

        thrown.expect(NullPointerException.class);

        scoredTextSUT.add(scoredTextElementMock);
    }

    @Test
    public void
    test_AddThrowsIllegalArgumentException_WhenParamHasEmptyText
            () throws Exception {
        Mockito.when(scoredTextElementMock.getTextElement()).thenReturn("");

        thrown.expect(IllegalArgumentException.class);

        scoredTextSUT.add(scoredTextElementMock);
    }

    @Test
    public void
    test_AddThrowsIllegalArgumentException_WhenParamHasEmptyScores
            () throws Exception {
        Mockito.when(scoredTextElementMock.getScores())
                .thenReturn(new HashMap());

        thrown.expect(IllegalArgumentException.class);

        scoredTextSUT.add(scoredTextElementMock);
    }

    @Test
    public void
    test_GetTextElementReturnsExpectedOutput_OnSimpleInputCase
            () throws Exception {
        List<ScoredTextElement> elementMocks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ScoredTextElement elementMock
                    = Mockito.mock(ScoredTextElement.class);
            Mockito.when(elementMock.getTextElement())
                    .thenReturn(new String(stringPrototype));
            Mockito.when(elementMock.getScores())
                    .thenReturn(new HashMap<>(scoresPrototype));
            elementMocks.add(elementMock);
        }

        String expectedOutput = "foo foo foo foo foo";

        for (ScoredTextElement elementMock : elementMocks) {
            scoredTextSUT.add(elementMock);
        }

        String output = scoredTextSUT.toString();

        assertEquals(expectedOutput, output);
    }
}