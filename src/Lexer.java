package src;

import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

enum TokenType {
    OPERADOR, OPERADOR_COMPARACION, OPERADOR_LOGICO, AGRUPADOR, NUMERO, IDENTIFICADOR,
    PALABRA_RESERVADA, TIPO_DATO, BOOLEANO, CHAR, LITERAL, PUNTO_Y_COMA, FUNCION_RESERVADA, ERROR
}

class LexicalError {
    private int linea;
    private int columna;
    private String mensaje;

    public LexicalError(int linea, int columna, String mensaje) {
        this.linea = linea;
        this.columna = columna;
        this.mensaje = mensaje;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return String.format("Error en línea %d, columna %d: %s", linea, columna, mensaje);
    }
}

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
    private static final String IDENTIFICADOR = "[a-zA-Z_][a-zA-Z0-9_]*";

    private static final Pattern PATRON = Pattern.compile(
            OPERADORES_COMPARACION + "|" + OPERADORES_LOGICOS + "|" + OPERADORES_ARITMETICOS + "|" +
                    OPERADOR_ASIGNACION + "|" + AGRUPADORES + "|" + DOUBLE_NUMERO + "|" + INT_NUMERO + "|" +
                    BOOLEANO + "|" + CHAR + "|" + STRING + "|" + PUNTO_Y_COMA_REGEX + "|" + IDENTIFICADOR + "|" + ESPACIO,
            Pattern.CASE_INSENSITIVE
    );

    private static final List<LexicalError> errores = new ArrayList<>();
    private static final Set<String> variablesDeclaradas = new HashSet<>();

    private static class ParInfo {
        int linea, columna;
        public ParInfo(int linea, int columna) {
            this.linea = linea;
            this.columna = columna;
        }
    }

    public static List<LexicalError> getErrores() {
        return errores;
    }

    public static List<Token> analizarTexto(String texto) {
        List<Token> tokens = new ArrayList<>();
        errores.clear();
        variablesDeclaradas.clear();

        boolean enComentarioMultilinea = false;
        int numeroLinea = 0;

        Deque<ParInfo> pilaLlaves = new ArrayDeque<>();
        Deque<ParInfo> pilaParentesis = new ArrayDeque<>();

        String[] lineas = texto.split("\n");

        for (String lineaTexto : lineas) {
            numeroLinea++;
            lineaTexto = lineaTexto.trim();

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
                    lineaTexto = lineaTexto.replaceAll("/\\*.*?\\*/", "").trim();
                } else {
                    enComentarioMultilinea = true;
                    lineaTexto = lineaTexto.substring(0, inicioComentario).trim();
                }
            }

            if (lineaTexto.contains("//")) {
                lineaTexto = lineaTexto.substring(0, lineaTexto.indexOf("//")).trim();
            }

            if (!lineaTexto.isEmpty()) {
                tokens.addAll(analizar(lineaTexto, numeroLinea, pilaLlaves, pilaParentesis));
            }
        }

        while (!pilaLlaves.isEmpty()) {
            ParInfo p = pilaLlaves.pop();
            errores.add(new LexicalError(p.linea, p.columna, "Llave `{` sin cerrar."));
        }
        while (!pilaParentesis.isEmpty()) {
            ParInfo p = pilaParentesis.pop();
            errores.add(new LexicalError(p.linea, p.columna, "Paréntesis `(` sin cerrar."));
        }

        return tokens;
    }

    private static List<Token> analizar(String input, int linea, Deque<ParInfo> pilaLlaves, Deque<ParInfo> pilaParentesis) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = PATRON.matcher(input);

        boolean esDeclaracion = false;

        while (matcher.find()) {
            String lexema = matcher.group().trim();
            int columna = matcher.start() + 1;

            if (lexema.isEmpty() || lexema.matches(ESPACIO)) continue;

            Token token = crearToken(lexema, linea, columna);
            tokens.add(token);

            if (token.tipo == TokenType.TIPO_DATO) {
                esDeclaracion = true;
            } else if (esDeclaracion && token.tipo == TokenType.IDENTIFICADOR) {
                variablesDeclaradas.add(token.valor);
                esDeclaracion = false;
            } else if (token.tipo == TokenType.PUNTO_Y_COMA) {
                esDeclaracion = false;
            }

            if (token.tipo == TokenType.IDENTIFICADOR && !variablesDeclaradas.contains(token.valor)) {
                errores.add(new LexicalError(linea, columna, "Variable \"" + token.valor + "\" no declarada antes de su uso."));
            }

            if (lexema.equals("{")) {
                pilaLlaves.push(new ParInfo(linea, columna));
            } else if (lexema.equals("}")) {
                if (pilaLlaves.isEmpty()) {
                    errores.add(new LexicalError(linea, columna, "Llave `}` sin `{` de apertura."));
                } else {
                    pilaLlaves.pop();
                }
            }

            if (lexema.equals("(")) {
                pilaParentesis.push(new ParInfo(linea, columna));
            } else if (lexema.equals(")")) {
                if (pilaParentesis.isEmpty()) {
                    errores.add(new LexicalError(linea, columna, "Paréntesis de cierre `)` sin `(` previo."));
                } else {
                    pilaParentesis.pop();
                }
            }
        }

        return tokens;
    }

    private static Token crearToken(String lexema, int linea, int columna) {
        String lexemaLower = lexema.toLowerCase();

        if (PALABRAS_RESERVADAS.contains(lexemaLower)) return new Token(TokenType.PALABRA_RESERVADA, lexema, linea, columna);
        if (TIPOS_DATO.contains(lexemaLower)) return new Token(TokenType.TIPO_DATO, lexema, linea, columna);
        if (FUNCIONES_RESERVADAS.contains(lexema)) return new Token(TokenType.FUNCION_RESERVADA, lexema, linea, columna);
        if (lexema.matches(OPERADORES_COMPARACION)) return new Token(TokenType.OPERADOR_COMPARACION, lexema, linea, columna);
        if (lexema.matches(OPERADORES_LOGICOS)) return new Token(TokenType.OPERADOR_LOGICO, lexema, linea, columna);
        if (lexema.matches(OPERADORES_ARITMETICOS)) return new Token(TokenType.OPERADOR, lexema, linea, columna);
        if (lexema.equals(OPERADOR_ASIGNACION)) return new Token(TokenType.OPERADOR, lexema, linea, columna);
        if (lexema.matches(AGRUPADORES)) return new Token(TokenType.AGRUPADOR, lexema, linea, columna);
        if (lexema.matches(PUNTO_Y_COMA_REGEX)) return new Token(TokenType.PUNTO_Y_COMA, lexema, linea, columna);
        if (lexema.matches(DOUBLE_NUMERO) || lexema.matches(INT_NUMERO)) return new Token(TokenType.NUMERO, lexema, linea, columna);
        if (lexema.matches(BOOLEANO)) return new Token(TokenType.BOOLEANO, lexema, linea, columna);
        if (lexema.matches(CHAR)) return new Token(TokenType.CHAR, lexema, linea, columna);
        if (lexema.matches(STRING)) return new Token(TokenType.LITERAL, lexema, linea, columna);
        if (lexema.matches(IDENTIFICADOR)) return new Token(TokenType.IDENTIFICADOR, lexema, linea, columna);

        errores.add(new LexicalError(linea, columna, "Token desconocido: \"" + lexema + "\"."));
        return new Token(TokenType.ERROR, lexema, linea, columna);
    }

    public static void imprimirTablaSimbolos(List<Token> tokens) {
        Set<String> vistos = new HashSet<>();
        int posicion = 1;

        System.out.printf("%-10s %-20s %-20s %-10s %-10s%n", "Posición", "Identificador", "Tipo de Token", "Línea", "Columna");

        for (Token token : tokens) {
            if (token.tipo == TokenType.IDENTIFICADOR && !vistos.contains(token.valor)) {
                System.out.printf("%-10d %-20s %-20s %-10d %-10d%n", posicion, token.valor, token.tipo, token.linea, token.columna);
                vistos.add(token.valor);
                posicion++;
            }
        }

        if (posicion == 1) {
            System.out.println("No se encontraron identificadores.");
        }
    }

}
