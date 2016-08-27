package edu.illinois.cs.cogcomp.inference;


import net.sf.javailp.Constraint;
import net.sf.javailp.Linear;
import net.sf.javailp.OptType;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryGurobi;

import org.apache.commons.lang3.tuple.Pair;

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


/**
 * Created by binglin on 4/17/16.
 */
public class CCMLogicSolver {

    private static void addConstraint(Problem problem, Linear linear, String operator, Number rhs,
                                      Counter constraintCounter) {
        constraintCounter.increment();
        problem.add(new Constraint(constraintCounter.toString(), linear, operator, rhs));
    }

    private static void addIndicatorConstraint(Problem problem, String variable,
                                               Counter constraintCounter) {
        Linear linear = new Linear();
        linear.add(1, variable);
        addConstraint(problem, linear, ">=", 0, constraintCounter);
        addConstraint(problem, linear, "<=", 1, constraintCounter);
        problem.setVarType(variable, Integer.class);
    }

    private static void addEquivalenceConstraint(Problem problem, String variable1,
                                                 String variable2, Counter constraintCounter) {
        Linear linear = new Linear();
        linear.add(1, variable1);
        linear.add(-1, variable2);
        addConstraint(problem, linear, "=", 0, constraintCounter);
    }

    private static void addNegationConstraint(Problem problem, String variable1, String variable2,
                                              Counter constraintCounter) {
        Linear linear = new Linear();
        linear.add(1, variable1);
        linear.add(1, variable2);
        addConstraint(problem, linear, "=", 1, constraintCounter);
    }

