package com.vanancrm.PageObjects.MainPages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import java.awt.event.KeyEvent;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Captioning extends AccessingElement {

	private WebDriver driver;
    private JavascriptExecutor js;

    @FindBy(id = "getquote")
    private WebElement getQuoteElement;

	@FindBy(id = "emailquote")
	private WebElement emailMeQuoteElement;

    @FindBy(id = "proceedpayment")
    private WebElement proceedPaymentElement;

	@FindBy(id = "srclang_chosen")
	private WebElement sourceLanguageElement;

	@FindBy(id = "trglang_chosen")
	private WebElement targetLanguageElement;

    @FindBy(id = "formatting")
    private WebElement formattingElement;

    @FindBy(id = "hours")
    private WebElement hoursElement;

    @FindBy(id = "minutes")
    private WebElement minutesElement;

    @FindBy(id = "specification_pay_chosen")
    private WebElement specificationPayElement;

    @FindBy(id = "paytc_qemailcrm")
    private WebElement emailElement;

    @FindBy(id = "hlw_amt")
    private WebElement offerFeeElement;

    @FindBy(id = "trgtunitcost_disp")
    private WebElement basePriceElement;

    @FindBy(id = "trgt_tot")
    private WebElement totalUnitCostElement;

    @FindBy(id = "script_amount")
    private WebElement translationCostElement;

    @FindBy(id = "tcode_amount")
    private WebElement timeCodeElement;

    @FindBy(id = "sub_amt")
    private WebElement grandTotalElement;

    @FindBy(id = "trans_rate")
    private WebElement transactionFeeElement;

    @FindBy(id = "price_display")
    private WebElement orderTotalElement;

    @FindBy(id = "filecomments")
    private WebElement comments;

    @FindBy(id = "durationnewpay-0")
    private WebElement fileLength;

    @FindBy(id = "privacy_policy")
    private WebElement privacyPolicy;

    private Actions builder;
    private Action mouseOverHome;

    public Captioning(WebDriver driver) {

		this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
	}

    public void clickEmailMeGetQuote() {
        try {

            if (isElementDisplayed(emailMeQuoteElement)) {
                js.executeScript("$('#emailquote').click();");
            }
        } catch (Exception e) {
            System.out.println("Unable to click Get Email Me Quote button " + e);
        }
    }


    public void clickProceedPayment() {
        try {

            if (isElementDisplayed(proceedPaymentElement)) {
                js.executeScript("$('#proceedpayment').click();");
            }
        } catch (Exception e) {
            System.out.println("Unable to click Proceed payment button " + e);
        }
    }

    public void clickGetQuote() {
        try {

            if (isElementDisplayed(getQuoteElement)) {
                js.executeScript("$('#getquote').click();");
            }
        } catch (Exception e) {
            System.out.println("Unable to click Get Quote button " + e);
        }
    }

    public boolean isCustomMessageDisplayed() {

        return driver.findElement(By.xpath("//div[@class='qt_msg']"))
                .getText().contains("Customized rates apply");
    }

	public void selectSourceLanguage(String sourceLanguage) {
		try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(sourceLanguageElement).build();
            mouseOverHome.perform();
            clickElement(sourceLanguageElement);
            List<WebElement> srcLang = driver.findElements(By.xpath(
                    "//div[@id='srclang_chosen']/div/ul[@class='chosen-results']/li"));
            for (WebElement element : srcLang) {
                if (element.getText().equals(sourceLanguage)) {
                    element.click();
                    break;
                }
            }
			//selectDropDown(sourceLanguageElement, sourceLanguage);
			driver.findElement(By.tagName("body")).click();
		} catch (Exception e) {
			System.out.println("Unable to select source language " + e);
		}
	}

	public void selectTargetLanguage(String targetLanguage) {
		try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(sourceLanguageElement).build();
            mouseOverHome.perform();
            clickElement(targetLanguageElement);

            List<WebElement> tarLang = driver.findElements(By.xpath(
                    "//div[@id='trglang_chosen']/div/ul[@class='chosen-results']/li"));
            for (WebElement element : tarLang) {
                if (element.getText().equals(targetLanguage)) {
                    element.click();
                    break;
                }
            }
			/*selectDropDown(targetLanguageElement, targetLanguage);
			driver.findElement(By.tagName("body")).click();*/
		} catch (Exception e) {
			System.out.println("Unable to select target language " + e);
		}
	}

    public void selectFormatting(String categoryType) {

        selectDropDown(formattingElement, categoryType);
    }

    public void enterHours(String hour) {
        try {
            enterTestBoxValues(hoursElement, hour);
        } catch (Exception e) {
            System.out.println("Unable to enter a hour value " + e);
        }
    }

    public void enterMinutes(String minute) {
        try {
            enterTestBoxValues(minutesElement, minute);
        } catch (Exception e) {
            System.out.println("Unable to enter a minute value " + e);
        }
    }

    public void selectSpecificationPay(String specificPay) {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(sourceLanguageElement).build();
            mouseOverHome.perform();
            clickElement(specificationPayElement);

            List<WebElement> spec = driver.findElements(By.xpath(
                    "//div[@id='specification_pay_chosen']/div/ul[@class='chosen-results']/li"));
            for (WebElement element : spec) {
                if (element.getText().equals(specificPay)) {
                    element.click();
                    break;
                }
            }
            /*selectDropDown(specificationPayElement, specificPay);
            driver.findElement(By.tagName("body")).click();*/
        } catch (Exception e) {
            System.out.println("Unable to select specification pay " + e);
        }
    }

    public void selectFreeTrialPage() {
        if (!driver.findElement(By.xpath(
                "//input[@type='checkbox' and @id='frtrial']"))
                .isSelected()) {

            js.executeScript("$('#frtrial').click();");
        }
    }

    public void selectNeedTranslation() {
        if (!driver.findElement(By.xpath(
                "//input[@type='checkbox' and @id='needtrc']"))
                .isSelected()) {

            js.executeScript("$('#needtrc').click();");
        }
    }

    public void deselectFreeTrialPage() {
        if (driver.findElement(By.xpath(
                "//input[@type='checkbox' and @id='frtrial']"))
                .isSelected()) {

            js.executeScript("$('#frtrial').click();");
        }
    }

    public void deselectNeedTranslation() {
        if (driver.findElement(By.xpath(
                "//input[@type='checkbox' and @id='needtrc']"))
                .isSelected()) {

            js.executeScript("$('#needtrc').click();");
        }
    }

    public double getOfferFee() {

        return convertAndGetValue(offerFeeElement);
    }

    public void enterEmailId(String email) {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(emailElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(emailElement, email);
    }

    public double getBasePrice() {

        return convertAndGetValue(basePriceElement);
    }

    public double getTranslationPrice() {

        return convertAndGetValue(translationCostElement);
    }

    public double getTotalUnitCost() {

        return convertAndGetValue(totalUnitCostElement);
    }

    public double getTimeCodePrice() {

        return convertAndGetValue(timeCodeElement);
    }

    public double getGrandTotal() {

        return convertAndGetValue(grandTotalElement);
    }

    public double getTransactionFee() {

        return convertAndGetValue(transactionFeeElement);
    }

    public double getOrderTotal() {

        return convertAndGetValue(orderTotalElement);
    }

    public double convertAndGetValue(WebElement element) {

        if (element.isDisplayed()) {
            return Double.parseDouble(getElementValues(element));
        } else {
            return 0;
        }
    }

    public void enterComments(String comment) {
        try {
            enterTestBoxValues(comments, comment);
        } catch (Exception e) {
            System.out.println("Unable to enter a comment value " + e);
        }
    }
    public void enterFileLength(String length) {
        try {
            if (fileLength.isDisplayed()) {

                enterTestBoxValues(fileLength, length);
            }
        } catch (Exception e) {
            System.out.println("Unable to enter a file length value " + e);
        }
    }

    public void uploadFile(WebDriver driver, String fileName, String extenstion)
            throws IOException, AWTException, InterruptedException {

        fileName = fileName + extenstion;
        String filePath = System.getProperty("user.dir") + "/" + fileName;
        File file = new File(filePath);
        file.createNewFile();
        TimeUnit.SECONDS.sleep(10);
        WebElement fileUploadButton = driver.findElement(By.id("fileuploader"));
        fileUploadButton.click();
        StringSelection selection = new StringSelection(fileName);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
    public void clickPrivacyPolicy() {
        try {

            if (isElementDisplayed(privacyPolicy)) {
                clickElement(privacyPolicy);
            }
        } catch (Exception e) {
            System.out.println("Unable to click privacy policy " + e);
        }
    }

    public String getToolTipMessage() {

        return driver.findElement(By.xpath("//div[contains(@role,'tooltip')]/div[@class='tooltip-inner']")).getText();
    }
}
