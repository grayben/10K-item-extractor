package com.grayben.riskExtractor.headingMarker;

import java.util.List;

/**
 * An interface allowing a list of entries to be retrieved.
 * <p>
 * Created by Ben Gray on 10/02/2016.
 */
public interface EntriesRetrievable<T> {

    /**
     * @return a list, where some entries may be nominees
     */
    List<T> getEntries();
}
