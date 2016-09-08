package edu.illinois.cs.cogcomp.inference;

import gnu.trove.list.TDoubleList;
import gnu.trove.list.TIntList;
import gnu.trove.list.linked.TDoubleLinkedList;
import gnu.trove.list.linked.TIntLinkedList;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cogcomp.infer.ilp.ILPSolver;
import edu.illinois.cs.cogcomp.ir.IndicatorVariable;
import edu.illinois.cs.cogcomp.ir.fol.FolFormula;
import edu.illinois.cs.cogcomp.ir.fol.norm.Conjunction;
import edu.illinois.cs.cogcomp.ir.fol.norm.Disjunction;
import edu.illinois.cs.cogcomp.ir.fol.norm.Negation;
import edu.illinois.cs.cogcomp.ir.fol.quantifier.AtLeast;
import edu.illinois.cs.cogcomp.ir.fol.quantifier.AtMost;
import edu.illinois.cs.cogcomp.ir.fol.quantifier.ExactK;
import edu.illinois.cs.cogcomp.ir.fol.quantifier.Exist;
import edu.illinois.cs.cogcomp.ir.fol.quantifier.Forall;
import edu.illinois.cs.cogcomp.ir.fol.quantifier.NotExactK;


/**
 * Created by binglin on 4/17/16.
 */
public class CCMLogicSolver {

    public enum Operator {
        LE, GE, EQ
    }

    public class Linear {

        public TIntList variables;
        public TDoubleList weights;

        public Linear() {
            variables = new TIntLinkedList();
            weights = new TDoubleLinkedList();
        }

        public void add(double weight, String variableName) {
            int idx = getIdOfVariableThatAreNotInObj(variableName);
            variables.add(idx);
            weights.add(weight);
        }
    }

    private ILPSolver problem;
    private final List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective;
    private final List<FolFormula> constraints;
    private final Counter variableCounter;
    private final Counter constraintCounter;
    private final Map<String, ? extends CCMPredicate> predicateMap;
    private final Map<String, ? extends CCMTerm> termMap;

    // Stuff for Cogcomp Inference package.
    Map<String, Integer> variableNameToInteger = new HashMap<>();

    protected int getIdOfVariableThatAreNotInObj(String name) {
        if (variableNameToInteger.containsKey(name)) {
            return variableNameToInteger.get(name);
        } else {
            int newIdx = problem.addBooleanVariable(0);
            variableNameToInteger.put(name, newIdx);
            return newIdx;
        }
    }

    public CCMLogicSolver(
        List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective,
        List<FolFormula> constraints,
        Map<String, ? extends CCMPredicate> predicateMap,
        Map<String, ? extends CCMTerm> termMap) {
        this.objective = objective;
        this.constraints = constraints;
        this.variableCounter = new Counter("NV$");
        this.constraintCounter = new Counter("C$");
        this.predicateMap = predicateMap;
        this.termMap = termMap;
        problem = null;
    }

    public ILPSolver getProblem() {
        return problem;
    }

    public void prepare(ILPSolver problem) {
        // Set objective function
        this.problem = problem;

        for (Pair<CCMPredicate, Collection<? extends CCMTerm>> pair : objective) {
            CCMPredicate predicate = pair.getKey();
            Collection<? extends CCMTerm> terms = pair.getValue();

            for (CCMTerm term : terms) {
                double weight = predicate.getScore(term);
                String name = predicate.getID() + "$" + term.getID();
                int idx = problem.addBooleanVariable(weight);
                variableNameToInteger.put(name, idx);
            }


        }

//        objective.forEach(pair -> {
//            CCMPredicate predicate = pair.getKey();
//            Collection<? extends CCMTerm> terms = pair.getValue();
//
//            terms.forEach(term -> {
//                linear.add(predicate.getScore(term), predicate.getID() + "$" + term.getID());
//            });
//        });

//        problem.setObjective(linear, OptType.MAX);
        problem.setMaximize(true);

        // Set constraints
        constraints.forEach(folFormula -> {
//            translate(folFormula.toNnf(), null);
            translate(folFormula, null);
        });


    }

