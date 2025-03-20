import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("Iniciando servidor HTTP en el puerto 8081...");
            SimpleHttpServer.startServer();
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor HTTP: " + e.getMessage());
        }
    }
}

class SimpleHttpServer {

    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/api/analyze", new AnalyzeHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor HTTP iniciado en http://localhost:8081");
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

            // Manejar solicitudes POST
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    // Leer el cuerpo de la solicitud
                    String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    if (requestBody == null || requestBody.isEmpty()) {
                        sendErrorResponse(exchange, 400, "El cuerpo de la solicitud no puede estar vacío");
                        return;
                    }

                    // Extraer el campo "code" manualmente
                    String code = extractCodeFromJson(requestBody);
                    if (code == null) {
                        sendErrorResponse(exchange, 400, "El campo 'code' es requerido");
                        return;
                    }

                    // Analizar el código con el Lexer
                    List<Token> tokens = Lexer.analizarTexto(code);

                    // Construir la respuesta manualmente
                    String response = "{\"status\": \"success\", \"tokens\": " + tokensToJson(tokens) + "}";

                    // Enviar la respuesta
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    try (OutputStream output = exchange.getResponseBody()) {
                        output.write(response.getBytes());
                    }
                } catch (Exception e) {
                    // Manejar errores
                    System.err.println("Error en el backend: " + e.getMessage());
                    sendErrorResponse(exchange, 500, "Error en el backend: " + e.getMessage());
                }
            } else {
                // Método no permitido
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            }
        }

        // Método para extraer el campo "code" del JSON manualmente
        private String extractCodeFromJson(String json) {
            // Buscar el campo "code" en el JSON
            int codeIndex = json.indexOf("\"code\":");
            if (codeIndex == -1) {
                return null; // No se encontró el campo "code"
            }

            // Extraer el valor del campo "code"
            int start = json.indexOf("\"", codeIndex + 7) + 1; // +7 para saltar "\"code\":"
            int end = json.indexOf("\"", start);
            return json.substring(start, end);
        }

        // Método para convertir tokens a JSON manualmente
        private String tokensToJson(List<Token> tokens) {
            StringBuilder json = new StringBuilder("[");
            for (Token token : tokens) {
                json.append(String.format(
                    "{\"tipo\": \"%s\", \"valor\": \"%s\", \"linea\": %d, \"columna\": %d},",
                    escapeJson(token.tipo.name()), // Convertir TokenType a String usando name()
                    escapeJson(token.valor), 
                    token.linea, 
                    token.columna
                ));
            }
            if (tokens.size() > 0) {
                json.deleteCharAt(json.length() - 1); // Eliminar la última coma
            }
            json.append("]");
            return json.toString();
        }

        // Método para escapar caracteres especiales en JSON
        private String escapeJson(String value) {
            if (value == null) {
                return "";
            }
            return value.replace("\\", "\\\\")
                       .replace("\"", "\\\"")
                       .replace("\b", "\\b")
                       .replace("\f", "\\f")
                       .replace("\n", "\\n")
                       .replace("\r", "\\r")
                       .replace("\t", "\\t");
        }

        // Método para enviar respuestas de error
        private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
            String errorResponse = String.format("{\"status\": \"error\", \"message\": \"%s\"}", escapeJson(message));
            exchange.sendResponseHeaders(statusCode, errorResponse.getBytes().length);
            try (OutputStream output = exchange.getResponseBody()) {
                output.write(errorResponse.getBytes());
            }
        }
    }
}