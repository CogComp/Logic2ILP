package edu.illinois.cs.cogcomp.fol_ilp;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.Scanner;

import edu.illinois.cs.cogcomp.fol_ilp.grammar.CcmFolLexer;
import edu.illinois.cs.cogcomp.fol_ilp.grammar.CcmFolParser;
import edu.illinois.cs.cogcomp.fol_ilp.visitor.CCMListener;

/**
 * Created by haowu on 9/8/16.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        while (true) {

            CcmFolLexer lexer = new CcmFolLexer(new ANTLRFileStream("core-lang/term_defs.ccm"));
            CommonTokenStream tokens = new CommonTokenStream(lexer );
            CcmFolParser parser = new CcmFolParser(tokens );
            ParseTree pt = parser.program();
            ParseTreeWalker.DEFAULT.walk(new CCMListener(), pt);


        }

    }
}
