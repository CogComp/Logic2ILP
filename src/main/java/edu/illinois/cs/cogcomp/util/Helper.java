package edu.illinois.cs.cogcomp.util;

import net.sf.tweety.logics.fol.syntax.Conjunction;
import net.sf.tweety.logics.fol.syntax.Disjunction;
import net.sf.tweety.logics.fol.syntax.FolFormula;
import net.sf.tweety.logics.fol.syntax.Negation;

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
        return new Disjunction(colls.stream().map(fs).collect(Collectors.toList()));
    }

    public static <X> FolFormula forall(Collection<?> colls, Function<X, FolFormula> fs){
        return null;
    }


    public static FolFormula not(FolFormula f) {
        return new Negation(f);
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
