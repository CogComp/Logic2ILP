package edu.illinois.cs.cogcomp.ir.fol.quantifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.ir.IndicatorVariable;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class AtLeast implements FolFormula {

    private int k;
    private List<FolFormula> formulas;

    public AtLeast(int k, List<FolFormula> formulas) {
        if (k < 0) {
            throw new RuntimeException("K has to be non-negative.");
        }

        this.formulas = formulas;
        this.k = k;
    }

    public AtLeast(int k, FolFormula... formulas) {
        if (k < 0) {
            throw new RuntimeException("K has to be non-negative.");
        }

        this.formulas = new ArrayList<>();
        for (FolFormula f : formulas){
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

        for (FolFormula f : this.formulas){
            // If all unknown is ture, and we still cannot reach k,
            // return false.
            if (unknown + counter < k){
                return false;
            }

            if (f.eval(assignment)){
                counter ++;
                if (counter >= k){
                    return true;
                }
            }
            unknown --;
        }
        return false;
    }

    @Override
    public FolFormula toNnf() {
        List<FolFormula> formulas = new ArrayList<>(this.formulas.size());
        this.formulas.forEach(folFormula -> {
            formulas.add(folFormula.toNnf());
        });

        return new AtLeast(this.k, formulas);
    }

    @Override
    public FolFormula negate() {
        throw new RuntimeException("not implemented.");
    }

}
