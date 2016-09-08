// Generated from grammar/CcmFol.g4 by ANTLR 4.5.3
package edu.illinois.cs.cogcomp.fol_ilp.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CcmFolParser}.
 */
public interface CcmFolListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(CcmFolParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(CcmFolParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CcmFolParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CcmFolParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#eval_statement}.
	 * @param ctx the parse tree
	 */
	void enterEval_statement(CcmFolParser.Eval_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#eval_statement}.
	 * @param ctx the parse tree
	 */
	void exitEval_statement(CcmFolParser.Eval_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#term_def}.
	 * @param ctx the parse tree
	 */
	void enterTerm_def(CcmFolParser.Term_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#term_def}.
	 * @param ctx the parse tree
	 */
	void exitTerm_def(CcmFolParser.Term_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#termSeq}.
	 * @param ctx the parse tree
	 */
	void enterTermSeq(CcmFolParser.TermSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#termSeq}.
	 * @param ctx the parse tree
	 */
	void exitTermSeq(CcmFolParser.TermSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#relation_dec}.
	 * @param ctx the parse tree
	 */
	void enterRelation_dec(CcmFolParser.Relation_decContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#relation_dec}.
	 * @param ctx the parse tree
	 */
	void exitRelation_dec(CcmFolParser.Relation_decContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#relation_def}.
	 * @param ctx the parse tree
	 */
	void enterRelation_def(CcmFolParser.Relation_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#relation_def}.
	 * @param ctx the parse tree
	 */
	void exitRelation_def(CcmFolParser.Relation_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#predicate_def}.
	 * @param ctx the parse tree
	 */
	void enterPredicate_def(CcmFolParser.Predicate_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#predicate_def}.
	 * @param ctx the parse tree
	 */
	void exitPredicate_def(CcmFolParser.Predicate_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#constraint_dec}.
	 * @param ctx the parse tree
	 */
	void enterConstraint_dec(CcmFolParser.Constraint_decContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#constraint_dec}.
	 * @param ctx the parse tree
	 */
	void exitConstraint_dec(CcmFolParser.Constraint_decContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#constraint_def}.
	 * @param ctx the parse tree
	 */
	void enterConstraint_def(CcmFolParser.Constraint_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#constraint_def}.
	 * @param ctx the parse tree
	 */
	void exitConstraint_def(CcmFolParser.Constraint_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#optimize_statement}.
	 * @param ctx the parse tree
	 */
	void enterOptimize_statement(CcmFolParser.Optimize_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#optimize_statement}.
	 * @param ctx the parse tree
	 */
	void exitOptimize_statement(CcmFolParser.Optimize_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#poly_obj}.
	 * @param ctx the parse tree
	 */
	void enterPoly_obj(CcmFolParser.Poly_objContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#poly_obj}.
	 * @param ctx the parse tree
	 */
	void exitPoly_obj(CcmFolParser.Poly_objContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#opt_op}.
	 * @param ctx the parse tree
	 */
	void enterOpt_op(CcmFolParser.Opt_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#opt_op}.
	 * @param ctx the parse tree
	 */
	void exitOpt_op(CcmFolParser.Opt_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(CcmFolParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(CcmFolParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#indicatorVar}.
	 * @param ctx the parse tree
	 */
	void enterIndicatorVar(CcmFolParser.IndicatorVarContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#indicatorVar}.
	 * @param ctx the parse tree
	 */
	void exitIndicatorVar(CcmFolParser.IndicatorVarContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#forall}.
	 * @param ctx the parse tree
	 */
	void enterForall(CcmFolParser.ForallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#forall}.
	 * @param ctx the parse tree
	 */
	void exitForall(CcmFolParser.ForallContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#exist}.
	 * @param ctx the parse tree
	 */
	void enterExist(CcmFolParser.ExistContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#exist}.
	 * @param ctx the parse tree
	 */
	void exitExist(CcmFolParser.ExistContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#atLeast}.
	 * @param ctx the parse tree
	 */
	void enterAtLeast(CcmFolParser.AtLeastContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#atLeast}.
	 * @param ctx the parse tree
	 */
	void exitAtLeast(CcmFolParser.AtLeastContext ctx);
	/**
	 * Enter a parse tree produced by {@link CcmFolParser#atMost}.
	 * @param ctx the parse tree
	 */
	void enterAtMost(CcmFolParser.AtMostContext ctx);
	/**
	 * Exit a parse tree produced by {@link CcmFolParser#atMost}.
	 * @param ctx the parse tree
	 */
	void exitAtMost(CcmFolParser.AtMostContext ctx);
}