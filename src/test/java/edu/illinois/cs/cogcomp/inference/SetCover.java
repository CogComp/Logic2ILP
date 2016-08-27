package edu.illinois.cs.cogcomp.inference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.inference.constraint.ConstraintFunction;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

import static edu.illinois.cs.cogcomp.util.Helper.Register;
import static edu.illinois.cs.cogcomp.util.Helper.T;
import static edu.illinois.cs.cogcomp.util.Helper.apply;
import static edu.illinois.cs.cogcomp.util.Helper.argmin;
import static edu.illinois.cs.cogcomp.util.Helper.exist;
import static edu.illinois.cs.cogcomp.util.Helper.makePredicate;
import static edu.illinois.cs.cogcomp.util.Helper.or;


/**
 * Created by haowu on 5/13/16.
 */
public class SetCover {


    public static class Neighborhood {

        private static final Map<String, Neighborhood> NeighborhoodMap = new HashMap<>();

        public static Neighborhood makeNeighborhood(String line, boolean createNew) {
            if (createNew) {
                String id = line.split(" ")[0].trim();
                String neiborStr = line.substring(id.length()).trim();
                if (neiborStr.isEmpty()){
                    return new Neighborhood(id, new String[0]);
                }
                String[] adjacent = neiborStr.split(" ");
                return new Neighborhood(id, adjacent);
            } else {
                return NeighborhoodMap.get(line.trim());
            }
        }

        public static Neighborhood makeNeighborhood(String line) {
            return makeNeighborhood(line,true);
        }

        private String[] adjacent;
        private String id;

        private Neighborhood(String id, String[] adjacent) {
            this.id = id;
            this.adjacent = adjacent;
            NeighborhoodMap.put(id, this);
        }

        public List<Neighborhood> getAdjacent() {
            return Arrays.stream(this.adjacent).map(x -> makeNeighborhood(x, false))
                .collect(Collectors.toList());
        }

        public String getId() {
            return id;
        }

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

        // Now we created a list of object.
        List<Neighborhood>
            city =
            Arrays.stream(cityData).map(Neighborhood::makeNeighborhood)
                .collect(Collectors.toList());

        Register(Neighborhood.class, Neighborhood::getId);

        // Create a predicate
        CCMPredicate<Neighborhood> hasStation = makePredicate("hasStation", x -> 1.0);

        ConstraintFunction<Neighborhood>
            coverageConstraints =
            new ConstraintFunction<>(x ->
                                     {
                                         FolFormula hasStationOnX = hasStation.on(T(x));
                                         FolFormula
                                             hasStationOnAdjacents =
                                             exist(x.getAdjacent(), z -> hasStation.on(T(z)));
                                         return or(hasStationOnX, hasStationOnAdjacents);
                                     }
            );

        ILPBaseCCMProblem problem = argmin(Objective.sum(hasStation, city)).
            subjectTo(coverageConstraints.of(city)).getProblem();

//        problem.printConstraints();
//        problem.debug();
        problem.solve();

        System.out.println("Solution : ");
        city.stream().filter(n -> problem.isAssigned(hasStation, T(n))).forEachOrdered(n -> {
            System.out.println("Should select Neighborhood " + n.getId());
        });

    }

}
