import java.util.*;
import java.util.regex.*;

public class Lexer {
    // Definición de tipos de tokens
    public enum TokenType {
        OPERADOR, OPERADOR_COMPARACION, OPERADOR_LOGICO, AGRUPADOR, 
        NUMERO, IDENTIFICADOR, PALABRA_RESERVADA, TIPO_DATO, 
        BOOLEANO, CHAR, LITERAL, PUNTO_Y_COMA, FUNCION_RESERVADA, ERROR
    }

    // Clase para tokens
    public static class Token {
        public final TokenType tipo;
        public final String valor;
        public final int linea;
        public final int columna;

        public Token(TokenType tipo, String valor, int linea, int columna) {
            this.tipo = tipo;
            this.valor = valor;
            this.linea = linea;
            this.columna = columna;
        }

        @Override
        public String toString() {
            switch(tipo) {
                case NUMERO: return "<num," + valor + ">";
                case IDENTIFICADOR: return "<id," + valor + ">";
                case OPERADOR: 
                case OPERADOR_COMPARACION: 
                case OPERADOR_LOGICO: return "<op," + valor + ">";
                case AGRUPADOR: return "<agrup," + valor + ">";
                case PUNTO_Y_COMA: return "<ptcoma," + valor + ">";
                case TIPO_DATO: return "<tipo," + valor + ">";
                case PALABRA_RESERVADA: return "<palres," + valor + ">";
                case FUNCION_RESERVADA: return "<func," + valor + ">";
                case BOOLEANO: return "<bool," + valor + ">";
                case CHAR: return "<char," + valor + ">";
                case LITERAL: return "<lit," + valor + ">";
                default: return "<error," + valor + ">";
            }
        }
    }

    // Clase para errores léxicos
    public static class LexicalError {
        public final int linea;
        public final int columna;
        public final String mensaje;

        public LexicalError(int linea, int columna, String mensaje) {
            this.linea = linea;
            this.columna = columna;
            this.mensaje = mensaje;
        }

        @Override
        public String toString() {
            return String.format("Línea %d, Columna %d: %s", linea, columna, mensaje);
        }
    }

    // Palabras reservadas
    private static final Set<String> PALABRAS_RESERVADAS = Set.of(
        "if", "else", "for", "while", "do"
    );
    
    // Tipos de dato
    private static final Set<String> TIPOS_DATO = Set.of(
        "int", "double", "boolean", "char", "string"
    );
    
    // Funciones reservadas
    private static final Set<String> FUNCIONES_RESERVADAS = Set.of(
        "EscribirLinea", "Escribir", "Longitud", "aCadena"
    );
    
    // Patrones regex
    private static final String OPERADORES_ARIT = "[+\\-*/%]";
    private static final String OPERADORES_ASIG = "=";
    private static final String OPERADORES_COMP = "==|>=|<=|!=|>|<";
    private static final String OPERADORES_LOG = "&&|\\|\\||!";
    private static final String OPERADORES_INC_DEC = "\\+\\+|--";
    private static final String AGRUPADORES = "[\\[\\]{}()]";
    private static final String PUNTO_Y_COMA = ";";
    private static final String NUMERO = "\\b\\d+(\\.\\d+)?\\b";
    private static final String CADENA = "\"[^\"]*\"";
    private static final String CARACTER = "'[^']'";
    private static final String IDENTIFICADOR = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final String COMENTARIO_LINEA = "//.*";
    private static final String COMENTARIO_BLOQUE = "/\\*.*?\\*/";
    private static final String ESPACIOS = "\\s+";
    
    // Patrón completo
    private static final Pattern PATRON = Pattern.compile(
        String.format("(%s)|(%s)|(%s)|(%s)|(%s)|(%s)|(%s)|(%s)|(%s)|(%s)|(%s)",
            OPERADORES_INC_DEC,
            OPERADORES_ARIT,
            OPERADORES_ASIG,
            OPERADORES_COMP,
            OPERADORES_LOG,
            AGRUPADORES,
            PUNTO_Y_COMA,
            NUMERO,
            CADENA,
            CARACTER,
            IDENTIFICADOR
        ),
        Pattern.DOTALL
    );

