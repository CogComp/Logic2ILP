package edu.illinois.cs.cogcomp.l2ilp.api;


import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.infer.ilp.GurobiHook;
import edu.illinois.cs.cogcomp.infer.ilp.ILPSolver;
import edu.illinois.cs.cogcomp.l2ilp.inference.CCMLogicSolver;
import edu.illinois.cs.cogcomp.l2ilp.inference.ilp.representation.ILPProblem;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.BooleanVariable;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;

/**
 * Created by haowu on 5/12/16.
 */
public class InferenceProblem {

    private List<LogicFormula> hardConstraints;
    private List<Pair<LogicFormula, Double>> softConstraints;
    private List<Pair<BooleanVariable, Double>> objective;

    private boolean maxinizing;
    private boolean debug;

    public InferenceProblem(
        List<Pair<String, Double>> objs,
        List<LogicFormula> hardConstraints,
        List<Pair<LogicFormula, Double>> softConstraints) {
        this.objective = new ArrayList<>(objs.size());
        this.objective.addAll(objs.stream().map(
            p -> new ImmutablePair<>(BooleanVariable.getBooleanVariable(p.getKey()), p.getRight()))
                                  .collect(Collectors.toList()));
        this.hardConstraints = hardConstraints;
        this.softConstraints = softConstraints;
    }

    public ILPProblem getProblem(ILPSolver solver) {
        CCMLogicSolver
            ccmSolver = new CCMLogicSolver(objective, hardConstraints, softConstraints, this.maxinizing);

        ILPProblem problem = new ILPProblem(solver);
        problem.setMaximize(maxinizing);
        ccmSolver.prepare(problem);

        if (this.debug) {
            System.out.println(problem.toString());
        }

        return problem;
    }

//    public boolean isAssigned(WeightedPredicate p, String term) {
//        BooleanVariable var = p.on(term);
//        return this.problem.getBooleanValue(var.getId());
//    }


    public void debug() {
        for (LogicFormula f : this.hardConstraints) {
            System.out.println(f.toString());
        }
        this.debug = true;
    }

    public void printConstraints() {
        System.out.println("Hard Constraints:");
        for (LogicFormula f : this.hardConstraints) {
            System.out.println(f.toString());
        }
        System.out.println("Soft Constraints:");
        for (Pair p : this.softConstraints) {
            System.out.println(p.getLeft().toString());
            System.out.println(p.getRight().toString());
        }
    }

    public void setMaximize() {
        this.maxinizing = true;
    }

    public void setMinimize() {
        this.maxinizing = false;
    }

}
