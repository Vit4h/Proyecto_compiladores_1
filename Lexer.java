import java.util.*;
import java.util.regex.*;
import java.io.*;

// Enum para representar los diferentes tipos de tokens
enum TokenType {
    OPERADOR, AGRUPADOR, NUMERO, IDENTIFICADOR, PALABRA_RESERVADA, TIPO_DATO, BOOLEANO, CHAR, LITERAL, PUNTO_Y_COMA, DESCONOCIDO, OPERADOR_LOGICO
}

// Clase que representa un Token
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

// Analizador léxico con buffer
class Lexer {
    private static final String OPERADORES = "[+\\-*/^]";
    private static final String OPERADOR_ASIGNACION = "=";  
    private static final String OPERADORES_LOGICOS = "(\\*\\*|==|>=|<=|!=|::)";
    private static final String AGRUPADORES = "[(){}\\[\\]<>]";
    private static final String PUNTO_Y_COMA_REGEX = ";";
    private static final String ESPACIO = "\\s+";

    // Definición de tipos de datos
    private static final Set<String> TIPOS_DATO = Set.of("int", "double", "boolean", "char", "string");

    // Expresiones regulares para valores específicos
    private static final String INT_NUMERO = "-?(214748364[0-7]|21474836[0-3]\\d|2147483[0-5]\\d{2}|214748[0-2]\\d{3}|21474[0-7]\\d{4}|2147[0-3]\\d{5}|214[0-6]\\d{6}|21[0-3]\\d{7}|2[01]\\d{8}|1\\d{9}|\\d{1,9})(?=[^\\d.]|$)";
    private static final String DOUBLE_NUMERO = "-?\\d+\\.\\d+(?=[^\\d.]|$)"; 
    private static final String BOOLEANO = "true|false";
    private static final String CHAR = "'[^']'";  
    private static final String STRING = "\"[^\"]*\""; 
    private static final String IDENTIFICADOR = "[a-zA-Z_][a-zA-Z0-9_]*"; 

    // Expresiones regulares para comentarios
    private static final String COMENTARIO_SIMPLE = "//.*";  

    private static final Pattern PATRON = Pattern.compile(
        COMENTARIO_SIMPLE + "|" + OPERADORES_LOGICOS + "|" + OPERADOR_ASIGNACION + "|" + OPERADORES + "|" + 
        AGRUPADORES + "|" + DOUBLE_NUMERO + "|" + INT_NUMERO + "|" + BOOLEANO + "|" + CHAR + "|" + STRING + "|" +
        PUNTO_Y_COMA_REGEX + "|" + IDENTIFICADOR + "|" + ESPACIO,
        Pattern.CASE_INSENSITIVE
    );

    public static List<Token> analizar(String input) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = PATRON.matcher(input);

        while (matcher.find()) {
            String lexema = matcher.group().trim();

            if (lexema.isEmpty() || lexema.matches(ESPACIO) || lexema.matches(COMENTARIO_SIMPLE)) {
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
        } else if (lexema.matches(OPERADORES_LOGICOS)) {
            return new Token(TokenType.OPERADOR_LOGICO, lexema);
        } else if (lexema.matches(OPERADOR_ASIGNACION)) { 
            return new Token(TokenType.OPERADOR, lexema); 
        } else if (lexema.matches(OPERADORES)) {
            return new Token(TokenType.OPERADOR, lexema);
        } else if (lexema.matches(AGRUPADORES)) {
            return new Token(TokenType.AGRUPADOR, lexema);
        } else if (lexema.matches(PUNTO_Y_COMA_REGEX)) {
            return new Token(TokenType.PUNTO_Y_COMA, lexema);
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
