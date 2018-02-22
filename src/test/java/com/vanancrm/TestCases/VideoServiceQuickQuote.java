package com.vanancrm.TestCases;

import java.io.FileReader;
import java.io.IOException;

import java.util.Properties;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.UnknownEmail;

import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadUnknownEmailData;

import com.vanancrm.PageObjects.MainPages.QuickQuoteForVideoService;

import com.vanancrm.Common.TestBase;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class VideoServiceQuickQuote extends TestBase {

    private WebDriver driver;

    private String service = "Video Services";
    private String customerName = "Automation";
    private String mailId = "automation.vananservices@gmail.com";
    private String country = "India";
    private String areaCode = "600058";
    private String phoneNumber = "1-888-535-5668";
    private String minutes = "180";
    private String shotType = "3/4Body Shot";
    private String transition = "Fade In / Fade Out";
    private String toneOfVideo = "Sales pitch";
    private String background = "Noise";
    private String status = "Yes";
    private String comments = "Automation Testing";

    private String[] sizes = {"Medium", "Small"};
    private String[] talent = {"Male Casual", "Female Suit"};

    private static String username = "";
    private static String password = "";
    private String url = "";

    private Menus menus;

    @Test
    public void videoServices() throws IOException {

        System.out.println("===========================================");
        System.out.println("Scenario Started");
        System.out.println("===========================================");
        raiseTicket();
        waitForProcessCompletion(90);
        getCRMCreadential();
        checkCRM();
        System.out.println("Test Completed");
        System.out.println("===========================================");
    }

    @BeforeClass
    public void beforeClass() {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        fullScreen(driver);
    }

    @AfterClass
    public void afterClass() throws IOException {

        screenshot(driver, "VideoServiceQuickQuote");
        driver.quit();
    }

    private void raiseTicket() {

        url = System.getProperty("website");
        driver.get(url);
        QuickQuoteForVideoService quickQuoteForVideoService = new
                QuickQuoteForVideoService(driver);
        quickQuoteForVideoService.enterName(customerName);
        quickQuoteForVideoService.enterEmail(mailId);
        quickQuoteForVideoService.selectCountry(country);
        quickQuoteForVideoService.enterAreaCode(areaCode);
        quickQuoteForVideoService.enterPhoneNumber(phoneNumber);
        quickQuoteForVideoService.selectTalent(talent);
        quickQuoteForVideoService.enterVideoLength(minutes);
        quickQuoteForVideoService.selectVideoSize(sizes);
        quickQuoteForVideoService.selectShotType(shotType);
        quickQuoteForVideoService.selectVideoTransition(transition);
        quickQuoteForVideoService.selectToneOfVideo(toneOfVideo);
        quickQuoteForVideoService.enterBackground(background);
        quickQuoteForVideoService.enterVideoScript(status);
        quickQuoteForVideoService.enterComments(comments);
        quickQuoteForVideoService.clickSubmit();
        waitForProcessCompletion(10);
    }

    private void checkCRM() {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickUnknownMail();
        UnknownEmail unknownEmail = new UnknownEmail(driver);
        unknownEmail.selectParticularEmail(customerName, "Video Services Quote");
        checkCRMEmailConversation();
        unknownEmail.changeUnknownMailStaus("Folder", "Others");
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }


    private static void getCRMCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }

    private void evaluateCondition(String message, String first,
        String second) {

        System.out.print(message + " : " + second);
        if (first.contains(second)) {

            System.out.print("\t Status : Pass\n");
        } else {
            System.out.print("\t Status : [Fail]\n");
            System.out.print("\t Expected : " + first + "\n");
            System.out.print("\t Actual : " + second + "\n");
        }
    }

    private void checkCRMEmailConversation() {

        System.out.println("\n===========================================");
        System.out.println("Checking Unknown Email Conversation");
        System.out.println("===========================================\n");
        ReadUnknownEmailData readUnknownEmailData = new ReadUnknownEmailData
                (driver);

        evaluateCondition(service,
                readUnknownEmailData.getServiceValues(), service);
        evaluateCondition("Name", readUnknownEmailData
                .getRunTimeTicketFieldValues("Name"), customerName);
        evaluateCondition("Email ID", readUnknownEmailData
                .getRunTimeTicketFieldValues("Email ID"), mailId);
        evaluateCondition("Country", readUnknownEmailData
                .getRunTimeTicketFieldValues("Country"), country);

        evaluateCondition("Phone", readUnknownEmailData
                .getRunTimeTicketFieldValues("Phone"), areaCode + " - "
                + phoneNumber);
        evaluateCondition("Chosed Talent", readUnknownEmailData
                .getRunTimeTicketFieldValues("Chosed Talent"), talent[0]
                + "," + talent[1]);
        evaluateCondition("Video Length", readUnknownEmailData
                .getRunTimeTicketFieldValues("Video Length"), minutes);

        evaluateCondition("Video Size", readUnknownEmailData
                .getRunTimeTicketFieldValues("Video Size"), sizes[0]
                + "," + sizes[1]);
        evaluateCondition("Shot Type", readUnknownEmailData
                .getRunTimeTicketFieldValues("Shot Type"), shotType);
        evaluateCondition("Transition", readUnknownEmailData
                .getRunTimeTicketFieldValues("Transition"), transition);
        evaluateCondition("Tone Of Video", readUnknownEmailData
                .getRunTimeTicketFieldValues("Tone Of Video"), toneOfVideo);

        evaluateCondition("Background", readUnknownEmailData
                .getRunTimeTicketFieldValues("Background"), background);
        evaluateCondition("Script", readUnknownEmailData
                .getRunTimeTicketFieldValues("Script"), status);
        evaluateCondition("Comment", readUnknownEmailData
                .getRunTimeTicketFieldValues("Comment"), comments);

        System.out.println("===========================================");
    }
}
