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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
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

    @FindBy(id = "sendnotary")
    private WebElement notaryButton;

    @FindBy(xpath = "//h4/button[@class='close']")
    private WebElement popupCloseBtn;

    @FindBy(xpath = "//div[@class='form-group rate-input']/input[@name='deleverydate']")
    private WebElement deliveryDateElement;

    @FindBy(name = "notary")
    private WebElement notaryType;

    @FindBy(name = "cus_address")
    private WebElement customerAddress;

    @FindBy(name = "comments")
    private WebElement customerComments;

    @FindBy(xpath = "//*[@id='notary_form']/div/div/div[5]/button")
    private WebElement submitButton;

    @FindBy(xpath = "//button[@id='mail_btn']")
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
        return status;
    }

    public void selectDeliveryFile2Customer(String fileName, String
            language) {

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
            if (element.getText().contains(fileName) && driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/p")).getText().toLowerCase().contains(language)) {
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]/td[2]/input[@type='checkbox']"));
                if (element.isSelected() != true) {
                    clickElement(element);
                } else {
                    System.out.println("Already checkbox is selected");
                }
                break;
            }
        }
    }

    public boolean isSelectDeliveryFile2Customer(String fileName, String
            language) {

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
            if (element.getText().contains(fileName) && driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/p")).getText().toLowerCase().contains(language)) {
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]/td[2]/input[@type='checkbox']"));
                status = element.isEnabled();
                break;
            }
        }
        return status;
    }

    public void clickDeliverFilesToCustomerButton() {

        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(deliverFilesToCustomerElement).build();
        clickElement(deliverFilesToCustomerElement);
        mouseOverHome.perform();
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

    public List<String> getDeliveryFileDetails() {

        waitForPageLoad(driver);
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        List<String> status = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {

            status.add(driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/span")).getText() + "#" + driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/p")).getText() + "#" + driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[4]")).getText() + "#" + driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[5]")).getText() + "#" + driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[6]/a")).getText() + "#" + driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[7]")).getText() + "#" + driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[9]/span")).getText());
        }
        return status;
    }

    public void clickMailingNotaryButton() {

        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(notaryButton).build();
        clickElement(notaryButton);
        mouseOverHome.perform();
    }

    public void clickPopupCloseButton() {

        waitForPageLoad(driver);
        clickElement(popupCloseBtn);

    }

    public void selectDeliveryDate(String date) {

        waitForPageLoad(driver);
        clickElement(deliveryDateElement);
        clickElement(driver.findElement(By.xpath
                ("//div[@class='datepicker-days']//../td[@data-day='" + date +
                        "']")));

    }

    public void selectNotaryType(String type) {
        waitForPageLoad(driver);
        selectDropDown(notaryType, type);
    }

    public void enterCustomerAddress(String details) {

        waitForPageLoad(driver);
        enterTestBoxValues(customerAddress, details);
    }

    public void enterCustomerComments(String comment) {

        waitForPageLoad(driver);
        enterTestBoxValues(customerComments, comment);
    }

    public void clickPopupSubmitButton() {

        waitForPageLoad(driver);
        clickElement(submitButton);
    }

}
