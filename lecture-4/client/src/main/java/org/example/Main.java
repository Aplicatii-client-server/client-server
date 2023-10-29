package org.example;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws InterruptedException, URISyntaxException, IOException {
        var cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        try (var httpClient = HttpClient
                .newBuilder()
                .cookieHandler(cookieManager)
                .build()) {

            var request = HttpRequest
                    .newBuilder()
                    .uri(new URI("http://localhost:8081/cookie"))
                    .GET()
                    .build();

            var response1 = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response1.body());

            var response2 = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response2.body());
        }
    }
}