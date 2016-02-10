package com.grayben.riskExtractor.headingMarker.nominator;

import org.apache.commons.collections4.list.SetUniqueList;

/**
 * An interface allowing electees to be retrieved from an object.
 */
public interface NomineesRetrievable<T> extends EntriesRetrievable<T> {

    /**
     * @return indices into {@link #getEntries()} corresponding to nominated entries
     */
	SetUniqueList<Integer> getNomineeIndices();
}
