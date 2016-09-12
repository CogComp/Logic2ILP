package edu.illinois.cs.cogcomp.representation.logic;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import edu.illinois.cs.cogcomp.representation.IndicatorVariable;

/**
 * Created by haowu on 5/19/16.
 */
public class LogicFormulaTest {

    @Test
    public void evalNormForm() throws Exception {
        IndicatorVariable t = new IndicatorVariable("I","t");
        IndicatorVariable f = new IndicatorVariable("I","f");

        Map<IndicatorVariable, Boolean> assignment = new HashMap<>();

        assignment.put(t,true);
        assignment.put(f,false);
    }

}