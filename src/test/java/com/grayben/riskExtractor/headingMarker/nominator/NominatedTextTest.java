package com.grayben.riskExtractor.headingMarker.nominator;

import com.grayben.riskExtractor.headingMarker.Nominator;
import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class NominatedTextTest {

    private List<String> stringListArgument;

    private Nominator.NominatedText nominatedTextSUT = null;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    public Nominator.NominatedText getNominatedTextSUT() {
        return nominatedTextSUT;
    }

    public void setNominatedTextSUT(Nominator.NominatedText nominatedTextSUT) {
        this.nominatedTextSUT = nominatedTextSUT;
    }

    protected SetUniqueList<Integer> nomineesArgument = null;

    @Before
    public void setUp() throws Exception {
        this.stringListArgument = new ArrayList<>();
        this.stringListArgument.add("one");
        this.stringListArgument.add("two");
        this.stringListArgument.add("cow");

        this.nomineesArgument = SetUniqueList.setUniqueList(new ArrayList<>());
        this.nomineesArgument.add(0);
        this.nomineesArgument.add(2);

        this.setNominatedTextSUT(
                new Nominator.NominatedText(stringListArgument, nomineesArgument)
        );
    }

    @After
    public void tearDown() throws Exception {
        nominatedTextSUT = null;
    }

    @Test
    public void
    test_BasicConstructorThrowsNullPointerException_WhenStringListIsNull
            () throws Exception {
        stringListArgument = null;

        thrown.expect(NullPointerException.class);

        new Nominator.NominatedText(stringListArgument, nomineesArgument);
    }

    @Test
    public void
    test_BasicConstructorThrowsNullPointerException_WhenNomineesIsNull
            () throws Exception {
        nomineesArgument = null;

        thrown.expect(NullPointerException.class);

        new Nominator.NominatedText(stringListArgument, nomineesArgument);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenUnmodifiableTextIsNull
            () throws Exception {
        this.setNominatedTextSUT(null);

        thrown.expect(NullPointerException.class);

        new Nominator.NominatedText(this.nominatedTextSUT.getUnmodifiableText(), nomineesArgument);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenNomineesIsNull
            () throws Exception {
        nomineesArgument = null;

        thrown.expect(NullPointerException.class);

        new Nominator.NominatedText(this.nominatedTextSUT.getUnmodifiableText(), nomineesArgument);
    }

    @Test
    public void
    test_PrototypeConstructorThrowsNullPointerException_WhenPrototypeIsNull
            () throws Exception {
        setNominatedTextSUT(null);

        thrown.expect(NullPointerException.class);

        new Nominator.NominatedText(getNominatedTextSUT());
    }

    @Test
    public void
    test_GetNominees_ReturnsNonNull_Always
            () throws Exception {

        SetUniqueList<Integer> nomineesReturned = nominatedTextSUT.getNomineeIndices();

        assertNotNull(nomineesReturned);
    }

    @Test
    public void
    test_ModifyingReturnedNomineesDoesNotAffectCopyHeldBySUT
            () throws Exception {
        SetUniqueList<Integer> returnedFirst = nominatedTextSUT.getNomineeIndices();
        returnedFirst.add(Integer.MAX_VALUE);
        SetUniqueList<Integer> returnedSecond = nominatedTextSUT.getNomineeIndices();
        assertNotEquals(returnedFirst, returnedSecond);
    }
}