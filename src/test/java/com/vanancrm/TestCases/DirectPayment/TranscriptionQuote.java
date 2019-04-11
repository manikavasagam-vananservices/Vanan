package com.vanancrm.TestCases.DirectPayment;

import java.awt.AWTException;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;

import com.vanan.CRM.PageObjects.WholeSitePages.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vanancrm.PageObjects.MainPages.AdditionalInformation;
import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;
import com.vanancrm.PageObjects.MainPages.Transcription;
import com.vanan.CRM.PageObjects.MainPages.UnknownEmail;


import com.vanancrm.Common.TestBase;
import com.vanancrm.Common.TranscriptionPrice;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class TranscriptionQuote extends TestBase implements TranscriptionPrice {

    private WebDriver driver;

    private String[] additionalService = {"Video Services", "Typing"};
    private String[] channels = {"Email Quote", "Direct Payment", "Request for Quote"};
    private String[] categorys = {"General", "Legal"};
    private String[] languages = {"English", "Spanish", "Tamil"};
    private String[] timecodes = {"Every 3 sec", "Every 1 minute",
            "Not required"};
private String[] speaker = {"2 speakers","3 to 5 speakers","6 to 10 speakers","10+ speakers"};
    private String comment = "Automation Testing";
    private String country = "India";
    private String fileName = "AutomationTesting";
    private String fileExtention = ".mp3";
    private String minute = "180";
    private String mailId = "automation@vananservices.com";
    private String name = "Automation";
    private String phoneNumber = "1-888-535-5668";
    private String service = "Transcription";
    private String serviceType = "Weekly";




    private static String username = "";
    private static String password = "";
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

    @Test
    public void transcriptionServices() throws IOException,
            InterruptedException, AWTException {
        url = System.getProperty("website");
       // url ="https://vananservices.com/Transcription-Quote.php";
     //   driver.get(url);
        System.out.println("\n======================================");
        System.out.println("\nScenario Started");
        System.out.println("\n======================================");
        System.out.println("\nScenario #1");
        if (url.contains("Quote")) {
                testScenario(languages[0], categorys[0], timecodes[0],speaker[0],false,
                    false,true, channels[0], false);
            System.out.println("\n======================================");
            System.out.println("\nScenario #2");
            /*testScenario(languages[0], categorys[0], timecodes[0], false,
                    true,
                    false, channels[0]);
            System.out.println("\n======================================");
            System.out.println("\nScenario #3");
            testScenario(languages[0], categorys[1], timecodes[1], true, false,
                    false, channels[0]);
            System.out.println("\n======================================");
            System.out.println("\nScenario #4");*/
            testScenario(languages[0], categorys[0], timecodes[0],speaker[0], false, false,
                    true, channels[1], false);
            System.out.println("\n======================================");
            System.out.println("\nScenario #3");
            testScenario(languages[2], categorys[1], timecodes[1],speaker[0], false, false,
                    false, channels[2], false);
        } else if (url.contains("upload")){
            testScenario(languages[0], categorys[0], timecodes[0],speaker[0], false, false,
                    true, channels[1], false);
            System.out.println("\n======================================");
            System.out.println("\nScenario #2");
            testScenario(languages[2], categorys[1], timecodes[1],speaker[0], false, false,
                    false, channels[2],false);
        }else{
            testScenario(languages[2], categorys[1], timecodes[1],speaker[0], false, false,
                    false, channels[2],false);
            //System.out.println("\nScenario #2");
           // testScenario(languages[0], categorys[0], timecodes[0],speaker[0],false,
               //     false,true, channels[0], false);
            
        }


        System.out.println("Test Completed");

        System.out.println("======================================");
    }

    @BeforeClass
    public void beforeClass() {

        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        fullScreen(driver);
    }

    @AfterClass
    public void afterClass() throws IOException {

        screenshot(driver, "TranscriptionQuote");
        driver.quit();
    }

    private void raiseTicket(String language, String category, String timeCode,String speaker,
            boolean additionalQty, boolean tat, boolean basicPrice, boolean
            offer, String channel) throws AWTException, InterruptedException, IOException {

        /*driver.get(url);*/
       // url = "https://vananservices.com/Transcription-Quote.php";
        driver.get(url);
        Transcription transcription = new Transcription(driver);
        transcription.clickPersonal();
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
        transcription.selectSpeakercount(speaker);
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
        waitForProcessCompletion(40);
        double off = 0;
        if(offer) {
            off = transcription.getOfferDiscout();
        }
        if (basicPrice) {
            checkPrice(timeCode, offer, true, false, false,
                    false, true, false, false, true, true,
                    false,
                    transcription.getBasePrice(), transcription.getUnitCost(),
                    off, 0, transcription.getDiscountPrice(),
                    transcription.getVerbatim(), transcription.getTimeCode(),
                    //transcription.getESsubTotalCost(), 0,
                    0,0,
                    transcription.getExpedite(), transcription.getTotalCost(),
                    transcription.getAddtionalQtyCheck(),
                    transcription.getTranscationFee(), transcription
                            .getGrandTotal(), language);
        }
        if (tat) {
            checkPrice(timeCode, offer, true, true, false,
                    false, true, false, false, false, false, true,
                    transcription.getBasePrice(), transcription.getUnitCost(),
                    off, 0, transcription.getDiscountPrice(),
                    transcription.getVerbatim(), transcription.getTimeCode(),
                    0,0.5,//transcription.getESsubTotalCost(), 0.5,
                    transcription.getExpedite(), transcription.getTotalCost(),
                    transcription.getAddtionalQtyCheck(),
                    transcription.getTranscationFee(), transcription.getGrandTotal(), language);
        }
        if (language.equals(languages[0]) && category.equals(categorys[1])) {
            checkPrice(timeCode, offer, false, false, true,
                    false, false, false, true, false, false, false,
                    transcription.getBasePrice(), transcription.getUnitCost(),
                    off, 0, transcription.getDiscountPrice(),
                    transcription.getVerbatim(), transcription.getTimeCode(),
                    0,0.5,//transcription.getESsubTotalCost(), 0.5,
                    transcription.getExpedite(), transcription.getTotalCost(),
                    transcription.getAddtionalQtyCheck(),
                    transcription.getTranscationFee(), transcription.getGrandTotal(), language);
        }
        if (language.equals(languages[1])) {
            checkPrice(timeCode, offer, false, false, false,
                    true, false, false, false, false, false, false,
                    transcription.getBasePrice(), transcription.getUnitCost(),
                    off, 0, transcription.getDiscountPrice(),
                    transcription.getVerbatim(), transcription.getTimeCode(),
                    0,0,//transcription.getESsubTotalCost(), 0,
                    transcription.getExpedite(), transcription.getTotalCost(),
                    transcription.getAddtionalQtyCheck(),
                    transcription.getTranscationFee(), transcription.getGrandTotal(), language);
        }

        if (channel.equals(channels[2])) {

            if (transcription.isCustomMessageDisplayed()) {
                System.out.println("Quote info message is displayed");
            }
            /*transcription.clickGetQuote();
            if(transcription.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
                System.out.println("Accept button checked = > Pass");
            } else {
                System.out.println("Fail");
            }*/
            transcription.clickPrivacyPolicy();
            transcription.clickGetQuote();
        } else if (channel.equals(channels[1])) {
            /*transcription.clickProceedPayment();
            if(transcription.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
                System.out.println("Accept button checked = > Pass");
            } else {
                System.out.println("Fail");
            }*/
            transcription.clickPrivacyPolicy();
            transcription.clickProceedPayment();
        } else if (channel.equals(channels[0])) {
            /*transcription.clickEmailMeGetQuote();
            if(transcription.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
                System.out.println("Accept button checked = > Pass");
            } else {
                System.out.println("Fail");
            }*/
            transcription.clickPrivacyPolicy();
            transcription.clickEmailMeGetQuote();
        }

        waitForProcessCompletion(15);
        String currentUrl = driver.getCurrentUrl();
        if (!channel.equals(channels[1])) {

            checkCondition(currentUrl, "additional-information.php");
            AdditionalInformation additionalInformation = new AdditionalInformation
                    (driver);
            additionalInformation.enterCustomerName(name);
            additionalInformation.selectCountry(country);
            additionalInformation.enterPhoneNumber(phoneNumber);
            additionalInformation.selectServiceType(serviceType);
            //additionalInformation.selectAdditionalServices(additionalService);
            additionalInformation.enterComments(comment);
            additionalInformation.clickPrivacyPolicy();
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
            //evaluateCondition(message, dPrice, dPric);
            message = "Discounted price Total";
            evaluateCondition(message, dPrice * min, dPri);
        } else {
            dPrice = 0;
        }
        if(verbat) {
            message = "Verbatim";
            evaluateCondition(message, verbe * min, verba);
        }
        message = "Time Code";
        evaluateCondition(message, timePrice * min, timea);


        if (amount) {
            amo = bPrice * min;
        } else {
            if(lang.equals(languages[0])) {
                amo = offerPr + (dPrice * min);
            } else {
                amo = bPrice * min;
            }
        }

        if (exp) {

            message = "Subtotal";
            //evaluateCondition(message, amo + (dPrice * min) + (verbe * min) +
                    //(timePrice * min), sub);

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
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
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
                waitForProcessCompletion(10);
                  driver.findElement(By.id("view_btn")).click();
                 waitForProcessCompletion(10);
                System.out.println("Channel " + viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel"));
                if (viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel")
                        .contains(channel) && url.contains(viewTicketDetails
                        .getWebsite())) {

                    ticketID = tickets.get(i).substring(tickets.get(i).indexOf("VS"),
                            tickets.get(i).indexOf(service) - 1);
                    System.out.println((i + 1) + " : Channel = " +
                            viewTicketDetails.getRunTimeTicketFieldValues(
                                    "Channel"));
                    checkViewTicketInfo(language, typeOfService, channel);

                    System.out.println(channel + " Ticket ID: " + ticketID);
                    changeTicketStatus();
                    checkCRMEmailConversation(language, typeOfService,
                            timeCode, addtionalQty, tat,
                            basicPrice, offer);
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

    private static void getCRMCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/CRM.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }

    private void evaluateCondition(String message, String first,
                                   String second) {

        System.out.print(message + " : " + second);
        if ((first.replace("$","")).contains(second)) {

            System.out.print("\t Status : Pass\n");
        } else {

            System.out.print("\t Status : [Fail]\n");
            System.out.print("\t Expected : " + second + "\n");
            System.out.print("\t Actual : " + first + "\n");
        }
    }

    private void changeTicketStatus() {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("scroll(0, -250);");
        // Edit a ticket and moved the status into Others
        Edit edit = menus.clickEdit();

        edit.selectPaymentType("Full payment");
        edit.selectPaymentMode("Square");
        edit.selectStatus("Others");
        edit.clickUpdateButton();
        waitForProcessCompletion(10);
    }

    private void checkCRMEmailConversation(String language, String
            typeOfService, String timeCode, boolean addtionalQty, boolean tat,
            boolean basicPrice, boolean offer) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        emailConversation = menus.clickEmailConversation();
        emailConversation.clickReadMore("Quote");
        if (emailConversation.getServiceDetailsFromEmailHeading(service)) {
            System.out.println(service + " heading is correct");
        } else {
            System.out.println(service + " heading is wrong");
        }
        if (!language.equals(languages[2])) {
            evaluateCondition("Language",
                    emailConversation
                            .getTicketFieldValuesFromPayment("Language", false),
                    language);
            evaluateCondition("Category",
                    emailConversation
                            .getTicketFieldValuesFromPayment("Category", false), typeOfService);
            evaluateCondition("Minutes",
                    emailConversation
                            .getTicketFieldValuesFromPayment("Minutes", false), minute);
            evaluateCondition("Cost", emailConversation
                    .getTicketFieldValuesFromPayment("Cost",
                            false), "" + bPrice * min);
            evaluateCondition("Base price", emailConversation
                    .getTicketFieldValuesFromPayment("Base price",
                            false), "" + bPrice * min);
            if (basicPrice) {

                evaluateCondition("Discounted price", emailConversation
                        .getTicketFieldValuesFromPayment("Discounted price",
                                false), "" + amo);
            }
            if(offer) {

                evaluateCondition("New year discounted",
                        emailConversation.getTicketFieldValuesFromPayment(
                                "New year discounted",false), "" + amo);
            }
            if(!language.equals(languages[1])) {
                evaluateCondition("Verbatim",
                        emailConversation.getTicketFieldValuesFromPayment(
                                "Verbatim", false), "" + (verbe * min));
                evaluateCondition("Time code",
                        emailConversation.getTicketFieldValuesFromPayment(
                                "Time code", false), "" + (timePrice * min));
            }
            evaluateCondition("Grand total",
                    emailConversation.getTicketFieldValuesFromPayment(
                            "Grand total",false), "" + gtot);
            if (addtionalQty) {

                evaluateCondition("Additional quality check",
                        emailConversation.getTicketFieldValuesFromPayment(
                                "Additional quality check",false), "" + gtot);
            }

            evaluateCondition("Transaction fee",
                    emailConversation.getTicketFieldValuesFromPayment(
                            "Transaction fee",false), "" + trans);
            evaluateCondition("Order total",
                    emailConversation.getTicketFieldValuesFromPayment(
                            "Order total", false), "" + orderTot);
            /*evaluateCondition("File name", emailConversation
                   .getTicketFieldValuesFromPayment("File name", true),
                  fileName + fileExtention);
            evaluateCondition("Link", emailConversation
                  .getTicketFieldValuesFromPayment("Link", true), "Download");

            evaluateCondition("Special words", emailConversation
                    .getTicketFieldValuesFromPayment("Special words",
                    false), comment);
            if (tat) {

                evaluateCondition("Rush delivery", emailConversation
                        .getTicketFieldValuesFromPayment("Rush delivery",
                        false), "Rush delivery");
            } else {
                evaluateCondition("Standard delivery", emailConversation
                        .getTicketFieldValuesFromPayment("Standard delivery",
                        false), "Standard delivery");
            }
            evaluateCondition("Time code", emailConversation
                    .getTicketFieldValuesFromPayment("Time code will",
                    false), "Time code will");
            evaluateCondition("Free trial", emailConversation
                    .getTicketFieldValuesFromPayment("free trial",
                    false), "free trial");*/
        } else {
//evaluateCondition("Email Id",
                  //  emailConversation
                 //           .getTicketValuesFromPayment("Email Id",
                               //     false), mailId);
            evaluateCondition("File length",
                    emailConversation.getTicketValuesFromPayment("File " +
                            "length", false), minute);
            evaluateCondition("Category",
                    emailConversation.getTicketValuesFromPayment
                            ("Category", false),
                    typeOfService);

            evaluateCondition("Source language",
                    emailConversation.getTicketValuesFromPayment("Source " +
                            "language", false), language);

            evaluateCondition("Time code",
                    emailConversation.getTicketValuesFromPayment
                            ("Time code", false),
                    timeCode);
            evaluateCondition("Free trial",
                    emailConversation.getTicketValuesFromPayment
                            ("Free trial", false),
                    "Yes");
             /*evaluateCondition("File name",
                    emailConversation.getTicketValuesFromPayment
                            ("File name", true),
                    fileName + fileExtention);
            evaluateCondition("Link",
                    emailConversation.getTicketValuesFromPayment
                            ("Link", true),
                    "Download");*/
            }
        if (!language.equals(languages[1])) {
            if (emailConversation
                    .getParticularHeadingDetailsFromAllMsg(service + " " +
                            "Additional Information")) {
                System.out.println("Additional Info Message is present");
            } else {
                System.out.println("Additional Info Message is not present");
            }
        } else {
            if (emailConversation
                    .getParticularHeadingDetailsFromAllMsg(service + " Additional Information")) {
                System.out.println("This scenario not have, Additional Info " +
                        "page. But the Message is present");
            }
        }

        if(emailConversation
                .getParticularHeadingDetailsFromAllMsg("Others")){
            System.out.println("Others message is present");
        } else {
            System.out.println("Others message is not present");
        }
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
        waitForProcessCompletion(5);
        if(emailConversation
                .getParticularHeadingDetailsFromAllMsg("No Action")){
            System.out.println("No Action message is present");
        } else {
            System.out.println("No Action  message is not present");
        }

    }

    private void checkViewTicketInfo(String language, String typeOfService,
            String channel) {

        System.out.println("===========================================");
        System.out.println("Checking View Ticket Details");
        System.out.println("===========================================\n");
       // evaluateCondition("Email", viewTicketDetails
            //    .getEmailId(), mailId);
        evaluateCondition("Websites", url,
                viewTicketDetails.getWebsite());
        evaluateCondition("Channel", viewTicketDetails
                .getRunTimeTicketFieldValues("Channel"), channel);
        evaluateCondition("Service", viewTicketDetails
                .getServiceValues(), service);
        System.out.println("===========================================\n");
    }

    private void testScenario(String language, String category, String
            timeCode,String speaker, boolean addtionalQty, boolean tat, boolean basicPrice,
            String channel, boolean offer) throws AWTException,
            InterruptedException, IOException {

        raiseTicket(language, category, timeCode,speaker, addtionalQty, tat,
                basicPrice, offer, channel);
        getCRMCreadential();
        checkCRM(language, category, channel, timeCode, addtionalQty, tat,
                basicPrice, offer);
    }
}
