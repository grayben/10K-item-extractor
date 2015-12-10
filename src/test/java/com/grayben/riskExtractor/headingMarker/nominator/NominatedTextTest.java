package com.grayben.riskExtractor.headingMarker.nominator;

import com.grayben.riskExtractor.headingMarker.UnmodifiableTextTest;
import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class NominatedTextTest
            extends UnmodifiableTextTest
        implements
        INomineesRetrievableTest {

    private NominatedText nominatedTextSUT = null;

    public NominatedText getNominatedTextSUT() {
        return nominatedTextSUT;
    }

    public void setNominatedTextSUT(NominatedText nominatedTextSUT) {
        this.nominatedTextSUT = nominatedTextSUT;
        this.setUnmodifiableTextSUT(this.nominatedTextSUT);
    }

    protected SetUniqueList<Integer> nomineesArgument = null;

    @Before
    @Override
    public void setUp() throws Exception {
        this.stringListArgument = new ArrayList<>();
        this.stringListArgument.add("one");
        this.stringListArgument.add("two");
        this.stringListArgument.add("cow");

        this.nomineesArgument = SetUniqueList.setUniqueList(new ArrayList<>());
        this.nomineesArgument.add(0);
        this.nomineesArgument.add(2);

        this.setNominatedTextSUT(
                new NominatedText(stringListArgument, nomineesArgument)
        );
    }

    @After
    final public void tearDownNominatedText() throws Exception {
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

        SetUniqueList<Integer> nomineesReturned = nominatedTextSUT.getNominees();

        assertNotNull(nomineesReturned);
    }

    @Test
    public void
    test_ModifyingReturnedNomineesThrowsUnsupportedOperationException
            () throws Exception {
        SetUniqueList<Integer> returnedIntegerList = nominatedTextSUT.getNominees();

        thrown.expect(UnsupportedOperationException.class);

        returnedIntegerList.add(1);
    }
}