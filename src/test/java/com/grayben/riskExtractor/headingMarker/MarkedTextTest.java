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