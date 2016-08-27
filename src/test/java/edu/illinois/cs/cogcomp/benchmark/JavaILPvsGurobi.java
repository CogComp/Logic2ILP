package edu.illinois.cs.cogcomp.benchmark;

import gurobi.*;
import net.sf.javailp.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by binglin on 8/27/16.
 */
public class JavaILPvsGurobi {

    private static void addIndicatorConstraintJavaILP(Problem problem, String variable) {
        Linear linear = new Linear();
        linear.add(1, variable);
        problem.add(linear, ">=", 0);
        problem.add(linear, "<=", 1);
        problem.setVarType(variable, Integer.class);
    }

    public static void main(String[] args) throws GRBException {
        Random random = new Random(32);

        int numVariables = 300;
        int numDisjunctions = 300;

        // JavaILP
        Problem javailp = new Problem();
        Linear javailpObjective = new Linear();
        for (int j = 0; j < numVariables; j ++) {
            addIndicatorConstraintJavaILP(javailp, String.valueOf(j));
            javailpObjective.add(1, String.valueOf(j));
        }
        javailp.setObjective(javailpObjective, OptType.MIN);

        // Gurobi
        GRBEnv gurobiEnv = new GRBEnv();
        gurobiEnv.set(GRB.IntParam.OutputFlag, 0);
        GRBModel gurobi = new GRBModel(gurobiEnv);
        GRBVar[] gurobiVariables = new GRBVar[numVariables];
        for (int j = 0; j < numVariables; j ++) {
            gurobiVariables[j] = gurobi.addVar(0, 1, 1, GRB.BINARY, String.valueOf(j));
        }
        gurobi.update();

        for (int i = 0; i < numDisjunctions; i ++) {
            List<Integer> variables = new ArrayList<>();

            for (int j = 0; j < numVariables; j ++) {
                if (random.nextBoolean() || i == j) {
                    variables.add(j);
                }
            }

            // JavaILP
            Linear javaILPlinear = new Linear();
            variables.forEach(variable -> {
                javaILPlinear.add(1, String.valueOf(variable));
            });
            javailp.add(new Constraint(String.valueOf(i), javaILPlinear, ">=", 1));

            // Gurobi
            GRBLinExpr gurobiLinear = new GRBLinExpr();
            variables.forEach(variable -> {
                gurobiLinear.addTerm(1, gurobiVariables[variable]);
            });
            gurobi.addConstr(gurobiLinear, GRB.GREATER_EQUAL, 1, String.valueOf(i));
        }

        long start;
        long end;

        // JavaILP
        SolverFactory factory = new SolverFactoryGurobi();
        factory.setParameter(Solver.VERBOSE, 0);
        Solver solver = factory.get();

        start = System.currentTimeMillis();
        Result result = solver.solve(javailp);
        end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(result.getObjective());

        // Gurobi
        start = System.currentTimeMillis();
        gurobi.optimize();
        end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(gurobi.get(GRB.DoubleAttr.ObjVal));
    }
}
