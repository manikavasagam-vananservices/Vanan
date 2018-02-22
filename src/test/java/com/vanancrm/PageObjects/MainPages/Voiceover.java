package com.vanancrm.PageObjects.MainPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Voiceover extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;

    public Voiceover(WebDriver driver) {

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

    @FindBy(id = "qtargetcrm")
    private WebElement language;

    @FindBy(id = "needtranslationcrm")
    private WebElement needTranslationCrm;

    @FindBy(id = "qsourcevoicecrm")
    private WebElement sourceLanguage;

    @FindBy(id = "scriptcrm")
    private WebElement scriptCrm;

    @FindBy(id = "commentvoiceover")
    private WebElement comments;

    @FindBy(id = "purposevoiceover")
    private WebElement purpose;

    @FindBy(id = "voicecountcrm")
    private WebElement voiceCount;

    @FindBy(id = "gender_1")
    private WebElement gender1;

    @FindBy(id = "gender_2")
    private WebElement gender2;

    @FindBy(id = "age_1")
    private WebElement age1;

    @FindBy(id = "age_2")
    private WebElement age2;

    @FindBy(id = "servicefrequency")
    private WebElement serviceFreq;

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

    public void selectLanguageFrom(String sourceLanguage) {
        try {
            selectDropDown(language, sourceLanguage);
        } catch (Exception e) {
            System.out.println("Unable to select source language " + e);
        }
    }

    public void selectNeedTranslation(String translation) {
        try {
            selectDropDown(needTranslationCrm, translation);

        } catch (Exception e) {
            System.out.println("Unable to select Need translation crm " + e);
        }
    }

    public void selectTargetLanguage(String tlang) {
        try {
            selectDropDown(sourceLanguage, tlang);

        } catch (Exception e) {
            System.out.println("Unable to select acdemic level " + e);
        }
    }

    public void selectScript(String script) {
        try {
            selectDropDown(scriptCrm, script);

        } catch (Exception e) {
            System.out.println("Unable to select script " + e);
        }
    }

    public void enterScriptComments(String message) {
        try {
            enterTestBoxValues(comments, message);
        } catch (Exception e) {
            System.out.println("Unable to enter a message " + e);
        }
    }



    public void selectPurposeOfVoiceOver(String content) {
        try {
            selectDropDown(purpose, content);

        } catch (Exception e) {
            System.out.println("Unable to select Purpose of voice over " + e);
        }
    }

    public void selectNumberOfVoice(String content) {
        try {
            selectDropDown(voiceCount, content);

        } catch (Exception e) {
            System.out.println("Unable to select voice count " + e);
        }
    }

    public void selectFirstGender(String gender) {
        try {
            selectDropDown(gender1, gender);

        } catch (Exception e) {
            System.out.println("Unable to select first gender " + e);
        }
    }

    public void selectSecondGender(String gender) {
        try {
            selectDropDown(gender2, gender);

        } catch (Exception e) {
            System.out.println("Unable to select second gender " + e);
        }
    }

    public void selectFirstAge(String age) {
        try {
            selectDropDown(age1, age);

        } catch (Exception e) {
            System.out.println("Unable to select first age " + e);
        }
    }

    public void selectSecondAge(String age) {
        try {
            selectDropDown(age2, age);

        } catch (Exception e) {
            System.out.println("Unable to select second age " + e);
        }
    }

    public void selectServiceFrequency(String req) {
        try {
            selectDropDown(serviceFreq, req);

        } catch (Exception e) {
            System.out.println("Unable to select service frequency " + e);
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
}