    // Método principal de análisis
    public static Map<String, Object> analizarCodigo(String codigo) {
        List<Token> tokens = new ArrayList<>();
        List<LexicalError> errores = new ArrayList<>();
        Set<String> variablesDeclaradas = new HashSet<>();
        
        // Eliminar comentarios primero
        codigo = codigo.replaceAll(COMENTARIO_BLOQUE, "")
                      .replaceAll(COMENTARIO_LINEA, "");
        
        String[] lineas = codigo.split("\n");
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();
            if (linea.isEmpty()) continue;
            
            Matcher matcher = PATRON.matcher(linea);
            int posicion = 0;
            boolean esDeclaracion = false;
            String tipoDatoActual = null;

            while (matcher.find(posicion)) {
                if (matcher.start() > posicion) {
                    // Hay texto no reconocido entre tokens
                    String lexemaError = linea.substring(posicion, matcher.start()).trim();
                    if (!lexemaError.isEmpty()) {
                        errores.add(new LexicalError(
                            i + 1, 
                            posicion + 1, 
                            "Token desconocido: '" + lexemaError + "'"
                        ));
                        // Guardamos tokens válidos hasta ahora
                        Map<String, Object> resultado = new HashMap<>();
                        resultado.put("errores", errores);
                        resultado.put("tokensParciales", new ArrayList<>(tokens));
                        return resultado;
                    }
                }

                String lexema = matcher.group().trim();
                if (lexema.isEmpty()) {
                    posicion = matcher.end();
                    continue;
                }

                Token token = crearToken(lexema, i + 1, matcher.start() + 1);
                
                if (token.tipo == TokenType.ERROR) {
                    errores.add(new LexicalError(
                        i + 1, 
                        matcher.start() + 1, 
                        "Token no válido: '" + lexema + "'"
                    ));
                    // Guardamos tokens válidos hasta ahora
                    Map<String, Object> resultado = new HashMap<>();
                    resultado.put("errores", errores);
                    resultado.put("tokensParciales", new ArrayList<>(tokens));
                    return resultado;
                }

                tokens.add(token);
                posicion = matcher.end();

                // Manejo de declaración de variables
                if (token.tipo == TokenType.TIPO_DATO) {
                    esDeclaracion = true;
                    tipoDatoActual = token.valor;
                } else if (esDeclaracion && token.tipo == TokenType.IDENTIFICADOR) {
                    variablesDeclaradas.add(token.valor);
                    esDeclaracion = false;
                } else if (token.tipo == TokenType.PUNTO_Y_COMA) {
                    esDeclaracion = false;
                    tipoDatoActual = null;
                }
            }

            // Verificar si quedó texto sin procesar al final de la línea
            if (posicion < linea.length()) {
                String lexemaError = linea.substring(posicion).trim();
                if (!lexemaError.isEmpty()) {
                    errores.add(new LexicalError(
                        i + 1, 
                        posicion + 1, 
                        "Token desconocido: '" + lexemaError + "'"
                    ));
                    // Guardamos tokens válidos hasta ahora
                    Map<String, Object> resultado = new HashMap<>();
                    resultado.put("errores", errores);
                    resultado.put("tokensParciales", new ArrayList<>(tokens));
                    return resultado;
                }
            }
        }

        Map<String, Object> resultado = new HashMap<>();
        if (!errores.isEmpty()) {
            resultado.put("errores", errores);
            resultado.put("tokensParciales", tokens);
        } else {
            resultado.put("tokens", tokens);
        }
        return resultado;
    }

    private static Token crearToken(String lexema, int linea, int columna) {
        String lexemaLower = lexema.toLowerCase();

        // Palabras reservadas
        if (PALABRAS_RESERVADAS.contains(lexemaLower)) {
            return new Token(TokenType.PALABRA_RESERVADA, lexema, linea, columna);
        }

        // Tipos de dato
        if (TIPOS_DATO.contains(lexemaLower)) {
            return new Token(TokenType.TIPO_DATO, lexema, linea, columna);
        }

        // Funciones reservadas
        if (FUNCIONES_RESERVADAS.contains(lexema)) {
            return new Token(TokenType.FUNCION_RESERVADA, lexema, linea, columna);
        }

        // Operadores
        if (lexema.matches(OPERADORES_INC_DEC)) {
            return new Token(TokenType.OPERADOR, lexema, linea, columna);
        }
        if (lexema.matches(OPERADORES_ARIT)) {
            return new Token(TokenType.OPERADOR, lexema, linea, columna);
        }
        if (lexema.matches(OPERADORES_ASIG)) {
            return new Token(TokenType.OPERADOR, lexema, linea, columna);
        }
        if (lexema.matches(OPERADORES_COMP)) {
            return new Token(TokenType.OPERADOR_COMPARACION, lexema, linea, columna);
        }
        if (lexema.matches(OPERADORES_LOG)) {
            return new Token(TokenType.OPERADOR_LOGICO, lexema, linea, columna);
        }

        // Agrupadores
        if (lexema.matches(AGRUPADORES)) {
            return new Token(TokenType.AGRUPADOR, lexema, linea, columna);
        }

        // Punto y coma
        if (lexema.equals(PUNTO_Y_COMA)) {
            return new Token(TokenType.PUNTO_Y_COMA, lexema, linea, columna);
        }

        // Números
        if (lexema.matches(NUMERO)) {
            return new Token(TokenType.NUMERO, lexema, linea, columna);
        }

        // Cadenas
        if (lexema.matches(CADENA)) {
            return new Token(TokenType.LITERAL, lexema, linea, columna);
        }

        // Caracteres
        if (lexema.matches(CARACTER)) {
            return new Token(TokenType.CHAR, lexema, linea, columna);
        }

        // Identificadores
        if (lexema.matches(IDENTIFICADOR)) {
            return new Token(TokenType.IDENTIFICADOR, lexema, linea, columna);
        }

        // Si no coincide con ningún patrón
        return new Token(TokenType.ERROR, lexema, linea, columna);
    }
}