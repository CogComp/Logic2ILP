package edu.illinois.cs.cogcomp.inference;

import net.sf.tweety.logics.commons.syntax.Constant;
import net.sf.tweety.logics.commons.syntax.interfaces.Term;

/**
 * Created by haowu on 4/23/16.
 */
public interface CCMTerm<X> {
    String getID();
    X getInstance();
}
