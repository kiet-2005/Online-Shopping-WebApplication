package com.example.Spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;

public class LoginTest {
    private static final HttpClient client = HttpClient.newHttpClient();
    // Test login thanh cong
    @Test
    public void testLoginSuccess() throws IOException, InterruptedException {
        String formdata = "username=Kiệt&password=1";
        // Tao request gui toi api
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/login"))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(formdata))
        .build();
        // Gui request va nhan response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 200, "Login success!");
        System.out.println(response.body());
    }

    // Test login voi password khong dung
    @Test
    public void testLoginFail() throws IOException, InterruptedException {
        String formdata = "username=Kiệt&password=2";
        
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/login"))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(formdata))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 500, "Login fail!");
        System.out.println(response.body());
    }

    // Test login voi username khong ton tai
    @Test
    public void testLoginFail2() throws IOException, InterruptedException {
        String formdata = "username=k&password=1";
        
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/login"))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(formdata))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 500, "Login fail!");
        System.out.println(response.body());
    }

    // Test login voi username va password khong ton tai
    @Test
    public void testLoginFail3() throws IOException, InterruptedException {
        String formdata = "username=k&password=2";
        
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/login"))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(formdata))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 500, "Login fail!");
        System.out.println(response.body());
    }

    // Test login voi username va password trong
    @Test
    public void testLoginFail4() throws IOException, InterruptedException {
        String formdata = "username=&password=";
        
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/login"))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(HttpRequest.BodyPublishers.ofString(formdata))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(response.statusCode(), 500, "Login fail!");
        System.out.println(response.body());
    }
}
