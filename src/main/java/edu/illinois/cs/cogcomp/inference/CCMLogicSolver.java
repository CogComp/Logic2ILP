package edu.illinois.cs.cogcomp.inference;


import net.sf.javailp.Constraint;
import net.sf.javailp.Linear;
import net.sf.javailp.OptType;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    private Problem problem;
    private final List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective;
    private final List<FolFormula> constraints;
    private final Counter variableCounter;
    private final Counter constraintCounter;
    private final Map<String, ? extends CCMPredicate> predicateMap;
    private final Map<String, ? extends CCMTerm> termMap;

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

    public Problem getProblem() {
        return problem;
    }

    public void prepare() {
        this.problem = new Problem();
        // Set objective function
        Linear linear = new Linear();
        objective.forEach(pair -> {
            CCMPredicate predicate = pair.getKey();
            Collection<? extends CCMTerm> terms = pair.getValue();

            terms.forEach(term -> {
                linear.add(predicate.getScore(term), predicate.getID() + "$" + term.getID());
            });
        });
        problem.setObjective(linear, OptType.MAX);

        // Set constraints
        constraints.forEach(folFormula -> {
//            translate(problem, folFormula.toNnf(), null, variableCounter , predicateMap, termMap);
            translate(folFormula, null);
        });


    }

    public void translateAndSolve(Solver ilpSolver) {
        if (problem == null) {
            prepare();
        }

        solve(ilpSolver);
    }

    public void solve(Solver ilpSolver) {
        Result result = ilpSolver.solve(problem);

        predicateMap.forEach((s, ccmPredicate) -> {
            ccmPredicate.setResult(result);
        });
    }

    private void addConstraint(Linear linear, String operator, Number rhs) {
        constraintCounter.increment();
        problem.add(new Constraint(constraintCounter.toString(), linear, operator, rhs));
    }

    private void addIndicatorConstraint(String variable) {
        problem.setVarType(variable, Boolean.class);
    }

    private void addEquivalenceConstraint(String variable1, String variable2) {
        Linear linear = new Linear();
        linear.add(1, variable1);
        linear.add(-1, variable2);
        addConstraint(linear, "=", 0);
    }

    private void addNegationConstraint(String variable1, String variable2) {
        Linear linear = new Linear();
        linear.add(1, variable1);
        linear.add(1, variable2);
        addConstraint(linear, "=", 1);
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

            conjunction.getFormulas().forEach(folFormula -> {
                if (folFormula instanceof IndicatorVariable) {
                    CCMPredicate
                        predicate =
                        predicateMap.get(((IndicatorVariable) folFormula).predicateId());
                    CCMTerm term = termMap.get(((IndicatorVariable) folFormula).termId());

                    addIndicatorConstraint(predicate.getID() + "$" + term.getID());
                    l1.add(1, predicate.getID() + "$" + term.getID());
                    l2.add(1, predicate.getID() + "$" + term.getID());
                } else {
                    variableCounter.increment();
                    addIndicatorConstraint(variableCounter.toString());
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    translate(folFormula, variableCounter.toString());
                }
            });

            addConstraint(l1, ">=", 0);
            addConstraint(l2, "<=", conjunction.getFormulas().size() - 1);
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

            addConstraint(l1, ">=", 1);
        } else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            l1.add(-1, inheritedName);
            l2.add(-disjunction.getFormulas().size(), inheritedName);

            disjunction.getFormulas().forEach(folFormula -> {
                if (folFormula instanceof IndicatorVariable) {
                    CCMPredicate
                        predicate =
                        predicateMap.get(((IndicatorVariable) folFormula).predicateId());
                    CCMTerm term = termMap.get(((IndicatorVariable) folFormula).termId());

                    addIndicatorConstraint(predicate.getID() + "$" + term.getID());
                    l1.add(1, predicate.getID() + "$" + term.getID());
                    l2.add(1, predicate.getID() + "$" + term.getID());
                } else {
                    variableCounter.increment();
                    addIndicatorConstraint(variableCounter.toString());
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    translate(folFormula, variableCounter.toString());
                }
            });

            addConstraint(l1, ">=", 0);
            addConstraint(l2, "<=", 0);
        }
    }

    private void translateExactK(ExactK exactK, String inheritedName) {
        if (inheritedName == null) {
            Linear l1 = new Linear();

            exactK.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(variableCounter.toString());
                l1.add(1, variableCounter.toString());

                translate(folFormula, variableCounter.toString());
            });

            addConstraint(l1, "=", exactK.getK());
        } else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            Linear l3 = new Linear();
            Linear l4 = new Linear();
            l1.add(-exactK.getK(), inheritedName);
            l2.add(-exactK.getFormulas().size(), inheritedName);
            l3.add(exactK.getFormulas().size() - exactK.getK(), inheritedName);
            l4.add(exactK.getFormulas().size(), inheritedName);

            exactK.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(variableCounter.toString());
                l1.add(1, variableCounter.toString());
                l2.add(1, variableCounter.toString());
                l3.add(1, variableCounter.toString());
                l4.add(1, variableCounter.toString());

                translate(folFormula, variableCounter.toString());
            });

            addConstraint(l1, ">=", 0);
            addConstraint(l2, "<=", exactK.getK() - 1);
            addConstraint(l3, "<=", exactK.getFormulas().size());
            addConstraint(l4, ">=", exactK.getK() + 1);
        }
    }

    private void translateAtLeast(AtLeast atLeast, String inheritedName) {
        if (inheritedName == null) {
            Linear l1 = new Linear();

            atLeast.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(variableCounter.toString());
                l1.add(1, variableCounter.toString());

                translate(folFormula, variableCounter.toString());
            });

            addConstraint(l1, ">=", atLeast.getK());
        } else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            l1.add(-atLeast.getK(), inheritedName);
            l2.add(-atLeast.getFormulas().size(), inheritedName);

            atLeast.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(variableCounter.toString());
                l1.add(1, variableCounter.toString());
                l2.add(1, variableCounter.toString());

                translate(folFormula, variableCounter.toString());
            });

            addConstraint(l1, ">=", 0);
            addConstraint(l2, "<=", atLeast.getK() - 1);
        }
    }

    private void translateAtMost(AtMost atMost, String inheritedName) {
        if (inheritedName == null) {
            Linear l1 = new Linear();

            atMost.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(variableCounter.toString());
                l1.add(1, variableCounter.toString());

                translate(folFormula, variableCounter.toString());
            });

            addConstraint(l1, "<=", atMost.getK());
        } else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            l1.add(atMost.getFormulas().size() - atMost.getK(), inheritedName);
            l2.add(atMost.getFormulas().size(), inheritedName);

            atMost.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(variableCounter.toString());
                l1.add(1, variableCounter.toString());
                l2.add(1, variableCounter.toString());

                translate(folFormula, variableCounter.toString());
            });

            addConstraint(l1, "<=", atMost.getFormulas().size());
            addConstraint(l2, ">=", atMost.getK() + 1);
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