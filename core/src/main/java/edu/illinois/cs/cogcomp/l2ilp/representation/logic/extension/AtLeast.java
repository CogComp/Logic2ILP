package edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class AtLeast implements LogicFormula {

    private int k;
    private List<LogicFormula> formulas;

    public AtLeast(int k, List<LogicFormula> formulas) {
        if (k < 0) {
            throw new RuntimeException("K has to be non-negative.");
        }

        this.formulas = formulas;
        this.k = k;
    }

    public AtLeast(int k, LogicFormula... formulas) {
        if (k < 0) {
            throw new RuntimeException("K has to be non-negative.");
        }

        this.formulas = new ArrayList<>();
        for (LogicFormula f : formulas){
            this.formulas.add(f);
        }
        this.k = k;
    }


    public List<LogicFormula> getFormulas() {
        return this.formulas;
    }

    public int getK() {
        return k;
    }

    @Override
    public LogicFormula toNnf() {
        List<LogicFormula> formulas = new ArrayList<>(this.formulas.size());
        this.formulas.forEach(folFormula -> {
            formulas.add(folFormula.toNnf());
        });

        return new AtLeast(this.k, formulas);
    }

}
