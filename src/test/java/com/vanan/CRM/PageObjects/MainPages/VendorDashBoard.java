package com.vanan.CRM.PageObjects.MainPages;

import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class VendorDashBoard extends AccessingElement {

    private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

    public VendorDashBoard(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(id = "auto_Allocator_NewFiles")
    private WebElement newfilesElement;

    @FindBy(id = "auto_Allocator_Accepted")
    private WebElement acceptedElement;

    @FindBy(id = "auto_Allocator_Inprocess")
    private WebElement inprogressElement;

    @FindBy(id = "auto_Allocator_Completed")
    private WebElement completedElement;

    @FindBy(id = "fileAcceptBtn")
    private WebElement fileAcceptBtnElement;

    @FindBy(xpath = "//button[contains(text(),'Reject')]")
    private WebElement fileRejectBtnElement;

    @FindBy(id = "startWorkBtn")
    private WebElement startworkBtnElement;

    @FindBy(xpath = "//a[contains(text(),'Upload')]")
    private WebElement uploadElement;

    @FindBy(xpath = "//table[@id='order_lists']/tbody/tr")
    private List<WebElement> tableRows;

    @FindBy(id = "reject_category")
    private WebElement rejectReason;

    @FindBy(id = "reject_reason")
    private WebElement rejectMessage;

    @FindBy(id="reject_submit")
    private WebElement rejectSubmitBtn;

    public void clickNewFiles() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(newfilesElement).build();
        clickElement(newfilesElement);
        mouseOverHome.perform();
    }

    public void clickAccepted() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(acceptedElement).build();
        clickElement(acceptedElement);
        mouseOverHome.perform();
    }

    public void clickReject() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(fileRejectBtnElement).build();
        clickElement(fileRejectBtnElement);
        mouseOverHome.perform();
    }

    public void clickCompleted() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(completedElement).build();
        clickElement(completedElement);
        mouseOverHome.perform();
    }

    public void clickInprogress() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(inprogressElement).build();
        clickElement(inprogressElement);
        mouseOverHome.perform();
    }

    public void clickAcceptButton() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(fileAcceptBtnElement).build();
        mouseOverHome.perform();
        clickElement(fileAcceptBtnElement);
    }

    public void clickStartWorkButton() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(startworkBtnElement).build();
        mouseOverHome.perform();
        clickElement(startworkBtnElement);
    }

    public void clickUploadButton() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(uploadElement).build();
        mouseOverHome.perform();
        clickElement(uploadElement);
    }


    private int getTableRowSize() {

        return tableRows.size();
    }

    public void selectRejectReason(String reason) {

        waitForPageLoad(driver);
        selectDropDown(rejectReason, reason);
    }

    public void enterRejectReason(String reason) {

        waitForPageLoad(driver);
        enterTestBoxValues(rejectMessage, reason);
    }

    public void clickRejectSubmitButton() {

       waitForPageLoad(driver);
       clickElement(rejectSubmitBtn);
    }

    public String clickParticularFile(String fileName, String service) {

        WebElement element;
        String allocationId = "";
        waitForPageLoad(driver);
        for (int i = 1; i <= getTableRowSize(); i++) {


            element = driver.findElement(By.xpath
                    ("//table[@id='order_lists_allocator']/tbody/tr[" + i + "]/td[4]"));
            if (element.getText().contains(fileName)) {
                element = driver.findElement(By.xpath
                        ("//table[@id='order_lists_allocator']/tbody/tr[" + i + "]/td[10]"));
                if (element.getText().contains(service)) {
                    clickElement(driver.findElement(By.xpath
                            ("//table[@id='order_lists_allocator']/tbody/tr[" + i +
                                    "]/td[4]")));
                    allocationId = driver.findElement(By.xpath
                            ("//table[@id='order_lists_allocator']/tbody/tr[" + i +
                                    "]/td[3]")).getText();
                    break;
                }
            }
        }
        return allocationId;
    }

    public List<String> getAllocationIds() {

        List<String> allocationIds = new ArrayList<>();

        for (int i = 1; i <= getTableRowSize(); i++) {
            allocationIds.add(driver.findElement(By.xpath
                    ("//table[@id='order_lists_allocator']/tbody/tr[" + i +
                            "]/td[3]")).getText());
        }
        return allocationIds;
    }

}
