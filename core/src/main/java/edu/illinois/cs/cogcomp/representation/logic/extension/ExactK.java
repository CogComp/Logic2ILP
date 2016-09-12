package edu.illinois.cs.cogcomp.representation.logic.extension;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs.cogcomp.representation.logic.LogicFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class ExactK implements LogicFormula {

    private int k;
    private List<LogicFormula> formulas;

    public ExactK(int k, List<LogicFormula> formulas) {
        this.k = k;
        this.formulas = formulas;
    }


    public ExactK(int k, LogicFormula... formulas) {
        this.formulas = new ArrayList<>();
        for (LogicFormula f : formulas) {
            this.formulas.add(f);
        }
        this.k = k;
    }

    public int getK() {
        return k;
    }

    public List<LogicFormula> getFormulas() {
        return this.formulas;
    }

    @Override
    public LogicFormula toNnf() {
        List<LogicFormula> formulas = new ArrayList<>(this.formulas.size());
        this.formulas.forEach(folFormula -> {
            formulas.add(folFormula.toNnf());
        });

        return new ExactK(this.k, formulas);
    }

}
