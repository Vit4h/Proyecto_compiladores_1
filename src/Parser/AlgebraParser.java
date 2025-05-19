// Generated from C:/Users/maron/IdeaProjects/Proyecto_compiladores_2/Algebra.g4 by ANTLR 4.13.2
package src.Parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class AlgebraParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ASIGNACION=1, PUNTO_Y_COMA=2, MAS=3, MENOS=4, POR=5, DIVIDIDO=6, MODULO=7, 
		LLAVE_IZQUIERDA=8, LLAVE_DERECHA=9, INT=10, DOUBLE=11, IDENTIFICADOR=12, 
		WS=13;
	public static final int
		RULE_program = 0, RULE_instruccion = 1, RULE_asignacion = 2, RULE_expresion = 3, 
		RULE_sumaResta = 4, RULE_multiplicacionDivision = 5, RULE_agrupacion = 6, 
		RULE_literal = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "instruccion", "asignacion", "expresion", "sumaResta", "multiplicacionDivision", 
			"agrupacion", "literal"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'=>'", "';'", "'+'", "'-'", "'*'", "'/'", "'#'", "'{'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ASIGNACION", "PUNTO_Y_COMA", "MAS", "MENOS", "POR", "DIVIDIDO", 
			"MODULO", "LLAVE_IZQUIERDA", "LLAVE_DERECHA", "INT", "DOUBLE", "IDENTIFICADOR", 
			"WS"
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

	@Override
	public String getGrammarFileName() { return "Algebra.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AlgebraParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(AlgebraParser.EOF, 0); }
		public List<InstruccionContext> instruccion() {
			return getRuleContexts(InstruccionContext.class);
		}
		public InstruccionContext instruccion(int i) {
			return getRuleContext(InstruccionContext.class,i);
		}
		public List<TerminalNode> PUNTO_Y_COMA() { return getTokens(AlgebraParser.PUNTO_Y_COMA); }
		public TerminalNode PUNTO_Y_COMA(int i) {
			return getToken(AlgebraParser.PUNTO_Y_COMA, i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 7424L) != 0)) {
				{
				{
				setState(16);
				instruccion();
				setState(17);
				match(PUNTO_Y_COMA);
				}
				}
				setState(23);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(24);
			match(EOF);
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

	@SuppressWarnings("CheckReturnValue")
	public static class InstruccionContext extends ParserRuleContext {
		public AsignacionContext asignacion() {
			return getRuleContext(AsignacionContext.class,0);
		}
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public InstruccionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruccion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitInstruccion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstruccionContext instruccion() throws RecognitionException {
		InstruccionContext _localctx = new InstruccionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_instruccion);
		try {
			setState(28);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(26);
				asignacion();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(27);
				expresion();
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

	@SuppressWarnings("CheckReturnValue")
	public static class AsignacionContext extends ParserRuleContext {
		public TerminalNode IDENTIFICADOR() { return getToken(AlgebraParser.IDENTIFICADOR, 0); }
		public TerminalNode ASIGNACION() { return getToken(AlgebraParser.ASIGNACION, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public AsignacionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asignacion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitAsignacion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AsignacionContext asignacion() throws RecognitionException {
		AsignacionContext _localctx = new AsignacionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_asignacion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(IDENTIFICADOR);
			setState(31);
			match(ASIGNACION);
			setState(32);
			expresion();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExpresionContext extends ParserRuleContext {
		public SumaRestaContext sumaResta() {
			return getRuleContext(SumaRestaContext.class,0);
		}
		public ExpresionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expresion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitExpresion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpresionContext expresion() throws RecognitionException {
		ExpresionContext _localctx = new ExpresionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_expresion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			sumaResta();
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

	@SuppressWarnings("CheckReturnValue")
	public static class SumaRestaContext extends ParserRuleContext {
		public List<MultiplicacionDivisionContext> multiplicacionDivision() {
			return getRuleContexts(MultiplicacionDivisionContext.class);
		}
		public MultiplicacionDivisionContext multiplicacionDivision(int i) {
			return getRuleContext(MultiplicacionDivisionContext.class,i);
		}
		public List<TerminalNode> MAS() { return getTokens(AlgebraParser.MAS); }
		public TerminalNode MAS(int i) {
			return getToken(AlgebraParser.MAS, i);
		}
		public List<TerminalNode> MENOS() { return getTokens(AlgebraParser.MENOS); }
		public TerminalNode MENOS(int i) {
			return getToken(AlgebraParser.MENOS, i);
		}
		public SumaRestaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sumaResta; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitSumaResta(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SumaRestaContext sumaResta() throws RecognitionException {
		SumaRestaContext _localctx = new SumaRestaContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_sumaResta);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			multiplicacionDivision();
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MAS || _la==MENOS) {
				{
				{
				setState(37);
				_la = _input.LA(1);
				if ( !(_la==MAS || _la==MENOS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(38);
				multiplicacionDivision();
				}
				}
				setState(43);
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

	@SuppressWarnings("CheckReturnValue")
	public static class MultiplicacionDivisionContext extends ParserRuleContext {
		public List<AgrupacionContext> agrupacion() {
			return getRuleContexts(AgrupacionContext.class);
		}
		public AgrupacionContext agrupacion(int i) {
			return getRuleContext(AgrupacionContext.class,i);
		}
		public List<TerminalNode> POR() { return getTokens(AlgebraParser.POR); }
		public TerminalNode POR(int i) {
			return getToken(AlgebraParser.POR, i);
		}
		public List<TerminalNode> DIVIDIDO() { return getTokens(AlgebraParser.DIVIDIDO); }
		public TerminalNode DIVIDIDO(int i) {
			return getToken(AlgebraParser.DIVIDIDO, i);
		}
		public List<TerminalNode> MODULO() { return getTokens(AlgebraParser.MODULO); }
		public TerminalNode MODULO(int i) {
			return getToken(AlgebraParser.MODULO, i);
		}
		public MultiplicacionDivisionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicacionDivision; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitMultiplicacionDivision(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicacionDivisionContext multiplicacionDivision() throws RecognitionException {
		MultiplicacionDivisionContext _localctx = new MultiplicacionDivisionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_multiplicacionDivision);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			agrupacion();
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 224L) != 0)) {
				{
				{
				setState(45);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 224L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(46);
				agrupacion();
				}
				}
				setState(51);
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

	@SuppressWarnings("CheckReturnValue")
	public static class AgrupacionContext extends ParserRuleContext {
		public TerminalNode LLAVE_IZQUIERDA() { return getToken(AlgebraParser.LLAVE_IZQUIERDA, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode LLAVE_DERECHA() { return getToken(AlgebraParser.LLAVE_DERECHA, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode IDENTIFICADOR() { return getToken(AlgebraParser.IDENTIFICADOR, 0); }
		public AgrupacionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_agrupacion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitAgrupacion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AgrupacionContext agrupacion() throws RecognitionException {
		AgrupacionContext _localctx = new AgrupacionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_agrupacion);
		try {
			setState(58);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LLAVE_IZQUIERDA:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				match(LLAVE_IZQUIERDA);
				setState(53);
				expresion();
				setState(54);
				match(LLAVE_DERECHA);
				}
				break;
			case INT:
			case DOUBLE:
				enterOuterAlt(_localctx, 2);
				{
				setState(56);
				literal();
				}
				break;
			case IDENTIFICADOR:
				enterOuterAlt(_localctx, 3);
				{
				setState(57);
				match(IDENTIFICADOR);
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

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(AlgebraParser.INT, 0); }
		public TerminalNode DOUBLE() { return getToken(AlgebraParser.DOUBLE, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			_la = _input.LA(1);
			if ( !(_la==INT || _la==DOUBLE) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
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

	public static final String _serializedATN =
		"\u0004\u0001\r?\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u0014\b\u0000\n\u0000\f\u0000"+
		"\u0017\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001"+
		"\u001d\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004(\b\u0004"+
		"\n\u0004\f\u0004+\t\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005"+
		"0\b\u0005\n\u0005\f\u00053\t\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006;\b\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0000\u0000\b\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0000\u0003\u0001\u0000\u0003\u0004\u0001\u0000\u0005\u0007\u0001\u0000"+
		"\n\u000b<\u0000\u0015\u0001\u0000\u0000\u0000\u0002\u001c\u0001\u0000"+
		"\u0000\u0000\u0004\u001e\u0001\u0000\u0000\u0000\u0006\"\u0001\u0000\u0000"+
		"\u0000\b$\u0001\u0000\u0000\u0000\n,\u0001\u0000\u0000\u0000\f:\u0001"+
		"\u0000\u0000\u0000\u000e<\u0001\u0000\u0000\u0000\u0010\u0011\u0003\u0002"+
		"\u0001\u0000\u0011\u0012\u0005\u0002\u0000\u0000\u0012\u0014\u0001\u0000"+
		"\u0000\u0000\u0013\u0010\u0001\u0000\u0000\u0000\u0014\u0017\u0001\u0000"+
		"\u0000\u0000\u0015\u0013\u0001\u0000\u0000\u0000\u0015\u0016\u0001\u0000"+
		"\u0000\u0000\u0016\u0018\u0001\u0000\u0000\u0000\u0017\u0015\u0001\u0000"+
		"\u0000\u0000\u0018\u0019\u0005\u0000\u0000\u0001\u0019\u0001\u0001\u0000"+
		"\u0000\u0000\u001a\u001d\u0003\u0004\u0002\u0000\u001b\u001d\u0003\u0006"+
		"\u0003\u0000\u001c\u001a\u0001\u0000\u0000\u0000\u001c\u001b\u0001\u0000"+
		"\u0000\u0000\u001d\u0003\u0001\u0000\u0000\u0000\u001e\u001f\u0005\f\u0000"+
		"\u0000\u001f \u0005\u0001\u0000\u0000 !\u0003\u0006\u0003\u0000!\u0005"+
		"\u0001\u0000\u0000\u0000\"#\u0003\b\u0004\u0000#\u0007\u0001\u0000\u0000"+
		"\u0000$)\u0003\n\u0005\u0000%&\u0007\u0000\u0000\u0000&(\u0003\n\u0005"+
		"\u0000\'%\u0001\u0000\u0000\u0000(+\u0001\u0000\u0000\u0000)\'\u0001\u0000"+
		"\u0000\u0000)*\u0001\u0000\u0000\u0000*\t\u0001\u0000\u0000\u0000+)\u0001"+
		"\u0000\u0000\u0000,1\u0003\f\u0006\u0000-.\u0007\u0001\u0000\u0000.0\u0003"+
		"\f\u0006\u0000/-\u0001\u0000\u0000\u000003\u0001\u0000\u0000\u00001/\u0001"+
		"\u0000\u0000\u000012\u0001\u0000\u0000\u00002\u000b\u0001\u0000\u0000"+
		"\u000031\u0001\u0000\u0000\u000045\u0005\b\u0000\u000056\u0003\u0006\u0003"+
		"\u000067\u0005\t\u0000\u00007;\u0001\u0000\u0000\u00008;\u0003\u000e\u0007"+
		"\u00009;\u0005\f\u0000\u0000:4\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000"+
		"\u0000:9\u0001\u0000\u0000\u0000;\r\u0001\u0000\u0000\u0000<=\u0007\u0002"+
		"\u0000\u0000=\u000f\u0001\u0000\u0000\u0000\u0005\u0015\u001c)1:";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}