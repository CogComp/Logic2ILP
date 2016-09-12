package edu.illinois.cs.cogcomp.inference;

import java.util.Map;

import edu.illinois.cs.cogcomp.infer.ilp.ILPSolver;
import edu.illinois.cs.cogcomp.inference.repr.ILPProblem;
import edu.illinois.cs.cogcomp.ir.IndicatorVariable;

/**
 * Created by haowu on 4/23/16.
 */
public abstract class CCMPredicate<X> {

    private ILPProblem result;

    public void setResult(ILPProblem result) {
        this.result = result;
    }

    public int getAssignment(CCMTerm term) {
        String name = getID() + "$" + term.getID();
        if (result.getBooleanValue(name)) {
            return 1;
        } else {
            return 0;
        }
    }

    public abstract String getID();

    public abstract double getScore(CCMTerm<X> term);

    public String makeIndiactor(CCMTerm t) {
        return getID() + "$" + t.getID();
    }

    public IndicatorVariable on(CCMTerm t) {
        return new IndicatorVariable(this.getID(), t.getID());
    }

}
