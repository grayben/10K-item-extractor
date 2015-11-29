package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.headingMarker.elector.ElectedTextList;
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
public class MarkedTextTest {

    @Mock
    ElectedTextList electedTextListMock;

    MarkedText markedText;
    @Before
    public void setUp() throws Exception {
        markedText = new MarkedText(electedTextListMock);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_Subselections_ReturnNonNull_Always() throws Exception {
        List<String> subselections = markedText.subselections();
        assertNotNull(subselections);
    }
}