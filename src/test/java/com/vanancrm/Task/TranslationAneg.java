package com.vanancrm.Task;

import com.vanancrm.Common.TestBase;
import com.vanancrm.PageObjects.MainPages.Translation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

public class TranslationAneg extends TestBase {

    private WebDriver driver;

    private static String username = "";
    private static String password = "";

    private String service = "Translation";

    private static List<String> sourceLanguages = new ArrayList<String>();
    private static List<String> targetLanguages = new ArrayList<String>();
    private static List<Double> lengths = new ArrayList<Double>();
    private static List<String> needTranscripts = new ArrayList<String>();
    private static List<String> timeCodes = new ArrayList<String>();
    private static List<String> additionalQtys = new ArrayList<String>();
    private static List<String> certificationNotary = new ArrayList<String>();
    private static List<String> freeTrails = new ArrayList<String>();
    private static List<String> requestedMailCopies = new ArrayList<String>();
    private static List<String> totalOrders = new ArrayList<String>();

    private String address = "Vanan";
    private String country = "United States";

    private boolean offer = true;

    @Test
    public void testStep() throws IOException {

        driver.get("https://vananservices.com/Translation-Quote.php");
        sheet = workbook.getSheetAt(0);
        Translation translation = new Translation(driver);
        translation.selectFileType("Audio/Video");
        String letter = "";
        for (int i = 0; i < totalOrders.size(); i++) {
            dataSplit(totalOrders.get(i));
            String[] data = totalOrders.get(i).split(",");

            /*System.out.println("Source : " + sourceLanguages.get(i) +" \n Target :"+ targetLanguages.get(i)+" \n Length: "+
                    (lengths.get(i)).intValue()+"\n Need Transcript:" + needTranscripts.get(i)+"\n Time code:"+timeCodes.get(i)
                            +"\n additional qty:"+additionalQtys.get(i)+"\ncertification:"+certificationNotary.get(i)+"\n free trial"+ freeTrails.get(i)+"\n requested mail:"+
                    requestedMailCopies.get(i));*/

            for (int j = 0; j < data.length; j++) {
                for (int k = 0; k < data[j].length(); k++) {
                    char a = data[j].charAt(k);
                    if (Character.isDigit(a)) {

                    } else {
                        letter = letter + a;

                    }
                    callElement(letter, translation, sourceLanguages.get(i), targetLanguages.get(i),
                            (lengths.get(i)).intValue(), needTranscripts.get(i), timeCodes.get(i),
                            additionalQtys.get(i), certificationNotary.get(i), freeTrails.get(i),
                            requestedMailCopies.get(i));

                    letter = "";
                }
            }
            assignSheetValue(i, 18, 19, translation.getSourceLanguage(), sourceLanguages.get(i),
                    "Source Language");

            assignSheetValue(i, 20, 21, translation.getLanguageTo(), targetLanguages.get(i),
                    "Target Language");
            if ((lengths.get(i)).intValue() == 60) {

                assignSheetValue(i, 22, 23, translation.getMinutes(), (lengths.get(i)).intValue() + "",
                        "Minute");
            } else {
                assignSheetValue(i, 22, 23, translation.getHours(), (lengths.get(i)).intValue() + "",
                        "Hours");
            }
            if (needTranscripts.get(i).contains("Yes")) {
                assignSheetValue(i, 24, 25, translation.needTranscriptStatus(0) + "", "true",
                        "Need transcript");
            } else if (needTranscripts.get(i).contains("No")) {
                assignSheetValue(i, 24, 25, translation.needTranscriptStatus(1) + "", "true",
                        "Need transcript");
            }

            if (timeCodes.get(i).contains("Yes")) {
                assignSheetValue(i, 26, 27, translation.timeCodeStatus(0) + "", "true",
                        "Time code");
            } else if (timeCodes.get(i).contains("No")) {
                assignSheetValue(i, 26, 27, translation.timeCodeStatus(1) + "", "true",
                        "Time code");
            }


            if (additionalQtys.get(i).contains("Yes")) {
                assignSheetValue(i, 28, 29, translation.additionalQtyCheckStatus(0) + "", "true",
                        "Additional Quality");
            } else if (additionalQtys.get(i).contains("No")) {
                assignSheetValue(i, 28, 29, translation.additionalQtyCheckStatus(1) + "", "true",
                        "Additional Quality");
            }

            if (certificationNotary.get(i).equals("Yes")) {
                if(!sourceLanguages.get(i).equals("English") && !targetLanguages.get(i).equals("English")) {
                    assignSheetValue(i, 30, 31, translation.certificationStatus() + "", "true",
                            "Certification");
                } else {
                    if (sourceLanguages.get(i).equals("English")) {
                        assignSheetValue(i, 30, 31, translation.certificationStatus() + "", "true",
                                "Certification");
                    } else if (targetLanguages.get(i).equals("English")) {

                        assignSheetValue(i, 30, 31, translation.notarizationStatus() + "", "true",
                                "Notarization");
                    }
                }
            } else if (certificationNotary.get(i).equals("No")) {
                if(!sourceLanguages.get(i).equals("English") && !targetLanguages.get(i).equals("English")) {
                    assignSheetValue(i, 30, 31, translation.certificationStatus() + "", "false",
                            "Certification");
                } else {
                    if (sourceLanguages.get(i).equals("English")) {
                        assignSheetValue(i, 30, 31, translation.certificationStatus() + "", "false",
                                "Certification");
                    } else if (targetLanguages.get(i).equals("English")) {
                        assignSheetValue(i, 30, 31, translation.notarizationStatus() + "", "false",
                                "Notarization");
                    }
                }
            }

            if (freeTrails.get(i).contains("Yes")) {
                assignSheetValue(i, 32, 33, translation.freeTrailStatus(0) + "", "true",
                        "Free Trial");
            } else if (freeTrails.get(i).contains("No")) {
                assignSheetValue(i, 32, 33, translation.freeTrailStatus(1) + "", "true",
                        "Free Trial");
            }

            if (requestedMailCopies.get(i).contains("Yes")) {
                assignSheetValue(i, 34, 35, translation.requestMailCopyStatus(0) + "", "true",
                        "Requested Mail Copies");
            } else if (requestedMailCopies.get(i).contains("No")) {
                assignSheetValue(i, 34, 35, translation.requestMailCopyStatus(1) + "", "true",
                        "Requested Mail Copies");
            }
        }
        workbook.write(fileOutput);
        fileOutput.close();
    }

