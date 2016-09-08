package edu.illinois.cs.cogcomp.inference;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by binglin on 5/19/16.
 */
public class DuplicateConstraintsBenchmark {
//
//    private static void addConstraint(Problem problem, Linear linear, String operator, Number rhs, String constraint) {
//        problem.add(new Constraint(constraint, linear, operator, rhs));
//    }
//
//    private static void addIndicatorConstraint(Problem problem, String variable) {
//        Linear linear = new Linear();
//        linear.add(1, variable);
//        problem.add(linear, ">=", 0);
//        problem.add(linear, "<=", 1);
//        problem.setVarType(variable, Integer.class);
//    }
//
//    public static void main(String[] args) {
//        int max = 100;
//
//        List<String> variables = new ArrayList<>();
//        for (int i = 0; i < max; ++ i) {
//            variables.add("x_" + i);
//        }
//
//        long oneDuration = 0;
//        long tenDuration = 0;
//
//        for (int i = 0; i < max; ++ i) {
//            Problem one = new Problem();
//            Problem ten = new Problem();
//
//            Linear objective = new Linear();
//            variables.forEach(variable -> {
//                objective.add(1, variable);
//
//                addIndicatorConstraint(one, variable);
//
//                for (int j = 0; j < 10; ++ j) {
//                    addIndicatorConstraint(ten, variable);
//                }
//            });
//            one.setObjective(objective, OptType.MAX);
//            ten.setObjective(objective, OptType.MAX);
//
//            for (int j = 0; j < max; ++ j) {
//                Linear linear = new Linear();
//
//                variables.forEach(variable -> {
//                    if (Math.random() < 0.2) {
//                        linear.add(1, variable);
//                    }
//                });
//
//                addConstraint(one, linear, "<=", linear.size() * 0.2, "constraint_" + j);
//
//                for (int k = 0; k < 10; ++ k) {
//                    addConstraint(ten, linear, "<=", linear.size() * 0.2, "constraint_" + j + "_" + k);
//                }
//            }
//
//            SolverFactory factory = new SolverFactoryGurobi();
//            factory.setParameter(Solver.VERBOSE, 0);
//
//            Solver solver;
//            long start;
//            long end;
//
//            solver = factory.get();
//            start = System.currentTimeMillis();
//            solver.solve(one);
//            end = System.currentTimeMillis();
//            oneDuration += end - start;
//
//            solver = factory.get();
//            start = System.currentTimeMillis();
//            solver.solve(ten);
//            end = System.currentTimeMillis();
//            tenDuration += end - start;
//        }
//
//        System.out.println(oneDuration);
//        System.out.println(tenDuration);
//    }
}
