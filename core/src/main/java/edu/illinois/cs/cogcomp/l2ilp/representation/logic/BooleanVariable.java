package edu.illinois.cs.cogcomp.l2ilp.representation.logic;

/**
 * This class implements the behavior of a boolean variable in logic formulas.
 * BooleanVariables are identified by their unique ids.
 */
public class BooleanVariable implements LogicFormula {

    private final String id;

    /**
     * @param id the id of the BooleanVariable
     */
    public BooleanVariable(String id) {
        if (id == null || "".equals(id)) {
            throw new RuntimeException("BooleanVariable id has to be non-null and non-empty");
        }

        this.id = id;
    }

//    @Override
//    public LogicFormula negate() {
//        return new Negation(this);
//    }
//
//    @Override
//    public LogicFormula simplify() {
//        return this;
//    }

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

    public String getId() {
        return id;
    }
}