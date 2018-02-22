package com.vanan.CRM.PageObjects.MainPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanan.CRM.PageObjects.WholeSitePages.Menus;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

public class DashBoardPage extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;

    @FindBy(xpath = "//ul[@class='sidebar-menu']/li[@class='treeview scroll']/a")
    private WebElement allprocess;

    @FindBy(id = "allocator_queue")
    private WebElement myqueue;

    @FindBy(id = "auto_UnknownEmail")
    private WebElement unknownMail;

    public DashBoardPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public Menus clickAllProcess() {

        clickElement(allprocess);
        Menus menus = new Menus(driver);
        return menus;
    }

    public Menus clickUnknownMail() {

        clickElement(unknownMail);
        Menus menus = new Menus(driver);
        return menus;
    }

    public void clickSignOut() {

        WebElement element = driver.findElement(By.className("user-image"));
        element.click();
        element = driver.findElement(By.linkText("Sign out"));
        element.click();
    }

    public void enableMyQueue() {

        if (myqueue.isSelected()) {
            System.out.println("All ready My Queue is Enabled");
        } else {
            js.executeScript("$('#allocator_queue').click();");
            System.out.println("My Queue is Enabled");
        }

    }

    public void disableMyQueue() {

        if (myqueue.isSelected()) {
            js.executeScript("$('#allocator_queue').click();");
            System.out.println("My Queue is Disabled");
        } else {
            System.out.println("All ready My Queue is Disabled");
        }

    }
}
