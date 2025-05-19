import java.io.IOException;
import java.util.*;

public class main {
    public static void main(String[] args) {
        String archivo = "archivo.txt";

        try {
            List<Token> tokensArchivo = Lexer.analizarArchivo(archivo);

            Map<String, Token> simbolos = new LinkedHashMap<>();
            List<Token> tablaTokens = new ArrayList<>();

            // Registrar identificadores únicos en tabla de símbolos
            for (Token token : tokensArchivo) {
                if (token.tipo == TokenType.IDENTIFICADOR && !simbolos.containsKey(token.valor)) {
                    simbolos.put(token.valor, token);
                }
                tablaTokens.add(token);
            }

            // 🔹 PARTE 1: Línea de tokens con formato especial
            System.out.println("Tokens:");
            for (Token token : tokensArchivo) {
                switch (token.tipo) {
                    case IDENTIFICADOR:
                        int idPos = new ArrayList<>(simbolos.keySet()).indexOf(token.valor) + 1;
                        System.out.print("<id," + idPos + ">");
                        break;
                    case NUMERO:
                        System.out.print("<num," + token.valor + ">");
                        break;
                    case LITERAL:
                        System.out.print("<str," + token.valor + ">");
                        break;
                    default:
                        System.out.print("<" + token.valor + ">");
                }
            }

            // 🔹 PARTE 2: Tabla general de tokens
            System.out.println("\n\nposicion-----Identificador-------Tipo de Token--------Linea-------Columna");
            int index = 1;
            for (Token token : tokensArchivo) {
                System.out.printf("%-13d %-20s %-20s %-10d %-10d%n",
                        index++,
                        token.valor,
                        token.tipo,
                        token.linea,
                        token.columna
                );
            }

            // 🔹 PARTE 3: Tabla de símbolos (solo identificadores)
            System.out.println("\nTabla de Símbolos:");
            System.out.println("posicion-----Identificador-------Tipo de Token--------Linea-------Columna");

            int simboloIndex = 1;
            for (Map.Entry<String, Token> entry : simbolos.entrySet()) {
                Token token = entry.getValue();
                System.out.printf("%-13d %-20s %-20s %-10d %-10d%n",
                        simboloIndex++,
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
