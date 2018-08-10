package com.vanancrm.TestCases.CRM.Vendor;

import com.vanan.CRM.PageObjects.MainPages.VendorDashBoard;
import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Vendor2  extends TestBase {
    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private String ticketID = "344186";

    private static List<String> fileNames = new ArrayList<String>();
    private static List<String> sourceLangs = new ArrayList<String>();
    private static List<String> targetLangs = new ArrayList<String>();
    private static List<String> services = new ArrayList<String>();
    private static List<String> pageMinutes = new ArrayList<String>();
    private static List<String> costPerMins = new ArrayList<String>();
    private static List<String> fileQtys = new ArrayList<String>();

    @Test
    public void testStep() throws IOException {

        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
        Login login = new Login(driver);
        login.signIn(username, password);
        Menus menus = new Menus(driver);
        ReadTableData readTableData = menus.clickVendorNewFiles();
        DecimalFormat decimalFormat = new DecimalFormat(".##");

        for (int i = 0; i <fileNames.size(); i++) {
            VendorDashBoard vendorDashBoard = new VendorDashBoard(driver);
            vendorDashBoard.clickNewFiles();
            String allocationId = vendorDashBoard.clickParticularFile(fileNames.get(i),
                    services.get(i));
            vendorDashBoard.clickAcceptButton();

        }
        //workbook.write(fileOutput);
        //fileOutput.close();
        menus.clickSignOut();
    }

    @BeforeClass
    public void beforeClass() throws IOException, ParseException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1900,1200");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        readFileInfoData();
        //fileOutput = new FileOutputStream(file);
    }

    @AfterClass
    public void afterClass() {

        //driver.quit();
    }

    public void readFileInfoData() throws IOException, ParseException {

        file = new File("src/test/resources/Vendor1.xls");
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
