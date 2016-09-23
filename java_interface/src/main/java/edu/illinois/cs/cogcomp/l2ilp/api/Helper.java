package edu.illinois.cs.cogcomp.l2ilp.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by haowu on 9/23/16.
 */
public class Helper {
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
    private final static Map<Class<?>, Function<?, CCMTerm>> termMaker = new HashMap<>();

}
