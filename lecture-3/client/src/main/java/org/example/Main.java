package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, URISyntaxException, IOException {
        try (var httpClient = HttpClient.newHttpClient()) {
            while (true) {
                var scanner = new Scanner(System.in);

                System.out.print("Get command: ");
                var command = scanner.nextLine();
                if (command.equalsIgnoreCase("hello")) {
                    var request = HttpRequest
                            .newBuilder()
                            .uri(new URI("http://localhost:8081/hello?Vasea"))
                            .header("secret", "secret")
                            .GET()
                            .build();

                    var response = httpClient
                            .send(request, HttpResponse.BodyHandlers.ofString());

                    System.out.println(response.body());
                } else if (command.equalsIgnoreCase("bye")) {
                    var request = HttpRequest
                            .newBuilder()
                            .uri(new URI("http://localhost:8081/bye"))
                            .GET()
                            .build();

                    var response = httpClient
                            .send(request, HttpResponse.BodyHandlers.ofString());

                    System.out.println(response.body());
                } else if (command.equalsIgnoreCase("exit")) {
                    break;
                }
            }

        }
    }
}