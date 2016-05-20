package edu.illinois.cs.cogcomp.ir.fol.norm;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        return "!"+this.formula.toString();
    }
    @Override
    public FolFormula toNnf() {
        return null;
    }
}
