package com.grayben.riskExtractor.headingMarker;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beng on 2/12/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class UnmodifiableTextTest {

    private UnmodifiableText unmodifiableTextSUT = null;

    protected List<String> stringListArgument = null;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public UnmodifiableText getUnmodifiableTextSUT() {
        return unmodifiableTextSUT;
    }

    public void setUnmodifiableTextSUT(UnmodifiableText unmodifiableTextSUT) {
        this.unmodifiableTextSUT = unmodifiableTextSUT;
    }

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
    test_ConstructingThrowsNullPointerException_WhenStringListIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);

        stringListArgument = null;

        new UnmodifiableText(stringListArgument);
    }

    @Test
    public void
    test_ConstructingThrowsNullPointerException_WhenPrototypeIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);

        UnmodifiableText prototype = null;

        new UnmodifiableText(prototype);
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