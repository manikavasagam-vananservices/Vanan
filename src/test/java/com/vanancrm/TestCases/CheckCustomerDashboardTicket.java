package com.vanancrm.TestCases;

import com.vanan.CRM.PageObjects.MainPages.*;
import com.vanan.CRM.PageObjects.WholeSitePages.*;
import com.vanan.CustomerDashboard.PageObjects.MainPages.DashBoard;
import com.vanan.CustomerDashboard.PageObjects.WholeSitePages.LoginPage;
import com.vanancrm.Common.TestBase;
import com.vanancrm.PageObjects.MainPages.TrackOrder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        menus.clickFileInfo();
        FileInfo fileInfo = new FileInfo(driver);
        fileInfo.setFileDetails(fileName,
                "Document", "" + minute, "Good", "Accent", "> 5", comment);
        fileInfo.clickUpdateFileInfo();
        changePrivateModeStatus();
        changePaymentMadeStatus();
        checkCustomerDashboardStatus(ticketStatus[0], false);
        driver.navigate().refresh();

        dashBoardPage = new DashBoardPage(driver);
        menus = dashBoardPage.clickAllProcess();
        readTableData = menus.clickNewMenu();
        editTicketDetails(driver);
        quoteInfo = menus.clickQuoteInfo();
        quoteInfo.clickMoveToLocation();
        VendorAllocation vendorAllocation = menus
                .clickVendorAllocation();
        vendorAllocation.allocateFileIntoVendor
                (fileName, minute, false, "1",
                        getETAT(), comment, "Good", "Legal", vendorPersonName);
        vendorAllocation.clickAllocateFile();

        driver.navigate().refresh();
        allocatorDashboard = menus.clickAllocatorDashboard();
        allocatorDashboard.selectFile(fileName, username,
                vendorPersonName);
        allocatorDashboard.selectStatus("Completed");
        allocatorDashboard.clickChangeStatus();
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
        driver.navigate().refresh();
        menus = dashBoardPage.clickAllProcess();
        readTableData = menus.clickNewMenu();
        editTicketDetails(driver);
        Delivery delivery = menus.clickDelivery();
        delivery.selectDeliveryFileToCustomer(fileName,
                vendorPersonName);
        delivery.clickDeliverFilesToCustomerButton();
        delivery.clickSendEmailToCustomerButton();
        menus.clickEdit();
        edit.selectStatus("Order Delivered");
        edit.clickUpdateButton();
        checkCustomerDashboardStatus(ticketStatus[1], true);
        driver.navigate().refresh();
        menus = dashBoardPage.clickAllProcess();
        readTableData = menus.clickNewMenu();
        editTicketDetails(driver);
        edit.selectStatus("Others");
        edit.clickUpdateButton();

        driver.navigate().refresh();
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
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        fullScreen(driver);
        getCRMCreadential();
        fileName = service.replace(" ", "") + fileExtenstion;

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
    }

    @AfterClass
    public void afterClass() {

        screenshot(driver, "CheckCustomerDashboardTicket");
        //driver.quit();
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

        menus.clickEdit();
        edit.selectStatus("Private Note Added");
        edit.selectServiceFrequency(serviceFreq);
        edit.selectPossibleClosure("Yes");
        edit.enterETAT(getETAT());
        edit.enterOrderValue("42");
        edit.enterKeyword(comment);
        edit.selectSalesPerson(salesPersonName);
        edit.selectAllocator(allocatorPersonName);
        edit.clickUpdateButton();
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
    }

    private void changePaymentMadeStatus() {

        WebDriver driver1 = new ChromeDriver();
        driver1.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        fullScreen(driver1);
        driver1.get("https://secure-dt.com/crm/user/login");
        login = new Login(driver1);
        dashBoardPage = login.signIn(salesPersonName, salesPersonPwd);
        menus = dashBoardPage.clickProcess();
        menus.clickPrivateNoteAdded();
        readTableData = new ReadTableData(driver1);
        List<String> tickets = readTableData.readTableRows();

        for (int i = 0; i < tickets.size(); i++) {

            if (tickets.get(i).contains(service)) {

                viewTicketDetails = new ViewTicketDetails(driver1);
                viewTicketDetails = readTableData.clickService(service,
                        (i + 1));
                /*System.out.println("Channel " + viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel") +" = "+
                        viewTicketDetails.getOrderNo());*/
                //viewTicketDetails.getOrderNo().contains(ticketID)
                  //      &&
                if ( viewTicketDetails.getRunTimeTicketFieldValues("Channel")
                        .contains(channel)) {

                    PrivateNote privateNote = menus.clickPrivateNote();
                    privateNote.clickPaymentMadePrivateNote();
                    PaymentMadePrivateNoteAlert paymentMadePrivateNoteAlert =
                            new PaymentMadePrivateNoteAlert(driver1);
                    paymentMadePrivateNoteAlert.selectApprovedBy(allocatorPersonName);
                    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    Date date = new Date();
                    paymentMadePrivateNoteAlert.enterPaymentDate(dateFormat.format(date));
                    paymentMadePrivateNoteAlert.enterPaidAmount("42");
                    paymentMadePrivateNoteAlert.selectPaymentMode("PayPal");
                    paymentMadePrivateNoteAlert.clickSubmit();
                    break;
                }
            }
        }
        menus.clickSignOut();
        driver1.quit();
    }

    private void checkCustomerDashboardStatus(String status, boolean
            orderDeliver) {

        WebDriver driver1 = new ChromeDriver();
        driver1.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        fullScreen(driver1);
        driver1.get("https://vananservices.com/customer/index.php");
        LoginPage loginPage = new LoginPage(driver1);
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
        //dashBoard.clickLogOut();
        driver1.quit();
    }

    private void evaluateCondition(String message, String first,
                                   String second) {

        System.out.println(message + " : " + second);
        if (first.contains(second)) {

            System.out.println(message + " is correct");
        } else {

            System.out.println(message + " is wrong");
            System.out.print("\t Expected : " + first + "\n");
            System.out.print("\t Actual : " + second + "\n");
        }
    }
}
