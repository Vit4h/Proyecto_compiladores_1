import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
        int port = 8081;
        System.out.println("Iniciando servidor HTTP en el puerto " + port + "...");
        startServer(port);
    }

    public static void startServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/analyze", new AnalyzeHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor HTTP iniciado en http://localhost:" + port);
    }

    static class AnalyzeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Configurar CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            // Manejar solicitudes OPTIONS (preflight)
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // Solo manejar POST
            if (!"POST".equals(exchange.getRequestMethod())) {
                sendErrorResponse(exchange, 405, "Método no permitido");
                return;
            }

            try {
                // Leer el cuerpo de la solicitud
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                if (requestBody == null || requestBody.isEmpty()) {
                    sendErrorResponse(exchange, 400, "El cuerpo de la solicitud no puede estar vacío");
                    return;
                }

                // Extraer el código del JSON
                String code = extractCodeFromJson(requestBody);
                if (code == null) {
                    sendErrorResponse(exchange, 400, "El campo 'code' es requerido");
                    return;
                }

                // Analizar el código
                Map<String, Object> resultado = Lexer.analizarCodigo(code);

                // Preparar respuesta
                String response;
                int statusCode;

                if (resultado.containsKey("errores")) {
                    statusCode = 400;
                    response = gson.toJson(Map.of(
                        "status", "error",
                        "errores", resultado.get("errores"),
                        "tokensParciales", resultado.get("tokensParciales")
                    ));
                } else {
                    statusCode = 200;
                    response = gson.toJson(Map.of(
                        "status", "success",
                        "tokens", resultado.get("tokens")
                    ));
                }

                // Enviar respuesta
                exchange.sendResponseHeaders(statusCode, response.getBytes().length);
                try (OutputStream output = exchange.getResponseBody()) {
                    output.write(response.getBytes());
                }
            } catch (Exception e) {
                System.err.println("Error en el backend: " + e.getMessage());
                sendErrorResponse(exchange, 500, "Error interno del servidor: " + e.getMessage());
            }
        }

        private String extractCodeFromJson(String json) {
            try {
                // Usamos Gson para parsear el JSON de manera segura
                Map<?, ?> map = gson.fromJson(json, Map.class);
                return (String) map.get("code");
            } catch (Exception e) {
                return null;
            }
        }

        private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
            String response = gson.toJson(Map.of(
                "status", "error",
                "message", message
            ));
            exchange.sendResponseHeaders(statusCode, response.getBytes().length);
            try (OutputStream output = exchange.getResponseBody()) {
                output.write(response.getBytes());
            }
        }
    }

    // Métodos para convertir a JSON (usados por el Lexer)
    public static String tokensToJson(List<Lexer.Token> tokens) {
        return gson.toJson(tokens);
    }

    public static String erroresToJson(List<Lexer.LexicalError> errores) {
        return gson.toJson(errores);
    }
}