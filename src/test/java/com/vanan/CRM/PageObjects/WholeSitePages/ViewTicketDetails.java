package com.vanan.CRM.PageObjects.WholeSitePages;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ViewTicketDetails extends AccessingElement {

    private WebDriver driver;

    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

    private String allTicketParentElement = "//div[@id='ajax-dynamic-form']/div[@class='form-group    ']";
    private String allTicketStaticParentElement = "//form[@id='process_form']/div[@class='form-group']";

    @FindBy(xpath = "//select[@id='next_order_discount']")
    private WebElement customerNODDiscount;

    @FindBy(xpath = "//button[@id='save_btn']")
    private WebElement saveChangesButton;

    public ViewTicketDetails(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

	public String getRunTimeTicketFieldValues(String field) {
		List<WebElement> elements = driver
				.findElements(By.xpath(allTicketParentElement));
		String value = "";
		WebElement eachElement;
		for (int i = 1; i <= elements.size(); i++) {

			eachElement = driver.findElement(
					By.xpath(allTicketParentElement + "[" + i + "]/label"));
			//System.out.println(eachElement.getText() + "))))))))))");

			if (eachElement.getText().contains(field)) {
				value = driver
						.findElement(By.xpath(
								allTicketParentElement + "[" + i + "]/div/p"))
						.getText();
				break;
			}

		}
		return value;
	}

	public String getStaticTicketFieldValues(String field) {
		List<WebElement> elements = driver
				.findElements(By.xpath(allTicketStaticParentElement));
		String value = "";
		WebElement eachElement;
		for (int i = 1; i <= elements.size(); i++) {
			try {
				eachElement = driver.findElement(By.xpath(
						allTicketStaticParentElement + "[" + i + "]/label"));
				// System.out.println(
				// eachElement.getText() + "}}}}}}}}}}}}}}}}}}}}}}}}");
				if (eachElement.getText().contains(field)) {
					value = driver.findElement(By.xpath(
							allTicketStaticParentElement + "[" + i + "]/div/p"))
							.getText();
					break;
				}
			} catch (Exception e) {
				continue;
				// TODO: handle exception
			}

		}
		return value;
	}

    public String getServiceValues() {

        WebElement eachElement = driver.findElement(
                By.xpath("//form[@id='process_form']/div[@class='form-group'][7" +
                        "]/p"));

        return eachElement.getText();
    }

    public String getOrderNo() {

        WebElement eachElement = driver.findElement(
                By.xpath("//form[@id='process_form']/div[@class='form-group'][2" +
                        "]/div/p"));

        return eachElement.getText();
    }

	public String getVoiceValues() {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@id='ajax-dynamic-form']/div[@class='form-group" +
						"']/div/p"));

		return eachElement.getText();
	}

    public String getVoiceDetails() {

        WebElement eachElement = driver.findElement(
            By.xpath("//div[@id='ajax-dynamic-form']/div[@class='form-group" +
                "']/div/p"));

        return eachElement.getText();
    }

	public String getAgentCodeFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   agent_code ']/div/p"));

		return eachElement.getText();
	}

	public String getHowFrequentlyNeedFieldValues(String field) {

		WebElement eachElement = driver.findElement(By
				.xpath("//div[@class='form-group   service_frequency ']/div/p"));

		return eachElement.getText();
	}

	public String getFileCommentsFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   filecomments ']/div/p"));

		return eachElement.getText();
	}

	public String getMailingFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   mailing_option ']/div/p"));

		return eachElement.getText();
	}

	public String getMailingAddressFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   mail_address ']/div/p"));

		return eachElement.getText();
	}

	public String getSpeakerCountFieldValues(String field) {

		WebElement eachElement = driver.findElement(By
				.xpath("//div[@class='form-group   speakers_count_cls ']/div/p"));

		return eachElement.getText();
	}

	public String getTimeCodeFieldValues(String field) {

		WebElement eachElement = driver.findElement(By
				.xpath("//div[@class='form-group   timecode_freq_cls ']/div/p"));

		return eachElement.getText();
	}

	public String getPaymentTypeFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   payment_type ']/div/p"));

		return eachElement.getText();
	}

	public String getPaymentDurationFieldValues(String field) {

		WebElement eachElement = driver.findElement(By.xpath(
				"//div[@class='form-group lbl_pay_duration  payment_duration ']/div/p"));

		return eachElement.getText();
	}

	public String getPaymentModeFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   payment_mode ']/div/p"));

		return eachElement.getText();
	}

	public String getPaypalFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   paypal_cls ']/div/p"));

		return eachElement.getText();
	}

	public String getTwoCheckoutFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   2checkout_cls ']/div/p"));

		return eachElement.getText();
	}

	public String getWiredFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   wired_cls ']/div/p"));

		return eachElement.getText();
	}

	public String getCommentsFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   comments ']/div/p"));

        return eachElement.getText();
    	}
	
	public String getEmailId() {
        waitForPageLoad(driver);
        WebElement element = driver.findElement(
                By.xpath("//div[@class='form-group    email_div']/div[@class='col-sm-7 email_div']/p[@class='no-mouseflow']"));
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(element).build();
        mouseOverHome.perform();
        return element.getText();
    	}
	

        public String getWebsite() {
        waitForPageLoad(driver);

        WebElement element= driver.findElement(
                By.xpath("//div[@id='ajax-dynamic-form']/div[@class='form-group    '][7]/div/p"));
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(element).build();
        mouseOverHome.perform();
        return element.getText();
    	}

    public void waitForPageLoad() {
        waitTime();
        for(int i = 1; i <= 12; i++) {
            try {
                if(driver.findElement(By.xpath("//div[@class='loading_img']"))
                        .isDisplayed()) {
                    if(driver.findElement(By.xpath("//div[@class='loading_img']"))
                            .getCssValue("display").contains("block")) {
                        waitTime();
                    } else if(driver.findElement(By.xpath("//div[@class='loading_img']"))
                            .getCssValue("display").contains("none")){
                        break;
                    }

                } else {
                    continue;
                }
            } catch (Exception ex) {

            }
        }
    }

    private void waitTime() {
        waitForProcessCompletion(5);
    }

    public void waitForProcessCompletion(int waitTime) {
        try {
            TimeUnit.SECONDS.sleep(waitTime);
        } catch (Exception e) {
        }
    }

    public void clickCustomerInfo() {

        waitForPageLoad(driver);
        WebElement element = driver.findElement(By.id("custinfoview"));
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(element).build();
        mouseOverHome.perform();
        builder.doubleClick(element).perform();
    }

    public boolean getNODSelectedValues(String option) {

        waitForPageLoad(driver);
        boolean status = false;
        Select select = new Select(customerNODDiscount);
        List<WebElement> options = select.getOptions();
        for(int i = 0; i< options.size(); i++) {
           if(options.get(i).getText().contains(option)) {
                status = true;
                break;
            }
        }
        return status;
    }

    public void clickCloseCustomerInfoPopUp() {

        waitForPageLoad(driver);
        waitForProcessCompletion(10);
        WebElement element = driver.findElement(By.xpath("//div[contains(@id," +
                "'cust_info_modal')]//..//button[@class='close']"));
        element.click();
    }

    public void selectNOD(String option) {
        try {
            selectDropDown(customerNODDiscount, option);
        } catch (Exception e) {
            System.out.println("Unable to select NOD " + e);
        }
    }

    public void clickSaveChanges() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(saveChangesButton).build();
        mouseOverHome.perform();
        clickElement(saveChangesButton);
    }
}
