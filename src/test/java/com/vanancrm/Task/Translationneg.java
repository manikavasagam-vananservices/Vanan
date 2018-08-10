package com.vanancrm.Task;

import com.vanancrm.Common.TestBase;
import com.vanancrm.PageObjects.MainPages.Translation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translationneg extends TestBase {

    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private String service = "Translation";

    private static List<String> sourceLanguages = new ArrayList<String>();
    private static List<String> targetLanguages = new ArrayList<String>();
    private static List<Double> unitCosts = new ArrayList<Double>();
    private static List<String> processings = new ArrayList<String>();
    private static List<Double> pages = new ArrayList<Double>();
    private static List<String> additionalQtys = new ArrayList<String>();
    private static List<String> certificationNotary = new ArrayList<String>();
    private static List<String> requestedMailCopies = new ArrayList<String>();
    private static List<String> totalOrders = new ArrayList<String>();

    private double totalUnitCost = 0;
    private double grandtotal = 0;
    private double transcationCost = 0;
    private double orderCost = 0;
    private double additionalQCCost = 0;
    private double certificationCost = 0;
    private double mailCost = 0;
    private double offerCost = 0;

    private String address = "Vanan";
    private String country = "United States";

    private String unitStatus = "";
    private String additionalQCStatus = "";
    private String totalUnitStatus = "";
    private String grandtotalStatus = "";
    private String transcationStatus = "";
    private String certificationStatus = "";
    private String orderStatus = "";
    private String mailStatus = "";

    private boolean offer = true;

    @Test
    public void testStep() throws IOException {

        driver.get("https://vananservices.com/Translation-Quote.php");
        sheet = workbook.getSheetAt(0);
        Translation translation = new Translation(driver);
        translation.selectFileType("Document");
        translation.enterDocumentType("testing");
        String letter = "";
        for (int i = 26; i < totalOrders.size(); i++) {
            dataSplit(totalOrders.get(i));
            String[] data = totalOrders.get(i).split(",");
            /*System.out.print("S.No " + (i + 1));
            System.out.print("Source language : " + sourceLanguages.get(i) + " ===" + totalOrders.size());
            System.out.print("Target language : " + targetLanguages.get(i));
            System.out.print("Unit costs : " + unitCosts.get(i));
            System.out.print("Processings : " + processings.get(i));*/

            for (int j = 0; j < data.length; j++) {
                for (int k = 0; k < data[j].length(); k++) {
                    char a = data[j].charAt(k);
                    if (Character.isDigit(a)) {

                    } else {
                        letter = letter + a;

                    }
                    callElement(letter, translation, sourceLanguages.get(i),
                            targetLanguages.get(i), (pages.get(i)).intValue() + "", additionalQtys.get(i),
                            certificationNotary.get(i), requestedMailCopies.get(i));
                    letter = "";
                }
            }
            DecimalFormat df2 = new DecimalFormat(".##");

            if (offer) {
                totalUnitCost = Double.valueOf(df2.format(Math.round((pages.get(i) * unitCosts.get(i)) * 100) / 100.f));

                System.out.println(totalUnitCost+"=>");
                if (totalUnitCost >= 1000) {
                    offerCost = Double.valueOf(df2.format(Math.round(totalUnitCost * 0.2) * 100 / 100.f));
                    //System.out.println(totalUnitCost+"=>1");
                } else if (totalUnitCost < 1000) {
                    offerCost = Double.valueOf(df2.format(Math.round((totalUnitCost * 0.1) * 100) / 100.f));
                    //System.out.println(totalUnitCost+"=>2");
                }

            } else {
                totalUnitCost = Double.valueOf(df2.format(Math.round((pages.get(i) * unitCosts.get(i)) * 100) / 100.f));
                offerCost = totalUnitCost;
            }

            if (additionalQtys.get(i).equals("Yes")) {
                additionalQCCost = pages.get(i) * 5;
            }

            if (certificationNotary.get(i).equals("Yes")) {
                if (sourceLanguages.get(i).equals("English")) {
                    certificationCost = 10;
                } else if (targetLanguages.get(i).equals("English")) {
                    certificationCost = 15;
                }
            }

            if (requestedMailCopies.get(i).equals("Yes")) {
                mailCost = 20;
            }


            grandtotal = (totalUnitCost - offerCost) + additionalQCCost + certificationCost + mailCost;
            transcationCost = Double.valueOf(df2.format(Math.round((grandtotal * 0.05) * 100) / 100.f));
            orderCost = grandtotal + transcationCost;
            unitStatus = checkStatus(unitCosts.get(i), translation.getActualCost(), "Unit cost");

            if (offer) {
                totalUnitStatus = checkStatus(offerCost, translation.getOfferFee(), "Offer discount cost");
            } else {
                totalUnitStatus = checkStatus(totalUnitCost, translation.getTranslationCost(), "Total Unit cost");
            }

            additionalQCStatus = checkStatus(additionalQCCost, translation.getAdditionalQualityAmount(), "Additional QC");
            if (sourceLanguages.get(i).equals("English")) {
                certificationStatus = checkStatus(certificationCost, translation.getCertificationFee(), "Certification cost");
            } else if (targetLanguages.get(i).equals("English")) {
                certificationStatus = checkStatus(certificationCost, translation.getNotaryFee()
                        + translation.getProcessFee(), "Notary and processing charge cost");
            }

            mailStatus = checkStatus(mailCost, translation.getMailingFee(), "Mailing cost");
            grandtotalStatus = checkStatus(grandtotal, translation.getSubTotal(), "Grand Total");
            transcationStatus = checkStatus(transcationCost, translation.getTransactionFee(), "Transaction fee");
            orderStatus = checkStatus(orderCost, translation.getGrandTotal(), "Order total");

            cell = sheet.getRow(i + 1).getCell(14);
            cell.setCellValue(totalUnitCost);

            if (offer) {
                cell = sheet.getRow(i + 1).getCell(15);
                cell.setCellValue(offerCost);
            }
            cell = sheet.getRow(i + 1).getCell(16);
            cell.setCellValue(additionalQCCost);
            cell = sheet.getRow(i + 1).getCell(17);
            cell.setCellValue(certificationCost);
            cell = sheet.getRow(i + 1).getCell(18);
            cell.setCellValue(mailCost);
            cell = sheet.getRow(i + 1).getCell(19);
            cell.setCellValue(grandtotal);
            cell = sheet.getRow(i + 1).getCell(20);
            cell.setCellValue(transcationCost);
            cell = sheet.getRow(i + 1).getCell(21);
            cell.setCellValue(orderCost);
            cell = sheet.getRow(i + 1).getCell(22);
            cell.setCellValue(unitStatus);
            cell = sheet.getRow(i + 1).getCell(23);
            cell.setCellValue(totalUnitStatus);

            if (offer) {
                cell = sheet.getRow(i + 1).getCell(24);
                cell.setCellValue(totalUnitStatus);
            }

            cell = sheet.getRow(i + 1).getCell(25);
            cell.setCellValue(certificationStatus);
            cell = sheet.getRow(i + 1).getCell(26);
            cell.setCellValue(mailStatus);
            cell = sheet.getRow(i + 1).getCell(27);
            cell.setCellValue(additionalQCStatus);

            cell = sheet.getRow(i + 1).getCell(28);
            cell.setCellValue(grandtotalStatus);
            cell = sheet.getRow(i + 1).getCell(29);
            cell.setCellValue(transcationStatus);
            cell = sheet.getRow(i + 1).getCell(30);
            cell.setCellValue(orderStatus);

            mailCost = 0;
            grandtotal = 0;
            transcationCost = 0;
            orderCost = 0;
            totalUnitCost = 0;
            offerCost =0;
            certificationCost = 0;
            additionalQCCost = 0;
        }
        workbook.write(fileOutput);
        fileOutput.close();
    }

    private void callElement(String order, Translation translation, String slang,
                             String tlang, String page, String aqty, String cnoption, String reqMail) {

        switch (order) {
            case "a":
                translation.selectLanguageFrom(slang);
                break;

            case "b":
                translation.selectLanguageTo(tlang);
                break;

            case "c":
                translation.pageCount(page);
                break;

            case "d":
                if (aqty.equals("Yes")) {
                    translation.selectAdditionalQtyCheck(1);
                } else if (aqty.equals("No")) {
                    translation.selectAdditionalQtyCheck(0);
                }

                break;

            case "e":
                if (cnoption.equals("Yes")) {
                    if (slang.equals("English")) {

                        translation.selectCertification();
                    } else if (tlang.equals("English")) {

                        translation.selectNotarization();
                    }
                } else if (cnoption.equals("No")) {
                    if (slang.equals("English")) {

                        translation.deselectCertification();
                    } else if (tlang.equals("English")) {

                        translation.deselectNotarization();
                    }
                }
                break;

            case "f":
                if (reqMail.equals("Yes")) {
                    translation.selectRequestMailCopy(country, address);
                } else if (reqMail.equals("No")) {
                    translation.deselectRequestMailCopy();
                } else if (reqMail.equals("-")) {
                    translation.selectRequestMailCopy("India", address);
                }

                break;
        }
    }

    private String checkStatus(double data1, double data2, String message) {
        String status;
        //System.out.println(message);
        if (data1 == data2) {
            //System.out.print(": Pass\n");
            status = "Pass";
        } else {
            //System.out.print(": Fail\n");
            //System.out.println("Expected : " + data1);
            //System.out.println("Actual : " + data2);
            status = "Fail\n" + "Expected : " + data1 + "\nActual : " + data2;
        }
        return status;
    }

    @BeforeClass
    public void beforeClass() throws IOException {
        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        fullScreen(driver);
        readTranslateData();
        getEmailCreadential();
        fileOutput = new FileOutputStream(file);
    }

    @AfterClass
    public void afterClass() {

        //driver.quit();
    }

    public static void readTranslateData() throws IOException {

        file = new File("src/test/resources/Tests.xls");
        fileInput = new FileInputStream(file);
        workbook = new HSSFWorkbook(fileInput);
        sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            String temp = "";
            cell = sheet.getRow(i).getCell(0);
            sourceLanguages.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(1);
            temp = cell.getStringCellValue();

            cell = sheet.getRow(i).getCell(2);
            targetLanguages.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(3);
            temp = temp + "," + cell.getStringCellValue();

            cell = sheet.getRow(i).getCell(4);
            pages.add(cell.getNumericCellValue());
            cell = sheet.getRow(i).getCell(5);
            temp = temp + "," + cell.getStringCellValue();

            cell = sheet.getRow(i).getCell(6);
            additionalQtys.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(7);
            temp = temp + "," + cell.getStringCellValue();

            cell = sheet.getRow(i).getCell(8);
            certificationNotary.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(9);
            temp = temp + "," + cell.getStringCellValue();

            cell = sheet.getRow(i).getCell(10);
            requestedMailCopies.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(11);
            temp = temp + "," + cell.getStringCellValue();
            totalOrders.add(temp);

            cell = sheet.getRow(i).getCell(12);
            processings.add(cell.getStringCellValue());

            cell = sheet.getRow(i).getCell(13);
            unitCosts.add(cell.getNumericCellValue());
        }
    }

    private static void getEmailCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/gmail.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }

    private String checkCondition(String currentUrl, String site) {
        String status = "";
        if (currentUrl.contains(site)) {
            //System.out.println(currentUrl + " and it pass");
            status = currentUrl + " and it pass";
        } else {
            //System.out.println(currentUrl + " and it fail");
            status = currentUrl + " and it fail";
        }
        return status;
    }

    private List<String> dataSplit(String datas) {
        final Pattern p = Pattern.compile("^\\d+");
        Comparator<String> c = new Comparator<String>() {
            @Override
            public int compare(String object1, String object2) {
                Matcher m = p.matcher(object1);
                Integer number1 = null;
                if (!m.find()) {
                    return object1.compareTo(object2);
                } else {
                    Integer number2 = null;
                    number1 = Integer.parseInt(m.group());
                    m = p.matcher(object2);
                    if (!m.find()) {
                        return object1.compareTo(object2);
                    } else {
                        number2 = Integer.parseInt(m.group());
                        int comparison = number1.compareTo(number2);
                        if (comparison != 0) {
                            return comparison;
                        } else {
                            return object1.compareTo(object2);
                        }
                    }
                }
            }
        };

        String arr[] = datas.split(",");
        List<String> examplesList = new ArrayList<String>(Arrays.asList(arr));
        Collections.sort(examplesList, c);
        return examplesList;
    }
}
