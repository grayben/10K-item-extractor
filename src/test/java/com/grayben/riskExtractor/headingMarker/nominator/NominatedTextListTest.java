package com.grayben.riskExtractor.headingMarker.nominator;

import com.grayben.riskExtractor.headingMarker.TextCandidate;
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
public class NominatedTextListTest
        implements
        INomineesRetrievableTest {

    NominatedTextList nominatedTextListSUT;

    @Mock
    protected List<String> stringListMock;

    @Mock
    protected List<TextCandidate> nomineesMock;

    @Before
    public void setUp() throws Exception {
        nominatedTextListSUT = new NominatedTextList(stringListMock, nomineesMock);
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
        List<TextCandidate> nomineesReturned = nominatedTextListSUT.getNominees();

        //validate
        assertNotNull(nomineesReturned);
    }

    @Test
    public void test_GetNominees_ReturnsConstructedNominees_OnlyWhenNoOverwrite() throws Exception {
        //setup data
        List<TextCandidate> newNomineesMock = (List<TextCandidate>) mock(List.class);

        //setup expectations

        //exercise
        List<TextCandidate> nomineesReturned = nominatedTextListSUT.getNominees();
        nominatedTextListSUT.setNominees(newNomineesMock);
        List<TextCandidate> newNomineesReturned = nominatedTextListSUT.getNominees();

        //verify
        assertEquals(nomineesMock, nomineesReturned);
        assertEquals(newNomineesMock, newNomineesReturned);
        assertNotEquals(nomineesMock, newNomineesReturned);
    }
}