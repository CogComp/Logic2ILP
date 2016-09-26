package edu.illinois.cs.cogcomp.l2ilp.api.constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.LogicFormula;

/**
 * Created by haowu on 5/14/16.
 */
public class ConstraintFunction {

    private Function<String, LogicFormula> p;

    public ConstraintFunction(
        Function<String, LogicFormula> p) {
        this.p = p;
    }

    public List<LogicFormula> of(String x) {
        List<LogicFormula> f = new ArrayList<>();
        f.add(this.p.apply(x));
        return f;
    }

    public List<LogicFormula> of(Collection<String> xs) {
        return xs.stream().map(p).collect(Collectors.toList());
    }
}
