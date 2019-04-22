package com.vanancrm.TestCases.QuickQuote;

import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;

import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;

import com.vanancrm.Common.TestBase;

import com.vanancrm.PageObjects.MainPages.QuickQuote;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class TranscriptionQuickQuote extends TestBase {

    private WebDriver driver;

    private String minute = "180";

    private String[] languages = {"English", "Apache"};
    private String[] categorys = {"General", "Legal"};
    private String mailId = "automation@vananservices.com";
    private String service = "Transcription";
    private String channel = "Quick Quote";

    private static String username = "";
    private static String password = "";
    private String url = "";

    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    @Test
    public void transcriptionServices() throws IOException {

        System.out.println("\n======================================");
        System.out.println("\nScenario Started");
        System.out.println("\n======================================");

        testScenario(languages[0], categorys[1]);
        testScenario(languages[1], categorys[0]);
        System.out.println("Test Completed");

        System.out.println("======================================");
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

        screenshot(driver, "TranscriptionQuickQuote");
        driver.quit();
    }

    private void raiseTicket(String language, String category) {

        url = System.getProperty("website");
        driver.get(url);

        QuickQuote quickQuote = new QuickQuote(driver);
        quickQuote.enterEmail(mailId);
        quickQuote.enterMinutes(minute);
        quickQuote.selectLanguageFrom(language);
        quickQuote.category(category);
        System.out.println("\n======================================\n\t \t \t"
                + "Testing Input Data\n\nMail id : " +
                mailId + ",\nMinute : " + minute
                + ",\nLanguage : " + language + ",\nCategory : " +
                category + "\n======================================\n");
        quickQuote.clickGetQuote();
        screenshot(driver, url.substring(url.indexOf("//")+2,url.indexOf(".")));
        if(quickQuote.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
            System.out.println("Accept button is pressed => Pass");
        } else {
            System.out.println("Accept button is not pressed => Fail");
        }waitForProcessCompletion(5);
        quickQuote.clickPrivacyPolicy();
        waitForProcessCompletion(5);
        quickQuote.clickGetQuote();
        waitForProcessCompletion(15);
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("Transcription-Quote.php")) {
            System.out.println("After Quick quote form submit page is " +
                    "navigate into " + currentUrl + " and it pass");
        } else {
            System.out.println("After Quick quote form submit page is " +
                    "navigate into " + currentUrl + " and it fail");
        }
    }

    private void checkCRM(String language, String typeOfService) {

        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, language, typeOfService);
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String language, String
            typeOfService) {
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
                    checkViewTicketInfo(language, typeOfService);

                    System.out.println("Ticket ID: " + ticketID);
                    changeTicketStatus();
                    checkCRMEmailConversation(language, typeOfService);
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
  //      edit.selectStatus("Others");
        edit.clickUpdateButton();
        waitForProcessCompletion(10);
        emailConversation = menus.clickEmailConversation();
        emailConversation.clickReadMore("Quick Quote");
    }

    private void checkCRMEmailConversation(String language, String typeOfService) {

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
        evaluateCondition("Service",
                emailConversation.getTicketFieldValues("Service"), typeOfService);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }

    private void checkViewTicketInfo(String language, String typeOfService) {

        System.out.println("===========================================");
        System.out.println("Checking View Ticket Details");
        System.out.println("===========================================\n");
        evaluateCondition("Email", viewTicketDetails
            .getEmailId(), mailId);
        evaluateCondition("Websites", url,
            viewTicketDetails.getWebsite());
        evaluateCondition("Channel", viewTicketDetails
            .getRunTimeTicketFieldValues("Channel"), channel);
        evaluateCondition("Language", viewTicketDetails
            .getRunTimeTicketFieldValues("Language"), language);
        evaluateCondition("Minutes", viewTicketDetails
            .getRunTimeTicketFieldValues("Minutes"), minute);
        evaluateCondition("Type Of Service", viewTicketDetails
            .getRunTimeTicketFieldValues("Type Of Service"), typeOfService);
        evaluateCondition("Service", viewTicketDetails
                .getServiceValues(), service);
        System.out.println("===========================================\n");
    }

    private void testScenario(String language, String category) throws
        IOException {

        raiseTicket(language, category);
        getCRMCreadential();
        checkCRM(language, category);
    }
}
