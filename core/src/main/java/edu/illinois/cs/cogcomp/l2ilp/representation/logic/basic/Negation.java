package edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.*;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.BooleanVariable;

/**
 * Created by haowu on 5/19/16.
 */
public class Negation implements LogicFormula {
    private LogicFormula formula;

    public Negation(LogicFormula formula) {
            this.formula = formula;
    }

    public LogicFormula getFormula() {
        return this.formula;
    }

    @Override
    public String toString() {
        return "!"+this.formula.toString();
    }

    @Override
    public LogicFormula toNnf() {
        if (this.formula instanceof BooleanVariable) {
            return this;
        }
        else if (this.formula instanceof Negation) {
            return ((Negation) this.formula).getFormula();
        }
        else if (this.formula instanceof Conjunction) {
            Conjunction conjunction = (Conjunction) this.formula;

            List<LogicFormula> formulas = new ArrayList<>(conjunction.getFormulas().size());
            conjunction.getFormulas().forEach(folFormula -> {
                formulas.add(new Negation(folFormula).toNnf());
            });

            return new Disjunction(formulas);
        }
        else if (this.formula instanceof Disjunction) {
            Disjunction disjunction = (Disjunction) this.formula;

            List<LogicFormula> formulas = new ArrayList<>(disjunction.getFormulas().size());
            disjunction.getFormulas().forEach(folFormula -> {
                formulas.add(new Negation(folFormula).toNnf());
            });

            return new Conjunction(formulas);
        }
        else if (this.formula instanceof AtLeast) {
            AtLeast atLeast = (AtLeast) this.formula;
            return new AtMost(atLeast.getK() - 1, atLeast.getFormulas());
        }
        else if (this.formula instanceof AtMost) {
            AtMost atMost = (AtMost) this.formula;
            return new AtLeast(atMost.getK() + 1, atMost.getFormulas());
        }
        else if (this.formula instanceof ExactK) {
            ExactK exactK = (ExactK) this.formula;

            return new NotExactK(exactK.getK(), exactK.getFormulas());
        }
        else if (this.formula instanceof NotExactK) {
            NotExactK notExactK = (NotExactK) this.formula;

            return new ExactK(notExactK.getK(), notExactK.getFormulas());
        }
        else {
            throw new RuntimeException();
        }
    }

}
