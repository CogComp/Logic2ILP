package edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen386 on 8/26/16.
 */
public class NotExactK implements LogicFormula {

    private int k;
    private List<LogicFormula> formulas;

    public NotExactK(int k, List<LogicFormula> formulas) {
        this.k = k;
        this.formulas = formulas;
    }


    public NotExactK(int k, LogicFormula... formulas) {
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

        return new NotExactK(this.k, formulas);
    }

}
