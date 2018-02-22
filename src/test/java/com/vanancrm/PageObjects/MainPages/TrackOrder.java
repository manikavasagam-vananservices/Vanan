package com.vanancrm.PageObjects.MainPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TrackOrder extends AccessingElement {

    private WebDriver driver;

    public TrackOrder(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "ticket-id")
    private WebElement ticketIdElement;

    @FindBy(id = "email-id")
    private WebElement emailIdElement;

    @FindBy(id = "check-ticket")
    private WebElement submitButtonElement;

    @FindBy(id = "order-id")
    private WebElement orderIdElement;

    @FindBy(id = "order-date")
    private WebElement orderDateElement;

    @FindBy(id = "order-status")
    private WebElement orderStatusElement;

    @FindBy(className = "order_details")
    private WebElement orderTable;

    @FindBy(id = "not-found")
    private WebElement warningElement;

    public void enterTicketId(String ticketId) {
        try {
            enterTestBoxValues(ticketIdElement, ticketId);
        } catch (Exception e) {
            System.out.println("Unable to enter a ticket id value " + e);
        }
    }

    public void enterEmailId(String emailid) {
        try {
            enterTestBoxValues(emailIdElement, emailid);
        } catch (Exception e) {
            System.out.println("Unable to enter a email id value " + e);
        }
    }

    public void clickSubmit() {
        try {
            clickElement(submitButtonElement);
        } catch (Exception e) {
            System.out.println("Unable to click submit button " + e);
        }
    }

    public boolean isOrderTableDisplayed() {

        return orderTable.isDisplayed();
    }

    public boolean isWarningMessageDisplayed() {

        return warningElement.isDisplayed();
    }

    public String getOrderId() {

        return orderIdElement.getText();
    }

    public String getOrderDate() {

        return orderDateElement.getText();
    }

    public String getOrderStatus() {

        return orderStatusElement.getText();
    }

    public String getOrderInProgressStatus() {

        return driver.findElement(By.xpath("//div[@id='in-process-block']/div/" +
                "span[@id='del-ic']")).getAttribute("class");
    }


    public String getOrderDeliveredStatus() {

        return driver.findElement(By.xpath("//div[@id='in-delivered-block']/" +
                "strong[@id='del-status']")).getAttribute("class");
    }
}
