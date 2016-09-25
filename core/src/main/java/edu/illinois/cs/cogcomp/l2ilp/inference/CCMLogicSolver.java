package edu.illinois.cs.cogcomp.l2ilp.inference;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.BooleanLiteral;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicSimplifier;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.AtLeastK;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.AtMostK;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs.cogcomp.l2ilp.inference.ilp.representation.ILPProblem;
import edu.illinois.cs.cogcomp.l2ilp.inference.ilp.representation.Linear;
import edu.illinois.cs.cogcomp.l2ilp.inference.ilp.representation.Operator;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.BooleanVariable;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Conjunction;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Disjunction;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Negation;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.ExactK;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.NotExactK;
import edu.illinois.cs.cogcomp.l2ilp.util.Counter;

/**
 *
 */
public class CCMLogicSolver {

    private final List<Pair<BooleanVariable, Double>> objective;
    private final List<LogicFormula> hardConstraints;
    private final List<Pair<LogicFormula, Double>> softConstraints;
    private final Counter variableCounter;
    private final Counter constraintCounter;

    private ILPProblem problem;

    public CCMLogicSolver(
            List<Pair<BooleanVariable, Double>> objective,
            List<LogicFormula> hardConstraints,
            List<Pair<LogicFormula, Double>> softConstraints) {
        this.objective = objective;
        this.hardConstraints = hardConstraints;
        this.softConstraints = softConstraints;
        this.variableCounter = new Counter("NV$");
        this.constraintCounter = new Counter("C$");
        this.problem = null;
    }

    public ILPProblem getProblem() {
        return problem;
    }

    public void prepare(ILPProblem problem) {
        this.problem = problem;

        // Set objective function
        for (Pair<BooleanVariable, Double> pair : objective) {
            BooleanVariable booleanVariable = pair.getKey();
            double weight = pair.getValue();
            problem.introduceVariableToObjective(booleanVariable.getId(), weight);
        }

        problem.setMaximize(true);

        // Set hardConstraints
        LogicSimplifier logicSimplifier = new LogicSimplifier().withAllRules();
        hardConstraints.forEach(constraint -> translate(logicSimplifier.simplify(constraint).toNnf(), null));

        // Set softConstraints
        softConstraints.forEach(constraintWeightPair -> {
            LogicFormula constraint = constraintWeightPair.getKey();
            double weight = constraintWeightPair.getRight();

            variableCounter.increment();
            problem.introduceVariableToObjective(variableCounter.toString(), weight);
            translate(logicSimplifier.simplify(constraint).toNnf(), variableCounter.toString());
        });
    }

