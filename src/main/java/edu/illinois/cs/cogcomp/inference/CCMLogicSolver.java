package edu.illinois.cs.cogcomp.inference;


import edu.illinois.cs.cogcomp.ir.fol.quantifier.*;
import net.sf.javailp.*;

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


/**
 * Created by binglin on 4/17/16.
 */
public class CCMLogicSolver {

    private static void addConstraint(Problem problem, Linear linear, String operator, Number rhs, Counter constraintCounter) {
        constraintCounter.increment();
        problem.add(new Constraint(constraintCounter.toString(), linear, operator, rhs));
    }

    private static void addIndicatorConstraint(Problem problem, String variable, Counter constraintCounter) {
        problem.setVarType(variable, Boolean.class);
    }

    private static void addEquivalenceConstraint(Problem problem, String variable1, String variable2, Counter constraintCounter) {
        Linear linear = new Linear();
        linear.add(1, variable1);
        linear.add(-1, variable2);
        addConstraint(problem, linear, "=", 0, constraintCounter);
    }

    private static void addNegationConstraint(Problem problem, String variable1, String variable2, Counter constraintCounter) {
        Linear linear = new Linear();
        linear.add(1, variable1);
        linear.add(1, variable2);
        addConstraint(problem, linear, "=", 1, constraintCounter);
    }

    private static void recursiveTranslateConjunction(Problem problem, Conjunction conjunction,
                                                      String inheritedName, Counter variableCounter, Counter constraintCounter,
                                                      Map<String, ? extends CCMPredicate> predicateMap, Map<String, ? extends CCMTerm> termMap) {
        List<FolFormula> oldFormulas = new ArrayList<>();
        List<FolFormula> newFormulas = conjunction.getFormulas();
        while (newFormulas.size() != oldFormulas.size()) {
            oldFormulas = newFormulas;
            newFormulas = new ArrayList<>();

            for (FolFormula folFormula: oldFormulas) {
                if (folFormula instanceof Conjunction) {
                    newFormulas.addAll(((Conjunction) folFormula).getFormulas());
                }
                else if (folFormula instanceof Forall) {
                    newFormulas.addAll(((Forall) folFormula).getFormulas());
                }
                else {
                    newFormulas.add(folFormula);
                }
            }
        }
        conjunction = new Conjunction(newFormulas);

        if (inheritedName == null) {
            conjunction.getFormulas().forEach(folFormula -> {
                recursiveTranslate(problem, folFormula, null, variableCounter, constraintCounter, predicateMap, termMap);
            });
        }
        else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            l1.add(- conjunction.getFormulas().size(), inheritedName);
            l2.add(-1, inheritedName);

            conjunction.getFormulas().forEach(folFormula -> {
                if (folFormula instanceof IndicatorVariable) {
                    CCMPredicate predicate = predicateMap.get(((IndicatorVariable) folFormula).predicateId());
                    CCMTerm term = termMap.get(((IndicatorVariable) folFormula).termId());

                    addIndicatorConstraint(problem, predicate.getID() + "$" + term.getID(), constraintCounter);
                    l1.add(1, predicate.getID() + "$" + term.getID());
                    l2.add(1, predicate.getID() + "$" + term.getID());
                }
                else {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    recursiveTranslate(problem, folFormula, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
                }
            });

            addConstraint(problem, l1, ">=", 0, constraintCounter);
            addConstraint(problem, l2, "<=", conjunction.getFormulas().size() - 1, constraintCounter);
        }
    }

    private static void recursiveTranslateDisjunction(Problem problem, Disjunction disjunction,
                                                      String inheritedName, Counter variableCounter, Counter constraintCounter,
                                                      Map<String, ? extends CCMPredicate> predicateMap, Map<String, ? extends CCMTerm> termMap) {
        List<FolFormula> oldFormulas = new ArrayList<>();
        List<FolFormula> newFormulas = disjunction.getFormulas();
        while (newFormulas.size() != oldFormulas.size()) {
            oldFormulas = newFormulas;
            newFormulas = new ArrayList<>();

            for (FolFormula folFormula: oldFormulas) {
                if (folFormula instanceof Disjunction) {
                    newFormulas.addAll(((Disjunction) folFormula).getFormulas());
                }
                else if (folFormula instanceof Exist) {
                    newFormulas.addAll(((Exist) folFormula).getFormulas());
                }
                else {
                    newFormulas.add(folFormula);
                }
            }
        }
        disjunction = new Disjunction(newFormulas);

        if (inheritedName == null) {
            Linear l1 = new Linear();

            disjunction.getFormulas().forEach(folFormula -> {
                if (folFormula instanceof IndicatorVariable) {
                    CCMPredicate predicate = predicateMap.get(((IndicatorVariable) folFormula).predicateId());
                    CCMTerm term = termMap.get(((IndicatorVariable) folFormula).termId());

                    addIndicatorConstraint(problem, predicate.getID() + "$" + term.getID(), constraintCounter);
                    l1.add(1, predicate.getID() + "$" + term.getID());
                }
                else {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());

                    recursiveTranslate(problem, folFormula, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
                }
            });

            addConstraint(problem, l1, ">=", 1, constraintCounter);
        }
        else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            l1.add(-1, inheritedName);
            l2.add(- disjunction.getFormulas().size(), inheritedName);

            disjunction.getFormulas().forEach(folFormula -> {
                if (folFormula instanceof IndicatorVariable) {
                    CCMPredicate predicate = predicateMap.get(((IndicatorVariable) folFormula).predicateId());
                    CCMTerm term = termMap.get(((IndicatorVariable) folFormula).termId());

                    addIndicatorConstraint(problem, predicate.getID() + "$" + term.getID(), constraintCounter);
                    l1.add(1, predicate.getID() + "$" + term.getID());
                    l2.add(1, predicate.getID() + "$" + term.getID());
                }
                else {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    recursiveTranslate(problem, folFormula, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
                }
            });

            addConstraint(problem, l1, ">=", 0, constraintCounter);
            addConstraint(problem, l2, "<=", 0, constraintCounter);
        }
    }

