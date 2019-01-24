package com.vanancrm.PageObjects.MainPages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;

import java.awt.datatransfer.StringSelection;

import java.awt.event.KeyEvent;

import java.io.File;
import java.io.IOException;

import java.util.Random;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Translation extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;

    @FindBy(id = "trgtunitcost_disp")
    private WebElement actualCost;

    @FindBy(id = "expd_sub_amt")
    private WebElement expeditedCost;

    @FindBy(id = "nota_sub_amt")
    private WebElement notaryFee;

    @FindBy(id = "notapro_sub_amt")
    private WebElement processFee;

    @FindBy(id = "price_display")
    private WebElement grandTotalElement;

    @FindBy(id = "mfile_sub_amt")
    private WebElement mailingFeeElement;

    @FindBy(id = "hlw_amt")
    private WebElement offerFeeElement;

    @FindBy(id = "cert_sub_amt")
    private WebElement certificationFeeElement;

    @FindBy(id = "qcheck_sub_amt")
    private WebElement additionalQualityAmountElement;

    @FindBy(id = "trans_rate")
    private WebElement transactionFeeElement;

    @FindBy(id = "sub_amt")
    private WebElement subTotalElement;

    @FindBy(id = "trgt_tot")
    private WebElement translationCostElement;

    @FindBy(xpath = "//div[@id='first5']//..//select[@id='sourcefiletype']")
    private WebElement selectFileTypeElement;

    @FindBy(name = "pagecount")
    private WebElement pageCountElement;

    @FindBy(id = "minutes")
    private WebElement minuteElement;

    @FindBy(id = "hours")
    private WebElement hourElement;

    @FindBy(id = "doctype")
    private WebElement documentElement;

    @FindBy(id = "srclang")
    private WebElement selectSourceLanguageFromElement;

    @FindBy(id = "trglang")
    private WebElement selectSourceLanguageToElement;

    @FindBy(id = "catetype")
    private WebElement categoryElement;

    @FindBy(id = "mailstate")
    private WebElement mailCountry;

    @FindBy(id = "filecomments")
    private WebElement fileCommentElement;

    @FindBy(id = "emailquote")
    private WebElement emailMeQuote;

    @FindBy(id = "proceedpayment")
    private WebElement proceedPayment;

    @FindBy(id = "getquote")
    private WebElement getQuote;

    @FindBy(id = "privacy_policy")
    private WebElement privacyPolicy;

    @FindBy(id = "filecomments")
    private WebElement comments;

    @FindBy(id = "durationnewpay-0")
    private WebElement fileLength;
	
    public void clickBusiness(){
        try {

            if (driver.findElement(By.xpath("//span[@class='lbl ui_lbl_radio custom-control-description' and text()='Business']")).isEnabled())
                driver.findElement(By.xpath("//span[@class='lbl ui_lbl_radio custom-control-description' and text()='Business']")).click();
            {
                System.out.println("Business Purpose");
            }
        } catch (Exception e) {
            System.out.println("Unable to click Business" + e);
        }
    }

    public Translation(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public void selectFileType(String fileType) {
        try {
            selectDropDown(selectFileTypeElement, fileType);
           // driver.findElement(By.tagName("body")).click();
        } catch (Exception e) {
            System.out.println("Unable to select file type " + e);
        }

    }

    public void enterDocumentType(String dname) {
        try {
               enterTestBoxValues(documentElement, dname);

        } catch (Exception e) {
            System.out.println("Unable to enter a document type value " + e);
        }
    }

    public void pageCount(String pageCounts) {
        try {
            //pageCountElement.sendKeys(pageCounts);
            enterTestBoxValues(pageCountElement, pageCounts);
        } catch (Exception e) {
            System.out.println("Unable to enter a page count value " + e);
        }
    }

    public void minutes(String minute) {
        try {
            enterTestBoxValues(minuteElement, minute);
        } catch (Exception e) {
            System.out.println("Unable to enter a minute value " + e);
        }
    }

    public String getMinutes() {
        String status = "";
        try {
            status = driver.findElement(By.xpath("//input[@id='minutes']")).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Unable to get a minute value " + e);
        }
        return status;
    }

    public void hours(String hour) {
        try {
            enterTestBoxValues(hourElement, hour);
        } catch (Exception e) {
            System.out.println("Unable to enter a hour value " + e);
        }
    }

    public String getHours() {
        String status = "";
        try {
            status = driver.findElement(By.xpath("//input[@id='hours']")).getAttribute("value");
        } catch (Exception e) {
            System.out.println("Unable to get a hour value " + e);
        }
        return status;
    }

	public void selectSourceLanguageFrom(String selectLanguage) {

		selectDropDown(selectSourceLanguageFromElement, selectLanguage);
	}

	public void selectLanguageFrom(String sourceLanguage) {
		try {
			selectDropDown(selectSourceLanguageFromElement, sourceLanguage);
			driver.findElement(By.tagName("body")).click();
		} catch (Exception e) {
			System.out.println("Unable to select source language " + e);
		}
	}

    public String getSourceLanguage() {
        String status = "";
        try {
            status = getSelectedDropDownValue(selectSourceLanguageFromElement);
        } catch (Exception e) {
            System.out.println("Unable to get source language " + e);
        }
        return status;
    }

    public void deselectSourceLanguageFrom() {
        selectDropDown(selectSourceLanguageFromElement, " ");
    }

	public void deselectFileType() throws TimeoutException {

		selectDropDown(selectFileTypeElement, "");
	}
    
    public void selectSourceLanguageTo(String selectLanguage) {
    	
    	selectDropDown(selectSourceLanguageToElement, selectLanguage);
	}

	public void selectLanguageTo(String targetLanguage) {
		try {
			selectDropDown(selectSourceLanguageToElement, targetLanguage);
			driver.findElement(By.tagName("body")).click();
		} catch (Exception e) {
			System.out.println("Unable to select target language " + e);
		}
	}

    public String getLanguageTo() {
        String status = "";
        try {
            status = getSelectedDropDownValue(selectSourceLanguageToElement);
        } catch (Exception e) {
            System.out.println("Unable to get language To" + e);
        }
        return status;
    }

    public void deselectSourceLanguageTo() {
    	
    	selectDropDown(selectSourceLanguageToElement, "");
    }
    
    public void selectCategory(String categoryType) {
		
    	selectDropDown(categoryElement, categoryType);
	}
    
    public void deselectCategory() {
		
    	selectDropDown(categoryElement, "");
    }
    
   
    public void uploadFile() throws IOException, AWTException, InterruptedException {
    	
    	Random r = new Random();
    	int randint = Math.abs(r.nextInt()) % 10000;
    	String filePath = System.getProperty("user.dir")+"\\" + randint + ".txt";
    	File file = new File(filePath);
    	file.createNewFile();
    	WebElement fileUploadButton = driver.findElement(By.id("fileuploader"));
	Actions builder = new Actions(driver);
        Action mouseOverHome = builder.moveToElement(fileUploadButton).build();
        mouseOverHome.perform();
    	fileUploadButton.click();
    	Robot robot = new Robot();
    	robot.setAutoDelay(1000);
    	StringSelection stringSelection = new StringSelection(filePath);
    	Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    	robot.setAutoDelay(1000);
    	robot.keyPress(KeyEvent.VK_CONTROL);
    	robot.keyPress(KeyEvent.VK_V);
    	robot.keyRelease(KeyEvent.VK_V);
    	robot.keyRelease(KeyEvent.VK_CONTROL);
    	robot.keyPress(KeyEvent.VK_ENTER);
    	robot.keyRelease(KeyEvent.VK_ENTER);
	}
    
    public void fileComments(String message) {
    	
    	enterTestBoxValues(fileCommentElement, message);
    }
    
    public void selectTurnaroundTime(int optionValue) {
    	
    	switch (optionValue) {
    	case 0:

			if ((driver.findElement(By.id("rushopts0"))).isSelected()) {
				break;	
			} else {
				clickElement(driver.findElement(By.id("rushopts0")));
				break;
			}    			
		case 1:
			clickElement(driver.findElement(By.id("rushopts1")));
			break;
		case 2:
			clickElement(driver.findElement(By.id("rushopts2")));
			break;
		case 3:
			clickElement(driver.findElement(By.id("rushopts3")));
			break;		
		}

    }

    public void selectFreeTrail() {
        if (!driver.findElement(By.id("frtrial1"))
                .isSelected()) {

            js.executeScript("$('#frtrial1').click();");
        }
    }

    public void deselectFreeTrail() {
        if (!driver.findElement(By.id("frtrial0"))
                .isSelected()) {

            js.executeScript("$('#frtrial0').click();");
        }
    }

    public boolean freeTrailStatus(int status) {
        boolean value = false;
        switch (status) {
            case 0:
                value = driver.findElement(By.xpath("//input[@id='frtrial1']"))
                        .isSelected();
                break;
            case 1:
                value = driver.findElement(By.xpath("//input[@id='frtrial0']"))
                        .isSelected();
                break;
        }
        return  value;
    }

	public void emailId(String emailId) {

		enterTestBoxValues(driver.findElement(By.id("paytc_qemailcrm")), emailId);
	}

	public AdditionalInformation clickEmailQuote() {

		clickElement(driver.findElement(By.id("emailquote")));
		AdditionalInformation additionalInformation = new AdditionalInformation(driver);
		return additionalInformation;
	}

	public void clickAdditionalQtyCheck() {

		clickElement(driver.findElement(By.id("qcheck")));
	}

	public void clickCertificationLanguage() {

		clickElement(driver.findElement(By.id("certlang")));
	}

    public void selectRequestMailCopy(String country, String address) {

        if (!driver.findElement(By.id("mailfilecrmpay1"))
                .isSelected()) {

            js.executeScript("$('#mailfilecrmpay1').click();");
        }
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ex) {

        }

        try {
            selectDropDown(mailCountry, country);
            //driver.findElement(By.tagName("body")).click();
		driver.findElement(By.id("quote_summary")).click();
            driver.findElement(By.id("paytc_mailaddress")).clear();
            driver.findElement(By.id("paytc_mailaddress")).sendKeys(address);
        } catch (Exception e) {
            System.out.println("Unable to select mail country " + e);
        }
    }

    public void deselectRequestMailCopy() {

        if (driver.findElement(By.id("mailfilecrmpay1"))
                .isSelected()) {

            js.executeScript("$('#mailfilecrmpay0').click();");
        }
    }

    public boolean requestMailCopyStatus(int status) {
        boolean value = false;
        switch (status) {
            case 0:
                value = driver.findElement(By.xpath("//input[@id='mailfilecrmpay1']"))
                        .isSelected();
                break;
            case 1:
                value = driver.findElement(By.xpath("//input[@id='mailfilecrmpay0']"))
                        .isSelected();
                break;
        }
        return  value;
    }

    public double getActualCost() {
    	
    	return convertAndGetValue(actualCost);
    }
    
    public double getExpeditedCost() {
    	
    	return convertAndGetValue(expeditedCost);
    }

    public double getNotaryFee() {

        return convertAndGetValue(notaryFee);
    }

    public double getProcessFee() {

        return convertAndGetValue(processFee);
    }

    public double getTranslationCost() {
    	
    	return convertAndGetValue(translationCostElement);
    }
    
    public double getSubTotal() {
    	
		return convertAndGetValue(subTotalElement);
    }   
    
    public double getTransactionFee() {
    	
    	return convertAndGetValue(transactionFeeElement);
    }
    
    public double getAdditionalQualityAmount() {

    	return convertAndGetValue(additionalQualityAmountElement);
    }

    public double getCertificationFee() {

		return convertAndGetValue(certificationFeeElement);
	}

	public double getMailingFee() {

        return convertAndGetValue(mailingFeeElement);
    }

    public double getOfferFee() {

        return convertAndGetValue(offerFeeElement);
    }

	public double getGrandTotal() {

		return convertAndGetValue(grandTotalElement);
	}

	public double convertAndGetValue(WebElement element) {

		if (element.isDisplayed()) {
			return Double.parseDouble(getElementValues(element));
		} else {
			return 0;
		}
	}

	public void pageRefresh() {

		driver.navigate().refresh();
	}

	public boolean checkElementVisible(String value, String element) {

		WebElement locator = null;
		switch (value) {
		case "id":
			locator = driver.findElement(By.id(element));
			break;
		case "name":
			locator = driver.findElement(By.name(element));
			break;
		case "xpath":
			locator = driver.findElement(By.xpath(element));
			break;
		case "classname":
			locator = driver.findElement(By.className(element));
			break;
		case "classselector":
			locator = driver.findElement(By.cssSelector(element));
			break;
		case "partiallinktext":
			locator = driver.findElement(By.partialLinkText(element));
			break;
		case "linktext":
			locator = driver.findElement(By.linkText(element));
			break;
		case "tagname":
			locator = driver.findElement(By.tagName(element));
			break;
		}
		return isElementDisplayed(locator);
	}

    public void selectAdditionalQtyCheck(int additionalQtyCheck) {

        switch (additionalQtyCheck) {
            case 0:

                if (!driver.findElement(By.xpath("//input[@id='qc0']"))
                        .isSelected()) {

                    js.executeScript("$('#qc0').click();");
                }
                break;
            case 1:
                if (!driver.findElement(By.xpath("//input[@id='qc1']"))
                        .isSelected()) {

                    js.executeScript("$('#qc1').click();");
                }
                break;

        }
    }


    public boolean additionalQtyCheckStatus(int status) {
        boolean value = false;
        switch (status) {
            case 0:
                value = driver.findElement(By.xpath("//input[@id='qc1']"))
                        .isSelected();
                break;
            case 1:
                value = driver.findElement(By.xpath("//input[@id='qc0']"))
                        .isSelected();
                break;
        }
        return  value;
    }

    public void selectNeedTranscript(int status) {

        switch (status) {
            case 0:

                if (!driver.findElement(By.xpath("//input[@id='needtrc1']"))
                        .isSelected()) {

                    js.executeScript("$('#needtrc1').click();");
                }
                break;
            case 1:
                if (!driver.findElement(By.xpath("//input[@id='needtrc0']"))
                        .isSelected()) {

                    js.executeScript("$('#needtrc0').click();");
                }
                break;

        }
    }

    public boolean needTranscriptStatus(int status) {
        boolean value = false;
        switch (status) {
            case 0:
                value = driver.findElement(By.xpath("//input[@id='needtrc1']"))
                        .isSelected();
                break;
            case 1:
                value = driver.findElement(By.xpath("//input[@id='needtrc0']"))
                        .isSelected();
                break;
        }
        return  value;
    }

    public void selectTimeCode(int status) {

        switch (status) {
            case 0:

                if (!driver.findElement(By.xpath("//input[@id='tcode1']"))
                        .isSelected()) {

                    js.executeScript("$('#tcode1').click();");
                }
                break;
            case 1:
                if (!driver.findElement(By.xpath("//input[@id='tcode0']"))
                        .isSelected()) {

                    js.executeScript("$('#tcode0').click();");
                }
                break;

        }
    }

    public boolean timeCodeStatus(int status) {
        boolean value = false;
        switch (status) {
            case 0:
                value = driver.findElement(By.xpath("//input[@id='tcode1']"))
                        .isSelected();
                break;
            case 1:
                value = driver.findElement(By.xpath("//input[@id='tcode0']"))
                        .isSelected();
                break;
        }
        return  value;
    }

    public void selectNativeSpeaker() {

        if (!driver.findElement(By.xpath("//input[@id='nativespkr']"))
                .isSelected()) {

            js.executeScript("$('#nativespkr').click();");
        }
    }

    public void selectCertification() {

        if (!driver.findElement(By.id("certlang"))
                .isSelected()) {

            js.executeScript("$('#certlang').click();");
        }
    }

    public boolean certificationStatus() {
        return driver.findElement(By.id("certlang"))
                .isSelected();
    }

    public void selectNotarization() {

        if (!driver.findElement(By.id("notrpay"))
                .isSelected()) {

            js.executeScript("$('#notrpay').click();");
        }
    }

    public boolean notarizationStatus() {
        return driver.findElement(By.id("notrpay"))
                .isSelected();
    }

    public void deselectCertification() {

        if (driver.findElement(By.id("certlang"))
                .isSelected()) {

            js.executeScript("$('#certlang').click();");
        }
    }


    public void deselectNotarization() {

        if (driver.findElement(By.id("notrpay"))
                .isSelected()) {

            js.executeScript("$('#notrpay').click();");
        }
    }
    public void enterComments(String comment) {
        try {
            enterTestBoxValues(comments, comment);
        } catch (Exception e) {
            System.out.println("Unable to enter a comment value " + e);
        }
    }


    public void selectTAT(int optionValue) {

        switch (optionValue) {
            case 0:

                if (!driver.findElement(By.xpath("//input[@id='rushopts0']"))
                        .isSelected()) {

                    js.executeScript("$('#rushopts0').click();");
                }
                break;
            case 1:

                if (!driver.findElement(By.xpath("//input[@id='rushopts1']"))
                        .isSelected()) {

                    js.executeScript("$('#rushopts1').click();");
                }
                break;
            case 2:

                if (!driver.findElement(By.xpath("//input[@id='rushopts2']"))
                        .isSelected()) {

                    js.executeScript("$('#rushopts2').click();");
                }
                break;
            case 3:

                if (!driver.findElement(By.xpath("//input[@id='rushopts3']"))
                        .isSelected()) {

                    js.executeScript("$('#rushopts3').click();");
                }
                break;
            case 4:

                if (!driver.findElement(By.xpath("//input[@id='deliveryReq']"))
                        .isSelected()) {

                    js.executeScript("$('#deliveryReq').click();");
                }
                break;
            case 5:

                if (!driver.findElement(By.xpath("//input[@id='expedited']"))
                        .isSelected()) {

                    js.executeScript("$('#expedited').click();");
                }
                break;
        }
    }

    public void clickEmailMeGetQuote() {
        try {

            if (isElementDisplayed(emailMeQuote)) {
                js.executeScript("$('#emailquote').click();");
            }
        } catch (Exception e) {
            System.out.println("Unable to click Get Email Me Quote button " + e);
        }
    }


    public void clickProceedPayment() {
        try {

            if (isElementDisplayed(proceedPayment)) {
                js.executeScript("$('#proceedpayment').click();");
            }
        } catch (Exception e) {
            System.out.println("Unable to click Proceed payment button " + e);
        }
    }

    public void clickGetQuote() {
        try {

            if (isElementDisplayed(getQuote)) {
                js.executeScript("$('#getquote').click();");
            }
        } catch (Exception e) {
            System.out.println("Unable to click Get Quote button " + e);
        }
    }

    public void clickPrivacyPolicy() {
        try {

            if (isElementDisplayed(privacyPolicy)) {
               //clickElement(privacyPolicy);
                js.executeScript("$('#privacy_policy').click();");
            }
        } catch (Exception e) {
            System.out.println("Unable to click privacy policy " + e);
        }
    }

    public String getToolTipMessage() {

        return driver.findElement(By.xpath("//div[contains(@role,'tooltip')]/div[@class='tooltip-inner']")).getText();
    }

    public boolean isCustomMessageDisplayed() {
        boolean status = false;
        try {
          status = driver.findElement(By.xpath("//div[@class='qt_msg ui-msg']"))
                  .getText().contains("Customized rate apply for");
        } catch (Exception ex) {
            status = false;
        }
        return status;
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
}
