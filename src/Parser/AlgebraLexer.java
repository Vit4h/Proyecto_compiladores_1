// Generated from C:/Users/maron/IdeaProjects/Proyecto_compiladores_2/Algebra.g4 by ANTLR 4.13.2
package src.Parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class AlgebraLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ASIGNACION=1, PUNTO_Y_COMA=2, COMA=3, MAS=4, MENOS=5, POR=6, DIVIDIDO=7, 
		MODULO=8, LLAVE_IZQUIERDA=9, LLAVE_DERECHA=10, INT_LITERAL=11, DOUBLE_LITERAL=12, 
		INT_TIPO=13, DOUBLE_TIPO=14, IDENTIFICADOR=15, WS=16;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"ASIGNACION", "PUNTO_Y_COMA", "COMA", "MAS", "MENOS", "POR", "DIVIDIDO", 
			"MODULO", "LLAVE_IZQUIERDA", "LLAVE_DERECHA", "INT_LITERAL", "DOUBLE_LITERAL", 
			"INT_TIPO", "DOUBLE_TIPO", "IDENTIFICADOR", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'=>'", "';'", "','", "'+'", "'-'", "'*'", "'/'", "'#'", "'{'", 
			"'}'", null, null, "'int'", "'double'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ASIGNACION", "PUNTO_Y_COMA", "COMA", "MAS", "MENOS", "POR", "DIVIDIDO", 
			"MODULO", "LLAVE_IZQUIERDA", "LLAVE_DERECHA", "INT_LITERAL", "DOUBLE_LITERAL", 
			"INT_TIPO", "DOUBLE_TIPO", "IDENTIFICADOR", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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


	public AlgebraLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Algebra.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0010_\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0004\n8\b\n\u000b\n\f"+
		"\n9\u0001\u000b\u0004\u000b=\b\u000b\u000b\u000b\f\u000b>\u0001\u000b"+
		"\u0001\u000b\u0004\u000bC\b\u000b\u000b\u000b\f\u000bD\u0001\f\u0001\f"+
		"\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0005\u000eT\b\u000e\n\u000e\f\u000eW\t\u000e"+
		"\u0001\u000f\u0004\u000fZ\b\u000f\u000b\u000f\f\u000f[\u0001\u000f\u0001"+
		"\u000f\u0000\u0000\u0010\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017"+
		"\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010\u0001\u0000\u0004\u0001"+
		"\u000009\u0003\u0000AZ__az\u0004\u000009AZ__az\u0003\u0000\t\n\r\r  c"+
		"\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000"+
		"\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000"+
		"\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000"+
		"\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0001!\u0001"+
		"\u0000\u0000\u0000\u0003$\u0001\u0000\u0000\u0000\u0005&\u0001\u0000\u0000"+
		"\u0000\u0007(\u0001\u0000\u0000\u0000\t*\u0001\u0000\u0000\u0000\u000b"+
		",\u0001\u0000\u0000\u0000\r.\u0001\u0000\u0000\u0000\u000f0\u0001\u0000"+
		"\u0000\u0000\u00112\u0001\u0000\u0000\u0000\u00134\u0001\u0000\u0000\u0000"+
		"\u00157\u0001\u0000\u0000\u0000\u0017<\u0001\u0000\u0000\u0000\u0019F"+
		"\u0001\u0000\u0000\u0000\u001bJ\u0001\u0000\u0000\u0000\u001dQ\u0001\u0000"+
		"\u0000\u0000\u001fY\u0001\u0000\u0000\u0000!\"\u0005=\u0000\u0000\"#\u0005"+
		">\u0000\u0000#\u0002\u0001\u0000\u0000\u0000$%\u0005;\u0000\u0000%\u0004"+
		"\u0001\u0000\u0000\u0000&\'\u0005,\u0000\u0000\'\u0006\u0001\u0000\u0000"+
		"\u0000()\u0005+\u0000\u0000)\b\u0001\u0000\u0000\u0000*+\u0005-\u0000"+
		"\u0000+\n\u0001\u0000\u0000\u0000,-\u0005*\u0000\u0000-\f\u0001\u0000"+
		"\u0000\u0000./\u0005/\u0000\u0000/\u000e\u0001\u0000\u0000\u000001\u0005"+
		"#\u0000\u00001\u0010\u0001\u0000\u0000\u000023\u0005{\u0000\u00003\u0012"+
		"\u0001\u0000\u0000\u000045\u0005}\u0000\u00005\u0014\u0001\u0000\u0000"+
		"\u000068\u0007\u0000\u0000\u000076\u0001\u0000\u0000\u000089\u0001\u0000"+
		"\u0000\u000097\u0001\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:\u0016"+
		"\u0001\u0000\u0000\u0000;=\u0007\u0000\u0000\u0000<;\u0001\u0000\u0000"+
		"\u0000=>\u0001\u0000\u0000\u0000><\u0001\u0000\u0000\u0000>?\u0001\u0000"+
		"\u0000\u0000?@\u0001\u0000\u0000\u0000@B\u0005.\u0000\u0000AC\u0007\u0000"+
		"\u0000\u0000BA\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000DB\u0001"+
		"\u0000\u0000\u0000DE\u0001\u0000\u0000\u0000E\u0018\u0001\u0000\u0000"+
		"\u0000FG\u0005i\u0000\u0000GH\u0005n\u0000\u0000HI\u0005t\u0000\u0000"+
		"I\u001a\u0001\u0000\u0000\u0000JK\u0005d\u0000\u0000KL\u0005o\u0000\u0000"+
		"LM\u0005u\u0000\u0000MN\u0005b\u0000\u0000NO\u0005l\u0000\u0000OP\u0005"+
		"e\u0000\u0000P\u001c\u0001\u0000\u0000\u0000QU\u0007\u0001\u0000\u0000"+
		"RT\u0007\u0002\u0000\u0000SR\u0001\u0000\u0000\u0000TW\u0001\u0000\u0000"+
		"\u0000US\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000V\u001e\u0001"+
		"\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000XZ\u0007\u0003\u0000\u0000"+
		"YX\u0001\u0000\u0000\u0000Z[\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000"+
		"\u0000[\\\u0001\u0000\u0000\u0000\\]\u0001\u0000\u0000\u0000]^\u0006\u000f"+
		"\u0000\u0000^ \u0001\u0000\u0000\u0000\u0006\u00009>DU[\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}