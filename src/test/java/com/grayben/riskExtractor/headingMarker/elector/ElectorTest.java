package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.Elector;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by beng on 4/06/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElectorTest {

    private Elector electorSUT;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_InitThrowsNullPointerException_WhenFunctionIsNull() throws Exception {
        thrown.expect(NullPointerException.class);
        electorSUT = new Elector(null);
    }



}