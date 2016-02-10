package com.grayben.riskExtractor.headingMarker;

import org.apache.commons.collections4.list.SetUniqueList;

/**
 * An interface allowing electees to be retrieved from an object.
 */
public interface ElecteesRetrievable<T> extends NomineesRetrievable<T> {

    /**
     * @return indices into {@link #getEntries()} corresponding to elected entries
     */
	SetUniqueList<Integer> getElecteeIndices();
}
