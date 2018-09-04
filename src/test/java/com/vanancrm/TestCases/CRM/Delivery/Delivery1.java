package com.vanancrm.TestCases.CRM.Delivery;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Delivery;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;
import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;
import com.vanancrm.Common.TestBase;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Delivery1 extends TestBase {

    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private String ticketID = "";

    private String channel = "Quick Quote";
    private String service = "";

    private static List<String> fileNames = new ArrayList<String>();
    private static List<String> sourceLangs = new ArrayList<String>();
    private static List<String> targetLangs = new ArrayList<String>();
    private static List<String> services = new ArrayList<String>();
    private static List<String> fileQtys = new ArrayList<String>();
    private static List<String> vendorStatus = new ArrayList<String>();
    private static List<String> vendorName = new ArrayList<String>();
    private static List<String> deliveryStatus = new ArrayList<String>();
    private static List<String> afterDeliveryStatus = new ArrayList<String>();


    private Edit edit;
    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    @Test
    public void testStep() throws IOException {

        ticketID = System.getProperty("ticketid");
        service = System.getProperty("service");
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
        List<String> deliveryData = new ArrayList<>();
        if (viewTicketDetails.getRunTimeTicketFieldValues("Channel")
                        .contains(channel)) {
            System.out.println("Ticket ID: " + ticketID);
            delivery = menus.clickDelivery();
            deliveryData = delivery.getDeliveryFileDetails();
            String[] datas;
            for (int i = 0; i < fileNames.size(); i++) {

                datas = deliveryData.get(i).split("#");
                cell = sheet.getRow(i + 1).getCell(9);
                cell.setCellValue(checkStatus(datas[0], fileNames.get(i), "File Name"));
                cell = sheet.getRow(i + 1).getCell(10);
                cell.setCellValue(checkStatus(datas[1], sourceLangs.get(i) + " - " + targetLangs.get(i), "Language"));
                cell = sheet.getRow(i + 1).getCell(11);
                cell.setCellValue(checkStatus(datas[2], services.get(i), "Service"));
                cell = sheet.getRow(i + 1).getCell(12);
                cell.setCellValue(checkStatus(datas[3], fileQtys.get(i), "File Qualitys"));
                cell = sheet.getRow(i + 1).getCell(14);
                cell.setCellValue(checkStatus(datas[4], vendorName.get(i), "Vendor Name"));
                cell = sheet.getRow(i + 1).getCell(13);
                cell.setCellValue(checkStatus(datas[5], vendorStatus.get(i), "Vendor Status"));
                cell = sheet.getRow(i + 1).getCell(15);
                cell.setCellValue(checkStatus(datas[6], deliveryStatus.get(i), "Delivery Status"));
                if(vendorStatus.get(i).equals("Completed")) {
                    delivery.selectDeliveryFile2Customer(fileNames.get(i), sourceLangs.get(i).toLowerCase()
                            + " - " + targetLangs.get(i).toLowerCase());
                }
            }
            delivery.clickDeliverFilesToCustomerButton();
            delivery.clickSendEmailToCustomerButton();
        }

        delivery = new Delivery(driver);
        deliveryData = delivery.getDeliveryFileDetails();
        String[] datas;
        for (int i = 0; i < fileNames.size(); i++) {
            datas = deliveryData.get(i).split("#");
            cell = sheet.getRow(i + 1).getCell(16);
            cell.setCellValue(checkStatus(datas[6], afterDeliveryStatus.get(i), "Delivery Status"));
        }

        menus.clickSignOut();
        workbook.write(fileOutput);
        fileOutput.close();
    }

    @BeforeClass
    public void beforeClass() throws IOException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1900,1200");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        readDeliveryData();
        getEmailCreadential();
        fileOutput = new FileOutputStream(file);
    }

    @AfterClass
    public void afterClass() {

        screenshot(driver, "Delivery.png");
        driver.quit();
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

    public static void readDeliveryData() throws IOException {

        file = new File("src/test/resources/Delivery.xls");
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
            services.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(4);
            fileQtys.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(5);
            vendorStatus.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(6);
            vendorName.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(7);
            deliveryStatus.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(8);
            afterDeliveryStatus.add(cell.getStringCellValue());
        }
    }

    private static void getEmailCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }
}
