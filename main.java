import java.io.IOException;
import java.util.*;

public class main {
    public static void main(String[] args) {
        String archivo = "archivo.txt"; // archivo de entrada

        try {
            List<Token> tokensArchivo = Lexer.analizarArchivo(archivo);
            Map<String, Integer> idTablaSimbolos = new LinkedHashMap<>();
            Map<Integer, String> tokenLineas = new LinkedHashMap<>();
            int posicionTabla = 1;

            System.out.println("Tokens:");
            for (int i = 0; i < tokensArchivo.size(); i++) {
                Token token = tokensArchivo.get(i);

                // Guardar posición en tabla general para cada identificador
                if (token.tipo == TokenType.IDENTIFICADOR && !idTablaSimbolos.containsKey(token.valor)) {
                    idTablaSimbolos.put(token.valor, posicionTabla);
                }

                posicionTabla++;
            }

            // Reiniciar para imprimir tabla con posiciones reales
            posicionTabla = 1;
            

            // Construir línea de tokens con ID real basado en posición en tabla
            for (Token token : tokensArchivo) {
                if (token.tipo == TokenType.IDENTIFICADOR) {
                    System.out.print("< id," + idTablaSimbolos.get(token.valor) + " >");
                } else if (token.tipo == TokenType.NUMERO || token.tipo == TokenType.LITERAL) {
                    System.out.print("< " + token.valor + " >");
                } else {
                    System.out.print("< " + token.valor + " >");
                }
            }

            // Imprimir tabla
            System.out.println("\n\nposicion-----Identificador-------Tipo de Token--------Linea-------Columna");

            posicionTabla = 1;
            for (Token token : tokensArchivo) {
                System.out.printf("%-13d %-20s %-20s %-10d %-10d%n",
                        posicionTabla++,
                        token.valor,
                        token.tipo,
                        token.linea,
                        token.columna
                );
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Error en tiempo de ejecución: " + e.getMessage());
        }
    }
}
