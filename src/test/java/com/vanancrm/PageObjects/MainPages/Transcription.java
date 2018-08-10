package com.vanancrm.PageObjects.MainPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Transcription extends AccessingElement {

	private WebDriver driver;
	private JavascriptExecutor js;

	public Transcription(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
	}

	@FindBy(id = "hours")
	private WebElement hours;

	@FindBy(id = "minutes")
	private WebElement minutes;

	@FindBy(id = "srclang")
	private WebElement sourceLang;

    @FindBy(id = "qttcode")
    private WebElement timeCode;

	@FindBy(id = "trcpriceb_disp")
	private WebElement basePrice;

    @FindBy(id = "hlw_amt")
    private WebElement discountPrice;

    @FindBy(id = "off-lbl")
    private WebElement discountActualPrice;

    @FindBy(id = "trccostb_disp")
    private WebElement uintCost;

	@FindBy(id = "trcverbacost_disp")
	private WebElement verbatim;

	@FindBy(id = "trctcodecost_disp")
	private WebElement timeCodePrice;

	@FindBy(id = "expprice_disp")
	private WebElement expediatedPrice;

    @FindBy(id = "paytc_qemailcrm")
    private WebElement emailElement;

    @FindBy(id = "qcheckcost_disp")
    private WebElement additionalQtyCheckPrice;

    @FindBy(id = "sub_amt")
    private WebElement totalCost;

    @FindBy(id = "trctotcost_disp")
    private WebElement esubtotalCost;

    @FindBy(id = "hlw_amt")
    private WebElement offerDiscount;

	@FindBy(id = "trans_rate")
	private WebElement transactionFeeElement;

	@FindBy(id = "price_display")
	private WebElement grandTotal;

	@FindBy(xpath = "//i[@class='fa fa-file']")
	private WebElement fileIcon;

	@FindBy(xpath = "//i[@class='fa fa-language']")
	private WebElement languageIcon;

	@FindBy(xpath = "//i[@class='fa fa-list-alt']")
	private WebElement categoryIcon;

	@FindBy(xpath = "//i[@class='fa fa-font']")
	private WebElement verbatimIcon;

	@FindBy(xpath = "//i[@class='fa fa-clock-o']")
	private WebElement timecodeIcon;

	@FindBy(xpath = "//i[@class='fa fa-check-circle']")
	private WebElement additionalQtyIcon;

    @FindBy(xpath = "//i[@class='fa fa-tags']")
    private WebElement freeTrailIcon;

    @FindBy(id = "emailquote")
    private WebElement emailMeQuote;

    @FindBy(id = "proceedpayment")
    private WebElement proceedPayment;

    @FindBy(id = "getquote")
    private WebElement getQuote;

    @FindBy(id = "durationnewpay-0")
    private WebElement fileLength;

    @FindBy(id = "filecomments")
    private WebElement comments;

    @FindBy(id = "privacy_policy")
    private WebElement privacyPolicy;

    public void enterHours(String hour) {
        try {
            enterTestBoxValues(hours, hour);
        } catch (Exception e) {
            System.out.println("Unable to enter a hour value " + e);
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

    public void enterComments(String comment) {
        try {
            enterTestBoxValues(comments, comment);
        } catch (Exception e) {
            System.out.println("Unable to enter a comment value " + e);
        }
    }

	public void enterMinutes(String minute) {
		try {
			enterTestBoxValues(minutes, minute);
		} catch (Exception e) {
			System.out.println("Unable to enter a minute value " + e);
		}
	}

	public void selectLanguageFrom(String sourceLanguage) {
		try {
			selectDropDown(sourceLang, sourceLanguage);
			driver.findElement(By.tagName("body")).click();
		} catch (Exception e) {
			System.out.println("Unable to select source language " + e);
		}
	}

    public void selectTimeCode(String timecode) {
        try {
            selectDropDown(timeCode, timecode);
            driver.findElement(By.tagName("body")).click();
        } catch (Exception e) {
            System.out.println("Unable to select time code " + e);
        }
    }

    public void selectFreeTrail() {
        if (!driver.findElement(By.id("ft1"))
                .isSelected()) {

            js.executeScript("$('#ft1').click();");
        }
    }

	public void selectNativeSpeaker() {
		if (!driver.findElement(By.xpath(
				"//input[@type='checkbox' and @id='nativespkr']"))
				.isSelected()) {

			js.executeScript("$('#nativespkr').click();");
		}
	}

	public void selectTranslation(int optionValue) {
		switch (optionValue) {
		case 0:
		if (driver
				.findElement(By.xpath(
						"//input[@type='checkbox' and @id='needtranslation']"))
				.isSelected()) {

			js.executeScript("$('#needtranslation').click();");
		}
		break;
		case 1:
			if (!driver.findElement(By.xpath("//input[@id='needtranslation']"))
					.isSelected()) {

				js.executeScript("$('#needtranslation').click();");
			}
			break;
		}
	}

	public void selectVerbatim(int optionValue) {
		switch (optionValue) {
		case 0:
			if (!driver.findElement(By.xpath("//input[@id='cleanverba']"))
					.isSelected()) {

				js.executeScript("$('#cleanverba').click();");
			}
			break;
		case 1:
			if (!driver.findElement(By.xpath("//input[@id='fullverba']"))
					.isSelected()) {

				js.executeScript("$('#fullverba').click();");
			}
			break;
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

	public void selectCategory(int optionValue) {

		switch (optionValue) {
		case 0:
			if (!driver
					.findElement(
							By.xpath("//input[@type='radio' and @id='general']"))
					.isSelected()) {

				js.executeScript("$('#general').click();");
			}
			break;
		case 1:
			if (!driver
					.findElement(
							By.xpath("//input[@type='radio' and @id='legal']"))
					.isSelected()) {

				js.executeScript("$('#legal').click();");
			}
			break;
		}
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

	public double getBasePrice() {

		return convertAndGetValue(basePrice);
	}

	public double getDiscountPrice() {

		return convertAndGetValue(discountPrice);
	}

    public double getActualDiscountPrice() {

        return convertAndGetValue(discountActualPrice);
    }


    public double getUnitCost() {

		return convertAndGetValue(uintCost);
	}

	public double getVerbatim() {

		return convertAndGetValue(verbatim);
	}

	public double getTimeCode() {

		return convertAndGetValue(timeCodePrice);
	}

	public double getAddtionalQtyCheck() {

		return convertAndGetValue(additionalQtyCheckPrice);
	}

	public double getTotalCost() {

		return convertAndGetValue(totalCost);
	}

    public double getESsubTotalCost() {

        //return convertAndGetValue(esubtotalCost);
        return 0;
    }

    public double getTranscationFee() {

        return convertAndGetValue(transactionFeeElement);
    }

    public double getOfferDiscout() {

        return convertAndGetValue(offerDiscount);
    }

	public double getGrandTotal() {

		return convertAndGetValue(grandTotal);
	}

	public double getExpedite() {

		return convertAndGetValue(expediatedPrice);
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

	public boolean isFileIconDisplayed() {
		return isElementDisplayed(fileIcon);
	}

	public boolean isLangIconDisplayed() {
		return isElementDisplayed(languageIcon);
	}

	public boolean isCategoryIconDisplayed() {
		return isElementDisplayed(categoryIcon);
	}

	public boolean isVerbatimIconDisplayed() {
		return isElementDisplayed(verbatimIcon);
	}

	public boolean isTimeCodeIconDisplayed() {
		return isElementDisplayed(timecodeIcon);
	}

	public boolean isFreeTrailIconDisplayed() {
		return isElementDisplayed(freeTrailIcon);
	}

	public boolean isAdditionalQtyIconDisplayed() {
		return isElementDisplayed(additionalQtyIcon);
	}

	public boolean isUSNativeSpeakerDisplayed() {
		return isElementDisplayed(driver.findElement(By.id("nativespkr-lbl")));
	}

	public boolean isTranslationDisplayed() {
		return isElementDisplayed(
				driver.findElement(By.id("needtranslation-lbl")));
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

    public void enterEmailId(String email) {
	    
        Actions builder = new Actions(driver);
        Action mouseOverHome = builder.moveToElement(emailElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(emailElement, email);
    }

    public String getUploadedFileName() {

        return driver.findElement(By.xpath("//div[@id='fileuploadfield']"
            + "/div/div[2]")).getText();
    }

    public boolean isCustomMessageDisplayed() {

        return driver.findElement(By.xpath("//div[@class='qt_msg ui-msg']"))
                .getText().contains("Customized rate apply for");
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
  
	WebElement element = driver.findElement(By.xpath("//div[contains(@role,'tooltip')]/div[@class='tooltip-inner']"));
        Actions builder = new Actions(driver);
        Action mouseOverHome = builder.moveToElement(element).build();
        mouseOverHome.perform();
        return element.getText();
    }
}
