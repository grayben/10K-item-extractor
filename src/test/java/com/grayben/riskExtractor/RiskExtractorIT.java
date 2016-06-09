package com.grayben.riskExtractor;

import com.grayben.riskExtractor.htmlScorer.ScoringAndFlatteningNodeVisitor;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
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
        // requires knowing charset
        String charsetName = null;
        // requires input file
        String inputFileResourceAbsolutePath = null;
        String inputFileCopyRelativePath = null;
        File inputFileResource = new File(inputFileResourceAbsolutePath);
        File inputFileCopy = folder.newFile(inputFileCopyRelativePath);
        FileUtils.copyFile(inputFileResource, inputFileCopy);
        String inputFileArgument = inputFileCopy.getAbsolutePath();

        // produces output file
        String actualOutputFileResourceAbsolutePath = null;
        File actualOutputFileResource = new File(actualOutputFileResourceAbsolutePath);

        // requires expected output file
        File expectedOutputFile = folder.newFile();
        // ensure that the physical file doesn't exist before we run main
        expectedOutputFile.delete();
        assert ! expectedOutputFile.exists();
        String outputFileArgument = expectedOutputFile.getAbsolutePath();

        // requires that scorer parameters are stored somewhere
        ScoringAndFlatteningNodeVisitor nv = null;

        // requires that extraction parameters are stored somewhere

        // set working directory to temp dir (deleted on test finish)
        // copy relevant files into temp dir
            // input file
            // system files

        // construct String[] args
        List<String> argsList = new ArrayList<>();
        argsList.add(inputFileArgument);
        argsList.add(charsetName);
        argsList.add(outputFileArgument);
        String[] args = argsList.toArray(new String[argsList.size()]);

        // run main
            // loads parameters from system files
            // takes input from input file(s)
            // creates output file(s)
        RiskExtractor.main(args);

        // load actualOutput from newly created output file
        String actualOutput = FileUtils.readFileToString(actualOutputFileResource);

        // load expectedOutput from file in resources
        String expectedOutput = FileUtils.readFileToString(expectedOutputFile);

        assertEquals(expectedOutput, actualOutput);

        fail("Test not written");
    }

    @Ignore
    @Test
    public void test_MainCreatesExpectedOutputFileContents_WhenComplicatedExample
            () throws Exception {
        fail("Test not written");
    }
}
