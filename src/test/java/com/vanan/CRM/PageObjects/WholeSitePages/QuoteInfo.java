package com.vanan.CRM.PageObjects.WholeSitePages;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

public class QuoteInfo extends AccessingElement {

    private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

    public QuoteInfo(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    //
    @FindBy(name="serviceargs[notary]")
    private WebElement notary;

    @FindBy(name="serviceargs[mailed]")
    private WebElement mailing;

    @FindBy(name="serviceargs[timecode]")
    private WebElement timecode;

    @FindBy(name="serviceargs[verbatim]")
    private WebElement verbatim;

    @FindBy(name="serviceargs[qc]")
    private WebElement qc;

    @FindBy(name="serviceargs[certificate]")
    private WebElement certificate;
    //
    @FindBy(name="invoice_details[invoice][offers]")
    private WebElement discountCategory;

    @FindBy(name="invoice_details[invoice][discout_type]")
    private WebElement discountPercentage;

    @FindBy(name="invoice_details[invoice][discount]")
    private WebElement discountValues;

    @FindBy(name="invoice_details[invoice][rush_type]")
    private WebElement expedite;

    @FindBy(name="invoice_details[invoice][rush]")
    private WebElement expediteValue;

    @FindBy(name="invoice_details[invoice][native]")
    private WebElement nativeElement;

    @FindBy(name="invoice_details[invoice][native_amt]")
    private WebElement nativeValue;

    @FindBy(name="invoice_details[invoice][non_native_amt]")
    private WebElement nonNativeValue;

    @FindBy(name="invoice_details[invoice][notary_amt]")
    private WebElement notarizationElement;

    @FindBy(name="invoice_details[invoice][certificate_amt]")
    private WebElement certificateElement;

    @FindBy(name="invoice_details[invoice][processing_fee_amt]")
    private WebElement serviceFeeElement;

    @FindBy(name="invoice_details[invoice][mailed_amt]")
    private WebElement mailingElement;

    @FindBy(name="invoice_details[invoice][timecode_amt]")
    private WebElement timeCodeElement;

    @FindBy(name="invoice_details[invoice][verbatim_amt]")
    private WebElement verbatimElement;

    @FindBy(id="total")
    private WebElement total;

    @FindBy(id="discount_display")
    private WebElement promoDiscount;

    @FindBy(id="sub_total")
    private WebElement subTotal;

    @FindBy(id="rush_amount")
    private WebElement expediteFee;

    @FindBy(id="shippingamount")
    private WebElement transactionFee;

    @FindBy(name="invoice_details[invoice][qc_amt]")
    private WebElement qcFee;

    @FindBy(id="invoice_details[invoice][actual_total]")
    private WebElement orderValue;

    @FindBy(id="quote_info_btn")
    private WebElement updateQuoteInfoButton;

    @FindBy(id="pre-mail_btn")
    private WebElement preMailButton;

    @FindBy(id="movetoallocationbtn")
    private WebElement moveToLocationButton;

    @FindBy(id="cancel")
    private WebElement closeButton;

    @FindBy(xpath="//table[@id='process_list']/tbody/tr")
    private List<WebElement> tableRows;

    public void selectNotary() {

        if(notary.isSelected() != true) {
            clickElement(notary);
        }

    }

    public void deselectNotary() {

        if(notary.isSelected() == true) {
            clickElement(notary);
        }
    }

    public void selectMailing() {

        if(mailing.isSelected() != true) {
            clickElement(mailing);
        }

    }

    public void deselectMailing() {

        if(mailing.isSelected() == true) {
            clickElement(mailing);
        }
    }

    public void selectTimeCode() {

        if(timecode.isSelected() != true) {
            clickElement(timecode);
        }

    }

    public void deselectTimeCode() {

        if(timecode.isSelected() == true) {
            clickElement(timecode);
        }
    }

    public void selectVerbatim() {

        if(verbatim.isSelected() != true) {
            clickElement(verbatim);
        }

    }

    public void selectCertificate() {

        if(certificate.isSelected() != true) {
            clickElement(certificate);
        }

    }

    public void deselectVerbatim() {

        if(verbatim.isSelected() == true) {
            clickElement(verbatim);
        }
    }

    public void selectQc() {

        if(qc.isSelected() != true) {
            clickElement(qc);
        }

    }

    public void deselectQc() {

        if(qc.isSelected() == true) {
            clickElement(qc);
        }
    }

    public void selectDiscount(String discount) {

        selectDropDown(discountCategory, discount);
    }

    public void selectDiscountPercent(String discountPercent) {

        selectDropDown(discountPercentage, discountPercent);
    }

    public void enterDiscountValues(String discountValue) {

        enterTestBoxValues(discountValues, discountValue);
    }

    public void enterQualityCheckValues(String qcValue) {

        enterTestBoxValues(qcFee, qcValue);
    }

    public void selectExpedite() {

        if(expedite.isSelected() != true) {
            clickElement(expedite);
        }
    }

    public void enterExpediteValues(String expediteValues) {
        enterTestBoxValues(expediteValue, expediteValues);
    }

    public void selectNative() {

        if(nativeElement.isSelected() != true) {
            clickElement(nativeElement);
        }

    }

    public void deselectNative() {

        if(nativeElement.isSelected() == true) {
            clickElement(nativeElement);
        }
    }

    public double getNativeValues() {

        return Double.parseDouble(getElementValues(nativeValue));
    }

    public double getNonNativeValues() {

        return Double.parseDouble(getElementValues(nonNativeValue));
    }

    public void enterNativeValues(String nativeValues) {

        enterTestBoxValues(nativeValue, nativeValues);
    }

    public void enterNonNativeValues(String nonNatives) {

        enterTestBoxValues(nonNativeValue, nonNatives);
    }

    public void enterNotarizationValues(String notarizationValues) {
        enterTestBoxValues(notarizationElement, notarizationValues);
    }

    public void enterCertificationValues(String certificateValues) {
        enterTestBoxValues(certificateElement, certificateValues);
    }

    public void enterServiceFeeValues(String serviceFeeValues) {
        enterTestBoxValues(serviceFeeElement, serviceFeeValues);
    }

    public void enterMailingValues(String mailingValues) {
        enterTestBoxValues(mailingElement, mailingValues);
    }

    public void enterTimeCodeValues(String timecodeValues) {
        enterTestBoxValues(timeCodeElement, timecodeValues);
    }

    public void enterVerbatimValues(String verbatimValues) {
        enterTestBoxValues(verbatimElement, verbatimValues);
    }

    public double getTotal() {

        return Double.parseDouble(getAttributeValues(total, "value"));
    }

    public double getPromoDiscount() {

        return Double.parseDouble(getAttributeValues(promoDiscount, "value"));
    }

    public double getSubTotal() {

        return Double.parseDouble(getAttributeValues(subTotal, "value"));
    }

    public double getExpediteFee() {

        return Double.parseDouble(getAttributeValues(expediteFee, "value"));
    }

    public double getTransactionFee() {

        return Double.parseDouble(getAttributeValues(transactionFee, "value"));
    }

    public double getOrderValue() {

        return Double.parseDouble(getAttributeValues(orderValue, "value"));
    }

    public void clickClose() {

        clickElement(closeButton);
    }

    public void clickMoveToLocation() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(moveToLocationButton).build();
        mouseOverHome.perform();
        clickElement(moveToLocationButton);
    }

