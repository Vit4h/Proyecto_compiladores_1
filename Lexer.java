import java.util.*;
import java.util.regex.*;
import java.io.*;

enum TokenType {
    OPERADOR, OPERADOR_COMPARACION, OPERADOR_LOGICO, AGRUPADOR, NUMERO, IDENTIFICADOR, 
    PALABRA_RESERVADA, TIPO_DATO, BOOLEANO, CHAR, LITERAL, PUNTO_Y_COMA, IF, ELSE, ELSE_IF, 
    DESCONOCIDO
}

class Token {
    TokenType tipo;
    String valor;

    public Token(TokenType tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Token{tipo=" + tipo + ", valor='" + valor + "'}";
    }
}

class Lexer {
    // Palabras clave
    private static final Set<String> PALABRAS_RESERVADAS = Set.of("if", "else");

    // Expresiones regulares
    private static final String OPERADORES_COMPARACION = "(==|>=|<=|!=|>|<)";
    private static final String OPERADORES_LOGICOS = "(\\|\\||&&|!)";
    private static final String OPERADORES_ARITMETICOS = "(\\+\\+|--|\\+|\\-|\\*|\\/|\\^|#)";
    private static final String OPERADOR_ASIGNACION = "=";
    private static final String AGRUPADORES = "[(){}]";
    private static final String PUNTO_Y_COMA_REGEX = ";";
    private static final String ESPACIO = "\\s+";

    private static final Set<String> TIPOS_DATO = Set.of("int", "double", "boolean", "char", "string");

    private static final String INT_NUMERO = "-?\\d+";
    private static final String DOUBLE_NUMERO = "-?\\d+\\.\\d+"; 
    private static final String BOOLEANO = "true|false";
    private static final String CHAR = "'[^']'";
    private static final String STRING = "\"[^\"]*\"";
    private static final String IDENTIFICADOR = "[a-zA-Z_][a-zA-Z0-9_]*";

    private static final Pattern PATRON = Pattern.compile(
        OPERADORES_COMPARACION + "|" + OPERADORES_LOGICOS + "|" + OPERADORES_ARITMETICOS + "|" +
        OPERADOR_ASIGNACION + "|" + AGRUPADORES + "|" + DOUBLE_NUMERO + "|" + INT_NUMERO + "|" + 
        BOOLEANO + "|" + CHAR + "|" + STRING + "|" + PUNTO_Y_COMA_REGEX + "|" + IDENTIFICADOR + "|" + ESPACIO,
        Pattern.CASE_INSENSITIVE
    );

    public static List<Token> analizar(String input) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = PATRON.matcher(input);

        while (matcher.find()) {
            String lexema = matcher.group().trim();

            if (lexema.isEmpty() || lexema.matches(ESPACIO)) {
                continue;
            }

            tokens.add(crearToken(lexema));
        }
        return tokens;
    }

    private static Token crearToken(String lexema) {
        if (TIPOS_DATO.contains(lexema.toLowerCase())) {
            return new Token(TokenType.TIPO_DATO, lexema);
        } else if (lexema.matches(DOUBLE_NUMERO)) {
            return new Token(TokenType.NUMERO, lexema);
        } else if (lexema.matches(INT_NUMERO)) {
            return new Token(TokenType.NUMERO, lexema);
        } else if (lexema.matches(BOOLEANO)) {
            return new Token(TokenType.BOOLEANO, lexema);
        } else if (lexema.matches(CHAR)) {
            return new Token(TokenType.CHAR, lexema);
        } else if (lexema.matches(STRING)) {
            return new Token(TokenType.LITERAL, lexema);
        } else if (lexema.matches(OPERADORES_COMPARACION)) {
            return new Token(TokenType.OPERADOR_COMPARACION, lexema);
        } else if (lexema.matches(OPERADORES_LOGICOS)) {
            return new Token(TokenType.OPERADOR_LOGICO, lexema);
        } else if (lexema.matches(OPERADOR_ASIGNACION)) { 
            return new Token(TokenType.OPERADOR, lexema);
        } else if (lexema.matches(OPERADORES_ARITMETICOS)) {
            return new Token(TokenType.OPERADOR, lexema);
        } else if (lexema.matches(AGRUPADORES)) {
            return new Token(TokenType.AGRUPADOR, lexema);
        } else if (lexema.matches(PUNTO_Y_COMA_REGEX)) {
            return new Token(TokenType.PUNTO_Y_COMA, lexema);
        } else if (lexema.equals("if")) {
            return new Token(TokenType.IF, lexema);
        } else if (lexema.equals("else")) {
            return new Token(TokenType.ELSE, lexema);
        } else if (lexema.matches(IDENTIFICADOR)) {
            return new Token(TokenType.IDENTIFICADOR, lexema);
        } else {
            return new Token(TokenType.DESCONOCIDO, lexema);
        }
    }





    public static List<Token> analizarArchivo(String ruta) throws IOException {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            throw new FileNotFoundException("El archivo no existe: " + ruta);
        }

        List<Token> tokens = new ArrayList<>();
        boolean enComentarioMultilinea = false;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                // Manejo de comentarios de múltiples líneas
                if (enComentarioMultilinea) {
                    if (linea.contains("*/")) {
                        enComentarioMultilinea = false;
                        linea = linea.substring(linea.indexOf("*/") + 2).trim();
                    } else {
                        continue;
                    }
                }

                if (linea.contains("/*")) {
                    int inicioComentario = linea.indexOf("/*");
                    if (linea.contains("*/")) { 
                        linea = linea.substring(0, inicioComentario).trim();
                    } else {
                        enComentarioMultilinea = true;
                        linea = linea.substring(0, inicioComentario).trim();
                    }
                }

                if (linea.contains("//")) {
                    linea = linea.substring(0, linea.indexOf("//")).trim();
                }

                if (!linea.isEmpty()) {
                    tokens.addAll(analizar(linea));
                }
            }
        }
        return tokens;
    }
}
