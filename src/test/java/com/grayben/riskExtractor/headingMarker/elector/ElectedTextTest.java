package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.nominator.NominatedText;
import com.grayben.riskExtractor.headingMarker.nominator.NominatedTextTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElectedTextTest
        extends
        NominatedTextTest
        implements
        IElecteesRetrievableTest {

    private ElectedText electedTextSUT;

    public NominatedText getElectedTextSUT() {
        return electedTextSUT;
    }

    public void setElectedTextSUT(ElectedText electedTextSUT) {
        this.electedTextSUT = electedTextSUT;
        this.setNominatedTextSUT(this.electedTextSUT);
    }

    @Mock
    private List<Integer> electeesMock;

    @Before
    final public void setUpElectedText() throws Exception {
        this.setElectedTextSUT(
                new ElectedText(
                        stringListArgument,
                        nomineesMock,
                        electeesMock
                )
        );
    }

    @After
    final public void tearDownElectedText() throws Exception {
    }

    @Test
    public void test_GetElectees_ReturnsNonNull_Always() throws Exception {
        //setup data

        //setup expectations

        //exercise
        List<Integer> electeesReturned = electedTextSUT.getElectees();
        //verify
        assertNotNull(electeesReturned);
    }

    @Test
    @Override
    public void
    test_BasicConstructorThrowsNullPointerException_WhenStringListIsNull
            () throws Exception {
        stringListArgument = null;

        thrown.expect(NullPointerException.class);

        new ElectedText(stringListArgument, nomineesMock, electeesMock);
    }

    @Test
    @Override
    public void
    test_BasicConstructorThrowsNullPointerException_WhenNomineesIsNull
            () throws Exception {
        nomineesMock = null;

        thrown.expect(NullPointerException.class);

        new ElectedText(stringListArgument, nomineesMock, electeesMock);
    }

    @Test
    public void
    test_BasicConstructorThrowsNullPointerException_WhenElecteesIsNull
            () throws Exception {
        electeesMock = null;

        thrown.expect(NullPointerException.class);

        new ElectedText(stringListArgument, nomineesMock, electeesMock);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenNominatedTextIsNull
            () throws Exception {
        this.setNominatedTextSUT(null);

        thrown.expect(NullPointerException.class);

        new ElectedText(getNominatedTextSUT(), electeesMock);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenElecteesIsNull
            () throws Exception {
        this.electeesMock = null;

        thrown.expect(NullPointerException.class);

        new ElectedText(getNominatedTextSUT(), electeesMock);
    }

    @Test
    @Override
    public void
    test_PrototypeConstructorThrowsNullPointerException_WhenPrototypeIsNull
            () throws Exception {
        this.setElectedTextSUT(null);

        thrown.expect(NullPointerException.class);

        new ElectedText(electedTextSUT);
    }


}