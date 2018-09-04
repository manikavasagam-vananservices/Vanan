package com.vanancrm.TestCases.CRM.Review;

import com.vanan.CRM.PageObjects.MainPages.*;
import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;
import com.vanancrm.Common.TestBase;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Review1 extends TestBase {

    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private String ticketID = "";
    private String allocationID = "";
    private String channel = "Quick Quote";
    private String service = "";

    private static List<String> fileNames = new ArrayList<String>();
    private static List<String> fileTypes = new ArrayList<String>();
    private static List<String> sourceLangs = new ArrayList<String>();
    private static List<String> targetLangs = new ArrayList<String>();
    private static List<String> pagelengths = new ArrayList<String>();
    private static List<String> costPerMins = new ArrayList<String>();
    private static List<String> services = new ArrayList<String>();
    private static List<String> allocatorNames = new ArrayList<String>();
    private static List<String> vendorNames = new ArrayList<String>();
    private static List<String> allocationStatus = new ArrayList<String>();

    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    @Test
    public void testStep() throws IOException {
	
        ticketID = System.getProperty("ticketid");
        service = System.getProperty("service");
        allocationID = System.getProperty("allocationid");

        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = new Menus(driver);
        readTableData = new ReadTableData(driver);
        menus.searchCustomerDetails(ticketID);
        viewTicketDetails = new ViewTicketDetails(driver);
        Review review;
        viewTicketDetails = readTableData.clickOldTableService(service,
                (1));
        List<String> reviewTicketData = new ArrayList<>();
        List<String> reviewFileAllocationData = new ArrayList<>();
        Sheet sheet1;

        double len = 0.00f;
        if (viewTicketDetails.getRunTimeTicketFieldValues("Channel")
                        .contains(channel)) {
            System.out.println("Ticket ID: " + ticketID);
            review = menus.clickReview();
            reviewTicketData = review.getTicketDetails();
            reviewFileAllocationData = review.getFileAllocationDetails();
            String[] datas1 = reviewTicketData.get(0).split("#");
            sheet1 = workbook.createSheet("Ticket Details status");
            Row headerRow = sheet1.createRow(0);
            Cell cell;
            cell = headerRow.createCell(0);
            cell.setCellValue(datas1[0]);
            cell = headerRow.createCell(1);
            cell.setCellValue(checkStatus(datas1[0], ticketID, "Order No"));

            cell = headerRow.createCell(2);
            cell.setCellValue(datas1[1]);
            cell = headerRow.createCell(3);
            cell.setCellValue(checkStatus(datas1[1], service, "Service"));

            cell = headerRow.createCell(4);
            cell.setCellValue(datas1[2]);
            cell = headerRow.createCell(5);
            cell.setCellValue(checkStatus(datas1[2], "sales-Test", "Sales Assignee"));

            cell = headerRow.createCell(6);
            cell.setCellValue(datas1[2]);
            cell = headerRow.createCell(7);
            cell.setCellValue(checkStatus(datas1[3], "allocator-Test", "Allocator Assignee"));

            cell = headerRow.createCell(7);
            cell.setCellValue(datas1[3]);
            cell = headerRow.createCell(8);
            cell.setCellValue(checkStatus(datas1[4], "Others", "Status"));

            cell = headerRow.createCell(9);
            cell.setCellValue(datas1[4]);
            cell = headerRow.createCell(10);
            cell.setCellValue(checkStatus(datas1[5], "$77.00", "Order Value"));
            String[] datas2;
            for (int i = 0; i < fileNames.size(); i++) {

                datas2 = reviewFileAllocationData.get(i).split("#");
                cell = sheet.getRow(i + 1).getCell(10);
                cell.setCellValue(datas2[1]);
                cell = sheet.getRow(i + 1).getCell(11);
                cell.setCellValue(checkStatus(datas2[1], allocationID, "Allocation No"));

                cell = sheet.getRow(i + 1).getCell(12);
                cell.setCellValue(checkStatus(datas2[2], fileNames.get(i), "File name"));
                cell = sheet.getRow(i + 1).getCell(13);
                cell.setCellValue(checkStatus(datas2[3], services.get(i), "Services"));

                cell = sheet.getRow(i + 1).getCell(14);
                cell.setCellValue(checkStatus(datas2[4], fileTypes.get(i), "File type"));
                cell = sheet.getRow(i + 1).getCell(15);
                if (pagelengths.get(i).contains("-")) {
                    String lengths[] = pagelengths.get(i).split("-");
                    String leng = pagelengths.get(i).replace("-" ,":");
                    String[] times = leng.split(":");
                    //System.out.println("hr=" + times[0]+"\nmin="+times[1]+"\nsec="+times[2]);
                    int mintime = 0;
                    if(!times[0].equals("00")) {
                        mintime += (Integer.parseInt(times[0])*60);
                        //System.out.println("hr=" + times[0]+"\nmin="+times[1]+"\nsec="+times[2] + "hr=" + mintime);
                    }
                    if(!times[1].equals("00")) {
                        mintime += Integer.parseInt(times[1]);
                        //System.out.println("hr=" + times[0]+"\nmin="+times[1]+"\nsec="+times[2] + "min=" + mintime);
                    }

                    double scost = Double.parseDouble(costPerMins.get(i)) / 3600;
                    int seconds = (Integer.parseInt(lengths[0]) * 60 * 60)
                            + (Integer.parseInt(lengths[1]) * 60) + Integer.parseInt(lengths[2]);
                    len = (double) seconds * scost;

                    cell.setCellValue(checkStatus(mintime + ":" + times[2], datas2[5], "File Length"));
                } else {
                    int min = Integer.parseInt(pagelengths.get(i));
                    //System.out.print("@@@@@" + Double.parseDouble(costPerMins.get(i)));
                    len = Double.parseDouble(pagelengths.get(i)) * Double.parseDouble(costPerMins.get(i));
                    cell.setCellValue(checkStatus(min + "", datas2[5], "File Length"));
                }


                if (allocationStatus.get(i).equals("Rejected")) {
                    len = 0;
                }
                cell = sheet.getRow(i + 1).getCell(16);
                cell.setCellValue(checkStatus(len + "", datas2[6], "Vendor Cost"));
                cell = sheet.getRow(i + 1).getCell(17);
                cell.setCellValue(checkStatus(sourceLangs.get(i) + "-" + targetLangs.get(i), datas2[7], "Language"));

                cell = sheet.getRow(i + 1).getCell(18);
                cell.setCellValue(checkStatus(allocatorNames.get(i), datas2[8], "Allocator"));
                cell = sheet.getRow(i + 1).getCell(19);
                cell.setCellValue(checkStatus(vendorNames.get(i), datas2[9], "vendor"));
                cell = sheet.getRow(i + 1).getCell(20);
                cell.setCellValue(checkStatus(allocationStatus.get(i), datas2[10], "Allocation Status"));
                //System.out.println(i+"########" + reviewFileAllocationData.get(i));
            }
        }
        workbook.write(fileOutput);
        fileOutput.close();
        menus.clickSignOut();
    }

    @BeforeClass
    public void beforeClass() throws IOException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1900,1200");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        readReviewData();
        getEmailCreadential();
        fileOutput = new FileOutputStream(file);
    }

    @AfterClass
    public void afterClass() {

        screenshot(driver, "Review");
        driver.quit();
    }

    private static void getEmailCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }

    public static void readReviewData() throws IOException {

        file = new File("src/test/resources/Review1.xls");
        fileInput = new FileInputStream(file);
        workbook = new HSSFWorkbook(fileInput);
        sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            cell = sheet.getRow(i).getCell(0);
            fileNames.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(1);
            fileTypes.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(2);
            services.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(3);
            pagelengths.add(cell.getStringCellValue().replace("#", ""));
            cell = sheet.getRow(i).getCell(4);
            costPerMins.add(cell.getStringCellValue().replace("#", ""));
            cell = sheet.getRow(i).getCell(5);
            sourceLangs.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(6);
            targetLangs.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(7);
            allocatorNames.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(8);
            vendorNames.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(9);
            allocationStatus.add(cell.getStringCellValue());
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
}
