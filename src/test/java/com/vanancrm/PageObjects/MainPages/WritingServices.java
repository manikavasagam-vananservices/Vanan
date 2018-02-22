package com.vanancrm.PageObjects.MainPages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WritingServices extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;

    public WritingServices(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(id = "qnamecrm")
    private WebElement cname;

    @FindBy(id = "qemailcrm")
    private WebElement cemail;

    @FindBy(id = "qcountryscrm")
    private WebElement country;

    @FindBy(id = "qphonecrm")
    private WebElement phoneNumber;

    @FindBy(id = "qpapertypecrm")
    private WebElement paperType;

    @FindBy(id = "qacademiclevelcrm")
    private WebElement academiclevelcrm;

    @FindBy(id = "qtopiccrm")
    private WebElement topic;

    @FindBy(id = "qstylecrm")
    private WebElement style;

    @FindBy(id = "qlanguagestylecrm")
    private WebElement languagestyle;

    @FindBy(id = "qurgencycrm")
    private WebElement urgency;

    @FindBy(id = "pagescrm")
    private WebElement pages;

    @FindBy(id = "qstandardcrm")
    private WebElement standard;

    @FindBy(id = "qpaperdetailscrm")
    private WebElement paperDetails;

    @FindBy(id = "qcommentcrm")
    private WebElement comment;

    @FindBy(id = "qsubmitcrm")
    private WebElement submitbutton;

    public void enterName(String name) {
        try {
            enterTestBoxValues(cname, name);
        } catch (Exception e) {
            System.out.println("Unable to enter a name value " + e);
        }
    }

    public void enterEmail(String mailid) {
        try {
            enterTestBoxValues(cemail, mailid);
        } catch (Exception e) {
            System.out.println("Unable to enter a email value " + e);
        }
    }

    public void selectCountry(String ccountry) {
        try {
            selectDropDown(country, ccountry);

        } catch (Exception e) {
            System.out.println("Unable to select country " + e);
        }
    }

    public void enterPhoneNumber(String phoneNo) {
        try {
            enterTestBoxValues(phoneNumber, phoneNo);
        } catch (Exception e) {
            System.out.println("Unable to enter a phone number value " + e);
        }
    }

    public void selectTypeOfPaper(String paper) {
        try {
            selectDropDown(paperType, paper);

        } catch (Exception e) {
            System.out.println("Unable to select paper type " + e);
        }
    }

    public void selectAcadamicLevel(String acadamic) {
        try {
            selectDropDown(academiclevelcrm, acadamic);

        } catch (Exception e) {
            System.out.println("Unable to select acdemic level " + e);
        }
    }

    public void enterTopic(String content) {
        try {
            enterTestBoxValues(topic, content);
        } catch (Exception e) {
            System.out.println("Unable to enter a topic value " + e);
        }
    }

    public void selectStyle(String sstyle) {
        try {
            selectDropDown(style, sstyle);

        } catch (Exception e) {
            System.out.println("Unable to select style " + e);
        }
    }

    public void selectLanguageStyle(String lstyle) {
        try {
            selectDropDown(languagestyle, lstyle);

        } catch (Exception e) {
            System.out.println("Unable to select language style " + e);
        }
    }

    public void selectUrgency(String req) {
        try {
            selectDropDown(urgency, req);

        } catch (Exception e) {
            System.out.println("Unable to select urgency " + e);
        }
    }

    public void enterPages(String page) {
        try {
            enterTestBoxValues(pages, page);
        } catch (Exception e) {
            System.out.println("Unable to enter a page value " + e);
        }
    }

    public void selectStandard(String req) {
        try {
            selectDropDown(standard, req);

        } catch (Exception e) {
            System.out.println("Unable to select standard " + e);
        }
    }

    public void enterPaperDetails(String paper) {
        try {
            enterTestBoxValues(paperDetails, paper);
        } catch (Exception e) {
            System.out.println("Unable to enter a paper details " + e);
        }
    }

    public void enterComment(String comments) {
        try {
            enterTestBoxValues(comment, comments);
        } catch (Exception e) {
            System.out.println("Unable to enter a comment " + e);
        }
    }

    public void clickQuote() {
        clickElement(submitbutton);
    }
}
