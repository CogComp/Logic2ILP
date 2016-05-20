package edu.illinois.cs.cogcomp.util;

import edu.illinois.cs.cogcomp.ir.IndicatorVariable;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

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
import edu.illinois.cs.cogcomp.ir.fol.norm.Conjunction;
import edu.illinois.cs.cogcomp.ir.fol.norm.Disjunction;
import edu.illinois.cs.cogcomp.ir.fol.norm.Negation;
import edu.illinois.cs.cogcomp.ir.fol.quantifier.AtLeast;
import edu.illinois.cs.cogcomp.ir.fol.quantifier.AtMost;
import edu.illinois.cs.cogcomp.ir.fol.quantifier.Exist;
import edu.illinois.cs.cogcomp.ir.fol.quantifier.Forall;

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

    public static <X> FolFormula exist(Collection<X> colls, Function<X, FolFormula> fs){
        return new Exist(colls.stream().map(fs).collect(Collectors.toList()));
    }

    public static <X> FolFormula atLeast(int k ,Collection<X> colls, Function<X, FolFormula> fs){
        return new AtLeast(k, colls.stream().map(fs).collect(Collectors.toList()));
    }

    public static <X> FolFormula atMost(int k ,Collection<X> colls, Function<X, FolFormula> fs){
        return new AtMost(k, colls.stream().map(fs).collect(Collectors.toList()));
    }

    public static <X> FolFormula forall(Collection<X> colls, Function<X, FolFormula> fs){
        return new Forall(colls.stream().map(fs).collect(Collectors.toList()));
    }


    public static FolFormula not(FolFormula f) {
        if (f instanceof IndicatorVariable){
            return new Negation((IndicatorVariable)f);
        }else{
            return null;
        }
    }

    public static FolFormula and(FolFormula... fs) {
        List<FolFormula> subs = new ArrayList<>();
        Collections.addAll(subs, fs);
        return new Conjunction(subs);
    }

    public static FolFormula or(FolFormula... fs) {
        List<FolFormula> subs = new ArrayList<>();
        Collections.addAll(subs, fs);
        return new Disjunction(subs);
    }

    public static FolFormula imply(FolFormula p, FolFormula q) {
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

    public static <X> List<FolFormula> apply(List<X> coll,
                                             Function<X, List<FolFormula>> f) {
        return coll.stream().flatMap(x -> f.apply(x).stream()).collect(Collectors.toList());
    }

}
