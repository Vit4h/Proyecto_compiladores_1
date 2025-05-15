import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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

            if (!Lexer.getErrores().isEmpty()) {
                System.out.println("Se encontraron errores léxicos:");
                for (LexicalError error : Lexer.getErrores()) {
                    System.out.println(error);
                }
            }

            // Siempre imprimir tokens
            if (tokens != null) {
                System.out.println("\nTokens generados:");
                imprimirTokensCompilador(tokens);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: El archivo 'archivo.txt' no se encontró.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    // Imprimir tokens en formato <TIPO,valor> o <TIPO> cuando aplique
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

}
