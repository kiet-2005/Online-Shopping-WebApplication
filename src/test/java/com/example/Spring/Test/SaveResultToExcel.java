package com.example.Spring.Test;

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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SaveResultToExcel {
    WebDriver driver;
    public UIMap uimap;
    public UIMap datafile;
    public String workingDir;
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

        uimap = new UIMap("src/main/resources/locator.properties");
        datafile = new UIMap("src/main/resources/data.properties");
    }

    @Test(description = "Test 1 - Mở trang web")
    public void test1() {
        try {
            driver.get("https://www.fahasa.com/customer/account/login/referer/aHR0cHM6Ly93d3cuZmFoYXNhLmNvbS9jdXN0b21lci9hY2NvdW50L2luZGV4Lw,,/");
            TestNGResults.put("2", new Object[] { 1d, "Mở trang web", "Test 1", "Passed" });
        } catch (Exception e) {
            TestNGResults.put("2", new Object[] { 1d, "Mở trang web", "Test 1", "Failed" });
            e.printStackTrace();
        }
    }

    @Test(description = "Test 2 - Điền username và password")
    public void test2() {
        try {
            WebElement usernameField = driver.findElement(uimap.getLocator("username"));
            WebElement passwordField = driver.findElement(uimap.getLocator("password"));

            // usernameField.sendKeys("0374124082");
            // passwordField.sendKeys("kiet123");

            TestNGResults.put("3", new Object[] { 2d, "Nhập username & password", "Test 2", "Passed" });
        } catch (Exception e) {
            TestNGResults.put("3", new Object[] { 2d, "Nhập username & password", "Test 2", "Failed" });
            e.printStackTrace();
        }
    }

    @Test(description = "Test 3 - Nhấn đăng nhập")
    public void test3() throws Exception {
        try {
            WebElement loginButton = driver.findElement(uimap.getLocator("submit"));
            loginButton.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement userName = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(uimap.getLocator("username")));
            Assert.assertTrue(userName.isDisplayed());

            TestNGResults.put("4", new Object[] { 3d, "Nhấn đăng nhập", "Test 3", "Passed" });
        } catch (Exception e) {
            TestNGResults.put("4", new Object[] { 3d, "Nhấn đăng nhập", "Test 3", "Failed" });
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        Set<String> keys = TestNGResults.keySet();
        int rownum = 0;
        for (String key : keys) {
            Row row = sheet.createRow(rownum++);
            int rowNum = 0;
            Object[] objArr = TestNGResults.get(key);
            for (Object obj : objArr) {
                Cell cell = (Cell) row.createCell(rowNum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                } else if (obj instanceof Boolean) {
                    cell.setCellValue((Boolean) obj);
                }
            }
            try (FileOutputStream out = new FileOutputStream("TestNGResults.xls")) {
                workbook.write(out);
            } catch (Exception e) {
                e.printStackTrace();
            }

            driver.quit();
        }
    }
}
