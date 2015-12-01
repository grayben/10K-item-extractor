package com.grayben.riskExtractor.headingMarker;

import java.util.Collections;
import java.util.List;

/**
 * Created by beng on 1/12/2015.
 */
public class UnmodifiableText {

    private List<String> stringList;

    public UnmodifiableText(List<String> stringList){
        this.stringList = stringList;
    }

    public List<String> getStringList(){
        return Collections.unmodifiableList(this.stringList);
    }
}
