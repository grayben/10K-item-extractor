package com.grayben.riskExtractor.headingMarker.nominator;

import org.apache.commons.collections4.list.SetUniqueList;

/**
 * An interface allowing electees to be retrieved from an object.
 */
public interface NomineesRetrievable {

    /**
     * @return indices into a list corresponding to elected entries
     */
	SetUniqueList<Integer> getNomineeIndices();
}
