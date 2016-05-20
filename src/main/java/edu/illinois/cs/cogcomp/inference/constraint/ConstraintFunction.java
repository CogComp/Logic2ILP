package edu.illinois.cs.cogcomp.inference.constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/14/16.
 */
public class ConstraintFunction<X> implements ConstraintProvider<X> {

    private Function<X, FolFormula> p;

    public ConstraintFunction(
        Function<X, FolFormula> p) {
        this.p = p;
    }


    @Override
    public List<FolFormula> of(X x) {
        List<FolFormula> f = new ArrayList<>();
        f.add(this.p.apply(x));
        return f;
    }

    @Override
    public List<FolFormula> of(Collection<X> xs) {
        return xs.stream().map(p).collect(Collectors.toList());
    }
}
