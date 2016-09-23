package edu.illinois.cs.cogcomp.l2ilp.inference.ilp.representation;

import gnu.trove.list.TDoubleList;
import gnu.trove.list.linked.TDoubleLinkedList;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by haowu on 9/8/16.
 */
public class Linear {

    public List<String> variables;
    public TDoubleList weights;

    public Linear() {
        variables = new LinkedList<>();
        weights = new TDoubleLinkedList();
    }

    public void add(double weight, String variableName) {
        variables.add(variableName);
        weights.add(weight);
    }
}