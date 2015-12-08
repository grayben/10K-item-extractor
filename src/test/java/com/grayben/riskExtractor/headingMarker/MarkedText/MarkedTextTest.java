package com.grayben.riskExtractor.headingMarker.markedText;

import com.grayben.riskExtractor.headingMarker.MarkedText;
import com.grayben.riskExtractor.headingMarker.elector.ElectedText;
import com.grayben.riskExtractor.headingMarker.elector.ElectedTextTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class MarkedTextTest
    extends ElectedTextTest {


    //TODO: check setup methods

    @Mock
    ElectedText electedTextListMock;

    MarkedText markedTextSUT;

    @Before
    public void setUp() throws Exception {
        markedTextSUT = new MarkedText(electedTextListMock);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_Init_ThrowsNullPointerException_WhenTextArgumentIsNull
            () throws Exception {
        thrown.expect(NullPointerException.class);
        ElectedText electedTextListArgument = null;
        markedTextSUT = new MarkedText(electedTextListArgument);
    }

    @Test
    public void test_SubSelections_ReturnNonNull_Always() throws Exception {
        Collection<String> subSelectionsReturned = markedTextSUT.subSelections();
        assertNotNull(subSelectionsReturned);
    }

    private void
    setupElectedTextList(){
        //define test data output



    }


    @Test
    public void
    test_SubSelectionsReturnsExpectedOutput_Simple
            () throws Exception {

        MarkedTextOracle oracle = new MarkedTextOracle();
        ElectedText input = oracle.getTestInput();
        markedTextSUT = new MarkedText(input);
        List<String> output = markedTextSUT.subSelections();

        System.out.println("//////////////////////////");
        System.out.println("## OUTPUT ################");
        System.out.println(output.toString());
        System.out.println();
        System.out.println("## EXPECTED OUTPUT #######");
        System.out.println(oracle.getTestExpectedOutput());
        System.out.println();
        System.out.println("## ORACLE STATE #########");
        oracle.printData();
        System.out.println("//////////////////////////");

        assertTrue("The oracle determined that the expected " +
                "output was not valid", oracle.validateResult(output));
    }
}