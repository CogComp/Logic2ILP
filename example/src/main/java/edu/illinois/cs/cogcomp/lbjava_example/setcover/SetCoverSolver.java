/**
 * This software is released under the University of Illinois/Research and Academic Use License. See
 * the LICENSE file in the root folder for details. Copyright (c) 2016
 *
 * Developed by: The Cognitive Computations Group University of Illinois at Urbana-Champaign
 * http://cogcomp.cs.illinois.edu/
 */
package edu.illinois.cs.cogcomp.lbjava_example.setcover;


import java.util.HashSet;
import java.util.Set;

import edu.illinois.cs.cogcomp.benchmark.BenchMarkRecord;
import edu.illinois.cs.cogcomp.benchmark.SetCoverTask;


public class SetCoverSolver {

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

    public static void main(String[] args) {

        containsStationConstrained classifier = new containsStationConstrained();

//        for (String file : args) {
        String data = "1 4 6 9\n"
                      + "2\n"
                      + "3 4\n"
                      + "4 1 3 6 10\n"
                      + "5 8\n"
                      + "6 1 4 7 9 10\n"
                      + "7 6\n"
                      + "8 5\n"
                      + "9 1 6\n"
                      + "10 4 6";
            City c = new City(data);
            for (Neighborhood n : c.getNeighborhoods()) {
                System.out.println(n.getNumber() + ": " + classifier.discreteValue(n));
            }
//        }
    }
}
