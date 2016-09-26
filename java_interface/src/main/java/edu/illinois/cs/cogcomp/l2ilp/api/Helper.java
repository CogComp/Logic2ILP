package edu.illinois.cs.cogcomp.l2ilp.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by haowu on 9/23/16.
 */
public class Helper {

    public static ILPBaseCCMProblemBuilder argmin(Objective objective){
        ILPBaseCCMProblemBuilder builder = new ILPBaseCCMProblemBuilder();
        builder.setObjective(objective);
        builder.setArgmax(false);
        return builder;
    }

    public static ILPBaseCCMProblemBuilder argmax(Objective objective){
        ILPBaseCCMProblemBuilder builder = new ILPBaseCCMProblemBuilder();
        builder.setObjective(objective);
        builder.setArgmax(true);
        return builder;
    }

}
