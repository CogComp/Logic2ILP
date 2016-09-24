package edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.BooleanLiteral;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.BooleanVariable;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;

/**
 *
 */
public class Negation implements LogicFormula {

    private LogicFormula formula;

    public Negation(LogicFormula formula) {
        this.formula = formula;
    }

    public LogicFormula getFormula() {
        return this.formula;
    }

    @Override
    public LogicFormula negate() {
        return this.formula;
    }

    @Override
    public LogicFormula toNnf() {
        if (this.formula instanceof BooleanVariable) {
            return this;
        }

        return this.formula.negate().toNnf();
    }

    @Override
    public String toString() {
        return "!" + this.formula.toString();
    }
}