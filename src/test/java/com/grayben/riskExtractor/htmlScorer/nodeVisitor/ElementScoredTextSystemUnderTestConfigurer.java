package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.tools.testOracle.SystemUnderTest;
import org.jsoup.nodes.Element;

import java.util.function.Function;

/**
 * Created by Ben Gray on 12/02/2016.
 */
public class ElementScoredTextSystemUnderTestConfigurer
        implements Function<ElementScoredTextSystemUnderTestConfigurer.Config, SystemUnderTest<Element, ScoredText>> {

    @Override
    public SystemUnderTest<Element, ScoredText> apply(Config config) {
        return null;
    }

    enum Config {
        DEFAULT
    }

}
