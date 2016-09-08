package edu.illinois.cs.cogcomp.ir.fol.norm;

import edu.illinois.cs.cogcomp.ir.fol.quantifier.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.ir.IndicatorVariable;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class Negation implements FolFormula {
    private FolFormula formula;

    public Negation(FolFormula formula) {
            this.formula = formula;
    }

    public FolFormula getFormula() {
        return this.formula;
    }

    @Override
    public boolean eval(Map<IndicatorVariable, Boolean> assignment) {
        return !this.formula.eval(assignment);
    }

    @Override
    public String toString() {
        return "!"+this.formula.toString();
    }

    @Override
    public FolFormula toNnf() {
        if (this.formula instanceof IndicatorVariable) {
            return this;
        }
        else if (this.formula instanceof Negation) {
            return ((Negation) this.formula).getFormula();
        }
        else if (this.formula instanceof Conjunction) {
            Conjunction conjunction = (Conjunction) this.formula;

            List<FolFormula> formulas = new ArrayList<>(conjunction.getFormulas().size());
            conjunction.getFormulas().forEach(folFormula -> {
                formulas.add(new Negation(folFormula).toNnf());
            });

            return new Disjunction(formulas);
        }
        else if (this.formula instanceof Disjunction) {
            Disjunction disjunction = (Disjunction) this.formula;

            List<FolFormula> formulas = new ArrayList<>(disjunction.getFormulas().size());
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
        else if (this.formula instanceof Exist) {
            Exist exist = (Exist) this.formula;

            List<FolFormula> formulas = new ArrayList<>(exist.getFormulas().size());
            exist.getFormulas().forEach(folFormula -> {
                formulas.add(new Negation(folFormula).toNnf());
            });

            return new Forall(formulas);
        }
        else if (this.formula instanceof Forall) {
            Forall forall = (Forall) this.formula;

            List<FolFormula> formulas = new ArrayList<>(forall.getFormulas().size());
            forall.getFormulas().forEach(folFormula -> {
                formulas.add(new Negation(folFormula).toNnf());
            });

            return new Exist(formulas);
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

    @Override
    public FolFormula negate() {
        throw new RuntimeException("not implemented.");
    }
}
