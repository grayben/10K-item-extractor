package com.grayben.riskExtractor.htmlScorer.partScorers;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by beng on 21/12/2015.
 */
public final class TestHelper {

    private TestHelper(){}

    public static Element stubElement(Tag tag, Attributes attributes){
        Element element = Mockito.mock(Element.class);
        Mockito.when(element.tag())
                .thenReturn(tag);
        Mockito.when(element.attributes())
                .thenReturn(attributes);

        return element;
    }

    public static Element stubElement(Tag tag){
        //add some dummy attributes to the element
        //(they shouldn't matter, since only the tag is important)
        Attributes attributes = dummyAttributes();

        return stubElement(tag, attributes);
    }

    public static Element stubElement(TagAndAttribute tagAndAttribute){
        Tag tag;
        Attribute attribute;

        tag = tagAndAttribute.getTag();
        attribute = tagAndAttribute.getAttribute();

        //Stub the Attributes randomly - these ones don't matter
        Attributes attributeMocks = dummyAttributes();
        attributeMocks.put(attribute);

        return stubElement(tag, attributeMocks);
    }

    public static Tag stubTag(String tagName){
        Tag tag = Mockito.mock(Tag.class);
        Mockito.when(tag.getName()).thenReturn(tagName);
        Mockito.when(tag.isEmpty()).thenReturn(false);

        return tag;
    }

    public static Attribute stubAttribute(String key, String value){
        Attribute attribute = Mockito.mock(Attribute.class);
        Mockito.when(attribute.getKey()).thenReturn(key);
        Mockito.when(attribute.getValue()).thenReturn(value);

        return attribute;
    }

    public static Attributes stubAttributes(Map<String, String> mapping){
        List<Attribute> attributes = new ArrayList<>();
        for (Map.Entry<String, String> entry: mapping.entrySet()) {
            attributes.add(stubAttribute(entry.getKey(), entry.getValue()));
        }

        assert attributes.size() == mapping.size();
        return convertListToAttributes(attributes);
    }

    public static Attributes convertListToAttributes(List<Attribute> attributeList){
        Attributes attributes = new Attributes();
        for (Attribute attribute :
                attributeList) {
            attributes.put(attribute);
        }
        return attributes;
    }

    public static Attributes dummyAttributes(){
        Map<String, String> dummyMap = new HashMap<>();
        dummyMap.put("pleaseNeverUseThisKey", "orThisVALUE");
        dummyMap.put("andForGoodnessSake, not this either.", "JustDon't.");

        return stubAttributes(dummyMap);
    }

    public static Map<Element, Integer>
    stubElementsAndScoresByTagAndAttributeScores(Map<TagAndAttribute, Integer> scoreMap){
        Map<Element, Integer> elementsAndScores = new HashMap<>();

        for (Map.Entry<TagAndAttribute, Integer> entry : scoreMap.entrySet()){
            Element elementMock = stubElement(
                    entry.getKey()
            );

            //expect the element mock to be scored
            //according to the tagAndAttribute in the map
            elementsAndScores.put(elementMock, entry.getValue());
        }

        assert elementsAndScores.size() == scoreMap.size();

        return elementsAndScores;
    }

    public static Map<Element, Integer>
    stubElementsAndScoresByTagScores
            (Map<Tag, Integer> scoreMap) {

        Map<Element, Integer> elementsAndScores = new HashMap<>();

        for (Map.Entry<Tag, Integer> entry :
                scoreMap.entrySet()) {

            Element elementMock = stubElement(entry.getKey());

            System.out.println(elementMock.tag().toString());

            // expect the element mock to be scored
            // according to the tag in the map
            elementsAndScores.put(elementMock, entry.getValue());
        }

        assert elementsAndScores.size() == scoreMap.size();

        return elementsAndScores;
    }

}
