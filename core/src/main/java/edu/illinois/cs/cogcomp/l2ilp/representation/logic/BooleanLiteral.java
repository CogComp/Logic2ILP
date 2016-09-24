package edu.illinois.cs.cogcomp.l2ilp.representation.logic;

/**
 * This class implements the behavior of two boolean literals (true and false) in logic formulas.
 */
public class BooleanLiteral implements LogicFormula {

    public static final BooleanLiteral TRUE = new BooleanLiteral(true);
    public static final BooleanLiteral FALSE = new BooleanLiteral(false);

    private final boolean value;

    private BooleanLiteral(boolean value){
        this.value = value;
    }

    @Override
    public LogicFormula negate() {
        if (this.value) {
            return FALSE;
        }
        else {
            return TRUE;
        }
    }

    @Override
    public LogicFormula toNnf() {
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooleanLiteral that = (BooleanLiteral) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }
}