package edu.illinois.cs.cogcomp.util;

import edu.illinois.cs.cogcomp.representation.IndicatorVariable;
import edu.illinois.cs.cogcomp.representation.logic.LogicFormula;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.inference.CCMPredicate;
import edu.illinois.cs.cogcomp.inference.CCMTerm;
import edu.illinois.cs.cogcomp.inference.ILPBaseCCMProblemBuilder;
import edu.illinois.cs.cogcomp.inference.Objective;
import edu.illinois.cs.cogcomp.representation.logic.basic.Conjunction;
import edu.illinois.cs.cogcomp.representation.logic.basic.Disjunction;
import edu.illinois.cs.cogcomp.representation.logic.basic.Negation;
import edu.illinois.cs.cogcomp.representation.logic.extension.AtLeast;
import edu.illinois.cs.cogcomp.representation.logic.extension.AtMost;

/**
 * Created by haowu on 5/14/16.
 */
public class Helper {

    private final static Map<Class<?>, Function<?, CCMTerm>> termMaker = new HashMap<>();

    public static <X> void Register(Class<X> clazz, Function<X,String> getIDFun){
        termMaker.put(clazz, new Function<X, CCMTerm>() {
            @Override
            public CCMTerm<X> apply(X x) {
                return new CCMTerm<X>() {
                    @Override
                    public String getID() {
                        return getIDFun.apply(x);
                    }

                    @Override
                    public X getInstance() {
                        return x;
                    }
                };
            }
        });
    }

    public static <X> CCMTerm<X> T(X x){
        Function<X, CCMTerm> f = (Function<X, CCMTerm>) termMaker.get(x.getClass());
        return (CCMTerm<X>) f.apply(x);
    }

    public static <X> LogicFormula exist(Collection<X> colls, Function<X, LogicFormula> fs){
        return new Disjunction(colls.stream().map(fs).collect(Collectors.toList()));
    }

    public static <X> LogicFormula atLeast(int k , Collection<X> colls, Function<X, LogicFormula> fs){
        return new AtLeast(k, colls.stream().map(fs).collect(Collectors.toList()));
    }

    public static <X> LogicFormula atMost(int k , Collection<X> colls, Function<X, LogicFormula> fs){
        return new AtMost(k, colls.stream().map(fs).collect(Collectors.toList()));
    }

    public static <X> LogicFormula forall(Collection<X> colls, Function<X, LogicFormula> fs){
        return new Conjunction(colls.stream().map(fs).collect(Collectors.toList()));
    }


    public static LogicFormula not(LogicFormula f) {
        if (f instanceof IndicatorVariable){
            return new Negation((IndicatorVariable)f);
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

    public static <X> CCMPredicate<X> makePredicate(String name, Function<X,Double> score){
        return new CCMPredicate<X>() {
            @Override
            public String getID() {
                return name;
            }

            @Override
            public double getScore(CCMTerm<X> term) {
                return score.apply(term.getInstance());
            }
        };
    }


    public static ILPBaseCCMProblemBuilder argmin(Objective objective){
        ILPBaseCCMProblemBuilder builder = new ILPBaseCCMProblemBuilder();
        builder.setObjective(objective.negative());
        return builder;
    }

    public static ILPBaseCCMProblemBuilder argmax(Objective objective){
        ILPBaseCCMProblemBuilder builder = new ILPBaseCCMProblemBuilder();
        builder.setObjective(objective);
        return builder;
    }

    public static <X> List<LogicFormula> apply(List<X> coll,
                                               Function<X, List<LogicFormula>> f) {
        return coll.stream().flatMap(x -> f.apply(x).stream()).collect(Collectors.toList());
    }

}
