package com.grayben.riskExtractor.headingMarker.nominator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public interface INomineesRetrievableTest {



    @Test
    void test_GetNominees_ReturnsNonNull_Always() throws Exception;
}