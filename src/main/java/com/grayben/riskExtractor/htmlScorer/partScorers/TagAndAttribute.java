package com.grayben.riskExtractor.htmlScorer.partScorers;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregates a {@link Tag} instance and an {@link Attribute} instance.
 * <p>
 * Intended for use by {@link com.grayben.riskExtractor.htmlScorer.partScorers.tagScorers.TagAndAttributeScorer}.
 * <p>
 * Created by Ben Gray, 2015.
 */
public class TagAndAttribute {
    /**
     * {@link #tag} and {@link #attribute}: the instances to aggregate.
     */
	private final Tag tag;
	private final Attribute attribute;

    /**
     * @return the aggregated tag
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * @return the aggregated attribute
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * @param element the element to examine
     * @return the cartesian product of the argument's tag and the argument's set of attributes,
     * represented as a list of {@link TagAndAttribute}s.
     * E.g. an element containing the tag "div" and the attribute set
     * {"class": "nav-bar", "id": "top-nav-bar"} would produce the cartesian product
     * {("div", "class": "nav-bar"), ("div", "id": "top-nav-bar")}.
     */
    public static List<TagAndAttribute> fromElement(Element element){
        List<TagAndAttribute> list = new ArrayList<>();
        for (Attribute attribute :
                element.attributes()) {
            list.add(new TagAndAttribute(element.tag(), attribute));
        }
        return list;
    }

    /**
     * Aggregate the arguments into an instance of this class.
     * @param tag the tag to aggregate
     * @param attribute the attribute to aggregate
     */
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