    public void solve(ILPProblem ilpProblem) {
        if (problem == null) {
            prepare(ilpProblem);
        }

        try {
            ilpProblem.solve();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addConstraint(Linear linear, Operator operator, double rhs) {
        constraintCounter.increment();
        switch (operator) {
            case GE:
                problem.addGreaterThanConstraint(linear.variables, linear.weights.toArray(), rhs);
                break;
            case LE:
                problem.addLessThanConstraint(linear.variables, linear.weights.toArray(), rhs);
                break;
            case EQ:
                problem.addEqualityConstraint(linear.variables, linear.weights.toArray(), rhs);
                break;
            default:
                break;
        }
    }

    private void addBooleanConstraint(String variableName) {
        this.problem.introduceVariableToObjective(variableName, 0);
    }

    private void addEquivalenceConstraint(String variable1, String variable2) {
        Linear linear = new Linear();
        linear.add(1, variable1);
        linear.add(-1, variable2);
        addConstraint(linear, Operator.EQ, 0);
    }

    // Better name?
    private void addNegationConstraint(String variable1, String variable2) {
        Linear linear = new Linear();
        linear.add(1, variable1);
        linear.add(1, variable2);
        addConstraint(linear, Operator.EQ, 1);
    }

    private void translateChildFormula(LogicFormula logicFormula, Linear... linears) {
        if (logicFormula instanceof BooleanVariable) {
            BooleanVariable var = (BooleanVariable) logicFormula;

            addBooleanConstraint(var.getId());
            for (Linear l : linears) {
                l.add(1, var.getId());
            }
        } else {
            variableCounter.increment();

            addBooleanConstraint(variableCounter.toString());
            for (Linear l : linears) {
                l.add(1, variableCounter.toString());
            }

            translate(logicFormula, variableCounter.toString());
        }
    }

    private void translateBooleanLiteral(BooleanLiteral booleanLiteral, String inheritedName) {
        if (booleanLiteral == BooleanLiteral.TRUE) {
            if (inheritedName != null) {
                Linear l1 = new Linear();
                l1.add(1, inheritedName);
                addConstraint(l1, Operator.EQ, 1);
            }
        }
        else {
            if (inheritedName == null) {
                throw new RuntimeException("The ILP is infeasible.");
            }
            else {
                Linear l1 = new Linear();
                l1.add(1, inheritedName);
                addConstraint(l1, Operator.EQ, 0);
            }
        }
    }

    private void translateBooleanVariable(BooleanVariable booleanVariable, String inheritedName) {
        addBooleanConstraint(booleanVariable.getId());

        if (inheritedName != null) {
            addEquivalenceConstraint(inheritedName, booleanVariable.getId());
        }
    }

    private void translateConjunction(Conjunction conjunction, String inheritedName) {
        if (conjunction.getFormulas().size() == 0) {
            translateBooleanLiteral(BooleanLiteral.TRUE, inheritedName);
        }
        else {
            if (inheritedName == null) {
                conjunction.getFormulas().forEach(logicFormula -> translate(logicFormula, null));
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-conjunction.getFormulas().size(), inheritedName);
                l2.add(-1, inheritedName);

                conjunction.getFormulas().forEach(logicFormula -> translateChildFormula(logicFormula, l1, l2));

                addConstraint(l1, Operator.GE, 0);
                addConstraint(l2, Operator.LE, conjunction.getFormulas().size() - 1);
            }
        }
    }

    private void translateDisjunction(Disjunction disjunction, String inheritedName) {
        if (disjunction.getFormulas().size() == 0) {
            translateBooleanLiteral(BooleanLiteral.FALSE, inheritedName);
        } else {
            if (inheritedName == null) {
                Linear l1 = new Linear();

                disjunction.getFormulas().forEach(logicFormula -> {
                    if (logicFormula instanceof BooleanVariable) {
                        BooleanVariable booleanVariable = (BooleanVariable) logicFormula;

                        addBooleanConstraint(booleanVariable.getId());

                        l1.add(1, booleanVariable.getId());
                    } else {
                        variableCounter.increment();
                        addBooleanConstraint(variableCounter.toString());
                        l1.add(1, variableCounter.toString());

                        translate(logicFormula, variableCounter.toString());
                    }
                });

                addConstraint(l1, Operator.GE, 1);
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-1, inheritedName);
                l2.add(-disjunction.getFormulas().size(), inheritedName);

                disjunction.getFormulas().forEach(logicFormula -> translateChildFormula(logicFormula, l1, l2));

                addConstraint(l1, Operator.GE, 0);
                addConstraint(l2, Operator.LE, 0);
            }
        }
    }

    private void translateNegation(Negation negation, String inheritedName) {
        if (negation.getFormula() instanceof BooleanVariable) {
            BooleanVariable booleanVariable = (BooleanVariable) negation.getFormula();

            addBooleanConstraint(booleanVariable.getId());

            if (inheritedName != null) {
                addNegationConstraint(inheritedName, booleanVariable.getId());
            }
        } else {
            throw new RuntimeException("toNnf() is not called before translation or its implementation missed something.");
        }
    }

