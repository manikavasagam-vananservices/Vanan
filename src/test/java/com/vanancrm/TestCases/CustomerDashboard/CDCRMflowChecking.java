package com.vanancrm.TestCases.CustomerDashboard;

import com.vanan.CRM.PageObjects.MainPages.*;
import com.vanan.CRM.PageObjects.WholeSitePages.*;
import com.vanancrm.Common.TestBase;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class CDCRMflowChecking extends TestBase {

    private static String username = "";
    private static String password = "";
    private static String misPersonName = "";
    private static String salesPersonName = "";
    private static String allocatorPersonName = "";
    private static String vendorPersonName = "";
    private static String vendorPersonPwd = "";
    private static String misPersonPwd = "";
    private static String salesPersonPwd = "";
    private static String allocatorPersonPwd = "";

    private String serviceFreq = "Not sure";
    private DashBoardPage dashBoardPage;
    private Edit edit;
    private QuoteInfo quoteInfo;
    private EmailConversation emailConversation;
    private Login login;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    private String url = "vananservices.com";
    private String ticketID;
    private String channel = "Customer Dashboard";
    private String status = "Yes";
    private WebDriver driver;
    private String mailId = "automation@vananservices.com";

    private String[] ticketStatus = {"In Progress", "Delivered"};
    private String comments = "Automation Testing";
    private String fileName = "";
    private String fileExtenstion;

    public CDCRMflowChecking(WebDriver webDriver) {
        driver = webDriver;
    }

    private static void getCRMCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
        misPersonName = properties.getProperty("MISNAME");
        misPersonPwd = properties.getProperty("MISPASSWORD");
        salesPersonName = properties.getProperty("SALESNAME");
        salesPersonPwd = properties.getProperty("SALESPASSWORD");
        allocatorPersonName = properties.getProperty("ALLOCATORNAME");
        allocatorPersonPwd = properties.getProperty("ALLOCATORPASSWORD");
        vendorPersonName = properties.getProperty("VENDORNAME");
        vendorPersonPwd = properties.getProperty("VENDORPASSWORD");
    }

    public void checkCRM(String language, String service, String minute, String
            fileExt, double bPrice) throws IOException, InterruptedException,
            AWTException {

        fileExtenstion = fileExt;
        getCRMCreadential();
        driver.get("https://secure-dt.com/crm/user/login");
        fileName = service;
        System.out.println("File name = " + fileName);
        login = new Login(driver);
        dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, language, service, minute);
        updateFileInfoByMis(service, minute);
        changePaymentMadeStatusBySales(service,language, minute, bPrice);
        TranscriptionCD transcriptionCD = new TranscriptionCD();
        transcriptionCD.checkCustomerDashboardStatus
                (driver, ticketStatus[0], false);
        vendorAllocationByAllocator(driver, service,
                minute);
        vendorProcessing(driver, service);
        AllocatorChangeTicketStatus(driver, service);
        transcriptionCD.checkCustomerDashboardStatus
                (driver,ticketStatus[1], true);
        driver.get("https://secure-dt.com/crm/user/login");
        login = new Login(driver);
        dashBoardPage = login.signIn(username, password);
        menus = new Menus(driver);
        AllocatorDashboard allocatorDashboard = menus.clickAllocatorDashboard();
        menus.clickCompleted();
        allocatorDashboard.clickFileInCompletedSection(fileName,
                allocatorPersonName, vendorPersonName);
        allocatorDashboard.clickEditMenu();
        waitForProcessCompletion(10);
        allocatorDashboard.selectPopupStatus("Rejected");
        allocatorDashboard.clickEditSummarySubmit();
        allocatorDashboard.clickEmailConversationMenu();
        allocatorDashboard.clickReadMore();
        allocatorDashboard.clickNoActionButton();
        menus.clickAllProcess();
        menus.clickNewMenu();
        editTicketDetails(driver,service);
        edit.selectStatus("Others");
        edit.clickUpdateButton();
        System.out.println(
                "=============================================================================");
        System.out.println("Ticket ID : " + ticketID + " Moved to others");

        System.out.println(
                "=============================================================================");
        System.out.println("Test Completed");
        System.out.println(
                "=============================================================================");
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String language, String service,
            String minute) {

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
                    System.out.println("Ticket ID: " + ticketID);

                    checkViewTicketInfo(language, service, minute);
                    assignTicketToSalesByAdmin();
                    checkCRMEmailConversation(language, service, minute);
                    break;
                } else {
                    ticketID = "\n\nEither ticket is Not created or Still" +
                            " waiting for ticket";
                    System.out.println(ticketID);
                }
                waitForProcessCompletion(10);
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

    private void assignTicketToSalesByAdmin() {

        // Edit a ticket and moved the status into Others
        edit = menus.clickEdit();
        edit.selectSalesPerson(salesPersonName);
        edit.clickUpdateButton();
        emailConversation = menus.clickEmailConversation();
        emailConversation.clickReadMore(channel);
    }

    private void checkCRMEmailConversation(String language, String service,
            String minute) {

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

        evaluateCondition("File(s)", emailConversation
                .getTicketFieldValues("File(s)"), fileName + fileExtenstion);
        evaluateCondition("File(s) Link", emailConversation
                .getTicketFieldValues("File(s) Link"), fileName + fileExtenstion);
        System.out.println("Turnaround Time : " + emailConversation
                .getTicketFieldValues("Turnaround Time"));
        /*evaluateCondition("Turnaround Time", emailConversation
            .getTicketFieldValues("Turnaround Time"), status);*/
        evaluateCondition("Verbatim", emailConversation
                .getTicketFieldValues("Verbatim"), status);
        evaluateCondition("Time Code", emailConversation
                .getTicketFieldValues("Time Code"), status);

        evaluateCondition("Native Transcribers", emailConversation
                .getTicketFieldValues("Native Transcribers"), status);


        evaluateCondition("Comment",
                emailConversation.getTicketFieldValues("Comment"), comments);
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
        menus.clickSignOut();
    }

    private void checkViewTicketInfo(String language, String service,
            String minute) {

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


        evaluateCondition("Native", viewTicketDetails
                .getRunTimeTicketFieldValues("Native"), status);
        evaluateCondition("Minutes", viewTicketDetails
                .getRunTimeTicketFieldValues("Minutes"), minute);
        evaluateCondition("TimeCoding", viewTicketDetails
                .getRunTimeTicketFieldValues("TimeCoding"), status);
        evaluateCondition("Verbatim", viewTicketDetails
                .getRunTimeTicketFieldValues("Verbatim"), status);
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

    private void updateFileInfoByMis(String service, String minute) throws IOException{

        driver.get("https://secure-dt.com/crm/user/login");
        login = new Login(driver);
        dashBoardPage = login.signIn(misPersonName, misPersonPwd);
        menus = dashBoardPage.clickAllProcess();
        readTableData = menus.clickNewMenu();
        editTicketDetails(driver, service);
        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t File details are added in file info");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
        menus.clickFileInfo();
        FileInfo fileInfo = new FileInfo(driver);
        if(service.contains("Transcription")) {
            fileInfo.setFileDetails(fileName,
                    "Audio/Video", "00-00-" + minute, "Good", "Accent", ">" +
                            " 5", comments);
        } else {
            fileInfo.setFileDetails(fileName,
                    "Document", "" + minute, "Good", "Accent", "> 5", comments);
        }

        fileInfo.clickUpdateFileInfo();

        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Private mode status started");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
        menus.clickEdit();
        edit.selectStatus("Private Note Added");
        waitForProcessCompletion(10);
        edit.selectServiceFrequency(serviceFreq);
        edit.selectPossibleClosure("Yes");
        //waitForProcessCompletion(60);
        edit.enterETAT(getETAT());
        edit.enterOrderValue("42");
        edit.enterKeyword(comments);
        if(service.equals("Transcription") || service.equals("Translation")) {
            edit.enterPurpose(comments + " Message");
        } else if (service.equals("Voice Over")){
            edit.selectVoiceOverPurpose(" Broadcast ");
        } else if(service.equals("Typing")){

        }

        edit.selectSalesPerson(salesPersonName);
        edit.selectAllocator(allocatorPersonName);
        edit.clickUpdateButton();
        menus.clickSignOut();
    }

    private void editTicketDetails(WebDriver driver, String service) {
        menus = new Menus(driver);
        menus.searchCustomerDetails(ticketID);
        viewTicketDetails = new ViewTicketDetails(driver);
        viewTicketDetails = readTableData.clickOldTableService(service,
                (1));
        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Edit particular ticket");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
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

    private void changePaymentMadeStatusBySales(String service, String
            language, String minute, double bPrice) throws IOException {

        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Payment made status started");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
        driver.get("https://secure-dt.com/crm/user/login");
        login = new Login(driver);
        dashBoardPage = login.signIn(salesPersonName, salesPersonPwd);
        menus = dashBoardPage.clickProcess();
        menus.clickPrivateNoteAdded();
        editTicketDetails(driver, service);
        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Adding files in Quote Info");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
        quoteInfo = menus.clickQuoteInfo();
        quoteInfo.clickPopUpCloseButton();
        if(service.equals("Voice Over")) {
            int min = Integer.parseInt(minute);
            quoteInfo.setFileDetailsForVoiceOver(fileName, language,
                    language, service, minute, "" + bPrice, comments,
                    false, "" + (min*bPrice));
        } else {
            quoteInfo.setFileDetails(fileName, language,
                    language, service, minute, "" + bPrice, comments,
                    false);
        }
        quoteInfo.clickUpdateQuoteInfo();
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
        quoteInfo = menus.clickQuoteInfo();
        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t File is moved to allocation");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
        //quoteInfo.clickPopUpCloseButton();
        quoteInfo.clickMoveToLocation();
        menus.clickSignOut();
    }

    private void vendorAllocationByAllocator(WebDriver driver, String service,
            String minute) {

        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t File will allocate to vendor");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
        driver.get("https://secure-dt.com/crm/user/login");
        login = new Login(driver);
        dashBoardPage = login.signIn(allocatorPersonName, allocatorPersonPwd);
        menus = new Menus(driver);
        menus.clickPaymentMade();
        editTicketDetails(driver, service);
        VendorAllocation vendorAllocation = menus.clickVendorAllocation();
        vendorAllocation.allocateFileIntoVendor(fileName, minute, false, "1",
                        getETAT(), getFTAT(), comments, "Good", "Legal",
                        vendorPersonName);
        vendorAllocation.clickAllocateFile();
        menus.clickSignOut();
    }

    private void vendorProcessing(WebDriver driver, String service) throws
            InterruptedException, AWTException, IOException{

        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t File will allocate to vendor");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
        driver.get("https://secure-dt.com/crm/user/login");
        login = new Login(driver);
        dashBoardPage = login.signIn(vendorPersonName, vendorPersonPwd);
        menus = new Menus(driver);
        VendorDashBoard vendorDashBoard = new VendorDashBoard(driver);
        vendorDashBoard.clickNewFiles();
        String allocationId = vendorDashBoard.clickParticularFile(fileName,
                service);
        vendorDashBoard.clickAcceptButton();
        vendorDashBoard.clickParticularFile(fileName, service);
        vendorDashBoard.clickStartWorkButton();
        vendorDashBoard.clickInprogress();
        vendorDashBoard.clickParticularFile(fileName, service);
        vendorDashBoard.clickUploadButton();
        String parentWindow = driver.getWindowHandle();
        Set<String> handles =  driver.getWindowHandles();
        for(String windowHandle  : handles)
        {
            if(!windowHandle.equals(parentWindow))
            {
                driver.switchTo().window(windowHandle);
                FileUploadFromTicket fileUploadFromTicket = new
                        FileUploadFromTicket(driver);
                fileUploadFromTicket.uploadFile(driver, fileName, fileExtenstion);
                waitForProcessCompletion(10);
                if(fileUploadFromTicket.getUploadedSuccessMsg().contains(
                        "File uploaded")) {
                    System.out.println("File Successfully updated");
                }
                fileUploadFromTicket.selectSuject("Police Certificate");
                fileUploadFromTicket.enterComments(comments);
                fileUploadFromTicket.clickSubmitButton();
                waitForProcessCompletion(10);
                driver.close();
                driver.switchTo().window(parentWindow);
                break;
            }
        }
        vendorDashBoard.clickCompleted();
        for(String id : vendorDashBoard.getAllocationIds()) {
            if(id.contains(allocationId)) {
                System.out.println("File is moved into completed status");
                break;
            }
        }
        menus.clickSignOut();
    }

    private void AllocatorChangeTicketStatus(WebDriver driver, String service) {

        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t File will allocate to vendor");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
        driver.get("https://secure-dt.com/crm/user/login");
        login = new Login(driver);
        dashBoardPage = login.signIn(allocatorPersonName, allocatorPersonPwd);
        menus = new Menus(driver);
        menus.clickPaymentMade();
        menus.searchCustomerDetails(ticketID);
        viewTicketDetails = new ViewTicketDetails(driver);
        viewTicketDetails = readTableData.clickOldTableService(service,
                (1));
        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Edit particular ticket");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
        System.out.println("Channel " + viewTicketDetails
                .getRunTimeTicketFieldValues("Channel"));
        if (viewTicketDetails.getRunTimeTicketFieldValues("Email")
                .contains(mailId) && viewTicketDetails
                .getRunTimeTicketFieldValues("Channel")
                .contains(channel)) {

            System.out.println("Ticket ID: " + ticketID);
            /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
            System.out.println("\t Deliver the files");
            System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
            Delivery delivery = menus.clickDelivery();
            delivery.selectDeliveryFileToCustomer(fileName,
                    vendorPersonName);
            delivery.clickDeliverFilesToCustomerButton();
            delivery.clickSendEmailToCustomerButton();
        }
        /*System.out.println("\t++++++++++++++++++++++++++++++++++++");
        System.out.println("\t Deliver the files");
        System.out.println("\t++++++++++++++++++++++++++++++++++++");*/
        Delivery delivery = menus.clickDelivery();
        delivery.selectDeliveryFileToCustomer(fileName,
                vendorPersonName);
        delivery.clickDeliverFilesToCustomerButton();
        delivery.clickSendEmailToCustomerButton();
        menus.clickView();
        menus.clickQuickEditButton();
        menus.selectPopupStatus("Order Delivered");
        menus.clickSubmitButtonFromPopup();
        menus.clickOrderDelivered();
        menus.clickSignOut();
    }

    private String getETAT() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 2);
        String newDate = sdf.format(cal.getTime());
        return newDate;
    }

    private String getFTAT() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String newDate = sdf.format(cal.getTime());
        return newDate;
    }

}
