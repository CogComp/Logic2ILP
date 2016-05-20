package edu.illinois.cs.cogcomp.ir.fol.quantifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.ir.IndicatorVariable;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class NotK implements FolFormula {

    private int k;
    private List<FolFormula> formulas;

    public NotK(int k, List<FolFormula> formulas) {
        this.k = k;
        this.formulas = formulas;
    }


    public NotK(int k, FolFormula... formulas) {
        this.formulas = new ArrayList<>();
        for (FolFormula f : formulas) {
            this.formulas.add(f);
        }
        this.k = k;
    }

    public int getK() {
        return k;
    }

    public List<FolFormula> getFormulas() {
        return formulas;
    }

    @Override
    public boolean eval(Map<IndicatorVariable, Boolean> assignment) {
        return false;
    }

    @Override
    public FolFormula toNnf() {
        return null;
    }

    @Override
    public FolFormula negate() {
        return null;
    }
}
