package edu.illinois.cs.cogcomp.ir.fol.quantifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.ir.IndicatorVariable;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class AtMost implements FolFormula {

    private int k;
    private List<FolFormula> formulas;

    public AtMost(int k, List<FolFormula> formulas) {
        if (k < 0) {
            throw new RuntimeException("K has to be non-negative.");
        }

        this.formulas = formulas;
        this.k = k;
    }

    public AtMost(int k, FolFormula... formulas) {
        if (k < 0) {
            throw new RuntimeException("K has to be non-negative.");
        }

        this.formulas = new ArrayList<>();
        for (FolFormula f : formulas) {
            this.formulas.add(f);
        }
        this.k = k;
    }

    public List<FolFormula> getFormulas() {
        return this.formulas;
    }

    public int getK() {
        return k;
    }

    @Override
    public boolean eval(Map<IndicatorVariable, Boolean> assignment) {
        int counter = 0;
        int unknown = this.formulas.size();

        for (FolFormula f : this.formulas) {
            if (unknown + counter <= k){
                // if all unknown eval to True, we still won't reach k, return true;
                return true;
            }

            if (f.eval(assignment)) {
                counter ++;
                if (counter > k) {
                    return false;
                }
            }
            unknown --;
        }
        return true;
    }

    @Override
    public FolFormula toNnf() {
        List<FolFormula> formulas = new ArrayList<>(this.formulas.size());
        this.formulas.forEach(folFormula -> {
            formulas.add(folFormula.toNnf());
        });

        return new AtMost(this.k, formulas);
    }

    @Override
    public FolFormula negate() {
        throw new RuntimeException("not implemented.");
    }

}
