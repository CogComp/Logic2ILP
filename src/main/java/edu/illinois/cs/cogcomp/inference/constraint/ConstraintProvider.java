package edu.illinois.cs.cogcomp.inference.constraint;

import net.sf.tweety.logics.fol.syntax.FolFormula;

import java.util.Collection;
import java.util.List;

/**
 * Created by haowu on 5/14/16.
 */
public interface ConstraintProvider<X> {
    List<FolFormula> of(X x);
    List<FolFormula> of(Collection<X> xs);
}
