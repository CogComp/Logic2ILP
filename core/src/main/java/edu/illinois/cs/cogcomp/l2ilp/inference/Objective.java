package edu.illinois.cs.cogcomp.l2ilp.inference;

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
    List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objectives;

    public Objective(
        List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objectives) {
        this.objectives = objectives;
    }

    public Objective negative() {
        List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> negativeObjectives = new ArrayList<>();
        for (Pair<CCMPredicate, Collection<? extends CCMTerm>> pair : objectives){
            CCMPredicate m = pair.getKey();
            CCMPredicate np = new CCMPredicate() {
                @Override
                public String getID() {
                    return m.getID();
                }

                @Override
                public double getScore(CCMTerm term) {
                    return -m.getScore(term);
                }
            };
            negativeObjectives.add(new ImmutablePair<>(np,pair.getRight()));
        }

        return new Objective(negativeObjectives);
    }

    public Objective and(List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> o){
        this.objectives.addAll(o);
        return this;
    }

    public static <X> Objective sum(CCMPredicate p , Collection<X> coll){
        List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> o = new ArrayList<>();
        o.add(new ImmutablePair<>(p ,coll.stream().map(Helper::T).collect(Collectors.toList())));
        return new Objective(o);
    }

}
