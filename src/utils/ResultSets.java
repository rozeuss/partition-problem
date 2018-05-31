package utils;

import java.util.ArrayList;
import java.util.List;

public class ResultSets {

    private List<Integer> firstSubset = new ArrayList<>();
    private List<Integer> secondSubset = new ArrayList<>();

    public List<Integer> getFirstSubset() {
        return firstSubset;
    }

    public void setFirstSubset(List<Integer> firstSubset) {
        this.firstSubset = firstSubset;
    }

    public List<Integer> getSecondSubset() {
        return secondSubset;
    }

    public void setSecondSubset(List<Integer> secondSubset) {
        this.secondSubset = secondSubset;
    }
}
