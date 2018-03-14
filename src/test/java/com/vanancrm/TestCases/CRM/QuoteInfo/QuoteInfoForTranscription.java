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

import com.vanancrm.Common.TestBase;
import com.vanancrm.Common.TranscriptionPrice;

import com.vanancrm.PageObjects.MainPages.AdditionalInformation;
import com.vanancrm.PageObjects.MainPages.Transcription;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class QuoteInfoForTranscription extends TestBase implements TranscriptionPrice {

    private static String username = "";
    private static String password = "";

    private WebDriver driver;

    private String[] additionalService = {"Video Services", "Typing"};
    private String[] channels = {"Email Quote", "Direct Payment", "Request for Quote"};
    private String[] categorys = {"General", "Legal"};
    private String[] languages = {"English", "Spanish", "Tamil"};
    private String[] timecodes = {"Every 3 sec", "Every 1 minute",
            "Not required"};
    private String comment = "Automation Testing";
    private String country = "India";
    private String fileName = "Testing";
    private String fileExtention = ".mp3";
    private String minute = "180";
    private String mailId = "automation.vananservices@gmail.com";
    private String name = "Automation";
    private String phoneNumber = "1-888-535-5668";
    private String service = "Transcription";
    private String serviceType = "Weekly";
    private String url = "";

    private double amo = 0;
    private double bPrice = 0;
    private double dPrice = 0;
    private double gtot = 0;
    private int min;
    private double offerPr = 0;
    private double orderTot = 0;
    private double timePrice = 0;
    private double trans = 0;
    private double verbe = 0;

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
    public void transcriptionServices() throws IOException,
            InterruptedException, AWTException {
        url = System.getProperty("website");
        System.out.println("\n======================================");
        System.out.println("\nScenario Started");
        System.out.println("\n======================================");
        System.out.println("\nScenario #1");
        testScenario(languages[1], categorys[0], timecodes[2], false, false,
                false, channels[1], false);
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

        screenshot(driver, "TranscriptionQuote");
        //driver.quit();
    }

    private void raiseTicket(String language, String category, String timeCode,
                 boolean additionalQty, boolean tat, boolean basicPrice, boolean
                 offer) throws AWTException, InterruptedException, IOException {

        driver.get(url);
        Transcription transcription = new Transcription(driver);
        transcription.enterMinutes(minute);
        transcription.selectLanguageFrom(language);
        if (language.equals(languages[0]) || language.equals(languages[2])) {
            if (language.equals(languages[0])) {
                if (!basicPrice) {
                    transcription.selectNativeSpeaker();
                }
            }
            if (category.equals(categorys[0])) {
                if (!basicPrice) {
                    transcription.selectNativeSpeaker();
                }
                transcription.selectVerbatim(1);
            } else {
                if (!language.equals(languages[1]) && !basicPrice) {
                    transcription.selectCategory(1);
                }
            }
        }
        transcription.selectTimeCode(timeCode);
        transcription.uploadFile(driver, fileName, fileExtention);
        waitForProcessCompletion(60);
        transcription.enterFileLength(minute);
        if (additionalQty) {
            transcription.selectAdditionalQtyCheck(1);
        }
        if (tat) {
            transcription.selectTAT(1);
        }
        transcription.selectFreeTrail();
        transcription.enterComments(comment);

        transcription.enterEmailId(mailId);
        double off = 0;
        if (offer) {
            off = transcription.getOfferDiscout();
        }
        if (basicPrice) {
            checkPrice(timeCode, offer, true, false, false,
                    false, true, false, false, true, true,
                    false,
                    transcription.getBasePrice(), transcription.getUnitCost(),
                    off, transcription
                            .getActualDiscountPrice(), transcription.getDiscountPrice(),
                    transcription.getVerbatim(), transcription.getTimeCode(),
                    transcription.getESsubTotalCost(), 0,
                    transcription.getExpedite(), transcription.getTotalCost(),
                    transcription.getAddtionalQtyCheck(),
                    transcription.getTranscationFee(), transcription
                            .getGrandTotal(), language);
        }
        if (tat) {
            checkPrice(timeCode, offer, true, true, false,
                    false, true, false, false, false, false, true,
                    transcription.getBasePrice(), transcription.getUnitCost(),
                    off, transcription
                            .getActualDiscountPrice(), transcription.getDiscountPrice(),
                    transcription.getVerbatim(), transcription.getTimeCode(),
                    transcription.getESsubTotalCost(), 0.5,
                    transcription.getExpedite(), transcription.getTotalCost(),
                    transcription.getAddtionalQtyCheck(),
                    transcription.getTranscationFee(), transcription.getGrandTotal(), language);
        }
        if (language.equals(languages[0]) && category.equals(categorys[1])) {
            checkPrice(timeCode, offer, false, false, true,
                    false, false, false, true, false, false, false,
                    transcription.getBasePrice(), transcription.getUnitCost(),
                    off, transcription
                            .getActualDiscountPrice(), transcription.getDiscountPrice(),
                    transcription.getVerbatim(), transcription.getTimeCode(),
                    transcription.getESsubTotalCost(), 0.5,
                    transcription.getExpedite(), transcription.getTotalCost(),
                    transcription.getAddtionalQtyCheck(),
                    transcription.getTranscationFee(), transcription.getGrandTotal(), language);
        }
        if (language.equals(languages[1])) {
            checkPrice(timeCode, offer, false, false, false,
                    true, false, false, false, false, false, false,
                    transcription.getBasePrice(), transcription.getUnitCost(),
                    off, transcription
                            .getActualDiscountPrice(), transcription.getDiscountPrice(),
                    transcription.getVerbatim(), transcription.getTimeCode(),
                    transcription.getESsubTotalCost(), 0,
                    transcription.getExpedite(), transcription.getTotalCost(),
                    transcription.getAddtionalQtyCheck(),
                    transcription.getTranscationFee(), transcription.getGrandTotal(), language);
        }
        if (language.equals(languages[2])) {

            if (transcription.isCustomMessageDisplayed()) {
                System.out.println("Quote info message is displayed");
            }
        }
        if (language.equals(languages[2])) {
            transcription.clickGetQuote();
        } else if (language.equals(languages[1])) {
            transcription.clickProceedPayment();
        } else if (language.equals(languages[0])) {
            transcription.clickEmailMeGetQuote();
        }

        waitForProcessCompletion(15);
        String currentUrl = driver.getCurrentUrl();
        if (!language.equals(languages[1])) {

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

    private void checkPrice(String timeCode, boolean offer, boolean basicPrice,
            boolean nSpeaker, boolean legal, boolean language, boolean verbat,
            boolean amount, boolean additionalQtyCheck, boolean actual,
            boolean discount, boolean exp, double baPrice, double ucost,
            double oPri, double dPric, double dPri, double verba, double timea,
            double sub, double expe, double expa, double gtotal, double additional,
            double transFee, double total, String lang) {

        min = Integer.parseInt(minute);
        if (min >= 1 && min <= 179) {
            if (nSpeaker) {
                bPrice = basePri[1];
            }
            if (legal) {
                bPrice = basePri[2];
            }
            if (language) {
                bPrice = basePri[3];
            }
            if (actual) {
                bPrice = basePri[0];
            }
        } else if (min >= 180 && min <= 359) {
            if (nSpeaker) {
                bPrice = basePri[1];
            }
            if (legal) {
                bPrice = basePri[2];
            }
            if (language) {
                bPrice = basePri[3];
            }
            if (actual) {
                bPrice = basePri[0];
            }
            if (!language) {
                if (basicPrice) {
                    dPrice = discountPri[0];
                }
            }
        } else if (min >= 360 && min <= 720) {
            if (nSpeaker) {
                bPrice = basePri[1];
            }
            if (legal) {
                bPrice = basePri[2];
            }
            if (language) {
                bPrice = basePri[3];
            }
            if (actual) {
                bPrice = basePri[0];
            }
            if (!language) {
                if (basicPrice) {
                    dPrice = discountPri[1];
                }
            }
        } else if (min >= 720 && min <= 900) {
            if (nSpeaker) {
                bPrice = basePri[1];
            }
            if (legal) {
                bPrice = basePri[2];
            }
            if (language) {
                bPrice = basePri[3];
            }
            if (actual) {
                bPrice = basePri[0];
            }
            if (!language) {
                if (basicPrice) {
                    dPrice = discountPri[2];
                }
            }
        }
        if (timeCode.equals(timecodes[1])) {

            timePrice = timecodePri[0];
        } else if (timeCode.equals(timecodes[2])) {
            timePrice = 0;
        } else {
            timePrice = timecodePri[1];
        }
        if (verbat) {
            verbe = verbatimPri;
        }

        String message = "Base price Actual";
        evaluateCondition(message, bPrice, baPrice);
        message = "Base price Total";
        evaluateCondition(message, bPrice * min, ucost);
        if (offer) {
            message = "New year offer";
            offerPr = (bPrice * min) - (offerPri[0] * (bPrice * min));
            evaluateCondition(message, offerPr, oPri);
        }
        if (discount) {
            message = "Discounted price Actual";
            evaluateCondition(message, dPrice, dPric);
            message = "Discounted price Total";
            evaluateCondition(message, dPrice * min, dPri);
        } else {
            dPrice = 0;
        }
        if (verbat) {
            message = "Verbatim";
            evaluateCondition(message, verbe * min, verba);
        }
        message = "Time Code";
        evaluateCondition(message, timePrice * min, timea);


        if (amount) {
            amo = bPrice * min;
        } else {
            if (lang.equals(languages[0])) {
                amo = offerPr + (dPrice * min);
            } else {
                amo = bPrice * min;
            }
        }

        if (exp) {

            message = "Subtotal";
            evaluateCondition(message, amo + (dPrice * min) + (verbe * min) +
                    (timePrice * min), sub);

            message = "Expedited service fee";
            evaluateCondition(message, (bPrice * min) * expe, expa);
            gtot = amo + (timePrice * min) + (verbe * min) + ((bPrice * min) * expe);


        }
        if (additionalQtyCheck) {
            message = "Additional Quality Check";
            evaluateCondition(message, additionalQtyPri * min, additional);
            gtot = amo + (timePrice * min) + (additionalQtyPri * min);
        }
        if (!exp && !additionalQtyCheck && discount) {

            gtot = amo + (timePrice * min) + (verbe * min);
        }
        if (!exp && !additionalQtyCheck && !discount) {

            gtot = amo + (timePrice * min);
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

    private void checkCRM(String language, String typeOfService, String
            channel, String timeCode, boolean addtionalQty, boolean tat,
                          boolean basicPrice, boolean offer) {

        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, language, typeOfService, channel, timeCode, addtionalQty, tat,
                basicPrice, offer);
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String language, String
            typeOfService, String channel, String timeCode, boolean addtionalQty,
            boolean tat, boolean basicPrice, boolean offer) {

        String ticketID = "";
        readTableData = menus.clickNewMenu();
        List<String> tickets = readTableData.readTableRows();

        for (int i = 0; i < tickets.size(); i++) {

            if (tickets.get(i).contains(service)) {

                waitForProcessCompletion(20);
                viewTicketDetails = new ViewTicketDetails(driver);
                viewTicketDetails = readTableData.clickService(service,
                        (i + 1));
                waitForProcessCompletion(20);
                System.out.println("Channel " + viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel"));
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
                    changeTicketStatus(language);
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

    private void changeTicketStatus(String language) {

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
            quoteInfo.setFileDetails(fileName+ fileExtention, language,
                    language,"Transcription",minute, ""+bPrice,comment,
                    false);
            waitForProcessCompletion(5);
            System.out.println("==========================================" );
            System.out.println("Quto Info Price Details" );
            System.out.println("==========================================" );
            if(quoteInfo.getTotal()== amo) {
                System.out.println("Total : Pass("+quoteInfo.getTotal()+")" );
            } else {

                System.out.println("Total : Fail \n Autal : "+quoteInfo.getTotal()
                        +")" );
                System.out.println("Expected : " + amo +")" );
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

    private void testScenario(String language, String category, String
            timeCode, boolean addtionalQty, boolean tat, boolean basicPrice,
                              String channel, boolean offer) throws AWTException,
            InterruptedException, IOException {

        raiseTicket(language, category, timeCode, addtionalQty, tat,
                basicPrice, offer);
        getCRMCreadential();
        checkCRM(language, category, channel, timeCode, addtionalQty, tat,
                basicPrice, offer);
    }
}
