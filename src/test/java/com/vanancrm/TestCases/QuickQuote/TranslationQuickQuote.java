package com.vanancrm.TestCases.QuickQuote;

import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.util.concurrent.TimeUnit;

import com.vanancrm.PageObjects.MainPages.Translation;
import org.openqa.selenium.Cookie;
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

import com.vanancrm.PageObjects.MainPages.QuickQuote;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class TranslationQuickQuote extends TestBase {

    private WebDriver driver;

    private String mp = "10";

    private String[] sourcelanguages = {"English", "Apache"};
    private String[] targetlanguages = {"Spanish", "Afar"};
    private String[] fileTypes = {"Document", "Audio/Video"};
    private String mailId = "automation.vananservices@gmail.com";
    private String service = "Translation";
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

        System.out.println("======================================");
        System.out.println("Scenario Started");
        System.out.println("======================================");
        testScenario(sourcelanguages[0], targetlanguages[0], fileTypes[1]);
        testScenario(sourcelanguages[1], targetlanguages[1], fileTypes[0]);
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

        screenshot(driver, "TranslationQuickQuote");
        driver.quit();
    }

    private void raiseTicket(String slanguage, String tlanguage, String
        fileType) {

        url = System.getProperty("website");
        driver.get(url);
        QuickQuote quickQuote = new QuickQuote(driver);
        System.out.print("Mail id : " + mailId + ",\t");
        quickQuote.enterEmail(mailId);
        System.out.print("File Type : " + fileType + ",\t");
        quickQuote.selectFileType(fileType);
        System.out.print("Language : " + slanguage + ",\t");
        quickQuote.selectSourceLanguage(slanguage);
        System.out.print("Target Language : " + tlanguage);
        quickQuote.selectLanguageTo(tlanguage);

        if(fileType.equals(fileTypes[0])) {

            quickQuote.enterPages(mp);
        } else {

            quickQuote.enterMinutes(mp);

        }
        quickQuote.clickGetQuote();
        screenshot(driver, url.substring(url.indexOf("//")+2,url.indexOf(".")));
        if(quickQuote.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
            System.out.println("Accept button is pressed => Pass");
        } else {
            System.out.println("Accept button is not pressed => Fail");
        }
        quickQuote.clickPrivacyPolicy();
        quickQuote.clickGetQuote();
        waitForProcessCompletion(30);

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("Translation-Quote.php")) {
            System.out.println("\nAfter Quick quote form submit page is " +
                    "navigate into " + currentUrl + " and it pass");
        } else {
            System.out.println("\nAfter Quick quote form submit page is " +
                    "navigate into " + currentUrl + " and it fail");
        }
    }

    private void checkCRM(String slanguage, String tlanguage, String
        fileType) {

        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, slanguage, tlanguage, fileType);
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String slanguage, String tlanguage,
        String typeOfService) {

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
                System.out.println("Channel " + viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel"));
                if (viewTicketDetails.getRunTimeTicketFieldValues("Email")
                        .contains(mailId) && viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel")
                        .contains(channel) && url.contains(viewTicketDetails
                        .getRunTimeTicketFieldValues("Websites"))) {

                    ticketID = tickets.get(i).substring(tickets.get(i).indexOf("VS"),
                            tickets.get(i).indexOf(service) - 1);
                    System.out.println((i + 1) + " : Channel = " +
                            viewTicketDetails.getRunTimeTicketFieldValues(
                                    "Channel"));
                    checkViewTicketInfo(slanguage, tlanguage, typeOfService);

                    System.out.println("Ticket ID: " + ticketID);
                    changeTicketStatus();
                    checkCRMEmailConversation(slanguage, tlanguage,
                            typeOfService);
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
        emailConversation.clickReadMore();
    }

    private void checkCRMEmailConversation(String slanguage, String tlanguage,
            String fileType) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        evaluateCondition("Email Service",
            emailConversation.getServiceDetails(), service);
        if(fileType.equals(fileTypes[0])) {

            evaluateCondition("Pages", emailConversation
                    .getTicketFieldValues("Pages"), mp);
        } else {

            evaluateCondition("Minutes",
                emailConversation.getTicketFieldValues("Minutes"), mp);
        }

        evaluateCondition("Translate From",
            emailConversation.getTicketFieldValues("Translate From"), slanguage);
        evaluateCondition("Translate To",
            emailConversation.getTicketFieldValues("Translate To"), tlanguage);
        evaluateCondition("File Type",
            emailConversation.getTicketFieldValues("File Type"), fileType);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }

    private void checkViewTicketInfo(String slanguage, String tlanguage, String
        fileType) {

        System.out.println("\n===========================================");
        System.out.println("Checking View Ticket Details");
        System.out.println("===========================================\n");

        evaluateCondition("Email", viewTicketDetails
            .getRunTimeTicketFieldValues("Email"), mailId);
        evaluateCondition("Websites", url,
            viewTicketDetails.getRunTimeTicketFieldValues("Websites"));
        evaluateCondition("Channel", viewTicketDetails
            .getRunTimeTicketFieldValues("Channel"), channel);

        evaluateCondition("Translate From", viewTicketDetails
            .getRunTimeTicketFieldValues("Translate From"), slanguage);
        evaluateCondition("TranslateTo", viewTicketDetails
            .getRunTimeTicketFieldValues("TranslateTo"), tlanguage);

        if (fileType.equals(fileTypes[0])) {

            evaluateCondition("No Of Pages", viewTicketDetails
                .getRunTimeTicketFieldValues("No Of Pages"), mp);
        } else {

            evaluateCondition("Minutes", viewTicketDetails
                .getRunTimeTicketFieldValues("Minutes"), mp);
        }
        evaluateCondition("Audio/video", viewTicketDetails
            .getRunTimeTicketFieldValues("Audio/video"), fileType);
        evaluateCondition("Service", viewTicketDetails
            .getServiceValues(), service);

        System.out.println("===========================================\n");
    }

    private void testScenario(String slanguage, String tlanguage, String
        fileType) throws
        IOException {

        raiseTicket(slanguage, tlanguage, fileType);
        getCRMCreadential();
        checkCRM(slanguage, tlanguage, fileType);
    }
}
