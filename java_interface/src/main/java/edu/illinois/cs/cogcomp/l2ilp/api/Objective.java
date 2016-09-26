package edu.illinois.cs.cogcomp.l2ilp.api;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.l2ilp.util.Helper;

/**
 * Created by haowu on 5/14/16.
 */
public class Objective {
    List<Pair<String, Double>> objectives;

    public Objective(
        List<Pair<String, Double>> objectives) {
        this.objectives = objectives;
    }

    public Objective and(List<Pair<String, Double>> o){
        this.objectives.addAll(o);
        return this;
    }

    public static Objective sum(WeightedPredicate p , Collection<String> coll){
        List<Pair<String, Double>> o = new ArrayList<>();
        for (String t : coll){
            o.add(new ImmutablePair<>(p.makeIndiactor(t), p.scoreOf(t)));
        }
        return new Objective(o);
    }

}
