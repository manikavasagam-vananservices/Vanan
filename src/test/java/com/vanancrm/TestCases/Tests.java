package com.vanancrm.TestCases;

import com.vanancrm.Common.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class Tests extends TestBase {

    private WebDriver driver;

    @Test
    public void testStep() {
        driver.get("http://www.vananservices.com/");
    }

    @BeforeClass
    public void beforeClass() {

        // Linux driver loading
        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");

        // Windows driver loading
        //System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

        // Headless Chrome option
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--no-sandbox");
        driver = new ChromeDriver(chromeOptions);

        //driver = new ChromeDriver();
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        fullScreen(driver);
    }

    @AfterClass
    public void afterClass() {

        driver.quit();
    }
}
