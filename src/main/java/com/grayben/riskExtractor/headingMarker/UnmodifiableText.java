package com.grayben.riskExtractor.headingMarker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple immutable implementation of a list of strings.
 * <p>
 * Created by Ben Gray on 1/12/2015.
 */
public class UnmodifiableText implements EntriesRetrievable<String> {

    /**
     * The string list to wrap.
     */
    private List<String> stringList;

    /**
     * Wrap the specified list of strings.
     * @param stringList the list of strings to wrap
     */
    public UnmodifiableText(List<String> stringList){
        if(stringList == null)
            throw new NullPointerException("Attempted " +
                    "to construct with null pointer");

        /**
         * Copy {@link stringList} so that changes do not affect {@link #stringList}.
         */
        this.stringList = new ArrayList<>(stringList);
    }

    /**
     * Construct a copy of the specified object.
     * @param unmodifiableText the text to copy.
     */
    public UnmodifiableText(UnmodifiableText unmodifiableText){
        this(unmodifiableText.getStringList());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnmodifiableText that = (UnmodifiableText) o;

        return getStringList().equals(that.getStringList());

    }

    @Override
    public int hashCode() {
        return getStringList().hashCode();
    }

    /**
     * @return an immutable view of the text encapsulated by this object
     */
    public List<String> getStringList(){
        return Collections.unmodifiableList(this.stringList);
    }

    @Override
    final public List<String> getEntries() {
        return getStringList();
    }
}
