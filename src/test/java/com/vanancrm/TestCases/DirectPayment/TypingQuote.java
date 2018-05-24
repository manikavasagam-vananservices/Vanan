package com.vanancrm.TestCases.DirectPayment;

import java.awt.AWTException;

import java.io.FileReader;
import java.io.IOException;

import java.util.List;
import java.util.Properties;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;

import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanan.CRM.PageObjects.WholeSitePages.ViewTicketDetails;

import com.vanancrm.Common.TestBase;
import com.vanancrm.Common.TypingPrice;

import com.vanancrm.PageObjects.MainPages.AdditionalInformation;
import com.vanancrm.PageObjects.MainPages.Typing;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class TypingQuote extends TestBase implements TypingPrice {

    private static String username = "";
    private static String password = "";
    private WebDriver driver;

    private String[] channels = {"Email Quote", "Direct Payment",
            "Request for Quote"};
    private String[] categorys = {"General", "Legal"};
    private String[] languages = {"English", "Spanish", "Tamil"};
    private String[] timecodes = {"Every 3 sec", "Every 1 minute",
            "Not required"};
    private String[] formattings = {"Yes", "No", "Handwritten"};
    private String[] fileTypes = {"Document", "Audio/Video"};
    private int[] tiers = {1, 2};
    private String address = "chennai";
    private String comment = "Automation Testing";
    private String country = "United States";
    private String fileName = "Testing";
    private String fileExtention = "";
    private String minute = "2";
    private String mailId = "automation.vananservices@gmail.com";
    private String name = "Automation";
    private String phoneNumber = "1-888-535-5668";
    private String service = "Typing";
    private String serviceType = "Weekly";
    private String url = "";

    private double bPrice = 0;
    private double notaryFee = 0;
    private double processingFee = 0;
    private double mailingFee = 0;
    private double trans = 0;
    private double gtot = 0;
    private int min;
    private double offerPr = 0;
    private double orderTot = 0;
    private double timePrice = 0;
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
    public void typingServices() throws IOException,
            InterruptedException, AWTException {
        url = System.getProperty("website");
        System.out.println("\n======================================");
        System.out.println("\nScenario Started");
        System.out.println("\n======================================");
        System.out.println("\nScenario #1");
        if (!url.contains("Upload")) {

            testScenario(fileTypes[0], languages[0], categorys[0], "",
                    formattings[0], false, tiers[0],
                    true, true, false, true, true, false, channels[0]);
            /*testScenario(fileTypes[0], languages[0], categorys[0], "",
                    formattings[1],false, tiers[0],
                    false, false, false, true, true, false, channels[0]);
            testScenario(fileTypes[0], languages[0], categorys[1], "",
                    formattings[1],false, tiers[0],
                    true, false, false, true, true, false, channels[0]);*/
            System.out.println("\nScenario #2");
            System.out.println("\n======================================");
            testScenario(fileTypes[1], languages[0], categorys[0], timecodes[0],
                    "", false, tiers[1],
                    true, false, true, false, false, true, channels[1]);
            /*testScenario(fileTypes[1], languages[0], categorys[0], timecodes[0],
                    "",false, tiers[0],
                    false, false, false, false, false, true, channels[0]);
            testScenario(fileTypes[1], languages[0], categorys[1], timecodes[1],
                    "",false, tiers[0],
                    true, false, false, false, false, false, channels[0]);*/
            System.out.println("\nScenario #3");
            System.out.println("\n======================================");
            testScenario(fileTypes[0], languages[2], categorys[0], "",
                    formattings[0], false, 0,
                    true, true, false, true, true, false, channels[2]);
            /*testScenario(fileTypes[0], languages[1], categorys[0], "",
                    formattings[1],false, tiers[1],
                    false, false, false, true, true, false, channels[0]);
            testScenario(fileTypes[0], languages[1], categorys[1], "",
                    formattings[1],false, tiers[1],
                    true, false, false, true, true, false, channels[0]);*/
        } else {
            testScenario(fileTypes[1], languages[0], categorys[0], timecodes[0],
                    "", false, tiers[1],
                    true, false, true, false, false, true, channels[1]);
            System.out.println("\nScenario #2");
            System.out.println("\n======================================");
            testScenario(fileTypes[0], languages[2], categorys[0], "",
                    formattings[0], false, 0,
                    true, true, false, true, true, false, channels[2]);
        }

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

        screenshot(driver, "TypingQuote");
        driver.quit();
    }

    private void raiseTicket(String fileType, String language, String category,
            String timeCode, String format, boolean offer, int tier, boolean
            cate, boolean formatting, boolean usnativesp, boolean notar, boolean
            mailin, boolean verb, String channel) throws AWTException, InterruptedException,
            IOException {

        driver.get(url);
        Typing typing = new Typing(driver);
        typing.selectFileType(fileType);
        if (fileType.equals(fileTypes[0])) {
            typing.enterPageCount(minute);
        } else if (fileType.equals(fileTypes[1])) {
            typing.enterMinutes(minute);
        }
        typing.selectLanguageFrom(language);
        typing.selectCategory(category);
        if (fileType.equals(fileTypes[0])) {

            typing.selectFormatting(format);
            if (notar) {
                typing.selectNotarization();
            }
            if (mailin) {
                typing.selectRequestMailCopy(country, address);
            }
            fileExtention = ".txt";
        } else if (fileType.equals(fileTypes[1])) {
            typing.selectTimeCode(timeCode);
            typing.selectVerbatim();
            if (language.equals(languages[0])) {
                if (usnativesp) {
                    typing.selectUSNativeTranscriber();
                }
            }
            fileExtention = ".mp3";
        }
        typing.uploadFile(driver, fileName, fileExtention);
        waitForProcessCompletion(60);
        if (fileType.equals(fileTypes[1])) {
            typing.enterFileLength(minute);
        }
        typing.enterComments(comment);
        waitForProcessCompletion(20);
        typing.enterEmailId(mailId);
        double off = 0;
        if (offer) {
            //off = typing.getOfferDiscount();
        }

       if(!channel.equals("Request for Quote")) {
            checkPrice(fileType, tier, cate, formatting, timeCode,
                    usnativesp, offer, notar, mailin, typing.getBasePrice(),
                    typing.getUnitCost(), off, typing.getGrandTotal(),
                    typing.getTransactionFee(), typing.getOrderTotal(),
                    verb, typing.getVerbatim(), typing.getTimeCode());
        } else {
            System.out.println("Quote Scenario no price displayed");
        }
        if (language.equals(languages[2])) {

            /*if (typing.isCustomMessageDisplayed()) {
                System.out.println("Quote info message is displayed");
            }*/
        }
        if (tier == 0) {
            /*typing.clickGetQuote();
            if(typing.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
                System.out.println("Accept button checked = > Pass");
            } else {
                System.out.println("Fail");
            }*/
            typing.clickPrivacyPolicy();
            typing.clickGetQuote();
        } else if (tier == tiers[1]) {
            /*typing.clickProceedPayment();
            if(typing.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
                System.out.println("Accept button checked = > Pass");
            } else {
                System.out.println("Fail");
            }*/
            typing.clickPrivacyPolicy();
            typing.clickProceedPayment();
        } else if (tier == tiers[0]) {
            /*typing.clickEmailMeGetQuote();
            if(typing.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
                System.out.println("Accept button checked = > Pass");
            } else {
                System.out.println("Fail");
            }*/
            typing.clickPrivacyPolicy();
            typing.clickEmailMeGetQuote();
        }

        waitForProcessCompletion(15);
        String currentUrl = driver.getCurrentUrl();
        if (tier != 2) {

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

    private void checkPrice(String fileType, int tier, boolean category,
            boolean formatting, String timeCode, boolean usnativeSpeaker,
            boolean offer, boolean notary, boolean mailing, double baPrice,
            double ucost, double oPri, double gtotal, double transFee, double
            total, boolean verbat, double verba, double timea) {
        System.out.println("\n======================================");
        System.out.println("\n Website UI Price Checking");
        System.out.println("\n======================================");
        min = Integer.parseInt(minute);
        if (fileType.equals(fileTypes[0]) && tier == tiers[0]) {
            bPrice = basePri[1];
        } else if (fileType.equals(fileTypes[0]) && tier == tiers[1]) {
            bPrice = basePri[3];
        } else if (fileType.equals(fileTypes[1])) {
            bPrice = basePri[0];
        }
        if (category && tier == tiers[0]) {
            bPrice = basePri[2];
        } else if (category && tier == tiers[1]) {
            bPrice = basePri[3];
        }
        if (fileType.equals(fileTypes[1])) {
            if (category) {
                bPrice = basePri[1];
            } else {
                bPrice = basePri[0];
            }
        }
        if (timeCode.equals(timecodes[1])) {

            timePrice = timecodePri[0];
        } else if (timeCode.equals(timecodes[2])) {
            timePrice = 0;
        } else {
            timePrice = timecodePri[1];
        }

        if (formatting && tier == tiers[0]) {
            bPrice = formattingPri[0];
        } else if (formatting && tier == tiers[1]) {
            bPrice = formattingPri[1];
        }
        if (usnativeSpeaker) {
            bPrice = usNativeTranscriberPri;
        }
        String message = "Base price Actual";
        evaluateCondition(message, bPrice, baPrice);
        message = "Base price Total";
        evaluateCondition(message, bPrice * min, ucost);
        if (offer) {
            message = "New year offer";
            offerPr = (bPrice * min) - (offerPri[0] * (bPrice * min));
            evaluateCondition(message, offerPr, oPri);
        } else {
            offerPr = (bPrice * min);
        }
        if (verbat) {
            verbe = verbatimPri;
        }
        if (notary) {
            notaryFee = notaryPri;
            processingFee = processingFeePri;
        }
        if (mailing) {
            mailingFee = mailingPri;
        }

        if (fileType.equals(fileTypes[0])) {

            gtot = offerPr + notaryFee + mailingFee + processingFee;
        } else if (fileType.equals(fileTypes[1])) {

            if (verbat) {
                message = "Verbatim";
                evaluateCondition(message, verbe * min, verba);
            }
            message = "Time Code";
            evaluateCondition(message, timePrice * min, timea);
            System.out.println(offerPr + "" + (timePrice * min) + "" + (verbe * min));
            gtot = offerPr + (timePrice * min) + (verbe * min);
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

    private void checkCRM(String fileType, String language, String category,
            String timeCode, String format, boolean offer, int tier, boolean
            cate, boolean formatting, boolean usnativesp, boolean notar, boolean
            mailin, boolean verb, String channel) {

        driver.get("https://secure-dt.com/crm/user/login");
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
        Login login = new Login(driver);
        dashBoardPage = login.signIn(username, password);
        menus = dashBoardPage.clickAllProcess();
        checkTickets(menus, fileType, language, category, timeCode, format, offer, tier,
                cate, formatting, usnativesp, notar, mailin, verb, channel);
        waitForProcessCompletion(10);
        menus.clickSignOut();
    }

    private void checkTickets(Menus menus, String fileType, String language,
            String category, String timeCode, String format, boolean offer,
            int tier, boolean cate, boolean formatting, boolean usnativesp,
            boolean notar, boolean mailin, boolean verb, String channel) {

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
                        .contains(channel) && url.contains(viewTicketDetails
                        .getRunTimeTicketFieldValues("Websites"))) {

                    ticketID = tickets.get(i).substring(tickets.get(i).indexOf("VS"),
                            tickets.get(i).indexOf(service) - 1);
                    System.out.println((i + 1) + " : Channel = " +
                            viewTicketDetails.getRunTimeTicketFieldValues(
                                    "Channel"));
                    checkViewTicketInfo(channel);

                    System.out.println(channel + " Ticket ID: " + ticketID);
                    changeTicketStatus();
                    checkCRMEmailConversation(fileType, language, category,
                            timeCode, format, offer, tier, cate, formatting,
                            usnativesp, notar, mailin, verb, channel);
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

    private void changeTicketStatus() {

        // Edit a ticket and moved the status into Others
        Edit edit = menus.clickEdit();
        edit.selectStatus("Others");
        edit.clickUpdateButton();
        waitForProcessCompletion(10);
    }

    private void checkCRMEmailConversation(String fileType, String language,
            String category, String timeCode, String format, boolean offer,
            int tier, boolean cate, boolean formatting, boolean usnativesp,
            boolean notar, boolean mailin, boolean verb, String chanel) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        emailConversation = menus.clickEmailConversation();
        String message = "";
        if(chanel.equals("Direct Payment")) {
            message = "Price Quote";
        } else if (chanel.equals("Email Quote")) {
            message = chanel;
        } else if (chanel.equals("Request for Quote")) {
            message = "Quote";
        }
        emailConversation.clickReadMore(message);
        if (emailConversation.getServiceDetailsFromEmailHeading(service)) {
            System.out.println(service + " heading is correct");
        } else {
            System.out.println(service + " heading is wrong");
        }
        if (tier != 0) {
            evaluateCondition("Language", emailConversation
                            .getTicketFieldValuesFromPayment("Language", false),
                    language);
            evaluateCondition("Category", emailConversation
                    .getTicketFieldValuesFromPayment("Category", false), category);

            if (verb) {
                evaluateCondition("Verbatim", emailConversation
                                .getTicketFieldValuesFromPayment("Verbatim", false),
                        "Yes");
            }

            if (fileType.equals(fileTypes[1])) {
                evaluateCondition("Cost per minute", emailConversation
                        .getTicketFieldValuesFromPayment("Cost per minute",
                                false), "" + bPrice);
                evaluateCondition("Minutes", emailConversation
                        .getTicketFieldValuesFromPayment("Minutes", false), minute);
                evaluateCondition("Time code", emailConversation
                                .getTicketFieldValuesFromPayment("Time code", false),
                        "" + (timePrice * min));
            } else if (fileType.equals(fileTypes[0])) {
                evaluateCondition("Number of page(s)", emailConversation
                        .getTicketFieldValuesFromPayment("Number of page(s)",
                                false), minute);
                evaluateCondition("Cost per page", emailConversation
                        .getTicketFieldValuesFromPayment("Cost per page",
                                false), "" + bPrice);
            }
            if (notar) {
                evaluateCondition("Notary", emailConversation
                        .getTicketFieldValuesFromPayment("Notary",
                                false), "" + notaryFee);
                evaluateCondition("Processing fee", emailConversation
                        .getTicketFieldValuesFromPayment("Processing fee",
                                false), "" + processingFee);
            }
            if (mailin) {
                evaluateCondition("Mailing", emailConversation
                        .getTicketFieldValuesFromPayment("Mailing",
                                false), "" + mailingFee);
            }
            evaluateCondition("Cost", emailConversation
                    .getTicketFieldValuesFromPayment("Cost",
                            false), "" + bPrice * min);
            evaluateCondition("Grand total", emailConversation
                    .getTicketFieldValuesFromPayment("Grand total",
                            false), "" + gtot);
            evaluateCondition("Transaction fee", emailConversation
                    .getTicketFieldValuesFromPayment("Transaction fee",
                            false), "" + trans);
            evaluateCondition("Order total", emailConversation
                    .getTicketFieldValuesFromPayment("Order total",
                            false), "" + orderTot);
        } else {

            evaluateCondition("Email Id", emailConversation
                    .getTicketValuesFromPayment("Email Id", false), mailId);
            evaluateCondition("File Type", emailConversation
                    .getTicketValuesFromPayment("File Type", false), fileType);
            evaluateCondition("Language", emailConversation
                    .getTicketValuesFromPayment("Language", false), language);

            evaluateCondition("Category", emailConversation
                    .getTicketValuesFromPayment("Category", false), category);

            if (fileType.equals(fileTypes[0])) {
                evaluateCondition("No of pages", emailConversation
                        .getTicketValuesFromPayment("No of pages", false), minute);
                if (formatting) {
                    evaluateCondition("Format", emailConversation
                            .getTicketValuesFromPayment("Format", false), format);
                } else {
                    evaluateCondition("Format", emailConversation
                                    .getTicketValuesFromPayment("Format", false),
                            "No");
                }
                if (mailin) {
                    evaluateCondition("Mailing country", emailConversation
                            .getTicketValuesFromPayment("Mailing country",
                                    false), country);
                    evaluateCondition("Physical address", emailConversation
                            .getTicketValuesFromPayment("Physical address",
                                    false), address);
                } else {
                    evaluateCondition("Mailed", emailConversation
                            .getTicketValuesFromPayment("Mailed", false), "No");
                }
                if (notar) {
                    evaluateCondition("Notarized", emailConversation
                            .getTicketValuesFromPayment("Notarized", false), "Yes");
                } else {
                    evaluateCondition("Notarized", emailConversation
                            .getTicketValuesFromPayment("Notarized", false), "No");
                }
            } else if (fileType.equals(fileTypes[1])) {
                evaluateCondition("Minutes", emailConversation
                        .getTicketValuesFromPayment("Minutes", false), minute);

                evaluateCondition("Time code", emailConversation
                                .getTicketValuesFromPayment("Time code", false),
                        "" + timePrice * min);

                evaluateCondition("Mailed", emailConversation
                        .getTicketValuesFromPayment("Mailed", false), "No");
                evaluateCondition("Notarized", emailConversation
                        .getTicketValuesFromPayment("Notarized", false), "No");
                if (verb) {
                    if (tier == tiers[1]) {
                        evaluateCondition("Verbatim", emailConversation
                                .getTicketValuesFromPayment("Verbatim",
                                        false), "" + verbe * min);
                    } else {
                        evaluateCondition("Verbatim", emailConversation
                                .getTicketValuesFromPayment("Verbatim", false), "Yes");
                    }

                }
                if (usnativesp) {
                    evaluateCondition("U.S Native Speaker", emailConversation
                                    .getTicketValuesFromPayment("U.S Native Speaker", false),
                            "Yes");
                }
            }
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
        
        if (emailConversation
            .getParticularHeadingDetailsFromAllMsg("Others")) {
            System.out.println("Others message is present");
        } else {
            System.out.println("Others message is not present");
        }
        System.out.println("===========================================");
        emailConversation.clickNoActionButton();
        waitForProcessCompletion(5);
        if (emailConversation
            .getParticularHeadingDetailsFromAllMsg("No Action")) {
            System.out.println("No Action message is present");
        } else {
            System.out.println("No Action  message is not present");
        }
        System.out.println("===========================================");
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

    private void testScenario(String fileType, String language, String category,
            String timeCode, String format, boolean offer, int tier, boolean
            cate, boolean formatting, boolean usnativesp, boolean notar, boolean
            mailin, boolean verb, String channel) throws AWTException,
            InterruptedException, IOException {

        raiseTicket(fileType, language, category, timeCode, format, offer, tier,
                cate, formatting, usnativesp, notar, mailin, verb, channel);
        getCRMCreadential();
        checkCRM(fileType, language, category, timeCode, format, offer, tier,
                cate, formatting, usnativesp, notar, mailin, verb, channel);
    }
}
