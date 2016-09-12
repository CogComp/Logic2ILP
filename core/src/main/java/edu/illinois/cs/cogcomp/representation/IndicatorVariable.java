package edu.illinois.cs.cogcomp.representation;

import edu.illinois.cs.cogcomp.representation.logic.LogicFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class IndicatorVariable implements LogicFormula {
    private final String p;
    private final String t;

    public IndicatorVariable(String predicateId, String termId) {
        this.p = predicateId;
        this.t= termId;
    }

    public String predicateId(){
        return this.p;
    }

    public String termId(){
        return this.t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IndicatorVariable that = (IndicatorVariable) o;

        if (p != null ? !p.equals(that.p)
                                : that.p != null) {
            return false;
        }
        return t != null ? t.equals(that.t) : that.t == null;

    }

    @Override
    public int hashCode() {
        int result = p != null ? p.hashCode() : 0;
        result = 31 * result + (t != null ? t.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", p,t);
    }

    @Override
    public LogicFormula toNnf() {
        return this;
    }

}
