package com.example.Spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import jakarta.transaction.Transactional;

public class ProductTest {
    private static final HttpClient client = HttpClient.newHttpClient();
// Test cho việc tạo sản phẩm thành công
    @Test
    @Transactional
    @Rollback(value = true)
    public void testCreateProductSuccess() throws IOException, InterruptedException {
        String json = "{"
                + "\"name\":\"Sản phẩm test11\","
                + "\"image\":\"product-image11.jpg\","
                + "\"price\":2000000,"
                + "\"description\":\"Mô tả sản phẩm test\","
                + "\"quantity\":\"15\","
                + "\"createDate\":\"2024-03-30\","
                + "\"available\":true,"
                + "\"category\":{\"id\":\"C001\"}"
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/api/admin/products/create"))
        .header("Content-Type", "application/json")
        .header("Cookie", TestHelper.getSessionId())
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Create product success!" + response.body());
        assertEquals(200, response.statusCode(), "Create product success!");
    }
// Test lay danh sach san pham
    @Test
    public void testGetAllProduct() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/products"))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Get all product success!" + response.body());
        assertEquals(200, response.statusCode(), "Get all product success!");
    }

    // Test them san pham voi thong tin bo trong
    @Test
    public void testCreateProductFail() throws IOException, InterruptedException {
        String json = "{}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/admin/products/create"))
                .header("Content-Type", "application/json")
                .header("Cookie", TestHelper.getSessionId())
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Create product fail!" + response.body());
        assertEquals(500, response.statusCode(), "Create product fail!");
    }

    // Test them san pham voi gia am
    @Test
    public void testCreateProductFail1() throws IOException, InterruptedException {
        String json = "{"
                + "\"name\":\"Sản phẩm test11\","
                + "\"image\":\"product-image11.jpg\","
                + "\"price\":-2000000,"
                + "\"description\":\"Mô tả sản phẩm test\","
                + "\"quantity\":\"15\","
                + "\"createDate\":\"2024-03-30\","
                + "\"available\":true,"
                + "\"category\":{\"id\":\"C001\"}"
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/api/admin/products/create"))
        .header("Content-Type", "application/json")
        .header("Cookie", TestHelper.getSessionId())
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Create product success!" + response.body());
        assertEquals(500, response.statusCode(), "Create product success!");
    }

    // Test them san pham voi so luong am
    @Test
    public void testCreateProductFail2() throws IOException, InterruptedException {
        String json = "{"
                + "\"name\":\"Sản phẩm test11\","
                + "\"image\":\"product-image11.jpg\","
                + "\"price\":2000000,"
                + "\"description\":\"Mô tả sản phẩm test\","
                + "\"quantity\":\"-15\","
                + "\"createDate\":\"2024-03-30\","
                + "\"available\":true,"
                + "\"category\":{\"id\":\"C001\"}"
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/api/admin/products/create"))
        .header("Content-Type", "application/json")
        .header("Cookie", TestHelper.getSessionId())
        .POST(HttpRequest.BodyPublishers.ofString(json))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Create product success!" + response.body());
        assertEquals(500, response.statusCode(), "Create product success!");
    }

    // Test cap nhat san pham thanh cong
    @Test
    public void testUpdateProduct() throws IOException, InterruptedException {
        String json = "{"
                + "\"name\":\"Sản phẩm test11\","
                + "\"image\":\"product-image11.jpg\","
                + "\"price\":99999,"
                + "\"description\":\"Mô tả sản phẩm test\","
                + "\"quantity\":\"999\","
                + "\"createDate\":\"2024-03-30\","
                + "\"available\":true,"
                + "\"category\":{\"id\":\"C001\"}"
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/admin/products/72"))
                .header("Content-Type", "application/json")
                .header("Cookie", TestHelper.getSessionId())
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Update product success!" + response.body());
        assertEquals(200, response.statusCode(), "Update product success!");
    }

    // Test cap nhat san pham voi thong tin bo trong
    @Test
    public void testUpdateProduct1() throws IOException, InterruptedException {
        String json = "{}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/admin/products/72"))
                .header("Content-Type", "application/json")
                .header("Cookie", TestHelper.getSessionId())
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Update product fail!" + response.body());
        assertEquals(500, response.statusCode(), "Update product fail!");
    }


    // Test cap nhat san pham voi gia am
    @Test
    public void testUpdateProduct2() throws IOException, InterruptedException {
        String json = "{"
                + "\"name\":\"Sản phẩm test11\","
                + "\"image\":\"product-image11.jpg\","
                + "\"price\":-99999,"
                + "\"description\":\"Mô tả sản phẩm test\","
                + "\"quantity\":\"15\","
                + "\"createDate\":\"2024-03-30\","
                + "\"available\":true,"
                + "\"category\":{\"id\":\"C001\"}"
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/admin/products/72"))
                .header("Content-Type", "application/json")
                .header("Cookie", TestHelper.getSessionId())
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Update product fail!" + response.body());
        assertEquals(500, response.statusCode(), "Update product fail!");
    }

    // Test cap nhat san pham voi so luong am
    @Test
    public void testUpdateProduct3() throws IOException, InterruptedException {
        String json = "{"
                + "\"name\":\"Sản phẩm test11\","
                + "\"image\":\"product-image11.jpg\","
                + "\"price\":99999,"
                + "\"description\":\"Mô tả sản phẩm test\","
                + "\"quantity\":\"-15\","
                + "\"createDate\":\"2024-03-30\","
                + "\"available\":true,"
                + "\"category\":{\"id\":\"C001\"}"
                + "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/admin/products/72"))
                .header("Content-Type", "application/json")
                .header("Cookie", TestHelper.getSessionId())
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Update product fail!" + response.body());
        assertEquals(500, response.statusCode(), "Update product fail!");
    }

    // Test xoa san pham thanh cong
    @Test
    public void testDeleteProduct() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/admin/products/72"))
                .header("Content-Type", "application/json")
                .header("Cookie", TestHelper.getSessionId())
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Delete product success!" + response.body());
        assertEquals(204, response.statusCode(), "Delete product success!");
    }

    // Test xoa san pham khong ton tai
    @Test
    public void testDeleteProduct1() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/admin/products/72"))
                .header("Content-Type", "application/json")
                .header("Cookie", TestHelper.getSessionId())
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Delete product fail!" + response.body());
        assertEquals(404, response.statusCode(), "Delete product fail!");
    }
}
