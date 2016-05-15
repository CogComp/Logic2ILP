package inference;

import net.sf.tweety.logics.fol.syntax.FolFormula;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
