// Generated from grammar/CcmFol.g4 by ANTLR 4.5.3
package edu.illinois.cs.cogcomp.fol_ilp.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CcmFolParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, TERM=15, RELATION=16, 
		CONSTRAINT=17, SOFT_CONSTRAINT=18, PREDICATE=19, MUTEX_PREDICATE=20, MIN=21, 
		MAX=22, SUBJECT_TO=23, IN=24, AND=25, OR=26, NOT=27, FORALL=28, EXIST=29, 
		ATLEAST=30, ATMOST=31, Identifier=32, STRING=33, INT=34, WS=35;
	public static final int
		RULE_program = 0, RULE_statement = 1, RULE_eval_statement = 2, RULE_term_def = 3, 
		RULE_termSeq = 4, RULE_relation_dec = 5, RULE_relation_def = 6, RULE_predicate_def = 7, 
		RULE_constraint_dec = 8, RULE_constraint_def = 9, RULE_optimize_statement = 10, 
		RULE_poly_obj = 11, RULE_opt_op = 12, RULE_number = 13, RULE_indicatorVar = 14, 
		RULE_forall = 15, RULE_exist = 16, RULE_atLeast = 17, RULE_atMost = 18;
	public static final String[] ruleNames = {
		"program", "statement", "eval_statement", "term_def", "termSeq", "relation_dec", 
		"relation_def", "predicate_def", "constraint_dec", "constraint_def", "optimize_statement", 
		"poly_obj", "opt_op", "number", "indicatorVar", "forall", "exist", "atLeast", 
		"atMost"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'?'", "','", "'['", "'-'", "']'", "'='", "'{'", "'}'", "'->'", 
		"'+'", "'.'", "'('", "')'", "'term'", "'relation'", "'constraint'", "'soft_constraint'", 
		"'predicate'", "'mutex_predicate'", "'min'", "'max'", "'subjectTo'", "'in'", 
		"'and'", "'or'", "'not'", "'forall'", "'exist'", "'atLeast'", "'atMost'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "TERM", "RELATION", "CONSTRAINT", "SOFT_CONSTRAINT", 
		"PREDICATE", "MUTEX_PREDICATE", "MIN", "MAX", "SUBJECT_TO", "IN", "AND", 
		"OR", "NOT", "FORALL", "EXIST", "ATLEAST", "ATMOST", "Identifier", "STRING", 
		"INT", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CcmFol.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CcmFolParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(38);
				statement();
				}
				}
				setState(41); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << TERM) | (1L << RELATION) | (1L << CONSTRAINT) | (1L << SOFT_CONSTRAINT) | (1L << PREDICATE) | (1L << MUTEX_PREDICATE) | (1L << MIN) | (1L << MAX) | (1L << Identifier))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public Term_defContext term_def() {
			return getRuleContext(Term_defContext.class,0);
		}
		public Relation_decContext relation_dec() {
			return getRuleContext(Relation_decContext.class,0);
		}
		public Relation_defContext relation_def() {
			return getRuleContext(Relation_defContext.class,0);
		}
		public Predicate_defContext predicate_def() {
			return getRuleContext(Predicate_defContext.class,0);
		}
		public Constraint_decContext constraint_dec() {
			return getRuleContext(Constraint_decContext.class,0);
		}
		public Optimize_statementContext optimize_statement() {
			return getRuleContext(Optimize_statementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(62);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				match(T__0);
				}
				break;
			case TERM:
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				term_def();
				setState(45);
				match(T__0);
				}
				break;
			case RELATION:
				enterOuterAlt(_localctx, 3);
				{
				setState(47);
				relation_dec();
				setState(48);
				match(T__0);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 4);
				{
				setState(50);
				relation_def();
				setState(51);
				match(T__0);
				}
				break;
			case PREDICATE:
			case MUTEX_PREDICATE:
				enterOuterAlt(_localctx, 5);
				{
				setState(53);
				predicate_def();
				setState(54);
				match(T__0);
				}
				break;
			case CONSTRAINT:
			case SOFT_CONSTRAINT:
				enterOuterAlt(_localctx, 6);
				{
				setState(56);
				constraint_dec();
				setState(57);
				match(T__0);
				}
				break;
			case MIN:
			case MAX:
				enterOuterAlt(_localctx, 7);
				{
				setState(59);
				optimize_statement();
				setState(60);
				match(T__0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Eval_statementContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(CcmFolParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(CcmFolParser.Identifier, i);
		}
		public Eval_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eval_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterEval_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitEval_statement(this);
		}
	}

	public final Eval_statementContext eval_statement() throws RecognitionException {
		Eval_statementContext _localctx = new Eval_statementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_eval_statement);
		try {
			setState(68);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				match(Identifier);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				match(Identifier);
				setState(66);
				match(Identifier);
				setState(67);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Term_defContext extends ParserRuleContext {
		public TerminalNode TERM() { return getToken(CcmFolParser.TERM, 0); }
		public TermSeqContext termSeq() {
			return getRuleContext(TermSeqContext.class,0);
		}
		public Term_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterTerm_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitTerm_def(this);
		}
	}

	public final Term_defContext term_def() throws RecognitionException {
		Term_defContext _localctx = new Term_defContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_term_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(TERM);
			setState(71);
			termSeq();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermSeqContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(CcmFolParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(CcmFolParser.Identifier, i);
		}
		public List<TerminalNode> INT() { return getTokens(CcmFolParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(CcmFolParser.INT, i);
		}
		public TermSeqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_termSeq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterTermSeq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitTermSeq(this);
		}
	}

	public final TermSeqContext termSeq() throws RecognitionException {
		TermSeqContext _localctx = new TermSeqContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_termSeq);
		int _la;
		try {
			setState(87);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(73);
				match(Identifier);
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(74);
					match(T__2);
					setState(75);
					match(Identifier);
					}
					}
					setState(80);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				match(Identifier);
				setState(82);
				match(T__3);
				setState(83);
				match(INT);
				setState(84);
				match(T__4);
				setState(85);
				match(INT);
				setState(86);
				match(T__5);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Relation_decContext extends ParserRuleContext {
		public TerminalNode RELATION() { return getToken(CcmFolParser.RELATION, 0); }
		public TerminalNode Identifier() { return getToken(CcmFolParser.Identifier, 0); }
		public Relation_decContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relation_dec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterRelation_dec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitRelation_dec(this);
		}
	}

	public final Relation_decContext relation_dec() throws RecognitionException {
		Relation_decContext _localctx = new Relation_decContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_relation_dec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(RELATION);
			setState(90);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Relation_defContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(CcmFolParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(CcmFolParser.Identifier, i);
		}
		public Relation_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relation_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterRelation_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitRelation_def(this);
		}
	}

	public final Relation_defContext relation_def() throws RecognitionException {
		Relation_defContext _localctx = new Relation_defContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_relation_def);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(Identifier);
			setState(93);
			match(Identifier);
			setState(94);
			match(T__6);
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Identifier) {
				{
				{
				setState(95);
				match(Identifier);
				}
				}
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Predicate_defContext extends ParserRuleContext {
		public TerminalNode PREDICATE() { return getToken(CcmFolParser.PREDICATE, 0); }
		public List<TerminalNode> Identifier() { return getTokens(CcmFolParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(CcmFolParser.Identifier, i);
		}
		public TerminalNode MUTEX_PREDICATE() { return getToken(CcmFolParser.MUTEX_PREDICATE, 0); }
		public Predicate_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterPredicate_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitPredicate_def(this);
		}
	}

	public final Predicate_defContext predicate_def() throws RecognitionException {
		Predicate_defContext _localctx = new Predicate_defContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_predicate_def);
		int _la;
		try {
			setState(111);
			switch (_input.LA(1)) {
			case PREDICATE:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				match(PREDICATE);
				setState(102);
				match(Identifier);
				}
				break;
			case MUTEX_PREDICATE:
				enterOuterAlt(_localctx, 2);
				{
				setState(103);
				match(MUTEX_PREDICATE);
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(104);
					match(T__2);
					setState(105);
					match(Identifier);
					}
					}
					setState(110);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Constraint_decContext extends ParserRuleContext {
		public TerminalNode CONSTRAINT() { return getToken(CcmFolParser.CONSTRAINT, 0); }
		public TerminalNode Identifier() { return getToken(CcmFolParser.Identifier, 0); }
		public Constraint_defContext constraint_def() {
			return getRuleContext(Constraint_defContext.class,0);
		}
		public TerminalNode SOFT_CONSTRAINT() { return getToken(CcmFolParser.SOFT_CONSTRAINT, 0); }
		public Constraint_decContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint_dec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterConstraint_dec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitConstraint_dec(this);
		}
	}

	public final Constraint_decContext constraint_dec() throws RecognitionException {
		Constraint_decContext _localctx = new Constraint_decContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_constraint_dec);
		try {
			setState(121);
			switch (_input.LA(1)) {
			case CONSTRAINT:
				enterOuterAlt(_localctx, 1);
				{
				setState(113);
				match(CONSTRAINT);
				setState(114);
				match(Identifier);
				setState(115);
				match(T__6);
				setState(116);
				constraint_def(0);
				}
				break;
			case SOFT_CONSTRAINT:
				enterOuterAlt(_localctx, 2);
				{
				setState(117);
				match(SOFT_CONSTRAINT);
				setState(118);
				match(Identifier);
				setState(119);
				match(T__6);
				setState(120);
				constraint_def(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Constraint_defContext extends ParserRuleContext {
		public List<Constraint_defContext> constraint_def() {
			return getRuleContexts(Constraint_defContext.class);
		}
		public Constraint_defContext constraint_def(int i) {
			return getRuleContext(Constraint_defContext.class,i);
		}
		public IndicatorVarContext indicatorVar() {
			return getRuleContext(IndicatorVarContext.class,0);
		}
		public TerminalNode NOT() { return getToken(CcmFolParser.NOT, 0); }
		public AtMostContext atMost() {
			return getRuleContext(AtMostContext.class,0);
		}
		public AtLeastContext atLeast() {
			return getRuleContext(AtLeastContext.class,0);
		}
		public ExistContext exist() {
			return getRuleContext(ExistContext.class,0);
		}
		public ForallContext forall() {
			return getRuleContext(ForallContext.class,0);
		}
		public List<TerminalNode> OR() { return getTokens(CcmFolParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(CcmFolParser.OR, i);
		}
		public List<TerminalNode> AND() { return getTokens(CcmFolParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(CcmFolParser.AND, i);
		}
		public Constraint_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterConstraint_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitConstraint_def(this);
		}
	}

	public final Constraint_defContext constraint_def() throws RecognitionException {
		return constraint_def(0);
	}

	private Constraint_defContext constraint_def(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Constraint_defContext _localctx = new Constraint_defContext(_ctx, _parentState);
		Constraint_defContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_constraint_def, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			switch (_input.LA(1)) {
			case T__7:
				{
				setState(124);
				match(T__7);
				setState(125);
				constraint_def(0);
				setState(126);
				match(T__8);
				}
				break;
			case Identifier:
				{
				setState(128);
				indicatorVar();
				}
				break;
			case NOT:
				{
				setState(129);
				match(NOT);
				setState(130);
				constraint_def(5);
				}
				break;
			case ATMOST:
				{
				setState(131);
				atMost();
				setState(132);
				match(T__9);
				setState(133);
				constraint_def(4);
				}
				break;
			case ATLEAST:
				{
				setState(135);
				atLeast();
				setState(136);
				match(T__9);
				setState(137);
				constraint_def(3);
				}
				break;
			case EXIST:
				{
				setState(139);
				exist();
				setState(140);
				match(T__9);
				setState(141);
				constraint_def(2);
				}
				break;
			case FORALL:
				{
				setState(143);
				forall();
				setState(144);
				match(T__9);
				setState(145);
				constraint_def(1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(165);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(163);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new Constraint_defContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_constraint_def);
						setState(149);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(152); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(150);
								match(OR);
								setState(151);
								constraint_def(0);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(154); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					case 2:
						{
						_localctx = new Constraint_defContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_constraint_def);
						setState(156);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(159); 
						_errHandler.sync(this);
						_alt = 1;
						do {
							switch (_alt) {
							case 1:
								{
								{
								setState(157);
								match(AND);
								setState(158);
								constraint_def(0);
								}
								}
								break;
							default:
								throw new NoViableAltException(this);
							}
							setState(161); 
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
						} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
						}
						break;
					}
					} 
				}
				setState(167);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Optimize_statementContext extends ParserRuleContext {
		public Opt_opContext opt_op() {
			return getRuleContext(Opt_opContext.class,0);
		}
		public Poly_objContext poly_obj() {
			return getRuleContext(Poly_objContext.class,0);
		}
		public TerminalNode SUBJECT_TO() { return getToken(CcmFolParser.SUBJECT_TO, 0); }
		public List<TerminalNode> Identifier() { return getTokens(CcmFolParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(CcmFolParser.Identifier, i);
		}
		public Optimize_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optimize_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterOptimize_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitOptimize_statement(this);
		}
	}

	public final Optimize_statementContext optimize_statement() throws RecognitionException {
		Optimize_statementContext _localctx = new Optimize_statementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_optimize_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			opt_op();
			setState(169);
			poly_obj();
			setState(170);
			match(SUBJECT_TO);
			setState(174);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Identifier) {
				{
				{
				setState(171);
				match(Identifier);
				}
				}
				setState(176);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Poly_objContext extends ParserRuleContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public IndicatorVarContext indicatorVar() {
			return getRuleContext(IndicatorVarContext.class,0);
		}
		public Poly_objContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_poly_obj; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterPoly_obj(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitPoly_obj(this);
		}
	}

	public final Poly_objContext poly_obj() throws RecognitionException {
		Poly_objContext _localctx = new Poly_objContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_poly_obj);
		try {
			setState(188);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(177);
				number();
				setState(178);
				indicatorVar();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				setState(180);
				match(T__4);
				setState(181);
				number();
				setState(182);
				indicatorVar();
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 3);
				{
				setState(184);
				match(T__10);
				setState(185);
				number();
				setState(186);
				indicatorVar();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Opt_opContext extends ParserRuleContext {
		public TerminalNode MIN() { return getToken(CcmFolParser.MIN, 0); }
		public TerminalNode MAX() { return getToken(CcmFolParser.MAX, 0); }
		public Opt_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_opt_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterOpt_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitOpt_op(this);
		}
	}

	public final Opt_opContext opt_op() throws RecognitionException {
		Opt_opContext _localctx = new Opt_opContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_opt_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			_la = _input.LA(1);
			if ( !(_la==MIN || _la==MAX) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public List<TerminalNode> INT() { return getTokens(CcmFolParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(CcmFolParser.INT, i);
		}
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_number);
		try {
			setState(196);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(192);
				match(INT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(193);
				match(INT);
				setState(194);
				match(T__11);
				setState(195);
				match(INT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndicatorVarContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(CcmFolParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(CcmFolParser.Identifier, i);
		}
		public IndicatorVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indicatorVar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterIndicatorVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitIndicatorVar(this);
		}
	}

	public final IndicatorVarContext indicatorVar() throws RecognitionException {
		IndicatorVarContext _localctx = new IndicatorVarContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_indicatorVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(Identifier);
			setState(199);
			match(T__12);
			setState(200);
			match(Identifier);
			setState(201);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForallContext extends ParserRuleContext {
		public TerminalNode FORALL() { return getToken(CcmFolParser.FORALL, 0); }
		public TerminalNode Identifier() { return getToken(CcmFolParser.Identifier, 0); }
		public TerminalNode IN() { return getToken(CcmFolParser.IN, 0); }
		public TermSeqContext termSeq() {
			return getRuleContext(TermSeqContext.class,0);
		}
		public ForallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterForall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitForall(this);
		}
	}

	public final ForallContext forall() throws RecognitionException {
		ForallContext _localctx = new ForallContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_forall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(FORALL);
			setState(204);
			match(Identifier);
			setState(205);
			match(IN);
			setState(206);
			termSeq();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExistContext extends ParserRuleContext {
		public TerminalNode EXIST() { return getToken(CcmFolParser.EXIST, 0); }
		public TerminalNode Identifier() { return getToken(CcmFolParser.Identifier, 0); }
		public TerminalNode IN() { return getToken(CcmFolParser.IN, 0); }
		public TermSeqContext termSeq() {
			return getRuleContext(TermSeqContext.class,0);
		}
		public ExistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exist; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterExist(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitExist(this);
		}
	}

	public final ExistContext exist() throws RecognitionException {
		ExistContext _localctx = new ExistContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_exist);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			match(EXIST);
			setState(209);
			match(Identifier);
			setState(210);
			match(IN);
			setState(211);
			termSeq();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtLeastContext extends ParserRuleContext {
		public TerminalNode ATLEAST() { return getToken(CcmFolParser.ATLEAST, 0); }
		public TerminalNode INT() { return getToken(CcmFolParser.INT, 0); }
		public TerminalNode Identifier() { return getToken(CcmFolParser.Identifier, 0); }
		public TerminalNode IN() { return getToken(CcmFolParser.IN, 0); }
		public TermSeqContext termSeq() {
			return getRuleContext(TermSeqContext.class,0);
		}
		public AtLeastContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atLeast; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterAtLeast(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitAtLeast(this);
		}
	}

	public final AtLeastContext atLeast() throws RecognitionException {
		AtLeastContext _localctx = new AtLeastContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_atLeast);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(ATLEAST);
			setState(214);
			match(INT);
			setState(215);
			match(Identifier);
			setState(216);
			match(IN);
			setState(217);
			termSeq();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtMostContext extends ParserRuleContext {
		public TerminalNode ATMOST() { return getToken(CcmFolParser.ATMOST, 0); }
		public TerminalNode INT() { return getToken(CcmFolParser.INT, 0); }
		public TerminalNode Identifier() { return getToken(CcmFolParser.Identifier, 0); }
		public TerminalNode IN() { return getToken(CcmFolParser.IN, 0); }
		public TermSeqContext termSeq() {
			return getRuleContext(TermSeqContext.class,0);
		}
		public AtMostContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atMost; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).enterAtMost(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CcmFolListener ) ((CcmFolListener)listener).exitAtMost(this);
		}
	}

	public final AtMostContext atMost() throws RecognitionException {
		AtMostContext _localctx = new AtMostContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_atMost);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			match(ATMOST);
			setState(220);
			match(INT);
			setState(221);
			match(Identifier);
			setState(222);
			match(IN);
			setState(223);
			termSeq();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9:
			return constraint_def_sempred((Constraint_defContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean constraint_def_sempred(Constraint_defContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3%\u00e4\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\6\2*\n\2\r\2\16\2+\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3A\n\3\3\4\3\4\3"+
		"\4\3\4\5\4G\n\4\3\5\3\5\3\5\3\6\3\6\3\6\7\6O\n\6\f\6\16\6R\13\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\5\6Z\n\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\7\bc\n\b\f\b\16"+
		"\bf\13\b\3\t\3\t\3\t\3\t\3\t\7\tm\n\t\f\t\16\tp\13\t\5\tr\n\t\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\5\n|\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\5\13\u0096\n\13\3\13\3\13\3\13\6\13\u009b\n\13\r\13\16\13"+
		"\u009c\3\13\3\13\3\13\6\13\u00a2\n\13\r\13\16\13\u00a3\7\13\u00a6\n\13"+
		"\f\13\16\13\u00a9\13\13\3\f\3\f\3\f\3\f\7\f\u00af\n\f\f\f\16\f\u00b2\13"+
		"\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00bf\n\r\3\16\3\16"+
		"\3\17\3\17\3\17\3\17\5\17\u00c7\n\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\2\3\24\25\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \"$&\2\3\3\2\27\30\u00ec\2)\3\2\2\2\4@\3\2\2\2\6F\3\2"+
		"\2\2\bH\3\2\2\2\nY\3\2\2\2\f[\3\2\2\2\16^\3\2\2\2\20q\3\2\2\2\22{\3\2"+
		"\2\2\24\u0095\3\2\2\2\26\u00aa\3\2\2\2\30\u00be\3\2\2\2\32\u00c0\3\2\2"+
		"\2\34\u00c6\3\2\2\2\36\u00c8\3\2\2\2 \u00cd\3\2\2\2\"\u00d2\3\2\2\2$\u00d7"+
		"\3\2\2\2&\u00dd\3\2\2\2(*\5\4\3\2)(\3\2\2\2*+\3\2\2\2+)\3\2\2\2+,\3\2"+
		"\2\2,\3\3\2\2\2-A\7\3\2\2./\5\b\5\2/\60\7\3\2\2\60A\3\2\2\2\61\62\5\f"+
		"\7\2\62\63\7\3\2\2\63A\3\2\2\2\64\65\5\16\b\2\65\66\7\3\2\2\66A\3\2\2"+
		"\2\678\5\20\t\289\7\3\2\29A\3\2\2\2:;\5\22\n\2;<\7\3\2\2<A\3\2\2\2=>\5"+
		"\26\f\2>?\7\3\2\2?A\3\2\2\2@-\3\2\2\2@.\3\2\2\2@\61\3\2\2\2@\64\3\2\2"+
		"\2@\67\3\2\2\2@:\3\2\2\2@=\3\2\2\2A\5\3\2\2\2BG\7\"\2\2CD\7\"\2\2DE\7"+
		"\"\2\2EG\7\4\2\2FB\3\2\2\2FC\3\2\2\2G\7\3\2\2\2HI\7\21\2\2IJ\5\n\6\2J"+
		"\t\3\2\2\2KP\7\"\2\2LM\7\5\2\2MO\7\"\2\2NL\3\2\2\2OR\3\2\2\2PN\3\2\2\2"+
		"PQ\3\2\2\2QZ\3\2\2\2RP\3\2\2\2ST\7\"\2\2TU\7\6\2\2UV\7$\2\2VW\7\7\2\2"+
		"WX\7$\2\2XZ\7\b\2\2YK\3\2\2\2YS\3\2\2\2Z\13\3\2\2\2[\\\7\22\2\2\\]\7\""+
		"\2\2]\r\3\2\2\2^_\7\"\2\2_`\7\"\2\2`d\7\t\2\2ac\7\"\2\2ba\3\2\2\2cf\3"+
		"\2\2\2db\3\2\2\2de\3\2\2\2e\17\3\2\2\2fd\3\2\2\2gh\7\25\2\2hr\7\"\2\2"+
		"in\7\26\2\2jk\7\5\2\2km\7\"\2\2lj\3\2\2\2mp\3\2\2\2nl\3\2\2\2no\3\2\2"+
		"\2or\3\2\2\2pn\3\2\2\2qg\3\2\2\2qi\3\2\2\2r\21\3\2\2\2st\7\23\2\2tu\7"+
		"\"\2\2uv\7\t\2\2v|\5\24\13\2wx\7\24\2\2xy\7\"\2\2yz\7\t\2\2z|\5\24\13"+
		"\2{s\3\2\2\2{w\3\2\2\2|\23\3\2\2\2}~\b\13\1\2~\177\7\n\2\2\177\u0080\5"+
		"\24\13\2\u0080\u0081\7\13\2\2\u0081\u0096\3\2\2\2\u0082\u0096\5\36\20"+
		"\2\u0083\u0084\7\35\2\2\u0084\u0096\5\24\13\7\u0085\u0086\5&\24\2\u0086"+
		"\u0087\7\f\2\2\u0087\u0088\5\24\13\6\u0088\u0096\3\2\2\2\u0089\u008a\5"+
		"$\23\2\u008a\u008b\7\f\2\2\u008b\u008c\5\24\13\5\u008c\u0096\3\2\2\2\u008d"+
		"\u008e\5\"\22\2\u008e\u008f\7\f\2\2\u008f\u0090\5\24\13\4\u0090\u0096"+
		"\3\2\2\2\u0091\u0092\5 \21\2\u0092\u0093\7\f\2\2\u0093\u0094\5\24\13\3"+
		"\u0094\u0096\3\2\2\2\u0095}\3\2\2\2\u0095\u0082\3\2\2\2\u0095\u0083\3"+
		"\2\2\2\u0095\u0085\3\2\2\2\u0095\u0089\3\2\2\2\u0095\u008d\3\2\2\2\u0095"+
		"\u0091\3\2\2\2\u0096\u00a7\3\2\2\2\u0097\u009a\f\t\2\2\u0098\u0099\7\34"+
		"\2\2\u0099\u009b\5\24\13\2\u009a\u0098\3\2\2\2\u009b\u009c\3\2\2\2\u009c"+
		"\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u00a6\3\2\2\2\u009e\u00a1\f\b"+
		"\2\2\u009f\u00a0\7\33\2\2\u00a0\u00a2\5\24\13\2\u00a1\u009f\3\2\2\2\u00a2"+
		"\u00a3\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a6\3\2"+
		"\2\2\u00a5\u0097\3\2\2\2\u00a5\u009e\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7"+
		"\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\25\3\2\2\2\u00a9\u00a7\3\2\2"+
		"\2\u00aa\u00ab\5\32\16\2\u00ab\u00ac\5\30\r\2\u00ac\u00b0\7\31\2\2\u00ad"+
		"\u00af\7\"\2\2\u00ae\u00ad\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00ae\3\2"+
		"\2\2\u00b0\u00b1\3\2\2\2\u00b1\27\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00b4"+
		"\5\34\17\2\u00b4\u00b5\5\36\20\2\u00b5\u00bf\3\2\2\2\u00b6\u00b7\7\7\2"+
		"\2\u00b7\u00b8\5\34\17\2\u00b8\u00b9\5\36\20\2\u00b9\u00bf\3\2\2\2\u00ba"+
		"\u00bb\7\r\2\2\u00bb\u00bc\5\34\17\2\u00bc\u00bd\5\36\20\2\u00bd\u00bf"+
		"\3\2\2\2\u00be\u00b3\3\2\2\2\u00be\u00b6\3\2\2\2\u00be\u00ba\3\2\2\2\u00bf"+
		"\31\3\2\2\2\u00c0\u00c1\t\2\2\2\u00c1\33\3\2\2\2\u00c2\u00c7\7$\2\2\u00c3"+
		"\u00c4\7$\2\2\u00c4\u00c5\7\16\2\2\u00c5\u00c7\7$\2\2\u00c6\u00c2\3\2"+
		"\2\2\u00c6\u00c3\3\2\2\2\u00c7\35\3\2\2\2\u00c8\u00c9\7\"\2\2\u00c9\u00ca"+
		"\7\17\2\2\u00ca\u00cb\7\"\2\2\u00cb\u00cc\7\20\2\2\u00cc\37\3\2\2\2\u00cd"+
		"\u00ce\7\36\2\2\u00ce\u00cf\7\"\2\2\u00cf\u00d0\7\32\2\2\u00d0\u00d1\5"+
		"\n\6\2\u00d1!\3\2\2\2\u00d2\u00d3\7\37\2\2\u00d3\u00d4\7\"\2\2\u00d4\u00d5"+
		"\7\32\2\2\u00d5\u00d6\5\n\6\2\u00d6#\3\2\2\2\u00d7\u00d8\7 \2\2\u00d8"+
		"\u00d9\7$\2\2\u00d9\u00da\7\"\2\2\u00da\u00db\7\32\2\2\u00db\u00dc\5\n"+
		"\6\2\u00dc%\3\2\2\2\u00dd\u00de\7!\2\2\u00de\u00df\7$\2\2\u00df\u00e0"+
		"\7\"\2\2\u00e0\u00e1\7\32\2\2\u00e1\u00e2\5\n\6\2\u00e2\'\3\2\2\2\23+"+
		"@FPYdnq{\u0095\u009c\u00a3\u00a5\u00a7\u00b0\u00be\u00c6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}