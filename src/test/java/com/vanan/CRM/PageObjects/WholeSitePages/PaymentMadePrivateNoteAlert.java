package com.vanan.CRM.PageObjects.WholeSitePages;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class PaymentMadePrivateNoteAlert extends AccessingElement {

	private WebDriver driver;
	private JavascriptExecutor js;

	public PaymentMadePrivateNoteAlert(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
	}

	@FindBy(name = "approvedby")
	private WebElement approvedByElement;

	@FindBy(name = "paymentdate")
	private WebElement paymentDateElement;

	@FindBy(name = "paid_now")
	private WebElement paidAmountElement;

	@FindBy(xpath = "//span[text()='Submit']")
	private WebElement signin;

	@FindBy(id = "paid_mode")
	private WebElement paidModeElement;

    @FindBy(xpath ="//form[@id='paid_privatenote']//*//div[@class='modal-header" +
            "']/span[@class='close']")
    private WebElement closeElement;

	public void selectApprovedBy(String username) {
		try {
            selectDropDown(approvedByElement, username);
		} catch (Exception e) {
			System.out.println("Unable to enter a approved by user " + e);
		}
	}

	public void enterPaymentDate(String paymentDate) {
		try {
			enterTestBoxValues(paymentDateElement, paymentDate);
            driver.findElement(By.tagName("body")).click();
		} catch (Exception e) {
			System.out.println("Unable to enter a payment date " + e);
		}
	}

	public void enterPaidAmount(String paidAmount) {
		try {
			enterTestBoxValues(paidAmountElement, paidAmount);
		} catch (Exception e) {
			System.out.println("Unable to enter a paid amount " + e);
		}
	}

    public void selectPaymentMode(String paymentMode) {
        try {
            selectDropDown(paidModeElement, paymentMode);
        } catch (Exception e) {
            System.out.println("Unable to select payment mode " + e);
        }
    }

    public void clickSubmit() {
		
		clickElement(signin);
	}

    public void clickClose() {

        clickElement(closeElement);
    }

}
