package edu.illinois.cs.cogcomp.inference.repr;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.illinois.cs.cogcomp.infer.ilp.GurobiHook;
import edu.illinois.cs.cogcomp.infer.ilp.ILPSolver;

/**
 * Created by haowu on 9/8/16.
 */
public class ILPProblem {

    private ILPSolver solver;
    Map<String, Integer> variableNameToInteger;

    public ILPProblem(ILPSolver solver) {
        this.solver = solver;
        this.variableNameToInteger = new ConcurrentHashMap<>();
    }

    public ILPProblem() {
        this(new GurobiHook());
    }

    public void setMaximize(boolean maximize) {
        this.solver.setMaximize(maximize);
    }

    public void solve() throws Exception {
        solver.solve();
    }

    private int getIdOfVariableThatAreNotInObj(String name) {
        return introduceVariableToObjective(name, 0.0);
    }


    public int introduceVariableToObjective(String name, double weight) {
        if (variableNameToInteger.containsKey(name)) {
            return variableNameToInteger.get(name);
        } else {
            int newIdx = solver.addBooleanVariable(weight);
            variableNameToInteger.put(name, newIdx);
            return newIdx;
        }
    }

    public void addGreaterThanConstraint(List<String> variables, double[] doubles, double rhs) {
        if (variables.size() != doubles.length) {
            throw new RuntimeException("variables.size() != doubles.length");
        }
        int[] vars = variables.stream().mapToInt(this.variableNameToInteger::get).toArray();
        this.solver.addGreaterThanConstraint(vars, doubles, rhs);
    }

    public void addLessThanConstraint(List<String> variables, double[] doubles, double rhs) {
        if (variables.size() != doubles.length) {
            throw new RuntimeException("variables.size() != doubles.length");
        }
        int[] vars = variables.stream().mapToInt(this.variableNameToInteger::get).toArray();
        this.solver.addLessThanConstraint(vars, doubles, rhs);
    }

    public void addEqualityConstraint(List<String> variables, double[] doubles, double rhs) {
        if (variables.size() != doubles.length) {
            throw new RuntimeException("variables.size() != doubles.length");
        }
        int[] vars = variables.stream().mapToInt(this.variableNameToInteger::get).toArray();
        this.solver.addEqualityConstraint(vars, doubles, rhs);
    }

    public boolean getBooleanValue(String var) {
        return this.solver.getBooleanValue(this.getIdOfVariableThatAreNotInObj(var));
    }

    @Override
    public String toString() {
        // TODO(@haowu4): print more readable format, i.e. replace integer variable name with readable name in lexicon.
        StringBuffer buffer = new StringBuffer();
        solver.write(buffer);
        return buffer.toString();
    }
}
