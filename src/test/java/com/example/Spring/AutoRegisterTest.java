package com.example.Spring;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class AutoRegisterTest {
    // Đăng ký với thông tin hợp lệ
    @Test
    public void testRegisterSuccess(){
        WebDriver driver = new EdgeDriver();
        try {
            String baseUrl = "http://localhost:5173/register";
            driver.get(baseUrl);
            WebElement usernameField = driver.findElement(By.name("username"));
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement confirmPasswordField = driver.findElement(By.name("passwordcheck"));
            WebElement fullnameField = driver.findElement(By.name("fullname"));
            WebElement emailField = driver.findElement(By.name("email"));
            usernameField.sendKeys("Testregister");
            passwordField.sendKeys("1");
            confirmPasswordField.sendKeys("1");
            fullnameField.sendKeys("Nguyễn Văn A");
            emailField.sendKeys("Z5eHt@example.com");
            WebElement registerButton = driver.findElement(By.name("register"));
            registerButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Đăng ký với thông tin bỏ trống
    @Test
    public void testRegisterFail(){
        WebDriver driver = new EdgeDriver();
        try {
            String baseUrl = "http://localhost:5173/register";
            driver.get(baseUrl);
            WebElement usernameField = driver.findElement(By.name("username"));
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement confirmPasswordField = driver.findElement(By.name("passwordcheck"));
            WebElement fullnameField = driver.findElement(By.name("fullname"));
            WebElement emailField = driver.findElement(By.name("email"));
            usernameField.sendKeys("");
            passwordField.sendKeys("");
            confirmPasswordField.sendKeys("");
            fullnameField.sendKeys("");
            emailField.sendKeys("");
            WebElement registerButton = driver.findElement(By.name("register"));
            registerButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