    private static void recursiveTranslateExactK(Problem problem, ExactK exactK,
                                                 String inheritedName, Counter variableCounter, Counter constraintCounter,
                                                 Map<String, ? extends CCMPredicate> predicateMap, Map<String, ? extends CCMTerm> termMap) {
        if (inheritedName == null) {
            Linear l1 = new Linear();

            exactK.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                l1.add(1, variableCounter.toString());

                recursiveTranslate(problem, folFormula, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
            });

            addConstraint(problem, l1, "=", exactK.getK(), constraintCounter);
        }
        else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            Linear l3 = new Linear();
            Linear l4 = new Linear();
            l1.add(- exactK.getK(), inheritedName);
            l2.add(- exactK.getFormulas().size(), inheritedName);
            l3.add(exactK.getFormulas().size() - exactK.getK(), inheritedName);
            l4.add(exactK.getFormulas().size(), inheritedName);

            exactK.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                l1.add(1, variableCounter.toString());
                l2.add(1, variableCounter.toString());
                l3.add(1, variableCounter.toString());
                l4.add(1, variableCounter.toString());

                recursiveTranslate(problem, folFormula, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
            });

            addConstraint(problem, l1, ">=", 0, constraintCounter);
            addConstraint(problem, l2, "<=", exactK.getK() - 1, constraintCounter);
            addConstraint(problem, l3, "<=", exactK.getFormulas().size(), constraintCounter);
            addConstraint(problem, l4, ">=", exactK.getK() + 1, constraintCounter);
        }
    }

    private static void recursiveTranslateAtLeast(Problem problem, AtLeast atLeast,
                                                  String inheritedName, Counter variableCounter, Counter constraintCounter,
                                                  Map<String, ? extends CCMPredicate> predicateMap, Map<String, ? extends CCMTerm> termMap) {
        if (inheritedName == null) {
            Linear l1 = new Linear();

            atLeast.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                l1.add(1, variableCounter.toString());

                recursiveTranslate(problem, folFormula, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
            });

            addConstraint(problem, l1, ">=", atLeast.getK(), constraintCounter);
        }
        else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            l1.add(- atLeast.getK(), inheritedName);
            l2.add(- atLeast.getFormulas().size(), inheritedName);

            atLeast.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                l1.add(1, variableCounter.toString());
                l2.add(1, variableCounter.toString());

                recursiveTranslate(problem, folFormula, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
            });

            addConstraint(problem, l1, ">=", 0, constraintCounter);
            addConstraint(problem, l2, "<=", atLeast.getK() - 1, constraintCounter);
        }
    }

    private static void recursiveTranslateAtMost(Problem problem, AtMost atMost,
                                                 String inheritedName, Counter variableCounter, Counter constraintCounter,
                                                 Map<String, ? extends CCMPredicate> predicateMap, Map<String, ? extends CCMTerm> termMap) {
        if (inheritedName == null) {
            Linear l1 = new Linear();

            atMost.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                l1.add(1, variableCounter.toString());

                recursiveTranslate(problem, folFormula, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
            });

            addConstraint(problem, l1, "<=", atMost.getK(), constraintCounter);
        }
        else {
            Linear l1 = new Linear();
            Linear l2 = new Linear();
            l1.add(atMost.getFormulas().size() - atMost.getK(), inheritedName);
            l2.add(atMost.getFormulas().size(), inheritedName);

            atMost.getFormulas().forEach(folFormula -> {
                variableCounter.increment();
                addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                l1.add(1, variableCounter.toString());
                l2.add(1, variableCounter.toString());

                recursiveTranslate(problem, folFormula, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
            });

            addConstraint(problem, l1, "<=", atMost.getFormulas().size(), constraintCounter);
            addConstraint(problem, l2, ">=", atMost.getK() + 1, constraintCounter);
        }
    }

    private static void recursiveTranslate(Problem problem, FolFormula formula,
                                           String inheritedName, Counter variableCounter, Counter constraintCounter,
                                           Map<String, ? extends CCMPredicate> predicateMap, Map<String, ? extends CCMTerm> termMap) {
        if (formula instanceof Conjunction) {
            Conjunction conjunction = (Conjunction) formula;

            recursiveTranslateConjunction(problem, conjunction, inheritedName, variableCounter, constraintCounter, predicateMap, termMap);
        }
        else if (formula instanceof Disjunction) {
            Disjunction disjunction = (Disjunction) formula;

            recursiveTranslateDisjunction(problem, disjunction, inheritedName, variableCounter, constraintCounter, predicateMap, termMap);
        }
        else if (formula instanceof AtLeast) {
            AtLeast atLeast = (AtLeast) formula;

            recursiveTranslateAtLeast(problem, atLeast, inheritedName, variableCounter, constraintCounter, predicateMap, termMap);
        }
        else if (formula instanceof AtMost) {
            AtMost atMost = (AtMost) formula;

            recursiveTranslateAtMost(problem, atMost, inheritedName, variableCounter, constraintCounter, predicateMap, termMap);
        }
        else if (formula instanceof Exist) {
            Exist exist = (Exist) formula;

            recursiveTranslateDisjunction(problem, new Disjunction(exist.getFormulas()), inheritedName, variableCounter, constraintCounter, predicateMap, termMap);
        }
        else if (formula instanceof Forall) {
            Forall forall = (Forall) formula;

            recursiveTranslateConjunction(problem, new Conjunction(forall.getFormulas()), inheritedName, variableCounter, constraintCounter, predicateMap, termMap);
        }
        else if (formula instanceof ExactK) {
            ExactK exactK = (ExactK) formula;

            recursiveTranslateExactK(problem, exactK, inheritedName, variableCounter, constraintCounter, predicateMap, termMap);
        }
        else if (formula instanceof NotExactK) {
            NotExactK notExactK = (NotExactK) formula;

            List<FolFormula> formulas = new ArrayList<>(2);
            formulas.add(new AtLeast(notExactK.getK() + 1, notExactK.getFormulas()));
            formulas.add(new AtMost(notExactK.getK() - 1, notExactK.getFormulas()));

            recursiveTranslateDisjunction(problem, new Disjunction(formulas), inheritedName, variableCounter, constraintCounter, predicateMap, termMap);
        }
        else if (formula instanceof IndicatorVariable) {
            IndicatorVariable indicatorVariable = (IndicatorVariable) formula;

            CCMPredicate predicate = predicateMap.get(indicatorVariable.predicateId());
            CCMTerm term = termMap.get(indicatorVariable.termId());

            addIndicatorConstraint(problem, predicate.getID() + "$" + term.getID(), constraintCounter);
            if (inheritedName != null) {
                addEquivalenceConstraint(problem, inheritedName, predicate.getID() + "$" + term.getID(), constraintCounter);
            }
        }
        else if (formula instanceof Negation) {
            Negation negation = (Negation) formula;

            if (negation.getFormula() instanceof IndicatorVariable) {
                IndicatorVariable indicatorVariable = (IndicatorVariable) negation.getFormula();

                CCMPredicate predicate = predicateMap.get(indicatorVariable.predicateId());
                CCMTerm term = termMap.get(indicatorVariable.termId());

                addIndicatorConstraint(problem, predicate.getID() + "$" + term.getID(), constraintCounter);
                if (inheritedName != null) {
                    addNegationConstraint(problem, inheritedName, predicate.getID() + "$" + term.getID(), constraintCounter);
                }
            }
            else {
                throw new RuntimeException("NNF failed or is not called before translation.");
            }
        }
        else {
            throw new RuntimeException("Unknown FolFormula implementation.");
        }
    }

    public static Problem translateLogicToILP(List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective, List<FolFormula> constraints,
                                               Map<String, ? extends CCMPredicate> predicateMap, Map<String, ? extends CCMTerm> termMap) {
        Problem problem = new Problem();

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
        Counter variableCounter = new Counter("NV$");
        Counter constraintCounter = new Counter("C$");
        constraints.forEach(folFormula -> {
//            recursiveTranslate(problem, folFormula.toNnf(), null, variableCounter, constraintCounter, predicateMap, termMap);
            recursiveTranslate(problem, folFormula, null, variableCounter, constraintCounter, predicateMap, termMap);
        });

        return problem;
    }

    public static void translateAndSolve(List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective, List<FolFormula> constraints,
                             Map<String,? extends CCMPredicate> predicateMap, Map<String,? extends CCMTerm> termMap, int timeout) {
        Problem problem = translateLogicToILP(objective, constraints, predicateMap, termMap);
        solve(problem,predicateMap,timeout);
    }

    public static void solve(Problem problem, Map<String,? extends CCMPredicate> predicateMap,int timeout) {
        SolverFactory factory = new SolverFactoryGurobi();
        factory.setParameter(Solver.VERBOSE, 0);
        factory.setParameter(Solver.TIMEOUT, timeout);

        Solver solver = factory.get();
        Result result = solver.solve(problem);

        predicateMap.forEach((s, ccmPredicate) -> {
            ccmPredicate.setResult(result);
        });
    }
}