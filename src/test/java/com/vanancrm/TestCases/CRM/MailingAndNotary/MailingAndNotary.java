package com.vanancrm.TestCases.CRM.MailingAndNotary;

import com.vanan.CRM.PageObjects.MainPages.*;
import com.vanan.CRM.PageObjects.WholeSitePages.*;
import com.vanancrm.Common.TestBase;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MailingAndNotary extends TestBase {

    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private String ticketID = "VS00345842";

    private String channel = "Quick Quote";
    private String comment = "Automation Testing";
    private String mailId = "naveen@vananservices.com";
    private String service = "Captioning";
    private String status = "Mailing with Notary";

    private static List<String> fileNames = new ArrayList<String>();
    private static List<String> sourceLangs = new ArrayList<String>();
    private static List<String> targetLangs = new ArrayList<String>();
    private static List<String> deliveryStatus = new ArrayList<String>();


    private Edit edit;
    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    @Test
    public void testStep() throws IOException, InterruptedException, AWTException {
        ticketID = System.getProperty("ticketid");
        channel =  System.getProperty("channel");
        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = new Menus(driver);
        readTableData = new ReadTableData(driver);
        menus.searchCustomerDetails(ticketID);
        viewTicketDetails = new ViewTicketDetails(driver);
        Delivery delivery;
        viewTicketDetails = readTableData.clickOldTableService(service,
                (1));

        if (
            //viewTicketDetails.getRunTimeTicketFieldValues("Email")
            //.contains(mailId) &&
                viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel")
                        .contains(channel)) {
            System.out.println("Ticket ID: " + ticketID);
            delivery = menus.clickDelivery();
            /*for(int i=0; i< fileNames.size(); i++) {

                if(deliveryStatus.get(i).equals("Delivered")) {

                    delivery.selectDeliveryFile2Customer(fileNames.get(i), sourceLangs.get(i).toLowerCase()
                            + " - " + targetLangs.get(i).toLowerCase());
                } else if(deliveryStatus.get(i).equals("Rejected")){

                    Assert.assertFalse(delivery.isSelectDeliveryFile2Customer(fileNames.get(i), sourceLangs.get(i).toLowerCase()
                            + " - " + targetLangs.get(i).toLowerCase()),"User can able to select Rejected file");
                }
            }
            delivery.clickMailingNotaryButton();
            delivery.selectDeliveryDate(getETAT());
            delivery.selectNotaryType(status);
            delivery.enterCustomerComments(comment);
            delivery.enterCustomerAddress("Vanan-"+comment);
            delivery.clickPopupSubmitButton();*/
            menus.clickMailingNotary();
            menus.clickMailingNotaryPending();
            MailingNotary mailingNotary = new MailingNotary(driver);
            mailingNotary.clickParticularTicket(ticketID, status);
            mailingNotary.selectStatus("In Progress");
            mailingNotary.clickNotaryScanFileUpload();
            String parentWindow = driver.getWindowHandle();
            Set<String> handles =  driver.getWindowHandles();
            String purpose = "Company Certificate";
            for(String windowHandle  : handles)
            {
                if(!windowHandle.equals(parentWindow))
                {
                    driver.switchTo().window(windowHandle);
                    FileUploadFromTicket fileUploadFromTicket = new
                            FileUploadFromTicket(driver);
                    fileUploadFromTicket.selectPurpose(purpose);
                    fileUploadFromTicket.uploadFile(driver, fileNames.get(0), "");
                    waitForProcessCompletion(10);
                    if(fileUploadFromTicket.getUploadedSuccessMsg().contains(
                            "File uploaded")) {
                        System.out.println("File Successfully updated");
                    }
                    fileUploadFromTicket.enterComments(comment);
                    fileUploadFromTicket.clickSubmitButton();
                    waitForProcessCompletion(10);
                    driver.close();
                    driver.switchTo().window(parentWindow);
                    break;
                }
            }
            mailingNotary.clickUpdateButton();
            menus.clickMailingNotaryInprogress();
            Assert.assertTrue(mailingNotary.isticketDisplayed(ticketID, status),
                    "Mailing notary ticket is not present in progress");
            mailingNotary.clickParticularTicket(ticketID, status);
            mailingNotary.selectStatus("Delivered");
            mailingNotary.clickTrackingDocumentFileUpload();
            String parentWindow1 = driver.getWindowHandle();
            Set<String> handles1 =  driver.getWindowHandles();
            for(String windowHandle  : handles1)
            {
                if(!windowHandle.equals(parentWindow1))
                {
                    driver.switchTo().window(windowHandle);
                    FileUploadFromTicket fileUploadFromTicket = new
                            FileUploadFromTicket(driver);
                    fileUploadFromTicket.selectPurpose(purpose);
                    fileUploadFromTicket.uploadFile(driver, fileNames.get(1), "");
                    waitForProcessCompletion(10);
                    if(fileUploadFromTicket.getUploadedSuccessMsg().contains(
                            "File uploaded")) {
                        System.out.println("File Successfully updated");
                    }
                    fileUploadFromTicket.enterComments(comment);

                    fileUploadFromTicket.clickSubmitButton();
                    waitForProcessCompletion(10);
                    driver.close();
                    driver.switchTo().window(parentWindow1);
                    break;
                }
            }
            mailingNotary.clickUpdateButton();
            menus.clickMailingNotaryDelivered();
            Assert.assertTrue(mailingNotary.isticketDisplayed(ticketID, status),
                    "Mailing notary ticket is not present in delivered");
        }
    }

    @BeforeClass
    public void beforeClass() throws IOException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1900,1200");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        readMailingNotaryData();
        getEmailCreadential();
        //fileOutput = new FileOutputStream(file);
    }

    @AfterClass
    public void afterClass() {

        //driver.quit();
    }

    private static void getEmailCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }


    public static void readMailingNotaryData() throws IOException {

        file = new File("src/test/resources/Mailing1.xls");
        fileInput = new FileInputStream(file);
        workbook = new HSSFWorkbook(fileInput);
        sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            cell = sheet.getRow(i).getCell(0);
            fileNames.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(1);
            sourceLangs.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(2);
            targetLangs.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(3);
            deliveryStatus.add(cell.getStringCellValue());
        }
    }

    private String checkStatus(String data1, String data2, String message) {
        String status;
        System.out.println(message);
        if (data1.toLowerCase().contains(data2.toLowerCase())) {
            System.out.print(": Pass\n");
            status = "Pass";
        } else {
            System.out.print(": Fail\n");
            System.out.println("Expected : " + data1);
            System.out.println("Actual : " + data2);
            status = "Fail\n" + "Expected : " + data1 + "\nActual : " + data2;
        }
        return status;
    }

    private String getETAT() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 2);
        String newDate = sdf.format(cal.getTime());
        return newDate;
    }
}
