package edu.illinois.cs.cogcomp.ir.fol.quantifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.ir.IndicatorVariable;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class Forall implements FolFormula {

    private List<FolFormula> formulas;

    public Forall(List<FolFormula> formulas) {
        this.formulas = formulas;
    }

    public Forall(FolFormula... formulas) {
        this.formulas = new ArrayList<>();
        for (FolFormula f : formulas){
            this.formulas.add(f);
        }
    }
    public List<FolFormula> getFormulas() {
        return formulas;
    }

    @Override
    public boolean eval(Map<IndicatorVariable, Boolean> assignment) {
        for (FolFormula f : formulas){
            if (!f.eval(assignment)){
                return false;
            }
        }
        return true;
    }
}
