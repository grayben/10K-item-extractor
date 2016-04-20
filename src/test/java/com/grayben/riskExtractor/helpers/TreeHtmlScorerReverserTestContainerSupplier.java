package com.grayben.riskExtractor.helpers;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.partScorers.Scorer;
import com.grayben.tools.testOracle.SystemUnderTest;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;
import com.grayben.tools.testOracle.testContainer.TestContainer;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.InputStream;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Ben Gray on 19/02/2016.
 */
public class TreeHtmlScorerReverserTestContainerSupplier implements Supplier<TestContainer<ScoredText, ScoredText>> {

    private final Supplier<Set<Scorer<Element>>> elementScorersSupplier;

    public TreeHtmlScorerReverserTestContainerSupplier(Supplier<Set<Scorer<Element>>> elementScorersSupplier) {
        this.elementScorersSupplier = elementScorersSupplier;
    }

    @Override
    public TestContainer<ScoredText, ScoredText> get() {
        return new TestContainer.Builder<ScoredText, ScoredText>()
                .begin()
                .systemUnderTest(systemUnderTest())
                .oracle(passiveOracle())
                .build();
    }

    private PassiveOracle<ScoredText, ScoredText> passiveOracle(){
        return (input, actualOutput) -> input.equals(actualOutput);
    }

    /**
     *
     * @param elementScorers the element scorers to be used in the forward function
     * @param scoredText the target scored text
     * @return a file for which a correct implementation of TreeHtmlScorer will produce the specified ScoredText
     */
    private Function<ScoredText, InputStream> reverse(){
        throw new UnsupportedOperationException("Not implemented");
    }

    private Function<InputStream, ScoredText> forward(){
        throw new UnsupportedOperationException("Not implemented");
    }

    private SystemUnderTest<ScoredText, ScoredText> systemUnderTest() {
        return scoredText -> reverse().andThen(forward()).apply(scoredText);
    }


}
