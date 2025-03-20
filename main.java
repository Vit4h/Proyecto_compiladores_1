import java.io.IOException;
import java.util.List;

public class main {
  public static void main(String[] args) {
      try {
        System.out.println("Identificador-------Tipo de Token--------Linea-------Columna");
          List<Token> tokensArchivo = Lexer.analizarArchivo("archivo.txt");
          for (Token token : tokensArchivo) {
              System.out.println(token);
          }
      } catch (IOException e) {
          System.err.println("Error al leer el archivo: " + e.getMessage());
      } catch (RuntimeException e) {
          System.err.println(e.getMessage());
      }
  }
}