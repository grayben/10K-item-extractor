package com.grayben.riskExtractor.headingMarker;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beng on 2/12/2015.
 */
public class UnmodifiableTextTest {

    UnmodifiableText unmodifiableTextSUT;

    List<String> stringList;

    List<String> returnedStringList;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        stringList = new ArrayList<>();
        stringList.add("one");
        stringList.add("two");
        stringList.add("cow");
        unmodifiableTextSUT = new UnmodifiableText(stringList);
        returnedStringList = unmodifiableTextSUT.getStringList();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void
    test_ReturnedStringListEqualsStringList
            () throws Exception {
        assert(returnedStringList.equals(stringList));
    }

    @Test
    public void
    test_ReturnedStringListThrowsUnsupportedOperationException_WhenModified
            () throws Exception {
        List<String> returnedStringList = unmodifiableTextSUT.getStringList();

        thrown.expect(UnsupportedOperationException.class);

        returnedStringList.add("something");
    }
}