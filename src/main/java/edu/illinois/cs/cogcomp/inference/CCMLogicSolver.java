package edu.illinois.cs.cogcomp.inference;


import net.sf.javailp.*;
import net.sf.tweety.logics.fol.syntax.*;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Created by binglin on 4/17/16.
 */
public class CCMLogicSolver {

    private static void addConstraint(Problem problem, Linear linear, String operator, Number rhs, Counter constraintCounter) {
        constraintCounter.increment();
        problem.add(new Constraint(constraintCounter.toString(), linear, operator, rhs));
    }

    private static void addIndicatorConstraint(Problem problem, String variable, Counter constraintCounter) {
        Linear linear = new Linear();
        linear.add(1, variable);
        addConstraint(problem, linear, ">=", 0, constraintCounter);
        addConstraint(problem, linear, "<=", 1, constraintCounter);
        problem.setVarType(variable, Integer.class);
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

    private static void recursiveTranslate(Problem problem, RelationalFormula formula,
                                           String inheritedName, Counter variableCounter, Counter constraintCounter,
                                           Map<String, ? extends CCMPredicate> predicateMap, Map<String, ? extends CCMTerm> termMap) {
        if (formula instanceof Conjunction) {
            Conjunction conjunction = (Conjunction) formula;

            if (inheritedName == null) {
                conjunction.getFormulas().forEach(c -> {
                    recursiveTranslate(problem, c, null, variableCounter, constraintCounter, predicateMap, termMap);
                });
            }
            else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(- conjunction.getFormulas().size(), inheritedName);
                l2.add(-1, inheritedName);

                conjunction.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", 0, constraintCounter);
                addConstraint(problem, l2, "<=", conjunction.getFormulas().size() - 1, constraintCounter);
            }
        }
        else if (formula instanceof Disjunction) {
            Disjunction disjunction = (Disjunction) formula;

            if (inheritedName == null) {
                Linear l1 = new Linear();

                disjunction.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", 1, constraintCounter);
            }
            else {
                Linear l1 = new Linear();
                Linear l2 = new Linear();
                l1.add(-1, inheritedName);
                l2.add(- disjunction.getFormulas().size(), inheritedName);

                disjunction.getFormulas().forEach(c -> {
                    variableCounter.increment();
                    addIndicatorConstraint(problem, variableCounter.toString(), constraintCounter);
                    l1.add(1, variableCounter.toString());
                    l2.add(1, variableCounter.toString());

                    recursiveTranslate(problem, c, variableCounter.toString(), variableCounter, constraintCounter, predicateMap, termMap);
                });

                addConstraint(problem, l1, ">=", 0, constraintCounter);
                addConstraint(problem, l2, "<=", 0, constraintCounter);
            }
        }
        else if (formula instanceof FOLAtom) {
            FOLAtom folAtom = (FOLAtom) formula;
            if (folAtom.getTerms().size() > 1) {
                System.err.println("Not sure how to handle a predicate with more than two arguments.");
            }

            CCMPredicate predicate = predicateMap.get(folAtom.getPredicate().getName());
            CCMTerm term = termMap.get(folAtom.getTerms().iterator().next().get());

            addIndicatorConstraint(problem, predicate.getID() + "$" + term.getID(), constraintCounter);
            if (inheritedName != null) {
                addEquivalenceConstraint(problem, inheritedName, predicate.getID() + "$" + term.getID(), constraintCounter);
            }
        }
        else if (formula instanceof Negation) {
            Negation negation = (Negation) formula;

            if (negation.getFormula() instanceof FOLAtom) {
                FOLAtom folAtom = (FOLAtom) negation.getFormula();
                if (folAtom.getTerms().size() > 1) {
                    System.err.println("Not sure how to handle a predicate with more than two arguments.");
                }

                CCMPredicate predicate = predicateMap.get(folAtom.getPredicate().getName());
                CCMTerm term = termMap.get(folAtom.getTerms().iterator().next().get());

                if (predicate == null){
                    System.out.println("Missing predicate");
                }

                if (term == null){
                    System.out.println("Missing term");
                }

                addIndicatorConstraint(problem, predicate.getID() + "$" + term.getID(), constraintCounter);
                if (inheritedName != null) {
                    addNegationConstraint(problem, inheritedName, predicate.getID() + "$" + term.getID(), constraintCounter);
                }
            }
            else {
                System.err.println("Not sure how to handle any Negation other than Negation of FOLAtom.");
                System.exit(1);
            }
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
            recursiveTranslate(problem, folFormula.toNnf(), null, variableCounter, constraintCounter, predicateMap, termMap);
        });

        return problem;
    }

    public static void solve(List<Pair<CCMPredicate, Collection<? extends CCMTerm>>> objective, List<FolFormula> constraints,
                             Map<String,? extends CCMPredicate> predicateMap, Map<String,? extends CCMTerm> termMap, int timeout) {
        Problem problem = translateLogicToILP(objective, constraints, predicateMap, termMap);

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
