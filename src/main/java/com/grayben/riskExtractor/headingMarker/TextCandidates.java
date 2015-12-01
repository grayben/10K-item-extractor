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

    private List<String> getTextList(Collection<? extends  TextCandidate> c){
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

    public TextCandidates(Collection<? extends TextCandidate> c) {

        //this should not have a textList assigned yet
        assert this.textList == null;

        //safely get the textList of the collection and assign
        this.textList = getTextList(c);

        //now actually assign the collection
        this.textCandidates = new ArrayList<>(c);

        //should have already thrown an exception if input is inconsistent
        assert(this.textIsConsistent());

    }

    public TextCandidates(int initialCapacity) {
        super();
        this.textCandidates = new ArrayList<>(initialCapacity);

    }

    public TextCandidates() {
        super();
        this.textCandidates = new ArrayList<>();
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
        return this.textCandidates.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.textCandidates.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.textCandidates.toArray(a);
    }

    @Override
    public boolean add(TextCandidate element) {
        if( ! this.textIsConsistentWith(element)){
            throw new IllegalArgumentException("Attempted " +
                    "to add an element with inconsistent textList");
        }
        this.textList = element.getList();
        return textCandidates.add(element);
    }

    @Override
    public boolean remove(Object o) {
        boolean removed = textCandidates.remove(o);
        if(this.size() == 0){
            this.setTextList(null);
        }
        return removed;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.textCandidates.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends TextCandidate> c) {
        List<String> textList = getTextList(c);
        if( ! this.textIsConsistentWith(textList)){
            throw new IllegalArgumentException("Tried to add collection " +
                    "with text list inconsistent with this");
        }
        this.setTextList(textList);
        return this.textCandidates.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends TextCandidate> c) {
        List<String> textList =  getTextList(c);
        if( ! this.textIsConsistentWith(textList)){
            throw new IllegalArgumentException("Tried to add collection " +
                    "with text list inconsistent with this");
        }
        this.setTextList(textList);
        return this.textCandidates.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = this.textCandidates.removeAll(c);
        if(this.size() == 0){
            this.setTextList(null);
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retained = this.textCandidates.retainAll(c);
        if(this.size() == 0){
            this.setTextList(null);
        }
        return retained;
    }

    @Override
    public void clear() {
        this.textList = null;
        this.textCandidates.clear();
    }

    @Override
    public TextCandidate get(int index) {
        return textCandidates.get(index);
    }

    @Override
    public TextCandidate set(int index, TextCandidate element) {
        if( ! this.textIsConsistentWith(element)){
            throw new IllegalArgumentException("Attempted " +
                    "to add an element with inconsistent textList");
        }
        this.textList = element.getList();
        return textCandidates.set(index, element);
    }

    @Override
    public void add(int index, TextCandidate element) {
        if( ! this.textIsConsistentWith(element)){
            throw new IllegalArgumentException("Attempted " +
                    "to add an element with inconsistent textList");
        }
        this.textList = element.getList();
        textCandidates.add(index, element);

    }

    @Override
    public TextCandidate remove(int index) {
        TextCandidate removed = textCandidates.remove(index);
        if(textCandidates.size() == 0) this.textList = null;
        return removed;
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
        return this.textCandidates.listIterator();
    }

    @Override
    public ListIterator<TextCandidate> listIterator(int index) {
        return this.textCandidates.listIterator(index);
    }

    @Override
    public List<TextCandidate> subList(int fromIndex, int toIndex) {
        return this.textCandidates.subList(fromIndex, toIndex);
    }
}
