package com.vanancrm.TestCases.CustomerDashboard;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;
import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;
import com.vanan.CustomerDashboard.PageObjects.MainPages.DashBoard;
import com.vanan.CustomerDashboard.PageObjects.WholeSitePages.LoginPage;
import com.vanancrm.Common.TestBase;
import com.vanancrm.PageObjects.MainPages.FreeTrailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.Cookie;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class CaptioningQuoteFromCD extends TestBase {

    private String channel = "Customer Dashboard";
    private String minute = "20";
    private String url = "vananservices.com";
    private static String username = "";
    private static String password = "";
    public String ticketID = "";

    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;
    private DashBoard dashBoard;
    private WebDriver driver;

    private String[] sourceLanguages = {"English", "Apache"};
    private String[] targetLanguages = {"Spanish", "Afar"};
    private String[] fileTypes = {"Document", "Audio/Video"};
    private String service = "Captioning";
    private String serviceFrequency = "Monthly";

    private String mailId = "automation@vananservices.com";
    private String comments = "Automation Testing";
    private String status = "Yes";
    private String fileName = "AutomationTesting";
    private String fileExtenstion = ".txt";
    private String[] fileFormats = {"Standalone", "Embedded"};
    private String[] transcriptions = {"Yes", "No"};

    @Test
    public void typingServices() throws AWTException,
            InterruptedException, IOException {

        System.out.println("======================================");
        System.out.println("Scenario Started");
        System.out.println("======================================");

        testScenario(sourceLanguages[0], targetLanguages[0], fileFormats[0],
                transcriptions[0]);
        System.out.println("Test Completed");
        System.out.println("======================================");
    }

    private static void getCRMCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }

    @BeforeClass
    public void beforeClass() throws IOException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1900,1200");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        fullScreen(driver);
        driver.get("https://vananservices.com/customer/");
        getCRMCreadential();
    }

    @AfterClass
    public void afterClass() throws IOException {

        screenshot(driver, "Captioning services form customer dashboard");
        driver.quit();
    }

    private void raiseTicket(String slanguage, String tlanguage, String
            fileFormat, String transcription) throws AWTException,
            InterruptedException, IOException {

        LoginPage loginPage = new LoginPage(driver);
        dashBoard = loginPage.signIn(mailId,
                password);
        dashBoard.clickPopUpCloseButton();
        waitForProcessCompletion(20);
        dashBoard.clickCaptioningMenu();
        FreeTrailPage freeTrailPage = new FreeTrailPage(driver);
        freeTrailPage.selectSourceLanguage(slanguage);
        freeTrailPage.selectLanguageTo(tlanguage);
        freeTrailPage.selectFileFormat(fileFormat);
        freeTrailPage.selectTranscription(transcription);
        freeTrailPage.uploadFile(driver, fileName, fileExtenstion);
        freeTrailPage.enterVideoLength(minute);
        freeTrailPage.selectServiceFrequency(serviceFrequency);
        freeTrailPage.enterComment(comments);
        freeTrailPage.clickSubmit();
       
        if(freeTrailPage.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
            System.out.println("Accept button is pressed => Pass");
        } else {
            System.out.println("Accept button is not pressed => Fail");
        }
        freeTrailPage.clickPrivacyPolicy();
        freeTrailPage.clickSubmit();
        waitForProcessCompletion(30);
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("order-success.php")) {
            System.out.println(currentUrl + " and it pass");
        } else {
            System.out.println(currentUrl + " and it fail");
        }
        dashBoard.clickBackToDashBoardPage();
        dashBoard.clickLogOut();
    }

    private void checkCRM(String slanguage, String tlanguage, String
            fileFormat, String transcription) {

        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, slanguage, tlanguage, fileFormat, transcription);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String slanguage, String tlanguage,
            String fileFormat, String transcription) {

        ticketID = "";
        readTableData = menus.clickNewMenu();
        List<String> tickets = readTableData.readTableRows();

        for (int i = 0; i < tickets.size(); i++) {

            if (tickets.get(i).contains(service)) {

                viewTicketDetails = new ViewTicketDetails(driver);
                viewTicketDetails = readTableData.clickService(service,
                        (i + 1));
                System.out.println("Channel " + viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel"));
                if (viewTicketDetails.getEmailId()
                        .contains(mailId) && viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel")
                        .contains(channel)) {

                    ticketID = tickets.get(i).substring(tickets.get(i).indexOf("VS"),
                            tickets.get(i).indexOf(service) - 1);
                    System.out.println((i + 1) + " : Channel = " +
                            viewTicketDetails.getRunTimeTicketFieldValues(
                                    "Channel"));
                    System.out.println("Ticket ID: " + ticketID);

                    checkViewTicketInfo(slanguage, tlanguage, fileFormat, transcription);
                    changeTicketStatus();
                    checkCRMEmailConversation(slanguage, tlanguage, fileFormat, transcription);
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
        emailConversation = menus.clickEmailConversation();
        emailConversation.clickReadMore(channel);
    }

    private void checkCRMEmailConversation(String slanguage, String tlanguage,
            String fileFormat, String transcription) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");

        evaluateCondition("Service (H)",
                emailConversation.getServiceDetails(), service);
        evaluateCondition("Need Transcription",
                emailConversation.getTicketFieldValues("Need Transcription"),
                transcription);

        evaluateCondition("Source Language",
                emailConversation.getTicketFieldValues("Source Language"), slanguage);
        evaluateCondition("Target Language",
                emailConversation.getTicketFieldValues("Target Language"), tlanguage);
        evaluateCondition("Files", emailConversation
                .getTicketFieldValues("Files"), fileName + fileExtenstion);

        evaluateCondition("Files Link", emailConversation
                .getTicketFieldValues("Files Link"), fileName + fileExtenstion);
        evaluateCondition("Video Length", emailConversation
                .getTicketFieldValues("Video Length"), minute);
        evaluateCondition("Formats", emailConversation
                .getTicketFieldValues("Formats"), fileFormat);
        System.out.println("Turnaround Time : " + emailConversation
                .getTicketFieldValues("Turnaround Time"));
        /*evaluateCondition("Turnaround Time", emailConversation
            .getTicketFieldValues("Turnaround Time"), status);*/
        evaluateCondition("Comment",
                emailConversation.getTicketFieldValues("Comment"), comments);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }

    private void checkViewTicketInfo(String slanguage, String tlanguage, String
            fileFormat, String transcription) {

        System.out.println("===========================================");
        System.out.println("Checking View Ticket Details");
        System.out.println("===========================================\n");

        evaluateCondition("Email", viewTicketDetails
                .getEmailId(), mailId);
        evaluateCondition("Websites", url,
                viewTicketDetails.getWebsite());
        evaluateCondition("Channel", viewTicketDetails
                .getRunTimeTicketFieldValues("Channel"), channel);
        evaluateCondition("Video Source Language", viewTicketDetails
                .getRunTimeTicketFieldValues("Video Source Language"), slanguage);
        evaluateCondition("Video Target Language", viewTicketDetails
                .getRunTimeTicketFieldValues("Video Target Language"), tlanguage);
        evaluateCondition("Video Length", viewTicketDetails
                .getRunTimeTicketFieldValues("Video Length"), minute);
        evaluateCondition("Comment", viewTicketDetails
                .getRunTimeTicketFieldValues("Comment"), comments);
        evaluateCondition("Attachment", viewTicketDetails
                .getRunTimeTicketFieldValues("Attachment"), status);
        evaluateCondition("Need Transcription", viewTicketDetails
                .getRunTimeTicketFieldValues("Need Transcription"), transcription);
        evaluateCondition("Format", viewTicketDetails
                .getRunTimeTicketFieldValues("Format"), fileFormat);
        System.out.println("ETAT : " + viewTicketDetails
                .getRunTimeTicketFieldValues("ETAT"));
        /*evaluateCondition("ETAT", viewTicketDetails
            .getRunTimeTicketFieldValues("ETAT"), "");*/
        evaluateCondition("Service", viewTicketDetails
                .getServiceValues(), service);
        System.out.println("===========================================\n");
    }

    private void testScenario(String slanguage, String tlanguage, String
            fileFormat, String transcritpion)
            throws AWTException,
            InterruptedException, IOException {

        raiseTicket(slanguage, tlanguage, fileFormat, transcritpion);
        checkCRM(slanguage, tlanguage, fileFormat, transcritpion);
    }

    public String getTicketID() throws AWTException, InterruptedException, IOException {

        beforeClass();
        testScenario(sourceLanguages[0], targetLanguages[0], fileFormats[0],
                transcriptions[0]);
        afterClass();
        return  ticketID;
    }
}
