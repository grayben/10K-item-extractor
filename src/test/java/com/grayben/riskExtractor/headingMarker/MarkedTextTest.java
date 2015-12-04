package com.grayben.riskExtractor.headingMarker;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class MarkedTextTest
    extends ElectedTextTest {

    private enum StringClassification {
        ELECTED_HEADING,
        NOMINATED_HEADING,
        SELECTED_CONTENT,
        EXCLUDED_CONTENT
    }

    //TODO: check setup methods

    @Mock
    ElectedText electedTextListMock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

        class TestOracle {

            static final String ELECTED_HEADING = "ELECTED HEADING";
            static final String NOMINATED_HEADING = "NOMINATED HEADING";
            static final String SELECTED_CONTENT = "selected content";
            static final String EXCLUDED_CONTENT = "excluded content";

            MarkedText testInput = null;

            List<String> testExpectedOutput = null;

            public TestOracle(){
                List<StringClassification> defaults = defaultClassifications();
                setupTestInput(defaults);
                setupTestOutput(defaults);
            }

            public TestOracle(List<StringClassification> classifiedList){
                setupTestInput(classifiedList);
                setupTestOutput(classifiedList);
            }

            private void
            setupTestOutput(List<StringClassification> classificationList){
                List<String> testExpectedOutput;
            }

            private void
            setupTestInput(List<StringClassification> classificationList){

                List<Integer> nomineeIndex = new ArrayList<>();
                List<Integer> electeeIndex = new ArrayList<>();
                List<Integer> includedTextIndex = new ArrayList<>();
                List<Integer> excludedTextIndex = new ArrayList<>();


                List<String> stringList = new ArrayList<>();

                ListIterator<StringClassification> it
                        = classificationList.listIterator();

                while(it.hasNext()){
                    int index = it.nextIndex();
                    StringClassification element = it.next();

                    if(element.equals(StringClassification.ELECTED_HEADING)) {
                        electeeIndex.add(index);
                        stringList.add(index + ": " + this.ELECTED_HEADING);
                    }
                    else if(element.equals(StringClassification.NOMINATED_HEADING)) {
                        nomineeIndex.add(index);
                        stringList.add(index + ": " + this.NOMINATED_HEADING);
                    }
                    else if(element.equals(StringClassification.SELECTED_CONTENT)) {
                        includedTextIndex.add(index);
                        stringList.add(index + ": " + this.SELECTED_CONTENT);
                    }
                    else {
                        excludedTextIndex.add(index);
                        stringList.add(index + ": " + this.EXCLUDED_CONTENT);
                    }
                }

                ElectedText electedText = new ElectedText(
                        stringList,
                        nomineeIndex,
                        electeeIndex
                );

                this.testInput = new MarkedText(electedText);
            }

            public MarkedText getMarkedText(){
                ElectedText electedText
                        = new ElectedText(
                        this.testTextList,
                        this.nomineeIndex,
                        this.electeeIndex
                );

                return new MarkedText(electedText);
            }

            public boolean validateResult(List<String> result){
                List<String> expectedResult = new ArrayList<>();
                return false;
            }

            private List<StringClassification> defaultClassifications(){
                List<StringClassification> list = new ArrayList<>();
                list.add(StringClassification.EXCLUDED_CONTENT);
                list.add(StringClassification.EXCLUDED_CONTENT);
                list.add(StringClassification.EXCLUDED_CONTENT);
                list.add(StringClassification.NOMINATED_HEADING);
                list.add(StringClassification.NOMINATED_HEADING);
                list.add(StringClassification.EXCLUDED_CONTENT);
                list.add(StringClassification.ELECTED_HEADING);
                list.add(StringClassification.SELECTED_CONTENT);
                list.add(StringClassification.SELECTED_CONTENT);
                list.add(StringClassification.NOMINATED_HEADING);
                list.add(StringClassification.NOMINATED_HEADING);
                list.add(StringClassification.EXCLUDED_CONTENT);
                list.add(StringClassification.ELECTED_HEADING);
                list.add(StringClassification.SELECTED_CONTENT);
                list.add(StringClassification.SELECTED_CONTENT);
                list.add(StringClassification.NOMINATED_HEADING);
                list.add(StringClassification.NOMINATED_HEADING);
                list.add(StringClassification.EXCLUDED_CONTENT);
                list.add(StringClassification.ELECTED_HEADING);
                list.add(StringClassification.SELECTED_CONTENT);
                list.add(StringClassification.SELECTED_CONTENT);

                return list;
            }

        }

    }


    @Test
    public void
    test_SubSelectionsReturnsExpectedOutput_WhenIndexListsAreUnordered
            () throws Exception {
        //TODO: decide when and where to make/check ordered lists





        ListIterator<String> lit = testInput.listIterator();
        while(lit.hasNext()){
            int index = lit.nextIndex();
            String element = lit.next();
            if(element.contains(nominatedHeading)){
                nominees.add(index);
            }
            if(element.contains(electedHeading)){
                electes.add(index);
            }
            if()
        }



        fail("This test has not been implemented");
    }
}