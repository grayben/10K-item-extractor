package com.grayben.riskExtractor.headingMarker;

import org.apache.commons.collections4.list.SetUniqueList;

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

    /**
     * An interface allowing electees to be retrieved from an object.
     */
    interface NomineesRetrievable<T> extends EntriesRetrievable<T> {

        /**
         * @return indices into {@link #getEntries()} corresponding to nominated entries
         */
        SetUniqueList<Integer> getNomineeIndices();
    }

    /**
     * An interface allowing electees to be retrieved from an object.
     * <p>
     * Created by Ben Gray, 2015.
     */
    interface ElecteesRetrievable<T> extends NomineesRetrievable<T> {

        /**
         * @return indices into {@link #getEntries()} corresponding to elected entries
         */
        SetUniqueList<Integer> getElecteeIndices();
    }
}
