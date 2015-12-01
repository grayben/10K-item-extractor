package com.grayben.riskExtractor.headingMarker;

import java.util.*;

/**
 * Created by beng on 30/11/2015.
 */
public class TextCandidates
        implements List<TextCandidate> {

    private ArrayList<TextCandidate> textCandidates;

    private List<String> textList = null;

    public List<String> getTextList() {
        return this.textList;
    }

    private List<String> extractTextListFrom(Collection<? extends  TextCandidate> c){
        List<String> prospectiveTextList = null;
        for (TextCandidate element: c ) {
            if(prospectiveTextList == null){
                prospectiveTextList = element.getList();
            } else {
                if(prospectiveTextList != element.getList()){
                    throw new IllegalArgumentException("Tried" +
                            " to construct with inconsistent collection");
                }
            }
        }
        return prospectiveTextList;
    }

    public TextCandidates(List<? extends TextCandidate> c) {

        //this should not have a textList assigned yet
        assert this.textList == null;

        //safely get the textList of the collection and assign
        this.textList = extractTextListFrom(c);

        //now actually assign the collection
        this.textCandidates = new ArrayList<>(c);

        //should have already thrown an exception if input is inconsistent
        assert(this.textIsConsistent());

    }

    boolean textIsConsistent() {

        //If contains 0 or 1 items: inconsistent lists not possible
        if (this.size() > 1) {

            if( ! this.hasTextList()) return false;

            Iterator<? extends TextCandidate> it = this.iterator();

            //until the end of the collection
            while (it.hasNext()) {

                //get the next item
                TextCandidate currentItem = it.next();

                    /*
                        if (text not consistent between
                        this item and the collection itself,
                        return false
                     */
                if (currentItem.textIsNotEqualTo(this.getTextList())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean textIsConsistentWith(TextCandidates other){
        return false;
    }

    public boolean textIsConsistentWith(TextCandidate element){
        if( ! this.hasTextList()) {
            return true;
        }
        if(element.textIsEqualTo(this.getTextList())){
            return true;
        } else {
            return false;
        }
    }

    public boolean textIsConsistentWith(List<String> textList){
        if( ! this.hasTextList()) {
            return true;
        }
        if(this.textList.equals(textList)){
            return true;
        } else {
            return false;
        }
    }

    public boolean hasTextList(){
        return(this.textList != null);
    }

    private void setTextList(List<String> list){
        if( ! this.hasTextList()) {
            this.textList = list;
        } else {
            throw new IllegalStateException("Attempting to set" +
                    " list when list is already set");
        }
    }

    @Override
    public int size() {
        return this.textCandidates.size();
    }

    @Override
    public boolean isEmpty() {
        return this.textCandidates.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.textCandidates.contains(o);
    }

    @Override
    public Iterator<TextCandidate> iterator() {
        return Collections.unmodifiableList(this.textCandidates).iterator();
    }

    @Override
    public Object[] toArray() {
        return Collections.unmodifiableList(this.textCandidates).toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return Collections.unmodifiableList(this.textCandidates).toArray(a);
    }

    @Override
    public boolean add(TextCandidate textCandidate) {
        throw new UnsupportedOperationException(
                "This list is not modifiable"
        );
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException(
                "This list is not modifiable"
        );
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.textCandidates.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends TextCandidate> c) {
        throw new UnsupportedOperationException(
                "This list is not modifiable"
        );
    }

    @Override
    public boolean addAll(int index, Collection<? extends TextCandidate> c) {
        throw new UnsupportedOperationException(
                "This list is not modifiable"
        );
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(
                "This list is not modifiable"
        );
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(
                "This list is not modifiable"
        );
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
                "This list is not modifiable"
        );
    }

    @Override
    public TextCandidate get(int index) {
        return Collections.unmodifiableList(textCandidates).get(index);
    }

    @Override
    public TextCandidate set(int index, TextCandidate element) {
        throw new UnsupportedOperationException(
                "This list is not modifiable"
        );
    }

    @Override
    public void add(int index, TextCandidate element) {
        throw new UnsupportedOperationException(
                "This list is not modifiable"
        );
    }

    @Override
    public TextCandidate remove(int index) {
        throw new UnsupportedOperationException(
                "This list is not modifiable"
        );
    }

    @Override
    public int indexOf(Object o) {
        return textCandidates.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return textCandidates.lastIndexOf(o);
    }

    @Override
    public ListIterator<TextCandidate> listIterator() {
        return Collections.unmodifiableList(this.textCandidates).listIterator();
    }

    @Override
    public ListIterator<TextCandidate> listIterator(int index) {
        return Collections.unmodifiableList(this.textCandidates).listIterator(index);
    }

    @Override
    public List<TextCandidate> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(this.textCandidates).subList(fromIndex, toIndex);
    }
}
