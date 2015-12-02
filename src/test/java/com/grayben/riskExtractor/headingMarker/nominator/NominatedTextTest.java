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

    NominatedText nominatedTextSUT = null;

    @Mock
    protected List<Integer> nomineesMock = null;

    @Before
    public void setUpNominatedText() throws Exception {
        assertNotNull (stringListArgument);
        this.nominatedTextSUT = new NominatedText(stringListArgument, nomineesMock);
        super.unmodifiableTextSUT = this.nominatedTextSUT;
    }

    @After
    public void tearDownNominatedText() throws Exception {
        nominatedTextSUT = null;
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