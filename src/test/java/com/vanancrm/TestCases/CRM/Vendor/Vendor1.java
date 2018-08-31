package com.vanancrm.TestCases.CRM.Vendor;

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

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Vendor1 extends TestBase {
    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private String ticketID = "";

    private static List<String> fileNames = new ArrayList<String>();
    private static List<String> sourceLangs = new ArrayList<String>();
    private static List<String> targetLangs = new ArrayList<String>();
    private static List<String> services = new ArrayList<String>();
    private static List<String> pageMinutes = new ArrayList<String>();
    private static List<String> costPerMins = new ArrayList<String>();
    private static List<String> fileQtys = new ArrayList<String>();

    @Test
    public void testStep() throws IOException {

        ticketID = System.getProperty("ticketid");
        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
        Login login = new Login(driver);
        login.signIn(username, password);
        Menus menus = new Menus(driver);
        ReadTableData readTableData = menus.clickVendorNewFiles();
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        List<String> temp = new ArrayList<>();
        for (int i = 0; i <fileNames.size(); i++) {
            temp = readTableData.getAllocatedFileDetails(fileNames.get(i),
                    sourceLangs.get(i), targetLangs.get(i));

            cell = sheet.getRow(i + 1).getCell(7);
            cell.setCellValue(temp.get(0));
            cell = sheet.getRow(i + 1).getCell(8);
            cell.setCellValue(checkStatus(temp.get(0), ticketID, "Allocation Id"));

            cell = sheet.getRow(i + 1).getCell(9);
            cell.setCellValue(checkStatus(temp.get(1), fileNames.get(i), "File Names"));
            cell = sheet.getRow(i + 1).getCell(10);
            cell.setCellValue(checkStatus(temp.get(2), sourceLangs.get(i) + " - " + targetLangs.get(i), "Languages"));


            cell = sheet.getRow(i + 1).getCell(11);
            cell.setCellValue(checkStatus(temp.get(3), fileQtys.get(i), "File Quality"));
            cell = sheet.getRow(i + 1).getCell(12);
            cell.setCellValue(temp.get(4));
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
            cell = sheet.getRow(i + 1).getCell(13);
            cell.setCellValue(checkStatus(temp.get(4), decimalFormat.format(len), "Pay out Cost"));
            cell = sheet.getRow(i + 1).getCell(14);
            cell.setCellValue(checkStatus(temp.get(6), "New File", "Allocated Status"));
            cell = sheet.getRow(i + 1).getCell(15);
            cell.setCellValue(checkStatus(temp.get(7), services.get(i), "Service Status"));
        }
        workbook.write(fileOutput);
        fileOutput.close();
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
        getEmailCreadential();
        fileOutput = new FileOutputStream(file);
    }

    @AfterClass
    public void afterClass() {

        driver.quit();
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

    private static void getEmailCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("VENDORNAME");
        password = properties.getProperty("VENDORPASSWORD");
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
}
