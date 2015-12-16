package com.grayben.riskExtractor.htmlScorer;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.fail;

/**
 * Created by beng on 28/11/2015.
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public abstract class HtmlScorerTest {

    private HtmlScorer htmlScorer;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    protected void setHtmlScorer(HtmlScorer htmlScorer){
        this.htmlScorer = htmlScorer;
    }

    protected HtmlScorer getHtmlScorer(){
        return this.htmlScorer;
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

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
    test_ScoreHtmlThrowsNullPointerException_WhenURLIsNull
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsIllegalArgumentException_WhenCharsetNameIsEmptyString
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsIllegalArgumentException_WhenURLIsEmptyString
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsException_WhenURLCannotBeResolved
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlReturnsNonNull_WhenSimpleInput
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlReturnsNonNull_WhenEmptyFile
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlReturnsSameFromAnySignature_WhenTextInputIsSameSimple
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    abstract public void
    test_ScoreHtmlReturnsExpected_WhenTextInputIsSimple
            () throws Exception;

    @Test
    abstract public void
    test_ScoreHtmlReturnsExpected_WhenTextInputIsComplicated
            () throws Exception;
}