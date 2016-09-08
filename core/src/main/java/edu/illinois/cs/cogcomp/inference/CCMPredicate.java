package edu.illinois.cs.cogcomp.inference;

import java.util.Map;

import edu.illinois.cs.cogcomp.infer.ilp.ILPSolver;
import edu.illinois.cs.cogcomp.ir.IndicatorVariable;

/**
 * Created by haowu on 4/23/16.
 */
public abstract class CCMPredicate<X> {

    private ILPSolver result;
    private Map<String, Integer> lexicon;

    public void setResult(ILPSolver result, Map<String, Integer> lexicon) {
        this.result = result;
        this.lexicon = lexicon;
    }

    public int getAssignment(CCMTerm term) {
        String name = getID() + "$" + term.getID();
        int idx = lexicon.get(name);
        if (result.getBooleanValue(idx)) {
            return 1;
        } else {
            return 0;
        }
    }

    public abstract String getID();

    public abstract double getScore(CCMTerm<X> term);

    public String _(CCMTerm t) {
        return getID() + "$" + t.getID();
    }

    public IndicatorVariable on(CCMTerm t) {
        return new IndicatorVariable(this.getID(), t.getID());
    }

//    private static Constant getConstant(CCMTerm t){
//        return new Constant(t.getID());
//    }
//
//    private static Predicate getPredicate(CCMPredicate p){
//        return new Predicate(p.getID(),1);
//    }
}
