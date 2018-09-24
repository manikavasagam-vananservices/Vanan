package com.vanancrm.TestCases.CustomerDashboard;

import com.vanan.CustomerDashboard.PageObjects.MainPages.DashBoard;
import com.vanan.CustomerDashboard.PageObjects.WholeSitePages.LoginPage;
import com.vanancrm.Common.TestBase;
import com.vanancrm.PageObjects.MainPages.FreeTrailPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class TranscriptionCD extends TestBase {


    public String ticketID = "";
    private String language = "English";
    private String service = "Transcription";
    private String mailId = "automation.vananservices@gmail.com";
    private String comments = "Automation Testing";
    private String minute = "10";
    private static String password;
    private String fileName = service;
    private String fileExtenstion = ".mp3";
    private double bPrice = 2.00;
    private boolean service1, service2;

    private DashBoard dashBoard;
    private WebDriver driver;

    /*public TranscriptionCD(WebDriver webDriver) {

        driver = webDriver;
    }*/

    @BeforeClass
    public void beforeClass() throws IOException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1900,1200");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        fullScreen(driver);
    }

    @Test
    public void transcriptionServices() throws AWTException,
            InterruptedException, IOException {
        service1 = false;
        service2 = true;
        /*System.out.println("======================================");
        System.out.println("Scenario Started for Transcription");
        System.out.println("======================================");*/
        raiseTicket(language);
        CDCRMflowChecking cdcrMflowChecking = new CDCRMflowChecking(driver);
        cdcrMflowChecking.checkCRM(language, service, minute, fileExtenstion,
                bPrice);
    }

    private static void getCRMCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        password = properties.getProperty("PASSWORD");
    }

    private void raiseTicket(String language) throws AWTException,
            InterruptedException, IOException {

        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        fullScreen(driver);
        driver.get("https://vananservices.com/customer/");
        getCRMCreadential();
        LoginPage loginPage = new LoginPage(driver);
        dashBoard = loginPage.signIn(mailId,
                password);
        dashBoard.clickPopUpCloseButton();
        waitForProcessCompletion(10);
        dashBoard.clickTranscriptionMenu();
        FreeTrailPage freeTrailPage = new FreeTrailPage(driver);
        freeTrailPage.selectSourceLanguage(language);
        freeTrailPage.uploadFile(driver, fileName, fileExtenstion);
        freeTrailPage.clickUSNativeTranscribers();
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

    public void checkCustomerDashboardStatus(WebDriver driver, String status,
            boolean orderDeliver) {

        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Checking customer dashbard status");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
        driver.get("https://vananservices.com/customer/index.php");
        LoginPage loginPage = new LoginPage(driver);
        DashBoard dashBoard = loginPage.signIn(mailId, password);
        dashBoard.clickPopUpCloseButton();
        evaluateCondition("Ticket Id", dashBoard.getTicketNumber(),
                ticketID);
        evaluateCondition("Service", dashBoard.getServiceName(),
                service);
        System.out.println("Created Date" + dashBoard.getCreatedDate());
        evaluateCondition("Ticket Id : ", dashBoard.getTicketNumber(),
                ticketID);
        evaluateCondition("Status", dashBoard.getStatus(),
                status);
        dashBoard.clickParticularTicket(ticketID);
        if(orderDeliver) {
            evaluateCondition("File Name", dashBoard
                    .getDeliveredFileName(), fileName);
            //dashBoard.clickDownloadDeliveredFile();
            System.out.println("Delivered Date and Time : " + dashBoard
                    .getDeliveredFileDate());
            /*evaluateCondition("Order Number from Heading", dashBoard
                    .getOrderNumber(), ticketID);
            evaluateCondition("Order Number from Heading", dashBoard
                    .getOrderNumber(), ticketID);*/

        }
        dashBoard.clickOrderInfo();
        evaluateCondition("Order Number from Heading", dashBoard
                .getOrderNumber(), ticketID);
        evaluateCondition("Order Number", dashBoard
                .getOrderInfoTableValues("Order Number"), ticketID);
        evaluateCondition("Service", dashBoard
                .getOrderInfoTableValues("Service"), service);
        String message = "";
        if(service1) {
            message = "no";
        } else {
            message = "yes";
        }
        evaluateCondition("Verbatim", dashBoard
                .getOrderInfoTableValues("Verbatim"), message);
        evaluateCondition("TimeCoding", dashBoard
                .getOrderInfoTableValues("TimeCoding"), message);

        if(service2) {
            message = "no";
        } else {
            message = "yes";
        }
        evaluateCondition("Notarization", dashBoard
                .getOrderInfoTableValues("Notarization"), message);
        evaluateCondition("Mail", dashBoard
                .getOrderInfoTableValues("Mail"), message);
        dashBoard.clickUploadedFileDetails();
        evaluateCondition("File Name", dashBoard
                .getUploadFileName(), fileName);
        dashBoard.clickclosePopupWindow();
        waitForProcessCompletion(10);
        dashBoard.clickLogOut();
    }

    private void evaluateCondition(String message, String first,
                                   String second) {

        System.out.println("\t\n"+message + " : " + second);
        if (first.contains(second)) {

            System.out.println("\t\n"+message + " is correct");
        } else {

            System.out.println("\n"+message + " is wrong");
            System.out.print("\t \nExpected : " + first + "\n");
            System.out.print("\t \nActual : " + second + "\n");
        }
    }
}
