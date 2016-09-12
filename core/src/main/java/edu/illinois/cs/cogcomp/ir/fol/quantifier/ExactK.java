package edu.illinois.cs.cogcomp.ir.fol.quantifier;

import java.util.ArrayList;
import java.util.List;

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
    public FolFormula toNnf() {
        List<FolFormula> formulas = new ArrayList<>(this.formulas.size());
        this.formulas.forEach(folFormula -> {
            formulas.add(folFormula.toNnf());
        });

        return new ExactK(this.k, formulas);
    }

}
