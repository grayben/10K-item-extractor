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
        List<ScoredTextElement> scoredTextElementList = scoredText.getList();
        for (int i = 0; i < scoredTextElementList.size(); i++){
            ScoredTextElement element = scoredTextElementList.get(i);
            if (isNominee.test(element)){
                nomineeIndices.add(i);
            }
        }
        return new NominatedText(scoredText.getText(), SetUniqueList.setUniqueList(nomineeIndices));
    }

    /**
     * {@link UnmodifiableText} for which zero or more entries have been deemed 'nominees.'
     * <p>
     * Created by Ben Gray, 2015.
     */
    public static class NominatedText
            implements EntriesRetrievable.NomineesRetrievable<String> {

        private SetUniqueList<Integer> nomineeIndices;

        public UnmodifiableText getUnmodifiableText() {
            return unmodifiableText;
        }

        private UnmodifiableText unmodifiableText;

        /**
         * Construct 'from scratch'.
         * @param stringList the list of strings containing the text entries
         * @param nomineeIndices the indices identifying nominees
         */
        public NominatedText(List<String> stringList, SetUniqueList<Integer> nomineeIndices) {
            this.unmodifiableText = new UnmodifiableText(stringList);
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
            this.unmodifiableText = unmodifiableText;
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
            this(nominatedText.getEntries(), nominatedText.getNomineeIndices());
        }

        @Override
        public SetUniqueList<Integer> getNomineeIndices() {
            SetUniqueList<Integer> newList = SetUniqueList.setUniqueList(
                    new ArrayList<>()
            );
            newList.addAll(this.nomineeIndices);
            return newList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NominatedText that = (NominatedText) o;

            if (!getNomineeIndices().equals(that.getNomineeIndices())) return false;
            return unmodifiableText.equals(that.unmodifiableText);

        }

        @Override
        public int hashCode() {
            int result = getNomineeIndices().hashCode();
            result = 31 * result + unmodifiableText.hashCode();
            return result;
        }

        @Override
        public List<String> getEntries() {
            return this.unmodifiableText.getEntries();
        }
    }

}
