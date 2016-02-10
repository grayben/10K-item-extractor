package com.grayben.riskExtractor.headingMarker.nominator;

import java.util.List;

/**
 * Created by beng on 10/02/2016.
 */
public interface EntriesRetrievable<T> {

    /**
     * @return a list, where some entries may be nominees
     */
    List<T> getEntries();
}
