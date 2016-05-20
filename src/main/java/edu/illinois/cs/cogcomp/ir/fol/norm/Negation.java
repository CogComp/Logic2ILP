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
    private IndicatorVariable formula;

    public Negation(IndicatorVariable formula) {
//        if(formula instanceof IndicatorVariable){
            this.formula = formula;
//        }

    }

    public IndicatorVariable getFormula() {
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

    @Override
    public FolFormula negate() {
        return formula;
    }
}
