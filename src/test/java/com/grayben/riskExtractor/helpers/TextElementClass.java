package com.grayben.riskExtractor.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beng on 8/12/2015.
 */
public enum TextElementClass {
    ELECTED_HEADING("ELECTED HEADING"),
    NOMINATED_HEADING("NOMINATED HEADING"),
    NON_HEADING_CONTENT("non-heading content");

    private final String archetype;

    TextElementClass(String archetype){
        this.archetype = archetype;
    }

    public static List<TextElementClass> defaultClassifications(){
        List<TextElementClass> list = new ArrayList<>();
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NOMINATED_HEADING);
        list.add(NOMINATED_HEADING);
        list.add(NON_HEADING_CONTENT);
        list.add(ELECTED_HEADING);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NOMINATED_HEADING);
        list.add(NOMINATED_HEADING);
        list.add(NON_HEADING_CONTENT);
        list.add(ELECTED_HEADING);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NOMINATED_HEADING);
        list.add(NOMINATED_HEADING);
        list.add(NON_HEADING_CONTENT);
        list.add(ELECTED_HEADING);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);

        return list;
    }

    public static List<TextElementClass> emptyList(){
        return new ArrayList<>();
    }

    public static List<TextElementClass> noHeadingList() {
        List<TextElementClass> list = new ArrayList<>();
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);

        return list;
    }

    public static List<TextElementClass> noElectedHeadingList(){
        List<TextElementClass> list = new ArrayList<>();
        list.add(NON_HEADING_CONTENT);
        list.add(NOMINATED_HEADING);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);

        return list;
    }

    public static List<TextElementClass> solelyTargetTextList(){
        List<TextElementClass> list = new ArrayList<>();
        list.add(ELECTED_HEADING);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);

        return list;
    }

    public static List<TextElementClass> electedHeadingOnlyAtEndList(){
        List<TextElementClass> list = new ArrayList<>();
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(NON_HEADING_CONTENT);
        list.add(ELECTED_HEADING);

        return list;
    }

    public String archetype(){
        return this.archetype;
    }
}
