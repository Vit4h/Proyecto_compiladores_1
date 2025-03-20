import java.io.*;
import java.util.*;
import java.util.regex.*;

enum TokenType {
    OPERADOR, OPERADOR_COMPARACION, OPERADOR_LOGICO, AGRUPADOR, NUMERO, IDENTIFICADOR, 
    PALABRA_RESERVADA, TIPO_DATO, BOOLEANO, CHAR, LITERAL, PUNTO_Y_COMA, FUNCION_RESERVADA, ERROR
}

// Clase para errores léxicos
class LexicalError {
    int linea;
    int columna;
    String mensaje;

    public LexicalError(int linea, int columna, String mensaje) {
        this.linea = linea;
        this.columna = columna;
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return String.format("Error en línea %d, columna %d: %s", linea, columna, mensaje);
    }
}

// Clase Token con información de tipo, valor, línea y columna
class Token {
    TokenType tipo;
    String valor;
    int linea;
    int columna;

    public Token(TokenType tipo, String valor, int linea, int columna) {
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public String toString() {
        return String.format("%-15s %-20s %-5d %-5d", valor, tipo, linea, columna);
    }
}

class Lexer {
    private static final Set<String> PALABRAS_RESERVADAS = Set.of("if", "else", "for", "while", "do");
    private static final Set<String> TIPOS_DATO = Set.of("int", "double", "boolean", "char", "string");
    private static final Set<String> FUNCIONES_RESERVADAS = Set.of("EscribirLinea", "Escribir", "Longitud", "aCadena");

    private static final String OPERADORES_COMPARACION = "(==|>=|<=|!=|>|<)";
    private static final String OPERADORES_LOGICOS = "(\\|\\||&&|!)";
    private static final String OPERADORES_ARITMETICOS = "(\\+\\+|--|\\+=|-=|\\*=|/=|\\+|-|\\*|/|\\^|#)";
    private static final String OPERADOR_ASIGNACION = "=";
    private static final String AGRUPADORES = "[(){}]";
    private static final String PUNTO_Y_COMA_REGEX = ";";
    private static final String ESPACIO = "\\s+";

    private static final String INT_NUMERO = "-?\\d+";
    private static final String DOUBLE_NUMERO = "-?\\d+(\\.\\d+)?";
    private static final String BOOLEANO = "true|false";
    private static final String CHAR = "'([^'\\\\]|\\\\.)'";
    private static final String STRING = "\"([^\"\\\\]|\\\\.)*\"";

    // Expresión regular mejorada para identificadores
    private static final String IDENTIFICADOR = "[a-zA-Z_][a-zA-Z0-9_]*";

    private static final Pattern PATRON = Pattern.compile(
        OPERADORES_COMPARACION + "|" + OPERADORES_LOGICOS + "|" + OPERADORES_ARITMETICOS + "|" +
        OPERADOR_ASIGNACION + "|" + AGRUPADORES + "|" + DOUBLE_NUMERO + "|" + INT_NUMERO + "|" + 
        BOOLEANO + "|" + CHAR + "|" + STRING + "|" + PUNTO_Y_COMA_REGEX + "|" + IDENTIFICADOR + "|" + ESPACIO,
        Pattern.CASE_INSENSITIVE
    );

    private static final List<LexicalError> errores = new ArrayList<>();
    private static final Set<String> variablesDeclaradas = new HashSet<>();

    // Método público para obtener los errores
    public static List<LexicalError> getErrores() {
        return errores;
    }

    // Método para analizar texto directamente
    public static List<Token> analizarTexto(String texto) {
        List<Token> tokens = new ArrayList<>();
        errores.clear(); // Limpiar errores anteriores
        variablesDeclaradas.clear();
    
        boolean enComentarioMultilinea = false;
        int numeroLinea = 0;
    
        String[] lineas = texto.split("\n");
        for (String lineaTexto : lineas) {
            numeroLinea++;
            lineaTexto = lineaTexto.trim();
    
            // Manejo de comentarios
            if (enComentarioMultilinea) {
                if (lineaTexto.contains("*/")) {
                    enComentarioMultilinea = false;
                    lineaTexto = lineaTexto.substring(lineaTexto.indexOf("*/") + 2).trim();
                } else {
                    continue;
                }
            }
    
            if (lineaTexto.contains("/*")) {
                int inicioComentario = lineaTexto.indexOf("/*");
                if (lineaTexto.contains("*/")) { 
                    lineaTexto = lineaTexto.substring(0, inicioComentario).trim();
                } else {
                    enComentarioMultilinea = true;
                    lineaTexto = lineaTexto.substring(0, inicioComentario).trim();
                }
            }
    
            if (lineaTexto.contains("//")) {
                lineaTexto = lineaTexto.substring(0, lineaTexto.indexOf("//")).trim();
            }
    
            if (!lineaTexto.isEmpty()) {
                tokens.addAll(analizar(lineaTexto, numeroLinea));
            }
        }
    
        // Si hay errores, devolver null
        if (!errores.isEmpty()) {
            return null; // No generar tokens si hay errores
        }
    
        return tokens;
    }

