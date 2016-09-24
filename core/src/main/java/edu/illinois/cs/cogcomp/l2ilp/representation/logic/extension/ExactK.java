package edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.BooleanLiteral;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class ExactK implements LogicFormula {

    private int k;
    private List<LogicFormula> formulas;

    public ExactK(int k, List<LogicFormula> formulas) {
        if (k < 0) {
            throw new RuntimeException("K has to be non-negative.");
        }

        if (formulas == null) {
            throw new RuntimeException("List of formulas has to be non-null.");
        }

        this.k = k;
        this.formulas = formulas;
    }

    public ExactK(int k, LogicFormula... formulas) {
        if (k < 0) {
            throw new RuntimeException("K has to be non-negative.");
        }

        this.k = k;
        this.formulas = new ArrayList<>(formulas.length);

        for (LogicFormula logicFormula : formulas) {
            this.formulas.add(logicFormula);
        }
    }

    public int getK() {
        return k;
    }

    public List<LogicFormula> getFormulas() {
        return this.formulas;
    }

    @Override
    public LogicFormula negate() {
        return new NotExactK(this.k, this.formulas);
    }

    @Override
    public LogicFormula toNnf() {
        return new ExactK(this.k, this.formulas.stream().map(LogicFormula::toNnf).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return ("Exact_" + this.k + "_of_" + this.formulas.size() + "("
                + StringUtils.join(this.formulas.stream().map(Object::toString).collect(Collectors.toList()), ", ")
                + ")");
    }
}