package com.grayben.riskExtractor.htmlScorer.nodeVisitor;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.Collections;
import java.util.Map;

/**
 * Created by beng on 5/01/2016.
 */
class AnnotatedElement extends Element {

    Map<String, Integer> scores;

    Map<String, Integer> getScores() {
        return Collections.unmodifiableMap(scores);
    }

    AnnotatedElement(Element element, Map<String, Integer> scores) {
        this(element.tag(), element.baseUri(), element.attributes(), scores);
    }

    AnnotatedElement(Tag tag, String baseUri, Attributes attributes, Map<String, Integer> scores) {
        super(tag, baseUri, attributes);
        this.scores = scores;
    }

    AnnotatedElement(Tag tag, String baseUri, Map<String, Integer> scores) {
        super(tag, baseUri);
        this.scores = scores;
    }
}
