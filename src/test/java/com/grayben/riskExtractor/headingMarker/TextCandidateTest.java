package com.grayben.riskExtractor.headingMarker;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TextCandidateTest {

    TextCandidate textCandidateSUT;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    int index = 0;

    @Mock
    List<String> list;
    int listSize = 50;

    @Before
    public void setUp() throws Exception {
        Mockito.when(list.size()).thenReturn(listSize);
        textCandidateSUT = new TextCandidate(list, index);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void
    test_ConstructorThrowsIndexOutOfBoundsException_WhenIndexIsNegative(){
        index = -1;

        thrown.expect(IndexOutOfBoundsException.class);

        textCandidateSUT = new TextCandidate(list, index);
    }

    @Test
    public void
    test_ConstructorThrowsIndexOutOfBoundsException_WhenIndexIsTooHigh
            () throws Exception {
        Mockito.when(list.size()).thenReturn(listSize);
        index = listSize + 1;

        thrown.expect(IndexOutOfBoundsException.class);

        textCandidateSUT = new TextCandidate(list, index);
    }

    @Test
    public void
    test_ConstructorThrowsNullPointerException_WhenListIsNull
            () throws Exception {

        thrown.expect(NullPointerException.class);

        textCandidateSUT = new TextCandidate(null, index);
    }
}