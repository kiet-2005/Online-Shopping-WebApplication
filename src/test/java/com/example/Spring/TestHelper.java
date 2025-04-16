package com.example.Spring;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestHelper {
    private static String sessionId;
    private static final HttpClient client = HttpClient.newHttpClient();
    public static String getSessionId() throws IOException, InterruptedException{
        String formdata = "username=Kiệt&password=1";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/login"))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(formdata))
        .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        sessionId = response.headers().firstValue("Set-Cookie").get().split(";")[0];
        System.out.println("Login success!" + sessionId);
        return sessionId;
    }
}
