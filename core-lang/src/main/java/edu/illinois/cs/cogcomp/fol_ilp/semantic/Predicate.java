package edu.illinois.cs.cogcomp.fol_ilp.semantic;

import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.inference.CCMPredicate;
import edu.illinois.cs.cogcomp.inference.CCMTerm;

/**
 * Created by haowu on 9/11/16.
 */
public class Predicate extends CCMPredicate<String>{

    private String id;
    private Map<String,Double> map;

    public Predicate(String id) {
        super();
        this.id = id;
    }

    public void addMap(String k, Double v){
        this.map.put(k,v);
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public double getScore(CCMTerm<String> term) {
        return this.map.get(term.getID());
    }
}
