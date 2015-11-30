package com.grayben.riskExtractor.headingMarker;

import java.util.List;

public class TextCandidate {
	private List<String> list;

    public TextCandidate(List<String> list, int index) {
        if(index < 0 || index >= list.size())
            throw new IllegalArgumentException("The index " +
                    "passed is not within the bounds " +
                    "of the list passed");
        this.list = list;
        this.index = index;
    }

    public List<String> getList() {
        return list;
    }

    private int index;

    public int getIndex() {
        return index;
    }

    public boolean textIsEqualTo(List<String> other){
        if(this.getList().equals(other))
            return true;
        return false;
    }

	public boolean textIsEqualTo(TextCandidate other){
        if(this.textIsEqualTo(other.getList()))
            return true;
        return false;
    }

    public boolean textIsNotEqualTo(List<String> other){
        return !this.textIsEqualTo(other);
    }

    public boolean textIsNotEqualTo(TextCandidate other){
        return !this.textIsEqualTo(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextCandidate that = (TextCandidate) o;

        if (index != that.index) return false;
        return list.equals(that.list);

    }

    @Override
    public int hashCode() {
        int result = list.hashCode();
        result = 31 * result + index;
        return result;
    }
}
