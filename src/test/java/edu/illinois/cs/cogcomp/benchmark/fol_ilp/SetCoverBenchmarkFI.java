package edu.illinois.cs.cogcomp.benchmark.fol_ilp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.benchmark.BenchMarkRecord;
import edu.illinois.cs.cogcomp.benchmark.lbjava.setCover.City;
import edu.illinois.cs.cogcomp.benchmark.lbjava.setCover.Neighborhood;
import edu.illinois.cs.cogcomp.benchmark.lbjava.setCover.containsStationConstrained;
import edu.illinois.cs.cogcomp.benchmark.task.SetCoverTask;
import edu.illinois.cs.cogcomp.inference.CCMPredicate;
import edu.illinois.cs.cogcomp.inference.ILPBaseCCMProblem;
import edu.illinois.cs.cogcomp.inference.Objective;
import edu.illinois.cs.cogcomp.inference.SetCover;
import edu.illinois.cs.cogcomp.inference.constraint.ConstraintFunction;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

import static edu.illinois.cs.cogcomp.util.Helper.Register;
import static edu.illinois.cs.cogcomp.util.Helper.T;
import static edu.illinois.cs.cogcomp.util.Helper.argmin;
import static edu.illinois.cs.cogcomp.util.Helper.exist;
import static edu.illinois.cs.cogcomp.util.Helper.makePredicate;
import static edu.illinois.cs.cogcomp.util.Helper.or;

/**
 * Created by haowu on 8/26/16.
 */
public class SetCoverBenchmarkFI {

    public final static Gson GSON = new GsonBuilder().create();

    public static BenchMarkRecord solve(SetCoverTask task) {
        String problemString = task.toString();
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

        double startInferenceWalltime = System.currentTimeMillis();
        problem.solve();
        double endInferenceWalltime = System.currentTimeMillis();

//        System.out.println("Solution : ");
        Set<String>
            answer =
            city.stream().filter(n -> problem.isAssigned(hasStation, T(n))).map(x -> x.getId())
                .collect(Collectors.toSet());

        double endWalltime = System.currentTimeMillis();
        record.inferenceTime = endInferenceWalltime - startInferenceWalltime;
        record.totalTime = endWalltime - startWalltime;

        record.solution =
            new SetCoverTask.SetCoverSolution(
                answer.stream().mapToInt(Integer::parseInt).toArray());

        return record;
    }


    public static BenchMarkRecord solveByLBJ(SetCoverTask task) {
        BenchMarkRecord record = new BenchMarkRecord();
        record.description = task.toDesp();

        double startWalltime = System.currentTimeMillis();

        containsStationConstrained classifier = new containsStationConstrained();
        City c = new City(task.toString());

        Set<Integer> answer = new HashSet<>();
        for (Neighborhood n : c.getNeighborhoods()) {
            if (classifier.discreteValue(n).equals("true")) {
                answer.add(n.getNumber());
            }
        }
        double endWalltime = System.currentTimeMillis();
        record.totalTime = endWalltime - startWalltime;

        record.solution =
            new SetCoverTask.SetCoverSolution(
                answer.stream().mapToInt(Integer::intValue).toArray());
        return record;

    }

    public static void main(String[] args)
        throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {
        PrintWriter writer = new PrintWriter("bench_fol.txt", "UTF-8");
        PrintWriter writer_lbj = new PrintWriter("bench_lbj.txt", "UTF-8");

        for (int i = 10; i < 100; i++) {

            System.out.print("Node size : " + i + "\r");

            for (double density = 0.2; density <= 0.8; density += 0.1) {

                SetCoverTask task = new SetCoverTask(i, density);

                for (int j = 0; j < 5; j++) {
                    try {
                        BenchMarkRecord result = solve(task);
                        writer.println(GSON.toJson(result));

                        BenchMarkRecord lbj_result = solveByLBJ(task);
                        writer_lbj.println(GSON.toJson(lbj_result));

                        // Now check if retuls is the same.

                        int[] mySol = ((SetCoverTask.SetCoverSolution) result.solution).assigned;
                        int[]
                            lbjSol =
                            ((SetCoverTask.SetCoverSolution) lbj_result.solution).assigned;
                        if (lbjSol.length != mySol.length) {
                            System.err.println(
                                "Answer is not the same !: \n-------------------------------------- ");
                            System.err.println("My length :" + mySol.length);
                            System.err.println(GSON.toJson(mySol));
                            System.err.println("LBJ length : " + lbjSol.length);
                            System.err.println(GSON.toJson(lbjSol));
                            System.err.println(task.toString());
                            System.err.println("------------------------------------------");
                            j = 123123123;
                            i = 123123123;
                            density = 100;
                        } else {
                            if (!task.checkCondition(mySol)) {
                                System.err.println(
                                    "My Solution is bad: \n-------------------------------------- ");
                                System.err.println(GSON.toJson(mySol));
                                System.err.println("================================================");
                                System.err.println(task.toString());
                                System.err.println("------------------------------------------");
                                j = 123123123;
                                i = 123123123;
                                density = 100;
                            }

                            if (!task.checkCondition(lbjSol)) {
                                System.err.println(
                                    "LBJ solution is bad: \n-------------------------------------- ");
                                System.err.println(GSON.toJson(lbjSol));
                                System.err.println("================================================");
                                System.err.println(task.toString());
                                System.err.println("------------------------------------------");
                                j = 123123123;
                                i = 123123123;
                                density = 100;

                            }
                        }

                    } catch (Exception e) {
                        System.err.println(task.toString());
                        e.printStackTrace();
                        System.exit(1);
                    }


                }
            }
        }
        writer.close();

    }
}
