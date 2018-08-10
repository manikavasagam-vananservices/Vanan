package com.vanan.CRM.PageObjects.WholeSitePages;

import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.By;
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
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FileUploadFromTicket extends AccessingElement {

    private WebDriver driver;
    @FindBy(id = "getquote")
    private WebElement getQuote;

    @FindBy(id = "command")
    private WebElement commentElement;

    @FindBy(id = "filesubmit")
    private WebElement submitButtonElement;

    @FindBy(id = "filesubject")
    private WebElement fileSubjectElement;

    @FindBy(xpath = "//div[@class='audioLength']")
    private WebElement fileCompletedMsgElement;

    private Actions builder;
    private Action mouseOverHome;

    public FileUploadFromTicket(WebDriver driver) {

        this.driver = driver;
        builder = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickSubmitButton() {
        try {
            clickElement(submitButtonElement);
        } catch (Exception e) {
            System.out.println("Unable to click submit button " + e);
        }
    }
    public void enterComments(String comment) {
        try {
            enterTestBoxValues(commentElement, comment);
        } catch (Exception e) {
            System.out.println("Unable to enter a comment value " + e);
        }
    }

    public String getOrderNumber() {
        return null;
    }

    public String getEmailDetails(){
        return null;
    }

    public String getWebsiteDetail() {
        return null;
    }

    public String getUploadedSuccessMsg() {
        return fileCompletedMsgElement.getText();
    }

    public List<String> fileNames() {
        List<String> list = new ArrayList<String>();
        return list;
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
        TimeUnit.SECONDS.sleep(10);
    }

    public void selectSuject(String option) {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(fileSubjectElement).build();
            mouseOverHome.perform();
            selectDropDown(fileSubjectElement, option);
        } catch (Exception e) {
            System.out.println("Unable to select subject " + e);
        }
    }
}
