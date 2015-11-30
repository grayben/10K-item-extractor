package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.TextCandidate;
import com.grayben.riskExtractor.headingMarker.TextCandidates;
import com.grayben.riskExtractor.headingMarker.nominator.NominatedTextListTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
    protected TextCandidates nomineesMock;

    @Mock
    protected TextCandidates electeesMock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        when(nomineesMock.getTextList()).thenReturn(stringListMock);
        when(electeesMock.getTextList()).thenReturn(stringListMock);
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
        TextCandidates electeesReturned = electedTextListSUT.getElectees();
        //verify
        assertNotNull(electeesReturned);
    }

    @Test
    public void test_GetElectees_ReturnsConstructedElectees_OnlyWhenNoOverwrite() throws Exception {
        //setup data
        TextCandidates newElecteesMock = mock(TextCandidates.class);

        //setup expectations

        //exercise
        TextCandidates electeesReturned = electedTextListSUT.getElectees();
        electedTextListSUT.setElectees(newElecteesMock);
        TextCandidates newElecteesReturned = electedTextListSUT.getElectees();

        //verify
        assertEquals(electeesMock, electeesReturned);
        assertEquals(newElecteesMock, newElecteesReturned);
        assertNotEquals(electeesMock, newElecteesReturned);
    }

    @Test
    public void test_constructorAcceptsEqualLists(){
        electedTextListSUT = new ElectedTextList(
                stringListMock,
                nomineesMock,
                electeesMock);

        assertNotNull(electedTextListSUT);
    }

    @Test
    public void test_constructorDoesNotAcceptUnequalLists(){
        List<String> otherTextListMock = mock(List.class);
        when(nomineesMock.getTextList()).thenReturn(otherTextListMock);

        thrown.expect(IllegalArgumentException.class);

        electedTextListSUT = new ElectedTextList(stringListMock, nomineesMock, electeesMock);
    }
}