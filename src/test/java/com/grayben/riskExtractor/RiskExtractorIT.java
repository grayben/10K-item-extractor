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

    private void saveDefaultScoringAndFlatteningNodeVisitorTo(File outfile){
        ScoringAndFlatteningNodeVisitor nv = RiskExtractor.setupNodeVisitor();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outfile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(nv);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_MainCreatesExpectedOutputFileContents_WhenEasyExample
            () throws Exception {

        String resourcesRelativePath = "src/test/resources";
        // requires knowing charset
        String charsetName = "UTF-8";
        // requires input file
        String inputFileResourceRelativePath = resourcesRelativePath.concat("/simple.html");
        String inputFileCopyRelativePath = "easy.html";
        File inputFileResource = new File(inputFileResourceRelativePath);
        File inputFileCopy = folder.newFile(inputFileCopyRelativePath);
        FileUtils.copyFile(inputFileResource, inputFileCopy);
        String inputFileArgument = inputFileCopy.getAbsolutePath();

        // produces output file
        String actualOutputFileResourceRelativePath = "src/test/resources/easy.out.html";
        File actualOutputFileResource = new File(actualOutputFileResourceRelativePath);

        // requires expected output file
        File expectedOutputFile = folder.newFile();
        // ensure that the physical file doesn't exist before we run main
        //noinspection ResultOfMethodCallIgnored
        expectedOutputFile.delete();
        assert ! expectedOutputFile.exists();
        String outputFileArgument = expectedOutputFile.getAbsolutePath();

        // requires that scorer parameters are stored somewhere
        File nvFile = new File(RiskExtractor.scoringAndFlatteningNodeVisitorRelativePath);
        saveDefaultScoringAndFlatteningNodeVisitorTo(nvFile);

        // requires that extraction parameters are stored somewhere

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
