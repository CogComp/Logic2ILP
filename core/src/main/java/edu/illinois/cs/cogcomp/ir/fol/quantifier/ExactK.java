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
        return this.formulas;
    }

    @Override
    public boolean eval(Map<IndicatorVariable, Boolean> assignment) {
        int counter = 0;
        int unknown = this.formulas.size();

        for (FolFormula f : this.formulas) {
            if (unknown + counter < k) {
                // if all unknown eval to True, we still won't reach k, return false;
                return false;
            }

            if (f.eval(assignment)) {
                counter ++;
                if (counter > k) {
                    return false;
                }
            }
            unknown --;
        }
        return counter == k;
    }

    @Override
    public FolFormula toNnf() {
        List<FolFormula> formulas = new ArrayList<>(this.formulas.size());
        this.formulas.forEach(folFormula -> {
            formulas.add(folFormula.toNnf());
        });

        return new ExactK(this.k, formulas);
    }

    @Override
    public FolFormula negate() {
        throw new RuntimeException("not implemented.");
    }
}
