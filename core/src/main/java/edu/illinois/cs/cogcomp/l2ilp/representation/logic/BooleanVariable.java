package edu.illinois.cs.cogcomp.l2ilp.representation.logic;

import edu.illinois.cs.cogcomp.l2ilp.representation.logic.basic.Negation;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the behavior of a boolean variable in logic formulas.
 * BooleanVariables are identified by their unique ids.
 */
public class BooleanVariable implements LogicFormula {

    private static Map<String, BooleanVariable> booleanVariables;

    public static BooleanVariable getBooleanVariable(String id) {
        if (booleanVariables == null) {
            booleanVariables = new HashMap<>();
        }

        BooleanVariable booleanVariable = booleanVariables.get(id);
        if (booleanVariable == null) {
            booleanVariable = new BooleanVariable(id);
            booleanVariables.put(id, booleanVariable);
        }

        return booleanVariable;
    }

    private final String id;

    private BooleanVariable(String id) {
        if (id == null || "".equals(id)) {
            throw new RuntimeException("BooleanVariable id has to be non-null and non-empty");
        }

        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public LogicFormula negate() {
        return new Negation(this);
    }

    @Override
    public LogicFormula toNnf() {
        return this;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooleanVariable that = (BooleanVariable) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}