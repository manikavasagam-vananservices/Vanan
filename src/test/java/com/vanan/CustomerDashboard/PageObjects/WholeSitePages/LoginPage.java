package com.vanan.CustomerDashboard.PageObjects.WholeSitePages;

import java.util.concurrent.TimeUnit;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanan.CustomerDashboard.PageObjects.MainPages.DashBoard;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

public class LoginPage extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;
    @FindBy(id = "email")
    private WebElement userName;
    @FindBy(name = "password")
    private WebElement userPassword;
    @FindBy(xpath = "//input[@type='button' and @value='Sign In']")
    private WebElement signin;

    public LoginPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public void enterUserName(String username) {
        try {
            enterTestBoxValues(userName, username);
        } catch (Exception e) {
            System.out.println("Unable to enter a user name " + e);
        }
    }

    public void enterPassword(String password) {
        try {
            enterTestBoxValues(userPassword, password);
        } catch (Exception e) {
            System.out.println("Unable to enter a password " + e);
        }
    }

    private void clickSignIn() {

        clickElement(signin);
    }

    public DashBoard signIn(String userName, String pass) {

        enterUserName(userName);
        enterPassword(pass);
        clickSignIn();
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException ex) {

        }
        DashBoard dashBoard = new DashBoard(driver);
        return dashBoard;
    }
}
