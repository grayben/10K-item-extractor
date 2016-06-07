package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.Elector;
import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElectedTextTest {

    private Elector.ElectedText electedTextSUT;

    private List<String> stringListArgument;

    private SetUniqueList<Integer> nomineesArgument;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public Elector.ElectedText getElectedTextSUT() {
        return electedTextSUT;
    }

    protected SetUniqueList<Integer> electeesArgument;

    @Before
    public void setUp() throws Exception {
        this.stringListArgument = new ArrayList<>();
        stringListArgument.add("one");
        stringListArgument.add("two");
        stringListArgument.add("cow");

        this.nomineesArgument
                = SetUniqueList.setUniqueList(new ArrayList<>());
        nomineesArgument.add(0);
        nomineesArgument.add(2);


        this.electeesArgument
                = SetUniqueList.setUniqueList(new ArrayList<>());
        electeesArgument.add(0);

        this.electedTextSUT =
            new Elector.ElectedText(
                    stringListArgument,
                    nomineesArgument,
                    electeesArgument

        );
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void
    test_InitThrowsIllegalArgumentException_WhenElecteesInNotSubsetOfNominees
            () throws Exception {

        List<String> stringList = new ArrayList<>();
        stringList.add("one");
        stringList.add("two");
        stringList.add("three");

        SetUniqueList<Integer> nominees
                = SetUniqueList.setUniqueList(new ArrayList<>());
        nominees.add(1);
        nominees.add(2);

        SetUniqueList<Integer> electees
                = SetUniqueList.setUniqueList(new ArrayList<>());
        electees.add(3);

        thrown.expect(IllegalArgumentException.class);

        new Elector.ElectedText(
                stringList,
                nominees,
                electees
        );
    }

    @Test
    public void test_GetElectees_ReturnsNonNull_Always() throws Exception {
        //setup data

        //setup expectations

        //exercise
        SetUniqueList<Integer> electeesReturned = electedTextSUT.getElecteeIndices();
        //verify
        assertNotNull(electeesReturned);
    }

    @Test
    public void
    test_BasicConstructorThrowsNullPointerException_WhenStringListIsNull
            () throws Exception {
        stringListArgument = null;

        thrown.expect(NullPointerException.class);

        new Elector.ElectedText(stringListArgument, nomineesArgument, electeesArgument);
    }

    @Test
    public void
    test_BasicConstructorThrowsNullPointerException_WhenNomineesIsNull
            () throws Exception {
        nomineesArgument = null;

        thrown.expect(NullPointerException.class);

        new Elector.ElectedText(stringListArgument, nomineesArgument, electeesArgument);
    }

    @Test
    public void
    test_BasicConstructorThrowsNullPointerException_WhenElecteesIsNull
            () throws Exception {
        electeesArgument = null;

        thrown.expect(NullPointerException.class);

        new Elector.ElectedText(stringListArgument, nomineesArgument, electeesArgument);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenNominatedTextIsNull
            () throws Exception {

        thrown.expect(NullPointerException.class);

        new Elector.ElectedText(null, electeesArgument);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenElecteesIsNull
            () throws Exception {
        this.electeesArgument = null;

        thrown.expect(NullPointerException.class);

        new Elector.ElectedText(electedTextSUT.getNominatedText(), electeesArgument);
    }

    @Test
    public void
    test_PrototypeConstructorThrowsNullPointerException_WhenPrototypeIsNull
            () throws Exception {

        this.electedTextSUT = null;

        thrown.expect(NullPointerException.class);

        new Elector.ElectedText(electedTextSUT);
    }

    @Test
    public void
    test_ModifyingReturnedElecteesDoesNotAffectCopyHeldBySUT
            () throws Exception {
        SetUniqueList<Integer> returnedFirst = electedTextSUT.getElecteeIndices();
        returnedFirst.add(Integer.MAX_VALUE);
        SetUniqueList<Integer> returnedSecond = electedTextSUT.getElecteeIndices();
        assertNotEquals(returnedFirst, returnedSecond);
    }


}