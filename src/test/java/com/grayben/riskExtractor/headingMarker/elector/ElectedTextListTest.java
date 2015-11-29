package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.TextCandidate;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
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
        List<TextCandidate> newNomineesMock = (List<TextCandidate>) Mockito.mock(List.class);

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
    public void test_GetElectees_ReturnsNonNull() throws Exception {
        //setup data

        //setup expectations

        //exercise

        //verify
    }
}