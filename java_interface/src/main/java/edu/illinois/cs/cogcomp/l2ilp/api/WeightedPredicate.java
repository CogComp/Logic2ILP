package edu.illinois.cs.cogcomp.l2ilp.api;

import edu.illinois.cs.cogcomp.l2ilp.inference.ilp.representation.ILPProblem;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.BooleanVariable;

/**
 * Created by haowu on 4/23/16.
 */
public abstract class WeightedPredicate {

    private ILPProblem result;
    private String id;

    public WeightedPredicate(String id) {
        this.id = id;
    }

    public void setResult(ILPProblem result) {
        this.result = result;
    }

    public int getAssignment(String term) {
        String name = this.makeIndiactor(term);
        if (result.getBooleanValue(name)) {
            return 1;
        } else {
            return 0;
        }
    }


    public String makeIndiactor(String t) {
        return this.id + "$" + t;
    }

    public BooleanVariable on(String t) {
        return BooleanVariable.getBooleanVariable(makeIndiactor(t));
    }

    public String getId() {
        return id;
    }

    public abstract double scoreOf(String t);

}
