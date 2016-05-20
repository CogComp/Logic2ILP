package edu.illinois.cs.cogcomp.inference;

import net.sf.javailp.Result;

import edu.illinois.cs.cogcomp.ir.IndicatorVariable;

/**
 * Created by haowu on 4/23/16.
 */
public abstract class CCMPredicate<X> {

    private Result result;

    public void setResult(Result result) {
        this.result = result;
    }

    public int getAssignment(CCMTerm term) {
        return result.get(getID() + "$" + term.getID()).intValue();
    }

    public abstract String getID();

    public abstract double getScore(CCMTerm<X> term);

    public String _(CCMTerm t){
        return getID()+"$"+t.getID();
    }

    public IndicatorVariable on(CCMTerm t){
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
