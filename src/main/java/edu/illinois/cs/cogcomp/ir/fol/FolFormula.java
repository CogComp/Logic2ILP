package edu.illinois.cs.cogcomp.ir.fol;

import java.util.Map;

import edu.illinois.cs.cogcomp.ir.IndicatorVariable;

/**
 * Created by haowu on 5/19/16.
 */
public interface FolFormula {
    boolean eval(Map<IndicatorVariable, Boolean> assignment);
    FolFormula toNnf();
    FolFormula negate();
}
