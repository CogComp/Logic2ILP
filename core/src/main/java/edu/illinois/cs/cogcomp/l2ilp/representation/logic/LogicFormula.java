package edu.illinois.cs.cogcomp.l2ilp.representation.logic;

/**
 * Created by haowu on 5/19/16.
 */

/**
 * A General interface for *fol* formula.
 */
public interface LogicFormula {
    LogicFormula toNnf();
}
