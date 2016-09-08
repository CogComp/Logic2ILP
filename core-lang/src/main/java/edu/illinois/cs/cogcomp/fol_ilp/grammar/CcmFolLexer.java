// Generated from grammar/CcmFol.g4 by ANTLR 4.5.3
package edu.illinois.cs.cogcomp.fol_ilp.grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CcmFolLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "TERM", "RELATION", "CONSTRAINT", 
		"SOFT_CONSTRAINT", "PREDICATE", "MUTEX_PREDICATE", "MIN", "MAX", "SUBJECT_TO", 
		"IN", "AND", "OR", "NOT", "FORALL", "EXIST", "ATLEAST", "ATMOST", "Identifier", 
		"JavaLetter", "JavaLetterOrDigit", "STRING", "INT", "WS"
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


	public CcmFolLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CcmFol.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2%\u0102\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r"+
		"\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3\33"+
		"\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36"+
		"\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 "+
		"\3 \3 \3 \3 \3!\3!\7!\u00ec\n!\f!\16!\u00ef\13!\3\"\3\"\3#\3#\3$\6$\u00f6"+
		"\n$\r$\16$\u00f7\3%\6%\u00fb\n%\r%\16%\u00fc\3&\3&\3&\3&\2\2\'\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C"+
		"\2E\2G#I$K%\3\2\7\6\2&&C\\aac|\7\2&&\62;C\\aac|\3\2c|\3\2\62;\5\2\13\f"+
		"\17\17\"\"\u0102\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2"+
		"!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3"+
		"\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2"+
		"\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2G\3\2\2\2\2I"+
		"\3\2\2\2\2K\3\2\2\2\3M\3\2\2\2\5O\3\2\2\2\7Q\3\2\2\2\tS\3\2\2\2\13U\3"+
		"\2\2\2\rW\3\2\2\2\17Y\3\2\2\2\21[\3\2\2\2\23]\3\2\2\2\25_\3\2\2\2\27b"+
		"\3\2\2\2\31d\3\2\2\2\33f\3\2\2\2\35h\3\2\2\2\37j\3\2\2\2!o\3\2\2\2#x\3"+
		"\2\2\2%\u0083\3\2\2\2\'\u0093\3\2\2\2)\u009d\3\2\2\2+\u00ad\3\2\2\2-\u00b1"+
		"\3\2\2\2/\u00b5\3\2\2\2\61\u00bf\3\2\2\2\63\u00c2\3\2\2\2\65\u00c6\3\2"+
		"\2\2\67\u00c9\3\2\2\29\u00cd\3\2\2\2;\u00d4\3\2\2\2=\u00da\3\2\2\2?\u00e2"+
		"\3\2\2\2A\u00e9\3\2\2\2C\u00f0\3\2\2\2E\u00f2\3\2\2\2G\u00f5\3\2\2\2I"+
		"\u00fa\3\2\2\2K\u00fe\3\2\2\2MN\7=\2\2N\4\3\2\2\2OP\7A\2\2P\6\3\2\2\2"+
		"QR\7.\2\2R\b\3\2\2\2ST\7]\2\2T\n\3\2\2\2UV\7/\2\2V\f\3\2\2\2WX\7_\2\2"+
		"X\16\3\2\2\2YZ\7?\2\2Z\20\3\2\2\2[\\\7}\2\2\\\22\3\2\2\2]^\7\177\2\2^"+
		"\24\3\2\2\2_`\7/\2\2`a\7@\2\2a\26\3\2\2\2bc\7-\2\2c\30\3\2\2\2de\7\60"+
		"\2\2e\32\3\2\2\2fg\7*\2\2g\34\3\2\2\2hi\7+\2\2i\36\3\2\2\2jk\7v\2\2kl"+
		"\7g\2\2lm\7t\2\2mn\7o\2\2n \3\2\2\2op\7t\2\2pq\7g\2\2qr\7n\2\2rs\7c\2"+
		"\2st\7v\2\2tu\7k\2\2uv\7q\2\2vw\7p\2\2w\"\3\2\2\2xy\7e\2\2yz\7q\2\2z{"+
		"\7p\2\2{|\7u\2\2|}\7v\2\2}~\7t\2\2~\177\7c\2\2\177\u0080\7k\2\2\u0080"+
		"\u0081\7p\2\2\u0081\u0082\7v\2\2\u0082$\3\2\2\2\u0083\u0084\7u\2\2\u0084"+
		"\u0085\7q\2\2\u0085\u0086\7h\2\2\u0086\u0087\7v\2\2\u0087\u0088\7a\2\2"+
		"\u0088\u0089\7e\2\2\u0089\u008a\7q\2\2\u008a\u008b\7p\2\2\u008b\u008c"+
		"\7u\2\2\u008c\u008d\7v\2\2\u008d\u008e\7t\2\2\u008e\u008f\7c\2\2\u008f"+
		"\u0090\7k\2\2\u0090\u0091\7p\2\2\u0091\u0092\7v\2\2\u0092&\3\2\2\2\u0093"+
		"\u0094\7r\2\2\u0094\u0095\7t\2\2\u0095\u0096\7g\2\2\u0096\u0097\7f\2\2"+
		"\u0097\u0098\7k\2\2\u0098\u0099\7e\2\2\u0099\u009a\7c\2\2\u009a\u009b"+
		"\7v\2\2\u009b\u009c\7g\2\2\u009c(\3\2\2\2\u009d\u009e\7o\2\2\u009e\u009f"+
		"\7w\2\2\u009f\u00a0\7v\2\2\u00a0\u00a1\7g\2\2\u00a1\u00a2\7z\2\2\u00a2"+
		"\u00a3\7a\2\2\u00a3\u00a4\7r\2\2\u00a4\u00a5\7t\2\2\u00a5\u00a6\7g\2\2"+
		"\u00a6\u00a7\7f\2\2\u00a7\u00a8\7k\2\2\u00a8\u00a9\7e\2\2\u00a9\u00aa"+
		"\7c\2\2\u00aa\u00ab\7v\2\2\u00ab\u00ac\7g\2\2\u00ac*\3\2\2\2\u00ad\u00ae"+
		"\7o\2\2\u00ae\u00af\7k\2\2\u00af\u00b0\7p\2\2\u00b0,\3\2\2\2\u00b1\u00b2"+
		"\7o\2\2\u00b2\u00b3\7c\2\2\u00b3\u00b4\7z\2\2\u00b4.\3\2\2\2\u00b5\u00b6"+
		"\7u\2\2\u00b6\u00b7\7w\2\2\u00b7\u00b8\7d\2\2\u00b8\u00b9\7l\2\2\u00b9"+
		"\u00ba\7g\2\2\u00ba\u00bb\7e\2\2\u00bb\u00bc\7v\2\2\u00bc\u00bd\7V\2\2"+
		"\u00bd\u00be\7q\2\2\u00be\60\3\2\2\2\u00bf\u00c0\7k\2\2\u00c0\u00c1\7"+
		"p\2\2\u00c1\62\3\2\2\2\u00c2\u00c3\7c\2\2\u00c3\u00c4\7p\2\2\u00c4\u00c5"+
		"\7f\2\2\u00c5\64\3\2\2\2\u00c6\u00c7\7q\2\2\u00c7\u00c8\7t\2\2\u00c8\66"+
		"\3\2\2\2\u00c9\u00ca\7p\2\2\u00ca\u00cb\7q\2\2\u00cb\u00cc\7v\2\2\u00cc"+
		"8\3\2\2\2\u00cd\u00ce\7h\2\2\u00ce\u00cf\7q\2\2\u00cf\u00d0\7t\2\2\u00d0"+
		"\u00d1\7c\2\2\u00d1\u00d2\7n\2\2\u00d2\u00d3\7n\2\2\u00d3:\3\2\2\2\u00d4"+
		"\u00d5\7g\2\2\u00d5\u00d6\7z\2\2\u00d6\u00d7\7k\2\2\u00d7\u00d8\7u\2\2"+
		"\u00d8\u00d9\7v\2\2\u00d9<\3\2\2\2\u00da\u00db\7c\2\2\u00db\u00dc\7v\2"+
		"\2\u00dc\u00dd\7N\2\2\u00dd\u00de\7g\2\2\u00de\u00df\7c\2\2\u00df\u00e0"+
		"\7u\2\2\u00e0\u00e1\7v\2\2\u00e1>\3\2\2\2\u00e2\u00e3\7c\2\2\u00e3\u00e4"+
		"\7v\2\2\u00e4\u00e5\7O\2\2\u00e5\u00e6\7q\2\2\u00e6\u00e7\7u\2\2\u00e7"+
		"\u00e8\7v\2\2\u00e8@\3\2\2\2\u00e9\u00ed\5C\"\2\u00ea\u00ec\5E#\2\u00eb"+
		"\u00ea\3\2\2\2\u00ec\u00ef\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ed\u00ee\3\2"+
		"\2\2\u00eeB\3\2\2\2\u00ef\u00ed\3\2\2\2\u00f0\u00f1\t\2\2\2\u00f1D\3\2"+
		"\2\2\u00f2\u00f3\t\3\2\2\u00f3F\3\2\2\2\u00f4\u00f6\t\4\2\2\u00f5\u00f4"+
		"\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8"+
		"H\3\2\2\2\u00f9\u00fb\t\5\2\2\u00fa\u00f9\3\2\2\2\u00fb\u00fc\3\2\2\2"+
		"\u00fc\u00fa\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fdJ\3\2\2\2\u00fe\u00ff\t"+
		"\6\2\2\u00ff\u0100\3\2\2\2\u0100\u0101\b&\2\2\u0101L\3\2\2\2\6\2\u00ed"+
		"\u00f7\u00fc\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}