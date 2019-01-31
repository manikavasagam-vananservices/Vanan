package com.vanan.CRM.PageObjects.MainPages;

import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.concurrent.TimeUnit.SECONDS;

// test

public class Edit extends AccessingElement {

	private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

    @FindBy(id = "process_id")
    private WebElement status;

    @FindBy(id = "closure_possibility")
    private WebElement posibileClosureElement;

	@FindBy(id = "update_btn")
	private WebElement updateButton;

    @FindBy(name = "service_frequency")
    private WebElement serviceFrequencyButton;

    @FindBy(name = "assigned_alloc")
    private WebElement allocatorElement;

    @FindBy(name = "mode_of_payment")
    private WebElement paymentModeElement;
	
    @FindBy(name = "payment_type")
    private WebElement paymentTypeElement;
	
	@FindBy(linkText = "Upload")
	private WebElement fileUploadButtonElement;

	@FindBy(name = "order_value")
	private WebElement orderValueElement;

    @FindBy(xpath = "//div[@class='col-sm-7']" +
            "/input[@type='text' and @name='customer_email']")
    private WebElement mailIdElement;

	@FindBy(id = "expected_turnaround")
    private WebElement etatElement;

    @FindBy(name = "keyword")
    private WebElement keywordElement;

    @FindBy(name = "purpose")
    private WebElement purposeElement;

    @FindBy(name = "purposevoiceover")
    private WebElement voiceOverPurposeElement;

    @FindBy(name = "assigned_to")
    private WebElement salesElement;

    @FindBy(xpath = "//input[@type='checkbox' and @name='additionalqc']")
    private WebElement qcElement;

    @FindBy(xpath = "//input[@type='checkbox' and @name='certificate']")
    private WebElement certificateElement;

    @FindBy(xpath = "//input[@type='checkbox' and @name='timecode']")
    private WebElement timecodeElement;

    @FindBy(xpath = "//input[@type='checkbox' and @name='notary']")
    private WebElement notaryElement;

    @FindBy(xpath = "//input[@type='checkbox' and @name='mailed']")
    private WebElement mailElement;

    @FindBy(xpath = "//select[@name='priority_id']")
    private WebElement priorityElement;

    @FindBy(name = "mailing_option")
    private WebElement mailOptionElement;

    @FindBy(name = "mail_address")
    private WebElement mailAddressElement;

    @FindBy(xpath = "//input[@type='checkbox' and @name='verbatim']")
    private WebElement verbatimElement;

    @FindBy(xpath = "//input[@type='checkbox' and @name='native_speaker']")
    private WebElement nativeElement;

    public Edit(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public void selectStatus(String option) {
		try {
			selectDropDown(status, option);
		} catch (Exception e) {
			System.out.println("Unable to select status " + e);
		}
	}

    public void selectPriority(String option) {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(priorityElement).build();
            mouseOverHome.perform();
            selectDropDown(priorityElement, option);
        } catch (Exception e) {
            System.out.println("Unable to select Priority " + e);
        }
    }

    public void selectPossibleClosure(String option) {
        try {
            selectDropDown(posibileClosureElement, option);
        } catch (Exception e) {
            System.out.println("Unable to select Possible Closure " + e);
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

  public void selectPaymentType(String option) {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(paymentTypeElement).build();
            mouseOverHome.perform();
            selectDropDown(paymentTypeElement, option);
        } catch (Exception e) {
            System.out.println("Unable to select payment mode " + e);
        }
    }
	public void clickUpdateButton() {
		try {
		    builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(updateButton).build();
            mouseOverHome.perform();
			clickElement(updateButton);
            waitForPageLoad(driver);
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

    public void enterEmailId(String mail) {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(mailIdElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(mailIdElement, mail);
    }

    public void enterETAT(String etatDate) {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(etatElement).build();
        mouseOverHome.perform();
        etatElement.click();
        /* Alternate way to display datepicket tool tip
        js.executeScript("$('#expected_turnaround').data('DateTimePicker')" +
                ".show();");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception ex) {

        }*/
        WebElement element = driver.findElement(By.xpath
                ("//div[@class='datepicker-days']//../td[@data-day='" + etatDate +
                        "']"));
        element.click();
        driver.findElement(By.tagName("body")).click();
    }

    public void enterKeyword(String keyword) {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(keywordElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(keywordElement, keyword);
    }

    public void enterPurpose(String content) {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(purposeElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(purposeElement, content);
    }

    public void selectVoiceOverPurpose(String content) {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(voiceOverPurposeElement).build();
            mouseOverHome.perform();
            selectDropDown(voiceOverPurposeElement, content);
        } catch (Exception e) {
            System.out.println("Unable to select voice over purpose " + e);
        }
    }

    public void selectSalesPerson(String option) {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(salesElement).build();
            mouseOverHome.perform();
            selectDropDown(salesElement, option);
        } catch (Exception e) {
            System.out.println("Unable to select sales person " + e);
        }
    }

    public void clickAdditionalQC() {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(qcElement).build();
            mouseOverHome.perform();
            clickElement(qcElement);
        } catch (Exception e) {
            System.out.println("Unable to click the additional qc" + e);
        }
    }

    public void clickNotarization() {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(notaryElement).build();
            mouseOverHome.perform();
            clickElement(notaryElement);
        } catch (Exception e) {
            System.out.println("Unable to clcik the Notary " + e);
        }
    }

    public void clickMailing() {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(mailElement).build();
            mouseOverHome.perform();
            clickElement(mailElement);
        } catch (Exception e) {
            System.out.println("Unable to click the mailing " + e);
        }

    }

    public void clickVerbatim() {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(verbatimElement).build();
            mouseOverHome.perform();
            clickElement(verbatimElement);
        } catch (Exception e) {
            System.out.println("Unable to click Verbatim " + e);
            ;
        }
    }

    public void clickTimecode() {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(timecodeElement).build();
            mouseOverHome.perform();
            clickElement(timecodeElement);
        } catch (Exception e) {
            System.out.println("Unable to click Timecode " + e);
        }

    }

    public void clickNative() {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(nativeElement).build();
            mouseOverHome.perform();
            clickElement(nativeElement);
        } catch (Exception e) {
            System.err.println("Unable to click Native " + e);
        }

    }
}
