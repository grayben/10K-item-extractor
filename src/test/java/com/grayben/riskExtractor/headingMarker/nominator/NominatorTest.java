package com.grayben.riskExtractor.headingMarker.nominator;

import com.grayben.riskExtractor.headingMarker.Nominator;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.fail;

/**
 * Created by beng on 5/06/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class NominatorTest {

    Nominator nominatorSUT;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        nominatorSUT = new Nominator(x -> true);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_InitThrowsNullPointerException_WhenPredicateIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        nominatorSUT = new Nominator(null);
    }

    @Test
    public void test_NominateThrowsNullPointerException_WhenScoredTextIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        nominatorSUT.nominate(null);
    }

    @Test
    @Ignore
    public void test_NominateReturnsExpectedResult
            () throws Exception {
        fail("Not implemented");
    }

}