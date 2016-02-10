package com.grayben.riskExtractor.headingMarker.elector;

import com.grayben.riskExtractor.headingMarker.nominator.NomineesRetrievable;

import java.util.Set;

/**
 * An interface allowing electeeIndices to be retrieved from an object.
 */
public interface ElecteesRetrievable<T> extends NomineesRetrievable<T> {

    /**
     * @return indices into {@link #getEntries()} corresponding to elected entries
     */
	Set<Integer> getElecteeIndices();
}
