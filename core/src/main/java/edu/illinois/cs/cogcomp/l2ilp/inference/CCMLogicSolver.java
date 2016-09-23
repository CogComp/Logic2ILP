package edu.illinois.cs.cogcomp.l2ilp.inference;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;
import edu.illinois.cs.cogcomp.l2ilp.util.Counter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.l2ilp.inference.ilp.representation.ILPProblem;
import edu.illinois.cs.cogcomp.l2ilp.inference.ilp.representation.Linear;
import edu.illinois.cs.cogcomp.l2ilp.inference.ilp.representation.Operator;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.BooleanVariable;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Conjunction;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Disjunction;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Negation;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.AtLeast;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.AtMost;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.ExactK;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.NotExactK;


/**
 * Created by binglin on 4/17/16.
 */
public class CCMLogicSolver {


    ILPProblem problem;
    private final List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective;
    private final List<LogicFormula> hardConstraints;
    private final List<Pair<LogicFormula, Double>> softConstraints;
    private final Counter variableCounter;
    private final Counter constraintCounter;
    private final Map<String, ? extends CCMPredicate> predicateMap;
    private final Map<String, ? extends CCMTerm> termMap;

    // Stuff for Cogcomp Inference package.

    public CCMLogicSolver(
        List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective,
        List<LogicFormula> hardConstraints,
        List<Pair<LogicFormula, Double>> softConstraints,
        Map<String, ? extends CCMPredicate> predicateMap,
        Map<String, ? extends CCMTerm> termMap) {
        this.objective = objective;
        this.hardConstraints = hardConstraints;
        this.softConstraints = softConstraints;
        this.variableCounter = new Counter("NV$");
        this.constraintCounter = new Counter("C$");
        this.predicateMap = predicateMap;
        this.termMap = termMap;
        problem = null;
    }

    public ILPProblem getProblem() {
        return problem;
    }

