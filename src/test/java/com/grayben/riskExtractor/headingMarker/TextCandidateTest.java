package com.grayben.riskExtractor.headingMarker;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;

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

    int listSize = 50;

    @Mock
    List<String> list;

    @Mock
    Collection<String> collectionOfStrings;


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
        thrown.expect(UnsupportedOperationException.class);
        stringListReturned.add(new String());
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddObjectWithIndex
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        stringListReturned.add(0, new String());
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddAllCollection
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        stringListReturned.addAll(collectionOfStrings);
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddAllCollectionWithIndex
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        stringListReturned.addAll(0, collectionOfStrings);
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenClear
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        stringListReturned.clear();
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenRemoveAll
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        stringListReturned.removeAll(collectionOfStrings);
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenReplaceAll
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        stringListReturned.replaceAll(new UnaryOperator<String>() {
            @Override
            public String apply(String s) {
                return null;
            }
        });
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenRetainAll
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        stringListReturned.retainAll(collectionOfStrings);
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenSet
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        stringListReturned.set(0, "cow");
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenSort
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        stringListReturned.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.hashCode() - o2.hashCode();
            }
        });
    }
}