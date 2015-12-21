package com.grayben.riskExtractor.htmlScorer.partScorers;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagAndAttribute {
	private final Tag tag;
	private final Attribute attribute;

    public Tag getTag() {
        return tag;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public static List<TagAndAttribute> fromElement(Element element){
        List<TagAndAttribute> list = new ArrayList<>();
        for (Attribute attribute :
                element.attributes()) {
            list.add(new TagAndAttribute(element.tag(), attribute));
        }
        return list;
    }

    public TagAndAttribute(Tag tag, Attribute attribute) {
		super();
        if (tag == null) {
            throw new NullPointerException(
                    "Attempted to pass in null tag:Tag"
            );
        }
        if (attribute == null) {
            throw new NullPointerException(
                    "Attempted to pass in null attribute:Attribute"
            );
        }

        this.tag = tag;
		this.attribute = attribute;
	}

    @Override
    public boolean equals(Object o) {
        assert getTag() != null;
        assert getAttribute() != null;

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagAndAttribute that = (TagAndAttribute) o;

        if (!getTag().equals(that.getTag())) return false;
        return getAttribute().equals(that.getAttribute());

    }

    @Override
    public int hashCode() {
        assert getAttribute() != null;
        assert getTag() != null;
        int result = getTag().hashCode();
        result = 31 * result + getAttribute().hashCode();
        return result;
    }
}
