package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(8081), 0);

        server.createContext("/hello", exchange -> {
            System.out.println("/hello handler");
            var query = exchange.getRequestURI().getQuery();
            var method = exchange.getRequestMethod();
            System.out.println(method);

            if (exchange.getRequestHeaders().containsKey("secret")
                && exchange.getRequestHeaders().get("secret").contains("secret")) {
                var responseText = "Hello, World!(" + method + ")\n" ;
                if (query != null) {
                    responseText = "Hello " + query + "(" + method + ")!\n";
                }
                ok(exchange, responseText);
            } else {
                unauthorized(exchange, "Access denied\n");
            }

        });

        server.createContext("/bye", exchange -> {
            System.out.println("/bye handler");
            var responseText = "Bye World!\n";
            ok(exchange, responseText);
        });

        server.createContext("/", exchange -> {
            System.out.println("/ handler");
            var responseText = "Sorry, not found!\n";
            notFound(exchange, responseText);
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
