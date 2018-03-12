package com.vanancrm.TestCases;

import java.awt.AWTException;

import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vanan.CustomerDashboard.PageObjects.MainPages.DashBoard;
import com.vanan.CustomerDashboard.PageObjects.WholeSitePages.LoginPage;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;

import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;

import com.vanancrm.Common.TestBase;

import com.vanancrm.PageObjects.MainPages.FreeTrailPage;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class TypingQuoteFromCD extends TestBase {

    private WebDriver driver;

    private String[] languages = {"English", "Apache"};
    private String[] typeOfServices = {"Document", "Audio/Video"};
    private String[] formattings = {"Yes", "No", "Handwritten"};
    private String service = "Typing";
    private String channel = "Customer Dashboard";

    private String mailId = "automation.vananservices@gmail.com";
    private String comments = "Automation Testing";
    private String status = "Yes";
    private String fileName = "";
    private String fileExtenstion = ".txt";
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

    @Test
    public void typingServices() throws AWTException,
            InterruptedException, IOException {

        System.out.println("======================================");
        System.out.println("Scenario Started");
        System.out.println("======================================");

        testScenario(languages[0], typeOfServices[0], formattings[0]);
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

        screenshot(driver, "Typing services form customer dashboard");
        driver.quit();
    }

    private void raiseTicket(String language, String typeOfService, String
            formatting) throws AWTException, InterruptedException, IOException {
        LoginPage loginPage = new LoginPage(driver);
        dashBoard = loginPage.signIn(mailId,
                password);
        dashBoard.clickPopUpCloseButton();
        waitForProcessCompletion(10);
        dashBoard.clickTypingMenu();
        FreeTrailPage freeTrailPage = new FreeTrailPage(driver);
        freeTrailPage.selectTypeOfService(typeOfService);
        freeTrailPage.selectSourceLanguage(language);

        if(typeOfService.equals(typeOfServices[0])) {

            freeTrailPage.enterPages(minute);
            freeTrailPage.selectFormatting(formatting);
        } else {
            freeTrailPage.enterMinute(minute);
            freeTrailPage.clickVerb();
            freeTrailPage.clickTimeCode();
        }

        fileName = service;
        freeTrailPage.uploadFile(driver, fileName, fileExtenstion);
        waitForProcessCompletion(20);
        freeTrailPage.enterComment(comments);
        freeTrailPage.clickSubmit();
        waitForProcessCompletion(30);
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("order-success.php")) {
            System.out.println(currentUrl + " and it pass");
        } else {
            System.out.println(currentUrl + " and it fail");
        }
        dashBoard.clickBackToDashBoardPage();
        waitForProcessCompletion(10);
        dashBoard.clickLogOut();
    }

    private void checkCRM(String language, String typeOfService, String formatting) {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        waitForProcessCompletion(10);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, language, typeOfService, formatting);
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String language, String typeOfService,
            String formatting) {


        readTableData = menus.clickNewMenu();
        List<String> tickets = readTableData.readTableRows();

        for (int i = 0; i < tickets.size(); i++) {

            if (tickets.get(i).contains(service)) {

                viewTicketDetails = new ViewTicketDetails(driver);
                waitForProcessCompletion(20);
                viewTicketDetails = readTableData.clickService(service,
                        (i + 1));
                waitForProcessCompletion(20);
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

                    checkViewTicketInfo(language, typeOfService,formatting);
                    changeTicketStatus();
                    checkCRMEmailConversation(language, typeOfService,formatting);
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
        waitForProcessCompletion(10);
        emailConversation = menus.clickEmailConversation();
        emailConversation.clickReadMore(channel);
    }

    private void checkCRMEmailConversation(String language, String
            typeOfService, String formatting) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        evaluateCondition("Service (H)",
                emailConversation.getServiceDetails(), service);
        evaluateCondition("Type Of Service",
                emailConversation.getTicketFieldValues("Type Of Service"),
                typeOfService);
        if(typeOfService.equals(typeOfServices[0])) {
            evaluateCondition("No Of Pages", emailConversation
                    .getTicketFieldValues("No Of Pages"), minute);
            evaluateCondition("Formatting", emailConversation
                    .getTicketFieldValues("Formatting"), formatting);
        } else {
            evaluateCondition("Minutes", emailConversation
                    .getTicketFieldValues("Minutes"), minute);
            evaluateCondition("Verbatim", emailConversation
                    .getTicketFieldValues("Verbatim"), status);
            evaluateCondition("Time Code", emailConversation
                    .getTicketFieldValues("Time Code"), status);
        }
        evaluateCondition("Language", emailConversation
                .getTicketFieldValues("Language"), language);

        evaluateCondition("Files", emailConversation
                .getTicketFieldValues("Files"), fileName + fileExtenstion);
        evaluateCondition("Files Link", emailConversation
                .getTicketFieldValues("Files Link"), fileName + fileExtenstion);
        System.out.println("Turnaround Time : " + emailConversation
                .getTicketFieldValues("Turnaround Time"));
        /*evaluateCondition("Turnaround Time", emailConversation
            .getTicketFieldValues("Turnaround Time"), status);*/

        evaluateCondition("Comment",
                emailConversation.getTicketFieldValues("Comment"), comments);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }

    private void checkViewTicketInfo(String language, String typeOfService,
            String formatting) {

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

        evaluateCondition("Service Type", viewTicketDetails
                .getRunTimeTicketFieldValues("Service Type"), typeOfService);
        if (typeOfService.equals(typeOfServices[0])) {
            evaluateCondition("Pages", viewTicketDetails
                    .getRunTimeTicketFieldValues("Pages"), minute);
            evaluateCondition("Formatting", viewTicketDetails
                    .getRunTimeTicketFieldValues("Formatting"), formatting);
        } else {
            evaluateCondition("Minutes", viewTicketDetails
                    .getRunTimeTicketFieldValues("Minutes"), minute);
            evaluateCondition("TimeCoding", viewTicketDetails
                    .getRunTimeTicketFieldValues("TimeCoding"), status);
            evaluateCondition("Verbatim", viewTicketDetails
                    .getRunTimeTicketFieldValues("Verbatim"), status);
        }

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

    private void testScenario(String language, String typeOfService, String formatting)
            throws AWTException, InterruptedException, IOException {

        raiseTicket(language, typeOfService, formatting);
        checkCRM(language, typeOfService, formatting);
    }

    public String getTicketID() throws AWTException, InterruptedException, IOException {

        beforeClass();
        testScenario(languages[0], typeOfServices[0], formattings[0]);
        afterClass();
        return  ticketID;
    }
}
