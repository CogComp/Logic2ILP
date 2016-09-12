package edu.illinois.cs.cogcomp.ir.fol.quantifier;

import java.util.ArrayList;
import java.util.List;

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
    public FolFormula toNnf() {
        List<FolFormula> formulas = new ArrayList<>(this.formulas.size());
        this.formulas.forEach(folFormula -> {
            formulas.add(folFormula.toNnf());
        });

        return new AtLeast(this.k, formulas);
    }

}
