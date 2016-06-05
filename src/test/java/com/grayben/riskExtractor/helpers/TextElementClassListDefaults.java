package com.grayben.riskExtractor.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beng on 4/06/2016.
 */
public class TextElementClassListDefaults {
    public static List<TextElementClass> defaultClassifications(){
        List<TextElementClass> list = new ArrayList<>();
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.ELECTED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.ELECTED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.ELECTED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);

        return list;
    }

    public static List<TextElementClass> emptyList(){
        return new ArrayList<>();
    }

    public static List<TextElementClass> noHeadingList() {
        List<TextElementClass> list = new ArrayList<>();
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);

        return list;
    }

    public static List<TextElementClass> noElectedHeadingList(){
        List<TextElementClass> list = new ArrayList<>();
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NOMINATED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);

        return list;
    }

    public static List<TextElementClass> solelyTargetTextList(){
        List<TextElementClass> list = new ArrayList<>();
        list.add(TextElementClass.ELECTED_HEADING);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);

        return list;
    }

    public static List<TextElementClass> electedHeadingOnlyAtEndList(){
        List<TextElementClass> list = new ArrayList<>();
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.NON_HEADING_CONTENT);
        list.add(TextElementClass.ELECTED_HEADING);

        return list;
    }
}
