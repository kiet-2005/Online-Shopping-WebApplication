package com.example.Spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;

public class RegisterTest {
    private static final HttpClient client = HttpClient.newHttpClient();
// Test register account
    @Test
    public void testRegisterSuccess() throws IOException, InterruptedException {
        String json = "{"
                + "\"username\":\"testregister\","
                + "\"password\":\"1\","
                + "\"fullname\": \"testregister\","
                + "\"email\":\"testregister@gmail.com\""
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/api/register"))
        .header("Content-Type", "application/json")
        .header("Cookie", TestHelper.getSessionId())
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Register account success!" + response.body());
        assertEquals(200, response.statusCode(), "Register account success!");
    }

    // Test register voi tong tin bo trong
    @Test
    public void testRegisterFail() throws IOException, InterruptedException {
        String json = "{}";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/api/register"))
        .header("Content-Type", "application/json")
        .header("Cookie", TestHelper.getSessionId())
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Register account fail!" + response.body());
        assertEquals(500, response.statusCode(), "Register account fail!");
    }

    // Test register voi username co ki tu dat biet
    @Test
    public void testRegisterFail1() throws IOException, InterruptedException {
        String json = "{"
                + "\"username\":\"testregister@\","
                + "\"password\":\"1\","
                + "\"fullname\": \"testregister\","
                + "\"email\":\"testregister@1.com\""
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/api/register"))
        .header("Content-Type", "application/json")
        .header("Cookie", TestHelper.getSessionId())
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Register account success!" + response.body());
        assertEquals(500, response.statusCode(), "Register account success!");
    }

    // Test register voi email khong hop le
    @Test
    public void testRegisterFail2() throws IOException, InterruptedException {
        String json = "{"
                + "\"username\":\"testregister\","
                + "\"password\":\"1\","
                + "\"fullname\": \"testregister\","
                + "\"email\":\"testregister@1\""
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/api/register"))
        .header("Content-Type", "application/json")
        .header("Cookie", TestHelper.getSessionId())
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Register account success!" + response.body());
        assertEquals(500, response.statusCode(), "Register account success!");
    }

    // Test register voi Username da ton tai
    @Test
    public void testRegisterFail3() throws IOException, InterruptedException {
        String json = "{"
                + "\"username\":\"t\","
                + "\"password\":\"1\","
                + "\"fullname\": \"testregister\","
                + "\"email\":\"testregister@gmail.com\""
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/api/register"))
        .header("Content-Type", "application/json")
        .header("Cookie", TestHelper.getSessionId())
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Register account success!" + response.body());
        assertEquals(500, response.statusCode(), "Register account success!");
    }

    // Test register voi Email da ton tai
    @Test
    public void testRegisterFail4() throws IOException, InterruptedException {
        String json = "{"
                + "\"username\":\"hehe\","
                + "\"password\":\"1\","
                + "\"fullname\": \"testregister\","
                + "\"email\":\"t@gmail.com\""
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/api/register"))
        .header("Content-Type", "application/json")
        .header("Cookie", TestHelper.getSessionId())
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Register account success!" + response.body());
        assertEquals(500, response.statusCode(), "Register account success!");
    }
}