    public void prepare(ILPProblem problem) {
        // Set objective function
        this.problem = problem;

        for (Pair<CCMPredicate, Collection<? extends CCMTerm>> pair : objective) {
            CCMPredicate predicate = pair.getKey();
            Collection<? extends CCMTerm> terms = pair.getValue();

            for (CCMTerm term : terms) {
                double weight = predicate.getScore(term);
                String name = predicate.getID() + "$" + term.getID();
                int idx = problem.introduceVariableToObjective(name, weight);
            }


        }

        problem.setMaximize(true);

        // Set hardConstraints
        hardConstraints.forEach(folFormula -> {
//            translate(folFormula.toNnf(), null);
            translate(folFormula, null);
        });

        // Set softConstraints
        softConstraints.forEach(constraintPenaltyPair -> {
            variableCounter.increment();
            problem.introduceVariableToObjective(variableCounter.toString(),
                                                 -constraintPenaltyPair.getRight());
            translate(constraintPenaltyPair.getLeft(), variableCounter.toString());
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

//        Result result =

        predicateMap.forEach((s, ccmPredicate) -> {
            ccmPredicate.setResult(ilpProblem);
        });
    }

    private void addConstraint(Linear linear, Operator operator, double rhs) {
        constraintCounter.increment();
        switch (operator) {
            case GE:
                problem
                    .addGreaterThanConstraint(linear.variables, linear.weights.toArray(),
                                              rhs);
                break;
            case LE:
                problem.addLessThanConstraint(linear.variables, linear.weights.toArray(),
                                              rhs);
                break;
            case EQ:
                problem.addEqualityConstraint(linear.variables, linear.weights.toArray(),
                                              rhs);
                break;
            default:
                break;
        }
    }

    private void addIndicatorConstraint(String variableName) {
        this.problem.introduceVariableToObjective(variableName, 0);
    }

    private void addEquivalenceConstraint(String variable1, String variable2) {
        Linear linear = new Linear();
        linear.add(1, variable1);
        linear.add(-1, variable2);
        addConstraint(linear, Operator.EQ, 0);
    }

    private void addNegationConstraint(String variable1, String variable2) {
        Linear linear = new Linear();
        linear.add(1, variable1);
        linear.add(1, variable2);
        addConstraint(linear, Operator.EQ, 1);
    }

    private void handleFormulaChildren(LogicFormula logicFormula, Linear... linears) {
        if (logicFormula instanceof BooleanVariable) {
            CCMPredicate
                predicate =
                predicateMap.get(((BooleanVariable) logicFormula).predicateId());
            CCMTerm term = termMap.get(((BooleanVariable) logicFormula).termId());

            addIndicatorConstraint(predicate.getID() + "$" + term.getID());
            for (Linear l : linears) {
                l.add(1, predicate.getID() + "$" + term.getID());
            }
        } else {
            variableCounter.increment();

            addIndicatorConstraint(variableCounter.toString());
            for (Linear l : linears) {
                l.add(1, variableCounter.toString());
            }

            translate(logicFormula, variableCounter.toString());
        }
    }

    private void translateConjunction(Conjunction conjunction, String inheritedName) {
        List<LogicFormula> oldFormulas = new ArrayList<>();
        List<LogicFormula> newFormulas = conjunction.getFormulas();
        while (newFormulas.size() != oldFormulas.size()) {
            oldFormulas = newFormulas;
            newFormulas = new ArrayList<>();

            for (LogicFormula logicFormula : oldFormulas) {
                if (logicFormula instanceof Conjunction) {
                    newFormulas.addAll(((Conjunction) logicFormula).getFormulas());
                } else {
                    newFormulas.add(logicFormula);
                }
            }
        }
        conjunction = new Conjunction(newFormulas);

        if (conjunction.getFormulas().size() > 0) {
            if (inheritedName == null) {
                conjunction.getFormulas().forEach(folFormula -> {
                    translate(folFormula, null);
                });
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-conjunction.getFormulas().size(), inheritedName);
                l2.add(-1, inheritedName);

                for (LogicFormula logicFormula : conjunction.getFormulas()) {
                    handleFormulaChildren(logicFormula, l1, l2);
                }

                addConstraint(l1, Operator.GE, 0);
                addConstraint(l2, Operator.LE, conjunction.getFormulas().size() - 1);
            }
        }
    }

    private void translateDisjunction(Disjunction disjunction, String inheritedName) {
        List<LogicFormula> oldFormulas = new ArrayList<>();
        List<LogicFormula> newFormulas = disjunction.getFormulas();
        while (newFormulas.size() != oldFormulas.size()) {
            oldFormulas = newFormulas;
            newFormulas = new ArrayList<>();

            for (LogicFormula logicFormula : oldFormulas) {
                if (logicFormula instanceof Disjunction) {
                    newFormulas.addAll(((Disjunction) logicFormula).getFormulas());
                } else {
                    newFormulas.add(logicFormula);
                }
            }
        }
        disjunction = new Disjunction(newFormulas);

        if (disjunction.getFormulas().size() > 0) {
            if (inheritedName == null) {
                Linear l1 = new Linear();

                disjunction.getFormulas().forEach(folFormula -> {
                    if (folFormula instanceof BooleanVariable) {
                        CCMPredicate
                            predicate =
                            predicateMap.get(((BooleanVariable) folFormula).predicateId());
                        CCMTerm term = termMap.get(((BooleanVariable) folFormula).termId());

                        addIndicatorConstraint(predicate.getID() + "$" + term.getID());
                        l1.add(1, predicate.getID() + "$" + term.getID());
                    } else {
                        variableCounter.increment();
                        addIndicatorConstraint(variableCounter.toString());
                        l1.add(1, variableCounter.toString());

                        translate(folFormula, variableCounter.toString());
                    }
                });

                addConstraint(l1, Operator.GE, 1);
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-1, inheritedName);
                l2.add(-disjunction.getFormulas().size(), inheritedName);
                for (LogicFormula logicFormula : disjunction.getFormulas()) {
                    handleFormulaChildren(logicFormula, l1, l2);
                }

                addConstraint(l1, Operator.GE, 0);
                addConstraint(l2, Operator.LE, 0);
            }
        } else {
            if (inheritedName == null) {
                Linear l1 = new Linear();
                addConstraint(l1, Operator.EQ, 1);
            } else {
                Linear l1 = new Linear();
                l1.add(1, inheritedName);
                addConstraint(l1, Operator.EQ, 0);
            }
        }
    }

    private void translateExactK(ExactK exactK, String inheritedName) {
        if (inheritedName == null) {
            Linear l1 = new Linear();

            for (LogicFormula logicFormula : exactK.getFormulas()) {
                handleFormulaChildren(logicFormula, l1);
            }

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

            for (LogicFormula logicFormula : exactK.getFormulas()) {
                handleFormulaChildren(logicFormula, l1, l2, l3, l4);
            }

            addConstraint(l1, Operator.GE, 0);
            addConstraint(l2, Operator.LE, exactK.getK() - 1);
            addConstraint(l3, Operator.LE, exactK.getFormulas().size());
            addConstraint(l4, Operator.GE, exactK.getK() + 1);
        }
    }

