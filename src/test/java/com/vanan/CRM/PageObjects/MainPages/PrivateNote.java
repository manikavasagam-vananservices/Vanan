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

public class PrivateNote extends AccessingElement {

	private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

	public PrivateNote(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
	}

	@FindBy(linkText = "Payment Made Private Note")
	private WebElement paymentMadePrivateNoteElement;

	public  void clickPaymentMadePrivateNote() {
	    clickElement(paymentMadePrivateNoteElement);
    }
}
