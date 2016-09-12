package edu.illinois.cs.cogcomp.ir.fol.quantifier;

import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen386 on 8/26/16.
 */
public class NotExactK implements FolFormula {

    private int k;
    private List<FolFormula> formulas;

    public NotExactK(int k, List<FolFormula> formulas) {
        this.k = k;
        this.formulas = formulas;
    }


    public NotExactK(int k, FolFormula... formulas) {
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

        return new NotExactK(this.k, formulas);
    }

}
