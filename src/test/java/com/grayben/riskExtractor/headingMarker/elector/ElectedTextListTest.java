package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.TextCandidate;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElectedTextListTest {

    ElectedTextList electedTextList;

    @Mock
    protected List<String> stringListMock;

    @Mock
    protected List<TextCandidate> nomineesMock;

    @Mock
    protected List<TextCandidate> electeesMock;

    @Before
    public void setUp() throws Exception {
        electedTextList = new ElectedTextList(stringListMock, nomineesMock, electeesMock);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_GetNominees_ReturnsNonNull_Always() throws Exception {
        //setup data

        //setup expectations

        //exercise
        List<TextCandidate> nominees = electedTextList.getNominees();
        //verify
        assertNotNull(nominees);
    }

    @Test
    public void test_GetNominees_ReturnsConstructedNominees_OnlyWhenNoOverwrite() throws Exception {
        //setup data
        List<TextCandidate> newNomineesMock = (List<TextCandidate>) mock(List.class);

        //setup expectations

        //exercise
        List<TextCandidate> nomineesReturned = electedTextList.getNominees();
        electedTextList.setNominees(newNomineesMock);
        List<TextCandidate> newNomineesReturned = electedTextList.getNominees();

        //verify
        assertEquals(nomineesMock, nomineesReturned);
        assertEquals(newNomineesMock, newNomineesReturned);
        assertNotEquals(nomineesMock, newNomineesReturned);
    }

    @Test
    public void test_GetElectees_ReturnsNonNull_Always() throws Exception {
        //setup data

        //setup expectations

        //exercise
        List<TextCandidate> electees = electedTextList.getElectees();
        //verify
        assertNotNull(electees);
    }

    @Test
    public void test_GetElectees_ReturnsConstructedElectees_OnlyWhenNoOverwrite() throws Exception {
        //setup data
        List<TextCandidate> newElecteesMock = (List<TextCandidate>) mock(List.class);

        //setup expectations

        //exercise
        List<TextCandidate> electeesReturned = electedTextList.getElectees();
        electedTextList.setElectees(newElecteesMock);
        List<TextCandidate> newElecteesReturned = electedTextList.getElectees();

        //verify
        assertEquals(electeesMock, electeesReturned);
        assertEquals(newElecteesMock, newElecteesReturned);
        assertNotEquals(electeesMock, newElecteesReturned);
    }
}