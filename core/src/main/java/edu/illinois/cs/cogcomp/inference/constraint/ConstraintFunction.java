package edu.illinois.cs.cogcomp.inference.constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.representation.logic.LogicFormula;

/**
 * Created by haowu on 5/14/16.
 */
public class ConstraintFunction<X> implements ConstraintProvider<X> {

    private Function<X, LogicFormula> p;

    public ConstraintFunction(
        Function<X, LogicFormula> p) {
        this.p = p;
    }


    @Override
    public List<LogicFormula> of(X x) {
        List<LogicFormula> f = new ArrayList<>();
        f.add(this.p.apply(x));
        return f;
    }

    @Override
    public List<LogicFormula> of(Collection<X> xs) {
        return xs.stream().map(p).collect(Collectors.toList());
    }
}
