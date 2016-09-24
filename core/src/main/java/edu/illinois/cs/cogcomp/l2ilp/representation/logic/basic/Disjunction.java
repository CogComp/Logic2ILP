package edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.BooleanLiteral;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class Disjunction implements LogicFormula {

    private List<LogicFormula> formulas;

    public Disjunction(List<LogicFormula> formulas) {
        if (formulas == null) {
            throw new RuntimeException("List of formulas has to be non-null.");
        }

        this.formulas = formulas;
    }

    public Disjunction(LogicFormula... formulas) {
        this.formulas = new ArrayList<>(formulas.length);

        for (LogicFormula logicFormula : formulas) {
            this.formulas.add(logicFormula);
        }
    }

    public List<LogicFormula> getFormulas() {
        return this.formulas;
    }

    @Override
    public LogicFormula negate() {
        return new Conjunction(this.formulas.stream().map(LogicFormula::negate).collect(Collectors.toList()));
    }

    @Override
    public LogicFormula toNnf() {
        return new Disjunction(this.formulas.stream().map(LogicFormula::toNnf).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        return ("("
                + StringUtils.join(this.formulas.stream().map(Object::toString).collect(Collectors.toList()), " | ")
                + ")");
    }
}