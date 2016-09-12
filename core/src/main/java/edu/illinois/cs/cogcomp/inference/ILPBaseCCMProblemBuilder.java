package edu.illinois.cs.cogcomp.inference;

import edu.illinois.cs.cogcomp.ir.fol.FolFormula;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haowu on 5/14/16.
 */
public class ILPBaseCCMProblemBuilder {

    private Objective objective;

    private List<FolFormula> hardConstraints;
    private List<Pair<FolFormula, Double>> softConstraints;

    public ILPBaseCCMProblemBuilder subjectTo(
        List<FolFormula> hardConstraints) {
        return subjectTo(hardConstraints,new ArrayList<>());
    }
    public ILPBaseCCMProblemBuilder subjectTo(
        List<FolFormula> hardConstraints,
        List<Pair<FolFormula, Double>> softConstraints) {
        this.hardConstraints = hardConstraints;
        this.softConstraints = softConstraints;
        return this;
    }

    public ILPBaseCCMProblemBuilder setObjective(Objective objective) {
        this.objective = objective;
        return this;
    }

    public ILPBaseCCMProblem getProblem(){
        return new ILPBaseCCMProblem(objective.objectives, hardConstraints, softConstraints);
    };
}
