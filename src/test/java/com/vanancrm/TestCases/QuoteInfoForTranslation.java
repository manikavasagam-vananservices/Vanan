package com.vanancrm.TestCases;

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

import com.vanancrm.Common.TestBase;
import com.vanancrm.Common.TranslationPrice;

import com.vanancrm.PageObjects.MainPages.AdditionalInformation;
import com.vanancrm.PageObjects.MainPages.Translation;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class QuoteInfoForTranslation extends TestBase implements
        TranslationPrice {

    private static String username = "";
    private static String password = "";
    private WebDriver driver;
    private String[] additionalService = {"Captioning/Subtitling", "Voice Over"};
    private String[] channels = {"Email Quote", "Direct Payment", "Request for Quote"};
    private String[] categorys = {"General", "Legal"};

    private String[] timecodes = {"Every 3 sec", "Every 1 minute",
            "Not required"};

    private String[] fileTypes = {"Document", "Audio/Video"};
    private String[] srclanguages = {"English", "Japanese", "Afrikaans"};
    private String[] tarlanguages = {"Spanish", "English"};
    // 0 represents Tier 1 and 2
    // 1 represents Tier 3
    // 2 represents Tier 4
    // 3 represents Tier 5
    private int[] tiers = {0, 1, 3};
    private String address = "Vanan";
    private String country = "United States";
    private String comment = "Automation Testing";

    private String fileName = "";
    private String fileExtention = "";
    private String minute = "10";
    private String mailId = "automation.vananservices@gmail.com";
    private String name = "Automation";
    private String phoneNumber = "1-888-535-5668";
    private String service = "Translation";
    private String serviceType = "Weekly";
    private String url = "";
    private String ticketID = "";

    private double bPrice = 0;
    private double gtot = 0;
    private int min;
    private double offerPr = 0;
    private double orderTot = 0;
    private double certification = 0;
    private double exper = 0;
    private double trans = 0;

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
    public void translationServices() throws IOException,
            InterruptedException, AWTException {
        url = System.getProperty("website");
        System.out.println("\n======================================");
        System.out.println("\nScenario Started");
        System.out.println("\n======================================");
        System.out.println("\nScenario #1");
        testScenario(fileTypes[0], srclanguages[1], tarlanguages[1],
                true, false, tiers[1], channels[1],false,false,true,
                false);

        System.out.println("======================================");
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
    public void afterClass() throws IOException {

        screenshot(driver, "TranslationQuote");
        driver.quit();
    }

    private void raiseTicket(String fileType, String srcLang, String targetLang,
            boolean additionalQty, boolean tat, int tier, boolean offer)
            throws AWTException, InterruptedException, IOException {

        driver.get(url);
        Translation translation = new Translation(driver);
        translation.selectFileType(fileType);
        if (fileType.equals(fileTypes[1])) {
            translation.minutes(minute);
        } else {
            translation.pageCount(minute);
        }
        translation.selectLanguageFrom(srcLang);
        translation.selectLanguageTo(targetLang);
        if (!targetLang.equals(srclanguages[0])) {
            translation.selectCertification();
        } else {
            translation.selectNotarization();
        }

        if (additionalQty) {
            translation.selectAdditionalQtyCheck(1);
        }
        if (fileType.equals(fileTypes[0])) {
            translation.selectFreeTrail();
        }

        translation.selectRequestMailCopy(country, address);
        fileName = "Testing";
        if (fileType.equals(fileTypes[0])) {
            fileExtention = ".txt";
            translation.uploadFile(driver, fileName, fileExtention);
        } else {
            fileExtention = ".mp3";
            translation.uploadFile(driver, fileName, fileExtention);
        }

        waitForProcessCompletion(60);
        if (fileType.equals(fileTypes[1])) {
            translation.enterFileLength(minute);
        }
        translation.enterComments(comment);
        translation.emailId(mailId);

        if (tat) {
            translation.selectTAT(1);
        }
        if (fileType.equals(fileTypes[1])) {
            translation.selectNeedTranscript(0);
            translation.selectNativeSpeaker();
            translation.selectTimeCode(0);
            if (translation.isCustomMessageDisplayed()) {
                System.out.println("Quote info message is displayed");
            }
        } else {
            double off = 0;
            if (offer) {
                off = translation.getOfferFee();
            }
            waitForProcessCompletion(30);
            if (!targetLang.equals(srclanguages[0])) {


                checkPrice(tier, offer,
                        additionalQty, tat, off, translation.getActualCost(),
                        0.5, translation.getExpeditedCost(), translation.getSubTotal(),
                        translation.getAdditionalQualityAmount(), translation
                                .getTransactionFee(), translation
                                .getGrandTotal(), translation.getTranslationCost(),
                        false, 0, 0);
            } else {
                checkPrice(tier, offer,
                        additionalQty, tat, off, translation.getActualCost(),
                        0.5, translation.getExpeditedCost(), translation.getSubTotal(),
                        translation.getAdditionalQualityAmount(), translation
                                .getTransactionFee(), translation
                                .getGrandTotal(), translation.getTranslationCost(),
                        true, translation.getNotaryFee(), translation.getProcessFee());
            }
        }
        if (srcLang.equals(srclanguages[2])) {

            if (translation.isCustomMessageDisplayed()) {
                System.out.println("Quote info message is displayed");
            }
        }
        if (fileType.equals(fileTypes[1])) {
            translation.clickGetQuote();
        } else if (srcLang.equals(srclanguages[1])) {
            translation.clickProceedPayment();
        } else if (srcLang.equals(srclanguages[0]) || srcLang.equals
                (srclanguages[2])) {
            translation.clickEmailMeGetQuote();
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

    private void checkPrice(int tier, boolean offer, boolean additionalQty,
            boolean tat, double oPri, double baPrice, double expe, double expa,
            double gtotal, double additional, double transFee, double total,
            double ucost, boolean notary, double notFee, double processfee) {

        String message = "";
        if (tier == 0) {
            bPrice = basePrices[0];
        } else if (tier == 1) {
            bPrice = basePrices[1];
        } else if (tier == 2) {
            bPrice = basePrices[2];
        } else if (tier == 3) {
            bPrice = basePrices[3];
        }
        min = Integer.parseInt(minute);
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
        if (notary) {
            certification = notaryFee + notaryProcessFee;
            message = "Notary Fee ";
            evaluateCondition(message, notaryFee, notFee);
            message = "Notary Processing Fee ";
            evaluateCondition(message, notaryProcessFee, processfee);
        } else {
            certification = certificationFee;
            message = "Expedited service fee";
            evaluateCondition(message, (bPrice * min) * expe, expa);
            exper = (bPrice * min) * expe;
        }
        if (tat) {

            message = "Expedited service fee";
            evaluateCondition(message, (bPrice * min) * expe, expa);
            gtot = certification + offerPr + ((bPrice * min) * expe) +
                    mailingFee;

        }
        if (additionalQty) {
            message = "Additional Quality Check";
            evaluateCondition(message, additionalQtyPri * min, additional);
            gtot = certification + offerPr + (additionalQtyPri * min) +
                    mailingFee;
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

    private void checkCRM(String fileType, String srcLang, String targetLang,
            boolean additionalQty, boolean tat, int tier, String channel,
            boolean offer, boolean notary, boolean cert, boolean times) {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, fileType, srcLang, targetLang, additionalQty, tat,
                tier, channel, offer, notary, cert, times);
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String fileType, String srcLang,
            String targetLang, boolean additionalQty, boolean tat, int tier,
            String channel, boolean offer, boolean notary, boolean cert,
            boolean times) {

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

                    ticketID = tickets.get(i).substring(tickets.get(i).indexOf("VS"),
                            tickets.get(i).indexOf(service) - 1);
                    System.out.println((i + 1) + " : Channel = " +
                            viewTicketDetails.getRunTimeTicketFieldValues(
                                    "Channel"));
                    checkViewTicketInfo(channel);

                    System.out.println(channel + " Ticket ID: " + ticketID);
                    changeTicketStatus(srcLang, targetLang, notary, cert,
                            times, additionalQty);

                    break;
                } else {
                    ticketID = "\n\nEither ticket is Not created or Still" +
                            " waiting for ticket";
                    System.out.println(ticketID);
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

    private void changeTicketStatus(String srcLang, String targetLang, boolean
            notary, boolean cert, boolean timecode, boolean additionalQty) {

        try {
            String times="";
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
            fileInfo.setFileDetails(fileName+fileExtention, "Document",
                    ""+minute, "Good", "Accent", "> 5", comment);
            fileInfo.clickUpdateFileInfo();
            waitForProcessCompletion(15);
            QuoteInfo quoteInfo = menus.clickQuoteInfo();
            waitForProcessCompletion(15);
            if (!targetLang.equals(srclanguages[0])) {
                quoteInfo.selectCertificate();
            } else {
                quoteInfo.selectNotary();
            }
            quoteInfo.selectMailing();
            //quoteInfo.selectTimeCode();
            //quoteInfo.selectVerbatim();
            if(additionalQty) {
                quoteInfo.selectQc();
            }

            quoteInfo.setFileDetails(fileName+ fileExtention, srcLang,
                    targetLang,"Translation",minute, ""+bPrice,comment,
                    false);
            waitForProcessCompletion(5);
            if (!targetLang.equals(srclanguages[0])) {
                quoteInfo.enterCertificationValues(""+certificationFee);

            } else {
                quoteInfo.enterNotarizationValues(""+notaryFee);
                quoteInfo.enterServiceFeeValues(""+notaryProcessFee);
            }
            quoteInfo.enterMailingValues(""+mailingFee);
            if(timecode) {
                //quoteInfo.enterTimeCodeValues(""+times);
            }
            if(additionalQty) {
                quoteInfo.enterQualityCheckValues(""+additionalQtyPri * min);
            }
            waitForProcessCompletion(5);
            System.out.println("==========================================" );
            System.out.println("Quto Info Price Details" );
            System.out.println("==========================================" );
            if(quoteInfo.getTotal()== offerPr) {
                System.out.println("Total : Pass("+quoteInfo.getTotal()+")" );
            } else {

                System.out.println("Total : Fail \n Autal : "+quoteInfo.getTotal()
                        +")" );
                System.out.println("Expected : " + offerPr +")" );
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

    private void testScenario(String fileType, String srcLang, String targetLang,
            boolean additionalQty, boolean tat, int tier, String channel,
            boolean offer, boolean notary, boolean cert,  boolean times) throws
            AWTException, InterruptedException, IOException {

        raiseTicket(fileType, srcLang, targetLang, additionalQty, tat, tier, offer);
        getCRMCreadential();
        checkCRM(fileType, srcLang, targetLang, additionalQty, tat, tier,
                channel, offer, notary, cert, times);
    }

    public String getTicketId() {
        return ticketID;
    }
}