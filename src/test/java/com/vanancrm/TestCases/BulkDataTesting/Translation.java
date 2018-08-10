package com.vanancrm.TestCases.BulkDataTesting;

import com.vanancrm.Common.TestBase;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Translation extends TestBase {

    private static java.util.List<String> fileTypes = new ArrayList<String>();
    private static java.util.List<Double> minutes = new ArrayList<Double>();
    private static java.util.List<Double> hours = new ArrayList<Double>();
    private static java.util.List<String> sourceLanguages = new ArrayList<String>();
    private static java.util.List<String> targetLanguages = new
            ArrayList<String>();
    private static java.util.List<String> transactionLanguages = new ArrayList<String>();
    private static java.util.List<String> timeCodes = new ArrayList<String>();
    private static java.util.List<String> notarizations = new ArrayList<String>();
    private static java.util.List<String> nativeSpeakers = new
            ArrayList<String>();
    private static java.util.List<String> certificates = new ArrayList<String>();
    private static java.util.List<String> additionalQC = new ArrayList<String>();
    private static java.util.List<String> reqMailCopies = new ArrayList<String>();
    private static java.util.List<String> fileUploads = new ArrayList<String>();
    private static java.util.List<String> emailIds = new ArrayList<String>();
    private static java.util.List<String> getQutoes = new ArrayList<String>();

    private static java.util.List<String> uiComponents = new ArrayList<String>();
    private static java.util.List<String> keys = new ArrayList<String>();
    private static java.util.List<String> locators = new ArrayList<String>();
    private static java.util.List<String> elements = new ArrayList<String>();
    private static java.util.List<String> jscripts = new
            ArrayList<String>();
    private static java.util.List<String> pages = new ArrayList<String>();
    private WebDriver driver;
    private WebElement element;

    @Test
    public void translationServices() throws Exception {

        System.out.println("======================================");
        System.out.println("Scenario Started");
        System.out.println("======================================");
        driver.get("https://vananservices.com/Translation-Quote.php");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        waitForProcessCompletion(10);
        System.out.println("1 ."+js.executeScript("document.getElementById" +
                "('hours')"));
        System.out.println("2 ."+js.executeScript("$('#minutes').is(':visible')"));
        System.out.println("3 ."+js.executeScript("$('#first5').is(':visible')"));
        /*System.out.println("4 ."+js.executeScript("$('#').is(':visible')"));
        System.out.println("5 ."+js.executeScript("$('#').is(':visible')"));
        System.out.println("6 ."+js.executeScript("$('#').is(':visible')"));
        System.out.println("7 ."+js.executeScript("$('#').is(':visible')"));
        System.out.println("8 ."+js.executeScript("$('#').is(':visible')"));
        System.out.println("9 ."+js.executeScript("$('#').is(':visible')"));
        System.out.println("10 ."+js.executeScript("$('#').is(':visible')"));*/

        //checkAudioVideoFileType();
        System.out.println("======================================");
        System.out.println("Test Completed");
        System.out.println("======================================");
    }

    @BeforeClass
    public void beforeClass() throws IOException {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        file = new File("src/test/resources/Translation.xls");
        fileInput = new FileInputStream(file);
        workbook = new HSSFWorkbook(fileInput);

        fullScreen(driver);
    }

    @AfterClass
    public void afterClass() throws IOException {

        screenshot(driver, "TranslationQuote");
        //driver.quit();
    }

    private void checkAudioVideoFileType() throws IOException {
        getDataFromExcel();
        getElementFromExcel();
        driver.get("https://vananservices.com/Translation-Quote.php");
        for (int i = 0; i < 3; i++) {
            System.out.println((i+1)+"|" + fileTypes.get(i) + "|");
            for (int j = 0; j < uiComponents.size() - 2; j++) {
                System.out.println("|" + uiComponents.get(j) + "|");
                switch (j) {
                    case 0:
                        checkElement(keys.get(j), locators.get(j), elements.get(j),
                                uiComponents.get(j), fileTypes.get(i), jscripts.get(j));
                        break;
                    case 1:
                        checkElement(keys.get(j), locators.get(j), elements.get(j),
                                uiComponents.get(j), String.valueOf(hours.get
                                        (i).intValue()), jscripts.get(j));
                        break;
                    case 2:
                        checkElement(keys.get(j), locators.get(j), elements.get(j),
                                uiComponents.get(j), String.valueOf(minutes.get
                                        (i).intValue()), jscripts.get(j));
                        break;
                    case 3:
                        checkElement(keys.get(j), locators.get(j), elements.get(j),
                                uiComponents.get(j), sourceLanguages.get(i), jscripts.get(j));
                        break;
                    case 4:
                        checkElement(keys.get(j), locators.get(j), elements.get(j),
                                uiComponents.get(j), targetLanguages.get(i), jscripts.get(j));
                        break;
                    case 5:
                    case 6:
                        if (nativeSpeakers.get(i).equals("Yes")) {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), nativeSpeakers.get(i), jscripts.get(j));
                        } else {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), nativeSpeakers.get((i)), jscripts
                                            .get(j));
                        }
                        break;
                    case 7:
                        if (nativeSpeakers.get(i).equals("Yes")) {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), nativeSpeakers.get(i), jscripts.get(j));
                        }
                        break;
                    case 8:
                    case 9:
                        if (timeCodes.get(i).equals("Yes")) {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), timeCodes.get(i), jscripts.get(j));
                        } else {
                            checkElement(keys.get((j + 1)), locators.get((j + 1)), elements.get
                                            ((j + 1)), uiComponents.get((j + 1)), timeCodes.get((i)),
                                    jscripts.get((j + 1)));
                        }
                        break;
                    case 10:
                        if (notarizations.get(i).equals("Yes")) {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), notarizations.get(i), jscripts.get(j));
                        } else {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), notarizations.get((i)), jscripts
                                            .get(j));
                        }
                        break;
                    case 11:
                        if (certificates.get(i).equals("Yes")) {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), certificates.get(i), jscripts.get(j));
                        } else {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), certificates.get((i)), jscripts
                                            .get(j));
                        }
                        break;
                    case 12:
                    case 13:
                        if (additionalQC.get(i).equals("Yes")) {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), additionalQC.get(i), jscripts.get(j));
                        } else {
                            /*checkElement(keys.get((j + 1)), locators.get((j +
                                            1)), elements.get
                                    ((j + 1)), uiComponents.get((j + 1)), additionalQC.get((i)),
                                    jscripts.get((j + 1)));*/
                        }
                        break;
                    case 14:
                        if (fileUploads.get(i).equals("Yes")) {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), fileUploads.get(i), jscripts.get(j));
                        } else {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), fileUploads.get((i)), jscripts
                                            .get(j));
                        }
                        break;
                    case 15:
                        checkElement(keys.get(j), locators.get(j), elements.get(j),
                                uiComponents.get(j), emailIds.get((i)), jscripts
                                        .get(j));

                        break;
                    case 16:
                        if (getQutoes.get(i).equals("Yes")) {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), getQutoes.get(i), jscripts.get(j));
                        } else {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), getQutoes.get((i)), jscripts
                                            .get(j));
                        }
                        break;
                    case 17:

                    case 18:
                        if (reqMailCopies.get(i).equals("Yes")) {
                            checkElement(keys.get(j), locators.get(j), elements.get(j),
                                    uiComponents.get(j), reqMailCopies.get(i), jscripts.get(j));
                            checkElement(keys.get((j + 1)), locators.get((j + 1)), elements.get
                                            ((j + 1)), uiComponents.get((j + 1)), reqMailCopies.get((i)),
                                    jscripts.get((j + 1)));
                            checkElement(keys.get((j + 2)), locators.get((j + 1)), elements
                                    .get((j + 2)), uiComponents.get((j + 1)), reqMailCopies.get
                                    ((i)), jscripts.get((j + 2)));
                        } else {
                            checkElement(keys.get((j + 1)), locators.get((j + 1)), elements.get
                                            ((j + 1)), uiComponents.get((j + 1)), reqMailCopies.get((i)),
                                    jscripts.get((j + 1)));
                        }
                        break;
                }
            }
        }
    }

    private void getDataFromExcel() throws IOException {

        sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            cell = sheet.getRow(i).getCell(1);
            fileTypes.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(2);
            minutes.add(cell.getNumericCellValue());
            cell = sheet.getRow(i).getCell(3);
            hours.add(cell.getNumericCellValue());
            cell = sheet.getRow(i).getCell(4);
            sourceLanguages.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(5);
            targetLanguages.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(6);
            timeCodes.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(7);
            notarizations.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(8);
            nativeSpeakers.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(9);
            certificates.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(10);
            additionalQC.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(11);
            reqMailCopies.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(12);
            fileUploads.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(13);
            emailIds.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(14);
            getQutoes.add(cell.getStringCellValue());
        }
    }

    private void getElementFromExcel() throws IOException {

        sheet = workbook.getSheetAt(1);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            cell = sheet.getRow(i).getCell(0);
            uiComponents.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(1);
            keys.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(2);
            locators.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(3);
            elements.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(4);
            jscripts.add(cell.getStringCellValue());
        }
    }

    private void checkElement(String key, String locators, String elementPath,
                              String field, String data, String javaPara) {
        if (!javaPara.equals("-")) {
            element = getElement(locators, elementPath);
            JavascriptExecutor js = (JavascriptExecutor) driver;

            switch (key) {

                case "Select":

                    try {
                        System.out.println(js.executeScript("$('#" +
                                elementPath + "').is(':visible');")
                                +"$$$$$$$$$$$");
                        if(js.executeScript("$('#" + elementPath + "').is(':visible')")
                                .toString()
                                .equals("true")) {
                            System.out.println("#############t");
                        }
                        if (element.isDisplayed()) {

                            Select dropDown = new Select(element);
                            dropDown.selectByVisibleText(data);
                            driver.findElement(By.tagName("body")).click();
                        } else {
                            System.out.println("Element is not visible " + field);
                        }

                    } catch (Exception e) {
                        System.out.println("Element is not visible / not " +
                                "selectable" + field +"="+ e);
                    }
                    break;

                case "Enter":

                    try {
                        if (element.isDisplayed()) {

                            element.clear();
                            element.sendKeys(data);
                        } else {
                            System.out.println("Element is not visible " + field);
                        }

                    } catch (Exception e) {
                        System.out.println("Element is not visible / not " +
                                " enter a data " + field +"="+ e);
                    }
                    break;

                case "Radio1":
                    try {
                        if (!element.isSelected()) {
                            js.executeScript("$('#" + javaPara + "').click();");
                        } else {

                        }


                    } catch (Exception e) {
                        System.out.println("Element is not visible / not " +
                                " select " + field +"="+ e);
                    }
                    break;

                case "Radio0":

                    try {
                        if (element.isSelected()) {
                            js.executeScript("$('#" + javaPara + "').click();");
                        }


                    } catch (Exception e) {
                        System.out.println("Element is not visible / not " +
                                " deselect " + field +"="+ e);
                    }
                    break;
                case "Check":
                case "Click":
                    try {
                        if (element.isDisplayed()) {
                            js.executeScript("$('#" + javaPara + "').click();");
                        }


                    } catch (Exception e) {
                        System.out.println("Element is not visible / not " +
                                " select " + field +"="+ e);
                    }
                    break;
                case "Upload":
                    try {
                        String fileName = "Translation.txt";
                        String filePath = System.getProperty("user.dir") + "/" + fileName;
                        File file = new File(filePath);
                        file.createNewFile();
                        TimeUnit.SECONDS.sleep(10);
                        element.click();
                        StringSelection selection = new StringSelection(fileName);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(selection, selection);

                        Robot robot = new Robot();
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.keyRelease(KeyEvent.VK_ENTER);
                    } catch (Exception e) {
                        System.out.println("Unable to upload a file" + field);
                    }
                    break;
                default:
                    System.out.println("Given Key is not available for " +
                            "processing");
                    break;
            }
        }
    }

    private WebElement getElement(String locators, String elementPath) {

        switch (locators) {
            case "id":
                element = driver.findElement(By.id(elementPath));
                break;

            case "linkText":
                element = driver.findElement(By.linkText(elementPath));
                break;

            case "partialLinkText":
                element = driver.findElement(By.partialLinkText(elementPath));
                break;

            case "name":
                element = driver.findElement(By.name(elementPath));
                break;

            case "tagName":
                element = driver.findElement(By.tagName(elementPath));
                break;

            case "xpath":
                element = driver.findElement(By.xpath(elementPath));
                break;

            case "className":
                element = driver.findElement(By.className(elementPath));
                break;

            case "cssSelector":
                element = driver.findElement(By.cssSelector(elementPath));
                break;

            default:
                System.out.println("Given locator is not available for " +
                        "processing");
                break;
        }
        return element;
    }
}
