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

import com.vanancrm.PageObjects.MainPages.AdditionalInformation;
import com.vanancrm.PageObjects.MainPages.Captioning;

import com.vanancrm.Common.CaptioningPrice;
import com.vanancrm.Common.TestBase;

/**
 * Author - Manikavasagam (manikavasagam@vananservices.com)
 */
public class CaptioningQuote extends TestBase implements CaptioningPrice {

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

    private String fileName = "AutomationTesting";
    private String fileExtention = ".mp3";
    private String minute = "100";
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
        url = System.getProperty("website");
        System.out.println("\n======================================");
        System.out.println("\nScenario Started");
        System.out.println("\n======================================");
        System.out.println("\nScenario #1");
        if (!url.contains("Upload")) {

            testScenario(srclanguages[0], tarlanguage, fileFormats[0],
                    specificationPays[0],true, false, false, tiers[1],
                    channels[0]);
            System.out.println("\n======================================");
            System.out.println("\nScenario #2");
            testScenario(srclanguages[1], srclanguages[1], fileFormats[0],
                    specificationPays[1],false, true, false, tiers[2],
                    channels[1]);
            System.out.println("\n======================================");
            System.out.println("\nScenario #3");
            testScenario(srclanguages[2], tarlanguage, fileFormats[1],
                    specificationPays[1],true, false, false, tiers[0],
                    channels[2]);
        } else {
            testScenario(srclanguages[1], srclanguages[1], fileFormats[0],
                    specificationPays[1],false, true, false, tiers[2],
                    channels[1]);
            System.out.println("\n======================================");
            System.out.println("\nScenario #2");
            testScenario(srclanguages[2], tarlanguage, fileFormats[1],
                    specificationPays[1],true, false, false, tiers[0],
                    channels[2]);
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
    public void afterClass() {

        screenshot(driver, "CaptioningQuote");
        driver.quit();
    }

    private void raiseTicket(String srcLang, String targetLang,
            String fileFormat, String specificationPay, boolean translation,
            boolean transcription, boolean offer, int tier) throws AWTException,
            InterruptedException, IOException {

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
        waitForProcessCompletion(20);
        captioning.enterEmailId(mailId);
        waitForProcessCompletion(20);
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
            /*captioning.clickGetQuote();
            if(captioning.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
                System.out.println("Accept button checked = > Pass");
            } else {
                System.out.println("Fail");
            }*/
            captioning.clickPrivacyPolicy();
            captioning.clickGetQuote();
        } else if (srcLang.equals(srclanguages[1])) {
            /*captioning.clickProceedPayment();
            if(captioning.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
                System.out.println("Accept button checked = > Pass");
            } else {
                System.out.println("Fail");
            }*/
            captioning.clickPrivacyPolicy();
            captioning.clickProceedPayment();
        } else if (srcLang.equals(srclanguages[0]) || srcLang.equals
                (srclanguages[2])) {
            /*captioning.clickEmailMeGetQuote();
            if(captioning.getToolTipMessage().contains("Please agree to terms and conditions to proceed")) {
                System.out.println("Accept button checked = > Pass");
            } else {
                System.out.println("Fail");
            }*/
            captioning.clickPrivacyPolicy();
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
            additionalInformation.clickPrivacyPolicy();
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
        double trascription = 0;
        double traslation = 0;
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
        Cookie name = new Cookie("TEST_MODE", "TEST_MODE");
        driver.manage().addCookie(name);
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
                    checkCRMEmailConversation(srcLang, targetLang, fileFormat,
                            specificationPay, translation, transcription, offer,
                            channel);
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

    private void checkCRMEmailConversation(String srcLang, String targetLang,
            String fileFormat, String specificationPay, boolean translation,
            boolean transcription, boolean offer, String channel) {

        System.out.println("\n===========================================");
        System.out.println("Checking Email Conversation");
        System.out.println("===========================================\n");
        emailConversation = menus.clickEmailConversation();
        String message = "";
        if(channel.equals("Direct Payment")) {
            message = "Price Quote";
        } else if (channel.equals("Email Quote")) {
            message = channel;
        } else if (channel.equals("Request for Quote")) {
            message = "Quote";
        }
        emailConversation.clickReadMore(message);
        if (emailConversation.getServiceDetailsFromEmailHeading(service)) {
            System.out.println(service + " heading is correct");
        } else {
            System.out.println(service + " heading is wrong");
        }
        if (!srcLang.equals(srclanguages[2])) {
            evaluateCondition("Source Language", emailConversation
                    .getTicketFieldValuesFromPayment("Source Language", false),
                    srcLang);
            evaluateCondition("Target Language", emailConversation
                    .getTicketFieldValuesFromPayment("Target Language", false),
                    targetLang);
            evaluateCondition("Purpose", emailConversation
                    .getTicketFieldValuesFromPayment("Purpose", false),
                    specificationPay);
            evaluateCondition("Formatting", emailConversation
                    .getTicketFieldValuesFromPayment("Formatting", false),
                    fileFormat);
            evaluateCondition("Minutes", emailConversation
                    .getTicketFieldValuesFromPayment("Minutes", false),
                    minute);
            evaluateCondition("Cost per minutes", emailConversation
                            .getTicketFieldValuesFromPayment("Cost per minutes", false),
                    "" + bPrice);
            evaluateCondition("Cost", emailConversation
                    .getTicketFieldValuesFromPayment("Cost",
                            false), "" + (bPrice * min));
            if(translation) {
                evaluateCondition("Translation fee", emailConversation
                        .getTicketFieldValuesFromPayment("Translation fee", false),
                        "" + btransl);
            }
            if(transcription) {
                evaluateCondition("Transcription fee", emailConversation
                        .getTicketFieldValuesFromPayment("Transcription fee", false),
                        "" + btransc);
            }
            if(offer) {
                evaluateCondition("New year offer", emailConversation
                        .getTicketFieldValuesFromPayment("New year offer", false),
                        "" + offerPr);
            }
            if(srcLang.equals(targetLang) && targetLang.equals(targetLang)) {

            } else {
                evaluateCondition("Timecode fee", emailConversation
                        .getTicketFieldValuesFromPayment("Timecode fee", false),
                        "" + btimecode);
            }
            evaluateCondition("Grand total",
                    emailConversation.getTicketFieldValuesFromPayment(
                            "Grand total", false), "" + gtot);
            evaluateCondition("Transaction fee",
                    emailConversation.getTicketFieldValuesFromPayment(
                            "Transaction fee", false), "" + trans);
            evaluateCondition("Order total",
                    emailConversation.getTicketFieldValuesFromPayment(
                            "Order total", false), "" + orderTot);

            /*evaluateCondition("Special words", emailConversation
                    .getTicketFieldValuesFromPayment("Special words",
                            false), comment);*/
        } else {

            evaluateCondition("Email Id", emailConversation
                    .getTicketValuesFromPayment("Email Id",
                            false), mailId);
            evaluateCondition("Source Language", emailConversation
                    .getTicketValuesFromPayment("Source Language",
                            false), srcLang);
            evaluateCondition("Target Language", emailConversation
                    .getTicketValuesFromPayment("Target Language",
                            false), targetLang);
            evaluateCondition("Purpose", emailConversation
                    .getTicketValuesFromPayment("Purpose",
                            false), specificationPay);
            evaluateCondition("Format", emailConversation
                    .getTicketValuesFromPayment("Format",
                            false), fileFormat);
            evaluateCondition("Minutes", emailConversation
                    .getTicketValuesFromPayment("Minutes",
                            false), minute);
            /*evaluateCondition("Special words", emailConversation
                    .getTicketValuesFromPayment("Special words",
                            false), comment);*/
        }

        if (!srcLang.equals(srclanguages[1])) {
            if (emailConversation
                    .getParticularHeadingDetailsFromAllMsg(service + " " +
                            "Additional Information")) {
                System.out.println("Additional Info Message is present");
            } else {
                System.out.println("Additional Info Message is not present");
            }
        } else {
            if (emailConversation.getParticularHeadingDetailsFromAllMsg(
                    service + " Additional Information")) {

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
}
