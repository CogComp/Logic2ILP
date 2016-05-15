package inference;

import net.sf.tweety.logics.fol.syntax.FolFormula;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import util.Helper.*;

import static util.Helper.T;
import static util.Helper.Register;
import static util.Helper.apply;
import static util.Helper.argmin;
import static util.Helper.exist;
import static util.Helper.makePredicate;
import static util.Helper.or;


/**
 * Created by haowu on 5/13/16.
 */
public class SetCover {



    public static class Neighborhood {

        private static final Map<String,Neighborhood> NeighborhoodMap = new HashMap<>();

        public static Neighborhood makeNeighborhood(String line){
            if (line.contains(" ")){
                String id = line.split(" ")[0].trim();
                String[] adjacent = line.substring(1).trim().split(" ");
                return new Neighborhood(id,adjacent);
            }else{
                return NeighborhoodMap.get(line.trim());
            }
        }

        private String[] adjacent;
        private String id;

        private Neighborhood(String id, String[] adjacent) {
            this.id = id;
            this.adjacent = adjacent;
            NeighborhoodMap.put(id,this);
        }

        public List<Neighborhood> getAdjacent() {
            return Arrays.stream(this.adjacent).map(x -> makeNeighborhood(x)).collect(Collectors.toList());
        }

        public String getId() {
            return id;
        }

    }


    public static void main(String[] args) {
        // The original data.
        String[] cityData = ("1 2 3 4\n"
                             + "2 1\n"
                             + "3 1\n"
                             + "4 1\n"
                             + "5 9\n"
                             + "6 7 8 9\n"
                             + "7 6\n"
                             + "8 6\n"
                             + "9 5 6").split("\n");

        // Now we created a list of object.
        List<Neighborhood>
            city =
            Arrays.stream(cityData).map(Neighborhood::makeNeighborhood).collect(Collectors.toList());

        Register(Neighborhood.class, Neighborhood::getId);

        CCMPredicate<Neighborhood> hasStation = makePredicate("hasStation", x -> 1.0);

        Function<Neighborhood, List<FolFormula>>
            coverageConstraints =
            x -> {
                List<FolFormula> constraintsOnNeighborhood = new ArrayList<>();
                FolFormula hasStationOnX = hasStation.on(T(x));
                FolFormula hasStationOnAdjacents = exist(x.getAdjacent(), z -> hasStation.on(T(z)));
                constraintsOnNeighborhood.add(or(hasStationOnX,hasStationOnAdjacents));
                return constraintsOnNeighborhood;
            };

        ILPBaseCCMProblem problem = argmin(Objective.sum(hasStation,city)).
            subjectTo(apply(city,coverageConstraints)).getProblem();

        problem.solve();

        System.out.println("Solution : ");
        for (Neighborhood n : city){
            if (problem.assigned(hasStation,T(n))){
                System.out.println("Should select "+n.getId());
            }
        }



    }

}
