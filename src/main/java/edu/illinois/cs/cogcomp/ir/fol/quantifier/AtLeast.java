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
        this.formulas = formulas;
        this.k = k;
    }

    public AtLeast(int k, FolFormula... formulas) {
        this.formulas = new ArrayList<>();
        for (FolFormula f : formulas){
            this.formulas.add(f);
        }
        this.k = k;
    }


    public List<FolFormula> getFormulas() {
        return formulas;
    }

    public int getK() {
        return k;
    }

    @Override
    public boolean eval(Map<IndicatorVariable, Boolean> assignment) {
        int counter = 0;
        int unk = this.formulas.size();

        for (FolFormula f : formulas){
            // If all unknown is ture, and we still cannot reach k,
            // return false.
            if (unk + counter < k){
                return false;
            }

            if (f.eval(assignment)){
                counter ++;
                if (counter >= k){
                    return true;
                }
            }
            unk--;

        }
        return false;
    }

    @Override
    public FolFormula toNnf() {
        return null;
    }

}
