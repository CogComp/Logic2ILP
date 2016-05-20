package edu.illinois.cs.cogcomp.inference;

import net.sf.javailp.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binglin on 5/19/16.
 */
public class DuplicateConstraintsBenchmark2 {

    private static void addConstraint(Problem problem, Linear linear, String operator, Number rhs, String constraint) {
        problem.add(new Constraint(constraint, linear, operator, rhs));
    }

    private static void addIndicatorConstraint(Problem problem, String variable) {
        Linear linear = new Linear();
        linear.add(1, variable);
        problem.add(linear, ">=", 0);
        problem.add(linear, "<=", 1);
        problem.setVarType(variable, Integer.class);
    }

    private static void addEquivalenceConstraint(Problem problem, String variable1, String variable2) {
        Linear linear = new Linear();
        linear.add(1, variable1);
        linear.add(-1, variable2);
        problem.add(linear, "=", 0);
    }

    public static void main(String[] args) {
        int max = 100;

        List<String> variables = new ArrayList<>();
        for (int i = 0; i < max; ++ i) {
            variables.add("x_" + i);
        }

        long oneDuration = 0;
        long tenDuration = 0;
        long localDuration = 0;

        for (int i = 0; i < max; ++ i) {
            long start = System.currentTimeMillis();
            Problem one = new Problem();
            Problem two = new Problem();

            Linear o1 = new Linear();
            Linear o2 = new Linear();
            variables.forEach(variable -> {
                o1.add(1, variable);
                if (Math.random() <= 0.5) {
                    o2.add(1, variable);
                }
                else {
                    o2.add(1, variable + "_2");
                }

                addIndicatorConstraint(one, variable);

                addIndicatorConstraint(two, variable);
                addIndicatorConstraint(two, variable + "_2");
                addEquivalenceConstraint(two, variable, variable + "_2");
            });
            one.setObjective(o1, OptType.MAX);
            two.setObjective(o2, OptType.MAX);

            for (int j = 0; j < max; ++ j) {
                Linear l1 = new Linear();
                Linear l2 = new Linear();

                variables.forEach(variable -> {
                    if (Math.random() < 0.2) {
                        l1.add(1, variable);

                        if (Math.random() <= 0.5) {
                            l2.add(1, variable);
                        }
                        else {
                            l2.add(1, variable + "_2");
                        }
                    }
                });

                addConstraint(one, l1, "<=", l1.size() * 0.2, "constraint_" + j);
                addConstraint(two, l2, "<=", l2.size() * 0.2, "constraint_" + j);
            }

            SolverFactory factory = new SolverFactoryGurobi();
            factory.setParameter(Solver.VERBOSE, 0);

            Solver solver;
            long end = System.currentTimeMillis();
            localDuration += end - start;

            solver = factory.get();
            start = System.currentTimeMillis();
            solver.solve(one);
            end = System.currentTimeMillis();
            oneDuration += end - start;

            solver = factory.get();
            start = System.currentTimeMillis();
            solver.solve(two);
            end = System.currentTimeMillis();
            tenDuration += end - start;
        }

        System.out.println(oneDuration);
        System.out.println(tenDuration);
        System.out.println(localDuration);
    }
}