    public void solve(ILPSolver ilpSolver) {
        if (problem == null) {
            prepare(ilpSolver);
        }

        try {
            ilpSolver.solve();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Result result =

        predicateMap.forEach((s, ccmPredicate) -> {
            ccmPredicate.setResult(ilpSolver, this.variableNameToInteger);
        });
    }

    private void addConstraint(Linear linear, Operator operator, double rhs) {
        constraintCounter.increment();
        switch (operator) {
            case GE:
                problem
                    .addGreaterThanConstraint(linear.variables.toArray(), linear.weights.toArray(),
                                              rhs);
//                problem.add(new Constraint(constraintCounter.toString(), linear, ">=", rhs));
                break;
            case LE:
                problem.addLessThanConstraint(linear.variables.toArray(), linear.weights.toArray(),
                                              rhs);
//                problem.add(new Constraint(constraintCounter.toString(), linear, "<=", rhs));
                break;
            case EQ:
                problem.addEqualityConstraint(linear.variables.toArray(), linear.weights.toArray(),
                                              rhs);
//                problem.add(new Constraint(constraintCounter.toString(), linear, "=", rhs));
                break;
            default:
                break;
        }
    }

    private void addIndicatorConstraint(String variableName) {

        if (!variableNameToInteger.containsKey(variableName)) {
            int newIdx = problem.addBooleanVariable(0);
            variableNameToInteger.put(variableName, newIdx);
        }
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

    private void handleFormulaChildren(FolFormula folFormula, Linear... linears) {
        if (folFormula instanceof IndicatorVariable) {
            CCMPredicate predicate = predicateMap.get(((IndicatorVariable) folFormula).predicateId());
            CCMTerm term = termMap.get(((IndicatorVariable) folFormula).termId());

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

            translate(folFormula, variableCounter.toString());
        }
    }

    private void translateConjunction(Conjunction conjunction, String inheritedName) {
        List<FolFormula> oldFormulas = new ArrayList<>();
        List<FolFormula> newFormulas = conjunction.getFormulas();
        while (newFormulas.size() != oldFormulas.size()) {
            oldFormulas = newFormulas;
            newFormulas = new ArrayList<>();

            for (FolFormula folFormula : oldFormulas) {
                if (folFormula instanceof Conjunction) {
                    newFormulas.addAll(((Conjunction) folFormula).getFormulas());
                } else if (folFormula instanceof Forall) {
                    newFormulas.addAll(((Forall) folFormula).getFormulas());
                } else {
                    newFormulas.add(folFormula);
                }
            }
        }
        conjunction = new Conjunction(newFormulas);

        if (inheritedName == null) {
            conjunction.getFormulas().forEach(folFormula -> {
                translate(folFormula, null);
            });
        } else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            l1.add(-conjunction.getFormulas().size(), inheritedName);
            l2.add(-1, inheritedName);

            for (FolFormula folFormula : conjunction.getFormulas()) {
                handleFormulaChildren(folFormula, l1, l2);
            }

            addConstraint(l1, Operator.GE, 0);
            addConstraint(l2, Operator.LE, conjunction.getFormulas().size() - 1);
        }
    }

    private void translateDisjunction(Disjunction disjunction, String inheritedName) {
        List<FolFormula> oldFormulas = new ArrayList<>();
        List<FolFormula> newFormulas = disjunction.getFormulas();
        while (newFormulas.size() != oldFormulas.size()) {
            oldFormulas = newFormulas;
            newFormulas = new ArrayList<>();

            for (FolFormula folFormula : oldFormulas) {
                if (folFormula instanceof Disjunction) {
                    newFormulas.addAll(((Disjunction) folFormula).getFormulas());
                } else if (folFormula instanceof Exist) {
                    newFormulas.addAll(((Exist) folFormula).getFormulas());
                } else {
                    newFormulas.add(folFormula);
                }
            }
        }
        disjunction = new Disjunction(newFormulas);

        if (inheritedName == null) {
            Linear l1 = new Linear();

            disjunction.getFormulas().forEach(folFormula -> {
                if (folFormula instanceof IndicatorVariable) {
                    CCMPredicate
                        predicate =
                        predicateMap.get(((IndicatorVariable) folFormula).predicateId());
                    CCMTerm term = termMap.get(((IndicatorVariable) folFormula).termId());

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
            for (FolFormula folFormula : disjunction.getFormulas()) {
                handleFormulaChildren(folFormula, l1, l2);
            }

            addConstraint(l1, Operator.GE, 0);
            addConstraint(l2, Operator.LE, 0);
        }
    }

    private void translateExactK(ExactK exactK, String inheritedName) {
        if (inheritedName == null) {
            Linear l1 = new Linear();

            for (FolFormula folFormula : exactK.getFormulas()) {
                handleFormulaChildren(folFormula, l1);
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

            for (FolFormula folFormula : exactK.getFormulas()) {
                handleFormulaChildren(folFormula, l1, l2, l3, l4);
            }

            addConstraint(l1, Operator.GE, 0);
            addConstraint(l2, Operator.LE, exactK.getK() - 1);
            addConstraint(l3, Operator.LE, exactK.getFormulas().size());
            addConstraint(l4, Operator.GE, exactK.getK() + 1);
        }
    }

    private void translateAtLeast(AtLeast atLeast, String inheritedName) {
        if (inheritedName == null) {
            Linear l1 = new Linear();

            for (FolFormula folFormula : atLeast.getFormulas()) {
                handleFormulaChildren(folFormula, l1);
            }

            addConstraint(l1, Operator.GE, atLeast.getK());
        } else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            l1.add(-atLeast.getK(), inheritedName);
            l2.add(-atLeast.getFormulas().size(), inheritedName);

            for (FolFormula folFormula : atLeast.getFormulas()) {
                handleFormulaChildren(folFormula, l1, l2);
            }

            addConstraint(l1, Operator.GE, 0);
            addConstraint(l2, Operator.LE, atLeast.getK() - 1);
        }
    }

    private void translateAtMost(AtMost atMost, String inheritedName) {
        if (inheritedName == null) {
            Linear l1 = new Linear();

            for (FolFormula folFormula : atMost.getFormulas()) {
                handleFormulaChildren(folFormula, l1);
            }

            addConstraint(l1, Operator.LE, atMost.getK());
        } else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            l1.add(atMost.getFormulas().size() - atMost.getK(), inheritedName);
            l2.add(atMost.getFormulas().size(), inheritedName);

            for (FolFormula folFormula : atMost.getFormulas()) {
                handleFormulaChildren(folFormula, l1, l2);
            }

            addConstraint(l1, Operator.LE, atMost.getFormulas().size());
            addConstraint(l2, Operator.GE, atMost.getK() + 1);
        }
    }

    private void translate(FolFormula formula, String inheritedName) {
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
        } else if (formula instanceof Exist) {
            Exist exist = (Exist) formula;

            translateDisjunction(new Disjunction(exist.getFormulas()), inheritedName);
        } else if (formula instanceof Forall) {
            Forall forall = (Forall) formula;

            translateConjunction(new Conjunction(forall.getFormulas()), inheritedName);
        } else if (formula instanceof ExactK) {
            ExactK exactK = (ExactK) formula;

            translateExactK(exactK, inheritedName);
        } else if (formula instanceof NotExactK) {
            NotExactK notExactK = (NotExactK) formula;

            List<FolFormula> formulas = new ArrayList<>(2);
            formulas.add(new AtLeast(notExactK.getK() + 1, notExactK.getFormulas()));
            formulas.add(new AtMost(notExactK.getK() - 1, notExactK.getFormulas()));

            translateDisjunction(new Disjunction(formulas), inheritedName);
        } else if (formula instanceof IndicatorVariable) {
            IndicatorVariable indicatorVariable = (IndicatorVariable) formula;

            CCMPredicate predicate = predicateMap.get(indicatorVariable.predicateId());
            CCMTerm term = termMap.get(indicatorVariable.termId());

            addIndicatorConstraint(predicate.getID() + "$" + term.getID());
            if (inheritedName != null) {
                addEquivalenceConstraint(inheritedName, predicate.getID() + "$" + term.getID());
            }
        } else if (formula instanceof Negation) {
            Negation negation = (Negation) formula;

            if (negation.getFormula() instanceof IndicatorVariable) {
                IndicatorVariable indicatorVariable = (IndicatorVariable) negation.getFormula();

                CCMPredicate predicate = predicateMap.get(indicatorVariable.predicateId());
                CCMTerm term = termMap.get(indicatorVariable.termId());

                addIndicatorConstraint(predicate.getID() + "$" + term.getID());
                if (inheritedName != null) {
                    addNegationConstraint(inheritedName, predicate.getID() + "$" + term.getID());
                }
            } else {
                throw new RuntimeException("NNF failed or is not called before translation.");
            }
        } else {
            throw new RuntimeException("Unknown FolFormula implementation.");
        }
    }

}