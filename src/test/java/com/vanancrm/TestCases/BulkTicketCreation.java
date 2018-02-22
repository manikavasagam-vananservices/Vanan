package com.vanancrm.TestCases;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;
import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.vanancrm.Common.TestBase;

import com.vanancrm.PageObjects.MainPages.Transcription;

public class BulkTicketCreation extends TestBase {

    private WebDriver driver;
    private Transcription transcription;
    private String[] email = {"suganya@vananservices.com", "naveen@vananservices.com",
            "soundarganesh@vananservices.com", "vijayakumar@vananservices.com",
            "rajkumar@vananservices.com", "rohini.r@vananservices.com",
            "vinothkumar@vananservices.com", "veeravel@vananservices.com",
            "kaviyarasu@vananservices.com", "muthulakshmi@vananservices.com"};
    private String[] language = {"English", "Spanish", "Tamil"};
    private String service = "Transcription";
    private String[] channel = {"Request for Quote", "Direct Payment", "Email Quote"};

    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;
    private Menus menus;
    private EmailConversation emailConversation;

    @Test
    public void transcriptionTest() throws IOException {
        Transcription transcription;
        for (int i = 0; i < email.length; i++) {
            for (int j = 0; j < language.length; j++) {
                driver.get("https://vananservices.com/Transcription-Quote.php");
                transcription = new Transcription(driver);
                transcription.enterMinutes("10");
                transcription.selectLanguageFrom(language[j]);
                transcription.enterEmailId(email[i]);
                if (j == 0) {
                    transcription.clickEmailMeGetQuote();
                } else if (j == 1) {
                    transcription.clickProceedPayment();
                } else if (j == 2) {
                    transcription.clickGetQuote();
                }
                waitForProcessCompletion(10);
                System.out.println((i + 1) + ". Email : " + email[i] + " " +
                        "Completed " +
                        language[j]);
            }
        }
        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn("developer",
                "devcrm2@rvr");
        menus = dashBoardPage.clickAllProcess();
        viewTicketDetails = new ViewTicketDetails(driver);
        for (int i = 0; i < email.length; i++) {
            for (int j = 0; j < channel.length; j++) {
                getTicketId(menus, channel[j], email[i]);
                changeTicketStatus(email[i], channel[j]);
            }
        }
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }


    private void getTicketId(Menus menus, String channel, String mailId) {
        String ticketID = "";
        readTableData = menus.clickNewMenu();
        List<String> tickets = readTableData.readTableRows();
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).contains(service)) {
                ticketID = tickets.get(i).substring(tickets.get(i).indexOf("VS"),
                        tickets.get(i).indexOf(service) - 1);
                viewTicketDetails = readTableData.clickService(service,
                        i + 1);
                waitForProcessCompletion(10);
                if (viewTicketDetails.getRunTimeTicketFieldValues("Email")
                        .contains(mailId)) {
                    if (viewTicketDetails.getRunTimeTicketFieldValues("Channel")
                            .contains(channel)) {
                        System.out.println((i + 1) + " : Channel = " +
                                viewTicketDetails.getRunTimeTicketFieldValues(
                                        "Channel \tEmail id: " + mailId));
                        break;
                    }
                }
            }
        }
    }

    private void changeTicketStatus(String email, String channel) {

        // Edit a ticket and moved the status into Others
        Edit edit = menus.clickEdit();
        edit.selectStatus("Others");
        edit.clickUpdateButton();
        waitForProcessCompletion(10);
        emailConversation = menus.clickEmailConversation();
        emailConversation.clickReadMore();
        System.out.println(email + " " + channel + " Changed to No action");
        emailConversation.clickNoActionButton();
        menus.clickView();
    }


    @BeforeClass
    public void beforeClass() throws IOException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @AfterClass
    public void afterClass() throws IOException {

        screenshot(driver, "final1");
        //driver.quit();
    }

}
