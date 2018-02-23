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

public class Delivery extends AccessingElement {

	private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

	public Delivery(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
	}

	@FindBy(linkText = "Payment Made Private Note")
	private WebElement paymentMadePrivateNoteElement;

    @FindBy(xpath="//table[@id='process_list']/tbody/tr")
    private List<WebElement> tableRows;

    @FindBy(xpath="//button[contains(text(), 'Deliver Files to Customer')]")
    private WebElement deliverFilesToCustomerElement;

    @FindBy(xpath="//button[@id='mail_btn']")
    private WebElement sendEmailToCustomerElement;

    @FindBy(id="allocate_btn")
    private WebElement allocateButton;
    public  void clickAllocateFile() {
	    clickElement(allocateButton);
    }

    public int getCurrentRowNumber(String fileName) {

        WebElement fileElement;
        int rowNumber = 0;
        for (int i=1; i< getTableRowSize(); i++) {
            fileElement = driver.findElement(By.xpath(
                    "//table[@id='process_list']/tbody/tr["
                            + i + "]/td[2]/a/span"));
            if(getElementValues(fileElement).equals(fileName)) {
                rowNumber = i;
                break;
            }
        }
        return rowNumber;
    }

	private int getTableRowSize() {

		return  tableRows.size();
	}

	public boolean selectDeliveryFileToCustomer(String fileName, String
            vendorName) {

        waitForPageLoad(driver);
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        boolean status = false;
        for (int i = 0; i < elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/span"));
            //System.out.println("Entering)))))))" + element.getText());
            if(element.getText().contains(fileName)) {
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]/td[2]/input[@type='checkbox']"));
                if(element.isSelected()!=true) {
                    clickElement(element);
                } else {
                    System.out.println("Already checkbox is selected");
                }
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]/td[6]/a"));

                if(element.getText().contains(vendorName)){
                    status = true;
                }
                break;
            }
        }
        return  status;
	}

	public void clickDeliverFilesToCustomerButton() {

        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(deliverFilesToCustomerElement).build();
        mouseOverHome.perform();
        clickElement(deliverFilesToCustomerElement);
    }

    public void clickSendEmailToCustomerButton() {

        waitForPageLoad(driver);
        checkAlert();
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(sendEmailToCustomerElement).build();
        mouseOverHome.perform();
        clickElement(sendEmailToCustomerElement);
        waitForPageLoad(driver);
    }

    private void checkAlert() {

        if (isAlertPresents()) {
            driver.switchTo().alert().accept();
            driver.switchTo().defaultContent();
            waitForPageLoad(driver);
        }
    }

    public boolean isAlertPresents() {
        try {
            driver.switchTo().alert();
            return true;
        }// try
        catch (Exception e) {
            return false;
        }// catch
    }


    public void waitForProcessCompletion(int waitTime) {
        try {
            TimeUnit.SECONDS.sleep(waitTime);
        } catch (Exception e) {
        }
    }
}
