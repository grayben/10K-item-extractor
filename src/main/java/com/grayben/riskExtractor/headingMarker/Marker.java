package com.grayben.riskExtractor.headingMarker;

import org.apache.commons.collections4.list.SetUniqueList;

import java.util.*;

/**
 * Elected text for which selected sections can be returned.
 * <p>
 * Created by Ben Gray, 2015.
 */
final public class Marker
        extends Elector.ElectedText {

    /**
     * Pairs of indices into {@link #stringList} corresponding to sections bounded by an elected entry and
     * a nominated entry immediately following, or, the end of the list of entries.
     */
    private Map<Integer, Integer> stringIndexPairs = null;

    /**
     * The set of selected strings, where each string is a concatenation of all strings in
     * a selected section.
     * @see #stringIndexPairs
     */
    private Set<String> selectedSections = null;

    /**
     * Incrementally construct from an {@link Elector.ElectedText} precursor.
     * @param text the precursor
     */
    public Marker(Elector.ElectedText text) {
        super(text);
        this.stringIndexPairs = new HashMap<>();

        /**
         * Eager-compute data.
         */
        generateStringIndexPairs();
    }

    private void generateStringIndexPairs(){

        /**
         * Verify assumption that the map is already instantiated.
         */
        assert this.stringIndexPairs != null;

        /**
         * Verify assumption that {@link #stringIndexPairs}, which this method assigns, has not previously been assigned.
         */
        assert this.stringIndexPairs.isEmpty();



        Map<Integer, Integer> stringIndexPairs = this.stringIndexPairs;
        SetUniqueList<Integer> electeeIndices = this.getElecteeIndices();
        SetUniqueList<Integer> nomineeIndices = this.getNomineeIndices();

        /**
         * Verify assumption that electees is a subset of nominees.
         */
        assert(nomineeIndices.containsAll(electeeIndices));

        /**
         * For each electee:
         */
        for (Integer electee : electeeIndices) {
            /**
             * The start index is the index of the electee.
             */
            Integer startIndex;

            /**
             * Declare, but do not assign, the end index to compute.
             */
            Integer endIndex;
            startIndex = electee;

            /**
             * Remember, we assume (and verify) that {@link electeeIndices} is a subset
             * of {@link nomineeIndices}. Therefore, {@link nomineeIndices} shall contain
             * any element of the set {@link electeeIndices}. Since {@link startIndex} is
             * {@link electee} and {@link electee is an element of {@link electeeIndices},
             * {@link nomineeIndices} contains {@link startIndex).
             * <p>
             * We are interested in the <b>index of</b> {@link startIndex} in {@link nomineeIndices} so that
             * we can retrive the nominee index immediately following, if it exists. In the case that it exists,
             * that index represents the nominated heading immediately following the elected heading,
             * and thus, represents the index of the entry immediately following the last string in
             * the selected section currently being examined.
             * In the case that there is no next nominated heading, we know that the last string of this section
             * is the last string in the entire list of entries.
             */
            int indexOfStartIndexInNominees
                    = nomineeIndices.indexOf(startIndex);

            /**
             * If there is no nominated entry immediately following:
             */
            if(indexOfStartIndexInNominees == nomineeIndices.size() - 1){
                /**
                 * Then the last string of this section is the last string in the entire list of entries.
                 * Represent this with {@code null}.
                 */
                endIndex = null;
            }
            /**
             * Otherwise, there is a nominated entry immediately following:
             */
            else {
                /**
                 * Set {@link endIndex} as following nominated entry's index into the entry list.
                 */
                endIndex = nomineeIndices.get(indexOfStartIndexInNominees + 1);

                /**
                 * Decrement by one: we don't want to include the next nominated entry into the contents
                 * of this selected section.
                 */
                endIndex = endIndex - 1;
            }
            /**
             * Having determined the values of {@link startIndex} and {@link endIndex}, store them.
             */
            stringIndexPairs.put(startIndex, endIndex);
        }

        /**
         * Assign {@link stringIndexPairs} to {@link #stringIndexPairs} so that the above
         * computation does not need to be repeated on subsequent calls to this method.
         */
        this.stringIndexPairs = stringIndexPairs;
    }

    /**
     * @return the set of selected strings, where each string is a concatenation of all strings in
     * a selected section.
     */
    public Set<String> subSelections() {

        //TODO: variable and method naming semantics

        return getSelectedSections();
    }

    /**
     * @return {@link #selectedSections}, computing it if not already done
     */
    private Set<String> getSelectedSections(){

        /**
         * If {@link selectedSections} has not yet been computed:
         */
        if(this.selectedSections == null){

            /**
             * Instantiate an iterator through {@link #getStringIndexPairs()}.
             */
            Iterator<Map.Entry<Integer, Integer>> pairIterator
                    = getStringIndexPairs().entrySet().iterator();

            /**
             * Instantiate a {@link Set} for use by this object.
             */
            Set<String> selectedSections = new HashSet<>();

            /**
             * For all entries in {@link pairIterator}:
             */
            while(pairIterator.hasNext()){
                /**
                 * Get the next entry.
                 */
                Map.Entry<Integer, Integer> entry = pairIterator.next();

                /**
                 * Assign the index of the first string of the selected section.
                 */
                int startIndex = entry.getKey();

                /**
                 * Allocate, but don't assign, the index of the last string of the selected section;
                 * we don't yet know whether this index is adjacent to a nominated entry
                 * or the end of the list of entries.
                 */
                int endIndex;
                //TODO: test that this is the correct range

                /**
                 * If {@link entry.getValue()} is null: interpret that there is no terminating nominee;
                 * extract every string from {@link startIndex} to the end of the list of entries.
                 */
                if(entry.getValue() == null){
                    endIndex = getStringList().size() - 1;
                }
                /**
                 * Otherwise, use the index given by the {@link entry}.
                 */
                else {
                    endIndex = entry.getValue();
                }

                /**
                 * Now that we know the start (inclusive) and end (inclusive) indices of
                 * the strings comprising the selected section, we can extract the sublist of
                 * these strings.
                 */
                List<String> textSectionElements
                        = getStringList().subList(
                        startIndex,
                        /**
                         * {@code {@link endIndex} + 1}, because sublist is exclusive of the end index.
                         */
                        endIndex + 1);

                /**
                 * Now use a {@link StringBuilder} to concatenate each extracted string into a single string.
                 */
                StringBuilder sb = new StringBuilder();
                for (String string : textSectionElements
                        ) {
                    /**
                     * Separate strings with a single space
                     */
                    sb.append(string).append(" ");
                }

                /**
                 * Build the section to add to {@link selectedSections}.
                 */
                String section =  sb.toString()
                        /**
                         * Replace whitespace runs with single space.
                         */
                        .replaceAll("\\s+", " ")
                        /**
                         * discard whitespace at each end
                         */
                        .trim();

                /**
                 * Add the cleaned-up section to {@link selectedSections}.
                 */
                selectedSections.add(section);
            }
            /**
             * Assign so that subsequent calls to this method do not induce duplicate computation.
             */
            this.selectedSections = selectedSections;
        }

        /**
         * Return an unmodifiable view of {@link #selectedSections}.
         */
        return Collections.unmodifiableSet(this.selectedSections);
    }

    /**
     * @return {@link #stringIndexPairs}
     */
    private Map<Integer, Integer> getStringIndexPairs(){

        /**
         * {@link stringIndexPairs} must be assigned before this method is called.
         */
        assert this.stringIndexPairs != null;

        return this.stringIndexPairs;
    }

}
