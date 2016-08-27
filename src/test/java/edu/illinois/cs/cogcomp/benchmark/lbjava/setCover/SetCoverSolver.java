/**
 * This software is released under the University of Illinois/Research and Academic Use License. See
 * the LICENSE file in the root folder for details. Copyright (c) 2016
 *
 * Developed by: The Cognitive Computations Group University of Illinois at Urbana-Champaign
 * http://cogcomp.cs.illinois.edu/
 */
package edu.illinois.cs.cogcomp.benchmark.lbjava.setCover;


import edu.illinois.cs.cogcomp.benchmark.task.SetCoverTask;

public class SetCoverSolver {

    public static void main(String[] args) {

        containsStationConstrained classifier = new containsStationConstrained();

        SetCoverTask task = new SetCoverTask(9, 0.5);
        System.out.println(task.toString());

        City c = new City(task.toString());
        for (Neighborhood n : c.getNeighborhoods()) {
            System.out.println(n.getNumber() + ": " + classifier.discreteValue(n));
        }
    }
}
