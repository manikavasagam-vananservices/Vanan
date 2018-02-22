package com.vanan.CRM.PageObjects.MainPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

public class Edit extends AccessingElement {

	private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

	public Edit(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
	}

	@FindBy(id = "process_id")
	private WebElement status;

	@FindBy(id = "update_btn")
	private WebElement updateButton;

	@FindBy(id = "service_frequency")
	private WebElement serviceFrequencyButton;

    @FindBy(name = "assigned_alloc")
    private WebElement allocatorElement;

    @FindBy(name = "mode_of_payment")
    private WebElement paymentModeElement;

	@FindBy(linkText = "Upload")
	private WebElement fileUploadButtonElement;

	@FindBy(name = "order_value")
	private WebElement orderValueElement;

	@FindBy(id = "expected_turnaround")
    private WebElement etatElement;

	public void selectStatus(String option) {
		try {
			selectDropDown(status, option);
		} catch (Exception e) {
			System.out.println("Unable to select status " + e);
		}
	}

    public void selectServiceFrequency(String option) {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(serviceFrequencyButton).build();
            mouseOverHome.perform();
            selectDropDown(serviceFrequencyButton, option);
        } catch (Exception e) {
            System.out.println("Unable to select service frequency " + e);
        }
    }

    public void selectAllocator(String option) {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(allocatorElement).build();
            mouseOverHome.perform();
            selectDropDown(allocatorElement, option);
        } catch (Exception e) {
            System.out.println("Unable to select allocator " + e);
        }
    }

    public void selectPaymentMode(String option) {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(paymentModeElement).build();
            mouseOverHome.perform();
            selectDropDown(paymentModeElement, option);
        } catch (Exception e) {
            System.out.println("Unable to select payment mode " + e);
        }
    }

	public void clickUpdateButton() {
		try {
			clickElement(updateButton);
		} catch (Exception e) {
			System.out.println("Unable to click update button " + e);
		}
	}

	public void clickSignOut() {

		WebElement element = driver.findElement(By.className("user-image"));
		element.click();
		element = driver.findElement(By.linkText("Sign out"));
		element.click();
	}

	public void clickFileUploadButton() {
		try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(fileUploadButtonElement).build();
            mouseOverHome.perform();
			clickElement(fileUploadButtonElement);
		} catch (Exception e) {
			System.out.println("Unable to click file upload button " + e);
		}
	}

	public void enterOrderValue(String amount) {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(orderValueElement).build();
        mouseOverHome.perform();
	    enterTestBoxValues(orderValueElement, amount);
    }

    public void enterETAT(String etatDate) {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(etatElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(etatElement, etatDate);
        driver.findElement(By.tagName("body")).click();
    }
}
