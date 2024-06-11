package de.syneco;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class TestOne {

    private static final String SELENIUM_HUB_URL = "http://localhost:4444/wd/hub";

    public static WebDriver remoteChromeDriver() throws MalformedURLException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return new RemoteWebDriver(new URL(SELENIUM_HUB_URL), options);
    }

    public static WebDriver chromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }

    @Test
    public void download() {
        WebDriver driver = null;
        try {
            System.out.println("TEST BEGIN");
            driver = remoteChromeDriver();
            driver.get("https://people.sc.fsu.edu/~jburkardt/data/csv/csv.html");
            Thread.sleep(9000); // Consider using WebDriverWait instead of Thread.sleep for better stability.
            driver.findElement(By.xpath("//a[text()='airtravel.csv']")).click();
            System.out.println("TEST END");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}