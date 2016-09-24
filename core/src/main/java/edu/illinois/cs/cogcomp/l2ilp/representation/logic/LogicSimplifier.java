package edu.illinois.cs.cogcomp.l2ilp.representation.logic;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Conjunction;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Disjunction;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Negation;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.AtLeastK;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.AtMostK;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.ExactK;
import edu.illinois.cs.cogcomp.l2ilp.representation.logic.extension.NotExactK;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class LogicSimplifier {

    public enum Rule {
        CONJUNCTION, DISJUNCTION, NEGATION, AT_LEAST_K, AT_MOST_K, EXACT_K, NOT_EXACT_K
    }

    private boolean simplifyConjunction = false;
    private boolean simplifyDisjunction = false;
    private boolean simplifyNegation = false;
    private boolean simplifyAtLeastK = false;
    private boolean simplifyAtMostK = false;
    private boolean simplifyExactK = false;
    private boolean simplifyNotExactK = false;

    public LogicSimplifier withRule(Rule... rules) {
        for (Rule rule : rules) {
            switch (rule) {
                case CONJUNCTION:
                    simplifyConjunction = true;
                    break;
                case DISJUNCTION:
                    simplifyDisjunction = true;
                    break;
                case NEGATION:
                    simplifyNegation = true;
                    break;
                case AT_LEAST_K:
                    simplifyAtLeastK = true;
                    break;
                case AT_MOST_K:
                    simplifyAtMostK = true;
                    break;
                case EXACT_K:
                    simplifyExactK = true;
                    break;
                case NOT_EXACT_K:
                    simplifyNotExactK = true;
                    break;
                default:
            }
        }

        return this;
    }

    public LogicSimplifier withAllRules() {
        simplifyConjunction = true;
        simplifyDisjunction = true;
        simplifyNegation = true;
        simplifyAtLeastK = true;
        simplifyAtMostK = true;
        simplifyExactK = true;
        simplifyNotExactK = true;

        return this;
    }

    public LogicFormula simplifyConjunction(Conjunction conjunction) {
        List<LogicFormula> simplifiedFormulas = conjunction.getFormulas().stream()
                .map(this::simplify)
                .collect(Collectors.toList());

        List<LogicFormula> flattenedFormulas = new ArrayList<>();
        for (LogicFormula simplifiedFormula: simplifiedFormulas) {
            if (simplifiedFormula == BooleanLiteral.TRUE) {
                // do nothing
            }
            else if (simplifiedFormula == BooleanLiteral.FALSE) {
                return BooleanLiteral.FALSE;
            }
            else if (simplifiedFormula instanceof Conjunction) {
                flattenedFormulas.addAll(((Conjunction) simplifiedFormula).getFormulas());
            }
            else {
                flattenedFormulas.add(simplifiedFormula);
            }
        }

        if (flattenedFormulas.size() == 0) {
            return BooleanLiteral.TRUE;
        }

        return new Conjunction(flattenedFormulas);
    }

    public LogicFormula simplifyDisjunction(Disjunction disjunction) {
        List<LogicFormula> simplifiedFormulas = disjunction.getFormulas().stream()
                .map(this::simplify)
                .collect(Collectors.toList());

        List<LogicFormula> flattenedFormulas = new ArrayList<>();
        for (LogicFormula simplifiedFormula: simplifiedFormulas) {
            if (simplifiedFormula == BooleanLiteral.TRUE) {
                return BooleanLiteral.TRUE;
            }
            else if (simplifiedFormula == BooleanLiteral.FALSE) {
                // do nothing
            }
            else if (simplifiedFormula instanceof Disjunction) {
                flattenedFormulas.addAll(((Disjunction) simplifiedFormula).getFormulas());
            }
            else {
                flattenedFormulas.add(simplifiedFormula);
            }
        }

        if (flattenedFormulas.size() == 0) {
            return BooleanLiteral.FALSE;
        }

        return new Disjunction(flattenedFormulas);
    }

    public LogicFormula simplifyNegation(Negation negation) {
        LogicFormula simplifiedFormula = simplify(negation.getFormula());

        if (simplifiedFormula == BooleanLiteral.TRUE) {
            return BooleanLiteral.FALSE;
        }
        else if (simplifiedFormula == BooleanLiteral.FALSE) {
            return BooleanLiteral.TRUE;
        }
        else if (simplifiedFormula instanceof Negation) {
            return ((Negation) simplifiedFormula).getFormula();
        }
        else {
            return new Negation(simplifiedFormula);
        }
    }

    private Pair<Integer, List<LogicFormula>> pruneChildren(int k, List<LogicFormula> formulas) {
        int prunedK = k;
        List<LogicFormula> prunedFormulas = new ArrayList<>();

        for (LogicFormula logicFormula: formulas) {
            if (logicFormula == BooleanLiteral.TRUE) {
                prunedK --;
            }
            else if (logicFormula == BooleanLiteral.FALSE) {
                // do nothing
            }
            else {
                prunedFormulas.add(logicFormula);
            }
        }

        return new ImmutablePair<>(prunedK, prunedFormulas);
    }

    public LogicFormula simplifyAtLeastK(AtLeastK atLeastK) {
        List<LogicFormula> simplifiedFormulas = atLeastK.getFormulas().stream()
                .map(this::simplify)
                .collect(Collectors.toList());

        Pair<Integer, List<LogicFormula>> pruneResult = pruneChildren(atLeastK.getK(), simplifiedFormulas);
        int prunedK = pruneResult.getLeft();
        List<LogicFormula> prunedFormulas = pruneResult.getRight();

        if (prunedK <= 0) {
            return BooleanLiteral.TRUE;
        }

        if (prunedK > prunedFormulas.size()) {
            return BooleanLiteral.FALSE;
        }

        return new AtLeastK(prunedK, prunedFormulas);
    }

    public LogicFormula simplifyAtMostK(AtMostK atMostK) {
        List<LogicFormula> simplifiedFormulas = atMostK.getFormulas().stream()
                .map(this::simplify)
                .collect(Collectors.toList());

        Pair<Integer, List<LogicFormula>> pruneResult = pruneChildren(atMostK.getK(), simplifiedFormulas);
        int prunedK = pruneResult.getLeft();
        List<LogicFormula> prunedFormulas = pruneResult.getRight();

        if (prunedK < 0) {
            return BooleanLiteral.FALSE;
        }

        if (prunedK >= prunedFormulas.size()) {
            return BooleanLiteral.TRUE;
        }

        return new AtMostK(prunedK, prunedFormulas);
    }

    public LogicFormula simplifyExactK(ExactK exactK) {
        List<LogicFormula> simplifiedFormulas = exactK.getFormulas().stream()
                .map(this::simplify)
                .collect(Collectors.toList());

        Pair<Integer, List<LogicFormula>> pruneResult = pruneChildren(exactK.getK(), simplifiedFormulas);
        int prunedK = pruneResult.getLeft();
        List<LogicFormula> prunedFormulas = pruneResult.getRight();

        if (prunedK < 0) {
            return BooleanLiteral.FALSE;
        }

        if (prunedK > prunedFormulas.size()) {
            return BooleanLiteral.FALSE;
        }

        if (prunedK == 0 && prunedFormulas.size() == 0) {
            return BooleanLiteral.TRUE;
        }

        return new ExactK(prunedK, prunedFormulas);
    }

    public LogicFormula simplifyNotExactK(NotExactK notExactK) {
        List<LogicFormula> simplifiedFormulas = notExactK.getFormulas().stream()
                .map(this::simplify)
                .collect(Collectors.toList());

        Pair<Integer, List<LogicFormula>> pruneResult = pruneChildren(notExactK.getK(), simplifiedFormulas);
        int prunedK = pruneResult.getLeft();
        List<LogicFormula> prunedFormulas = pruneResult.getRight();

        if (prunedK < 0) {
            return BooleanLiteral.TRUE;
        }

        if (prunedK > prunedFormulas.size()) {
            return BooleanLiteral.TRUE;
        }

        if (prunedK == 0 && prunedFormulas.size() == 0) {
            return BooleanLiteral.FALSE;
        }

        return new NotExactK(prunedK, prunedFormulas);
    }

    public LogicFormula simplify(LogicFormula logicFormula) {
        if (logicFormula instanceof BooleanLiteral) {
            return logicFormula;
        }
        else if (logicFormula instanceof BooleanVariable) {
            return logicFormula;
        }
        else if (logicFormula instanceof Conjunction) {
            if (simplifyConjunction) {
                return simplifyConjunction((Conjunction) logicFormula);
            }
        }
        else if (logicFormula instanceof Disjunction) {
            if (simplifyDisjunction) {
                return simplifyDisjunction((Disjunction) logicFormula);
            }
        }
        else if (logicFormula instanceof Negation) {
            if (simplifyNegation) {
                return simplifyNegation((Negation) logicFormula);
            }
        }
        else if (logicFormula instanceof AtLeastK) {
            if (simplifyAtLeastK) {
                return simplifyAtLeastK((AtLeastK) logicFormula);
            }
        }
        else if (logicFormula instanceof AtMostK) {
            if (simplifyAtMostK) {
                return simplifyAtMostK((AtMostK) logicFormula);
            }
        }
        else if (logicFormula instanceof ExactK) {
            if (simplifyExactK) {
                return simplifyExactK((ExactK) logicFormula);
            }
        }
        else if (logicFormula instanceof NotExactK) {
            if (simplifyNotExactK) {
                return simplifyNotExactK((NotExactK) logicFormula);
            }
        }
        else {
            throw new RuntimeException("LogicFormula implementation " + logicFormula.getClass() + " is not supported yet.");
        }

        return logicFormula;
    }
}
