package com.vanancrm.TestCases.CustomerDashboard;

import java.awt.AWTException;

import java.io.FileReader;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vanan.CRM.PageObjects.MainPages.AllocatorDashboard;
import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Delivery;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;
import com.vanan.CRM.PageObjects.MainPages.VendorAllocation;
import com.vanan.CRM.PageObjects.MainPages.PrivateNote;

import com.vanan.CRM.PageObjects.WholeSitePages.FileInfo;
import com.vanan.CRM.PageObjects.WholeSitePages.FileUploadFromTicket;
import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.PaymentMadePrivateNoteAlert;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.QuoteInfo;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;

import com.vanan.CustomerDashboard.PageObjects.MainPages.DashBoard;
import com.vanan.CustomerDashboard.PageObjects.WholeSitePages.LoginPage;

import com.vanancrm.Common.TestBase;


/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class CheckCustomerDashboardTicket extends TestBase {

    private static String username = "";
    private static String password = "";
    private static String salesPersonName = "";
    private static String allocatorPersonName = "";
    private static String vendorPersonName = "";
    private static String salesPersonPwd = "";
    private static String allocatorPersonPwd = "";

    private WebDriver driver;
    private String ticketID = "";
    private String fileName = "";
    private String comment = "Automation Testing";
    private String fileExtenstion = ".txt";
    private String service = "";
    private String channel = "Customer Dashboard";
    private String mailId = "automation.vananservices@gmail.com";
    private String minute = "20";
    private String language = "English";
    private String serviceFreq = "Not sure";
    private boolean service1, service2;

    private double bPrice = 2.00;
    private String[] ticketStatus = {"In Progress", "Delivered"};

    private AllocatorDashboard allocatorDashboard;
    private ChromeOptions chromeOptions;
    private DashBoardPage dashBoardPage;
    private Edit edit;
    private EmailConversation emailConversation;
    private Login login;
    private Menus menus;
    private QuoteInfo quoteInfo;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    private static void getCRMCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
        salesPersonName = properties.getProperty("SALESNAME");
        salesPersonPwd = properties.getProperty("SALESPASSWORD");
        allocatorPersonName = properties.getProperty("ALLOCATORNAME");
        allocatorPersonPwd = properties.getProperty("ALLOCATORPASSWORD");
        vendorPersonName = properties.getProperty("VENDORNAME");
    }

    @Test
    public void checkTrackOrder() throws IOException, InterruptedException, AWTException {

        driver.get("https://secure-dt.com/crm/user/login");
        login = new Login(driver);
        dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        readTableData = menus.clickNewMenu();
        editTicketDetails(driver);
        edit.selectStatus("New");
        edit.clickUpdateButton();
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t File details are added in file info");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        menus.clickFileInfo();
        FileInfo fileInfo = new FileInfo(driver);
        fileInfo.setFileDetails(fileName,
                "Document", "" + minute, "Good", "Accent", "> 5", comment);
        fileInfo.clickUpdateFileInfo();
        changePrivateModeStatus();
        changePaymentMadeStatus();
        checkCustomerDashboardStatus(ticketStatus[0], false);

        driver.get("https://secure-dt.com/crm/user/login");
        login = new Login(driver);
        dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        readTableData = menus.clickNewMenu();
        editTicketDetails(driver);
        quoteInfo = menus.clickQuoteInfo();
        quoteInfo.clickMoveToLocation();
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t File is moved to allocation");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        VendorAllocation vendorAllocation = menus
                .clickVendorAllocation();
        vendorAllocation.allocateFileIntoVendor
                (fileName, minute, false, "1",
                        getETAT(), comment, "Good", "Legal", vendorPersonName);
        vendorAllocation.clickAllocateFile();
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t File is allocated to vendor");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        driver.navigate().to(driver.getCurrentUrl());
        menus = new Menus(driver);
        allocatorDashboard = menus.clickAllocatorDashboard();
        allocatorDashboard.selectFile(fileName, username,
                vendorPersonName);
        allocatorDashboard.selectStatus("Completed");
        allocatorDashboard.clickChangeStatus();
        
        driver.navigate().to(driver.getCurrentUrl());
        dashBoardPage = new DashBoardPage(driver);
        menus = dashBoardPage.clickAllProcess();
        readTableData = menus.clickNewMenu();
        editTicketDetails(driver);
        emailConversation = menus.clickEmailConversation();
        emailConversation.clickReadMore("File Allocation");
        String parentWindow = driver.getWindowHandle();
        emailConversation.clickFileUpload();
        Set<String> handles =  driver.getWindowHandles();
        for(String windowHandle  : handles)
        {
            if(!windowHandle.equals(parentWindow))
            {
                driver.switchTo().window(windowHandle);
                FileUploadFromTicket fileUploadFromTicket = new
                        FileUploadFromTicket(driver);
                fileUploadFromTicket.uploadFile(driver, fileName, "");
                fileUploadFromTicket.selectSuject("Police Certificate");
                fileUploadFromTicket.enterComments(comment);
                fileUploadFromTicket.clickSubmitButton();
                waitForProcessCompletion(10);
                driver.close();
                driver.switchTo().window(parentWindow);
                break;
            }
        }

        driver.navigate().to(driver.getCurrentUrl());
        dashBoardPage = new DashBoardPage(driver);
        menus = dashBoardPage.clickAllProcess();
        readTableData = menus.clickNewMenu();
        editTicketDetails(driver);
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Deliver the files");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        Delivery delivery = menus.clickDelivery();
        delivery.selectDeliveryFileToCustomer(fileName,
                vendorPersonName);
        delivery.clickDeliverFilesToCustomerButton();
        delivery.clickSendEmailToCustomerButton();
        menus.clickEdit();
        edit.selectStatus("Order Delivered");
        edit.clickUpdateButton();
        menus.clickSignOut();

        checkCustomerDashboardStatus(ticketStatus[1], true);
        driver.get("https://secure-dt.com/crm/user/login");
        login = new Login(driver);
        dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        readTableData = menus.clickNewMenu();
        editTicketDetails(driver);
        edit.selectStatus("Others");
        edit.clickUpdateButton();
        
        driver.navigate().to(driver.getCurrentUrl());
        menus = new Menus(driver);
        allocatorDashboard = menus.clickAllocatorDashboard();
        menus.clickCompleted();
        allocatorDashboard.clickFileInCompletedSection(fileName,
                username, vendorPersonName);
        allocatorDashboard.clickEditMenu();
        waitForProcessCompletion(10);
        allocatorDashboard.selectPopupStatus("Rejected");
        allocatorDashboard.clickEditSummarySubmit();
        allocatorDashboard.clickEmailConversationMenu();
        allocatorDashboard.clickReadMore();
        allocatorDashboard.clickNoActionButton();
        System.out.println(
                "=============================================================================");
        System.out.println("Test Completed");
        System.out.println(
                "=============================================================================");
    }

    @BeforeClass
    public void beforeClass() throws IOException, InterruptedException, AWTException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1900,1200");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        fullScreen(driver);
        getCRMCreadential();

        if(System.getProperty("service").equals("Transcription")) {

            TranscriptionQuoteFromCD transcriptionQuoteFromCD = new
                    TranscriptionQuoteFromCD();
            ticketID = transcriptionQuoteFromCD.getTicketID();
            service = "Transcription";
            service1 = false;
            service2 = true;
        } else if (System.getProperty("service").equals("Typing")) {

            TypingQuoteFromCD typingQuoteFromCD = new TypingQuoteFromCD();
            ticketID = typingQuoteFromCD.getTicketID();
            service = "Typing";
            service1 = true;
            service2 = true;
        } else if (System.getProperty("service").equals("Translation")) {

            TranslationQuoteFromCD translationQuoteFromCD = new
                    TranslationQuoteFromCD();
            ticketID = translationQuoteFromCD.getTicketID();
            service = "Translation";
            service1 = true;
            service2 = false;
        } else if (System.getProperty("service").equals("VoiceOver")) {

            VoiceOverQuoteFromCD voiceOverQuoteFromCD = new VoiceOverQuoteFromCD();
            ticketID = voiceOverQuoteFromCD.getTicketID();
            service = "Voice Over";
            service1=true;
            service2=true;
        } else if (System.getProperty("service").equals("Captioning")) {

            CaptioningQuoteFromCD captioningQuoteFromCD = new CaptioningQuoteFromCD();
            ticketID = captioningQuoteFromCD.getTicketID();
            service = "Captioning";
            service1=true;
            service2=true;
        }
        fileName = service.replace(" ", "") + fileExtenstion;
    }

    @AfterClass
    public void afterClass() {

        screenshot(driver, "CheckCustomerDashboardTicket");
        driver.quit();
    }

    private String getETAT() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, 2);
        return now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1)
                + "-" + now.get(Calendar.DATE);
    }

    private void editTicketDetails(WebDriver driver) {
        menus = new Menus(driver);
        menus.searchCustomerDetails(ticketID);
        viewTicketDetails = new ViewTicketDetails(driver);
        viewTicketDetails = readTableData.clickOldTableService(service,
                (1));
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Edit particular ticket");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("Channel " + viewTicketDetails
                .getRunTimeTicketFieldValues("Channel"));
        if (viewTicketDetails.getRunTimeTicketFieldValues("Email")
                .contains(mailId) && viewTicketDetails
                .getRunTimeTicketFieldValues("Channel")
                .contains(channel)) {

            System.out.println("Ticket ID: " + ticketID);
            edit = menus.clickEdit();
        }
    }

    private void changePrivateModeStatus() throws IOException {

        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Private mode status started");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        menus.clickEdit();
        edit.selectStatus("Private Note Added");
        edit.selectServiceFrequency(serviceFreq);
        edit.selectPossibleClosure("Yes");
        edit.enterETAT(getETAT());
        edit.enterOrderValue("42");
        edit.enterKeyword(comment);
        if(!service.equals("Voice Over")) {
            edit.enterPurpose(comment + " Message");
        } else {
            edit.selectVoiceOverPurpose(" Broadcast ");
        }

        edit.selectSalesPerson(salesPersonName);
        edit.selectAllocator(allocatorPersonName);
        edit.clickUpdateButton();
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Adding files in Quote Info");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        quoteInfo = menus.clickQuoteInfo();
        if(service.equals("Voice Over")) {
            int min = Integer.parseInt(minute);
            quoteInfo.setFileDetailsForVoiceOver(fileName, language,
                    language, service, minute, "" + bPrice, comment,
                    false, "" + (min*bPrice));
        } else {
            quoteInfo.setFileDetails(fileName, language,
                    language, service, minute, "" + bPrice, comment,
                    false);
        }
        quoteInfo.clickUpdateQuoteInfo();
        menus.clickSignOut();
    }

    private void changePaymentMadeStatus() {

        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Payment made status started");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        driver.get("https://secure-dt.com/crm/user/login");
        login = new Login(driver);
        dashBoardPage = login.signIn(salesPersonName, salesPersonPwd);
        menus = dashBoardPage.clickProcess();
        menus.clickPrivateNoteAdded();
        editTicketDetails(driver);
        PrivateNote privateNote = menus.clickPrivateNote();
        privateNote.clickPaymentMadePrivateNote();
        PaymentMadePrivateNoteAlert paymentMadePrivateNoteAlert =
                new PaymentMadePrivateNoteAlert(driver);
        paymentMadePrivateNoteAlert.selectApprovedBy(allocatorPersonName);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        paymentMadePrivateNoteAlert.enterPaymentDate(dateFormat.format(date));
        paymentMadePrivateNoteAlert.enterPaidAmount("42");
        paymentMadePrivateNoteAlert.selectPaymentMode("PayPal");
        paymentMadePrivateNoteAlert.clickSubmit();
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Payment made status changed");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");

        menus.clickSignOut();
    }

    private void checkCustomerDashboardStatus(String status, boolean
            orderDeliver) {

        System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Checking customer dashbard status");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");
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
