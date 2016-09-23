package edu.illinois.cs.cogcomp.benchmark.fol_ilp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.benchmark.BenchMarkRecord;
import edu.illinois.cs.cogcomp.benchmark.task.SetCoverTask;
import edu.illinois.cs.cogcomp.l2ilp.inference.CCMPredicate;
import edu.illinois.cs.cogcomp.l2ilp.inference.ILPBaseCCMProblem;
import edu.illinois.cs.cogcomp.l2ilp.inference.Objective;
import edu.illinois.cs.cogcomp.l2ilp.inference.SetCover;
import edu.illinois.cs.cogcomp.l2ilp.inference.constraint.ConstraintFunction;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;

import static edu.illinois.cs.cogcomp.lbjava.examples.setCover.SetCoverSolver.solveByLBJ;
import static edu.illinois.cs.cogcomp.l2ilp.util.Helper.Register;
import static edu.illinois.cs.cogcomp.l2ilp.util.Helper.T;
import static edu.illinois.cs.cogcomp.l2ilp.util.Helper.argmin;
import static edu.illinois.cs.cogcomp.l2ilp.util.Helper.exist;
import static edu.illinois.cs.cogcomp.l2ilp.util.Helper.makePredicate;
import static edu.illinois.cs.cogcomp.l2ilp.util.Helper.or;

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
                                         LogicFormula hasStationOnX = hasStation.on(T(x));
//                                         List<LogicFormula>
//                                             in_or =
//                                             x.getAdjacent().stream().map(z -> hasStation.on(T(z)))
//                                                 .collect(Collectors.toList());
                                         LogicFormula
                                             hasStationOnAdjacents =
//                                             or(in_or);
                                             exist(x.getAdjacent(), z -> hasStation.on(T(z)));
//                                         in_or.add(hasStationOnX);
//                                         return or(in_or);
                                         return or(hasStationOnX, hasStationOnAdjacents);
                                     }
            );

        ILPBaseCCMProblem problem = argmin(Objective.sum(hasStation, city)).
            subjectTo(coverageConstraints.of(city)).getProblem();

//        problem.printConstraints();
//        problem.debug();

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

        return record;
    }

    public static final int NUM_OF_TRAIL = 10;

    public static boolean conductExperiment(int i, SetCoverTask task, PrintWriter writer,
                                            PrintWriter writer_lbj) {
        try {
            System.out.print("Node size : " + i + " ... running my lbj......\r");

            BenchMarkRecord lbj_result = solveByLBJ(task);
            writer_lbj.println(GSON.toJson(lbj_result));
            int[]
                lbjSol =
                ((SetCoverTask.SetCoverSolution) lbj_result.solution).assigned;

            System.out.print("Node size : " + i + " ... running my inference\r");

            BenchMarkRecord result = solve(task);
            writer.println(GSON.toJson(result));
            int[] mySol = ((SetCoverTask.SetCoverSolution) result.solution).assigned;

            if (lbjSol.length != mySol.length) {
                System.err.println(
                    "Answer is not the same !: \n-------------------------------------- ");
                System.err.println("My length :" + mySol.length);
                System.err.println(GSON.toJson(mySol));
                System.err.println("LBJ length : " + lbjSol.length);
                System.err.println(GSON.toJson(lbjSol));
                System.err.println(task.toString());
                System.err.println("------------------------------------------");
                return false;

            } else {
                if (!task.checkCondition(mySol)) {
                    System.err.println(
                        "My Solution is bad: \n-------------------------------------- ");
                    System.err.println(GSON.toJson(mySol));
                    System.err
                        .println("================================================");
                    System.err.println(task.toString());
                    System.err.println("------------------------------------------");
                    return false;
                }

                if (!task.checkCondition(lbjSol)) {
                    System.err.println(
                        "LBJ solution is bad: \n-------------------------------------- ");
                    System.err.println(GSON.toJson(lbjSol));
                    System.err
                        .println("================================================");
                    System.err.println(task.toString());
                    System.err.println("------------------------------------------");
                    return false;
                }
            }

        } catch (Exception e) {
            System.err.println(task.toString());
            e.printStackTrace();
            System.exit(1);
        }
        return true;
    }

    private static String setPrecision(double amt, int precision) {
        return String.format("%." + precision + "f", amt);
    }

    public static void loop(double density, int min_node, int max_node)
        throws FileNotFoundException, UnsupportedEncodingException {

        PrintWriter
            writer =
            new PrintWriter("benchs/bench_fol_d=" + setPrecision(density, 1) + ".txt", "UTF-8");
        PrintWriter
            writer_lbj =
            new PrintWriter("benchs/bench_lbj_d=" + setPrecision(density, 1) + ".txt", "UTF-8");

        boolean continue_ = true;
        System.out.println("Density=" + setPrecision(density, 1));

        for (int i = min_node; i < max_node && continue_; i++) {

            for (int k = 0; k < NUM_OF_TRAIL && continue_; k++) {
                SetCoverTask task = new SetCoverTask(i, density);
                for (int j = 0; j < 3 && continue_; j++) {
                    conductExperiment(i, task, writer, writer_lbj);
                }
            }


        }

        writer.close();
        writer_lbj.close();
    }


    public static void main(String[] args)
        throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {

        loop(0.2,20,25);

//        for (double d = 0.2; d < 0.4; d += 0.1) {
//            loop(d, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
//        }

    }
}
