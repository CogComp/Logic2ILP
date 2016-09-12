package edu.illinois.cs.cogcomp.util;

/**
 * Created by binglin on 4/23/16.
 */
public class Counter {

    private String prefix;
    private int count;

    public Counter(String prefix) {
        this.prefix = prefix;
        this.count = 0;
    }

    public void increment() {
        this.count ++;
    }

    @Override
    public String toString() {
        return prefix + String.valueOf(count);
    }
}
