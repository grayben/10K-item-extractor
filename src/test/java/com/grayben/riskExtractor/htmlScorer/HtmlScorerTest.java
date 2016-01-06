package com.grayben.riskExtractor.htmlScorer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

import static junit.framework.TestCase.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class HtmlScorerTest {

    private HtmlScorer htmlScorerSUT;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    protected void setHtmlScorerSUT(HtmlScorer htmlScorerSUT){
        this.htmlScorerSUT = htmlScorerSUT;
    }

    @Before
    public void setUp() throws Exception {
        assert htmlScorerSUT != null;
    }

    @After
    public void tearDown() throws Exception {
        this.htmlScorerSUT = null;
    }

    @Test
    public void
    test_ScoreHtmlThrowsNullPointerException_WhenURLIsNull
            () throws Exception {
        String url = null;

        thrown.expect(NullPointerException.class);

        htmlScorerSUT.scoreHtml(url);
    }

    @Test
    public void
    test_ScoreHtmlThrowsNullPointerException_WhenHtmlFileIsNull
            () throws Exception {
        File file = null;

        String charsetName = "ASCII";

        thrown.expect(NullPointerException.class);

        htmlScorerSUT.scoreHtml(file, charsetName);
    }

    @Test
    public void
    test_ScoreHtmlThrowsNullPointerException_WhenCharsetNameIsNull
            () throws Exception {
        File file = new File("src/test/resources/sample.html");

        String charsetName = null;

        thrown.expect(NullPointerException.class);

        htmlScorerSUT.scoreHtml(file, charsetName);
    }

    @Test
    public void
    test_ScoreHtmlThrowsIllegalArgumentException_WhenCharsetNameIsNotRecognised
            () throws Exception {
        File file = new File("src/test/resources/sample.html");

        String charsetName = "foo bar baz";

        thrown.expect(IllegalArgumentException.class);

        htmlScorerSUT.scoreHtml(file, charsetName);
    }


    @Test
    public void
    test_ScoreHtmlThrowsIllegalArgumentException_WhenCharsetNameIsEmptyString
            () throws Exception {
        File file = new File("src/test/resources/sample.html");

        String charsetName = "";

        thrown.expect(IllegalArgumentException.class);

        htmlScorerSUT.scoreHtml(file, charsetName);
    }

    @Test
    public void
    test_ScoreHtmlThrowsIllegalArgumentException_WhenUrlIsNotValid
            () throws Exception {
        String url = "ggggg:%//l";

        thrown.expect(IllegalArgumentException.class);

        htmlScorerSUT.scoreHtml(url);
    }

    @Test
    public void
    test_ScoreHtmlThrowsHttpStatusException_IfResponseIsNotOK
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsUnsupportedMimeTypeException_WhenMimeTypeUnsupported
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsSocketTimeoutException_WhenConnectionTimeout
            () throws Exception {
        fail("Test not implemented");
    }

    @Test
    public void
    test_ScoreHtmlThrowsIOException_WhenURLCannotBeResolved
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
    test_ScoreHtmlReturnsNonNull_WhenLengthyInput
            () throws Exception {
        File file = new File("src/test/resources/sample.html");

        String charsetName = null;

        thrown.expect(NullPointerException.class);

        htmlScorerSUT.scoreHtml(file, charsetName);
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