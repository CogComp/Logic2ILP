package edu.illinois.cs.cogcomp.ir;

import java.util.Map;

import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class IndicatorVariable implements FolFormula {
    private final String predicateId;
    private final String termId;

    public IndicatorVariable(String predicateId, String termId) {
        this.predicateId = predicateId;
        this.termId = termId;
    }

    public String predicateId(){
        return this.predicateId();
    }

    public String termId(){
        return this.termId;
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

        if (predicateId != null ? !predicateId.equals(that.predicateId)
                                : that.predicateId != null) {
            return false;
        }
        return termId != null ? termId.equals(that.termId) : that.termId == null;

    }

    @Override
    public int hashCode() {
        int result = predicateId != null ? predicateId.hashCode() : 0;
        result = 31 * result + (termId != null ? termId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("1_{%s(%s)}", predicateId,termId);
    }

    @Override
    public boolean eval(Map<IndicatorVariable, Boolean> assignment) {
        return assignment.get(this);
    }
}
