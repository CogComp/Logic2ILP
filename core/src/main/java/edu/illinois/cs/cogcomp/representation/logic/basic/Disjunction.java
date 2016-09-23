package edu.illinois.cs.cogcomp.representation.logic.basic;

import edu.illinois.cs.cogcomp.representation.logic.LogicFormula;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by haowu on 5/19/16.
 */
public class Disjunction implements LogicFormula {
    private List<LogicFormula> formulas;

    public Disjunction(List<LogicFormula> formulas) {
        this.formulas = formulas;
    }

    public Disjunction(LogicFormula... formulas) {
        this.formulas = new ArrayList<>();
        for (LogicFormula f : formulas){
            this.formulas.add(f);
        }
    }
    public List<LogicFormula> getFormulas() {
        return this.formulas;
    }

    @Override
    public String toString() {
        return ("(" + StringUtils
            .join(this.formulas.stream().map(Object::toString).collect(Collectors.toList()), " | ")
                + ")");
    }
    @Override
    public LogicFormula toNnf() {
        List<LogicFormula> formulas = new ArrayList<>(this.formulas.size());
        this.formulas.forEach(folFormula -> {
            formulas.add(folFormula.toNnf());
        });

        return new Disjunction(formulas);
    }

}