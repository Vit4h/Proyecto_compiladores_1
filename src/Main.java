package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
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

            List<Token> tokens = Lexer.analizarTexto(contenido.toString());

            // Mostrar errores léxicos
            if (!Lexer.getErrores().isEmpty()) {
                System.out.println("Se encontraron errores léxicos:");
                for (LexicalError error : Lexer.getErrores()) {
                    System.out.println(error);
                }
                System.out.println("\nEjecución detenida. No se realizará análisis sintáctico.");
            }

            // Siempre imprimir tokens
            if (tokens != null && !tokens.isEmpty()) {
                System.out.println("\nTokens generados:");
                imprimirTokensCompilador(tokens);

                System.out.println("\nTabla de símbolos:");
                imprimirTablaSimbolos(tokens);
            }

            // Solo continuar con ANTLR si no hay errores léxicos
            if (Lexer.getErrores().isEmpty()) {
                System.out.println("\n[ANÁLISIS SINTÁCTICO Y EJECUCIÓN CON ANTLR]");
                CharStream input = CharStreams.fromString(contenido.toString());

                AlgebraLexer antlrLexer = new AlgebraLexer(input);
                CommonTokenStream antlrTokens = new CommonTokenStream(antlrLexer);
                AlgebraParser parser = new AlgebraParser(antlrTokens);

                parser.removeErrorListeners();
                parser.addErrorListener(new CustomErrorListener());

                ParseTree tree = parser.program();  // punto de entrada

                System.out.println("\nÁrbol de sintaxis:");
                System.out.println(tree.toStringTree(parser));

                // Ejecutar con visitor
                AlgebraEvaluatorVisitor visitor = new AlgebraEvaluatorVisitor();
                visitor.visit(tree);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: El archivo 'archivo.txt' no se encontró.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Imprimir tokens en formato de compilador
    private static void imprimirTokensCompilador(List<Token> tokens) {
        if (tokens == null || tokens.isEmpty()) {
            System.out.println("No hay tokens para mostrar.");
            return;
        }

        for (Token token : tokens) {
            switch (token.tipo) {
                case IDENTIFICADOR:
                    System.out.print("<ID," + token.valor + ">");
                    break;
                case NUMERO:
                    System.out.print("<NUM," + token.valor + ">");
                    break;
                case OPERADOR:
                case OPERADOR_COMPARACION:
                case OPERADOR_LOGICO:
                    System.out.print("<" + token.valor + ">");
                    break;
                case PUNTO_Y_COMA:
                    System.out.print("<TERMINACION>");
                    break;
                case TIPO_DATO:
                case PALABRA_RESERVADA:
                case FUNCION_RESERVADA:
                    System.out.print("<" + token.tipo.name() + ">");
                    break;
                case BOOLEANO:
                case CHAR:
                case LITERAL:
                case AGRUPADOR:
                    System.out.print("<" + token.valor + ">");
                    break;
                default:
                    System.out.print("<" + token.tipo.name() + ">");
            }
        }
        System.out.println(); // salto de línea final
    }

    // Imprimir tabla de símbolos
    private static void imprimirTablaSimbolos(List<Token> tokens) {
        List<Token> identificadores = new ArrayList<>();
        Set<String> vistos = new HashSet<>();
        int posicion = 1;

        System.out.printf("%-10s %-20s %-20s %-10s %-10s%n", "Posición", "Identificador", "Tipo de Token", "Línea", "Columna");

        for (Token token : tokens) {
            if (token.tipo == TokenType.IDENTIFICADOR && !vistos.contains(token.valor)) {
                System.out.printf("%-10d %-20s %-20s %-10d %-10d%n", posicion, token.valor, token.tipo, token.linea, token.columna);
                vistos.add(token.valor);
                posicion++;
            }
        }

        if (posicion == 1) {
            System.out.println("No se encontraron identificadores.");
        }
    }
}
