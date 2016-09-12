package edu.illinois.cs.cogcomp.representation.logic;

/**
 * Created by haowu on 5/19/16.
 */

/**
 * A General interface for *fol* formula.
 */
public interface LogicFormula {
    LogicFormula toNnf();
}