    private void assignSheetValue(int number, int cellNo1, int cellNo2,
                                  String data1, String data2, String message) {

        cell = sheet.getRow(number + 1).getCell(cellNo1);
        cell.setCellValue(data1);
        cell = sheet.getRow(number + 1).getCell(cellNo2);
        cell.setCellValue(checkStatus(data1, data2, message));
    }

    private void callElement(String order, Translation translation, String slang,
                             String tlang, int length, String needTrans, String timeCode,
                             String aqty, String cnoption, String freeTrial, String reqMail) {

        switch (order) {
            case "a":
                translation.selectLanguageFrom(slang);
                break;

            case "b":
                translation.selectLanguageTo(tlang);
                break;

            case "c":
                if (length != 60) {
                    translation.hours(length + "");

                } else {
                    translation.minutes(length + "");
                }
                break;

            case "d":
                if (needTrans.equals("Yes")) {
                    translation.selectNeedTranscript(0);
                } else if (needTrans.equals("No")) {
                    translation.selectNeedTranscript(1);
                }
                break;

            case "e":
                if (timeCode.equals("Yes")) {
                    translation.selectTimeCode(0);
                } else if (timeCode.equals("No")) {
                    translation.selectTimeCode(1);
                }
                break;

            case "f":
                if (aqty.equals("Yes")) {
                    translation.selectAdditionalQtyCheck(1);
                } else if (aqty.equals("No")) {
                    translation.selectAdditionalQtyCheck(0);
                }
                break;

            case "g":
                if (cnoption.equals("Yes")) {
                    if(!slang.equals("English")&& !tlang.equals("English")) {
                        translation.selectCertification();
                    } else {
                        if (slang.equals("English")) {

                            translation.selectCertification();
                        } else if (tlang.equals("English")) {

                            translation.selectNotarization();
                        }
                    }

                } else if (cnoption.equals("No")) {
                    if (!slang.equals("English") && !tlang.equals("English")) {
                        translation.deselectCertification();
                    } else {
                        if (slang.equals("English")) {

                            translation.deselectCertification();
                        } else if (tlang.equals("English")) {

                            translation.deselectNotarization();
                        }
                    }
                }
                break;

            case "h":
                if (freeTrial.equals("Yes")) {
                    translation.selectFreeTrail();
                } else if (freeTrial.equals("No")) {
                    translation.deselectFreeTrail();
                }
                break;

            case "i":
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

    private String checkStatus(String data1, String data2, String message) {
        String status;
        //System.out.println(message);
        if (data1.contains(data2)) {
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

        file = new File("src/test/resources/Testsa.xls");
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
            lengths.add(cell.getNumericCellValue());
            cell = sheet.getRow(i).getCell(5);
            temp = temp + "," + cell.getStringCellValue();

            cell = sheet.getRow(i).getCell(6);
            needTranscripts.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(7);
            temp = temp + "," + cell.getStringCellValue();

            cell = sheet.getRow(i).getCell(8);
            timeCodes.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(9);
            temp = temp + "," + cell.getStringCellValue();

            cell = sheet.getRow(i).getCell(10);
            additionalQtys.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(11);
            temp = temp + "," + cell.getStringCellValue();

            cell = sheet.getRow(i).getCell(12);
            certificationNotary.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(13);
            temp = temp + "," + cell.getStringCellValue();

            cell = sheet.getRow(i).getCell(14);
            freeTrails.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(15);
            temp = temp + "," + cell.getStringCellValue();

            cell = sheet.getRow(i).getCell(16);
            requestedMailCopies.add(cell.getStringCellValue());
            cell = sheet.getRow(i).getCell(17);

            temp = temp + "," + cell.getStringCellValue();
            totalOrders.add(temp);
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
