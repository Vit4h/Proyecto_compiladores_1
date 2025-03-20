import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            // Iniciar el servidor HTTP en el puerto 8081
            System.out.println("Iniciando servidor HTTP en el puerto 8081...");
            SimpleHttpServer.startServer();
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor HTTP: " + e.getMessage());
        }
    }
}

// Clase para el servidor HTTP
class SimpleHttpServer {

    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/api/analyze", new AnalyzeHandler());
        server.setExecutor(null); // Usa el executor por defecto
        server.start();
        System.out.println("Servidor HTTP iniciado en http://localhost:8081");
    }

    static class AnalyzeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Habilitar CORS
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

            // Manejar solicitudes OPTIONS (preflight)
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // Respuesta vacía para preflight
                return;
            }

            // Manejar solicitudes POST
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    // Leer el cuerpo de la solicitud
                    String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                    System.out.println("Cuerpo de la solicitud: " + requestBody); // Depuración

                    // Parsear el JSON para extraer el campo "code"
                    JsonObject json;
                    try (JsonReader jsonReader = Json.createReader(new StringReader(requestBody))) {
                        json = jsonReader.readObject();
                    }
                    String code = json.getString("code"); // Extraer el valor de "code"
                    System.out.println("Código a analizar: " + code); // Depuración

                    // Procesar el código con el Lexer
                    List<Token> tokens = Lexer.analizarTexto(code);

                    // Convertir los tokens a JSON
                    String response = "{\"tokens\": " + tokensToJson(tokens) + "}";

                    // Enviar la respuesta
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    try (OutputStream output = exchange.getResponseBody()) {
                        output.write(response.getBytes());
                    }
                } catch (Exception e) {
                    // Manejar errores
                    System.err.println("Error en el backend: " + e.getMessage()); // Depuración
                    String errorResponse = "{\"error\": \"" + e.getMessage() + "\"}";
                    exchange.sendResponseHeaders(500, errorResponse.getBytes().length);
                    try (OutputStream output = exchange.getResponseBody()) {
                        output.write(errorResponse.getBytes());
                    }
                }
            } else {
                // Método no permitido
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            }
        }

        // Método para convertir tokens a JSON (simplificado)
        private String tokensToJson(List<Token> tokens) {
            StringBuilder json = new StringBuilder("[");
            for (Token token : tokens) {
                json.append(String.format(
                    "{\"tipo\": \"%s\", \"valor\": \"%s\", \"linea\": %d, \"columna\": %d},",
                    token.tipo, token.valor, token.linea, token.columna
                ));
            }
            if (tokens.size() > 0) {
                json.deleteCharAt(json.length() - 1); // Eliminar la última coma
            }
            json.append("]");
            return json.toString();
        }
    }
}