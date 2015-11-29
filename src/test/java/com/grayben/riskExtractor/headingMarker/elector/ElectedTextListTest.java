package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.TextCandidate;
import com.grayben.riskExtractor.headingMarker.nominator.NominatedTextListTest;
import org.junit.After;
import org.junit.Before;
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
public class ElectedTextListTest
        extends
        NominatedTextListTest
        implements
        IElecteesRetrievableTest {

    ElectedTextList electedTextListSUT;

    @Mock
    protected List<String> stringListMock;

    @Mock
    protected List<TextCandidate> nomineesMock;

    @Mock
    protected List<TextCandidate> electeesMock;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        electedTextListSUT = new ElectedTextList(stringListMock, nomineesMock, electeesMock);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    @Test
    public void test_GetElectees_ReturnsNonNull_Always() throws Exception {
        //setup data

        //setup expectations

        //exercise
        List<TextCandidate> electeesReturned = electedTextListSUT.getElectees();
        //verify
        assertNotNull(electeesReturned);
    }

    @Test
    public void test_GetElectees_ReturnsConstructedElectees_OnlyWhenNoOverwrite() throws Exception {
        //setup data
        List<TextCandidate> newElecteesMock = (List<TextCandidate>) mock(List.class);

        //setup expectations

        //exercise
        List<TextCandidate> electeesReturned = electedTextListSUT.getElectees();
        electedTextListSUT.setElectees(newElecteesMock);
        List<TextCandidate> newElecteesReturned = electedTextListSUT.getElectees();

        //verify
        assertEquals(electeesMock, electeesReturned);
        assertEquals(newElecteesMock, newElecteesReturned);
        assertNotEquals(electeesMock, newElecteesReturned);
    }
}