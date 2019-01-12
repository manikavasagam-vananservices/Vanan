package com.vanancrm.TestCases.DirectPayment;

import com.vanancrm.Common.TestBase;
import com.vanancrm.PageObjects.MainPages.AccessingElement;
import com.vanancrm.PageObjects.MainPages.VhCare;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.sql.Driver;
import java.util.concurrent.TimeUnit;

public class VananHealth extends TestBase {
    private WebDriver driver;
    private String name = "testing";
    private String email = "anand@vananservices.com";
    private String Phone = "123465";
    private String comment = "Automatiomn";

    @BeforeClass
    private void beforeClass() {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        fullScreen(driver);

    }

    @Test
    public void Healthcare() {
        driver.get("https://vananhealthcare.com/");
        waitForProcessCompletion(3);
        driver.findElement(By.linkText("Services")).click();
        driver.findElement(By.linkText("Medical billing specialities")).click();
        Medicalbilling();
        coding();
        anesthesio();
        chiropractic();
        medicalbillingcompany();

        /*  Transcription transcription = new Transcription(driver);
        transcription.enterMinutes(minute);*/
    }


    public void Medicalbilling() {
     VhCare clickmedicalbilling = new VhCare(driver);
     clickmedicalbilling.MBspspecialities();
     waitForProcessCompletion(5);
      driver.findElement(By.id("mb_quick_name")).sendKeys("Automation testing");
      driver.findElement(By.id("mb_quick_email")).sendKeys("crm.anand1@gmail.com");
      driver.findElement(By.id("mb_quick_phone")).sendKeys("987654312");
      driver.findElement(By.id("mb_quick_comments")).sendKeys("TESTING");
      waitForProcessCompletion(2);
     // driver.findElement(By.className("vch-new-btn mb_quick_submit")).click();
      WebElement test = driver.findElement(By.xpath("//div[@class='col-lg-12 form-group vch-btn-space']/span[@class='vch-new-btn  mb_quick_qmsg']"));
      test.submit();
      waitForProcessCompletion(5);
      System.out.println("Medical Billing successfully sumbitted");

   //  clickmedicalbilling.Customername();

  /*   clickmedicalbilling.Enteremail(email);
     clickmedicalbilling.Phoneno(Phone);
     clickmedicalbilling.Cusotmercommnet(comment);*/

 }
 public void coding(){
     driver.get("https://vananhealthcare.com/coding-service.php");
     Medicalbilling();

     System.out.println("*****************************");
     System.out.println("coding submitted successfully");
     System.out.println("*****************************");


 }
public void anesthesio(){
driver.get("https://vananhealthcare.com/medical-billing/anesthesiology.php");
 Medicalbilling();

    System.out.println("*****************************");
 System.out.println("anesthesio submitted successfully");
    System.out.println("*****************************");
    }
    public  void  chiropractic() {
        driver.get("https://vananhealthcare.com/medical-billing/chiropractic.php");
        Medicalbilling();

        System.out.println("*****************************");
        System.out.println("chiropractic submitted successfully");
        System.out.println("*****************************");
    }
    public  void  medicalbillingcompany() {
        driver.get("https://vananhealthcare.com/medical-billing-company.php");
        Medicalbilling();

        System.out.println("*****************************");
        System.out.println("medicalbillingcompany submitted successfully");
        System.out.println("*****************************");
        driver.close();
    }

}

