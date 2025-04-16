package com.example.Spring;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AutoLoginTest {
    WebDriver driver;
    HSSFWorkbook workbook;
    HSSFSheet sheet;
    Map<String, Object[]> TestNGResultsLogin;

    @BeforeClass
    public void setUp() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("Test Results");
        TestNGResultsLogin = new HashMap<>();
        TestNGResultsLogin.put("0", new Object[] { "STT", "Tên Test", "Chi tiết", "Kết quả" });
    }
    // Đăng nhập với thông tin hợp lệ
    @Test(description = "Test 1 - Đăng nhập với thông tin hợp lệ")
    public void testAutoLoginSuccess(){
        WebDriver driver = new EdgeDriver();
        try {
            String baseUrl = "http://localhost:5173/login";
            driver.get(baseUrl);
            WebElement usernameField = driver.findElement(By.name("username"));
            WebElement passwordField = driver.findElement(By.name("password"));
            usernameField.sendKeys("Kiệt");
            passwordField.sendKeys("1");
            WebElement loginButton = driver.findElement(By.name("login"));
            loginButton.click();
            TestNGResultsLogin.put("1", new Object[] { 1d, "Nhập username & password", "Test 1", "Passed" });
        } catch (Exception e) {
            TestNGResultsLogin.put("1", new Object[] { 1d, "Nhập username & password", "Test 1", "Failed" });
            e.printStackTrace();
        }
    }
    // Đăng nhập với password sai
    @Test(description = "Test 2 - Đăng nhập với password sai")
    public void testAutoLoginFail(){
        WebDriver driver = new EdgeDriver();
        try {
            String baseUrl = "http://localhost:5173/login";
            driver.get(baseUrl);
            WebElement usernameField = driver.findElement(By.name("username"));
            WebElement passwordField = driver.findElement(By.name("password"));
            usernameField.sendKeys("Kiệt");
            passwordField.sendKeys("2");
            WebElement loginButton = driver.findElement(By.name("login"));
            loginButton.click();
            TestNGResultsLogin.put("2", new Object[] { 2d, "Nhập username & password", "Test 2", "Passed" });
        } catch (Exception e) {
            TestNGResultsLogin.put("2", new Object[] { 2d, "Nhập username & password", "Test 2", "Failed" });
            e.printStackTrace();
        }
    }

    // Đăng nhập với username sai
    @Test(description = "Test 3 - Đăng nhập với username sai")
    public void testAutoLoginFail1(){
        WebDriver driver = new EdgeDriver();
        try {
            String baseUrl = "http://localhost:5173/login";
            driver.get(baseUrl);
            WebElement usernameField = driver.findElement(By.name("username"));
            WebElement passwordField = driver.findElement(By.name("password"));
            usernameField.sendKeys("Testlogin");
            passwordField.sendKeys("1");
            WebElement loginButton = driver.findElement(By.name("login"));
            loginButton.click();
            TestNGResultsLogin.put("3", new Object[] { 3d, "Nhập username & password", "Test 3", "Passed" });
        } catch (Exception e) {
            TestNGResultsLogin.put("3", new Object[] { 3d, "Nhập username & password", "Test 3", "Failed" });
            e.printStackTrace();
        }
    }

    // Đăng nhập với username và password trống
    @Test(description = "Test 4 - Đăng nhập với username và password trống")
    public void testAutoLoginFail2(){
        WebDriver driver = new EdgeDriver();
        try {
            String baseUrl = "http://localhost:5173/login";
            driver.get(baseUrl);
            WebElement usernameField = driver.findElement(By.name("username"));
            WebElement passwordField = driver.findElement(By.name("password"));
            usernameField.sendKeys("");
            passwordField.sendKeys("");
            WebElement loginButton = driver.findElement(By.name("login"));
            loginButton.click();
            TestNGResultsLogin.put("4", new Object[] { 4d, "Nhập username & password", "Test 4", "Passed" });
        } catch (Exception e) {
            TestNGResultsLogin.put("4", new Object[] { 4d, "Nhập username & password", "Test 4", "Failed" });
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        Set<String> keys = TestNGResultsLogin.keySet();
        int rownum = 0;

        for (String key : keys) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = TestNGResultsLogin.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Double)
                    cell.setCellValue((Double) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
                else if (obj instanceof Boolean)
                    cell.setCellValue((Boolean) obj);
            }
        }

        try (FileOutputStream out = new FileOutputStream("TestNGResultsLogin.xls")) {
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
