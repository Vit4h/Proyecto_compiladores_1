package src;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.Token;

import java.util.List;
import java.util.Map;

public class CustomErrorListener extends BaseErrorListener {

    private final List<Map<String, Object>> errores; // Lista externa para acumular errores

    public CustomErrorListener(List<Map<String, Object>> errores) {
        this.errores = errores;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line,
                            int charPositionInLine,
                            String msg,
                            RecognitionException e) {

        // Ignorar errores léxicos si los manejas por separado
        if (msg.contains("token recognition error")) {
            return;
        }

        String simbolo = "?";
        if (offendingSymbol instanceof Token token) {
            simbolo = token.getText();
        }

        String mensajeTraducido = traducirMensaje(msg, simbolo, line, charPositionInLine);
        System.out.println(mensajeTraducido); // sigue saliendo en consola

        errores.add(Map.of(
                "linea", line,
                "columna", charPositionInLine,
                "mensaje", mensajeTraducido
        ));
    }

    private String traducirMensaje(String original, String simbolo, int line, int col) {
        if (original.contains("mismatched input")) {
            if (original.contains("'=>")) {
                return String.format("Línea %d, columna %d: Símbolo inesperado: '%s' (se esperaba: '=>')", line, col, simbolo);
            } else {
                return String.format("Línea %d, columna %d: Símbolo inesperado: '%s'", line, col, simbolo);
            }
        }

        if (original.contains("missing ';'")) {
            return String.format("Línea %d, columna %d: Falta punto y coma ';'", line, col);
        }

        if (original.contains("extraneous input")) {
            return String.format("Línea %d, columna %d: Símbolo extra no esperado: '%s'", line, col, simbolo);
        }

        // Genérico
        return String.format("Línea %d, columna %d: %s", line, col, original);
    }
}