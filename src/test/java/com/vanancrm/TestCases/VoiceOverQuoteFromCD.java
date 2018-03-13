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

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;

import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;

import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;

import com.vanan.CustomerDashboard.PageObjects.MainPages.DashBoard;
import com.vanan.CustomerDashboard.PageObjects.WholeSitePages.LoginPage;

import com.vanancrm.PageObjects.MainPages.Voiceover;

import com.vanancrm.Common.TestBase;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class VoiceOverQuoteFromCD extends TestBase {

    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;
    private DashBoard dashBoard;


    private String mailId = "automation.vananservices@gmail.com";
    private String comments = "Automation Testing";
    private String service = "Voice Over";
    private String scriptComment = "auto-test";
    private String status = "Yes";
    private String fileName = "";
    private String fileExtension = ".txt";
    private String numberOfVoice = "2";
    private String url = "vananservices.com";

    private String[] ages = {"26-35", "15-18"};
    private String[] genders = {"Male", "Female"};
    private String[] purposes = {"Audio Books",
            "Movie Promos"};
    private String[] serviceFreqs = {"Monthly", "Not sure"};
    private String[] sourceLang = {"English", "Spanish"};
    private String[] targetLang = {"Tamil", "Arabic"};

    public String ticketID = "";
    private String channel = "";


    @Test
    public void voiceoverService() throws IOException, InterruptedException, AWTException {

        System.out.println("\n======================================");
        System.out.println("\nScenario Started");
        System.out.println("\n======================================");
        getCRMCreadential();
        driver.get("https://vananservices.com/customer");
        LoginPage loginPage = new LoginPage(driver);
        dashBoard = loginPage.signIn(mailId,
                password);
        dashBoard.clickPopUpCloseButton();
        waitForProcessCompletion(20);
        dashBoard.clickVoiceOverMenu();
        testScenario(sourceLang[0], targetLang[1], purposes[0], serviceFreqs[1]);
        System.out.println(
                "=============================================================================");
        System.out.println("Test Completed");
        System.out.println(
                "=============================================================================");
    }

    @BeforeClass
    public void beforeClass() throws IOException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("--no-sandbox");
        //driver = new ChromeDriver();
        // chromeOptions.addArguments("--headless");
        //fullScreen(driver);
        chromeOptions.addArguments("window-size=1900,1200");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        fileName = service.replace(" ", "");
        channel = "Customer Dashboard";
    }


    @AfterClass
    public void afterClass() {
        screenshot(driver,"VoiceOver Customer DashBoard page");
        driver.quit();
    }

    private void raiseTicket(String srcLang, String targetLang, String purpose,
            String serviceFreq) throws AWTException, InterruptedException,
            IOException {

        Voiceover voiceover = new Voiceover(driver);
        voiceover.selectLanguageFrom(srcLang);
        voiceover.selectNeedTranslation(status);
        voiceover.selectTargetLanguage(targetLang);
        voiceover.selectScript(status);
        voiceover.enterScriptComments(scriptComment);
        voiceover.uploadFile(driver, fileName, fileExtension);
        voiceover.selectPurposeOfVoiceOver(purpose);
        voiceover.selectNumberOfVoice(numberOfVoice);
        waitForProcessCompletion(10);
        voiceover.selectFirstGender(genders[0]);
        voiceover.selectFirstAge(ages[0]);
        voiceover.selectSecondGender(genders[1]);
        voiceover.selectSecondAge(ages[1]);
        voiceover.selectServiceFrequency(serviceFreq);
        voiceover.enterComment(comments);
        voiceover.clickQuote();
        waitForProcessCompletion(10);
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

    private void testScenario(String srcLang, String targetLang, String
            purpose, String serviceFreq)
            throws IOException, InterruptedException, AWTException {

        raiseTicket(srcLang, targetLang, purpose, serviceFreq);
        checkCRM(srcLang, targetLang, purpose, serviceFreq);
    }

    private static void getCRMCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }

    private void checkCRM(String srcLang, String targetLang, String purpose,
                          String serviceFreq) {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, srcLang, targetLang, purpose, serviceFreq);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String srcLang, String targetLang,
                              String purpose, String serviceFreq) {

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
                    checkViewTicketInfo(srcLang, targetLang, purpose, serviceFreq);

                    System.out.println("Ticket ID: " + ticketID);
                    changeTicketStatus();
                    checkCRMEmailConversation(srcLang, targetLang, purpose,
                            serviceFreq);
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
            System.out.print("\t Expected : " + first + "\n");
            System.out.print("\t Actual : " + second + "\n");
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

    private void checkCRMEmailConversation(String srcLang, String targetLang,
                                           String purpose, String serviceFreq) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        evaluateCondition("Email Service",
                emailConversation.getServiceDetails(), service);
        evaluateCondition("Target Language",
                emailConversation.getTicketFieldValues("Target Language"), srcLang);
        evaluateCondition("Need Translation",
                emailConversation.getTicketFieldValues("Need Translation"), status);
        evaluateCondition("Source Language",
                emailConversation.getTicketFieldValues("Source Language"), targetLang);

        evaluateCondition("Script",
                emailConversation.getTicketFieldValues("Script"), status);
        evaluateCondition("Files", emailConversation
                .getTicketFieldValues("Files"), fileName + fileExtension);
        evaluateCondition("Files Link", emailConversation
                .getTicketFieldValues("Files Link"), fileName
                + fileExtension);

        evaluateCondition("Script Comment", emailConversation
                .getTicketFieldValues("Script Comment :"), scriptComment);
        evaluateCondition("Purpose Of Voice Over ", emailConversation
                .getTicketFieldValues("Purpose Of Voice Over"), purpose);
        evaluateCondition("No Of Voices", emailConversation
                .getTicketFieldValues("No Of Voices"), numberOfVoice);
        evaluateCondition("Gender And Age", emailConversation
                .getTicketFieldValues("Gender And Age"),genders[0]
                + ":" + ages[0] + "," + genders[1] + ":" + ages[1]);
        /*System.out.println("Turnaround Time : " + emailConversation
                .getTicketFieldValues("Turnaround Time"));
        evaluateCondition("Comment",
                emailConversation.getTicketFieldValues("Comment"), comments);*/
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
    }

    private void checkViewTicketInfo(String srcLang, String targetLang,
                                     String purpose, String serviceFreq) {

        System.out.println("===========================================");
        System.out.println("Checking View Ticket Details");
        System.out.println("===========================================\n");
        evaluateCondition("Channel", viewTicketDetails
                .getRunTimeTicketFieldValues("Channel"), channel);
        evaluateCondition("Email", viewTicketDetails
                .getRunTimeTicketFieldValues("Email"), mailId);
        evaluateCondition("Websites", url,
                viewTicketDetails.getRunTimeTicketFieldValues("Websites"));

        evaluateCondition("Language", viewTicketDetails
                .getRunTimeTicketFieldValues("Language"), srcLang);
        evaluateCondition("Need Translation", viewTicketDetails
                .getRunTimeTicketFieldValues("Need Translation"), status);
        evaluateCondition("Translate From", viewTicketDetails
                .getRunTimeTicketFieldValues("Translate From"), targetLang);
        evaluateCondition("Need Script", viewTicketDetails
                .getRunTimeTicketFieldValues("Need Script"), status);
        evaluateCondition("Script_comment", viewTicketDetails
                .getRunTimeTicketFieldValues("Script_comment"), scriptComment);

        evaluateCondition("Purpose Voiceover", viewTicketDetails
                .getRunTimeTicketFieldValues("Purpose Voiceover"), purpose);
        evaluateCondition("Voices", viewTicketDetails
                .getRunTimeTicketFieldValues("Voices"), numberOfVoice);
        evaluateCondition("Voice Details", viewTicketDetails
                .getVoiceValues(), genders[0]
                + ":" + ages[0] + "," + genders[1] + ":" + ages[1]);
        evaluateCondition("Comment", viewTicketDetails
                .getRunTimeTicketFieldValues("Comment"), comments);
        evaluateCondition("Service", viewTicketDetails
                .getServiceValues(), service);
        /*System.out.println("ETAT : " + viewTicketDetails
                .getRunTimeTicketFieldValues("ETAT"));*/
        System.out.println("===========================================\n");
    }

    public String getTicketID() throws AWTException, InterruptedException, IOException {

        beforeClass();
        voiceoverService();
        afterClass();
        return  ticketID;
    }
}
