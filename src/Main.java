package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import src.Parser.AlgebraLexer;
import src.Parser.AlgebraParser;
import src.AlgebraEvaluatorVisitor;


public class Main {
    public static void main(String[] args) {
        try {
            StringBuilder contenido = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader("archivo.txt"));
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
            reader.close();

            // Ejecutar análisis léxico
            List<Token> tokens = Lexer.analizarTexto(contenido.toString());

            // Mostrar siempre los tokens
            System.out.println("=== TOKENS GENERADOS ===");
            imprimirTokensCompilador(tokens);

            // Mostrar errores léxicos si existen
            if (!Lexer.getErrores().isEmpty()) {
                System.out.println("\n=== ERRORES LÉXICOS ===");
                for (LexicalError error : Lexer.getErrores()) {
                    System.out.println(error);
                }
                return; // ⚠️ Evita pasar a ANTLR si hay errores léxicos
            }

            // Análisis sintáctico y ejecución
            System.out.println("\n[ANÁLISIS SINTÁCTICO Y EJECUCIÓN CON ANTLR]");
            CharStream input = CharStreams.fromString(contenido.toString());

            AlgebraLexer antlrLexer = new AlgebraLexer(input);
            antlrLexer.removeErrorListeners();
            antlrLexer.addErrorListener(new CustomErrorListener());

            CommonTokenStream antlrTokens = new CommonTokenStream(antlrLexer);
            AlgebraParser parser = new AlgebraParser(antlrTokens);

            parser.removeErrorListeners();
            parser.addErrorListener(new CustomErrorListener());
            AlgebraParser.ProgramContext tree = parser.program();

            AlgebraEvaluatorVisitor visitor = new AlgebraEvaluatorVisitor();
            visitor.visit(tree);
            visitor.generarTAC(tree);
            visitor.imprimirTAC();

        } catch (FileNotFoundException e) {
            System.out.println("Error: El archivo 'archivo.txt' no se encontró.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado durante el análisis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void imprimirTokensCompilador(List<Token> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            System.out.println("No hay tokens para mostrar.");
            return;
        }

        for (Token token : tokens) {
            switch (token.tipo) {
                case IDENTIFICADOR:
                    System.out.print("<ID>");
                    break;
                case NUMERO:
                    System.out.print("<NUM," + token.valor + "> ");
                    break;
                case OPERADOR:
                case OPERADOR_COMPARACION:
                case OPERADOR_LOGICO:
                    System.out.print("<" + token.valor + "> ");
                    break;
                case PUNTO_Y_COMA:
                    System.out.print("<TERMINACION> ");
                    break;
                case TIPO_DATO:
                case PALABRA_RESERVADA:
                case FUNCION_RESERVADA:
                    System.out.print("<" + token.tipo.name() + "> ");
                    break;
                case BOOLEANO:
                case CHAR:
                case LITERAL:
                case AGRUPADOR:
                    System.out.print("<" + token.valor + "> ");
                    break;
                default:
                    System.out.print("<" + token.tipo.name() + "> ");
            }
        }
        System.out.println();
    }
}