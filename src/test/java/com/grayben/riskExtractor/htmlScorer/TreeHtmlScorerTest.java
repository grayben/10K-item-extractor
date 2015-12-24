package com.grayben.riskExtractor.htmlScorer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TreeHtmlScorerTest
        extends HtmlScorerTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        setHtmlScorer(new TreeHtmlScorer());
    }

    @After
    public void tearDown() throws Exception {
        setHtmlScorer(null);
    }

    @Test
    public void
    test_ScoreHtmlThrowsNullPointerException_WhenUrlIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsNullPointerException_WhenHtmlFileIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsNullPointerException_WhenCharsetNameIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenNodeTraversorIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsIllegalArgumentException_WhenCharsetNameIsNotRecognised
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsMalformedURLExceptionException_WhenUrlIsNotValid
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsHttpStatusException_IfResponseIsNotOK
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsUnsopportedMimeTypeException_WhenMimeTypeUnsupported
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsSocketTimeoutException_WhenConnectionTimeout
            () throws Exception {
        fail("Test not implemented");
    }

    @Override
    @Test
    public void
    test_ScoreHtmlReturnsExpected_WhenTextInputIsSimple
            () throws Exception {
        fail("Test not implemented");
    }

    @Override
    @Test
    public void
    test_ScoreHtmlReturnsExpected_WhenTextInputIsComplicated
            () throws Exception {
        fail("Test not implemented");
    }
}