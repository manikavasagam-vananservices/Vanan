package com.vanancrm.PageObjects.MainPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;


public class VhCare  {


    private WebElement Viewservices;

    private WebDriver driver;

    public VhCare(WebDriver driver) {
        super();
    }

  /*@FindBy(linkText = "Medical billing specialities")
    private WebElement MBspecialitie;*/
    @FindBy(name = "mb_quick_name")
   private  WebElement Cname;
    @FindBy(id = "mb_quick_email")
    private WebElement Cemail;
    @FindBy(id = "mb_quick_phone")
    private WebElement Cphone;
    @FindBy(id = "mb_quick_comments")
    private WebElement CComment;

    public void Clickservices(String service) {

        try {

            driver.findElement(By.linkText("Services")).click();

        } catch (Exception e) {
            System.out.println("unable to select the service"+e);
        }

    }
    public void MBspspecialities(){
        try {
            WebElement element1 = driver.findElement(By.linkText("Medical billing specialities"));
            element1.click();


        } catch (Exception e) {
            System.out.println("Unable to select Service " + e);
        }

}
public  void Customername(){
    try {
        WebElement cname = driver.findElement(By.id("mb_quick_name"));
        cname.sendKeys("automation");
    } catch (Exception e) {
        System.out.println("Unable to enter a comment " + e);
    }
}
public void Mcoding(){

        driver.get("https://vananhealthcare.com/coding-service.php");
}


    }