    private static void recursiveTranslate(Problem problem, FolFormula formula,
                                           String inheritedName, Counter variableCounter,
                                           Counter constraintCounter,
                                           Map<String, ? extends CCMPredicate> predicateMap,
                                           Map<String, ? extends CCMTerm> termMap) {
        if (formula instanceof Conjunction) {
            Conjunction conjunction = (Conjunction) formula;

            if (inheritedName == null) {
                conjunction.getFormulas().forEach(c -> {
                    recursiveTranslate(problem, c, null, variableCounter, constraintCounter,
                                       predicateMap, termMap);
                });
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-conjunction.getFormulas().size(), inheritedName);
                l2.add(-1, inheritedName);

                conjunction.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", 0, constraintCounter);
                addConstraint(problem, l2, "<=", conjunction.getFormulas().size() - 1,
                              constraintCounter);
            }
        } else if (formula instanceof Disjunction) {
            Disjunction disjunction = (Disjunction) formula;

            if (inheritedName == null) {
                Linear l1 = new Linear();

                disjunction.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", 1, constraintCounter);
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-1, inheritedName);
                l2.add(-disjunction.getFormulas().size(), inheritedName);

                disjunction.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", 0, constraintCounter);
                addConstraint(problem, l2, "<=", 0, constraintCounter);
            }
        } else if (formula instanceof Forall) {
            Forall forall = (Forall) formula;

            if (inheritedName == null) {
                forall.getFormulas().forEach(c -> {
                    recursiveTranslate(problem, c, null, variableCounter, constraintCounter,
                                       predicateMap, termMap);
                });
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-forall.getFormulas().size(), inheritedName);
                l2.add(-1, inheritedName);

                forall.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", 0, constraintCounter);
                addConstraint(problem, l2, "<=", forall.getFormulas().size() - 1,
                              constraintCounter);
            }
        } else if (formula instanceof Exist) {
            Exist exist = (Exist) formula;

            if (inheritedName == null) {
                Linear l1 = new Linear();

                exist.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", 1, constraintCounter);
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-1, inheritedName);
                l2.add(-exist.getFormulas().size(), inheritedName);

                exist.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", 0, constraintCounter);
                addConstraint(problem, l2, "<=", 0, constraintCounter);
            }
        } else if (formula instanceof ExactK) {
            ExactK exactK = (ExactK) formula;

            if (inheritedName == null) {
                Linear l1 = new Linear();

                exactK.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, "=", exactK.getK(), constraintCounter);
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                Linear l3 = new Linear();
                Linear l4 = new Linear();
                l1.add(-exactK.getK(), inheritedName);
                l2.add(-exactK.getFormulas().size(), inheritedName);
                l3.add(exactK.getFormulas().size() - exactK.getK(), inheritedName);
                l4.add(exactK.getFormulas().size(), inheritedName);

                exactK.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());
                    l3.add(1, variableCounter.toString());
                    l4.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", 0, constraintCounter);
                addConstraint(problem, l2, "<=", exactK.getK() - 1, constraintCounter);
                addConstraint(problem, l3, "<=", exactK.getFormulas().size(), constraintCounter);
                addConstraint(problem, l4, ">=", exactK.getK() + 1, constraintCounter);
            }
        } else if (formula instanceof AtLeast) {
            AtLeast atLeast = (AtLeast) formula;

            if (inheritedName == null) {
                Linear l1 = new Linear();

                atLeast.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", atLeast.getK(), constraintCounter);
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-atLeast.getK(), inheritedName);
                l2.add(-atLeast.getFormulas().size(), inheritedName);

                atLeast.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", 0, constraintCounter);
                addConstraint(problem, l2, "<=", atLeast.getK() - 1, constraintCounter);
            }
        } else if (formula instanceof AtMost) {
            AtMost atMost = (AtMost) formula;

            if (inheritedName == null) {
                Linear l1 = new Linear();

                atMost.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, "<=", atMost.getK(), constraintCounter);
            } else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(atMost.getFormulas().size() - atMost.getK(), inheritedName);
                l2.add(atMost.getFormulas().size(), inheritedName);

                atMost.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter,
                                       constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, "<=", atMost.getFormulas().size(), constraintCounter);
                addConstraint(problem, l2, ">=", atMost.getK() + 1, constraintCounter);
            }
        } else if (formula instanceof IndicatorVariable) {
            IndicatorVariable folAtom = (IndicatorVariable) formula;
//            if (folAtom.getTerms().size() > 1) {
//                System.err.println("Not sure how to handle a predicate with more than two arguments.");
//            }

            CCMPredicate predicate = predicateMap.get(folAtom.predicateId());
            CCMTerm term = termMap.get(folAtom.termId());

            addIndicatorConstraint(problem, predicate.getID() + "$" + term.getID(),
                                   constraintCounter);
            if (inheritedName != null) {
                addEquivalenceConstraint(problem, inheritedName,
                                         predicate.getID() + "$" + term.getID(), constraintCounter);
            }
        } else if (formula instanceof Negation) {
            Negation negation = (Negation) formula;

            if (negation.getFormula() instanceof IndicatorVariable) {
                IndicatorVariable folAtom = (IndicatorVariable) negation.getFormula();
//                if (folAtom.getTerms().size() > 1) {
//                    System.err.println("Not sure how to handle a predicate with more than two arguments.");
//                }

                CCMPredicate predicate = predicateMap.get(folAtom.predicateId());
                CCMTerm term = termMap.get(folAtom.termId());

                if (predicate == null) {
                    System.out.println("Missing predicate");
                }

                if (term == null) {
                    System.out.println("Missing term");
                }

                addIndicatorConstraint(problem, predicate.getID() + "$" + term.getID(),
                                       constraintCounter);
                if (inheritedName != null) {
                    addNegationConstraint(problem, inheritedName,
                                          predicate.getID() + "$" + term.getID(),
                                          constraintCounter);
                }
            } else {
                System.err.println(
                    "Not sure how to handle any Negation other than Negation of IndicatorVariable.");
                System.exit(1);
            }
        }
    }

    public static Problem translateLogicToILP(
        List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective,
        List<FolFormula> constraints,
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
            recursiveTranslate(problem, folFormula, null, variableCounter, constraintCounter,
                               predicateMap, termMap);
        });

        return problem;
    }

    public static double solve(List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective,
                               List<FolFormula> constraints,
                               Map<String, ? extends CCMPredicate> predicateMap,
                               Map<String, ? extends CCMTerm> termMap, int timeout) {
        Problem problem = translateLogicToILP(objective, constraints, predicateMap, termMap);

        SolverFactory factory = new SolverFactoryGurobi();
        factory.setParameter(Solver.VERBOSE, 0);
        factory.setParameter(Solver.TIMEOUT, timeout);

        Solver solver = factory.get();
        final double startInferenceWalltime = System.currentTimeMillis();
        Result result = solver.solve(problem);
        final double endInferenceWalltime = System.currentTimeMillis();

        predicateMap.forEach((s, ccmPredicate) -> {
            ccmPredicate.setResult(result);
        });
        return endInferenceWalltime - startInferenceWalltime;
    }
}
