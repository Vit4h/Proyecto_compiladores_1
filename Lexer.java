import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

class Token{
    private String tipo;
    private String valor;
    private int fila;
    private int columna;git

    public Token(String tipo, String valor, int fila, int columna){
        this.tipo = tipo;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
    }

    public String getTipo(){ return tipo;}
    public String getValor(){ return valor;}
    public int getFila(){ return fila;}
    public int getColumna(){ return columna;}
}
class Lexico {
    String[] reservadas = {"entero","real", "booleano","caracter","cadena", "if", "else","else if", "for", "do", "while", "escribirlinea", "escribir", "longitud", "acadena"};
    String[] operadores = {"+", "-", "*", "/", "^", "#", "++", "--", "="};
    String[] loperadores = {"==", ">=", "<=", "!=", "<", ">", "||", "&&", "!"};
    String identificadores = "^[a-zA-Z_]\\w*$";
    String numeros = "^\\d+$";
    String flotante = "\\d+\\.\\d+$";
    String simbolosEspeciales = "[(){}\\[\\];,]";

    public List<Token> analizar(String entrada) {
        List<Token> tokens = new ArrayList<>();

        Pattern patronIdentificador = Pattern.compile(identificadores);
        Pattern patronNumero = Pattern.compile(numeros);
        Pattern patronFlotantes = Pattern.compile(flotante);
        Pattern patronSimbolos = Pattern.compile(simbolosEspeciales);

        String[] lineas = entrada.split("\n");
        int fila_actual = 1;
        int columna_actual = 1;

        for (String linea : lineas) {
            Pattern patronTokens = Pattern.compile(
                    "//.*?(?:\\n|$)|" +           // Comentarios de una línea
                            "/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/|" +  // Comentarios multilínea
                            "\\d+\\.\\d+|\\d+|\\w+|==|>=|<=|<>|::|\\*\\*|[+\\-*/^=<>();{},]|\"[^\"]*\""
            );

            Matcher matcher = patronTokens.matcher(linea);

            while (matcher.find()) {
                String token = matcher.group().trim();
                if (token.isEmpty()) continue;
                columna_actual = matcher.start() + 1;

                if (token.startsWith("//") || token.startsWith("/*")) {
                    continue;
                }
                if (Arrays.asList(reservadas).contains(token)) {
                    tokens.add(new Token("Reservadas", token, fila_actual, columna_actual));
                } else if (Arrays.asList(operadores).contains(token)) {
                    tokens.add(new Token("Operadores", token, fila_actual, columna_actual));
                } else if (Arrays.asList(loperadores).contains(token)) {
                    tokens.add(new Token("Operadores Logicos", token, fila_actual, columna_actual));
                } else if (patronIdentificador.matcher(token).matches()) {
                    tokens.add(new Token("Identificadores", token, fila_actual, columna_actual));
                } else if (patronNumero.matcher(token).matches()) {
                    tokens.add(new Token("Numeros", token, fila_actual, columna_actual));
                } else if (patronFlotantes.matcher(token).matches()) {
                    tokens.add(new Token("Flotantes", token, fila_actual, columna_actual));
                } else if (patronSimbolos.matcher(token).matches()) {
                    tokens.add(new Token("Simbolos Especiales", token, fila_actual, columna_actual));
                } else if (token.startsWith("\"") && token.endsWith("\"")) {
                    tokens.add(new Token("Cadena", token, fila_actual, columna_actual));
                } else {
                    tokens.add(new Token("Desconocido", token, fila_actual, columna_actual));
                }
            }
            fila_actual++;
            columna_actual = 1;
        }
            return tokens;
    }

}