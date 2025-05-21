package src;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.Token;

public class CustomErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int charPositionInLine,
                            String msg,
                            RecognitionException e) {

        if (msg.contains("token recognition error")) {
           return;
        }
        else if (msg.contains("missing ';'")) {
            System.out.printf("[Error de sintaxis] Línea %d, columna %d: Falta punto y coma ';'%n", line, charPositionInLine);
        }
        else if (msg.contains("mismatched input")) {
            String simbolo = "?";
            String esperadoBruto = "";

            if (offendingSymbol instanceof Token) {
                simbolo = ((Token) offendingSymbol).getText();
            }

            // Extraer lo esperado
            if (msg.contains("expecting")) {
                int idx = msg.indexOf("expecting");
                esperadoBruto = msg.substring(idx + 10).replaceAll("[{}]", "").trim();
            }

            boolean esperabaAsignacion = esperadoBruto.contains("'=>'");

            System.out.printf("[Error de sintaxis] Línea %d, columna %d: Símbolo inesperado: '%s'", line, charPositionInLine, simbolo);
            if (esperabaAsignacion) {
                System.out.print(" (se esperaba: '=>')");
            }

            System.out.println();
        }
        else {
            System.out.printf("[Error de sintaxis] Línea %d, columna %d: %s%n", line, charPositionInLine, msg);
        }
    }


    private String traducirMensaje(String original) {
        if (original.contains("missing ';'")) return "Falta punto y coma ';'";
        if (original.contains("mismatched input")) return "Símbolo inesperado";
        if (original.contains("extraneous input")) return "Símbolo extra no esperado";
        return original;
    }
}