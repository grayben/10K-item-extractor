package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.nominator.NominatedText;
import com.grayben.riskExtractor.headingMarker.nominator.NominatedTextTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.junit.Assert.assertNotNull;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElectedTextTest
        extends
        NominatedTextTest
        implements
        IElecteesRetrievableTest {

    private ElectedText electedTextSUT;

    public NominatedText getElectedTextSUT() {
        return electedTextSUT;
    }

    public void setElectedTextSUT(ElectedText electedTextSUT) {
        this.electedTextSUT = electedTextSUT;
        this.setNominatedTextSUT(this.electedTextSUT);
    }

    private SortedSet<Integer> electeesArgument;

    @Before
    @Override
    public void setUp() throws Exception {
        this.stringListArgument = new ArrayList<>();
        stringListArgument.add("one");
        stringListArgument.add("two");
        stringListArgument.add("cow");

        this.nomineesArgument = new TreeSet<>();
        nomineesArgument.add(0);
        nomineesArgument.add(2);


        this.electeesArgument = new TreeSet<>();
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
    final public void tearDownElectedText() throws Exception {
    }

    @Test
    public void
    test_InitThrowsIllegalArgumentException_WhenElecteesInNotSubsetOfNominees
            () throws Exception {

        List<String> stringList = new ArrayList<>();
        stringList.add("one");
        stringList.add("two");
        stringList.add("three");

        SortedSet<Integer> nominees = new TreeSet<>();
        nominees.add(1);
        nominees.add(2);

        SortedSet<Integer> electees = new TreeSet<>();
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
        SortedSet<Integer> electeesReturned = electedTextSUT.getElectees();
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


}