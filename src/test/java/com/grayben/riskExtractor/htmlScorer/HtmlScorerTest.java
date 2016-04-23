package com.grayben.riskExtractor.htmlScorer;

import org.apache.commons.io.FileUtils;
import org.jsoup.HttpStatusException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.net.URL;
import java.net.UnknownHostException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

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
    test_ScoreHtmlThrowsIllegalArgumentException_WhenURLIsNull
            () throws Exception {
        String url = null;

        thrown.expect(IllegalArgumentException.class);

        htmlScorerSUT.scoreHtml(url);
    }

    @Test
    public void
    test_ScoreHtmlThrowsNullPointerException_WhenHtmlFileIsNull
            () throws Exception {
        InputStream inputStream = null;

        String charsetName = "UTF-8";

        thrown.expect(NullPointerException.class);

        htmlScorerSUT.scoreHtml(inputStream, charsetName, "");
    }

    @Test
    public void
    test_ScoreHtmlThrowsNullPointerException_WhenCharsetNameIsNull
            () throws Exception {
        File file = new File("src/test/resources/lengthy.html");
        InputStream inputStream = new FileInputStream(file);

        String charsetName = null;

        thrown.expect(NullPointerException.class);

        htmlScorerSUT.scoreHtml(inputStream, charsetName, "");
    }

    @Test
    public void
    test_ScoreHtmlThrowsIllegalArgumentException_WhenCharsetNameIsNotRecognised
            () throws Exception {
        File file = new File("src/test/resources/lengthy.html");
        InputStream inputStream = new FileInputStream(file);

        String charsetName = "foo bar baz";

        thrown.expect(IllegalArgumentException.class);

        htmlScorerSUT.scoreHtml(inputStream, charsetName, "");
    }


    @Test
    public void
    test_ScoreHtmlThrowsIllegalArgumentException_WhenCharsetNameIsEmptyString
            () throws Exception {
        File file = new File("src/test/resources/lengthy.html");
        InputStream inputStream = new FileInputStream(file);

        String charsetName = "";

        thrown.expect(IllegalArgumentException.class);

        htmlScorerSUT.scoreHtml(inputStream, charsetName, "");
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
    test_ScoreHtmlThrowsUnknownHostException_WhenUrlCanNotBeFound
            () throws Exception {
        String url = "http://ggggggggggggggggggggggggggggggggggg.com.au";

        thrown.expect(UnknownHostException.class);

        htmlScorerSUT.scoreHtml(url);
    }

    @Test
    public void
    test_ScoreHtmlThrowsHttpStatusException_IfResponseIsNotOK
            () throws Exception {
        String url = "http://www.google.com/sfadgadbdbddb";

        thrown.expect(HttpStatusException.class);

        htmlScorerSUT.scoreHtml(url);
    }

    @Test
    public void
    test_ScoreHtmlReturnsNonNull_WhenSimpleLocalInput
            () throws Exception {
        File file = new File("src/test/resources/simple.html");
        InputStream inputStream = new FileInputStream(file);

        String charsetName = "UTF-8";

        ScoredText returned = htmlScorerSUT.scoreHtml(inputStream, charsetName, "");

        assertNotNull(returned);
    }

    @Test
    public void
    test_ScoreHtmlReturnsNonNull_WhenSimpleRemoteInput
            () throws Exception {
        String url = "http://grayben.com";

        ScoredText returned = htmlScorerSUT.scoreHtml(url);

        assertNotNull(returned);
    }

    @Test
    public void
    test_ScoreHtmlReturnsNonNull_WhenLengthyLocalInput
            () throws Exception {
        File file = new File("src/test/resources/lengthy.html");
        InputStream inputStream = new FileInputStream(file);

        String charsetName = null;

        thrown.expect(NullPointerException.class);

        htmlScorerSUT.scoreHtml(inputStream, charsetName, "");
    }

    @Test
    public void
    test_ScoreHtmlReturnsNonNull_WhenLengthyRemoteInput
            () throws Exception {
        String url = "https://en.wikipedia.org/wiki/Albert_Einstein";

        ScoredText returned = htmlScorerSUT.scoreHtml(url);

        assertNotNull(returned);
    }

    @Test
    public void
    test_ScoreHtmlReturnsNonNull_WhenEmptyFile
            () throws Exception {
        File file = new File("src/test/resources/lengthy.html");
        InputStream inputStream = new FileInputStream(file);

        String charsetName = "UTF-8";

        ScoredText returned = htmlScorerSUT.scoreHtml(inputStream, charsetName, "");

        assertNotNull(returned);
    }

    @Test
    public void
    test_ScoreHtmlReturnsSameFromAnySignature_WhenTextInputIsSameSimple
            () throws Exception {
        File file = File.createTempFile("foo", "bar");
        InputStream inputStream = new FileInputStream(file);

        String urlString = "https://en.wikipedia.org/wiki/Albert_Einstein";
        String charsetName = "UTF-8";

        URL url = new URL(urlString);

        FileUtils.copyURLToFile(url, file, 2000, 2000);

        ScoredText returnedFromLocal = htmlScorerSUT.scoreHtml(inputStream, charsetName, "");
        ScoredText returnedFromRemote = htmlScorerSUT.scoreHtml(urlString);

        assertEquals(returnedFromLocal, returnedFromRemote);
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