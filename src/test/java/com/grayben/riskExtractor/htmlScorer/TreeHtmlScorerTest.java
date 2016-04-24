package com.grayben.riskExtractor.htmlScorer;

import com.grayben.riskExtractor.helpers.ScoredTextGenerator;
import com.grayben.riskExtractor.helpers.TreeHtmlScorerReverserTestContainerSupplier;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.TagAndAttribute;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TreeHtmlScorerTest
        extends HtmlScorerTest {

    TreeHtmlScorer validTreeHtmlScorerSUT;
    Set<Scorer<Element>> elementScorersUsed;

    @Before
    public void setUp() throws Exception {
        Set<Scorer<Element>> elementScorers = new HashSet<>();
        elementScorers.add(
                new EmphasisElementScorer(
                        new TagEmphasisScorer(TagEmphasisScorer.defaultMap()),
                        new TagAndAttributeScorer(TagAndAttributeScorer.defaultMap())
                )
        );
        elementScorers.add(
                new SegmentationElementScorer(
                        new TagSegmentationScorer(TagSegmentationScorer.defaultMap())
                )
        );
        elementScorersUsed = elementScorers;
        ScoringAndFlatteningNodeVisitor nv = new ScoringAndFlatteningNodeVisitor(elementScorers);
        validTreeHtmlScorerSUT = new TreeHtmlScorer(nv);

        //TODO: inject the NodeTraversor into the TreeHtmlScorer constructor
        setHtmlScorerSUT(validTreeHtmlScorerSUT);
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        this.validTreeHtmlScorerSUT = null;
    }

    @Test
    public void
    test_InitThrowsNullPointerException_WhenNodeVisitorIsNull
            () throws Exception {
        ScoringAndFlatteningNodeVisitor nv = null;

        thrown.expect(NullPointerException.class);

        new TreeHtmlScorer(nv);
    }

    @Override
    @Test
    public void
    test_ScoreHtmlReturnsExpected_WhenTextInputIsSimple
            () throws Exception {
        elementScorersUsed = new HashSet<>();
        Map<Tag, Integer> emphasisMap = new HashMap<>();
        emphasisMap.put(Tag.valueOf("h1"), 2);
        emphasisMap.put(Tag.valueOf("h2"), 1);
        TagEmphasisScorer tagEmphasisScorer = new TagEmphasisScorer(emphasisMap);

        Map<TagAndAttribute, Integer> tagAndAttributeIntegerMap = new HashMap<>();
        tagAndAttributeIntegerMap.put(
                new TagAndAttribute(
                        Tag.valueOf("font"),
                        new Attribute("style", "bold")
                ),
                2
        );
        TagAndAttributeScorer tagAndAttributeScorer
                = new TagAndAttributeScorer(tagAndAttributeIntegerMap);

        Map<Tag, Integer> segmentationMap = new HashMap<>();
        segmentationMap.put(Tag.valueOf("div"), 1);
        TagSegmentationScorer tagSegmentationScorer = new TagSegmentationScorer(segmentationMap);

        elementScorersUsed.add(new EmphasisElementScorer(tagEmphasisScorer, tagAndAttributeScorer));
        elementScorersUsed.add(new SegmentationElementScorer(tagSegmentationScorer));
        String emphasisLabel = EmphasisElementScorer.SCORE_LABEL;
        String segmentationLabel = SegmentationElementScorer.SCORE_LABEL;
        ScoringAndFlatteningNodeVisitor nv = new ScoringAndFlatteningNodeVisitor(elementScorersUsed);
        validTreeHtmlScorerSUT = new TreeHtmlScorer(nv);

        ScoredText expected = new ScoredText();
        Map<String, Integer> scores = new HashMap<>();
        scores.put(emphasisLabel, 2);
        scores.put(segmentationLabel, 0);
        expected.add(new ScoredTextElement("Ben Gray", new HashMap<>(scores)));
        scores.put(emphasisLabel, 1);
        expected.add(new ScoredTextElement("Student", new HashMap<>(scores)));
        scores.put(emphasisLabel, 0);
        expected.add(new ScoredTextElement("Portfolio", new HashMap<>(scores)));
        expected.add(new ScoredTextElement("About", new HashMap<>(scores)));
        expected.add(new ScoredTextElement("Contact", new HashMap<>(scores)));
        scores.put(segmentationLabel, 1);
        expected.add(new ScoredTextElement("Footprints and Silhouette", new HashMap<>(scores)));
        expected.add(new ScoredTextElement("Lifeblood of Thailand", new HashMap<>(scores)));
        expected.add(new ScoredTextElement("The Simple Life", new HashMap<>(scores)));
        expected.add(new ScoredTextElement("Mondays", new HashMap<>(scores)));
        expected.add(new ScoredTextElement("Champions", new HashMap<>(scores)));
        expected.add(new ScoredTextElement("Copyright: 2014 Benjamin Gray.", new HashMap<>(scores)));

        ScoredText actual = validTreeHtmlScorerSUT.scoreHtml(
                new FileInputStream(new File("src/test/resources/simple.html")),
                "ASCII",
                "");

        assertEquals(expected, actual);
    }

    @Test
    public void
    test_ScoreHtmlReturnsExpected_OnManyRandomlyInputsReversedEngineeredFromExpectedOutputs
            () throws Exception {

        TreeHtmlScorerReverserTestContainerSupplier testContainerSupplier
                = new TreeHtmlScorerReverserTestContainerSupplier(() -> elementScorersUsed);

        // use TreeHtmlScorerReverserTestContainerSuppler to apply reverse engineer to expected outputs
        for(int i = 0; i < 100; i++){
            ScoredText expectedOutput = ScoredTextGenerator.randomScoredText(new Random(444l), elementScorersUsed);
            testContainerSupplier.get().verify(expectedOutput);
        }
    }


}