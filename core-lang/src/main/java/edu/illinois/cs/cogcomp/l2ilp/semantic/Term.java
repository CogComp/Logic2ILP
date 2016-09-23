package edu.illinois.cs.cogcomp.l2ilp.semantic;

import edu.illinois.cs.cogcomp.l2ilp.inference.CCMTerm;

/**
 * Created by haowu on 9/11/16.
 */
public class Term implements CCMTerm<String> {

    private String id;

    public Term(String id) {
        this.id = id;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getInstance() {
        return id;
    }
}
