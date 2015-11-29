package com.grayben.riskExtractor.htmlScorer;

import org.jsoup.nodes.Attribute;
import org.jsoup.parser.Tag;

public class TagAndAttribute {
	private final Tag tag;
	private final Attribute attribute;

    public Tag getTag() {
        return tag;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public TagAndAttribute(Tag tag, Attribute attribute) {
		super();
		this.tag = tag;
		this.attribute = attribute;
	}
}
