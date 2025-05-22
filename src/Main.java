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
        server.setExecutor(null);
        server.start();
    }

    static class AnalyzeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            is.close();

            Gson gson = new Gson();
            Map<String, String> jsonBody = gson.fromJson(body, Map.class);
            String codigo = jsonBody.get("code");

            Map<String, Object> response = new HashMap<>();
            List<Map<String, Object>> erroresLexicos = new ArrayList<>();
            List<Map<String, Object>> erroresSintacticos = new ArrayList<>();
            List<Map<String, Object>> tokensResponse = new ArrayList<>();

            // Análisis léxico
            List<Token> tokens = Lexer.analizarTexto(codigo);

            for (Token token : tokens) {
                Map<String, Object> tokenMap = new HashMap<>();
                tokenMap.put("tipo", token.tipo.name());
                tokenMap.put("valor", token.valor);
                tokenMap.put("linea", token.linea);
                tokenMap.put("columna", token.columna);
                tokensResponse.add(tokenMap);
            }
            response.put("tokens", tokensResponse);

            for (LexicalError error : Lexer.getErrores()) {
                Map<String, Object> err = new HashMap<>();
                err.put("linea", error.getLinea());
                err.put("columna", error.getColumna());
                err.put("mensaje", error.getMensaje());
                erroresLexicos.add(err);
            }
            if (!erroresLexicos.isEmpty()) {
                response.put("erroresLexicos", erroresLexicos);
            }

            // Tabla de símbolos
            List<Map<String, Object>> tablaSimbolos = new ArrayList<>();
            Set<String> vistos = new HashSet<>();
            int posicion = 1;

            List<String> tokensCompilador = new ArrayList<>();
            for (Token token : tokens) {
                if (token.tipo == TokenType.IDENTIFICADOR && !vistos.contains(token.valor)) {
                    Map<String, Object> simbolo = new HashMap<>();
                    simbolo.put("posicion", posicion++);
                    simbolo.put("identificador", token.valor);
                    simbolo.put("tipo", token.tipo.name());
                    simbolo.put("linea", token.linea);
                    simbolo.put("columna", token.columna);
                    tablaSimbolos.add(simbolo);
                    vistos.add(token.valor);
                }

                switch (token.tipo) {
                    case IDENTIFICADOR -> tokensCompilador.add("<ID>");
                    case NUMERO -> tokensCompilador.add("<NUM," + token.valor + ">");
                    case OPERADOR, OPERADOR_COMPARACION, OPERADOR_LOGICO -> tokensCompilador.add("<" + token.valor + ">");
                    case PUNTO_Y_COMA -> tokensCompilador.add("<TERMINACION>");
                    case TIPO_DATO, PALABRA_RESERVADA, FUNCION_RESERVADA -> tokensCompilador.add("<" + token.tipo.name() + ">");
                    case BOOLEANO, CHAR, LITERAL, AGRUPADOR -> tokensCompilador.add("<" + token.valor + ">");
                    default -> tokensCompilador.add("<" + token.tipo.name() + ">");
                }
            }

            response.put("tablaSimbolos", tablaSimbolos);
            response.put("tokensCompilador", tokensCompilador);

            // Análisis sintáctico
            CharStream input = CharStreams.fromString(codigo);
            AlgebraLexer antlrLexer = new AlgebraLexer(input);
            CommonTokenStream antlrTokens = new CommonTokenStream(antlrLexer);
            AlgebraParser parser = new AlgebraParser(antlrTokens);

            CustomErrorListener customListener = new CustomErrorListener(erroresSintacticos);
            antlrLexer.removeErrorListeners();
            parser.removeErrorListeners();
            antlrLexer.addErrorListener(customListener);
            parser.addErrorListener(customListener);

            try {
                ParseTree tree = parser.program();

                if (!erroresSintacticos.isEmpty()) {
                    response.put("erroresSintacticos", erroresSintacticos);
                }

                AlgebraEvaluatorVisitor visitor = new AlgebraEvaluatorVisitor();
                visitor.visit(tree);
                visitor.generarTAC((AlgebraParser.ProgramContext) tree);

                response.put("arbol", tree.toStringTree(parser));
                response.put("acciones", visitor.getAcciones());
                response.put("tac", visitor.getTAC());

            } catch (Exception e) {
                erroresSintacticos.add(Map.of(
                        "linea", -1,
                        "columna", -1,
                        "mensaje", "Error fatal en análisis sintáctico: " + e.getMessage()
                ));
                response.put("erroresSintacticos", erroresSintacticos);
            }

            // Enviar respuesta
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