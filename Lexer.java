import java.util.*;
import java.util.regex.*;
import java.io.*;

// Enum para representar los diferentes tipos de tokens
enum TokenType {
    OPERADOR, AGRUPADOR, NUMERO, IDENTIFICADOR, PALABRA_RESERVADA, DESCONOCIDO, OPERADOR_LOGICO, LITERAL
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
        return "Token{" + "tipo =" + tipo + ", valor='" + valor + "'}";
    }
}

// Analizador léxico con buffer
class Lexer {
    private static final String OPERADORES = "[+\\-*/^]";
    private static final String OPERADORES_LOGICOS = "(\\*\\*|==|>=|<=|!=|::|=)";
    private static final String AGRUPADORES = "[(){}\\[\\]<>]"; 
    private static final String NUMERO = "\\d+(\\.\\d+)?";
    private static final String IDENTIFICADOR = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final Set<String> PALABRAS_RESERVADAS = Set.of("cuadrado", "if", "else", "return", "while", "for");
    private static final String LITERAL = "\"([^\"]*)\"|'([^']*)'";

    public static List<Token> analizar(String input) {
        List<Token> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile(OPERADORES_LOGICOS + "|" + OPERADORES + "|" + AGRUPADORES + "|" + NUMERO + "|" + LITERAL + "|" + IDENTIFICADOR);
        Matcher matcher = pattern.matcher(input);
        
        while (matcher.find()) {
            String lexema = matcher.group();
            if (lexema.matches(OPERADORES_LOGICOS)) {
                tokens.add(new Token(TokenType.OPERADOR_LOGICO, lexema));
            } else if (lexema.matches(OPERADORES)) {
                tokens.add(new Token(TokenType.OPERADOR, lexema));
            } else if (lexema.matches(AGRUPADORES)) {
                tokens.add(new Token(TokenType.AGRUPADOR, lexema));
            } else if (lexema.matches(NUMERO)) {
                tokens.add(new Token(TokenType.NUMERO, lexema));
            } else if (lexema.matches(LITERAL)) {
                tokens.add(new Token(TokenType.LITERAL, lexema));
            } else if (PALABRAS_RESERVADAS.contains(lexema)) {
                tokens.add(new Token(TokenType.PALABRA_RESERVADA, lexema));
            } else if (lexema.matches(IDENTIFICADOR)) {
                tokens.add(new Token(TokenType.IDENTIFICADOR, lexema));
            } else {
                tokens.add(new Token(TokenType.DESCONOCIDO, lexema));
            }
        }
        return tokens;
    }

    public static List<Token> analizarArchivo(String ruta) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append(" \n ");
            }
        }
        return analizar(contenido.toString());
    }
}