    private void translateAtLeast(AtLeast atLeast, String inheritedName) {
        if (atLeast.getK() > atLeast.getFormulas().size()) {
            if (inheritedName == null) {
                Linear l1 = new Linear();
                addConstraint(l1, Operator.EQ, 1);
            } else {
                Linear l1 = new Linear();
                l1.add(1, inheritedName);
                addConstraint(l1, Operator.EQ, 0);
            }
        } else {
            if (inheritedName == null) {
                Linear l1 = new Linear();

                for (LogicFormula logicFormula : atLeast.getFormulas()) {
                    handleFormulaChildren(logicFormula, l1);
                }

                addConstraint(l1, Operator.GE, atLeast.getK());
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-atLeast.getK(), inheritedName);
                l2.add(-atLeast.getFormulas().size(), inheritedName);

                for (LogicFormula logicFormula : atLeast.getFormulas()) {
                    handleFormulaChildren(logicFormula, l1, l2);
                }

                addConstraint(l1, Operator.GE, 0);
                addConstraint(l2, Operator.LE, atLeast.getK() - 1);
            }
        }
    }

    private void translateAtMost(AtMost atMost, String inheritedName) {
        if (atMost.getK() >= atMost.getFormulas().size()) {
            if (inheritedName != null) {
                Linear l1 = new Linear();
                l1.add(1, inheritedName);
                addConstraint(l1, Operator.EQ, 1);
            }
        } else {
            if (inheritedName == null) {
                Linear l1 = new Linear();

                for (LogicFormula logicFormula : atMost.getFormulas()) {
                    handleFormulaChildren(logicFormula, l1);
                }

                addConstraint(l1, Operator.LE, atMost.getK());
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(atMost.getFormulas().size() - atMost.getK(), inheritedName);
                l2.add(atMost.getFormulas().size(), inheritedName);

                for (LogicFormula logicFormula : atMost.getFormulas()) {
                    handleFormulaChildren(logicFormula, l1, l2);
                }

                addConstraint(l1, Operator.LE, atMost.getFormulas().size());
                addConstraint(l2, Operator.GE, atMost.getK() + 1);
            }
        }
    }

    private void translate(LogicFormula formula, String inheritedName) {
        if (formula instanceof Conjunction) {
            Conjunction conjunction = (Conjunction) formula;

            translateConjunction(conjunction, inheritedName);
        } else if (formula instanceof Disjunction) {
            Disjunction disjunction = (Disjunction) formula;

            translateDisjunction(disjunction, inheritedName);
        } else if (formula instanceof AtLeast) {
            AtLeast atLeast = (AtLeast) formula;

            translateAtLeast(atLeast, inheritedName);
        } else if (formula instanceof AtMost) {
            AtMost atMost = (AtMost) formula;

            translateAtMost(atMost, inheritedName);
        } else if (formula instanceof ExactK) {
            ExactK exactK = (ExactK) formula;

            translateExactK(exactK, inheritedName);
        } else if (formula instanceof NotExactK) {
            NotExactK notExactK = (NotExactK) formula;

            List<LogicFormula> formulas = new ArrayList<>(2);
            formulas.add(new AtLeast(notExactK.getK() + 1, notExactK.getFormulas()));
            formulas.add(new AtMost(notExactK.getK() - 1, notExactK.getFormulas()));

            translateDisjunction(new Disjunction(formulas), inheritedName);
        } else if (formula instanceof BooleanVariable) {
            BooleanVariable booleanVariable = (BooleanVariable) formula;

            CCMPredicate predicate = predicateMap.get(booleanVariable.predicateId());
            CCMTerm term = termMap.get(booleanVariable.termId());

            addIndicatorConstraint(predicate.getID() + "$" + term.getID());
            if (inheritedName != null) {
                addEquivalenceConstraint(inheritedName, predicate.getID() + "$" + term.getID());
            }
        } else if (formula instanceof Negation) {
            Negation negation = (Negation) formula;

            if (negation.getFormula() instanceof BooleanVariable) {
                BooleanVariable booleanVariable = (BooleanVariable) negation.getFormula();

                CCMPredicate predicate = predicateMap.get(booleanVariable.predicateId());
                CCMTerm term = termMap.get(booleanVariable.termId());

                addIndicatorConstraint(predicate.getID() + "$" + term.getID());
                if (inheritedName != null) {
                    addNegationConstraint(inheritedName, predicate.getID() + "$" + term.getID());
                }
            } else {
                throw new RuntimeException("NNF failed or is not called before translation.");
            }
        } else {
            throw new RuntimeException("Unknown LogicFormula implementation.");
        }
    }

}