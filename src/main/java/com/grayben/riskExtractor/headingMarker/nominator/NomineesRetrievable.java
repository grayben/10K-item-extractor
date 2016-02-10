package com.grayben.riskExtractor.headingMarker.nominator;

import java.util.Set;

/**
 * An interface allowing electees to be retrieved from an object.
 */
public interface NomineesRetrievable<T> extends EntriesRetrievable<T> {

    /**
     * @return indices into {@link #getEntries()} corresponding to nominated entries
     */
	Set<Integer> getNomineeIndices();
}
