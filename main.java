import java.util.List;

public class main {
  public static void main(String[] args) {
      String input = "Abc = 33*sum*-1.00;If (abc == 90) return “si”; else return “no”;";
      List<Token> tokens = Lexer.analizar(input);
      tokens.forEach(System.out::println);
  }
}
