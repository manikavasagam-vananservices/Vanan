package com.vanancrm.PageObjects.MainPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class Gmail extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;

    @FindBy(id = "identifierId")
    private WebElement emailIdElement;

    @FindBy(name = "password")
    private WebElement passwordElement;

    @FindBy(id = "identifierNext")
    private WebElement emailNextElement;

    @FindBy(id = "passwordNext")
    private WebElement passwordNextElement;

    @FindBy(xpath = "//a[@aria-label='Google apps']")
    private WebElement googleApps;

    @FindBy(id = "gb23")
    private WebElement gmailApp;

    private Actions builder;
    private Action mouseOverHome;

    public Gmail(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    private void enterEmailId(String emailId) {
        try {
            enterTestBoxValues(emailIdElement, emailId);
        } catch (Exception e) {
            System.out.println("Unable to enter a email id value " + e);
        }
    }


    private void enterPassword(String password) {
        try {
            enterTestBoxValues(passwordElement, password);
        } catch (Exception e) {
            System.out.println("Unable to enter a password value " + e);
        }
    }

    private void clickEmailNext() {
        try {
            clickElement(emailNextElement);
        } catch (Exception e) {
            System.out.println("Unable to click email next button " + e);
        }
    }

    private void clickPasswordNext() {
        try {
            clickElement(passwordNextElement);
        } catch (Exception e) {
            System.out.println("Unable to click password next button " + e);
        }
    }

    private void clickLoadGmail() {
        try {
            clickElement(googleApps);
            clickElement(gmailApp);
        } catch (Exception e) {
            System.out.println("Unable to click load gmail app " + e);
        }
    }

    public void gmailLogin(String username, String password) {
        driver.get("https://accounts.google.com");
        enterEmailId(username);
        clickEmailNext();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception ex) {

        }
        enterPassword(password);
        clickPasswordNext();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception ex) {

        }
        clickLoadGmail();
    }

    public void clickParticularEmail(String service) {
        try {
            clickElement(driver.findElement(By.xpath("//table/tbody/tr/td/div//../div/span/b[contains(text(),'"+service+" Price Quote')]")));
        } catch (Exception e) {
            System.out.println("Unable to click load gmail app " + e);
        }
    }

}
