package com.vanancrm.TestCases.CRM.FileInfo;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;
import com.vanan.CRM.PageObjects.WholeSitePages.*;
import com.vanancrm.Common.TestBase;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FileInfo1 extends TestBase {

    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private String ticketID = "VS00344186";

    private String channel = "Quote";
    private String comment = "Automation Testing";
    private String mailId = "naveen@vananservices.com";
    private String service = "Voice Over";

    private static List<String> fileNames = new ArrayList<String>();
    private static List<String> fileTypes = new ArrayList<String>();
    private static List<String> pagelengths = new ArrayList<String>();
    private static List<String> qualitys = new ArrayList<String>();
    private static List<String> qualityCategorys = new ArrayList<String>();
    private static List<String> speakerCounts = new ArrayList<String>();
    private static List<String> misComments = new ArrayList<String>();

    private Edit edit;
    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    @Test
    public void testStep() throws IOException, InterruptedException, AWTException, ParseException {

        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        readTableData = menus.clickNewMenu();
        menus.searchCustomerDetails(ticketID);
        viewTicketDetails = new ViewTicketDetails(driver);
        viewTicketDetails = readTableData.clickOldTableService(service,
                (1));
        if (viewTicketDetails.getRunTimeTicketFieldValues("Email")
                .contains(mailId) && viewTicketDetails
                .getRunTimeTicketFieldValues("Channel")
                .contains(channel)) {

            System.out.println("Ticket ID: " + ticketID);
            edit = menus.clickEdit();
            waitForProcessCompletion(15);
            edit.clickFileUploadButton();
            FileUploadFromTicket fileUploadFromTicket = new FileUploadFromTicket
                    (driver);
            String parentWindow = driver.getWindowHandle();
            Set<String> handles = driver.getWindowHandles();
            for (String windowHandle : handles) {
                if (!windowHandle.equals(parentWindow)) {
                    driver.switchTo().window(windowHandle);
                    waitForProcessCompletion(5);
                    for (int i = 0; i < fileNames.size(); i++) {
                        fileUploadFromTicket.uploadFile(driver, fileNames.get(i), "");
                    }

                    waitForProcessCompletion(15);
                    fileUploadFromTicket.enterComments(comment);
                    fileUploadFromTicket.clickSubmitButton();
                    waitForProcessCompletion(15);
                    driver.close();
                    driver.switchTo().window(parentWindow);
                    break;
                }
            }
            waitForProcessCompletion(10);
            edit.selectPaymentType("Full payment");
            edit.selectPaymentMode("Square");
            edit.clickUpdateButton();
            menus.clickFileInfo();
            FileInfo fileInfo = new FileInfo(driver);
            waitForProcessCompletion(15);

            int temp = 0;
            long totalLength = 0;
            sheet = workbook.getSheetAt(0);

            for (int i = 0; i < fileNames.size(); i++) {
                System.out.println((i + 1) + "==" + fileNames.get(i) + "==" + fileTypes.get(i) + "==" +
                        pagelengths.get(i) + "==" + qualitys.get(i) + "==" + qualityCategorys.get(i) + "==" + speakerCounts.get(i) + "==" + misComments.get(i));
                fileInfo.setFileDetails(fileNames.get(i), fileTypes.get(i),
                        pagelengths.get(i), qualitys.get(i), qualityCategorys.get(i), speakerCounts.get(i), misComments.get(i));
                fileInfo.clickUpdateFileInfo();
                waitForProcessCompletion(10);

                if (pagelengths.get(i).contains("-")) {
                    String selectedLength = fileInfo.getSelectedTotalNoOfLength();
                    String length = fileInfo.getTotalNoOfLength();
                    String leng = pagelengths.get(i).replace("-" ,":");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date date = timeFormat.parse(leng);
                    totalLength += date.getTime();
                    cell = sheet.getRow(i + 1).getCell(7);
                    cell.setCellValue("-");
                    cell = sheet.getRow(i + 1).getCell(8);
                    cell.setCellValue("-");
                    cell = sheet.getRow(i + 1).getCell(9);
                    cell.setCellValue(length);
                    cell = sheet.getRow(i + 1).getCell(10);
                    cell.setCellValue(checkStatus(length, timeFormat.format(new Date(totalLength)), "Total No. of Length"));
                    cell = sheet.getRow(i + 1).getCell(11);
                    cell.setCellValue("-");
                    cell = sheet.getRow(i + 1).getCell(12);
                    cell.setCellValue("-");
                    cell = sheet.getRow(i + 1).getCell(13);
                    cell.setCellValue(selectedLength);
                    cell = sheet.getRow(i + 1).getCell(14);
                    cell.setCellValue(checkStatus(selectedLength, timeFormat.format(new Date(totalLength)), "Total No. of Length Selected"));
                } else {

                    int selectedPages = Integer.parseInt(fileInfo.getSelectedTotalNoOfPages());
                    int pages = Integer.parseInt(fileInfo.getTotalNoOfPages());
                    temp += Integer.parseInt(pagelengths.get(i));

                    cell = sheet.getRow(i + 1).getCell(7);
                    cell.setCellValue(pages);
                    cell = sheet.getRow(i + 1).getCell(8);
                    cell.setCellValue(checkStatus(pages, temp, "Total No. of Pages"));
                    cell = sheet.getRow(i + 1).getCell(9);
                    cell.setCellValue("-");
                    cell = sheet.getRow(i + 1).getCell(10);
                    cell.setCellValue("-");
                    cell = sheet.getRow(i + 1).getCell(11);
                    cell.setCellValue(selectedPages);
                    cell = sheet.getRow(i + 1).getCell(12);
                    cell.setCellValue(checkStatus(selectedPages, temp, "Total No. of Pages Selected"));
                    cell = sheet.getRow(i + 1).getCell(13);
                    cell.setCellValue("-");
                    cell = sheet.getRow(i + 1).getCell(14);
                    cell.setCellValue("-");
                }
            }

            EmailConversation emailConversation = menus.clickEmailConversation();
            waitForProcessCompletion(5);
            emailConversation.openAllEmailContents();
            waitForProcessCompletion(5);
            String status = "";
            for (int i = fileNames.size()-1; i >= 0; i--) {
                String tmp = emailConversation.readAllEmailContentForFileInfo(fileNames.get(i));
                if(pagelengths.get(i).contains("-")) {
                    String lengths[]= pagelengths.get(i).split("-");
                    if(!lengths[0].equals("00")) {
                        if(!checkEmailContentStatus(tmp, "F.No. " + (i+1) + " - "
                                + fileNames.get(i), "File Name").contains("Fail") && !checkEmailContentStatus(tmp, "Hrs : ' ' To " + lengths[0], "Pages").contains("Fail")
                                && !checkEmailContentStatus(tmp, "Mins : ' ' To " + lengths[1], "Pages").contains("Fail")
                                && !checkEmailContentStatus(tmp, "Secs : ' ' To " + lengths[2], "Pages").contains("Fail")
                                &&
                                !checkEmailContentStatus(tmp, "Quality : To " + qualitys.get(i), "Quality").contains("Fail") &&
                                !checkEmailContentStatus(tmp, "Quality Category : To " + qualityCategorys.get(i), "Quality Category").contains("Fail") &&
                                !checkEmailContentStatus(tmp, "Speakers Change : To " + speakerCounts.get(i), "Speakers Change").contains("Fail") &&
                                !checkEmailContentStatus(tmp, "Mis Comment : ' ' To " + misComments.get(i), "Mis Comment").contains("Fail")) {
                            status = "pass";
                        } else {
                            status = "fail";
                        }
                    } else {
                        if(!checkEmailContentStatus(tmp, "F.No. " + (i+1) + " - "
                                + fileNames.get(i), "File Name").contains("Fail")
                                && !checkEmailContentStatus(tmp, "Mins : ' ' To " + lengths[1], "Pages").contains("Fail")
                                && !checkEmailContentStatus(tmp, "Secs : ' ' To " + lengths[2], "Pages").contains("Fail")
                                &&
                                !checkEmailContentStatus(tmp, "Quality : To " + qualitys.get(i), "Quality").contains("Fail") &&
                                !checkEmailContentStatus(tmp, "Quality Category : To " + qualityCategorys.get(i), "Quality Category").contains("Fail") &&
                                !checkEmailContentStatus(tmp, "Speakers Change : To " + speakerCounts.get(i), "Speakers Change").contains("Fail") &&
                                !checkEmailContentStatus(tmp, "Mis Comment : ' ' To " + misComments.get(i), "Mis Comment").contains("Fail")) {
                            status = "pass";
                        } else {
                            status = "fail";
                        }
                    }

                } else {

                    if(!checkEmailContentStatus(tmp, "F.No. " + (i+1) + " - "
                            + fileNames.get(i), "File Name").contains("Fail") && !checkEmailContentStatus(tmp, "Pages : ' ' To " + pagelengths.get(i), "Pages").contains("Fail") &&
                            !checkEmailContentStatus(tmp, "Quality : To " + qualitys.get(i), "Quality").contains("Fail") &&
                            !checkEmailContentStatus(tmp, "Quality Category : To " + qualityCategorys.get(i), "Quality Category").contains("Fail") &&
                            !checkEmailContentStatus(tmp, "Speakers Change : To " + speakerCounts.get(i), "Speakers Change").contains("Fail") &&
                            !checkEmailContentStatus(tmp, "Mis Comment : ' ' To " + misComments.get(i), "Mis Comment").contains("Fail")) {
                        status = "pass";
                    } else {
                        status = "fail";
                    }
                }
                //System.out.println(")))))))))))" + status + "(((((((((((((");
                cell = sheet.getRow(i + 1).getCell(15);
                cell.setCellValue(status);

            }
            workbook.write(fileOutput);
            fileOutput.close();
        }
        menus.clickSignOut();
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

    private String checkEmailContentStatus(String data1, String data2, String message) {
        String status;
        System.out.println(message);
        if (data1.contains(data2)) {
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

    @BeforeClass
    public void beforeClass() throws IOException {

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

        //driver.quit();
    }

    public static void readFileInfoData() throws IOException {

        file = new File("src/test/resources/Fileinfo1.xls");
        fileInput = new FileInputStream(file);
        workbook = new HSSFWorkbook(fileInput);
        sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            cell = sheet.getRow(i).getCell(0);
            fileNames.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(1);
            fileTypes.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(2);
            pagelengths.add(cell.getStringCellValue().replace("#", ""));
            cell = sheet.getRow(i).getCell(3);
            qualitys.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(4);
            qualityCategorys.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(5);
            speakerCounts.add(cell.getStringCellValue().replace("#", ""));
            cell = sheet.getRow(i).getCell(6);
            misComments.add(cell.getStringCellValue());

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
