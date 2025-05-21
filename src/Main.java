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
                err.put("linea", error.linea);
                err.put("columna", error.columna);
                err.put("mensaje", error.mensaje);
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
                response.put("acciones", visitor.getAcciones());  // <- Aquí se agregan las acciones
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
