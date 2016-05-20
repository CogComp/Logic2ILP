package edu.illinois.cs.cogcomp.inference;

import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

import java.util.List;

/**
 * Created by haowu on 5/14/16.
 */
public class ILPBaseCCMProblemBuilder {

    private Objective objective;

    private List<FolFormula> constraints;

    public ILPBaseCCMProblemBuilder subjectTo(
        List<FolFormula> constraints) {
        this.constraints = constraints;
        return this;
    }

    public ILPBaseCCMProblemBuilder setObjective(Objective objective) {
        this.objective = objective;
        return this;
    }

    public ILPBaseCCMProblem getProblem(){
        return new ILPBaseCCMProblem(objective.objectives,constraints);
    };
}
