package com.grayben.riskExtractor.htmlScorer.nodeVisitor.setup;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.tools.testOracle.SystemUnderTest;
import org.apache.commons.lang3.NotImplementedException;
import org.jsoup.nodes.Element;

import java.util.function.Supplier;

/**
 * Created by Ben Gray on 16/02/2016.
 */
public class SystemUnderTestSupplier
        implements Supplier<SystemUnderTest<Element, ScoredText>> {


    @Override
    public SystemUnderTest<Element, ScoredText> get() {
        throw new NotImplementedException("This class is not implemented");
    }
}
