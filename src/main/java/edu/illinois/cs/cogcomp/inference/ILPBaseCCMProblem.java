package edu.illinois.cs.cogcomp.inference;

import net.sf.javailp.Problem;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Problem problem;
    private boolean debug;

    public ILPBaseCCMProblem(
        List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective,
        List<FolFormula> constraints) {
        this.objective = objective;
        this.constraints = constraints;

        this.predicateMap = new HashMap<>();
        this.termMap = new HashMap<>();

        for (Pair<CCMPredicate, Collection<? extends CCMTerm>> pair : objective){
            CCMPredicate p = pair.getKey();
            predicateMap.put(p.getID(),p);
            Collection<? extends CCMTerm> terms = pair.getRight();
            for (CCMTerm term : terms){
                termMap.put(term.getID(), term);
            }
        }

    }

    public void addAdditionalTerm(Collection<? extends CCMTerm>  terms){
        for (CCMTerm term : terms){
            termMap.put(term.getID(), term);
        }
    }

    public void addAdditionalPredicate(Collection<CCMPredicate>  preds){
        for (CCMPredicate pred: preds){
            predicateMap.put(pred.getID(), pred);
        }
    }


    public double solve(){
        this.problem = CCMLogicSolver.translateLogicToILP(objective, constraints, predicateMap,
                                                          termMap);
        if (this.debug){
            System.out.println(problem);
        }
//        Solver solver = factory.get(); // you should use this solver only once for one problem
//        Result result = solver.solve(problem);
        final double startInferenceWalltime = System.currentTimeMillis();
        CCMLogicSolver.solve(this.problem, predicateMap, 3000);
        final double endInferenceWalltime = System.currentTimeMillis();
        return endInferenceWalltime-startInferenceWalltime;
    }

    public String getAssignment(String[] labels, CCMTerm term){
        String ret = null;
        for (String label: labels){
            boolean assigned = this.predicateMap.get(label).getAssignment(term) == 1;
            if (assigned){
                if (ret != null){
                    System.err.println("Duplicated assignment !");
                }else{
                    ret = label;
                }
            }
        }
        return ret;
    }

    public boolean isAssigned(CCMPredicate p, CCMTerm term){
        boolean assigned = this.predicateMap.get(p.getID()).getAssignment(term) == 1;
        return assigned;
    }


    public void debug() {
        for (FolFormula f : this.constraints){
            System.out.println(f);
        }
        this.debug = true;
    }

    public void printConstraints() {
        for (FolFormula f : this.constraints){
            System.out.println(f.toString());
        }
    }
}
