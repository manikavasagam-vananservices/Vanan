package com.vanan.CustomerDashboard.PageObjects.MainPages;

import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DashBoard extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;
    private Actions builder;
    private Action mouseOverHome;
    
    @FindBy(xpath = "//div[@id='sidebar-menu']//*//a[contains(text(), 'Voice " +
            "Over')]")
    private WebElement voiceOverMenu;

    @FindBy(xpath = "//div[@id='sidebar-menu']//*//a[contains(text(), 'Typing')]")
    private WebElement typingMenu;

    @FindBy(xpath = "//div[@id='sidebar-menu']//*//a[contains(text(), 'Translation')]")
    private WebElement translationMenu;

    @FindBy(xpath = "//div[@id='sidebar-menu']//*//a[contains(text(), 'Transcription')]")
    private WebElement transcriptionMenu;

    @FindBy(xpath = "//div[@id='sidebar-menu']//*//a[contains(text(), 'Captioning')]")
    private WebElement captioningMenu;

    @FindBy(xpath = "//a[contains(text(),'Back to dashboard')]")
    private WebElement backToDashBoardElement;

    @FindBy(xpath = "//table[@id='my_table']/tbody/tr")
    private List<WebElement> myOrderTableRows;

    @FindBy(xpath = "//div[@id='bookmarkModal']//*//button[@class='close']")
    private WebElement popUpCloseButtonElement;

    public DashBoard(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

   public void clickVoiceOverMenu() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(voiceOverMenu).build();
        mouseOverHome.perform();
        clickElement(voiceOverMenu);
    }

    public void clickTypingMenu() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(typingMenu).build();
        mouseOverHome.perform();
        clickElement(typingMenu);
    }

    public void clickTranslationMenu() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(translationMenu).build();
        mouseOverHome.perform();
        clickElement(translationMenu);
    }

    public void clickTranscriptionMenu() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(transcriptionMenu).build();
        mouseOverHome.perform();
        clickElement(transcriptionMenu);
    }

    public void clickCaptioningMenu() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(captioningMenu).build();
        mouseOverHome.perform();
        clickElement(captioningMenu);
    }

    public void clickPopUpCloseButton() {

        waitForPageLoad(driver);
        try {
            if(popUpCloseButtonElement.isDisplayed()) {
                js.executeScript("$('.close').click();");
            } else {
                System.out.println("Element not displayed");
            }

        } catch (Exception ex) {
            System.out.println("Unable to close pop window : " + ex);
        }
        waitForPageLoad(driver);
    }

    public void clickBackToDashBoardPage() {

        clickElement(backToDashBoardElement);
    }

    public void clickLogOut() {

        waitForPageLoad(driver);
        WebElement element = driver.findElement(By.xpath
                ("//div[@class='nav_menu']/nav/ul/li/a"));
        element.click();
        element = driver.findElement(By.linkText("Log Out"));
        element.click();
    }

    private int getOrderTableRowCount() {

        return myOrderTableRows.size();
    }

    public String getTicketNumber() {

        waitForPageLoad(driver);
        String value = driver.findElement(
                By.xpath("//table[@id='my_table']/tbody/tr[1]/td[2]")).getText();
        return value;
    }

    public String getOrderNumber() {

        String value = driver.findElement(
                By.className("ord_number")).getText();
        return value;
    }

    public String getServiceName() {

        String value = driver.findElement(
                By.xpath("//table[@id='my_table']/tbody/tr[1]/td[3]")).getText();
        return value;
    }

    public String getStatus() {

        String value = driver.findElement(
                By.xpath("//table[@id='my_table']/tbody/tr[1]/td[5]")).getText();
        return value;
    }

    public String getCreatedDate() {

        String value = driver.findElement(
                By.xpath("//table[@id='my_table']/tbody/tr[1]/td[6]")).getText();
        return value;
    }

    public void clickParticularTicket(String ticketID) {

        waitForPageLoad(driver);
        if (getTicketNumber().contains(ticketID)) {

            clickElement(driver.findElement(By.xpath(
                    "//table[@id='my_table']/tbody/tr[1]/td[2]")));
        }
        waitForPageLoad(driver);
    }

    public void clickOrderInfo() {

        if (!driver.findElement(By.id("home-tab"))
                .isSelected()) {

            js.executeScript("$('#home-tab').click();");
        }
        waitForPageLoad(driver);
    }

    public void clickUploadedFileDetails() {

        WebElement element = driver.findElement(By.xpath
                ("//li[@id='content4']/a[@id='profile-tab']"));
        if (!element
                .isSelected()) {
            clickElement(element);
        }
        waitForPageLoad(driver);
    }


    public String getOrderInfoTableValues(String field) {

        List<WebElement> elements = driver
                .findElements(By.xpath("//div[@id='tab_content1']/div/table/tbody/tr"));
        String value = "";
        WebElement eachElement;
        for (int i = 1; i <= elements.size(); i++) {
            try {
                eachElement = driver.findElement(
                        By.xpath("//div[@id='tab_content1']/div/table/tbody/tr[" + i
                                + "]/th[1]"));
                //System.out.println(eachElement.getText()+")))))))))))))");
                if (eachElement.getText().contains(field)) {
                    value = driver.findElement(
                            By.xpath("//div[@id='tab_content1']/div/table/tbody/tr["
                                    + i + "]/td[1]"))
                            .getText();
                    break;
                }

            } catch (Exception ex) {
                continue;
            }
        }
        return value;
    }

    public String getUploadFileName() {

        return driver.findElement(By.xpath
                ("//div[@id='tab_content4']/div/table/tbody/tr[1]/td[2]"))
                .getText();
    }

    public String getDeliveredFileName() {

        return driver.findElement(By.xpath
                ("//div[@id='tab_content2']/div/table/tbody/tr[1]/td[2]"))
                .getText();
    }

    public void clickDownloadDeliveredFile() {

        clickElement(driver.findElement(By.xpath
                ("//div[@id='tab_content2']/div/table/tbody/tr[1]/td[3]/a")));
    }

    public String getDeliveredFileDate() {

        return driver.findElement(By.xpath
                ("//div[@id='tab_content2']/div/table/tbody/tr[1]/td[4]"))
                .getText();
    }

    public void clickclosePopupWindow() {
        if (!driver.findElement(By.className("close"))
                .isSelected()) {

            js.executeScript("$('.close').click();");
        }
        waitForPageLoad(driver);
    }
}
