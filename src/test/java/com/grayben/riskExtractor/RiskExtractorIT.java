package com.grayben.riskExtractor;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.jsoup.helper.Validate.fail;
import static org.junit.Assert.*;

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
    public void test_MainThrowsIllegalArgumentException_WhenTwoArgument
            () throws Exception {
        List<String> args = new ArrayList<>();
        args.add("foo");
        args.add("bar");
        thrown.expect(IllegalArgumentException.class);
        RiskExtractor.main(args.toArray(new String[args.size()]));
    }

    @Test
    public void test_MainThrowsIllegalArgumentException_WhenFourArguments
            () throws Exception {
        List<String> args = new ArrayList<>();
        args.add("foo");
        args.add("bar");
        args.add("baz");
        args.add("buzz");
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

    @Test
    public void test_MainCreatesExpectedOutputFile
            () throws Exception {
        String inputName = "input.csv";
        String charsetName = "UTF-8";
        String outputName = "output.csv";
        File tempFolder = folder.newFolder();
        File inputFile = folder.newFile(inputName);
        File outputFileToBeCreated = new File(tempFolder.getAbsolutePath().concat("/").concat(outputName));

        List<String> argsList = new ArrayList<>();
        argsList.add(inputFile.getAbsolutePath());
        argsList.add(charsetName);
        argsList.add(outputFileToBeCreated.getAbsolutePath());

        assertFalse(outputFileToBeCreated.exists());
        RiskExtractor.main(argsList.toArray(new String[argsList.size()]));
        assertTrue(outputFileToBeCreated.exists());
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
