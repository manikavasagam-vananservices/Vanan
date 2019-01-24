package com.vanancrm.TestCases.FreeTrial;

import java.awt.AWTException;

import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

import java.util.concurrent.TimeUnit;

import com.vanancrm.PageObjects.MainPages.FreeTrailPage;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;

import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;

import com.vanancrm.Common.TestBase;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class TranslationFreeTrialPage extends TestBase {

    private WebDriver driver;

    private String[] sourceLanguages = {"English", "Apache"};
    private String[] targetLanguages = {"Spanish", "Afar"};
    private String[] fileTypes = {"Document", "Audio/Video"};
    private String service = "Translation";
    private String channel = "Free Trial";

    private String mailId = "automation@vananservices.com";
    private String name = "Automation";
    private String country = "Alabama";
    private String phoneNumber = "1-888-535-5668";
    private String comments = "Automation Testing";
    private String status = "Yes";
    private String fileName = "";
    private String fileExtenstion = ".txt";
    private String url = "";

    private static String username = "";
    private static String password = "";

    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    @Test
    public void translationServices() throws AWTException,
        InterruptedException, IOException {

        System.out.println("======================================");
        System.out.println("Scenario Started");
        System.out.println("======================================");
        testScenario(sourceLanguages[0], targetLanguages[0], fileTypes[0]);
        testScenario(sourceLanguages[1], targetLanguages[1], fileTypes[1]);
        System.out.println("Test Completed");
        System.out.println("======================================");
    }

    @BeforeClass
    public void beforeClass() {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        fullScreen(driver);
    }

    @AfterClass
    public void afterClass() throws IOException {

        screenshot(driver, "TranslationFreeTrialPage");
        driver.quit();
    }

    private void raiseTicket(String slanguage, String tlanguage, String fileType)
        throws AWTException,
        InterruptedException, IOException {

        url = System.getProperty("website");
        driver.get(url);
        FreeTrailPage freeTrailPage = new FreeTrailPage(driver);
        freeTrailPage.enterName(name);
        freeTrailPage.enterEmail(mailId);
        freeTrailPage.selectCountry(country);
        freeTrailPage.enterPhoneNo(phoneNumber);
        freeTrailPage.selectSourceLanguage(slanguage);
        freeTrailPage.selectLanguageTo(tlanguage);
        freeTrailPage.selectFileType(fileType);
        fileName = "AutomationTesting";
        freeTrailPage.uploadFile(driver, fileName, fileExtenstion);
        waitForProcessCompletion(30);
        freeTrailPage.enterComment(comments);
        waitForProcessCompletion(10);
        freeTrailPage.clickSubmit();
        screenshot(driver, url.substring(url.indexOf("//")+2,url.indexOf(".")));
        if(freeTrailPage.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
            System.out.println("Accept button is pressed => Pass");
        } else {
            System.out.println("Accept button is not pressed => Fail");
        }
        freeTrailPage.clickPrivacyPolicy();
        freeTrailPage.clickSubmit();
        waitForProcessCompletion(30);
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("success.php")) {
            System.out.println(currentUrl + " and it pass");
        } else {
            System.out.println(currentUrl + " and it fail");
        }
    }

    private void checkCRM(String slanguage, String tlanguage, String fileType) {

        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        waitForProcessCompletion(10);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, slanguage, tlanguage, fileType);
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }


    private void checkTickets(Menus menus, String slanguage, String tlanguage,
        String fileType) {

        String ticketID = "";
        readTableData = menus.clickNewMenu();
        List<String> tickets = readTableData.readTableRows();

        for (int i = 0; i < tickets.size(); i++) {

            if (tickets.get(i).contains(service)) {

                viewTicketDetails = new ViewTicketDetails(driver);
                waitForProcessCompletion(20);
                viewTicketDetails = readTableData.clickService(service,
                        (i + 1));
                waitForProcessCompletion(20);
                driver.findElement(By.id("view_btn")).click();
                waitForProcessCompletion(10);
                System.out.println("Channel " + viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel"));
                if (viewTicketDetails.getEmailId()
                        .contains(mailId) && viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel")
                        .contains(channel) && url.contains(viewTicketDetails
                        .getWebsite())) {

                    ticketID = tickets.get(i).substring(tickets.get(i).indexOf("VS"),
                            tickets.get(i).indexOf(service) - 1);
                    System.out.println((i + 1) + " : Channel = " +
                            viewTicketDetails.getRunTimeTicketFieldValues(
                                    "Channel"));
                    System.out.println("Ticket ID: " + ticketID);

                    checkViewTicketInfo(slanguage, tlanguage, fileType);
                    changeTicketStatus();
                    checkCRMEmailConversation(slanguage, tlanguage, fileType);
                    break;
                } else {
                    ticketID = "\n\nEither ticket is Not created or Still" +
                            " waiting for ticket";
                    System.out.println(ticketID);
                }
                waitForProcessCompletion(60);
            }
        }
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
            System.out.print("\t Expected : " + second + "\n");
            System.out.print("\t Actual : " + first + "\n");
        }
    }

    private void changeTicketStatus() {

        // Edit a ticket and moved the status into Others
        Edit edit = menus.clickEdit();
        
        edit.selectPaymentType("Full payment");
        edit.selectPaymentMode("Square");
        edit.selectStatus("Others");
        edit.clickUpdateButton();
        waitForProcessCompletion(10);
        emailConversation = menus.clickEmailConversation();
        emailConversation.clickReadMore("Free Trial");
    }

    private void checkCRMEmailConversation(String slanguage, String tlanguage,
            String fileType) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        evaluateCondition("Service (H)",
            emailConversation.getServiceDetails(), service);
        evaluateCondition("Name",
            emailConversation.getTicketFieldValues("Name"), name);
        evaluateCondition("Phone Number",
            emailConversation.getTicketFieldValues("Phone Number"), phoneNumber);

        evaluateCondition("Translate From",
            emailConversation.getTicketFieldValues("Translate From"), slanguage);
        evaluateCondition("Translate To",
            emailConversation.getTicketFieldValues("Translate To"), tlanguage);
        evaluateCondition("File Type",
            emailConversation.getTicketFieldValues("File Type"), fileType);
        evaluateCondition("File(s)", emailConversation
            .getTicketFieldValues("File(s)"), fileName + fileExtenstion);
        evaluateCondition("File(s) Link", emailConversation
            .getTicketFieldValues("File(s) Link"), fileName + fileExtenstion);
        /*evaluateCondition("Turnaround Time", emailConversation
            .getTicketFieldValues("Turnaround Time"), status);*/
        System.out.println("Turnaround Time : " + emailConversation
                .getTicketFieldValues("Turnaround Time"));

        evaluateCondition("Comment",
            emailConversation.getTicketFieldValues("Comment"), comments);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }

    private void checkViewTicketInfo(String slanguage, String tlanguage, String
            fileType) {

        System.out.println("===========================================");
        System.out.println("Checking View Ticket Details");
        System.out.println("===========================================\n");

        evaluateCondition("Email", viewTicketDetails
            .getEmailId(), mailId);
        evaluateCondition("Websites", url,
            viewTicketDetails.getWebsite());
        evaluateCondition("Channel", viewTicketDetails
            .getRunTimeTicketFieldValues("Channel"), channel);
        evaluateCondition("Translate From", viewTicketDetails
            .getRunTimeTicketFieldValues("Translate From"), slanguage);
        evaluateCondition("TranslateTo", viewTicketDetails
            .getRunTimeTicketFieldValues("TranslateTo"), tlanguage);
        evaluateCondition("Name", viewTicketDetails
            .getRunTimeTicketFieldValues("Name"), name);
        evaluateCondition("Country", viewTicketDetails
            .getRunTimeTicketFieldValues("Country"), country);
        evaluateCondition("PhoneNo", viewTicketDetails
            .getRunTimeTicketFieldValues("PhoneNo"), phoneNumber);

        evaluateCondition("Comment", viewTicketDetails
            .getRunTimeTicketFieldValues("Comment"), comments);
        evaluateCondition("Attachment", viewTicketDetails
            .getRunTimeTicketFieldValues("Attachment"), status);
        evaluateCondition("Audio/video", viewTicketDetails
            .getRunTimeTicketFieldValues("Audio/video"), fileType);
        /*evaluateCondition("ETAT", viewTicketDetails
            .getRunTimeTicketFieldValues("ETAT"), "");*/
        System.out.println("ETAT : " + viewTicketDetails
                .getRunTimeTicketFieldValues("ETAT"));
        evaluateCondition("Service", viewTicketDetails
            .getServiceValues(), service);
        System.out.println("===========================================\n");
    }

    private void testScenario(String slanguage, String tlanguage, String
        fileType) throws AWTException, InterruptedException, IOException {

        raiseTicket(slanguage, tlanguage, fileType);
        getCRMCreadential();
        checkCRM(slanguage, tlanguage, fileType);
    }
}
