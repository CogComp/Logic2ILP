package edu.illinois.cs.cogcomp.l2ilp.api;


import edu.illinois.cs.cogcomp.l2ilp.inference.CCMLogicSolver;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.infer.ilp.GurobiHook;
import edu.illinois.cs.cogcomp.l2ilp.inference.ilp.representation.ILPProblem;

/**
 * Created by haowu on 5/12/16.
 */
public class ILPBaseCCMProblem {

    private Map<String, CCMTerm> termMap;
    private Map<String, CCMPredicate> predicateMap;
    private List<LogicFormula> hardConstraints;
    private List<Pair<LogicFormula, Double>> softConstraints;
    private List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective;

    private ILPProblem problem;
    private boolean debug;

    public ILPBaseCCMProblem(
        List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective,
        List<LogicFormula> hardConstraints,
        List<Pair<LogicFormula, Double>> softConstraints) {
        this.objective = objective;
        this.hardConstraints = hardConstraints;
        this.softConstraints = softConstraints;

        this.predicateMap = new HashMap<>();
        this.termMap = new HashMap<>();

        for (Pair<CCMPredicate, Collection<? extends CCMTerm>> pair : objective) {
            CCMPredicate p = pair.getKey();
            predicateMap.put(p.getID(), p);
            Collection<? extends CCMTerm> terms = pair.getRight();
            for (CCMTerm term : terms) {
                termMap.put(term.getID(), term);
            }
        }

    }

    public void addAdditionalTerm(Collection<? extends CCMTerm> terms) {
        for (CCMTerm term : terms) {
            termMap.put(term.getID(), term);
        }
    }

    public void addAdditionalPredicate(Collection<CCMPredicate> preds) {
        for (CCMPredicate pred : preds) {
            predicateMap.put(pred.getID(), pred);
        }
    }


    public double solve() {
        CCMLogicSolver
            ccmSolver = new CCMLogicSolver(objective, hardConstraints, softConstraints);

        this.problem = new ILPProblem(new GurobiHook());

        if (this.debug) {
            ccmSolver.prepare(problem);
            System.out.println(problem.toString());
        }

//        SolverFactory factory = new SolverFactoryGurobi();
//        factory.setParameter(Solver.VERBOSE, 0);
//        factory.setParameter(Solver.TIMEOUT, 3000);
//
//        Solver solver = factory.get();
        final double startInferenceWalltime = System.currentTimeMillis();

        ccmSolver.solve(problem);

        final double endInferenceWalltime = System.currentTimeMillis();
        return endInferenceWalltime - startInferenceWalltime;
    }

    public String getAssignment(String[] labels, CCMTerm term) {
        String ret = null;
        for (String label : labels) {
            boolean assigned = this.predicateMap.get(label).getAssignment(term) == 1;
            if (assigned) {
                if (ret != null) {
                    System.err.println("Duplicated assignment !");
                } else {
                    ret = label;
                }
            }
        }
        return ret;
    }

    public boolean isAssigned(CCMPredicate p, CCMTerm term) {
        boolean assigned = this.predicateMap.get(p.getID()).getAssignment(term) == 1;
        return assigned;
    }


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
}
