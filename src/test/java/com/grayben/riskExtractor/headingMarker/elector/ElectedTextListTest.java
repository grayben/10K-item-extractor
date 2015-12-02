package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.nominator.NominatedTextTest;
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
        NominatedTextTest
        implements
        IElecteesRetrievableTest {

    ElectedText electedTextListSUT;

    @Mock
    protected List<String> stringListMock;

    @Mock
    protected List<Integer> nomineesMock;

    @Mock
    protected List<Integer> electeesMock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        electedTextListSUT = new ElectedText(stringListMock, nomineesMock, electeesMock);

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
        List<Integer> electeesReturned = electedTextListSUT.getElectees();
        //verify
        assertNotNull(electeesReturned);
    }

    @Test
    public void test_constructorAcceptsEqualLists()
        throws Exception {

        electedTextListSUT = new ElectedText(
                stringListMock,
                nomineesMock,
                electeesMock);

        assertNotNull(electedTextListSUT);
    }
}