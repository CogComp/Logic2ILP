package edu.illinois.cs.cogcomp.benchmark;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.benchmark.task.SetCoverTask;
import edu.illinois.cs.cogcomp.inference.CCMPredicate;
import edu.illinois.cs.cogcomp.inference.ILPBaseCCMProblem;
import edu.illinois.cs.cogcomp.inference.Objective;
import edu.illinois.cs.cogcomp.inference.SetCover;
import edu.illinois.cs.cogcomp.inference.constraint.ConstraintFunction;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

import static edu.illinois.cs.cogcomp.benchmark.fol_ilp.SetCoverBenchmarkFI.GSON;
import static edu.illinois.cs.cogcomp.benchmark.fol_ilp.SetCoverBenchmarkFI.solve;
import static edu.illinois.cs.cogcomp.lbjava.examples.setCover.SetCoverSolver.solveByLBJ;
import static edu.illinois.cs.cogcomp.util.Helper.Register;
import static edu.illinois.cs.cogcomp.util.Helper.T;
import static edu.illinois.cs.cogcomp.util.Helper.argmin;
import static edu.illinois.cs.cogcomp.util.Helper.makePredicate;
import static edu.illinois.cs.cogcomp.util.Helper.or;

/**
 * Created by haowu on 8/26/16.
 */
public class Main {

    public static void main(String[] args) {

        SetCoverTask task = new SetCoverTask(10, 0.2);

//        System.out.println(task.toString());
//        BenchMarkRecord lbj_result = solveByLBJ(task);
//        System.out.println(GSON.toJson(lbj_result));
//
//        BenchMarkRecord my_result = solve(task);
//        System.out.println(GSON.toJson(my_result ));
////
//        task = new SetCoverTask(4, 0.2);
        String problemString = task.toString();
        System.out.println(task.toString());

        BenchMarkRecord record = new BenchMarkRecord();
        record.description = task.toDesp();
        double startWalltime = System.currentTimeMillis();

        String[] cityData = problemString.split("\n");

        // Now we created a list of object.
        List<SetCover.Neighborhood>
            city =
            Arrays.stream(cityData).map(SetCover.Neighborhood::makeNeighborhood)
                .collect(Collectors.toList());

        Register(SetCover.Neighborhood.class, SetCover.Neighborhood::getId);

        // Create a predicate
        CCMPredicate<SetCover.Neighborhood> hasStation = makePredicate("hasStation", x -> 1.0);

        ConstraintFunction<SetCover.Neighborhood>
            coverageConstraints =
            new ConstraintFunction<>(x ->
                                     {
                                         FolFormula hasStationOnX = hasStation.on(T(x));
                                         List<FolFormula>
                                             in_or =
                                             x.getAdjacent().stream().map(z -> hasStation.on(T(z)))
                                                 .collect(Collectors.toList());
                                         FolFormula
                                             hasStationOnAdjacents =
                                             or(in_or);
//                                             exist();
                                         return or(hasStationOnX, hasStationOnAdjacents);
                                     }
            );

        ILPBaseCCMProblem problem = argmin(Objective.sum(hasStation, city)).
            subjectTo(coverageConstraints.of(city)).getProblem();

        System.out.println("Constraints");
        problem.printConstraints();
        System.out.println("--------");
        problem.debug();

        record.inferenceTime = problem.solve();

//        System.out.println("Solution : ");
        Set<String>
            answer =
            city.stream().filter(n -> problem.isAssigned(hasStation, T(n))).map(x -> x.getId())
                .collect(Collectors.toSet());

        final double endWalltime = System.currentTimeMillis();
//        record.inferenceTime = endInferenceWalltime - startInferenceWalltime;
        record.totalTime = endWalltime - startWalltime;

        record.solution =
            new SetCoverTask.SetCoverSolution(
                answer.stream().mapToInt(Integer::parseInt).toArray());

    }
}
