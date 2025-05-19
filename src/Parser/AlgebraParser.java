// Generated from C:/Users/maron/IdeaProjects/Proyecto_compiladores_2/Algebra.g4 by ANTLR 4.13.2
package src.Parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class AlgebraParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ASIGNACION=1, PUNTO_Y_COMA=2, COMA=3, MAS=4, MENOS=5, POR=6, DIVIDIDO=7, 
		MODULO=8, LLAVE_IZQUIERDA=9, LLAVE_DERECHA=10, INT_LITERAL=11, DOUBLE_LITERAL=12, 
		INT_TIPO=13, DOUBLE_TIPO=14, IDENTIFICADOR=15, WS=16;
	public static final int
		RULE_program = 0, RULE_instruccion = 1, RULE_declaracion = 2, RULE_tipo = 3, 
		RULE_listaDeclaradores = 4, RULE_declarador = 5, RULE_expresion = 6, RULE_sumaResta = 7, 
		RULE_multiplicacionDivision = 8, RULE_agrupacion = 9, RULE_literal = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "instruccion", "declaracion", "tipo", "listaDeclaradores", 
			"declarador", "expresion", "sumaResta", "multiplicacionDivision", "agrupacion", 
			"literal"
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
			setState(27);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==INT_TIPO || _la==DOUBLE_TIPO) {
				{
				{
				setState(22);
				instruccion();
				setState(23);
				match(PUNTO_Y_COMA);
				}
				}
				setState(29);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(30);
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
		public DeclaracionContext declaracion() {
			return getRuleContext(DeclaracionContext.class,0);
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
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			declaracion();
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
	public static class DeclaracionContext extends ParserRuleContext {
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public ListaDeclaradoresContext listaDeclaradores() {
			return getRuleContext(ListaDeclaradoresContext.class,0);
		}
		public DeclaracionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaracion; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitDeclaracion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaracionContext declaracion() throws RecognitionException {
		DeclaracionContext _localctx = new DeclaracionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaracion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			tipo();
			setState(35);
			listaDeclaradores();
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
	public static class TipoContext extends ParserRuleContext {
		public TerminalNode INT_TIPO() { return getToken(AlgebraParser.INT_TIPO, 0); }
		public TerminalNode DOUBLE_TIPO() { return getToken(AlgebraParser.DOUBLE_TIPO, 0); }
		public TipoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tipo; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitTipo(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TipoContext tipo() throws RecognitionException {
		TipoContext _localctx = new TipoContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_tipo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			_la = _input.LA(1);
			if ( !(_la==INT_TIPO || _la==DOUBLE_TIPO) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class ListaDeclaradoresContext extends ParserRuleContext {
		public List<DeclaradorContext> declarador() {
			return getRuleContexts(DeclaradorContext.class);
		}
		public DeclaradorContext declarador(int i) {
			return getRuleContext(DeclaradorContext.class,i);
		}
		public List<TerminalNode> COMA() { return getTokens(AlgebraParser.COMA); }
		public TerminalNode COMA(int i) {
			return getToken(AlgebraParser.COMA, i);
		}
		public ListaDeclaradoresContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listaDeclaradores; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitListaDeclaradores(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListaDeclaradoresContext listaDeclaradores() throws RecognitionException {
		ListaDeclaradoresContext _localctx = new ListaDeclaradoresContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_listaDeclaradores);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			declarador();
			setState(44);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMA) {
				{
				{
				setState(40);
				match(COMA);
				setState(41);
				declarador();
				}
				}
				setState(46);
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
	public static class DeclaradorContext extends ParserRuleContext {
		public TerminalNode IDENTIFICADOR() { return getToken(AlgebraParser.IDENTIFICADOR, 0); }
		public TerminalNode ASIGNACION() { return getToken(AlgebraParser.ASIGNACION, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public DeclaradorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarador; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AlgebraVisitor ) return ((AlgebraVisitor<? extends T>)visitor).visitDeclarador(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaradorContext declarador() throws RecognitionException {
		DeclaradorContext _localctx = new DeclaradorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_declarador);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(IDENTIFICADOR);
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASIGNACION) {
				{
				setState(48);
				match(ASIGNACION);
				setState(49);
				expresion();
				}
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
		enterRule(_localctx, 12, RULE_expresion);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
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
		enterRule(_localctx, 14, RULE_sumaResta);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			multiplicacionDivision();
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MAS || _la==MENOS) {
				{
				{
				setState(55);
				_la = _input.LA(1);
				if ( !(_la==MAS || _la==MENOS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(56);
				multiplicacionDivision();
				}
				}
				setState(61);
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
		enterRule(_localctx, 16, RULE_multiplicacionDivision);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			agrupacion();
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 448L) != 0)) {
				{
				{
				setState(63);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 448L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(64);
				agrupacion();
				}
				}
				setState(69);
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
		enterRule(_localctx, 18, RULE_agrupacion);
		try {
			setState(76);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LLAVE_IZQUIERDA:
				enterOuterAlt(_localctx, 1);
				{
				setState(70);
				match(LLAVE_IZQUIERDA);
				setState(71);
				expresion();
				setState(72);
				match(LLAVE_DERECHA);
				}
				break;
			case INT_LITERAL:
			case DOUBLE_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(74);
				literal();
				}
				break;
			case IDENTIFICADOR:
				enterOuterAlt(_localctx, 3);
				{
				setState(75);
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
		public TerminalNode INT_LITERAL() { return getToken(AlgebraParser.INT_LITERAL, 0); }
		public TerminalNode DOUBLE_LITERAL() { return getToken(AlgebraParser.DOUBLE_LITERAL, 0); }
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
		enterRule(_localctx, 20, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			_la = _input.LA(1);
			if ( !(_la==INT_LITERAL || _la==DOUBLE_LITERAL) ) {
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
		"\u0004\u0001\u0010Q\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0005\u0000\u001a\b\u0000\n\u0000\f\u0000\u001d\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004"+
		"+\b\u0004\n\u0004\f\u0004.\t\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0003\u00053\b\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0005\u0007:\b\u0007\n\u0007\f\u0007=\t\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0005\bB\b\b\n\b\f\bE\t\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0003\tM\b\t\u0001\n\u0001\n\u0001\n\u0000\u0000\u000b\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0000\u0004\u0001\u0000"+
		"\r\u000e\u0001\u0000\u0004\u0005\u0001\u0000\u0006\b\u0001\u0000\u000b"+
		"\fL\u0000\u001b\u0001\u0000\u0000\u0000\u0002 \u0001\u0000\u0000\u0000"+
		"\u0004\"\u0001\u0000\u0000\u0000\u0006%\u0001\u0000\u0000\u0000\b\'\u0001"+
		"\u0000\u0000\u0000\n/\u0001\u0000\u0000\u0000\f4\u0001\u0000\u0000\u0000"+
		"\u000e6\u0001\u0000\u0000\u0000\u0010>\u0001\u0000\u0000\u0000\u0012L"+
		"\u0001\u0000\u0000\u0000\u0014N\u0001\u0000\u0000\u0000\u0016\u0017\u0003"+
		"\u0002\u0001\u0000\u0017\u0018\u0005\u0002\u0000\u0000\u0018\u001a\u0001"+
		"\u0000\u0000\u0000\u0019\u0016\u0001\u0000\u0000\u0000\u001a\u001d\u0001"+
		"\u0000\u0000\u0000\u001b\u0019\u0001\u0000\u0000\u0000\u001b\u001c\u0001"+
		"\u0000\u0000\u0000\u001c\u001e\u0001\u0000\u0000\u0000\u001d\u001b\u0001"+
		"\u0000\u0000\u0000\u001e\u001f\u0005\u0000\u0000\u0001\u001f\u0001\u0001"+
		"\u0000\u0000\u0000 !\u0003\u0004\u0002\u0000!\u0003\u0001\u0000\u0000"+
		"\u0000\"#\u0003\u0006\u0003\u0000#$\u0003\b\u0004\u0000$\u0005\u0001\u0000"+
		"\u0000\u0000%&\u0007\u0000\u0000\u0000&\u0007\u0001\u0000\u0000\u0000"+
		"\',\u0003\n\u0005\u0000()\u0005\u0003\u0000\u0000)+\u0003\n\u0005\u0000"+
		"*(\u0001\u0000\u0000\u0000+.\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000"+
		"\u0000,-\u0001\u0000\u0000\u0000-\t\u0001\u0000\u0000\u0000.,\u0001\u0000"+
		"\u0000\u0000/2\u0005\u000f\u0000\u000001\u0005\u0001\u0000\u000013\u0003"+
		"\f\u0006\u000020\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u00003\u000b"+
		"\u0001\u0000\u0000\u000045\u0003\u000e\u0007\u00005\r\u0001\u0000\u0000"+
		"\u00006;\u0003\u0010\b\u000078\u0007\u0001\u0000\u00008:\u0003\u0010\b"+
		"\u000097\u0001\u0000\u0000\u0000:=\u0001\u0000\u0000\u0000;9\u0001\u0000"+
		"\u0000\u0000;<\u0001\u0000\u0000\u0000<\u000f\u0001\u0000\u0000\u0000"+
		"=;\u0001\u0000\u0000\u0000>C\u0003\u0012\t\u0000?@\u0007\u0002\u0000\u0000"+
		"@B\u0003\u0012\t\u0000A?\u0001\u0000\u0000\u0000BE\u0001\u0000\u0000\u0000"+
		"CA\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000D\u0011\u0001\u0000"+
		"\u0000\u0000EC\u0001\u0000\u0000\u0000FG\u0005\t\u0000\u0000GH\u0003\f"+
		"\u0006\u0000HI\u0005\n\u0000\u0000IM\u0001\u0000\u0000\u0000JM\u0003\u0014"+
		"\n\u0000KM\u0005\u000f\u0000\u0000LF\u0001\u0000\u0000\u0000LJ\u0001\u0000"+
		"\u0000\u0000LK\u0001\u0000\u0000\u0000M\u0013\u0001\u0000\u0000\u0000"+
		"NO\u0007\u0003\u0000\u0000O\u0015\u0001\u0000\u0000\u0000\u0006\u001b"+
		",2;CL";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}