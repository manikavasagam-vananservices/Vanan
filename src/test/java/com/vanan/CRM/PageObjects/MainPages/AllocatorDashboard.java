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

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AllocatorDashboard extends AccessingElement {

    private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

    @FindBy(linkText = "Read more")
    private WebElement readMoreButton;

    @FindBy(linkText = "No Action")
    private WebElement noActionButton;

    @FindBy(id = "edit_btn")
    private WebElement editMenuElement;

    @FindBy(id = "mail_discussion_btn")
    private WebElement emailConversationMenuElement;

    @FindBy(id = "edit_order_submit")
    private WebElement editOrderSubmitElement;

    @FindBy(id = "status_list")
    private WebElement statusListElement;

    @FindBy(id = "change_status")
    private WebElement changeStatusElement;

    @FindBy(xpath = "//table[@id='order_lists']/tbody/tr")
    private List<WebElement> tableRows;

    @FindBy(id = "bulk_status")
    private WebElement bulkStatusElement;

    public AllocatorDashboard(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    private int getTableRowSize() {

        return tableRows.size();
    }

    public void selectFile(String fileName, String allocatorAssignee,
                           String vendorName) {
        WebElement element;

        waitForPageLoad(driver);
        for (int i = 1; i <= getTableRowSize(); i++) {

            element = driver.findElement(By.xpath
                    ("//table[@id='order_lists']/tbody/tr[" + i + "]/td[3]"));
            if (element.getText().contains(allocatorAssignee)) {

                element = driver.findElement(By.xpath
                        ("//table[@id='order_lists']/tbody/tr[" + i + "]/td[4]"));
                if (element.getText().contains(vendorName)) {
                    element = driver.findElement(By.xpath
                            ("//table[@id='order_lists']/tbody/tr[" + i + "]/td[6]"));
                    if (element.getText().contains(fileName)) {
                        clickElement(driver.findElement(By.xpath
                                ("//table[@id='order_lists']/tbody/tr[" + i + "]/td[1]/input[@type='checkbox']")));
                        break;
                    }
                }
            }
        }
    }

    public void clickFileInCompletedSection(String fileName, String
            allocatorAssignee, String vendorName) {
        WebElement element;

        waitForPageLoad(driver);
        for (int i = 1; i <= getTableRowSize(); i++) {

            element = driver.findElement(By.xpath
                    ("//table[@id='order_lists']/tbody/tr[" + i + "]/td[3]"));
            if (element.getText().contains(allocatorAssignee)) {

                element = driver.findElement(By.xpath
                        ("//table[@id='order_lists']/tbody/tr[" + i + "]/td[4]"));
                if (element.getText().contains(vendorName)) {
                    element = driver.findElement(By.xpath
                            ("//table[@id='order_lists']/tbody/tr[" + i + "]/td[6]"));
                    if (element.getText().contains(fileName)) {
                        clickElement(driver.findElement(By.xpath
                                ("//table[@id='order_lists']/tbody/tr[" + i +
                                        "]/td[3]")));
                        break;
                    }
                }
            }
        }
    }

    public void selectStatus(String option) {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(bulkStatusElement).build();
            mouseOverHome.perform();
            selectDropDown(bulkStatusElement, option);
        } catch (Exception e) {
            System.out.println("Unable to select bulk status " + e);
        }
    }

    public void clickChangeStatus() {
        try {
            clickElement(changeStatusElement);
        } catch (Exception e) {
            System.out.println("Unable to click update button " + e);
        }
    }


    public void clickEditMenu() {
        try {
            waitForPageLoad(driver);
            if(editMenuElement.isDisplayed()) {
                clickElement(editMenuElement);
            }
        } catch (Exception e) {
            System.out.println("Unable to click edit menu " + e);
        }
    }

    public void clickEmailConversationMenu() {
        try {
            waitForPageLoad(driver);
            if(emailConversationMenuElement.isDisplayed()) {
                js.executeScript("$('#mail_discussion_btn').click();");
            }
        } catch (Exception e) {
            System.out.println("Unable to click edit menu " + e);
        }
    }

    public void selectPopupStatus(String option) {
        try {
            waitForPageLoad(driver);
            selectDropDown(statusListElement, option);
        } catch (Exception e) {
            System.out.println("Unable to select bulk status " + e);
        }
    }


    public void clickEditSummarySubmit() {
        try {
            waitForPageLoad(driver);
            if(editOrderSubmitElement.isDisplayed()) {
                js.executeScript("$('#edit_order_submit').click();");
            }
            waitForPageLoad(driver);
        } catch (Exception e) {
            System.out.println("Unable to click edit menu " + e);
        }
    }

    public void waitForProcessCompletion(int waitTime) {
        try {
            TimeUnit.SECONDS.sleep(waitTime);
        } catch (Exception e) {
        }
    }

    public void clickReadMore() {
        waitForPageLoad(driver);
        clickElement(readMoreButton);
    }

    public void clickNoActionButton() {


        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(noActionButton).build();
        mouseOverHome.perform();
        clickElement(noActionButton);
        waitForPageLoad(driver);
    }
}