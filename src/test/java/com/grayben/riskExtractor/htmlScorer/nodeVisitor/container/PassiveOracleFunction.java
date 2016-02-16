package com.grayben.riskExtractor.htmlScorer.nodeVisitor.container;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.tools.testOracle.oracle.passive.PassiveOracle;
import org.jsoup.nodes.Element;

import java.util.function.Function;

/**
 * Created by Ben Gray on 16/02/2016.
 */
public class PassiveOracleFunction
        implements Function<PassiveOracleFunction.OracleConfig, PassiveOracle<Element, ScoredText>> {

    enum OracleConfig {
        DEFAULT
    }

    @Override
    public PassiveOracle<Element, ScoredText> apply(OracleConfig oracleConfig) {
        return null;
    }


}
