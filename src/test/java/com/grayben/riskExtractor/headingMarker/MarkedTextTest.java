package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.headingMarker.elector.ElectedTextList;
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

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class MarkedTextTest {

    @Mock
    ElectedTextList electedTextListMock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    MarkedText markedTextSUT;

    @Before
    public void setUp() throws Exception {
        markedTextSUT = new MarkedText(electedTextListMock);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_Init_ThrowsNullPointerException_WhenTextArgumentIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        ElectedTextList electedTextListArgument = null;
        markedTextSUT = new MarkedText(electedTextListArgument);
    }

    @Test
    public void test_SubSelections_ReturnNonNull_Always() throws Exception {
        List<String> subSelectionsReturned = markedTextSUT.subSelections();
        assertNotNull(subSelectionsReturned);
    }
}