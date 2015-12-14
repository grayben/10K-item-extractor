package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.nominator.NominatedTextTest;
import org.apache.commons.collections4.list.SetUniqueList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
public class ElectedTextTest
        extends
        NominatedTextTest {

    private ElectedText electedTextSUT;

    public ElectedText getElectedTextSUT() {
        return electedTextSUT;
    }

    public void setElectedTextSUT(ElectedText electedTextSUT) {
        this.electedTextSUT = electedTextSUT;
        this.setNominatedTextSUT(this.electedTextSUT);
    }

    protected SetUniqueList<Integer> electeesArgument;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
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

        this.setElectedTextSUT(
                new ElectedText(
                        stringListArgument,
                        nomineesArgument,
                        electeesArgument
                )
        );
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
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

        new ElectedText(
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
        SetUniqueList<Integer> electeesReturned = electedTextSUT.getElectees();
        //verify
        assertNotNull(electeesReturned);
    }

    @Test
    @Override
    public void
    test_BasicConstructorThrowsNullPointerException_WhenStringListIsNull
            () throws Exception {
        stringListArgument = null;

        thrown.expect(NullPointerException.class);

        new ElectedText(stringListArgument, nomineesArgument, electeesArgument);
    }

    @Test
    @Override
    public void
    test_BasicConstructorThrowsNullPointerException_WhenNomineesIsNull
            () throws Exception {
        nomineesArgument = null;

        thrown.expect(NullPointerException.class);

        new ElectedText(stringListArgument, nomineesArgument, electeesArgument);
    }

    @Test
    public void
    test_BasicConstructorThrowsNullPointerException_WhenElecteesIsNull
            () throws Exception {
        electeesArgument = null;

        thrown.expect(NullPointerException.class);

        new ElectedText(stringListArgument, nomineesArgument, electeesArgument);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenNominatedTextIsNull
            () throws Exception {
        this.setNominatedTextSUT(null);

        thrown.expect(NullPointerException.class);

        new ElectedText(getNominatedTextSUT(), electeesArgument);
    }

    @Test
    public void
    test_IncrementalConstructorThrowsNullPointerException_WhenElecteesIsNull
            () throws Exception {
        this.electeesArgument = null;

        thrown.expect(NullPointerException.class);

        new ElectedText(getNominatedTextSUT(), electeesArgument);
    }

    @Test
    @Override
    public void
    test_PrototypeConstructorThrowsNullPointerException_WhenPrototypeIsNull
            () throws Exception {
        this.setElectedTextSUT(null);

        thrown.expect(NullPointerException.class);

        new ElectedText(electedTextSUT);
    }

    @Test
    public void
    test_ModifyingReturnedElecteesDoesNotAffectCopyHeldBySUT
            () throws Exception {
        SetUniqueList<Integer> returnedFirst = electedTextSUT.getElectees();
        returnedFirst.add(Integer.MAX_VALUE);
        SetUniqueList<Integer> returnedSecond = electedTextSUT.getElectees();
        assertNotEquals(returnedFirst, returnedSecond);
    }


}