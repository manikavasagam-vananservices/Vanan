package com.vanancrm.TestCases.CRM.QuoteInfo;

import java.awt.AWTException;

import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;

import com.vanan.CRM.PageObjects.WholeSitePages.FileInfo;
import com.vanan.CRM.PageObjects.WholeSitePages.FileUploadFromTicket;
import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.QuoteInfo;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;

import com.vanancrm.Common.CaptioningPrice;
import com.vanancrm.Common.TestBase;

import com.vanancrm.PageObjects.MainPages.AdditionalInformation;
import com.vanancrm.PageObjects.MainPages.Captioning;


/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class QuoteInfoForCaptioning extends TestBase implements CaptioningPrice {

    private static String username = "";
    private static String password = "";
    private WebDriver driver;

    private String[] channels = {"Email Quote", "Direct Payment", "Request for Quote"};
    private String[] fileFormats = {"Standalone", "Embedded"};
    private String[] srclanguages = {"Spanish", "Japanese", "Afrikaans"};
    private String[] specificationPays = {"Netflix", "Amazon", "Digital Cinema Package"};
    // 0 represents Tier 1 and 2
    // 1 represents Tier 3
    // 2 represents Tier 4
    // 3 represents Tier 5
    private int[] tiers = {0, 1, 2};
    private String tarlanguage = "English";
    private String address = "Vanan";
    private String country = "United States";
    private String comment = "Automation Testing";

    private String fileName = "Testing";
    private String fileExtention = ".mp3";
    private String minute = "50";
    private String mailId = "automation.vananservices@gmail.com";
    private String name = "Automation";
    private String phoneNumber = "1-888-535-5668";
    private String service = "Captioning";
    private String serviceType = "Weekly";
    private String url = "";

    private double bPrice = 0;
    private double gtot = 0;
    private int min;
    private double offerPr = 0;
    private double orderTot = 0;
    private double btransl = 0;
    private double btransc = 0;
    private double btimecode = 0;
    private double trans = 0;
    private double trascription = 0;
    private double traslation = 0;
    private String ticketId = "";

    private DashBoardPage dashBoardPage;
    private EmailConversation emailConversation;
    private Menus menus;
    private ReadTableData readTableData;
    private ViewTicketDetails viewTicketDetails;

    private static void getCRMCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }

    @Test
    public void testCaptioningServices() throws IOException,
            InterruptedException, AWTException {

        System.out.println("\n======================================");
        System.out.println("\nScenario Started");
        System.out.println("\n======================================");

        testScenario(srclanguages[0], tarlanguage, fileFormats[0],
                specificationPays[0],true, false, false, tiers[1],
                channels[0]);

        System.out.println("Test Completed");

        System.out.println("======================================");
    }

    @BeforeClass
    public void beforeClass() {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        fullScreen(driver);
    }

    @AfterClass
    public void afterClass() {

        screenshot(driver, "CaptioningQuote");
        driver.quit();
    }

    private void raiseTicket(String srcLang, String targetLang,
            String fileFormat, String specificationPay, boolean translation,
            boolean transcription, boolean offer, int tier) throws AWTException,
            InterruptedException, IOException {
        url = System.getProperty("website");
        min = Integer.parseInt(minute);
        driver.get(url);
        Captioning captioning = new Captioning(driver);
        captioning.selectSourceLanguage(srcLang);
        captioning.selectTargetLanguage(targetLang);
        captioning.selectFormatting(fileFormat);
        captioning.enterMinutes(minute);
        captioning.selectSpecificationPay(specificationPay);
        if(translation || transcription) {
            captioning.deselectNeedTranslation();
        }
        if(min>=60) {
            captioning.deselectFreeTrialPage();
        }
        captioning.uploadFile(driver, fileName, fileExtention);
        waitForProcessCompletion(10);
        captioning.enterFileLength(minute);
        captioning.enterComments(comment);
        captioning.enterEmailId(mailId);

        if (!fileFormat.equals(fileFormats[1])) {

            double off = 0;
            if(offer) {
                off = captioning.getOfferFee();
            }
            checkPrice(offer, off, captioning.getBasePrice(),
                    captioning.getTranslationPrice(), captioning
                    .getTranslationPrice(), captioning.getTimeCodePrice(),
                    captioning.getGrandTotal(), captioning.getTransactionFee(),
                    captioning.getOrderTotal(), captioning.getTotalUnitCost(),
                    srcLang, translation, transcription, tier);
        } else {

            if (captioning.isCustomMessageDisplayed()) {
                System.out.println("Quote info message is displayed");
            }
        }

        if (fileFormat.equals(fileFormats[1])) {
            captioning.clickGetQuote();
        } else if (srcLang.equals(srclanguages[1])) {
            captioning.clickProceedPayment();
        } else if (srcLang.equals(srclanguages[0]) || srcLang.equals
                (srclanguages[2])) {
            captioning.clickEmailMeGetQuote();
        }

        waitForProcessCompletion(15);
        String currentUrl = driver.getCurrentUrl();
        if (!srcLang.equals(srclanguages[1])) {

            checkCondition(currentUrl, "additional-information.php");
            AdditionalInformation additionalInformation = new AdditionalInformation
                    (driver);
            additionalInformation.enterCustomerName(name);
            additionalInformation.selectCountry(country);
            additionalInformation.enterPhoneNumber(phoneNumber);
            additionalInformation.selectServiceType(serviceType);
            //additionalInformation.selectAdditionalServices(additionalService);
            additionalInformation.enterComments(comment);
            additionalInformation.clickSubmitButton();
        } else {
            checkCondition(currentUrl, "paypal");
        }
        waitForProcessCompletion(20);

    }

    private void checkPrice(boolean offer, double oPri, double baPrice,
            double transc, double transl,double time, double gtotal,
            double transFee, double total, double ucost, String language,
            boolean transla, boolean transcr, int tier) {

        String message = "";
        if (tier == 0) {
            bPrice = basePrices[0];
        } else if (tier == 1) {
            bPrice = basePrices[1];
        } else if (tier == 2) {
            bPrice = basePrices[2];
        }
        message = "Base price Actual";
        evaluateCondition(message, bPrice, baPrice);
        message = "Base price Total";
        evaluateCondition(message, (bPrice * min), ucost);

        if (offer) {
            message = "New year offer";
            if ((bPrice * min) < 1000) {
                offerPr = (bPrice * min) - (offerPri[0] * (bPrice * min));
            } else {
                offerPr = (bPrice * min) - (offerPri[1] * (bPrice * min));
            }
            evaluateCondition(message, offerPr, oPri);
        } else {
            offerPr = (bPrice * min);
        }

        if(transcr) {
            message = "Transcription";
            if (tier == 0) {
                trascription = transcriptionPrices[0];
            } else if (tier == 1) {
                trascription = transcriptionPrices[1];
            } else if (tier == 2) {
                trascription = transcriptionPrices[2];
            }
            btransc = trascription * min;
            evaluateCondition(message, btransc, transc);
        } else if(transla) {
            if(!language.equals(tarlanguage)) {
                message = "Translation";
                if (tier == 1) {
                    traslation = translationPrices[0];
                } else if (tier == 2) {
                    traslation = translationPrices[1];
                }
                btransl = traslation * min;
                evaluateCondition(message, btransl, transl);
            }
        }
        if(transcr || transla) {
            if (!language.equals(tarlanguage)) {
                message = "Timecode";
                btimecode = timeCodePri [0] * min;
                evaluateCondition(message, btimecode, time);
            }
        }
        if(transcr) {
            gtot = offerPr + btransc + btimecode;
        } else if(transla) {
            gtot = offerPr + btransl + btimecode;
        }

        trans = (double) Math.round((gtot * transcationPri) * 100) / 100;
        orderTot = gtot + trans;
        message = "Grand total";
        evaluateCondition(message, gtot, gtotal);
        message = "Transaction Fee";
        evaluateCondition(message, trans, transFee);
        message = "Order total";
        evaluateCondition(message, orderTot, total);

    }

    private void evaluateCondition(String message, double first,
            double second) {

        System.out.print(message + " : " + second);
        if (first == second) {

            System.out.print("\t Status : Pass\n");
        } else {

            System.out.print("\t Status : [Fail]\n");
            System.out.print("\t Expected : " + first + "\n");
            System.out.print("\t Actual : " + second + "\n");
        }
    }

    private void checkCondition(String currentUrl, String site) {
        if (currentUrl.contains(site)) {
            System.out.println(currentUrl + " and it pass");
        } else {
            System.out.println(currentUrl + " and it fail");
        }
    }

    private void checkCRM(String srcLang, String targetLang, String fileFormat,
            String specificationPay, boolean translation, boolean transcription,
            boolean offer, int tier, String channel) {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, srcLang, targetLang, fileFormat, specificationPay,
                translation, transcription, offer, tier, channel);
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String srcLang, String targetLang,
            String fileFormat, String specificationPay, boolean translation,
            boolean transcription, boolean offer, int tier, String channel) {

        readTableData = menus.clickNewMenu();
        List<String> tickets = readTableData.readTableRows();

        for (int i = 0; i < tickets.size(); i++) {

            if (tickets.get(i).contains(service)) {

                waitForProcessCompletion(20);
                viewTicketDetails = new ViewTicketDetails(driver);
                viewTicketDetails = readTableData.clickService(service,
                        (i + 1));
                waitForProcessCompletion(20);

                if (viewTicketDetails.getRunTimeTicketFieldValues("Email")
                        .contains(mailId) && viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel")
                        .contains(channel)) {

                    ticketId = tickets.get(i).substring(tickets.get(i).indexOf("VS"),
                            tickets.get(i).indexOf(service) - 1);
                    System.out.println((i + 1) + " : Channel = " +
                            viewTicketDetails.getRunTimeTicketFieldValues(
                                    "Channel"));
                    checkViewTicketInfo(channel);

                    System.out.println(channel + " Ticket ID: " + ticketId);
                    changeTicketStatus(srcLang,targetLang, translation,
                            transcription, offer);

                    break;
                } else {
                    ticketId = "\n\nEither ticket is Not created or Still" +
                            " waiting for ticket";
                    System.out.println(ticketId);
                }
                waitForProcessCompletion(60);
            }
        }
    }

    private void evaluateCondition(String message, String first,
                                   String second) {

        System.out.print(message + " : " + second);
        if ((first.replace("$", "")).contains(second)) {

            System.out.print("\t Status : Pass\n");
        } else {

            System.out.print("\t Status : [Fail]\n");
            System.out.print("\t Expected : " + second + "\n");
            System.out.print("\t Actual : " + first + "\n");
        }
    }

    private void changeTicketStatus( String srcLang, String targetLang,
            boolean translation, boolean transcription, boolean offer) {

        // Edit a ticket and moved the status into Others
        try {
            Edit edit = menus.clickEdit();
            waitForProcessCompletion(15);
            edit.clickFileUploadButton();
            FileUploadFromTicket fileUploadFromTicket = new FileUploadFromTicket
                    (driver);
            String parentWindow = driver.getWindowHandle();
            Set<String> handles =  driver.getWindowHandles();
            for(String windowHandle  : handles)
            {
                if(!windowHandle.equals(parentWindow))
                {
                    driver.switchTo().window(windowHandle);
                    waitForProcessCompletion(5);
                    fileUploadFromTicket.uploadFile(driver, fileName, fileExtention);
                    waitForProcessCompletion(10);
                    fileUploadFromTicket.enterComments(comment);
                    fileUploadFromTicket.clickSubmitButton();
                    waitForProcessCompletion(15);
                    driver.close();
                    driver.switchTo().window(parentWindow);
                    break;
                }
            }
            waitForProcessCompletion(10);
            edit.selectStatus("Others");
            edit.clickUpdateButton();
            waitForProcessCompletion(15);
            menus.clickFileInfo();
            FileInfo fileInfo = new FileInfo(driver);
            waitForProcessCompletion(15);
            fileInfo.setFileDetails(fileName+fileExtention, "Audio/Video",
                    "00-"+minute+"-00", "Good", "Accent", "> 5", comment);
            fileInfo.clickUpdateFileInfo();
            waitForProcessCompletion(15);
            QuoteInfo quoteInfo = menus.clickQuoteInfo();
            waitForProcessCompletion(15);
            quoteInfo.selectTimeCode();
            if(transcription) {

            } else if(translation) {

                quoteInfo.setFileDetails(fileName + fileExtention, srcLang,
                        targetLang,service,minute+"-00", ""+bPrice,comment,
                        true);
                quoteInfo.clickAction(fileName);
                quoteInfo.setFileDetails(fileName+ fileExtention, srcLang,
                        targetLang,"Translation",minute+"-00", ""+traslation,comment,
                        true);
            }
            waitForProcessCompletion(5);
            quoteInfo.enterTimeCodeValues(""+btimecode);
            waitForProcessCompletion(5);
            System.out.println("==========================================" );
            System.out.println("Quto Info Price Details" );
            System.out.println("==========================================" );
            if(quoteInfo.getTotal()== (offerPr + btransl)) {
                System.out.println("Total : Pass("+quoteInfo.getTotal()+")" );
            } else {

                System.out.println("Total : Fail \n Autal : "+quoteInfo.getTotal()
                        +")" );
                System.out.println("Expected : "+(offerPr + btransl)+")" );
            }
            if(quoteInfo.getSubTotal() == gtot) {
                System.out.println("Sub Total : Pass("+quoteInfo.getSubTotal()+")" );
            } else {
                System.out.println("Sub Total : Fail \n Autal : "+quoteInfo
                        .getSubTotal() + ")" );
                System.out.println("Expected : "+(gtot)+")" );
            }
            if(quoteInfo.getTransactionFee()==(trans)){
                System.out.println("Transaction Fee : Pass("+quoteInfo.getTransactionFee()+")" );
            } else {
                System.out.println("Transaction Fee : Fail \n Autal : "+quoteInfo
                        .getTransactionFee() + ")" );
                System.out.println("Expected : "+(trans)+")" );
            }
            if(quoteInfo.getOrderValue() == orderTot) {
                System.out.println("Order Value : Pass("+quoteInfo.getOrderValue()+")" );
            } else {
                System.out.println("Order Value : Fail \n Autal : "+quoteInfo
                        .getOrderValue() + ")" );
                System.out.println("Expected : "+(orderTot)+")" );
            }
            waitForProcessCompletion(10);
            quoteInfo.clickUpdateQuoteInfo();
            waitForProcessCompletion(15);
            emailConversation = menus.clickEmailConversation();
            waitForProcessCompletion(15);
            emailConversation.clickReadMore();
            emailConversation.clickNoActionButton();
        } catch (Exception ex) {

        }
    }

    private void checkViewTicketInfo(String channel) {

        System.out.println("===========================================");
        System.out.println("Checking View Ticket Details");
        System.out.println("===========================================\n");
        evaluateCondition("Email", viewTicketDetails
                .getRunTimeTicketFieldValues("Email"), mailId);
        evaluateCondition("Websites", url,
                viewTicketDetails.getRunTimeTicketFieldValues("Websites"));
        evaluateCondition("Channel", viewTicketDetails
                .getRunTimeTicketFieldValues("Channel"), channel);
        evaluateCondition("Service", viewTicketDetails
                .getServiceValues(), service);
        System.out.println("===========================================\n");
    }

    private void testScenario(String srcLang, String targetLang,
            String fileFormat, String specificationPay, boolean translation,
            boolean transcription, boolean offer, int tier, String channel)
            throws AWTException, InterruptedException, IOException {

        raiseTicket(srcLang, targetLang, fileFormat, specificationPay,
                translation, transcription, offer, tier);

        getCRMCreadential();
        checkCRM(srcLang, targetLang, fileFormat, specificationPay,
                translation, transcription, offer, tier, channel);
    }

    public String getTicketId() {
        return ticketId;
    }
}
