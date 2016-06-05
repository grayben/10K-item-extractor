package com.grayben.riskExtractor.headingMarker.markedText;

import com.grayben.riskExtractor.headingMarker.Elector;
import com.grayben.riskExtractor.headingMarker.Marker;
import com.grayben.riskExtractor.headingMarker.elector.ElectedTextTest;
import com.grayben.riskExtractor.helpers.TextElementClassListDefaults;
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
public class MarkerTest
    extends ElectedTextTest {


    //TODO: check setup methods

    private Marker markerSUT;

    private com.grayben.riskExtractor.headingMarker.markedText.MarkedTextOracle oracle;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.markerSUT = new Marker(getElectedTextSUT());
        this.oracle = new MarkedTextOracle(TextElementClassListDefaults.defaultClassifications());
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void
    test_Init_ThrowsNullPointerException_WhenElectedTextArgumentIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);

        markerSUT = new Marker(null);
    }

    @Test
    public void
    test_SubSelections_ReturnNonNull_Always
             () throws Exception {
        Set<String> subSelectionsReturned = markerSUT.subSelections();

        assertNotNull(subSelectionsReturned);
    }

    @Test
    public void
    test_SubselectionReturnsNonNull_Normally
            () throws Exception {
        markerSUT = new Marker(oracle.getTestInput());

        assertNotNull(markerSUT.subSelections());
    }

    @Test
    public void
    test_SubselectionsReturnsNonNull_WhenTextInputIsEmpty
            () throws Exception {
        List<TextElementClass> textElementClasses = new ArrayList<>();
        this.markerSUT = new Marker(
                new Elector.ElectedText(
                        new ArrayList<>(), nomineesArgument, electeesArgument)
        );
        oracle = new MarkedTextOracle(textElementClasses);

        markerSUT = new Marker(oracle.getTestInput());

        assertNotNull("The returned object was null",
                markerSUT.subSelections());
    }

    @Test
    public void
    test_SubSelectionsReturnsExpectedOutput_WhenSimpleInput
            () throws Exception {
        oracle = new MarkedTextOracle(TextElementClassListDefaults.defaultClassifications());
        Elector.ElectedText input = oracle.getTestInput();
        markerSUT = new Marker(input);

        Set<String> output = markerSUT.subSelections();

            assertTrue("The oracle determined that the expected " +
                "output was not valid", oracle.validateResult(output));
    }

    @Test
    public void
    test_SubselectionsReturnsExpectedOutput_WhenEmptyInput
            () throws Exception {
        List<TextElementClass> param = TextElementClassListDefaults.emptyList();
        oracle = new MarkedTextOracle(param);
        Elector.ElectedText input = oracle.getTestInput();
        markerSUT = new Marker(input);

        Set<String> output = markerSUT.subSelections();

        assertTrue(oracle.validateResult(output));
    }

    @Test
    public void
    test_SubselectionsReturnsExpectedOutput_WhenNoHeadings
            () throws Exception {
        List<TextElementClass> param = TextElementClassListDefaults.noHeadingList();
        oracle = new MarkedTextOracle(param);
        Elector.ElectedText input = oracle.getTestInput();
        markerSUT = new Marker(input);

        Set<String> output = markerSUT.subSelections();

        assertTrue(oracle.validateResult(output));

    }

    @Test
    public void
    test_SubselectionsReturnsExpectedOutput_WhenNoElectedHeadingList
            () throws Exception {
        List<TextElementClass> param = TextElementClassListDefaults.noElectedHeadingList();
        oracle = new MarkedTextOracle(param);
        Elector.ElectedText input = oracle.getTestInput();
        markerSUT = new Marker(input);

        Set<String> output = markerSUT.subSelections();

        assertTrue(oracle.validateResult(output));

    }

    @Test
    public void
    test_SubselectionsReturnsExpectedOutput_WhenSolelyTargetTextList
            () throws Exception {
        List<TextElementClass> param = TextElementClassListDefaults.solelyTargetTextList();
        oracle = new MarkedTextOracle(param);
        Elector.ElectedText input = oracle.getTestInput();
        markerSUT = new Marker(input);

        Set<String> output = markerSUT.subSelections();

        assertTrue(oracle.validateResult(output));

    }

    @Test
    public void
    test_SubselectionsReturnsExpectedOutput_WhenElectedHeadingOnlyAtEndList
            () throws Exception {
        List<TextElementClass> param = TextElementClassListDefaults.electedHeadingOnlyAtEndList();
        oracle = new MarkedTextOracle(param);
        Elector.ElectedText input = oracle.getTestInput();
        markerSUT = new Marker(input);

        Set<String> output = markerSUT.subSelections();

        assertTrue(oracle.validateResult(output));

    }


}