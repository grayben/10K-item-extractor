package com.grayben.riskExtractor.headingMarker;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Created by beng on 30/11/2015.
 */
public class TextCandidatesTest {

    @Mock
    List<TextCandidate> listOfCandidates;

    @Mock
    Collection<String> collectionOfStrings;

    TextCandidates textCandidatesSUT;

    List<String> textListReturned;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
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
        textListReturned.addAll(collectionOfStrings);
    }

    @Test
    public void
    test_ReturnedTextListThrowsUnsupportedOperationException_WhenAddAllCollectionWithIndex
            () throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        textListReturned.addAll(0, collectionOfStrings);
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
        textListReturned.removeAll(collectionOfStrings);
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
        textListReturned.retainAll(collectionOfStrings);
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