package com.grayben.riskExtractor.headingMarker;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Created by beng on 30/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TextCandidatesTest {

    List<TextCandidate> listOfCandidates;

    @Mock
    Collection<String> collectionOfStringsMock;

    @Mock
    List<String> stringListMock;

    @Mock
    TextCandidate textCandidateMock;

    TextCandidates textCandidatesSUT;

    List<String> textListReturned;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private void initListOfCandidates(){
        listOfCandidates = new ArrayList<>();
        listOfCandidates.add(textCandidateMock);
        listOfCandidates.add(textCandidateMock);
        listOfCandidates.add(textCandidateMock);
    }

    @Before
    public void setUp() throws Exception {
        initListOfCandidates();
        Mockito.when(textCandidateMock.getList()).thenReturn(stringListMock);
        textCandidatesSUT = new TextCandidates(listOfCandidates);
        textListReturned = textCandidatesSUT.getTextList();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddObject
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        textListReturned.add(new String());
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddObjectWithIndex
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        textListReturned.add(0, new String());
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddAllCollection
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        textListReturned.addAll(collectionOfStringsMock);
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddAllCollectionWithIndex
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        textListReturned.addAll(0, collectionOfStringsMock);
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenClear
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        textListReturned.clear();
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenRemoveAll
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        textListReturned.removeAll(collectionOfStringsMock);
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenReplaceAll
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        textListReturned.replaceAll(new UnaryOperator<String>() {
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
        textListReturned.retainAll(collectionOfStringsMock);
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenSet
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        textListReturned.set(0, "cow");
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenSort
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        textListReturned.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.hashCode() - o2.hashCode();
            }
        });
    }
}