package src;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import src.Parser.AlgebraLexer;
import src.Parser.AlgebraParser;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = 8081;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Servidor iniciado en http://localhost:" + port);

        server.createContext("/api/analyze", new AnalyzeHandler());
        server.setExecutor(null); // Usa el default
        server.start();
    }

    private static void imprimirTablaSimbolos(List<Token> tokens) {
        List<Token> identificadores = new ArrayList<>();
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

    static class AnalyzeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Configurar encabezados CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                return;
            }

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            is.close();

            Gson gson = new Gson();
            Map<String, String> jsonBody = gson.fromJson(body, Map.class);
            String codigo = jsonBody.get("code");

            Map<String, Object> response = new HashMap<>();
            List<Map<String, Object>> tokensResponse = new ArrayList<>();
            List<Map<String, Object>> errores = new ArrayList<>();

            List<Token> tokens = Lexer.analizarTexto(codigo);

            // Registrar errores léxicos
            for (LexicalError error : Lexer.getErrores()) {
                Map<String, Object> err = new HashMap<>();
                err.put("linea", error.getLinea());
                err.put("columna", error.getColumna());
                err.put("mensaje", error.getMensaje());
                errores.add(err);
            }

            if (!errores.isEmpty()) {
                response.put("errores", errores);
            } else {
                // Agregar tokens
                for (Token token : tokens) {
                    Map<String, Object> tokenMap = new HashMap<>();
                    tokenMap.put("tipo", token.tipo.name());
                    tokenMap.put("valor", token.valor);
                    tokenMap.put("linea", token.linea);
                    tokenMap.put("columna", token.columna);
                    tokensResponse.add(tokenMap);
                }
                response.put("tokens", tokensResponse);

                // Tabla de símbolos (identificadores únicos)
                List<Map<String, Object>> tablaSimbolos = new ArrayList<>();
                Set<String> vistos = new HashSet<>();
                int posicion = 1;

                for (Token token : tokens) {
                    if (token.tipo == TokenType.IDENTIFICADOR && !vistos.contains(token.valor)) {
                        Map<String, Object> simbolo = new HashMap<>();
                        simbolo.put("posicion", posicion);
                        simbolo.put("identificador", token.valor);
                        simbolo.put("tipo", token.tipo.name());
                        simbolo.put("linea", token.linea);
                        simbolo.put("columna", token.columna);
                        tablaSimbolos.add(simbolo);
                        vistos.add(token.valor);
                        posicion++;
                    }
                }

                response.put("tablaSimbolos", tablaSimbolos);

                // Imprimir en consola
                imprimirTablaSimbolos(tokens);

                // Análisis con ANTLR
                CharStream input = CharStreams.fromString(codigo);
                AlgebraLexer antlrLexer = new AlgebraLexer(input);
                CommonTokenStream antlrTokens = new CommonTokenStream(antlrLexer);
                AlgebraParser parser = new AlgebraParser(antlrTokens);

                parser.removeErrorListeners();
                parser.addErrorListener(new CustomErrorListener());

                ParseTree tree = parser.program();

                AlgebraEvaluatorVisitor visitor = new AlgebraEvaluatorVisitor();
                visitor.visit(tree);

                response.put("arbol", tree.toStringTree(parser));
                response.put("acciones", visitor.getAcciones());
            }

            String jsonResponse = gson.toJson(response);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }
    }
}
