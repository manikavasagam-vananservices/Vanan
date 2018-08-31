package com.vanancrm.TestCases.CRM.VendorAllocation;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;
import com.vanan.CRM.PageObjects.MainPages.VendorAllocation;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class VendorAllocation1 extends TestBase {

    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private String ticketID = "";

    private String channel = "Quick Quote";
    private String service = "";
    private String cetatTime = "";

    private int totalPages = 0;
    private int totalLength = 0;

    private Date etat;
    private SimpleDateFormat sdf;
    private SimpleDateFormat timeFormat;
    private static List<String> fileNames = new ArrayList<String>();
    private static List<String> sourceLangs = new ArrayList<String>();
    private static List<String> targetLangs = new ArrayList<String>();
    private static List<String> services = new ArrayList<String>();
    private static List<String> pageMinutes = new ArrayList<String>();
    private static List<String> costPerMins = new ArrayList<String>();
    private static List<String> fileQtys = new ArrayList<String>();
    private static List<String> outputFileFormat = new ArrayList<String>();
    private static List<String> assignedTo = new ArrayList<String>();

    private Edit edit;
    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    @Test
    public void testStep() throws IOException, ParseException {
        
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
        viewTicketDetails = readTableData.clickOldTableService(service,
                (1));
        int length = 0;
        int temp = 0;
        double len = 0;
        double vendorCost = 0;
        DecimalFormat decimalFormat = new DecimalFormat(".##");
        if (viewTicketDetails
                .getRunTimeTicketFieldValues("Channel")
                .contains(channel)) {
            System.out.println("Ticket ID: " + ticketID);
            VendorAllocation vendorAllocation = menus.clickVendorAllocation();
            cetatTime = vendorAllocation.getTat();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            //System.out.println("!!!!!!!!!!!!!!!!!" + vendorAllocation.getVendorCost());

            cell = sheet.getRow(1).getCell(13);
            cell.setCellValue(vendorAllocation.getTotalFileLengthYetToAssign());
            cell = sheet.getRow(1).getCell(14);
            cell.setCellValue(checkStatus(timeFormat.format(new Date(totalLength)), vendorAllocation.getTotalFileLengthYetToAssign(), "Yet Assign File Length"));

            cell = sheet.getRow(1).getCell(15);
            cell.setCellValue(vendorAllocation.getTotalPagesYetToAssign());
            cell = sheet.getRow(1).getCell(16);
            cell.setCellValue(checkStatus(totalPages, vendorAllocation.getTotalPagesYetToAssign(), "Yet Assign Pages"));

            cell = sheet.getRow(1).getCell(17);
            cell.setCellValue("00:00:00");
            cell = sheet.getRow(1).getCell(18);
            cell.setCellValue(checkStatus("00:00:00", vendorAllocation.getTotalFileLengthAssigned(), "Assigned Length"));

            cell = sheet.getRow(1).getCell(19);
            cell.setCellValue(0);
            cell = sheet.getRow(1).getCell(20);
            cell.setCellValue(checkStatus(0, vendorAllocation.getTotalPagesAssigned(), "Assigned Pages"));

            cell = sheet.getRow(1).getCell(21);
            cell.setCellValue("00:00:00");
            cell = sheet.getRow(1).getCell(22);
            cell.setCellValue(checkStatus("00:00:00", vendorAllocation.getSelectedFileLength(), "Selected Length"));
            cell = sheet.getRow(1).getCell(23);
            cell.setCellValue(0);
            cell = sheet.getRow(1).getCell(24);
            cell.setCellValue(checkStatus(0, vendorAllocation.getSelectedFileLenPages(), "Selected Pages"));
            cell = sheet.getRow(1).getCell(25);
            cell.setCellValue(0);
            cell = sheet.getRow(1).getCell(26);
            cell.setCellValue(checkStatus(0, vendorAllocation.getVendorCost(), "Vendor Cost"));
            for (int i = 0; i < fileNames.size(); i++) {


                List<String> allocatedFileDetails = vendorAllocation.allocateFileToVendor(
                        fileNames.get(i), services.get(i), sourceLangs.get(i),
                        targetLangs.get(i), pageMinutes.get(i), costPerMins.get(i),
                        getETAT(), getFTAT(), "", fileQtys.get(i),
                        outputFileFormat.get(i), assignedTo.get(i));
                if (pageMinutes.get(i).contains("-")) {
                    String[] lengths = pageMinutes.get(i).split("-");
                    double scost = Double.parseDouble(costPerMins.get(i)) / 3600;
                    int seconds = (Integer.parseInt(lengths[0]) * 60 * 60)
                            + (Integer.parseInt(lengths[1]) * 60) + Integer.parseInt(lengths[2]);
                    len = (double) seconds * scost;

                    for (int j = 0; j < allocatedFileDetails.size(); j++) {
                        if (j == 0) {
                            String leng = pageMinutes.get(i).replace("-" ,":");
                            String[] times = leng.split(":");
                            System.out.println("hr=" + times[0]+"\nmin="+times[1]+"\nsec="+times[2]);
                            int mintime = 0;
                            if(!times[0].equals("00")) {
                                mintime += (Integer.parseInt(times[0])*60);
                                System.out.println("hr=" + times[0]+"\nmin="+times[1]+"\nsec="+times[2] + "hr=" + mintime);
                            }
                            if(!times[1].equals("00")) {
                                mintime += Integer.parseInt(times[1]);
                                System.out.println("hr=" + times[0]+"\nmin="+times[1]+"\nsec="+times[2] + "min=" + mintime);
                            }
                            cell = sheet.getRow(i + 2).getCell(9);
                            cell.setCellValue(checkStatus(mintime + ":" + times[2] , allocatedFileDetails.get(j), "File length"));
                            mintime = 0;
                            break;
                        }
                    }
                    String leng1 = pageMinutes.get(i).replace("-", ":");
                    vendorAllocation.clickAllocateFile();

                    Date date = timeFormat.parse(leng1);
                    length += date.getTime();
                    totalLength -= date.getTime();
                    cell = sheet.getRow(i + 2).getCell(10);
                    cell.setCellValue(Double.parseDouble(decimalFormat.format(len)));
                    for (int j = 0; j < allocatedFileDetails.size(); j++) {
                        if (j == 1) {
                            cell = sheet.getRow(i + 2).getCell(11);
                            cell.setCellValue(allocatedFileDetails.get(j).replace("#", ""));
                            break;
                        }
                    }
                } else {
                    len = Double.parseDouble(pageMinutes.get(i)) * Double.parseDouble(costPerMins.get(i));
                    if (!services.get(i).contains("Others")) {
                        for (int j = 0; j < allocatedFileDetails.size(); j++) {
                            if (j == 0) {
                                cell = sheet.getRow(i + 2).getCell(9);
                                cell.setCellValue(checkStatus(pageMinutes.get(i), allocatedFileDetails.get(j), "File Length"));
                                break;
                            }
                        }

                        vendorAllocation.clickAllocateFile();

                        temp += Integer.parseInt(pageMinutes.get(i));
                        totalPages -= Integer.parseInt(pageMinutes.get(i));
                        cell = sheet.getRow(i + 2).getCell(10);
                        cell.setCellValue(Double.parseDouble(pageMinutes.get(i)) * Double.parseDouble(costPerMins.get(i)));
                        for (int j = 0; j < allocatedFileDetails.size(); j++) {
                            if (j == 1) {
                                cell = sheet.getRow(i + 2).getCell(11);
                                cell.setCellValue(allocatedFileDetails.get(j).replace("#", ""));
                                break;
                            }
                        }

                    } else {

                        vendorAllocation.deleteFileAllocation(fileNames.get(i));
                    }

                }

                if (!services.get(i).equals("Others")) {

                    cell = sheet.getRow(i + 2).getCell(13);
                    cell.setCellValue(vendorAllocation.getTotalFileLengthYetToAssign());
                    cell = sheet.getRow(i + 2).getCell(14);
                    cell.setCellValue(checkStatus(timeFormat.format(new Date(totalLength)), vendorAllocation.getTotalFileLengthYetToAssign(), "Yet Assign File Length"));
                    cell = sheet.getRow(i + 2).getCell(15);
                    cell.setCellValue(vendorAllocation.getTotalPagesYetToAssign());
                    cell = sheet.getRow(i + 2).getCell(16);
                    cell.setCellValue(checkStatus(totalPages, vendorAllocation.getTotalPagesYetToAssign(), "Yet Assign Pages"));

                    cell = sheet.getRow(i + 2).getCell(17);
                    cell.setCellValue(vendorAllocation.getTotalFileLengthAssigned());
                    cell = sheet.getRow(i + 2).getCell(18);
                    cell.setCellValue(checkStatus(timeFormat.format(new Date(length)), vendorAllocation.getTotalFileLengthAssigned(), "Assigned Length"));
                    cell = sheet.getRow(i + 2).getCell(19);
                    cell.setCellValue(vendorAllocation.getTotalPagesAssigned());
                    cell = sheet.getRow(i + 2).getCell(20);
                    cell.setCellValue(checkStatus(temp, vendorAllocation.getTotalPagesAssigned(), "Total Assigned Pages"));

                    cell = sheet.getRow(i + 2).getCell(21);
                    cell.setCellValue(vendorAllocation.getSelectedFileLength());
                    cell = sheet.getRow(i + 2).getCell(22);
                    cell.setCellValue(checkStatus("00:00:00", vendorAllocation.getSelectedFileLength(), "Selected File Length"));
                    cell = sheet.getRow(i + 2).getCell(23);
                    cell.setCellValue(vendorAllocation.getSelectedFileLenPages());
                    cell = sheet.getRow(i + 2).getCell(24);
                    cell.setCellValue(checkStatus(0, vendorAllocation.getSelectedFileLenPages(), "Selected Pages"));

                    for (int j = 0; j < allocatedFileDetails.size(); j++) {
                        if (j == 2) {
                            cell = sheet.getRow(i + 2).getCell(12);
                            cell.setCellValue(allocatedFileDetails.get(j).replace("!", ""));
                            break;
                        }
                    }
                    double vendorCosts = vendorAllocation.getVendorCost();
                    vendorCost += Double.parseDouble(decimalFormat.format(len));
                    cell = sheet.getRow(i + 2).getCell(25);
                    cell.setCellValue(vendorCosts);
                    cell = sheet.getRow(i + 2).getCell(26);
                    cell.setCellValue(checkStatus(vendorCost, vendorCosts, "Vendor Cost"));
                }
                System.out.println("File : " + fileNames.get(i) + " processed successfully");
            }
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

        file = new File("src/test/resources/VendorAllocation1.xls");
        fileInput = new FileInputStream(file);
        workbook = new HSSFWorkbook(fileInput);
        sheet = workbook.getSheetAt(0);
        int totalLen = 0;
        int totalPa = 0;
        int totalSec = 0;
        int minutes = 0;
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            cell = sheet.getRow(i).getCell(0);
            fileNames.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(1);
            sourceLangs.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(2);
            targetLangs.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(3);
            services.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(4);
            String temp = cell.getStringCellValue().replace("#", "");
            pageMinutes.add(temp);
            if (temp.contains("-")) {
                temp = temp.replace("-", ":");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = timeFormat.parse(temp);
                totalLength += date.getTime();
            } else {
                totalPa = Integer.parseInt(temp);
                totalPages += totalPa;
            }
            cell = sheet.getRow(i).getCell(5);
            costPerMins.add(cell.getStringCellValue().replace("#", ""));
            cell = sheet.getRow(i).getCell(6);
            fileQtys.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(7);
            outputFileFormat.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(8);
            assignedTo.add(cell.getStringCellValue());
        }
    }

    private static void getEmailCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("ALLOCATORNAME");
        password = properties.getProperty("ALLOCATORPASSWORD");
    }

    private String getETAT() {
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, getDate());
        String newDate = sdf.format(cal.getTime());
        return newDate;
    }

    private String getFTAT() {
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, getDate());
        String newDate = sdf.format(cal.getTime());
        return newDate;
    }

    private int getDate() {
        int difference = 0;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String lDate = sf.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cetatTime));
            //System.out.println(lDate);
            Date date = new Date();
            long dates = getDaysBetweenDates(sf.parse(lDate), sf.parse(sf.format(date.getTime())));

            if (dates != 0) {
                difference = (int) dates / 2;
            }
        } catch (Exception ex) {

        }
        return difference;
    }


    public long getDaysBetweenDates(Date d1, Date d2) {
        return TimeUnit.MILLISECONDS.toDays(d1.getTime() - d2.getTime());
    }

    private String checkStatus(int data1, int data2, String message) {
        String status;
        System.out.println(message);
        if (data1 == data2) {
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

    private String checkStatus(String data1, String data2, String message) {
        String status;
        System.out.println(message);
        if (data1.equals(data2)) {
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

    private String checkStatus(double data1, double data2, String message) {
        String status;
        System.out.println(message);
        if (data1 == data2) {
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
