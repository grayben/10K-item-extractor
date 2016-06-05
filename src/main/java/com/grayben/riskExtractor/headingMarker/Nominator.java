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
public class Nominator {
    private final Predicate<ScoredTextElement> isNominee;

    public Nominator(Predicate<ScoredTextElement> isNominee) {
        if (isNominee == null) {
            throw new NullPointerException("isNominee cannot be null");
        }
        this.isNominee = isNominee;
    }

    public NominatedText nominate(ScoredText scoredText){
        if (scoredText == null) {
            throw new NullPointerException("scoredText cannot be null");
        }
        List<Integer> nomineeIndices = new ArrayList<>();
        for (ScoredTextElement element : scoredText.getList()){

        }
        return null;
    }

    /**
     * {@link UnmodifiableText} for which zero or more entries have been deemed 'nominees.'
     * <p>
     * Created by Ben Gray, 2015.
     */
    public static class NominatedText
            extends UnmodifiableText
            implements EntriesRetrievable.NomineesRetrievable<String> {

        /**
         * The indices into {@link #stringList} identifying nominees.
         */
        private SetUniqueList<Integer> nomineeIndices;

        /**
         * Construct 'from scratch'.
         * @param stringList the list of strings containing the text entries
         * @param nomineeIndices the indices identifying nominees
         */
        public NominatedText(List<String> stringList, SetUniqueList<Integer> nomineeIndices) {
            super(stringList);
            if (nomineeIndices == null) {
                throw new NullPointerException("Attempted to pass null argument");
            }
            this.nomineeIndices = nomineeIndices;
        }

        /**
         * Construct incrementally.
         * @param unmodifiableText the text upon which to build
         * @param nomineeIndices the indices identifying nominees
         */
        public NominatedText(
                UnmodifiableText unmodifiableText,
                SetUniqueList<Integer> nomineeIndices){
            super(unmodifiableText);
            if (nomineeIndices == null) {
                throw new NullPointerException("Attempted to pass null argument");
            }
            this.nomineeIndices = nomineeIndices;
        }

        /**
         * Construct a copy.
         * @param nominatedText the object to copy
         */
        public NominatedText(NominatedText nominatedText){
            this(nominatedText, nominatedText.getNomineeIndices());
        }

        @Override
        public SetUniqueList<Integer> getNomineeIndices() {
            SetUniqueList<Integer> newList = SetUniqueList.setUniqueList(
                    new ArrayList<>()
            );
            newList.addAll(this.nomineeIndices);
            return newList;
        }
    }

}