    private void translateAtLeast(AtLeastK atLeastK, String inheritedName) {
        if (atLeastK.getK() > atLeastK.getFormulas().size()) {
            translateBooleanLiteral(BooleanLiteral.FALSE, inheritedName);
        } else {
            if (inheritedName == null) {
                Linear l1 = new Linear();

                atLeastK.getFormulas().forEach(logicFormula -> translateChildFormula(logicFormula, l1));

                addConstraint(l1, Operator.GE, atLeastK.getK());
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-atLeastK.getK(), inheritedName);
                l2.add(-atLeastK.getFormulas().size(), inheritedName);

                atLeastK.getFormulas().forEach(logicFormula -> translateChildFormula(logicFormula, l1, l2));

                addConstraint(l1, Operator.GE, 0);
                addConstraint(l2, Operator.LE, atLeastK.getK() - 1);
            }
        }
    }

    private void translateAtMost(AtMostK atMostK, String inheritedName) {
        if (atMostK.getK() >= atMostK.getFormulas().size()) {
            translateBooleanLiteral(BooleanLiteral.TRUE, inheritedName);
        } else {
            if (inheritedName == null) {
                Linear l1 = new Linear();

                atMostK.getFormulas().forEach(logicFormula -> translateChildFormula(logicFormula, l1));

                addConstraint(l1, Operator.LE, atMostK.getK());
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(atMostK.getFormulas().size() - atMostK.getK(), inheritedName);
                l2.add(atMostK.getFormulas().size(), inheritedName);

                atMostK.getFormulas().forEach(logicFormula -> translateChildFormula(logicFormula, l1, l2));

                addConstraint(l1, Operator.LE, atMostK.getFormulas().size());
                addConstraint(l2, Operator.GE, atMostK.getK() + 1);
            }
        }
    }

    private void translateExactK(ExactK exactK, String inheritedName) {
        if (exactK.getK() > exactK.getFormulas().size()) {
            translateBooleanLiteral(BooleanLiteral.FALSE, inheritedName);
        }
        else {
            if (inheritedName == null) {
                Linear l1 = new Linear();

                exactK.getFormulas().forEach(logicFormula -> translateChildFormula(logicFormula, l1));

                addConstraint(l1, Operator.EQ, exactK.getK());
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                Linear l3 = new Linear();
                Linear l4 = new Linear();
                l1.add(-exactK.getK(), inheritedName);
                l2.add(-exactK.getFormulas().size(), inheritedName);
                l3.add(exactK.getFormulas().size() - exactK.getK(), inheritedName);
                l4.add(exactK.getFormulas().size(), inheritedName);

                exactK.getFormulas().forEach(logicFormula -> translateChildFormula(logicFormula, l1, l2, l3, l4));

                addConstraint(l1, Operator.GE, 0);
                addConstraint(l2, Operator.LE, exactK.getK() - 1);
                addConstraint(l3, Operator.LE, exactK.getFormulas().size());
                addConstraint(l4, Operator.GE, exactK.getK() + 1);
            }
        }
    }

    private void translateNotExactK(NotExactK notExactK, String inheritedName) {
        if (notExactK.getK() > notExactK.getFormulas().size()) {
            translateBooleanLiteral(BooleanLiteral.TRUE, inheritedName);
        }
        else {
            List<LogicFormula> formulas = new ArrayList<>(2);
            formulas.add(new AtLeastK(notExactK.getK() + 1, notExactK.getFormulas()));
            formulas.add(new AtMostK(notExactK.getK() - 1, notExactK.getFormulas()));

            translateDisjunction(new Disjunction(formulas), inheritedName);
        }
    }

    private void translate(LogicFormula logicFormula, String inheritedName) {
        if (logicFormula instanceof BooleanLiteral) {
            translateBooleanLiteral((BooleanLiteral) logicFormula, inheritedName);
        }
        else if (logicFormula instanceof BooleanVariable) {
            translateBooleanVariable((BooleanVariable) logicFormula, inheritedName);
        }
        else if (logicFormula instanceof Conjunction) {
            translateConjunction((Conjunction) logicFormula, inheritedName);
        }
        else if (logicFormula instanceof Disjunction) {
            translateDisjunction((Disjunction) logicFormula, inheritedName);
        }
        else if (logicFormula instanceof Negation) {
            translateNegation((Negation) logicFormula, inheritedName);
        }
        else if (logicFormula instanceof AtLeastK) {
            translateAtLeast((AtLeastK) logicFormula, inheritedName);
        }
        else if (logicFormula instanceof AtMostK) {
            translateAtMost((AtMostK) logicFormula, inheritedName);
        }
        else if (logicFormula instanceof ExactK) {
            translateExactK((ExactK) logicFormula, inheritedName);
        }
        else if (logicFormula instanceof NotExactK) {
            translateNotExactK((NotExactK) logicFormula, inheritedName);
        }
        else {
            throw new RuntimeException("LogicFormula implementation " + logicFormula.getClass() + " is not supported yet.");
        }
    }
}