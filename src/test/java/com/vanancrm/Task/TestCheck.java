package com.vanancrm.Task;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


public class TestCheck {
    private WebDriver driver;

    @Test
    public void transcriptionServices() {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.get("https://vananservices.com/Translation-Quote.php");
        JavascriptExecutor js =  (JavascriptExecutor) driver;

        System.out.println("Test = >" + (boolean)js.executeScript("if($(\"#sourcefiletype\").is(\":visible\") != true ) { return false; } else { return true;}"));
    }

}
