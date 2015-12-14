package com.grayben.riskExtractor.headingMarker.markedText;

import com.grayben.riskExtractor.headingMarker.MarkedText;
import com.grayben.riskExtractor.headingMarker.elector.ElectedText;
import com.grayben.riskExtractor.headingMarker.elector.ElectedTextTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class MarkedTextTest
    extends ElectedTextTest {


    //TODO: check setup methods

    MarkedText markedTextSUT;

    MarkedTextOracle oracle;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.markedTextSUT = new MarkedText(getElectedTextSUT(), new HashMap<>());
        this.oracle = new MarkedTextOracle(defaultClassifications());
    }

    private List<TextElementClass> defaultClassifications(){
        List<TextElementClass> list = new ArrayList<>();
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.ELECTED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.ELECTED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.ELECTED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);

        return list;
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void test_Init_ThrowsNullPointerException_WhenElectedTextArgumentIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        markedTextSUT = new MarkedText(null, new HashMap<>());
    }

    @Test
    public void test_SubSelections_ReturnNonNull_Always() throws Exception {
        Set<String> subSelectionsReturned = markedTextSUT.subSelections();
        assertNotNull(subSelectionsReturned);
    }

    @Test
    public void
    test_SubselectionReturnsNonNull_Normally
            () throws Exception {
        markedTextSUT = new MarkedText(oracle.getTestInput(), new HashMap<>());
        assertNotNull(markedTextSUT.subSelections());
    }

    @Test
    public void
    test_SubselectionsReturnsNonNull_WhenTextInputIsEmpty
            () throws Exception {
        List<TextElementClass> textElementClasses = new ArrayList();
        this.markedTextSUT = new MarkedText(
                new ElectedText(
                        new ArrayList<>(), nomineesArgument, electeesArgument),
                new HashMap<>()
        );
        oracle = new MarkedTextOracle(textElementClasses);
        markedTextSUT = new MarkedText(oracle.getTestInput(), new HashMap<>());
        assertNotNull("The returned object was null",
                markedTextSUT.subSelections());
    }

    @Test
    public void
    test_SubSelectionsReturnsExpectedOutput_WhenSimpleInput
            () throws Exception {

        ElectedText input = oracle.getTestInput();
        markedTextSUT = new MarkedText(input, new HashMap<>());
        Set<String> output = markedTextSUT.subSelections();
        Set<String> expectedOutput = oracle.getTestExpectedOutput();

        System.out.println("//////////////////////////");
        System.out.println("## OUTPUT ################");
        System.out.println(output);
        System.out.println();
        System.out.println("## EXPECTED OUTPUT #######");
        System.out.println(expectedOutput);

            assertTrue("The oracle determined that the expected " +
                "output was not valid", oracle.validateResult(output));
    }

    @Test
    public void test_SubselectionsReturnsExpectedOutput_WhenEmptyInput
            () throws Exception {
        List<TextElementClass> param = new ArrayList<>();
        oracle = new MarkedTextOracle(param);
        ElectedText input = oracle.getTestInput();
        markedTextSUT = new MarkedText(input, new HashMap<>());
        Set<String> output = markedTextSUT.subSelections();

        assertTrue(oracle.validateResult(output));
    }
}