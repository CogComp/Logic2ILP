package edu.illinois.cs.cogcomp.ir.fol.quantifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.ir.IndicatorVariable;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class ExactK implements FolFormula {

    private int k;
    private List<FolFormula> formulas;

    public ExactK(int k, List<FolFormula> formulas) {
        this.k = k;
        this.formulas = formulas;
    }


    public ExactK(int k, FolFormula... formulas) {
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
        int counter = 0;
        int unk = this.formulas.size();
        for (FolFormula f : formulas) {
            if (unk + counter < k) {
                // if all unk eval to True, we still won't reach k, return true;
                return false;
            }
            if (f.eval(assignment)) {
                counter++;
                if (counter > k) {
                    return false;
                }
            }
            unk--;
        }

        return counter == k;
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
