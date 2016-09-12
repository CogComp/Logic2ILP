package edu.illinois.cs.cogcomp.inference.constraint;

import edu.illinois.cs.cogcomp.representation.logic.LogicFormula;

import java.util.Collection;
import java.util.List;

/**
 * Created by haowu on 5/14/16.
 */
public interface ConstraintProvider<X> {
    List<LogicFormula> of(X x);
    List<LogicFormula> of(Collection<X> xs);
}
