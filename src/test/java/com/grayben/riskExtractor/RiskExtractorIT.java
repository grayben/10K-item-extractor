package com.grayben.riskExtractor;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.jsoup.helper.Validate.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class RiskExtractorIT {

    private RiskExtractor riskExtractorSUT;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        riskExtractorSUT = new RiskExtractor();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_MainThrowsIllegalArgumentException_WhenOneArgument
            () throws Exception {
        List<String> args = new ArrayList<>();
        args.add("foo");
        thrown.expect(IllegalArgumentException.class);
        RiskExtractor.main(args.toArray(new String[args.size()]));
    }

    @Test
    public void test_MainThrowsIllegalArgumentException_WhenThreeArguments
            () throws Exception {
        fail("Test not written");
    }

    @Test
    public void test_MainThrowsFileNotFoundException_WhenInvalidFileArgument
            () throws Exception {
        fail("Test not written");
    }

    @Test
    public void test_MainCreatesExpectedOutputFile
            () throws Exception {
        fail("Test not written");
    }

    @Test
    public void test_MainCreatesExpectedOutputFileContents_WhenEasyExample
            () throws Exception {
        fail("Test not written");
    }

    @Test
    public void test_MainCreatesExpectedOutputFileContents_WhenComplicatedExample
            () throws Exception {
        fail("Test not written");
    }
}
