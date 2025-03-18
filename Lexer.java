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
        return "Token{tipo=" + tipo + ", valor='" + valor + "'}";
    }
}

// Analizador léxico con buffer
class Lexer {
    private static final String OPERADORES = "[+\\-*/^]";
    private static final String OPERADORES_LOGICOS = "(\\*\\*|==|>=|<=|!=|::|=)";
    private static final String AGRUPADORES = "[(){}\\[\\]<>]";
    private static final String NUMERO = "-?\\d+(\\.\\d+)?";
    private static final String IDENTIFICADOR = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final Set<String> PALABRAS_RESERVADAS = Set.of("cuadrado", "if", "else", "return", "while", "for");
    private static final String LITERAL = "\"([^\"\\\\]*(\\\\.[^\"\\\\]*)*)\"|'([^'\\\\]*(\\\\.[^'\\\\]*)*)'";
    private static final String ESPACIO = "\\s+";

    private static final Pattern PATRON = Pattern.compile(
        OPERADORES_LOGICOS + "|" + OPERADORES + "|" + AGRUPADORES + "|" + 
        NUMERO + "|" + LITERAL + "|" + IDENTIFICADOR + "|" + ESPACIO,
        Pattern.CASE_INSENSITIVE // Hace que el regex sea insensible a mayúsculas
    );

    private static Token crearToken(String lexema) {
        // Convertir el lexema a minúsculas para comparar con palabras reservadas
        String lexemaMinusculas = lexema.toLowerCase();

        if (lexema.matches(OPERADORES_LOGICOS)) {
            return new Token(TokenType.OPERADOR_LOGICO, lexema);
        } else if (lexema.matches(OPERADORES)) {
            return new Token(TokenType.OPERADOR, lexema);
        } else if (lexema.matches(AGRUPADORES)) {
            return new Token(TokenType.AGRUPADOR, lexema);
        } else if (lexema.matches(NUMERO)) {
            return new Token(TokenType.NUMERO, lexema);
        } else if (lexema.matches(LITERAL)) {
            return new Token(TokenType.LITERAL, lexema);
        } else if (PALABRAS_RESERVADAS.contains(lexemaMinusculas)) {
            return new Token(TokenType.PALABRA_RESERVADA, lexema);
        } else if (lexema.matches(IDENTIFICADOR)) {
            return new Token(TokenType.IDENTIFICADOR, lexema);
        } else {
            return new Token(TokenType.DESCONOCIDO, lexema);
        }
    }

    public static List<Token> analizar(String input) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = PATRON.matcher(input);

        while (matcher.find()) {
            String lexema = matcher.group();
            if (!lexema.matches(ESPACIO)) {
                tokens.add(crearToken(lexema));
            }
        }
        return tokens;
    }

    public static List<Token> analizarArchivo(String ruta) throws IOException {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            throw new FileNotFoundException("El archivo no existe: " + ruta);
        }

        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        return analizar(contenido.toString());
    }
}