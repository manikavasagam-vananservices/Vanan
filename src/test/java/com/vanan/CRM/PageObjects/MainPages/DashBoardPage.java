package com.vanan.CRM.PageObjects.MainPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanan.CRM.PageObjects.WholeSitePages.Menus;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

import java.util.concurrent.TimeUnit;

public class DashBoardPage extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;
    private Actions builder;
    private Action mouseOverHome;

    @FindBy(xpath = "//ul[@class='sidebar-menu']/li[@class='treeview scroll']/a")
    private WebElement allprocess;

    @FindBy(id = "allocator_queue")
    private WebElement myqueue;

    @FindBy(id = "auto_UnknownEmail")
    private WebElement unknownMail;

    @FindBy(id = "auto_PROCESS")
    private WebElement processElement;

    public DashBoardPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public Menus clickAllProcess() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(allprocess).build();
        mouseOverHome.perform();
        clickElement(allprocess);
        Menus menus = new Menus(driver);
        return menus;
    }

    public Menus clickUnknownMail() {

        waitForPageLoad(driver);
        clickElement(unknownMail);
        Menus menus = new Menus(driver);
        return menus;
    }

    public void clickSignOut() {

        waitForPageLoad(driver);
        WebElement element = driver.findElement(By.className("user-image"));
        element.click();
        element = driver.findElement(By.linkText("Sign out"));
        element.click();
    }

    public void enableMyQueue() {

        waitForPageLoad(driver);
        if (myqueue.isSelected()) {
            System.out.println("All ready My Queue is Enabled");
        } else {
            js.executeScript("$('#allocator_queue').click();");
            System.out.println("My Queue is Enabled");
        }

    }

    public void disableMyQueue() {

        waitForPageLoad(driver);
        if (myqueue.isSelected()) {
            js.executeScript("$('#allocator_queue').click();");
            System.out.println("My Queue is Disabled");
        } else {
            System.out.println("All ready My Queue is Disabled");
        }

    }

    public Menus clickProcess() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(processElement).build();
        mouseOverHome.perform();
        clickElement(processElement);
        Menus menus = new Menus(driver);
        return menus;
    }
}