    // Método para analizar una línea de texto
    private static List<Token> analizar(String input, int linea) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = PATRON.matcher(input);

        boolean esDeclaracion = false;
        String tipoDatoActual = null;

        while (matcher.find()) {
            String lexema = matcher.group().trim();
            int columna = matcher.start() + 1;

            if (lexema.isEmpty() || lexema.matches(ESPACIO)) continue;

            Token token = crearToken(lexema, linea, columna);
            tokens.add(token);

            // Identificar si es una declaración de variable
            if (token.tipo == TokenType.TIPO_DATO) {
                esDeclaracion = true; // Estamos en una declaración de variable
                tipoDatoActual = token.valor;
            } else if (esDeclaracion && token.tipo == TokenType.IDENTIFICADOR) {
                // Si estamos en una declaración y encontramos un identificador, es una variable declarada
                variablesDeclaradas.add(token.valor);
                esDeclaracion = false; // Reiniciar el estado de declaración
            } else if (token.tipo == TokenType.PUNTO_Y_COMA) {
                // Reiniciar el estado de declaración al encontrar un punto y coma
                esDeclaracion = false;
                tipoDatoActual = null;
            }

            // Verificar si se usa una variable sin declararse
            if (token.tipo == TokenType.IDENTIFICADOR && !variablesDeclaradas.contains(token.valor)) {
                errores.add(new LexicalError(linea, columna, "Variable \"" + token.valor + "\" no declarada antes de su uso."));
            }
        }

        return tokens;
    }

    // Método para crear un token
    private static Token crearToken(String lexema, int linea, int columna) {
        String lexemaLower = lexema.toLowerCase();

        // Verificar si es una palabra reservada
        if (PALABRAS_RESERVADAS.contains(lexemaLower)) {
            return new Token(TokenType.PALABRA_RESERVADA, lexema, linea, columna);
        }

        // Verificar si es un tipo de dato
        if (TIPOS_DATO.contains(lexemaLower)) {
            return new Token(TokenType.TIPO_DATO, lexema, linea, columna);
        }

        // Verificar si es una función reservada
        if (FUNCIONES_RESERVADAS.contains(lexema)) {
            return new Token(TokenType.FUNCION_RESERVADA, lexema, linea, columna);
        }

        // Verificar si es un operador de comparación
        if (lexema.matches(OPERADORES_COMPARACION)) {
            return new Token(TokenType.OPERADOR_COMPARACION, lexema, linea, columna);
        }

        // Verificar si es un operador lógico
        if (lexema.matches(OPERADORES_LOGICOS)) {
            return new Token(TokenType.OPERADOR_LOGICO, lexema, linea, columna);
        }

        // Verificar si es un operador aritmético
        if (lexema.matches(OPERADORES_ARITMETICOS)) {
            return new Token(TokenType.OPERADOR, lexema, linea, columna);
        }

        // Verificar si es un operador de asignación
        if (lexema.equals(OPERADOR_ASIGNACION)) {
            return new Token(TokenType.OPERADOR, lexema, linea, columna);
        }

        // Verificar si es un agrupador
        if (lexema.matches(AGRUPADORES)) {
            return new Token(TokenType.AGRUPADOR, lexema, linea, columna);
        }

        // Verificar si es un punto y coma
        if (lexema.matches(PUNTO_Y_COMA_REGEX)) {
            return new Token(TokenType.PUNTO_Y_COMA, lexema, linea, columna);
        }

        // Verificar si es un número entero
        if (lexema.matches(INT_NUMERO)) {
            return new Token(TokenType.NUMERO, lexema, linea, columna);
        }

        // Verificar si es un número decimal
        if (lexema.matches(DOUBLE_NUMERO)) {
            return new Token(TokenType.NUMERO, lexema, linea, columna);
        }

        // Verificar si es un booleano
        if (lexema.matches(BOOLEANO)) {
            return new Token(TokenType.BOOLEANO, lexema, linea, columna);
        }

        // Verificar si es un carácter
        if (lexema.matches(CHAR)) {
            return new Token(TokenType.CHAR, lexema, linea, columna);
        }

        // Verificar si es una cadena
        if (lexema.matches(STRING)) {
            return new Token(TokenType.LITERAL, lexema, linea, columna);
        }

        // Verificar si es un identificador
        if (lexema.matches(IDENTIFICADOR)) {
            return new Token(TokenType.IDENTIFICADOR, lexema, linea, columna);
        }

        // Si no coincide con ningún patrón, es un token desconocido
        errores.add(new LexicalError(linea, columna, "Token desconocido: \"" + lexema + "\"."));
        return new Token(TokenType.ERROR, lexema, linea, columna);
    }
}