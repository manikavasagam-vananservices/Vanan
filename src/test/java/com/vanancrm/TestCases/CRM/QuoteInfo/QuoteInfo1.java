package com.vanancrm.TestCases.CRM.QuoteInfo;

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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuoteInfo1 extends TestBase {

    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private String ticketID = "";

    private String channel = "Quick Quote";
    private String comment = "Automation Testing";
    private String service = "";

    private static List<String> fileNames = new ArrayList<String>();
    private static List<String> sourceLangs = new ArrayList<String>();
    private static List<String> targetLangs = new ArrayList<String>();
    private static List<String> services = new ArrayList<String>();
    private static List<String> pageMinutes = new ArrayList<String>();
    private static List<String> costPerMins = new ArrayList<String>();
    private static List<String> comments = new ArrayList<String>();
    private static List<String> fileQtys = new ArrayList<String>();
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
        viewTicketDetails = readTableData.clickOldTableService(service,
                (1));
        int min = 0;
        if (viewTicketDetails
                .getRunTimeTicketFieldValues("Channel")
                .contains(channel)) {

            double totalUnitPrice = 0.00f;
            double transcationFee = 0.00f;
            double orderCost = 0.00f;
            double temp = 0.00f;
            System.out.println("Ticket ID: " + ticketID);
            QuoteInfo quoteInfo = menus.clickQuoteInfo();
            quoteInfo.clickPopUpCloseButton();
            for (int i = 0; i < fileNames.size(); i++) {
                boolean status = false;
                if (pageMinutes.get(i).contains("-")) {
                    String lengths[] = pageMinutes.get(i).split("-");
                    if (!lengths[0].equals("00")) {
                        min = (int) Math.round(Integer.parseInt(lengths[0]) * 60);
                        min += Integer.parseInt(lengths[1]);
                    } else {
                        min = Integer.parseInt(lengths[1]);
                    }
                    if (!lengths[2].equals("00")) {
                        min += 1;
                    }
                    System.out.println("#########" + min);
                    status = true;
                } else {
                    status = false;
                    min = Integer.parseInt(pageMinutes.get(i));
                }

                String mp = "";
                if (!services.get(i).contains("Voice Over")) {
                    mp = "";
                } else {
                    mp = pageMinutes.get(i);
                }
                quoteInfo.setFileDetails(fileNames.get(i), sourceLangs.get(i),
                        targetLangs.get(i), services.get(i), mp, costPerMins.get(i), comments.get(i),
                        status);
                if (!services.get(i).contains("Captioning")) {

                    quoteInfo.clickUpdateQuoteInfo();
                }
                if (fileNames.get(i).contains("AutomationTesting4.wav") && services.get(i).contains("Transcription")) {

                    quoteInfo.clickUpdateQuoteInfo();
                }

                if (pageMinutes.get(i).contains("-")) {
                    totalUnitPrice = (double) (min * Integer.parseInt(costPerMins.get(i)));
                } else {
                    totalUnitPrice = (double) (Integer.parseInt(pageMinutes.get(i)) * Integer.parseInt(costPerMins.get(i)));
                }
                DecimalFormat decimalFormat = new DecimalFormat(".##");
                temp += totalUnitPrice;
                transcationFee = Double.parseDouble(decimalFormat.format(temp * 0.05));
                orderCost = temp + transcationFee;

                cell = sheet.getRow(i + 1).getCell(8);
                cell.setCellValue(temp);
                cell = sheet.getRow(i + 1).getCell(9);
                cell.setCellValue(checkStatus(temp, quoteInfo.getTotal(), "Total Unit Cost"));

                cell = sheet.getRow(i + 1).getCell(10);
                cell.setCellValue(0);
                cell = sheet.getRow(i + 1).getCell(11);
                cell.setCellValue(checkStatus(0, quoteInfo.getPromoDiscount(), "Discount Cost"));

                cell = sheet.getRow(i + 1).getCell(12);
                cell.setCellValue(0);
                cell = sheet.getRow(i + 1).getCell(13);
                cell.setCellValue(checkStatus(0, quoteInfo.getExpediteFee(), "Expedite Fee"));

                cell = sheet.getRow(i + 1).getCell(14);
                cell.setCellValue(temp);
                cell = sheet.getRow(i + 1).getCell(15);
                cell.setCellValue(checkStatus(temp, quoteInfo.getSubTotal(), "Sub Total"));

                cell = sheet.getRow(i + 1).getCell(16);
                cell.setCellValue(transcationFee);
                cell = sheet.getRow(i + 1).getCell(17);
                cell.setCellValue(checkStatus(transcationFee, quoteInfo.getTransactionFee(), "Transaction Fee"));

                cell = sheet.getRow(i + 1).getCell(18);
                cell.setCellValue(orderCost);
                cell = sheet.getRow(i + 1).getCell(19);
                cell.setCellValue(checkStatus(orderCost, quoteInfo.getOrderValue(), "Order Value"));
                if (services.get(i).contains("Captioning")) {
                    quoteInfo.clickAction(fileNames.get(i));
                }

                if (!services.get(i).contains("Captioning")) {
                    quoteInfo.clickPreviewEmail();
                    String[] messages1 = {sourceLangs.get(i), targetLangs.get(i), services.get(i), fileQtys.get(i), min + "", costPerMins.get(i), totalUnitPrice + ""};
                    cell = sheet.getRow(i + 1).getCell(20);
                    if(quoteInfo.checkQuoteEmailContent(messages1, fileNames.get(i))) {
                        cell.setCellValue("Pass");
                    } else {
                        cell.setCellValue("Fail");
                    }
                    //System.out.println("@@@@@@@@@@@@" + quoteInfo.checkQuoteEmailContent(messages1, fileNames.get(i)));
                    cell = sheet.getRow(i + 1).getCell(21);
                    if(quoteInfo.checkQuoteEmailPriceDetails("Total", String.format("%.2f", temp))
                            && quoteInfo.checkQuoteEmailPriceDetails("Transaction Fee", String.format("%.2f", transcationFee))
                            && quoteInfo.checkQuoteEmailPriceDetails("Order Total", String.format("%.2f", orderCost))) {
                        cell.setCellValue("Pass");
                    } else {
                        cell.setCellValue("Fail");
                    }
                    /*System.out.println("!!!!!!!!!!!!!!!!!!!!!" + quoteInfo.checkQuoteEmailPriceDetails("Total", String.format("%.2f", temp)));
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!" + quoteInfo.checkQuoteEmailPriceDetails("Transaction Fee", String.format("%.2f", transcationFee)));
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!" + quoteInfo.checkQuoteEmailPriceDetails("Order Total", String.format("%.2f", orderCost)));*/
                    quoteInfo.clickPreviewMailPopUpCloseButton();
                    quoteInfo.scrollUp();
                }
                if (fileNames.get(i).contains("AutomationTesting4.wav") && services.get(i).contains("Transcription")) {
                    quoteInfo.clickPreviewEmail();
                    String[] messages1 = {sourceLangs.get(i), targetLangs.get(i), services.get(i), fileQtys.get(i), min + "", costPerMins.get(i), totalUnitPrice + ""};
                    cell = sheet.getRow(i + 1).getCell(20);
                    if(quoteInfo.checkQuoteEmailContent(messages1, fileNames.get(i))) {
                        cell.setCellValue("Pass");
                    } else {
                        cell.setCellValue("Fail");
                    }
                    //System.out.println("@@@@@@@@@@@@" + quoteInfo.checkQuoteEmailContent(messages1, fileNames.get(i)));
                    cell = sheet.getRow(i + 1).getCell(21);
                    if(quoteInfo.checkQuoteEmailPriceDetails("Total", String.format("%.2f", temp))
                            && quoteInfo.checkQuoteEmailPriceDetails("Transaction Fee", String.format("%.2f", transcationFee))
                            && quoteInfo.checkQuoteEmailPriceDetails("Order Total", String.format("%.2f", orderCost))) {
                        cell.setCellValue("Pass");
                    } else {
                        cell.setCellValue("Fail");
                    }
                    /*System.out.println("!!!!!!!!!!!!!!!!!!!!!" + quoteInfo.checkQuoteEmailPriceDetails("Total", String.format("%.2f", temp)));
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!" + quoteInfo.checkQuoteEmailPriceDetails("Transaction Fee", String.format("%.2f", transcationFee)));
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!" + quoteInfo.checkQuoteEmailPriceDetails("Order Total", String.format("%.2f", orderCost)));*/
                    quoteInfo.clickPreviewMailPopUpCloseButton();
                    quoteInfo.scrollUp();
                }
                quoteInfo.clickMoveToLocation();
            }
        }
        workbook.write(fileOutput);
        fileOutput.close();
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

        file = new File("src/test/resources/QuoteInfo1.xls");
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
            comments.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(7);
            fileQtys.add(cell.getStringCellValue());

        }
    }

    private static void getEmailCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("SALESNAME");
        password = properties.getProperty("SALESPASSWORD");
    }
}
