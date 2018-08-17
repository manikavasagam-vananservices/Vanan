package com.vanancrm.TestCases.DirectPayment;

import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

import java.util.concurrent.TimeUnit;

import com.vanancrm.PageObjects.MainPages.WritingServices;

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
public class WritingService extends TestBase {

    private WebDriver driver;

    private String phoneNumber = "1-888-535-5668";

    private String[] academicLevel = {"Undergraduate", "PhD"};
    private String[] paperTypes = {"Dissertation abstract", "Problem solving"};
    private String mailId = "automation.vananservices@gmail.com";
    private String service = "Writing";
    private String channel = "";
    private String pages = "180";
    private String comments = "Automation Testing";
    private String name = "Automation";
    private String country = "India";
    private String topic = "auto-test";
    private String style = "MLA";
    private String lstyle = "English(U.S.)";
    private String urgency = "3 Days";
    private String standard = "Premium Quality(Double Charge)";
    private String paperDetails = "Automation-Test";

    private static String username = "";
    private static String password = "";
    private String url = "";

    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    @Test
    public void testWritingService() throws IOException {

        System.out.println("===========================================");
        System.out.println("Scenario Started");
        System.out.println("===========================================");
        testScenario(academicLevel[0], paperTypes[1]);
        testScenario(academicLevel[1], paperTypes[0]);
        System.out.println("Test Completed");
        System.out.println("===========================================");
    }

    @BeforeClass
    public void beforeClass() {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        fullScreen(driver);
        channel = System.getProperty("channel");
    }

    @AfterClass
    public void afterClass() throws IOException {

        screenshot(driver, "WritingService");
        driver.quit();
    }

    private void raiseTicket(String academicLevel, String paperType) {

        url = System.getProperty("website");
        driver.get(url);
        WritingServices writingServices = new WritingServices(driver);
        writingServices.enterName(name);
        writingServices.enterEmail(mailId);
        writingServices.selectCountry(country);
        writingServices.enterPhoneNumber(phoneNumber);
        writingServices.selectTypeOfPaper(paperType);
        writingServices.selectAcadamicLevel(academicLevel);
        writingServices.enterTopic(topic);
        writingServices.selectStyle(style);
        writingServices.selectLanguageStyle(lstyle);
        writingServices.selectUrgency(urgency);
        writingServices.enterPages(pages);
        writingServices.selectStandard(standard);
        writingServices.enterPaperDetails(paperDetails);
        writingServices.enterComment(comments);
        writingServices.clickQuote();
        if(writingServices.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
            System.out.println("Accept button checked = > Pass");
        } else {
            System.out.println("Fail");
        }
        writingServices.clickPrivacyPolicy();
        writingServices.clickQuote();
        waitForProcessCompletion(10);
        String currentUrl = driver.getCurrentUrl();
        checkCondition(currentUrl, "success.php");
        waitForProcessCompletion(10);
    }

    private void checkCRM(String academicLevel, String paperType) {

        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, academicLevel, paperType);
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String academicLevel, String
            paperType) {
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
                    checkViewTicketInfo(academicLevel, paperType);

                    System.out.println("Ticket ID: " + ticketID);
                    changeTicketStatus();
                    checkCRMEmailConversation(academicLevel, paperType);
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
            System.out.print("\t Expected : " + first + "\n");
            System.out.print("\t Actual : " + second + "\n");
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

    private void checkCRMEmailConversation(String academicLevel, String paperType) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        evaluateCondition("Email Service",
            emailConversation.getServiceDetails(), service);
        evaluateCondition("Name",
                emailConversation.getTicketFieldValues("Name"), name);

        evaluateCondition("Phone Number",
            emailConversation.getTicketFieldValues("Phone Number"), phoneNumber);
        evaluateCondition("Type Of Paper",
                emailConversation.getTicketFieldValues("Type Of Paper"), paperType);
        evaluateCondition("Academic Level",
                emailConversation.getTicketFieldValues("Academic Level"), academicLevel);
        evaluateCondition("Topic",
                emailConversation.getTicketFieldValues("Topic"), topic);

        evaluateCondition("Style",
                emailConversation.getTicketFieldValues("Style"), style);
        evaluateCondition("Language Style",
                emailConversation.getTicketFieldValues("Language Style"), lstyle);
        evaluateCondition("Urgency",
                emailConversation.getTicketFieldValues("Urgency"), urgency);

        evaluateCondition("Standard",
                emailConversation.getTicketFieldValues("Standard"), standard);
        evaluateCondition("No Of Pages",
            emailConversation.getTicketFieldValues("No Of Pages"), pages);
        evaluateCondition("Paper Details",
                emailConversation.getTicketFieldValues("Paper Details"), paperDetails);
        evaluateCondition("Comment",
            emailConversation.getTicketFieldValues("Comment"), comments);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }

    private void checkViewTicketInfo(String academicLevel, String paperType) {

        System.out.println("===========================================");
        System.out.println("Checking View Ticket Details");
        System.out.println("===========================================\n");
        evaluateCondition("Channel", viewTicketDetails
                .getRunTimeTicketFieldValues("Channel"), channel);
        evaluateCondition("Name", viewTicketDetails
                .getRunTimeTicketFieldValues("Name"), name);
        evaluateCondition("Email", viewTicketDetails
            .getEmailId(), mailId);

        evaluateCondition("Country", viewTicketDetails
                .getRunTimeTicketFieldValues("Country"), country);
        evaluateCondition("PhoneNo", viewTicketDetails
                .getRunTimeTicketFieldValues("PhoneNo"), phoneNumber);
        evaluateCondition("Websites", url,
            viewTicketDetails.getWebsite());

        evaluateCondition("Type Of Paper", viewTicketDetails
            .getRunTimeTicketFieldValues("Type Of Paper"), paperType);
        evaluateCondition("Academic Level", viewTicketDetails
            .getRunTimeTicketFieldValues("Academic Level"), academicLevel);
        evaluateCondition("Topic", viewTicketDetails
                .getRunTimeTicketFieldValues("Topic"), topic);
        evaluateCondition("Style", viewTicketDetails
                .getRunTimeTicketFieldValues("Style"), style);
        evaluateCondition("Language Style", viewTicketDetails
                .getRunTimeTicketFieldValues("Language Style"), lstyle);

        evaluateCondition("Urgency", viewTicketDetails
                .getRunTimeTicketFieldValues("Urgency"), urgency);
        evaluateCondition("Standard", viewTicketDetails
                .getRunTimeTicketFieldValues("Standard"), standard);
        evaluateCondition("No Of Pages", viewTicketDetails
            .getRunTimeTicketFieldValues("No Of Pages"), pages);
        evaluateCondition("Paper Details", viewTicketDetails
                .getRunTimeTicketFieldValues("Paper Details"), paperDetails);
        evaluateCondition("Comment", viewTicketDetails
            .getRunTimeTicketFieldValues("Comment"), comments);
        evaluateCondition("Service", viewTicketDetails
            .getServiceValues(), service);
        System.out.println("===========================================\n");
    }

    private void testScenario(String academicLevel, String paperType)
        throws IOException {

        raiseTicket(academicLevel, paperType);
        getCRMCreadential();
        checkCRM(academicLevel, paperType);
    }

    private void checkCondition(String currentUrl, String site) {
        if (currentUrl.contains(site)) {
            System.out.println(currentUrl + " and it pass");
        } else {
            System.out.println(currentUrl + " and it fail");
        }
    }
}
