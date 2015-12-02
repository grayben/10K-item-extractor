package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.nominator.NominatedTextTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

    @Override
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
    public void test_constructorAcceptsEqualLists()
        throws Exception {

        electedTextSUT = new ElectedText(
                stringListArgument,
                nomineesMock,
                electeesMock);

        assertNotNull(electedTextSUT);
    }
}