package com.vanancrm.PageObjects.MainPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FreeTrailPage extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;

    public FreeTrailPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(id = "qnamecrm")
    private WebElement tcname;

    @FindBy(id = "qlastnamecrm")
    private WebElement tlname;

    @FindBy(id = "qemailcrm")
    private WebElement tcemail;

    @FindBy(id = "videolengthcrm")
    private WebElement videoLength;

    @FindBy(id = "qcountryscrm")
    private WebElement tccountry;

    @FindBy(id = "servicefrequency")
    private WebElement serviceFreq;

    @FindBy(id = "qphonecrm")
    private WebElement tcphone;

    @FindBy(id = "qsourcecrm")
    private WebElement tslanguage;

    @FindBy(id = "qtvercrm")
    private WebElement tcverbatim;

    @FindBy(id = "qttcodecrm")
    private WebElement tctimecode;

    @FindBy(id = "nativecrm")
    private WebElement tcnative;

    @FindBy(id = "qcommentcrm")
    private WebElement tccomment;

    @FindBy(id = "qsubmitcrm")
    private WebElement csubmit;

    @FindBy(id = "qtargetcrm")
    private WebElement tlanguage;

    @FindBy(id = "audioVideo")
    private WebElement fileType;

    @FindBy(id = "captioning_format")
    private WebElement fileFormat;

    @FindBy(id = "needtranscriptioncrm")
    private WebElement transcritptionElement;

    @FindBy(id = "otherlangcrm")
    private WebElement otherLangSource;

    @FindBy(id = "otherlang1crm")
    private WebElement otherLangTarget;

    @FindBy(id="qservicecrm")
    private  WebElement typeOfServiceElement;

    @FindBy(id="minutescrm")
    private  WebElement minuteElement;

    @FindBy(id="qtvercrm")
    private  WebElement verbElement;

    @FindBy(id="qttcodecrm")
    private  WebElement timeElement;

    @FindBy(id="pagescrm")
    private  WebElement pageElement;

    @FindBy(id="formatcrm")
    private  WebElement formattingElement;

    @FindBy(id="qtatcrm")
    private  WebElement datePickerElement;

    @FindBy(id="qnotecrm")
    private  WebElement noteCertElement;

    @FindBy(id="qmailcrm")
    private  WebElement mailSentElement;

    @FindBy(id="qmailmsgcrm")
    private  WebElement mailAddressElement;

    @FindBy(id = "confirm_agreement")
    private WebElement privacyPolicy;

    public void enterName(String name) {
        try {
            enterTestBoxValues(tcname, name);
        } catch (Exception e) {
            System.out.println("Unable to enter a name value " + e);
        }
    }

    public void enterLastName(String name) {
        try {
            enterTestBoxValues(tlname, name);
        } catch (Exception e) {
            System.out.println("Unable to enter a last name value " + e);
        }
    }

    public void enterEmail(String email) {
        try {
            enterTestBoxValues(tcemail, email);
        } catch (Exception e) {
            System.out.println("Unable to enter a email value " + e);
        }
    }

    public void enterVideoLength(String videoLen) {
        try {
            enterTestBoxValues(videoLength, videoLen);
        } catch (Exception e) {
            System.out.println("Unable to enter a video length value " + e);
        }
    }

    public void enterPhoneNo(String number) {
        try {
            enterTestBoxValues(tcphone, number);
        } catch (Exception e) {
            System.out.println("Unable to enter a phone number value " + e);
        }
    }

    public void selectCountry(String country) {
        try {
            selectDropDown(tccountry, country);

        } catch (Exception e) {
            System.out.println("Unable to select country " + e);
        }
    }

    public void selectServiceFrequency(String frequency) {
        try {
            selectDropDown(serviceFreq, frequency);

        } catch (Exception e) {
            System.out.println("Unable to select service frequency " + e);
        }
    }

    public void selectSourceLanguage(String language) {
        try {
            selectDropDown(tslanguage, language);

        } catch (Exception e) {
            System.out.println("Unable to select source language " + e);
        }
    }

    public void selectLanguageTo(String targetLanguage) {
        try {
            selectDropDown(tlanguage, targetLanguage);
            driver.findElement(By.tagName("body")).click();
        } catch (Exception e) {
            System.out.println("Unable to select target language " + e);
        }
    }

    public void selectOtherSourceLanguage(String language) {
        try {
            selectDropDown(otherLangSource, language);

        } catch (Exception e) {
            System.out.println("Unable to select other source language " + e);
        }
    }

    public void selectOtherTargetLanguage(String language) {
        try {
            selectDropDown(otherLangTarget, language);

        } catch (Exception e) {
            System.out.println("Unable to select other target language " + e);
        }
    }

    public void selectFileType(String file) {
        try {
            selectDropDown(fileType, file);

        } catch (Exception e) {
            System.out.println("Unable to select file type " + e);
        }
    }

    public void selectTypeOfService(String fileType) {
        try {
            selectDropDown(typeOfServiceElement, fileType);

        } catch (Exception e) {
            System.out.println("Unable to select type of service" + e);
        }
    }

    public void selectFileFormat(String format) {
        try {
            selectDropDown(fileFormat, format);

        } catch (Exception e) {
            System.out.println("Unable to select file format " + e);
        }
    }

    public void selectTranscription(String transcription) {
        try {
            selectDropDown(transcritptionElement, transcription);

        } catch (Exception e) {
            System.out.println("Unable to select Need Transcription/Translation" +
                " " + e);
        }
    }

    public void clickVerbatim() {
        try {
            clickElement(tcverbatim);
        } catch (Exception e) {
            System.out.println("Unable to click Verbatim checkbox " + e);
        }
    }

    public void clickTimeCoding() {
        try {
            clickElement(tctimecode);
        } catch (Exception e) {
            System.out.println("Unable to click Time Coding checkbox " + e);
        }
    }

    public void clickUSNativeTranscribers() {
        try {
            clickElement(tcnative);
        } catch (Exception e) {
            System.out.println("Unable to click US Native transcribers checkbox"
                + e);
        }
    }

    public void enterMinute(String minute) {
        try {
            enterTestBoxValues(minuteElement, minute);
        } catch (Exception e) {
            System.out.println("Unable to enter a minute " + e);
        }
    }

    public void enterPages(String page) {
        try {
            enterTestBoxValues(pageElement, page);
        } catch (Exception e) {
            System.out.println("Unable to enter a page " + e);
        }
    }

    public void clickVerb() {
        try {
            if(!verbElement.isSelected()) {
                clickElement(verbElement);
            }
        } catch (Exception e) {
            System.out.println("Unable to click Verbatim checkbox " + e);
        }
    }

    public void clickTimeCode() {
        try {
            if(!timeElement.isSelected()) {
                clickElement(timeElement);
            }

        } catch (Exception e) {
            System.out.println("Unable to click Time Coding checkbox " + e);
        }
    }

    public void clickNotAndCertificate() {
        try {
            if(!noteCertElement.isSelected()) {
                clickElement(noteCertElement);
            }

        } catch (Exception e) {
            System.out.println("Unable to click Notarization Certificate checkbox " + e);
        }
    }

    public void enterAddress(String address) {
        try {
            enterTestBoxValues(mailAddressElement, address);
        } catch (Exception e) {
            System.out.println("Unable to enter a address " + e);
        }
    }

    public void clickMail() {
        try {
            if(!mailSentElement.isSelected()) {
                clickElement(mailSentElement);
            }

        } catch (Exception e) {
            System.out.println("Unable to click Mail the document checkbox " + e);
        }
    }

    public void selectFormatting(String format) {
        try {
            selectDropDown(formattingElement, format);

        } catch (Exception e) {
            System.out.println("Unable to select formattting " + e);
        }
    }

    public void enterComment(String comment) {
        try {
            enterTestBoxValues(tccomment, comment);
        } catch (Exception e) {
            System.out.println("Unable to enter a comment value " + e);
        }
    }

    public void clickSubmit() {
        try {
            clickElement(csubmit);
        } catch (Exception e) {
            System.out.println("Unable to click submit button" + e);
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
        Actions builder = new Actions(driver);
        Action mouseOverHome = builder.moveToElement(fileUploadButton).build();
        mouseOverHome.perform();
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
        TimeUnit.SECONDS.sleep(10);
    }

    public String getUploadedFileName() {

        return driver.findElement(By.xpath("//div[@id='info']/div[2]/div[1]"))
            .getText();
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

    public void clickPrivacyPolicyUsingJScript() {
        try {

            js.executeScript("$('#privacy_policy').click();");

        } catch (Exception e) {
            System.out.println("Unable to click privacy policy " + e);
        }
    }

    public String getToolTipMessage() {

        return driver.findElement(By.xpath("//div[contains(@role,'tooltip')]/div[@class='tooltip-inner']")).getText();
    }

}
