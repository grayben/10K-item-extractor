package com.grayben.riskExtractor.htmlScorer;

import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.EmphasisElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.elementScorers.SegmentationElementScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagEmphasisScorer;
import com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagSegmentationScorer;
import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static junit.framework.TestCase.fail;

/**
 * Created by beng on 28/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TreeHtmlScorerTest
        extends HtmlScorerTest {

    TreeHtmlScorer validTreeHtmlScorerSUT;

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
        // use a worked example - there's no way around testing a couple in this way
        fail("Test not implemented");
    }

    @Override
    @Test
    public void
    test_ScoreHtmlReturnsExpected_WhenTextInputIsComplicated
            () throws Exception {
        // use a worked example - there's no way around testing a couple in this way
        fail("Test not implemented");
    }

    @Override
    @Test
    public void
    test_ScoreHtmlReturnsExpected_OnManyRandomlyInputsReversedEngineeredFromExpectedOutputs
            () throws Exception {

        TreeHtmlScorerReverserTestContainerSupplier testContainerSupplier
                = new TreeHtmlScorerReverserTestContainerSupplier(() -> null);

        // use TreeHtmlScorerReverserTestContainerSuppler to apply reverse engineer to expected outputs
        for(int i = 0; i < 100; i++){
            ScoredText expectedOutput = randomScoredText();
            testContainerSupplier.get().verify(expectedOutput);
        }
        fail("Test not implemented");
    }

    private ScoredText randomScoredText() {
        throw new UnsupportedOperationException("Not implemented");
    }


}