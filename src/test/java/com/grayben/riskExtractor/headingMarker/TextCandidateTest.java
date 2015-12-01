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

    List<String> stringListReturned;

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
        stringListReturned = textCandidateSUT.getList();
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

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddObject
            () throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddObjectWithIndex
            () throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddAllCollection
            () throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddAllCollectionWithIndex
            () throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenClear
            () throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenRemoveAll
            () throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenReplaceAll
            () throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenRetainAll
            () throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenSet
            () throws Exception {
        fail("This test has not been implemented");
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenSort
            () throws Exception {
        fail("This test has not been implemented");
    }
}