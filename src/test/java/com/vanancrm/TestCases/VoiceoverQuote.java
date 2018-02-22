package com.vanancrm.TestCases;

import java.awt.AWTException;

import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

import java.util.concurrent.TimeUnit;


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

import com.vanancrm.PageObjects.MainPages.Voiceover;

import com.vanancrm.Common.TestBase;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class VoiceoverQuote extends TestBase {


    private static String username = "";
    private static String password = "";

    private WebDriver driver;

    private String[] ages = {"26-35", "15-18"};
    private String[] genders = {"Male", "Female"};
    private String[] purposes = {"Audio Books",
            "Movie Promos"};
    private String[] serviceFreqs = {"Monthly", "Not sure"};
    private String[] sourceLang = {"English", "Spanish"};
    private String[] targetLang = {"Tamil", "Arabic"};

    private String channel = "";

    private String comments = "Automation Testing";
    private String phoneNumber = "1-888-535-5668";
    private String mailId = "automation.vananservices@gmail.com";
    private String service = "Voice Over";
    private String name = "Automation";
    private String country = "India";
    private String scriptComment = "auto-test";
    private String status = "Yes";
    private String fileName = "voice";
    private String fileExtension = ".txt";
    private String numberOfVoice = "2";
    private String url = "";

    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    private static void getCRMCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }

    @Test
    public void voiceoverService() throws IOException, AWTException,
            InterruptedException {

        System.out.println("===========================================");
        System.out.println("Scenario Started");
        System.out.println("===========================================");
        testScenario(sourceLang[0], targetLang[1], purposes[0], serviceFreqs[1]);
        testScenario(sourceLang[1], targetLang[0], purposes[1], serviceFreqs[0]);
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

        screenshot(driver, "VoiceOverService");
        driver.quit();
    }

    private void raiseTicket(String srcLang, String targetLang, String purpose,
            String serviceFreq) throws AWTException, InterruptedException,
            IOException {

        url = System.getProperty("website");
        driver.get(url);

        Voiceover voiceover = new Voiceover(driver);
        voiceover.enterName(name);
        voiceover.enterEmail(mailId);
        voiceover.selectCountry(country);
        voiceover.enterPhoneNumber(phoneNumber);
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
        checkCondition(currentUrl, "success.php");
        waitForProcessCompletion(10);
    }

    private void checkCondition(String currentUrl, String site) {
        if (currentUrl.contains(site)) {
            System.out.println(currentUrl + " and it pass");
        } else {
            System.out.println(currentUrl + " and it fail");
        }
    }

    private void checkCRM(String srcLang, String targetLang, String purpose,
            String serviceFreq) {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, srcLang, targetLang, purpose, serviceFreq);
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String srcLang, String targetLang,
            String purpose, String serviceFreq) {

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
        waitForProcessCompletion(10);
        emailConversation = menus.clickEmailConversation();
        emailConversation.clickReadMore();
    }

    private void checkCRMEmailConversation(String srcLang, String targetLang,
            String purpose, String serviceFreq) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        evaluateCondition("Email Service",
                emailConversation.getServiceDetails(), service);
        evaluateCondition("Name",
                emailConversation.getTicketFieldValues("Name"), name);

        evaluateCondition("Phone Number",
                emailConversation.getTicketFieldValues("Phone Number"), phoneNumber);
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
        evaluateCondition("Name", viewTicketDetails
                .getRunTimeTicketFieldValues("Name"), name);
        evaluateCondition("Email", viewTicketDetails
                .getRunTimeTicketFieldValues("Email"), mailId);

        evaluateCondition("Country", viewTicketDetails
                .getRunTimeTicketFieldValues("Country"), country);
        evaluateCondition("PhoneNo", viewTicketDetails
                .getRunTimeTicketFieldValues("PhoneNo"), phoneNumber);
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

    private void testScenario(String srcLang, String targetLang, String
            purpose, String serviceFreq)
            throws IOException, InterruptedException, AWTException {

        raiseTicket(srcLang, targetLang, purpose, serviceFreq);
        getCRMCreadential();
        checkCRM(srcLang, targetLang, purpose, serviceFreq);
    }
}