    public void clickPreviewEmail() {

        clickElement(preMailButton);
    }

    public void clickUpdateQuoteInfo() {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(updateQuoteInfoButton).build();
        mouseOverHome.perform();
        clickElement(updateQuoteInfoButton);
    }

    private int getTableRowSize() {

        return  tableRows.size();
    }

    public boolean changeFileDetailsAndCost(String fileName, String sLang,
                                            String dLang, String services, String pagelenhr, String pagelenmin,
                                            String cost, String message) {

        WebElement fileElement;
        WebElement sourceLang;
        WebElement destinationLang;
        WebElement service;
        WebElement pagelenghr;
        WebElement pagelengmin;
        WebElement costPerPage;
        WebElement comments;
        WebElement totalCost;
        boolean result = false;
        double perCost = Double.parseDouble(cost);
        double perPageLenHr = Double.parseDouble(pagelenhr);
        double perPageLenMin;
        if (pagelenmin != "" && pagelenmin !=null) {
            perPageLenMin = Double.parseDouble(pagelenmin);
            if(perPageLenMin >= 1) {
                perPageLenHr = perPageLenHr + 1;
            }
        }
        double total = perCost * perPageLenHr;
        int i = getCurrentRowNumber(fileName);
        if (i>=2) {
            fileElement = driver.findElement(By.xpath(
                    "//table[@id='process_list']/tbody/tr["
                            + i + "]/td[2]/a/span"));
            if(getElementValues(fileElement).equals(fileName)) {
                fileElement = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr["
                                + i + "]/td[2]/input[@name="
                                + "'quote_details[file_selected][]']"));
                clickElement(fileElement);
                sourceLang = driver.findElement(By.id("quote_details[source_lang][]"));
                selectDropDown(sourceLang, sLang);
                destinationLang = driver.findElement(By.id("quote_details[target_lang][]"));
                selectDropDown(destinationLang, dLang);
                service = driver.findElement(By.id("quote_details[service_id][]"));
                selectDropDown(service, services);
                pagelenghr = driver.findElement(By.xpath("//table[@id='process_list']/tbody/tr["
                        + i + "]//*//input[@name='quote_details[page_length_job_hr][]']"));
                enterTestBoxValues(pagelenghr, pagelenhr);
                if (pagelenmin != "" && pagelenmin !=null) {
                    pagelengmin = driver.findElement(By.xpath("//table[@id='process_list']/tbody/tr["
                            + i + "]//*//input[@name='quote_details[page_length_job_min][]']"));
                    enterTestBoxValues(pagelengmin, pagelenmin);
                }
                costPerPage = driver.findElement(By.xpath("//table[@id='process_list']/tbody/tr["
                        + i + "]//*//input[@id='quote_details[cost][]']"));
                enterTestBoxValues(costPerPage, cost);
                comments = driver.findElement(By.xpath("//table[@id='process_list']/tbody/tr["
                        + i + "]//*//textarea[@name='quote_details[comments][]']"));
                enterTestBoxValues(comments, message);
                totalCost = driver.findElement(By.xpath("//table[@id='process_list']/tbody/tr["
                        + i + "]//*//input[@name='quote_details[total_cost][]']"));
                System.out.println("PCost : " + getAttributeValues(totalCost, "value"));
                if (total == Double.parseDouble(getAttributeValues(totalCost, "value"))) {
                    result = true;
                }
            }
        }
        return result;
    }

    public int getCurrentRowNumber (String fileName) {

        WebElement fileElement;
        int rowNumber = 0;
        for (int i=2; i< getTableRowSize(); i++) {
            fileElement = driver.findElement(By.xpath(
                    "//table[@id='process_list']/tbody/tr["
                            + i + "]/td[2]/a/span"));
            if(getElementValues(fileElement).equals(fileName)) {
                rowNumber = i;
                break;
            }
        }
        return rowNumber;
    }


    public void setFileDetails(String fileName, String srcLang, String targLang,
            String service, String mp, String cost, String misComment, boolean
            minutes) throws IOException {

        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 1; i <= elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/a/span"));
            //System.out.println("Entering)))))))" + element.getText());
            if(element.getText().contains(fileName)) {
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//input[@name='quote_details[file_selected" +
                                "][]']"));
                if(element.isSelected()!=true) {
                    clickElement(driver.findElement(By.xpath(
                            "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*//input[@name='quote_details[file_selected][]']")));
                } else {
                    System.out.println("Already checkbox is selected");
                }
                //System.out.println("Entering)))))))!="+ fileName);

                //System.out.println("Entering)))))))!");
                selectDropDown(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//select[@id='quote_details[source_lang][]']")),
                        srcLang);
                //System.out.println("Entering)))))))@");
                selectDropDown(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//select[@id='quote_details[target_lang][]']")),
                        targLang);
                //System.out.println("Entering)))))))#");
                selectDropDown(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//select[@id='quote_details[service_id][]']")),
                         service);
                if(minutes) {
                    //System.out.println("Entering)))))))$");
                    String min = mp.substring(0,mp.indexOf("-"));
                    String ss = mp.substring(mp.indexOf("-") +1,mp.length());
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*//input[@name='quote_details[page_length_job_hr][]']")
                            ), min);
                    //System.out.println("Entering)))))))%");
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*//input[@name='quote_details[page_length_job_min][]']")
                            ), ss);


                } else {
                    //System.out.println("Entering)))))))^");
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*//input[@name='quote_details[page_length_job_hr][]']")
                            ), mp);
                }
                //System.out.println("Entering)))))))&");
                enterTestBoxValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//input[@id='quote_details[cost][]']")
                        ), cost);
                //System.out.println("Entering)))))))*");
                enterTestBoxValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//textarea[@id='quote_details[comments][]']")
                        ), misComment);
                break;
            }
        }
    }


    public void setFileDetailsForVoiceOver(String fileName, String srcLang,
            String targLang, String service, String mp, String cost,
            String misComment, boolean minutes, String totalCost) throws
            IOException {

        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 1; i <= elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/a/span"));
            //System.out.println("Entering)))))))" + element.getText());
            if(element.getText().contains(fileName)) {
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//input[@name='quote_details[file_selected" +
                                "][]']"));
                if(element.isSelected()!=true) {
                    clickElement(driver.findElement(By.xpath(
                            "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*//input[@name='quote_details[file_selected][]']")));
                } else {
                    System.out.println("Already checkbox is selected");
                }
                //System.out.println("Entering)))))))!="+ fileName);

                //System.out.println("Entering)))))))!");
                selectDropDown(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//select[@id='quote_details[source_lang][]']")),
                        srcLang);
                //System.out.println("Entering)))))))@");
                selectDropDown(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//select[@id='quote_details[target_lang][]']")),
                        targLang);
                //System.out.println("Entering)))))))#");
                selectDropDown(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//select[@id='quote_details[service_id][]']")),
                        service);
                if(minutes) {
                    //System.out.println("Entering)))))))$");
                    String min = mp.substring(0,mp.indexOf("-"));
                    String ss = mp.substring(mp.indexOf("-") +1,mp.length());
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*//input[@name='quote_details[page_length_job_hr][]']")
                    ), min);
                    //System.out.println("Entering)))))))%");
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*//input[@name='quote_details[page_length_job_min][]']")
                    ), ss);


                } else {
                    //System.out.println("Entering)))))))^");
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*//input[@name='quote_details[page_length_job_hr][]']")
                    ), mp);
                }

                //System.out.println("Entering)))))))&");
                enterTestBoxValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//input[@id='quote_details[cost][]']")
                ), cost);

                enterTestBoxValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//input[@name='quote_details[total_cost][]']")
                ), totalCost);
                //System.out.println("Entering)))))))*");
                enterTestBoxValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//textarea[@id='quote_details[comments][]']")
                ), misComment);
                break;
            }
        }
    }


    public void clickAction(String fileName) {


        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 1; i <= elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/a/span"));

            if(element.getText().contains(fileName)) {
                clickElement(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//input[@class='btn btn-primary btn-md " +
                        "add-btn setroughquote']")));
                break;
            }
        }
    }
}

