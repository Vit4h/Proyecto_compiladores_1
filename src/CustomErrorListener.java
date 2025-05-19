package src;
import org.antlr.v4.runtime.*;

public class CustomErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int charPositionInLine,
                            String msg,
                            RecognitionException e) {

        String mensaje = String.format(
                "[Error de sintaxis] Línea %d, columna %d: %s",
                line,
                charPositionInLine,
                traducirMensaje(msg)
        );
        System.out.println(mensaje);
    }

    private String traducirMensaje(String original) {
        if (original.contains("missing ';'")) return "Falta punto y coma ';'";
        if (original.contains("mismatched input")) return "Símbolo inesperado";
        if (original.contains("extraneous input")) return "Símbolo extra no esperado";
        return original;
    }
}