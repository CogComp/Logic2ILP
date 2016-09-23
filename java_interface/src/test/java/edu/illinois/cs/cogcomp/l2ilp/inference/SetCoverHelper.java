package edu.illinois.cs.cogcomp.l2ilp.inference;

import java.util.function.Function;

/**
 * Created by haowu on 5/15/16.
 */
public class SetCoverHelper {
    public static Function<SetCover.Neighborhood,String> getId =
        SetCover.Neighborhood::getId;

    public static Function<SetCover.Neighborhood,Double> ConstanceScoreOne =
        neighborhood -> 1.0;
}
