package edu.illinois.cs.cogcomp.l2ilp.representation.logic;

/**
 * A general interface for logic formula that may evaluates to true or false.
 */
public interface LogicFormula {

    /**
     * Negate the LogicFormula.
     * @return this formula negated.
     */
    LogicFormula negate();

    /**
     * Recursively transform the formula to negation normal form.
     * @return this formula in negation normal form
     */
    LogicFormula toNnf();
}