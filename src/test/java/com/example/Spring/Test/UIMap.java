package com.example.Spring.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.openqa.selenium.By;

public class UIMap {
    Properties properties;

    public UIMap(String FilePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(FilePath);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) throws Exception {
        return properties.getProperty(key);
    }

    public By getLocator(String key) throws Exception {
        String locator = properties.getProperty(key);
        String locatorType = locator.split(":")[0];
        String locatorValue = locator.split(":")[1];

        if (locatorType.toLowerCase().equals("id")) {
            return By.id(locatorValue);
        } else if (locatorType.toLowerCase().equals("name")) {
            return By.name(locatorValue);
        } else if (locatorType.toLowerCase().equals("classname")) {
            return By.className(locatorValue);
        } else if (locatorType.toLowerCase().equals("cssselector")) {
            return By.cssSelector(locatorValue);
        } else if (locatorType.toLowerCase().equals("xpath")) {
            return By.xpath(locatorValue);
        } else if (locatorType.toLowerCase().equals("linktext")) {
            return By.linkText(locatorValue);
        } else if (locatorType.toLowerCase().equals("partiallinktext")) {
            return By.partialLinkText(locatorValue);
        } else if (locatorType.toLowerCase().equals("tagname")) {
            return By.tagName(locatorValue);
        } else {
            throw new Exception("Locator type '" + locatorType + "' not supported.");
        }
    }
}
