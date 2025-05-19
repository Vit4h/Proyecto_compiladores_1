// Generated from C:/Users/maron/IdeaProjects/Proyecto_compiladores_2/Algebra.g4 by ANTLR 4.13.2
package src.Parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AlgebraParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AlgebraVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AlgebraParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(AlgebraParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link AlgebraParser#instruccion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccion(AlgebraParser.InstruccionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AlgebraParser#asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignacion(AlgebraParser.AsignacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AlgebraParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion(AlgebraParser.ExpresionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AlgebraParser#sumaResta}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSumaResta(AlgebraParser.SumaRestaContext ctx);
	/**
	 * Visit a parse tree produced by {@link AlgebraParser#multiplicacionDivision}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicacionDivision(AlgebraParser.MultiplicacionDivisionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AlgebraParser#agrupacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAgrupacion(AlgebraParser.AgrupacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AlgebraParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(AlgebraParser.LiteralContext ctx);
}