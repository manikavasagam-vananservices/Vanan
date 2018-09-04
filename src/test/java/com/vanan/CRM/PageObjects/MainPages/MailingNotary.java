package com.vanan.CRM.PageObjects.MainPages;

import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;
import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MailingNotary extends AccessingElement {

    private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

    public MailingNotary(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(id = "status")
    private WebElement statusElement;

    @FindBy(xpath = "//*[@id='notary_form']/div[24]/div/p/a")
    private WebElement notaryFileUploadElement;

    @FindBy(xpath = "//*[@id='notary_form']/div[25]/div/p/a")
    private WebElement trackingDocFileUploadElement;

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    private WebElement updateBtnElement;

    public void clickParticularTicket(String ticketid, String service) {

        waitForPageLoad(driver);
        List<WebElement> tickets = driver.findElements(By.xpath("//tbody[@id='dynamic_row']/tr"));

        for (int i = 1; i <= tickets.size(); i++) {
            System.out.print(driver.findElement(By.xpath("//tbody[@id='dynamic_row']/tr["+i+"]/td[2]/a")).getText()
                    +"*****"+
                    driver.findElement(By.xpath("//tbody[@id='dynamic_row']/tr["+i+"]/td[4]")).getText());
            if(driver.findElement(By.xpath("//tbody[@id='dynamic_row']/tr["+i+"]/td[2]/a")).getText().contains(ticketid)
                    &&
                    driver.findElement(By.xpath("//tbody[@id='dynamic_row']/tr["+i+"]/td[4]")).getText().contains(service)) {
                clickElement(driver.findElement(By.xpath("//tbody[@id='dynamic_row']/tr["+i+"]/td[4]")));
                break;
            }
        }
    }


    public void selectStatus(String type) {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(statusElement).build();
        selectDropDown(statusElement, type);
        mouseOverHome.perform();

    }

    public void clickNotaryScanFileUpload() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(notaryFileUploadElement).build();
        clickElement(notaryFileUploadElement);
        mouseOverHome.perform();
    }

    public void clickTrackingDocumentFileUpload() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(trackingDocFileUploadElement).build();
        clickElement(trackingDocFileUploadElement);
        mouseOverHome.perform();
    }

    public void clickUpdateButton() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(updateBtnElement).build();
        clickElement(updateBtnElement);
        mouseOverHome.perform();
    }

    public boolean isticketDisplayed(String ticketid, String service) {

        List<WebElement> tickets = driver.findElements(By.xpath("//tbody[@id='dynamic_row']/tr"));
        boolean status = false;
        for (int i = 1; i <= tickets.size(); i++) {

            if(driver.findElement(By.xpath("//tbody[@id='dynamic_row']/tr["+i+"]/td[2]/a")).getText().contains(ticketid)
                    &&
                    driver.findElement(By.xpath("//tbody[@id='dynamic_row']/tr["+i+"]/td[4]")).getText().contains(service)) {
                status = true;
                break;
            }
        }
        return status;
    }
}
