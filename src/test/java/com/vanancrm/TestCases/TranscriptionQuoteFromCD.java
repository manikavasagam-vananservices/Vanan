package com.vanancrm.TestCases;

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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class TranscriptionQuoteFromCD extends TestBase {

    private String[] languages = {"English", "Apache"};
    private String service = "Transcription";
    private String mailId = "automation.vananservices@gmail.com";
    private String comments = "Automation Testing";
    private String minute = "10";
    private String status = "Yes";
    private String fileName = service;
    private String fileExtenstion = ".txt";
    private static String username = "";
    private static String password = "";
    private String url = "vananservices.com";

    private String channel = "Customer Dashboard";
    public String ticketID = "";

    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;
    private DashBoard dashBoard;
    private WebDriver driver;

    @Test
    public void typingServices() throws AWTException,
            InterruptedException, IOException {

        System.out.println("======================================");
        System.out.println("Scenario Started");
        System.out.println("======================================");

        testScenario(languages[0]);
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
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        fullScreen(driver);
        driver.get("https://vananservices.com/customer/");
        getCRMCreadential();
    }

    @AfterClass
    public void afterClass() throws IOException {

        screenshot(driver, "Typing services form customer dashboard");
        driver.quit();
    }

    private void raiseTicket(String language) throws AWTException,
            InterruptedException, IOException {

        LoginPage loginPage = new LoginPage(driver);
        dashBoard = loginPage.signIn(mailId,
                password);
        dashBoard.clickPopUpCloseButton();
        waitForProcessCompletion(10);
        dashBoard.clickTranscriptionMenu();
        FreeTrailPage freeTrailPage = new FreeTrailPage(driver);
        freeTrailPage.selectSourceLanguage(language);
        freeTrailPage.uploadFile(driver, fileName, fileExtenstion);
        if (language.equals(languages[0])) {

            freeTrailPage.clickUSNativeTranscribers();
        }
        freeTrailPage.clickTimeCoding();
        freeTrailPage.clickVerbatim();
        freeTrailPage.enterMinute(minute);
        freeTrailPage.enterComment(comments);
        freeTrailPage.clickSubmit();
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("order-success.php")) {
            System.out.println(currentUrl + " and it pass");
        } else {
            System.out.println(currentUrl + " and it fail");
        }
        dashBoard.clickBackToDashBoardPage();
        dashBoard.clickLogOut();
    }

    private void checkCRM(String language) {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, language);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String language) {

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
                if (viewTicketDetails.getRunTimeTicketFieldValues("Email")
                        .contains(mailId) && viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel")
                        .contains(channel)) {

                    ticketID = tickets.get(i).substring(tickets.get(i).indexOf("VS"),
                            tickets.get(i).indexOf(service) - 1);
                    System.out.println((i + 1) + " : Channel = " +
                            viewTicketDetails.getRunTimeTicketFieldValues(
                                    "Channel"));
                    System.out.println("Ticket ID: " + ticketID);

                    checkViewTicketInfo(language);
                    changeTicketStatus();
                    checkCRMEmailConversation(language);
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
        edit.selectStatus("Others");
        edit.clickUpdateButton();
        emailConversation = menus.clickEmailConversation();
        emailConversation.clickReadMore(channel);
    }

    private void checkCRMEmailConversation(String language) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        evaluateCondition("Service (H)",
                emailConversation.getServiceDetails(), service);
        evaluateCondition("Minutes",
                emailConversation.getTicketFieldValues("Minutes"), minute);

        evaluateCondition("Transcription Language",
                emailConversation.getTicketFieldValues("Transcription "
                        + "Language"), language);

        evaluateCondition("Files", emailConversation
                .getTicketFieldValues("Files"), fileName + fileExtenstion);
        evaluateCondition("Files Link", emailConversation
                .getTicketFieldValues("Files Link"), fileName + fileExtenstion);
        System.out.println("Turnaround Time : " + emailConversation
                .getTicketFieldValues("Turnaround Time"));
        /*evaluateCondition("Turnaround Time", emailConversation
            .getTicketFieldValues("Turnaround Time"), status);*/
        evaluateCondition("Verbatim", emailConversation
                .getTicketFieldValues("Verbatim"), status);
        evaluateCondition("Time Code", emailConversation
                .getTicketFieldValues("Time Code"), status);
        if (language.equals(languages[0])) {
            evaluateCondition("Native Transcribers", emailConversation
                    .getTicketFieldValues("Native Transcribers"), status);
        }

        evaluateCondition("Comment",
                emailConversation.getTicketFieldValues("Comment"), comments);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }
    private void checkViewTicketInfo(String language) {

        System.out.println("===========================================");
        System.out.println("Checking View Ticket Details");
        System.out.println("===========================================\n");

        evaluateCondition("Email", viewTicketDetails
                .getRunTimeTicketFieldValues("Email"), mailId);
        evaluateCondition("Websites", url,
                viewTicketDetails.getRunTimeTicketFieldValues("Websites"));
        evaluateCondition("Channel", viewTicketDetails
                .getRunTimeTicketFieldValues("Channel"), channel);
        evaluateCondition("Language", viewTicketDetails
                .getRunTimeTicketFieldValues("Language"), language);

        if(language.equals(languages[0])) {
            evaluateCondition("Native", viewTicketDetails
                    .getRunTimeTicketFieldValues("Native"), status);
        }

        evaluateCondition("Minutes", viewTicketDetails
                .getRunTimeTicketFieldValues("Minutes"), minute);
        evaluateCondition("TimeCoding", viewTicketDetails
                .getRunTimeTicketFieldValues("TimeCoding"), status);
        evaluateCondition("Verbatim", viewTicketDetails
                .getRunTimeTicketFieldValues("Verbatim"), status);
        evaluateCondition("Comment", viewTicketDetails
                .getRunTimeTicketFieldValues("Comment"), comments);
        evaluateCondition("Attachment", viewTicketDetails
                .getRunTimeTicketFieldValues("Attachment"), status);
        /*evaluateCondition("ETAT", viewTicketDetails
            .getRunTimeTicketFieldValues("ETAT"), "");*/
        System.out.println("ETAT : " + viewTicketDetails
                .getRunTimeTicketFieldValues("ETAT"));
        evaluateCondition("Service", viewTicketDetails
                .getServiceValues(), service);
        System.out.println("===========================================\n");
    }

    private void testScenario(String language) throws AWTException,
            InterruptedException, IOException {

        raiseTicket(language);
        getCRMCreadential();
        checkCRM(language);
    }

    public String getTicketID() throws AWTException, InterruptedException, IOException {

        beforeClass();
        testScenario(languages[0]);
        afterClass();
        return  ticketID;
    }
}
