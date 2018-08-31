package com.vanancrm.TestCases.CRM.Vendor;

import com.vanan.CRM.PageObjects.MainPages.AllocatorDashboard;
import com.vanan.CRM.PageObjects.MainPages.VendorDashBoard;
import com.vanan.CRM.PageObjects.WholeSitePages.FileUploadFromTicket;
import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanancrm.Common.TestBase;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Vendor2 extends TestBase {
    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private String comments = "Automation Testing";
    private String ticketID = "";
    private Sheet sheet1, sheet2;

    private static List<String> fileNames = new ArrayList<String>();
    private static List<String> sourceLangs = new ArrayList<String>();
    private static List<String> targetLangs = new ArrayList<String>();
    private static List<String> services = new ArrayList<String>();
    private static List<String> pageMinutes = new ArrayList<String>();
    private static List<String> costPerMins = new ArrayList<String>();
    private static List<String> fileQtys = new ArrayList<String>();
    private static List<String> actions = new ArrayList<String>();

    @Test
    public void testStep() throws IOException, InterruptedException, AWTException {

        ticketID = System.getProperty("ticketid");
        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);

        Menus menus = new Menus(driver);
        AllocatorDashboard allocatorDashboard;
        ReadTableData readTableData = new ReadTableData(driver);
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        List<String> temp = new ArrayList<>();
        CreationHelper createHelper = workbook.getCreationHelper();

        sheet1 = workbook.createSheet("Allocator-Result");
        sheet2 = workbook.createSheet("Vendor-Result");
        for (int i = 0; i < fileNames.size(); i++) {
            getVendorEmailCreadential();
            Login login = new Login(driver);
            login.signIn(username, password);
            VendorDashBoard vendorDashBoard = new VendorDashBoard(driver);
            readTableData = menus.clickVendorNewFiles();
            temp = readTableData.getAllocatedFileDetails(fileNames.get(i),
                    sourceLangs.get(i), targetLangs.get(i));

            checkConditions(i,  temp, decimalFormat, false,"");
            String allocationId1 = vendorDashBoard.clickParticularFile(fileNames.get(i),
                    services.get(i));
            if (actions.get(i).equalsIgnoreCase("Accept")) {

                vendorDashBoard.clickAcceptButton();
                menus.clickVendorAccepted();
                temp = readTableData.getAllocatedFileDetails(fileNames.get(i),
                        sourceLangs.get(i), targetLangs.get(i));

                checkConditions(i,  temp, decimalFormat, false,"");
                menus.clickSignOut();

                getAllocatorEmailCreadential();
                login.signIn(username, password);
                menus = new Menus(driver);
                menus.clickAllocatorDashboard();
                menus.clickVendorAccepted();
                temp = readTableData.getAllocatedDetails(fileNames.get(i),
                        sourceLangs.get(i), targetLangs.get(i));
                checkConditions(i,  temp, decimalFormat, true,"Accepted");
                menus.clickSignOut();

                getVendorEmailCreadential();
                login.signIn(username, password);
                menus = new Menus(driver);
                menus.clickVendorAccepted();
                vendorDashBoard.clickParticularFile(fileNames.get(i),
                        services.get(i));
                vendorDashBoard.clickStartWorkButton();
                vendorDashBoard.clickInprogress();
                temp = readTableData.getAllocatedFileDetails(fileNames.get(i),
                        sourceLangs.get(i), targetLangs.get(i));
                checkConditions(i,  temp, decimalFormat, false,"");
                menus.clickSignOut();

                getAllocatorEmailCreadential();
                login.signIn(username, password);
                menus = new Menus(driver);
                menus.clickAllocatorDashboard();
                vendorDashBoard.clickInprogress();
                temp = readTableData.getAllocatedDetails(fileNames.get(i),
                        sourceLangs.get(i), targetLangs.get(i));
                checkConditions(i,  temp, decimalFormat, true,"In process");
                menus.clickSignOut();

                getVendorEmailCreadential();
                login.signIn(username, password);
                menus = new Menus(driver);
                vendorDashBoard.clickInprogress();
                vendorDashBoard.clickParticularFile(fileNames.get(i),
                        services.get(i));
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
                        fileUploadFromTicket.uploadFile(driver, fileNames.get(i), "");
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
                temp = readTableData.getAllocatedCompletedFileDetails(fileNames.get(i),
                        sourceLangs.get(i), targetLangs.get(i));
                checkConditions(i,  temp, decimalFormat, false,"");
                menus.clickSignOut();

                getAllocatorEmailCreadential();
                login.signIn(username, password);
                menus = new Menus(driver);
                menus.clickAllocatorDashboard();
                menus.clickCompleted();
                temp = readTableData.getAllocatedDetails(fileNames.get(i),
                        sourceLangs.get(i), targetLangs.get(i));
                checkConditions(i,  temp, decimalFormat, true,"Completed");
            } else if (actions.get(i).equalsIgnoreCase("Reject")) {

                vendorDashBoard.clickReject();
                vendorDashBoard.selectRejectReason("TAT concern");
                vendorDashBoard.enterRejectReason("Automation TEsting - Reject");
                vendorDashBoard.clickRejectSubmitButton();

                menus.clickSignOut();

                getAllocatorEmailCreadential();
                login.signIn(username, password);
                menus = new Menus(driver);
                menus.clickAllocatorDashboard();
                menus.clickReject();
                temp = readTableData.getAllocatedDetails(fileNames.get(i),
                        sourceLangs.get(i), targetLangs.get(i));
                checkConditions(i, temp, decimalFormat, true, "Rejected");
            } else if (actions.get(i).equalsIgnoreCase("Cancel")) {


                menus.clickSignOut();
                getAllocatorEmailCreadential();
                login.signIn(username, password);
                menus = new Menus(driver);
                menus.clickAllocatorDashboard();
                menus.clickAllocated();

                temp = readTableData.getAllocatedDetails(fileNames.get(i),
                        sourceLangs.get(i), targetLangs.get(i));
                checkConditions(i, temp, decimalFormat, true, "Allocated");
                allocatorDashboard = new AllocatorDashboard(driver);
                allocatorDashboard.clickParticularFile(fileNames.get(i),
                        ticketID, sourceLangs.get(i), targetLangs.get(i));
                allocatorDashboard.clickCancelFileAllocationBtn();
                allocatorDashboard.selectCancelReason("Link Issue");
                allocatorDashboard.enterCancelReason("Automation TEsting -  Cancel");
                allocatorDashboard.clickCancelSubmitButton();
                menus.clickCancelled();
                temp = readTableData.getAllocatedDetails(fileNames.get(i),
                        sourceLangs.get(i), targetLangs.get(i));
                checkConditions(i, temp, decimalFormat, true, "Cancelled");
            }
            menus.clickSignOut();
        }

        workbook.write(fileOutput);
        fileOutput.close();
    }

    @BeforeClass
    public void beforeClass() throws IOException, ParseException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1900,1200");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        readFileInfoData();
        fileOutput = new FileOutputStream(file);
    }

    @AfterClass
    public void afterClass() {

        driver.quit();
    }

    private void checkConditions(int i, List<String> temp, DecimalFormat decimalFormat, boolean allocator, String message) {
        Row headerRow;
        Cell cell;
        if (allocator) {
            headerRow = sheet1.createRow(i);
            cell = headerRow.createCell(0);
            cell.setCellValue(temp.get(0));
            cell = headerRow.createCell(1);
            cell.setCellValue("Allocator-Test");
            cell = headerRow.createCell(2);
            cell.setCellValue(checkStatus(temp.get(0), "Allocator-Test", "Allocator Assignee"));

            cell = headerRow.createCell(3);
            cell.setCellValue(temp.get(1));
            cell = headerRow.createCell(4);
            cell.setCellValue("Vendor-Test");
            cell = headerRow.createCell(5);
            cell.setCellValue(checkStatus(temp.get(1), "Vendor-Test", "Vendor Name"));

            cell = headerRow.createCell(6);
            cell.setCellValue(temp.get(2));
            cell = headerRow.createCell(7);
            cell.setCellValue(ticketID);
            cell = headerRow.createCell(8);
            cell.setCellValue(checkStatus(temp.get(2), ticketID, "Allocation Id"));

            cell = headerRow.createCell(9);
            cell.setCellValue(temp.get(3));
            cell = headerRow.createCell(10);
            cell.setCellValue(fileNames.get(i));
            cell = headerRow.createCell(11);
            cell.setCellValue(checkStatus(temp.get(3), fileNames.get(i), "File name"));

            cell = headerRow.createCell(12);
            cell.setCellValue(temp.get(4));
            cell = headerRow.createCell(13);
            cell.setCellValue(sourceLangs.get(i) + " - " + targetLangs.get(i));
            cell = headerRow.createCell(14);
            cell.setCellValue(checkStatus(temp.get(4), sourceLangs.get(i) + " - " + targetLangs.get(i), "Languages"));

            double len = 0;
            if (pageMinutes.get(i).contains("-")) {
                String[] lengths = pageMinutes.get(i).split("-");
                double scost = Double.parseDouble(costPerMins.get(i)) / 3600;
                int seconds = (Integer.parseInt(lengths[0]) * 60 * 60)
                        + (Integer.parseInt(lengths[1]) * 60) + Integer.parseInt(lengths[2]);
                len = (double) seconds * scost;
            } else {
                len = Double.parseDouble(pageMinutes.get(i)) * Double.parseDouble(costPerMins.get(i));
            }

            cell = headerRow.createCell(15);
            cell.setCellValue(temp.get(6));
            cell = headerRow.createCell(16);
            cell.setCellValue(decimalFormat.format(len));
            cell = headerRow.createCell(17);
            cell.setCellValue(checkStatus(temp.get(6), decimalFormat.format(len), "Pay out Cost"));

            cell = headerRow.createCell(18);
            cell.setCellValue(temp.get(8));
            cell = headerRow.createCell(19);
            cell.setCellValue(message);
            cell = headerRow.createCell(20);
            cell.setCellValue(checkStatus(temp.get(8), message, "Status"));
        } else {
            headerRow = sheet2.createRow(i);
            cell = headerRow.createCell(0);
            cell.setCellValue(temp.get(0));
            cell = headerRow.createCell(1);
            cell.setCellValue(ticketID);
            cell = headerRow.createCell(2);
            cell.setCellValue(checkStatus(temp.get(0), ticketID, "Allocation Id"));

            cell = headerRow.createCell(3);
            cell.setCellValue(temp.get(1));
            cell = headerRow.createCell(4);
            cell.setCellValue(fileNames.get(i));
            cell = headerRow.createCell(5);
            cell.setCellValue(checkStatus(temp.get(1), fileNames.get(i), "File Names"));

            cell = headerRow.createCell(6);
            cell.setCellValue(temp.get(2));
            cell = headerRow.createCell(7);
            cell.setCellValue(sourceLangs.get(i) + " - " + targetLangs.get(i));
            cell = headerRow.createCell(8);
            cell.setCellValue(checkStatus(temp.get(2), sourceLangs.get(i) + " - " + targetLangs.get(i), "Languages"));

            cell = headerRow.createCell(9);
            cell.setCellValue(temp.get(3));
            cell = headerRow.createCell(10);
            cell.setCellValue(fileQtys.get(i));
            cell = headerRow.createCell(11);
            cell.setCellValue(checkStatus(temp.get(3), fileQtys.get(i), "File Quality"));

            double len = 0;
            if (pageMinutes.get(i).contains("-")) {
                String[] lengths = pageMinutes.get(i).split("-");
                double scost = Double.parseDouble(costPerMins.get(i)) / 3600;
                int seconds = (Integer.parseInt(lengths[0]) * 60 * 60)
                        + (Integer.parseInt(lengths[1]) * 60) + Integer.parseInt(lengths[2]);
                len = (double) seconds * scost;
            } else {
                len = Double.parseDouble(pageMinutes.get(i)) * Double.parseDouble(costPerMins.get(i));
            }

            cell = headerRow.createCell(12);
            cell.setCellValue(temp.get(4));
            cell = headerRow.createCell(13);
            cell.setCellValue(decimalFormat.format(len));
            cell = headerRow.createCell(14);
            cell.setCellValue(checkStatus(temp.get(4), decimalFormat.format(len), "Pay out Cost"));

            cell = headerRow.createCell(15);
            cell.setCellValue(temp.get(7));
            cell = headerRow.createCell(16);
            cell.setCellValue(services.get(i));
            cell = headerRow.createCell(17);
            cell.setCellValue(checkStatus(temp.get(7), services.get(i), "Service Status"));
        }
    }

    public void readFileInfoData() throws IOException, ParseException {

        file = new File("src/test/resources/Vendor2.xls");
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
            pageMinutes.add(cell.getStringCellValue().replace("#", ""));
            cell = sheet.getRow(i).getCell(5);
            costPerMins.add(cell.getStringCellValue().replace("#", ""));
            cell = sheet.getRow(i).getCell(6);
            fileQtys.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(7);
            actions.add(cell.getStringCellValue());
        }
    }

    private static void getVendorEmailCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("VENDORNAME");
        password = properties.getProperty("VENDORPASSWORD");
    }

    private static void getAllocatorEmailCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("ALLOCATORNAME");
        password = properties.getProperty("ALLOCATORPASSWORD");
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


    private void loginVendorDashboard() throws IOException {
        getVendorEmailCreadential();
        Login login = new Login(driver);
        login.signIn(username, password);
        Menus menus = new Menus(driver);
        ReadTableData readTableData = menus.clickVendorNewFiles();
    }
}
