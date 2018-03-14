package com.vanancrm.TestCases.General;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vanancrm.Common.TestBase;

import com.vanancrm.PageObjects.MainPages.TrackOrder;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class TrackOrderFromSites extends TestBase {

    private WebDriver driver;
    private TrackOrder trackOrder;

    private List<String> orderIds = new ArrayList<>();
    private List<String> emailIds = new ArrayList<>();
    private List<String> status = new ArrayList<>();
    private List<String> dates = new ArrayList<>();

    private DateFormat dateFormat;
    private Date date;

    @Test
    public void checkTrackOrder() throws IOException {

        driver.get(System.getProperty("website"));

        trackOrder = new TrackOrder(driver);
        for (int i = 0; i < orderIds.size(); i++) {

            System.out.println("Testing Email : " + emailIds.get(i) + ", " +
                    "Ticket id : " + orderIds.get(i) + " ");
            enterData(emailIds.get(i), orderIds.get(i));
            checkMessageDisplayed();
            System.out.println(trackOrder.getOrderId() + " : " + trackOrder
                    .getOrderDate() + " : " + trackOrder.getOrderStatus() +
                    " : " + trackOrder.getOrderDeliveredStatus() + " : " +
                    trackOrder.getOrderInProgressStatus());
            int pcount = 0;
            if (trackOrder.getOrderId().contains(orderIds.get(i))) {

                System.out.println(orderIds.get(i) + " :\tOrder Id is correct");
                pcount = pcount + 1;
            } else {

                System.out.println(orderIds.get(i) + " :\tmissmatched order id");
            }

            if (status.get(i).equals("In Process")) {

                if (trackOrder.getOrderInProgressStatus().contains("active")) {
                    pcount = pcount + 1;
                    System.out.println("In progress status is correct");
                } else {
                    System.out.println("In progress status is wrong");
                }
                if (trackOrder.getOrderDeliveredStatus().contains("disable")) {
                    pcount = pcount + 1;
                    System.out.println("Delivery status is correct");
                } else {
                    System.out.println("Delivery status is wrong");
                }
                if (trackOrder.getOrderStatus().contains(status.get(i))) {
                    pcount = pcount + 1;
                    System.out.println(trackOrder.getOrderStatus() +
                            " :\tcorrect");
                } else {
                    System.out.println(trackOrder.getOrderStatus() +
                            " :\twrong");
                }
            }
            if (status.get(i).equals("Delivered")) {

                if ((trackOrder.getOrderInProgressStatus()).contains
                        ("disble")) {
                    pcount = pcount + 1;
                    System.out.println("In progress status is correct");
                } else {
                    System.out.println("In progress status is wrong");
                }
                if ((trackOrder.getOrderDeliveredStatus()).contains
                        ("active")) {
                    pcount = pcount + 1;
                    System.out.println("Delivery status is correct");
                } else {
                    System.out.println("Delivery status is wrong");
                }
                if ((trackOrder.getOrderStatus()).contains(status.get(i))) {
                    pcount = pcount + 1;
                    System.out.println(trackOrder.getOrderStatus() +
                            " :\tcorrect");
                } else {

                    System.out.println(trackOrder.getOrderStatus() +
                            " :\twrong");
                }

            }
            if (trackOrder.getOrderDate().contains(dates.get(i))) {
                pcount = pcount + 1;
                System.out.println(trackOrder.getOrderDate() +
                        " :\tdate is correct");
            } else {
                System.out.println(trackOrder.getOrderDate() +
                        " :\tdate is wrong");
            }
            System.out.println("\n");
            cell = sheet.getRow(i + 1).getCell(4);
            if(pcount == 5) {
                cell.setCellValue("Pass");
            } else {
                cell.setCellValue("Fail");
            }
            date = new Date();
            cell = sheet.getRow(i + 1).getCell(5);
            cell.setCellValue(dateFormat.format(date));
        }
        workbook.write(fileOutput);
        fileOutput.close();
        System.out.println("Test Empty scenario : #1(Both are empty)");
        enterData("", "");
        checkMessageDisplayed();

        System.out.println("Test Empty scenario : #2 (ticket id empty)");
        enterData("", "2323423");
        checkMessageDisplayed();

        System.out.println("Test Empty scenario : #3(email id empty)");
        enterData("test@vananservices.com", "");
        checkMessageDisplayed();

        System.out.println(
                "=============================================================================");
        System.out.println("Test Completed");
        System.out.println(
                "=============================================================================");
    }

    @BeforeClass
    public void beforeClass() throws IOException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        fullScreen(driver);
        file = new File("src/test/resources/Trackorder.xls");
        fileInput = new FileInputStream(file);
        workbook = new HSSFWorkbook(fileInput);
        sheet = workbook.getSheetAt(0);
        fileOutput = new FileOutputStream(file);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        readInputData();
    }

    private void readInputData() throws IOException {

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            cell = sheet.getRow(i).getCell(0);
            emailIds.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(1);
            orderIds.add(String.format("%.0f", cell.getNumericCellValue()));
            cell = sheet.getRow(i).getCell(2);
            status.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(3);
            dates.add(cell.getStringCellValue());
        }

    }


    private void checkMessageDisplayed() {

        if (trackOrder.isWarningMessageDisplayed()) {
            System.out.println("Warning message displayed");
        } else {
            System.out.println("Warning message not displayed");
        }
        if (trackOrder.isOrderTableDisplayed()) {
            System.out.println("Order table not displayed");
        } else {
            System.out.println("Order table is displayed");
        }
    }

    private void enterData(String email, String ticketid) {

        trackOrder.enterTicketId(ticketid);
        trackOrder.enterEmailId(email);
        trackOrder.clickSubmit();
        waitForProcessCompletion(5);
    }

    @AfterClass
    public void afterClass() {
        screenshot(driver,"TrackOrder");
        driver.quit();
    }

}
