package edu.illinois.cs.cogcomp.ir.fol.norm;

import java.util.Map;

import edu.illinois.cs.cogcomp.ir.IndicatorVariable;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class Negation implements FolFormula {
    private FolFormula formula;

    public Negation(FolFormula formula) {
        this.formula = formula;
    }

    public FolFormula getFormula() {
        return formula;
    }

    @Override
    public boolean eval(Map<IndicatorVariable, Boolean> assignment) {
        return !this.formula.eval(assignment);
    }
}
