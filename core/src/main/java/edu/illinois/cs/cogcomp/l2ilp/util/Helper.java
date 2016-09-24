package edu.illinois.cs.cogcomp.l2ilp.util;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.BooleanVariable;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Conjunction;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Disjunction;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Negation;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.AtLeastK;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.AtMostK;

/**
 * Created by haowu on 5/14/16.
 */
public class Helper {

    public static <X> LogicFormula exist(Collection<X> colls, Function<X, LogicFormula> fs){
        return new Disjunction(colls.stream().map(fs).collect(Collectors.toList()));
    }

    public static <X> LogicFormula atLeast(int k , Collection<X> colls, Function<X, LogicFormula> fs){
        return new AtLeastK(k, colls.stream().map(fs).collect(Collectors.toList()));
    }

    public static <X> LogicFormula atMost(int k , Collection<X> colls, Function<X, LogicFormula> fs){
        return new AtMostK(k, colls.stream().map(fs).collect(Collectors.toList()));
    }

    public static <X> LogicFormula forall(Collection<X> colls, Function<X, LogicFormula> fs){
        return new Conjunction(colls.stream().map(fs).collect(Collectors.toList()));
    }


    public static LogicFormula not(LogicFormula f) {
        if (f instanceof BooleanVariable){
            return new Negation((BooleanVariable)f);
        }else{
            return null;
        }
    }

    public static LogicFormula and(LogicFormula... fs) {
        List<LogicFormula> subs = new ArrayList<>();
        Collections.addAll(subs, fs);
        return new Conjunction(subs);
    }

    public static LogicFormula or(LogicFormula... fs) {
        List<LogicFormula> subs = new ArrayList<>();
        Collections.addAll(subs, fs);
        return new Disjunction(subs);
    }

    public static LogicFormula or(List<LogicFormula> fs) {
        return new Disjunction(fs);
    }

    public static LogicFormula imply(LogicFormula p, LogicFormula q) {
        return or(not(p), q);
    }


    public static <X> List<LogicFormula> apply(List<X> coll,
                                               Function<X, List<LogicFormula>> f) {
        return coll.stream().flatMap(x -> f.apply(x).stream()).collect(Collectors.toList());
    }

}
