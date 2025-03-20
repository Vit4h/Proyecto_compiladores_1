import java.io.*;
import java.util.*;
import java.util.regex.*;

enum TokenType {
    OPERADOR, OPERADOR_COMPARACION, OPERADOR_LOGICO, AGRUPADOR, NUMERO, IDENTIFICADOR, 
    PALABRA_RESERVADA, TIPO_DATO, BOOLEANO, CHAR, LITERAL, PUNTO_Y_COMA, FUNCION_RESERVADA, ERROR
}

// Clase para errores léxicos y semánticos
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

    public static List<Token> analizarArchivo(String ruta) throws IOException {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            throw new FileNotFoundException("El archivo no existe: " + ruta);
        }

        List<Token> tokens = new ArrayList<>();
        errores.clear();
        variablesDeclaradas.clear();

        boolean enComentarioMultilinea = false;
        int numeroLinea = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String lineaTexto;
            while ((lineaTexto = br.readLine()) != null) {
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
        }

        // Imprimir errores al final del análisis
        if (!errores.isEmpty()) {
            System.out.println("\nErrores Encontrados:");
            for (LexicalError error : errores) {
                System.out.println(error);
            }
        }

        return tokens;
    }

    private static List<Token> analizar(String input, int linea) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = PATRON.matcher(input);

        boolean esDeclaracion = false;

        boolean esperandoCondicion = false;
        boolean esperandoBloque = false;
        boolean ifPresente = false;
        boolean elseEsperado = false;
        boolean forPresente = false;
        boolean doPresente = false;
        boolean whileEsperado = false;

        Deque<String> pilaLlaves = new ArrayDeque<>(); // Rastrea llaves {}

        while (matcher.find()) {
            String lexema = matcher.group().trim();
            int columna = matcher.start() + 1;

            if (lexema.isEmpty() || lexema.matches(ESPACIO)) continue;

            Token token = crearToken(lexema, linea, columna);
            tokens.add(token);

            // Detectar declaraciones de variables correctamente
            if (token.tipo == TokenType.TIPO_DATO) {
                esDeclaracion = true;
            } else if (esDeclaracion && token.tipo == TokenType.IDENTIFICADOR) {
                variablesDeclaradas.add(token.valor);
            } else if (token.tipo == TokenType.PUNTO_Y_COMA) {
                esDeclaracion = false;
            }
            

            // Verificar si se usa una variable sin declararse
            if (!esDeclaracion && token.tipo == TokenType.IDENTIFICADOR) {
                if (!variablesDeclaradas.contains(token.valor)) {
                    errores.add(new LexicalError(linea, columna, "Variable \"" + token.valor + "\" no declarada antes de su uso."));
                }
            }


            if (token.tipo == TokenType.PALABRA_RESERVADA) {
                if (lexema.equals("if")) {
                    ifPresente = true;
                    esperandoCondicion = true;
                } else if (lexema.equals("else")) {
                    if (!ifPresente) {
                        errores.add(new LexicalError(linea, columna, "`else` sin `if` previo."));
                    } else {
                        elseEsperado = true;
                    }
                }
            } else if (lexema.equals("(") && esperandoCondicion) {
                esperandoCondicion = false;
                esperandoBloque = true;
            } else if (lexema.equals(")")) {
                if (!esperandoBloque) {
                    errores.add(new LexicalError(linea, columna, "Paréntesis de cierre `)` sin `(` previo."));
                }
                esperandoBloque = false;
            } else if (lexema.equals("{")) {
                if (!ifPresente && !elseEsperado) {
                    errores.add(new LexicalError(linea, columna, "Bloque `{` sin `if` o `else` válido antes."));
                }
                pilaLlaves.push("{");
                ifPresente = false;
                elseEsperado = false;
            } else if (lexema.equals("}")) {
                if (pilaLlaves.isEmpty()) {
                    errores.add(new LexicalError(linea, columna, "Llave `}` sin `{` de apertura."));
                } else {
                    pilaLlaves.pop();
                }
            }
            
            // Manejo de la estructura del FOR
        if (token.tipo == TokenType.PALABRA_RESERVADA) {
            if (lexema.equals("for")) {
                forPresente = true;
                esperandoCondicion = true;
            } else if (lexema.equals("do")) {
                doPresente = true;
            } else if (lexema.equals("while") && doPresente) {
                whileEsperado = true;
            }
        } else if (lexema.equals("(") && esperandoCondicion) {
            esperandoCondicion = false;
            esperandoBloque = true;
        } else if (lexema.equals(")")) {
            if (!esperandoBloque) {
                errores.add(new LexicalError(linea, columna, "Paréntesis de cierre `)` sin `(` previo."));
            }
            esperandoBloque = false;
        } else if (lexema.equals("{")) {
            if (!forPresente && !doPresente) {
                errores.add(new LexicalError(linea, columna, "Bloque `{` sin `for` o `do` válido antes."));
            }
            pilaLlaves.push("{");
            forPresente = false;
            doPresente = false;
        } else if (lexema.equals("}")) {
            if (pilaLlaves.isEmpty()) {
                errores.add(new LexicalError(linea, columna, "Llave `}` sin `{` de apertura."));
            } else {
                pilaLlaves.pop();
            }
        } else if (lexema.equals(";") && whileEsperado) {
            whileEsperado = false;
        }

    }
          // Verificar si hay bloques `{}` sin cerrar
        if (!pilaLlaves.isEmpty()) {
        errores.add(new LexicalError(linea, 1, "Bloques `{}` sin cerrar."));
        }

        return tokens;
}

    private static Token crearToken(String lexema, int linea, int columna) {
        String lexemaLower = lexema.toLowerCase();

        if (TIPOS_DATO.contains(lexemaLower)) return new Token(TokenType.TIPO_DATO, lexema, linea, columna);
        if (FUNCIONES_RESERVADAS.contains(lexema)) return new Token(TokenType.FUNCION_RESERVADA, lexema, linea, columna);
        if (lexema.matches(DOUBLE_NUMERO) || lexema.matches(INT_NUMERO)) return new Token(TokenType.NUMERO, lexema, linea, columna);
        if (lexema.matches(BOOLEANO)) return new Token(TokenType.BOOLEANO, lexema, linea, columna);
        if (lexema.matches(CHAR)) return new Token(TokenType.CHAR, lexema, linea, columna);
        if (lexema.matches(STRING)) return new Token(TokenType.LITERAL, lexema, linea, columna);
        if (lexema.matches(OPERADORES_COMPARACION) || lexema.matches(OPERADORES_ARITMETICOS) || lexema.matches(OPERADOR_ASIGNACION))
            return new Token(TokenType.OPERADOR, lexema, linea, columna);
        if (lexema.matches(AGRUPADORES)) return new Token(TokenType.AGRUPADOR, lexema, linea, columna);
        if (lexema.matches(PUNTO_Y_COMA_REGEX)) return new Token(TokenType.PUNTO_Y_COMA, lexema, linea, columna);
        if (PALABRAS_RESERVADAS.contains(lexemaLower)) return new Token(TokenType.PALABRA_RESERVADA, lexema, linea, columna);
        if (lexema.matches(IDENTIFICADOR)) return new Token(TokenType.IDENTIFICADOR, lexema, linea, columna);

        errores.add(new LexicalError(linea, columna, "Identificador inválido o token desconocido: \"" + lexema + "\"."));
        return new Token(TokenType.ERROR, lexema, linea, columna);
    }
}
