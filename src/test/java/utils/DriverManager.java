package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    private static WebDriver driver;

    public static void initDriver() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            //options.addArguments("--headless=new"); // Run in modern headless mode
            //options.addArguments("--window-size=1920,1080"); // Important for headless mode
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}