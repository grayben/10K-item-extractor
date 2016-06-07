package com.grayben.riskExtractor.headingMarker;

import com.grayben.riskExtractor.htmlScorer.ScoredText;
import com.grayben.riskExtractor.htmlScorer.ScoredTextElement;
import org.apache.commons.collections4.list.SetUniqueList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by beng on 24/04/2016.
 */
public class Elector {
    private final Predicate<ScoredTextElement> isElectee;

    public Elector(Predicate<ScoredTextElement> isElectee) {
        this.isElectee = isElectee;
    }

    public ElectedText elect(Nominator.NominatedText nominatedText){
        if (nominatedText == null) {
            throw new NullPointerException("NominatedText argument cannot be null");
        }
        throw new UnsupportedOperationException("Not implemented");
    }

    public ElectedText electedText(Nominator nominator, ScoredText scoredText){
        Nominator.NominatedText nominatedText = nominator.nominate(scoredText);
        return elect(nominatedText);
    }

    /**
     * NominatedText which has also had nominees elected.
     * <p>
     * Created by Ben Gray, 2015.
     */
    public static class ElectedText
            implements
            EntriesRetrievable.ElecteesRetrievable<String> {

        /**
         * The list of indices to the list of Strings corresponding to electees.
         */
        SetUniqueList<Integer> electees;

        public Nominator.NominatedText getNominatedText() {
            return nominatedText;
        }

        private Nominator.NominatedText nominatedText;

        /**
         * Constructs ElectedText 'from scratch'.
         * <p>
         * @param textList the list of Strings corresponding to the text
         * @param nominees the indices into textList corresponding to nominated entries
         * @param electees the indices into textList corresponding to elected entries
         * @apiNote electees must be a subset of nominees
         */
        public ElectedText(List<String> textList, SetUniqueList<Integer> nominees, SetUniqueList<Integer> electees){
            this.nominatedText = new Nominator.NominatedText(textList, nominees);
            if (electees == null) {
                throw new NullPointerException("Attempted to pass illegal null argument");
            }
            if ( ! nominees.containsAll(electees))
                throw new IllegalArgumentException(
                        "The electees argument was not a subset " +
                                "of the nominees argument"
                );
            this.electees = electees;
        }

        /**
         * Construct ElectedText incrementally from an existing {@link Nominator.NominatedText}
         * @param nominatedText the nominatedText upon which to construct
         * @param electees the list of indices into the text list corresponding to elected entries
         */
        public ElectedText(Nominator.NominatedText nominatedText, SetUniqueList<Integer> electees){
            this(
                    nominatedText.getEntries(),
                    nominatedText.getNomineeIndices(),
                    electees
            );
        }

        /**
         * Constructs as a copy of the input.
         * @param electedText the ElectedText to copy
         */
        public ElectedText(ElectedText electedText){
            this(electedText.getNominatedText(), electedText.getElecteeIndices());
        }

        @Override
        public SetUniqueList<Integer> getElecteeIndices() {

            SetUniqueList<Integer> newSetUniqueList
                    = SetUniqueList.setUniqueList(new ArrayList<>());

            newSetUniqueList.addAll(this.electees);

            return newSetUniqueList;
        }

        @Override
        public SetUniqueList<Integer> getNomineeIndices() {
            return this.nominatedText.getNomineeIndices();
        }

        @Override
        public List<String> getEntries() {
            return getNominatedText().getEntries();
        }
    }

}