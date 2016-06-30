package com.grayben.riskExtractor;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class RiskExtractorIT {

    private static String resourcesRelativePath = "src/test/resources";

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
        args.add("UTF-8");
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

    @Test
    public void test_MainCreatesExpectedOutputFileContents_WhenEasyExample
            () throws Exception {

        // requires knowing charset
        String charsetName = "UTF-8";
        // requires input file
        String inputFileCopyRelativePath = "easy.html";
        String inputFileResourceRelativePath = resourcesRelativePath.concat("/").concat(inputFileCopyRelativePath);

        // produces output file
        String targetOutputFileResourceRelativePath = "easy.out.txt";
        File inputFileResource = new File(inputFileResourceRelativePath);
        File inputFileCopy = folder.newFile(inputFileCopyRelativePath);
        FileUtils.copyFile(inputFileResource, inputFileCopy);
        String inputFileArgument = inputFileCopy.getAbsolutePath();

        String expectedOutputFileRelativePath = resourcesRelativePath.concat("/").concat(targetOutputFileResourceRelativePath);
        File expectedOutputFile = new File(expectedOutputFileRelativePath);

        // requires expected output file
        File targetOutputFile = folder.newFile(targetOutputFileResourceRelativePath);
        // ensure that the physical file doesn't exist before we run main
        //noinspection ResultOfMethodCallIgnored
        targetOutputFile.delete();
        assert ! targetOutputFile.exists();
        String outputFileArgument = targetOutputFile.getAbsolutePath();

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
        String actualOutput = FileUtils.readFileToString(targetOutputFile);

        // load expectedOutput from file in resources
        String expectedOutput = FileUtils.readFileToString(expectedOutputFile);

        assertEquals(expectedOutput.trim(), actualOutput.trim());
    }



    @Test
    public void test_MainCreatesEmptyOutputFileContents_WhenNoTargetSection
            () throws Exception {

        // requires knowing charset
        String charsetName = "UTF-8";
        // requires input file
        String inputFileCopyRelativePath = "no-target-heading.html";
        String inputFileResourceRelativePath = resourcesRelativePath.concat("/").concat(inputFileCopyRelativePath);
        // produces output file
        String targetOutputFileResourceRelativePath = "no-target-heading.out.txt";
        File inputFileResource = new File(inputFileResourceRelativePath);
        File inputFileCopy = folder.newFile(inputFileCopyRelativePath);
        FileUtils.copyFile(inputFileResource, inputFileCopy);
        String inputFileArgument = inputFileCopy.getAbsolutePath();

        String expectedOutputFileRelativePath = resourcesRelativePath.concat("/").concat(targetOutputFileResourceRelativePath);
        File expectedOutputFile = new File(expectedOutputFileRelativePath);


        // requires expected output file
        File targetOutputFile = folder.newFile(targetOutputFileResourceRelativePath);
        // ensure that the physical file doesn't exist before we run main
        //noinspection ResultOfMethodCallIgnored
        targetOutputFile.delete();
        assert ! targetOutputFile.exists();
        String outputFileArgument = targetOutputFile.getAbsolutePath();

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
        String actualOutput = FileUtils.readFileToString(targetOutputFile);

        // load expectedOutput from file in resources
        String expectedOutput = FileUtils.readFileToString(expectedOutputFile);

        assertEquals(expectedOutput.trim(), actualOutput.trim());
    }

    @Test
    public void test_MainCreatesExpectedOutputFileContents_ForAAPL
            () throws Exception {

        // requires knowing charset
        String charsetName = "UTF-8";
        // requires input file
        String inputFileCopyRelativePath = "AAPL_10-K.htm";
        String inputFileResourceRelativePath = resourcesRelativePath.concat("/").concat(inputFileCopyRelativePath);
        File inputFileResource = new File(inputFileResourceRelativePath);
        File inputFileCopy = folder.newFile(inputFileCopyRelativePath);
        FileUtils.copyFile(inputFileResource, inputFileCopy);
        String inputFileArgument = inputFileCopy.getAbsolutePath();

        // produces output file
        String targetOutputFileResourceRelativePath = "AAPL_10-K.out.txt";

        String expectedOutputFileRelativePath = resourcesRelativePath.concat("/").concat(targetOutputFileResourceRelativePath);
        File expectedOutputFile = new File(expectedOutputFileRelativePath);


        // requires expected output file
        File targetOutputFile = folder.newFile(targetOutputFileResourceRelativePath);
        // ensure that the physical file doesn't exist before we run main
        //noinspection ResultOfMethodCallIgnored
        targetOutputFile.delete();
        assert ! targetOutputFile.exists();
        String outputFileArgument = targetOutputFile.getAbsolutePath();

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
        String actualOutput = FileUtils.readFileToString(targetOutputFile);

        // load expectedOutput from file in resources
        String expectedOutput = FileUtils.readFileToString(expectedOutputFile);
        assertFalse(isSubsetByWordsStripPunctuationNewlinesIgnoreCase("the quick brown fox fox", "the quick brown fox"));
        assertTrue("actual output must contain expected output words", isSubsetByWordsStripPunctuationNewlinesIgnoreCase(expectedOutput, actualOutput));
    }

    @Test
    public void test_MainCreatesExpectedOutputFileContents_ForGOOG
            () throws Exception {

        // requires knowing charset
        String charsetName = "UTF-8";
        // requires input file
        String inputFileCopyRelativePath = "GOOG_10-K.htm";
        String inputFileResourceRelativePath = resourcesRelativePath.concat("/").concat(inputFileCopyRelativePath);
        File inputFileResource = new File(inputFileResourceRelativePath);
        File inputFileCopy = folder.newFile(inputFileCopyRelativePath);
        FileUtils.copyFile(inputFileResource, inputFileCopy);
        String inputFileArgument = inputFileCopy.getAbsolutePath();

        // produces output file
        String targetOutputFileResourceRelativePath = "GOOG_10-K.out.txt";

        String expectedOutputFileRelativePath = resourcesRelativePath.concat("/").concat(targetOutputFileResourceRelativePath);
        File expectedOutputFile = new File(expectedOutputFileRelativePath);


        // requires expected output file
        File targetOutputFile = folder.newFile(targetOutputFileResourceRelativePath);
        // ensure that the physical file doesn't exist before we run main
        //noinspection ResultOfMethodCallIgnored
        targetOutputFile.delete();
        assert ! targetOutputFile.exists();
        String outputFileArgument = targetOutputFile.getAbsolutePath();

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
        String actualOutput = FileUtils.readFileToString(targetOutputFile);

        // load expectedOutput from file in resources
        String expectedOutput = FileUtils.readFileToString(expectedOutputFile);
        assertFalse(isSubsetByWordsStripPunctuationNewlinesIgnoreCase("the quick brown fox fox", "the quick brown fox"));
        // assertEquals(expectedOutput, actualOutput);
        assertTrue("actual output must contain expected output words", isSubsetByWordsStripPunctuationNewlinesIgnoreCase(expectedOutput, actualOutput));
    }

    public static boolean isSubsetByWordsStripPunctuationNewlinesIgnoreCase(String subset, String superset) {
        String[] subsetWords = subset.replaceAll("\\p{P}", "").replaceAll("\\p{Space}", " ").replaceAll("\\p{Z}", " ").toLowerCase().trim().split(" ");
        String[] supersetWords = superset.replaceAll("\\p{P}", "").replaceAll("\\p{Space}", " ").replaceAll("\\p{Z}", " ").toLowerCase().trim().split(" ");

        Map<String, Integer> subsetWordCounter = counter(subsetWords);
        Map<String, Integer> supersetWordCounter = counter(supersetWords);

        for (String subsetWord:
             subsetWordCounter.keySet()) {
            Integer subsetCount = subsetWordCounter.get(subsetWord);
            Integer supersetCount = supersetWordCounter.get(subsetWord);

            // if the superset doesn't contain the word, or if it contains less instances
            // then the subset-superset relation is false
            if (supersetCount == null || supersetCount < subsetCount) {
                return false;
            }
        }
        // if we haven't proven otherwise by now, the relationship holds
        return true;
    }

    public static <T> Map<T, Integer> counter(T[] instances){
        Map<T, Integer> counter = new HashMap<>();
        for (T instance : instances) {
            if (counter.containsKey(instance)) {
                int oldCount = counter.get(instance);
                counter.put(instance, oldCount + 1);
            } else {
                counter.put(instance, 1);
            }
        }
        return counter;

    }
}
