package edu.illinois.cs.cogcomp.l2ilp.setcover;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.infer.ilp.BeamSearch;
import edu.illinois.cs.cogcomp.infer.ilp.GurobiHook;
import edu.illinois.cs.cogcomp.l2ilp.api.InferenceProblem;
import edu.illinois.cs.cogcomp.l2ilp.api.Objective;
import edu.illinois.cs.cogcomp.l2ilp.api.WeightedPredicate;
import edu.illinois.cs.cogcomp.l2ilp.api.constraint.ConstraintFunction;
import edu.illinois.cs.cogcomp.l2ilp.inference.ilp.representation.ILPProblem;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;

import static edu.illinois.cs.cogcomp.l2ilp.api.Helper.argmin;
import static edu.illinois.cs.cogcomp.l2ilp.util.Helper.exist;
import static edu.illinois.cs.cogcomp.l2ilp.util.Helper.or;


/**
 * Created by haowu on 5/13/16.
 */
public class SetCover {

    public static Map<String, List<String>> parseData(String cityData) {
        return parseData(cityData.split("\n"));
    }

    public static Map<String, List<String>> parseData(String[] cityData) {
        // Now we created a list of object.
        Map<String, List<String>> adjacent = new HashMap<>();
        for (String line : cityData) {
            String id = line.split(" ")[0].trim();
            String neiborStr = line.substring(id.length()).trim();
            List<String> adjs = new ArrayList<>();
            if(!neiborStr.isEmpty()){
                for (String n : neiborStr.split(" ")) {
                    adjs.add(n);
                }
            }
            adjacent.put(id, adjs);
        }
        return adjacent;

    }

    public static void main(String[] args) {
        // The original data.
        String[] cityData = ("1 4 5 7 8\n"
                             + "2 5 10\n"
                             + "3 12\n"
                             + "4 1 7 13\n"
                             + "5 1 2 13\n"
                             + "6 9 10 13\n"
                             + "7 1 4 10\n"
                             + "8 1\n"
                             + "9 6 10\n"
                             + "10 2 6 7 9 13\n"
                             + "11 \n"
                             + "12 3\n"
                             + "13 4 5 6 10\n"
                             + "14").split("\n");

        Map<String, List<String>> adjacent = parseData(cityData);
        Collection<String> cityBlocks = adjacent.keySet();

        WeightedPredicate hasStation = new WeightedPredicate("hasStation") {
            @Override
            public double scoreOf(String t) {
                return 1;
            }
        };

        ConstraintFunction coverageConstraints =
            new ConstraintFunction(x ->
                                   {
                                       LogicFormula hasStationOnX = hasStation.on(x);
                                       LogicFormula
                                           hasStationOnAdjacents =
                                           exist(adjacent.get(x), z -> hasStation.on(z));
                                       return or(hasStationOnX, hasStationOnAdjacents);
                                   }
            );

        InferenceProblem problem = argmin(Objective.sum(hasStation, cityBlocks)).
            subjectTo(coverageConstraints.of(cityBlocks)).getProblem();
//        Uncomment the following line to print the constraint in logic form.
//        problem.printConstraints();

        ILPProblem ilp = problem.getProblem(new GurobiHook());
        System.out.println("Print ");
        System.out.println(ilp.toString());

        try {
            ilp.solve();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Solution : ");
        cityBlocks.stream().filter(n -> ilp.getBooleanValue(hasStation.on(n))).forEachOrdered(n -> {
            System.out.println("Should select Neighborhood " + n);
        });

    }

}
