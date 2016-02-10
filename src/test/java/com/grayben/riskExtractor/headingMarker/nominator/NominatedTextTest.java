package com.grayben.riskExtractor.headingMarker.nominator;

import com.grayben.riskExtractor.headingMarker.UnmodifiableTextTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class NominatedTextTest
            extends UnmodifiableTextTest {

    private NominatedText nominatedTextSUT = null;

    public NominatedText getNominatedTextSUT() {
        return nominatedTextSUT;
    }

    public void setNominatedTextSUT(NominatedText nominatedTextSUT) {
        this.nominatedTextSUT = nominatedTextSUT;
        this.setUnmodifiableTextSUT(this.nominatedTextSUT);
    }

    protected Set<Integer> nomineesArgument = null;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.stringListArgument = new ArrayList<>();
        this.stringListArgument.add("one");
        this.stringListArgument.add("two");
        this.stringListArgument.add("cow");

        this.nomineesArgument = new HashSet<>();
        this.nomineesArgument.add(0);
        this.nomineesArgument.add(2);

        this.setNominatedTextSUT(
                new NominatedText(stringListArgument, nomineesArgument)
        );
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        nominatedTextSUT = null;
    }

    @Test
    public void
    test_BasicConstructorThrowsNullPointerException_WhenStringListIsNull
            () throws Exception {
        stringListArgument = null;

        thrown.expect(NullPointerException.class);

        new NominatedText(stringListArgument, nomineesArgument);
    }

    @Test
    public void
    test_BasicConstructorThrowsNullPointerException_WhenNomineesIsNull
            () throws Exception {
        nomineesArgument = null;

        thrown.expect(NullPointerException.class);

        new NominatedText(stringListArgument, nomineesArgument);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenUnmodifiableTextIsNull
            () throws Exception {
        this.setNominatedTextSUT(null);

        thrown.expect(NullPointerException.class);

        new NominatedText(this.getUnmodifiableTextSUT(), nomineesArgument);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenNomineesIsNull
            () throws Exception {
        nomineesArgument = null;

        thrown.expect(NullPointerException.class);

        new NominatedText(this.getUnmodifiableTextSUT(), nomineesArgument);
    }

    @Test
    public void
    test_PrototypeConstructorThrowsNullPointerException_WhenPrototypeIsNull
            () throws Exception {
        setNominatedTextSUT(null);

        thrown.expect(NullPointerException.class);

        new NominatedText(getNominatedTextSUT());
    }

    @Test
    public void
    test_GetNominees_ReturnsNonNull_Always
            () throws Exception {

        Set<Integer> nomineesReturned = nominatedTextSUT.getNomineeIndices();

        assertNotNull(nomineesReturned);
    }

    @Test
    public void
    test_ModifyingReturnedNomineesDoesNotAffectCopyHeldBySUT
            () throws Exception {
        Set<Integer> returnedFirst = nominatedTextSUT.getNomineeIndices();
        returnedFirst.add(Integer.MAX_VALUE);
        Set<Integer> returnedSecond = nominatedTextSUT.getNomineeIndices();
        assertNotEquals(returnedFirst, returnedSecond);
    }
}