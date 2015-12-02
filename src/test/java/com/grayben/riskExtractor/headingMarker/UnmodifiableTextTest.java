package com.grayben.riskExtractor.headingMarker;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beng on 2/12/2015.
 */
public class UnmodifiableTextTest {

    protected UnmodifiableText unmodifiableTextSUT = null;

    protected List<String> stringListArgument = null;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        stringListArgument = new ArrayList<>();
        stringListArgument.add("one");
        stringListArgument.add("two");
        stringListArgument.add("cow");
        unmodifiableTextSUT = new UnmodifiableText(stringListArgument);
    }

    @After
    public void tearDown() throws Exception {
        unmodifiableTextSUT = null;
        stringListArgument = null;
    }

    @Test
    public void
    test_ReturnedStringListEqualsStringList
            () throws Exception {
        List<String> returnedStringList = unmodifiableTextSUT.getStringList();
        assert(returnedStringList.equals(stringListArgument));
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