package edu.illinois.cs.cogcomp.ir.fol.norm;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.ir.IndicatorVariable;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class Conjunction implements FolFormula {

    private List<FolFormula> formulas;

    /**
     * Return the conjunction of a list of Fol formula.
     * @param formulas list of Fol formula
     */
    public Conjunction(List<FolFormula> formulas) {
        this.formulas = formulas;
    }

    public Conjunction(FolFormula... formulas) {
        this.formulas = new ArrayList<>();
        for (FolFormula f : formulas) {
            this.formulas.add(f);
        }
    }

    public List<FolFormula> getFormulas() {
        return formulas;
    }

    @Override
    public boolean eval(Map<IndicatorVariable, Boolean> assignment) {
        for (FolFormula f : formulas) {
            if (!f.eval(assignment)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public FolFormula toNnf() {
        return null;
    }

    @Override
    public FolFormula negate() {
        return new Disjunction(formulas.stream().map(f -> f.negate()).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return ("(" + StringUtils
            .join(this.formulas.stream().map(Object::toString).collect(Collectors.toList()), " & ")
                + ")");
    }
}
