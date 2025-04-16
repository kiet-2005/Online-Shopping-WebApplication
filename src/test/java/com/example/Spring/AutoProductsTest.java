package com.example.Spring;

import java.io.FileOutputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AutoProductsTest {
    WebDriver driver;
    HSSFWorkbook workbook;
    HSSFSheet sheet;
    Map<String, Object[]> TestNGResults;

    @BeforeClass
    public void setUp() {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("Test Results");
        TestNGResults = new HashMap<>();
        TestNGResults.put("0", new Object[] { "STT", "Tên Test", "Chi tiết", "Kết quả" });
    }

    @Test(description = "Test 1 - Tạo sản phẩm thành công!")
    public void testAutoProducts() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            driver.get("http://localhost:5173/login");

            driver.findElement(By.name("username")).sendKeys("Kiệt");
            driver.findElement(By.name("password")).sendKeys("1");
            driver.findElement(By.name("login")).click();

            WebElement manageProductLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Quản lý sản phẩm")));
            manageProductLink.click();

            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("productname")));
            WebElement descriptionField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("productdescription")));
            WebElement priceField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("price")));
            WebElement quantityField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("quantity")));
            WebElement categoryField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("category")));
            WebElement availableField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("available")));
            WebElement imageField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("image")));

            nameField.sendKeys("Testproduct");
            descriptionField.sendKeys("Testdescription");
            priceField.sendKeys("1");
            quantityField.sendKeys("1");
            Select categorySelect = new Select(categoryField);
            categorySelect.selectByValue("C001");
            Select availableSelect = new Select(availableField);
            availableSelect.selectByValue("true");
            imageField.sendKeys("E:/Java5/PS41774_HuynhAnhKiet_ASM_Final/VueJS/src/assets/image/sp15.png");

            WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(By.name("create")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", createBtn);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createBtn);

            TestNGResults.put("1", new Object[] { 1d, "Tạo sản phẩm", "Test 1", "Passed" });
        } catch (Exception e) {
            TestNGResults.put("1", new Object[] { 1d, "Tạo sản phẩm", "Test 1", "Failed" });
            e.printStackTrace();
        }
    }

    @Test(description = "Test 2 - Xóa sản phẩm")
    public void testDeleteProducts() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            int idproduct = 79;
            String xpath = String.format("//tr[td[text()='%d']]//button[@name='edit']", idproduct);
            WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            editBtn.click();

            WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(By.name("delete")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteBtn);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);

            TestNGResults.put("2", new Object[] { 2d, "Xóa sản phẩm", "Test 2", "Passed" });
        } catch (Exception e) {
            TestNGResults.put("2", new Object[] { 2d, "Xóa sản phẩm", "Test 2", "Failed" });
            e.printStackTrace();
        }
    }

    @Test(description = "Test 3 - Sửa sản phẩm")
    public void testEditProducts() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            int idproduct = 63;
            String xpath = String.format("//tr[td[text()='%d']]//button[@name='edit']", idproduct);
            WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            editBtn.click();

            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("productname")));
            WebElement descriptionField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("productdescription")));
            WebElement priceField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("price")));
            WebElement quantityField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("quantity")));
            WebElement categoryField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("category")));
            WebElement availableField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("available")));
            WebElement imageField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("image")));

            nameField.sendKeys("Testproduct");
            descriptionField.sendKeys("Testdescription");
            priceField.sendKeys("1");
            quantityField.sendKeys("1");
            Select categorySelect = new Select(categoryField);
            categorySelect.selectByValue("C001");
            Select availableSelect = new Select(availableField);
            availableSelect.selectByValue("true");
            imageField.sendKeys("E:/Java5/PS41774_HuynhAnhKiet_ASM_Final/VueJS/src/assets/image/sp16.png");

            WebElement updateBtn = wait.until(ExpectedConditions.elementToBeClickable(By.name("update")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", updateBtn);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", updateBtn);

            TestNGResults.put("3", new Object[] { 3d, "Sửa sản phẩm", "Test 3", "Passed" });
        } catch (Exception e) {
            TestNGResults.put("3", new Object[] { 3d, "Sửa sản phẩm", "Test 3", "Failed" });
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        Set<String> keys = TestNGResults.keySet();
        int rownum = 0;

        for (String key : keys) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = TestNGResults.get(key);
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

        try (FileOutputStream out = new FileOutputStream("TestNGResults.xls")) {
            workbook.write(out);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
