package com.grayben.riskExtractor.headingMarker.nominator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        implements
        INomineesRetrievableTest {

    NominatedText nominatedTextSUT;

    @Mock
    protected List<String> stringListMock;

    @Mock
    protected List<Integer> nomineesMock;

    @Before
    public void setUp() throws Exception {
        nominatedTextSUT = new NominatedText(stringListMock, nomineesMock);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Override
    @Test
    public void test_GetNominees_ReturnsNonNull_Always() throws Exception {
        //setup data

        //setup expectations

        //execute
        List<Integer> nomineesReturned = nominatedTextSUT.getNominees();

        //validate
        assertNotNull(nomineesReturned);
    }
}