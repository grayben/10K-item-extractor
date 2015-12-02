package com.grayben.riskExtractor.headingMarker.nominator;

import com.grayben.riskExtractor.headingMarker.UnmodifiableText;
import com.grayben.riskExtractor.headingMarker.UnmodifiableTextTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

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

    @Mock
    protected List<Integer> nomineesMock = null;

    @Before
    final public void setUpNominatedText() throws Exception {
        this.setNominatedTextSUT(
                new NominatedText(stringListArgument, nomineesMock)
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

        new NominatedText(stringListArgument, nomineesMock);
    }

    @Test
    public void
    test_BasicConstructorThrowsNullPointerException_WhenNomineesIsNull
            () throws Exception {
        nomineesMock = null;

        thrown.expect(NullPointerException.class);

        new NominatedText(stringListArgument, nomineesMock);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenUnmodifiableTextIsNull
            () throws Exception {
        this.setNominatedTextSUT(null);

        thrown.expect(NullPointerException.class);

        new NominatedText(this.getUnmodifiableTextSUT(), nomineesMock);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenNomineesIsNull
            () throws Exception {
        nomineesMock = null;

        thrown.expect(NullPointerException.class);

        new NominatedText(this.getUnmodifiableTextSUT(), nomineesMock);
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

        List<Integer> nomineesReturned = nominatedTextSUT.getNominees();

        assertNotNull(nomineesReturned);
    }

    @Test
    public void
    test_ModifyingReturnedNomineesThrowsUnsupportedOperationException
            () throws Exception {
        List<Integer> returnedIntegerList = nominatedTextSUT.getNominees();

        thrown.expect(UnsupportedOperationException.class);

        returnedIntegerList.add(1);
    }
}