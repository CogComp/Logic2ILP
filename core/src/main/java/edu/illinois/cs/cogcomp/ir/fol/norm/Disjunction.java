package edu.illinois.cs.cogcomp.ir.fol.norm;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.illinois.cs.cogcomp.ir.fol.FolFormula;

/**
 * Created by haowu on 5/19/16.
 */
public class Disjunction implements FolFormula {
    private List<FolFormula> formulas;

    public Disjunction(List<FolFormula> formulas) {
        this.formulas = formulas;
    }

    public Disjunction(FolFormula... formulas) {
        this.formulas = new ArrayList<>();
        for (FolFormula f : formulas){
            this.formulas.add(f);
        }
    }
    public List<FolFormula> getFormulas() {
        return this.formulas;
    }

    @Override
    public String toString() {
        return ("(" + StringUtils
            .join(this.formulas.stream().map(Object::toString).collect(Collectors.toList()), " | ")
                + ")");
    }
    @Override
    public FolFormula toNnf() {
        List<FolFormula> formulas = new ArrayList<>(this.formulas.size());
        this.formulas.forEach(folFormula -> {
            formulas.add(folFormula.toNnf());
        });

        return new Disjunction(formulas);
    }

}
