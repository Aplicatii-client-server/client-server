package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(8081), 0);

        server.createContext("/cookie", exchange -> {
            var cookie = exchange.getRequestHeaders().get("cookie");
            System.out.println("Cookie: " + cookie);

            var message = cookie == null
                    ? "No cookie"
                    : "Cookie found";

            exchange.getResponseHeaders()
                    .set("set-cookie", "foo=123;bar=baz");

            notFound(exchange, message);
        });

        server.start();
    }

    private static void unauthorized(HttpExchange exchange, String responseText) throws IOException {
        sendRequest(exchange, 401, responseText);
    }

    private static void notFound(HttpExchange exchange, String responseText) throws IOException {
        sendRequest(exchange, 404, responseText);
    }

    private static void ok(HttpExchange exchange, String responseText) throws IOException {
        sendRequest(exchange, 200, responseText);
    }

    private static void sendRequest(HttpExchange exchange, int statusCode, String responseText) throws IOException {
        exchange.sendResponseHeaders(statusCode, responseText.getBytes().length);

        try (var output = exchange.getResponseBody()) {
            output.write(responseText.getBytes());
        }
    }
}
