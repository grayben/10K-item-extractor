package com.grayben.riskExtractor;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.jsoup.helper.Validate.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class RiskExtractorIT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
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
        List<String> args = new ArrayList<>();
        args.add("foo");
        args.add("bar");
        args.add("baz");
        thrown.expect(IllegalArgumentException.class);
        RiskExtractor.main(args.toArray(new String[args.size()]));
    }

    @Test
    public void test_MainThrowsFileNotFoundException_WhenInvalidFileArgument
            () throws Exception {
        List<String> args = new ArrayList<>();
        args.add("//////##$%foo");
        args.add("bar");
        thrown.expect(FileNotFoundException.class);
        RiskExtractor.main(args.toArray(new String[args.size()]));
    }

    @Ignore
    @Test
    public void test_MainCreatesExpectedOutputFile
            () throws Exception {
        String inputName = "input.csv";
        String outputName = "output.csv";
        fail("Test not written");

    }

    @Ignore
    @Test
    public void test_MainCreatesExpectedOutputFileContents_WhenEasyExample
            () throws Exception {
        fail("Test not written");
    }

    @Ignore
    @Test
    public void test_MainCreatesExpectedOutputFileContents_WhenComplicatedExample
            () throws Exception {
        fail("Test not written");
    }
}
