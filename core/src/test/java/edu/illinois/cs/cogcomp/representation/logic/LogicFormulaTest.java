package edu.illinois.cs.cogcomp.representation.logic;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import edu.illinois.cs.cogcomp.representation.BooleanVariable;

/**
 * Created by haowu on 5/19/16.
 */
public class LogicFormulaTest {

    @Test
    public void evalNormForm() throws Exception {
        BooleanVariable t = new BooleanVariable("I","t");
        BooleanVariable f = new BooleanVariable("I","f");

        Map<BooleanVariable, Boolean> assignment = new HashMap<>();

        assignment.put(t,true);
        assignment.put(f,false);
    }

}