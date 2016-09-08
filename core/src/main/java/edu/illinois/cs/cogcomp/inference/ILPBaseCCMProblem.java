package edu.illinois.cs.cogcomp.inference;


import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.infer.ilp.BeamSearch;
import edu.illinois.cs.cogcomp.infer.ilp.GurobiHook;
import edu.illinois.cs.cogcomp.infer.ilp.ILPSolver;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/12/16.
 */
public class ILPBaseCCMProblem {

//    private static final SolverFactory factory = new SolverFactoryGurobi(); // use lp_solve
//
//    static {
//        factory.setParameter(Solver.VERBOSE, 0);
//        factory.setParameter(Solver.TIMEOUT, 100); // set timeout to 100 seconds
//    }

    private Map<String, CCMTerm> termMap;
    private Map<String, CCMPredicate> predicateMap;
    private List<FolFormula> constraints;
    private List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective;

    private ILPSolver problem;
    private boolean debug;

    public ILPBaseCCMProblem(
        List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective,
        List<FolFormula> constraints) {
        this.objective = objective;
        this.constraints = constraints;

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
        CCMLogicSolver ccmSolver = new CCMLogicSolver(objective, constraints, predicateMap,
                                                      termMap);

        ILPSolver problem = new GurobiHook();
        if (this.debug) {

            ccmSolver.prepare(problem);
            StringBuffer buffer = new StringBuffer();
            ccmSolver.getProblem().write(buffer);
            System.out.println(buffer);
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
        for (FolFormula f : this.constraints) {
            System.out.println(f);
        }
        this.debug = true;
    }

    public void printConstraints() {
        for (FolFormula f : this.constraints) {
            System.out.println(f.toString());
        }
    }
}
