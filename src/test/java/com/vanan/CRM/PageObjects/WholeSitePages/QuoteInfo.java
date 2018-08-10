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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QuoteInfo extends AccessingElement {

    private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;
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
    @FindBy(xpath = "//input[@type='checkbox' and " +
            "@name='service_option[notary]']")
    private WebElement notaryBoth;
    @FindBy(xpath = "//input[@type='checkbox' and @id='mailed__opt']")
    private WebElement mailingBoth;
    @FindBy(xpath = "//input[@type='checkbox' and @name='service_option[timecode]']")
    private WebElement timecodeBoth;
    @FindBy(xpath = "//input[@type='checkbox' and @name='service_option[verbatim]']")
    private WebElement verbatimBoth;
    @FindBy(xpath = "//input[@type='checkbox' and @id='qc__opt']")
    private WebElement qcBoth;
    @FindBy(xpath = "//input[@type='checkbox' and @name='service_option[rush]']")
    private WebElement expediteFeeBoth;
    //
    @FindBy(xpath = "//input[@type='checkbox' and @name='service_option[native]']")
    private WebElement nativeBoth;
    @FindBy(xpath = "//input[@type='checkbox' and @name='serviceargs[certificate]']")
    private WebElement certificate;
    //
    @FindBy(xpath = "//select[@name='invoice_details[invoice][offers]']")
    private WebElement discountCategory;

    @FindBy(xpath = "//td[@class='disLable']")
    private WebElement discountTableRow;

    @FindBy(name = "invoice_details[invoice][discout_type]")
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
    @FindBy(xpath = "//input[@type='text' and " +
            "@name='invoice_details[invoice][notary_amt]']")
    private WebElement notarizationElement;
    @FindBy(xpath = "//input[@type='text' and " +
            "@name='invoice_details[invoice][certificate_amt]']")
    private WebElement certificateElement;
    @FindBy(xpath = "//input[@type='text' and " +
            "@name='invoice_details[invoice][processing_fee_amt]']")
    private WebElement serviceFeeElement;
    @FindBy(xpath = "//input[@type='text' and " +
            "@name='invoice_details[invoice][mailed_amt]']")
    private WebElement mailingElement;
    @FindBy(xpath = "//input[@type='text' and " +
            "@name='invoice_details[invoice][timecode_amt]']")
    private WebElement timeCodeElement;
    @FindBy(xpath = "//input[@type='text' and " +
            "@name='invoice_details[invoice][verbatim_amt]']")
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
    @FindBy(xpath = "//input[@type='text' and " +
            "@name='invoice_details[invoice][qc_amt]']")
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

    @FindBy(xpath = "//input[@type='text' and @name='invoice_details[invoice][rush]']")
    private WebElement expediateElement;

    @FindBy(name = "invoice_details[invoice][discount]")
    private WebElement customerNODDiscount;

    @FindBy(xpath = "//div[@id='specialCustomers']//*//button[@class='close']")
    private WebElement popUpCloseButtonElement;

    @FindBy(xpath = "//div[@id='mail-preview']//*//button[@class='close']")
    private WebElement mailPreviewCloseButtonElement;

    public QuoteInfo(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }


    // Notarty

    public static boolean isClickable(By by, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 6);
            wait.until(ExpectedConditions.elementToBeClickable(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectNotary() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(notary).build();
        mouseOverHome.perform();
        if (notary.isSelected() != true) {
            clickElement(notary);
        }

    }

    public void deselectNotary() {

        waitForPageLoad(driver);
        if (notary.isSelected() == true) {
            clickElement(notary);
        }
    }

    public boolean isNotaryOptionSelected() {

        waitForPageLoad(driver);
        return notary.isSelected();
    }

    public boolean isNotaryOptionEnabled() {

        waitForPageLoad(driver);
        return notary.isEnabled();
    }

    public boolean isNotaryOptionDisplayed() {

        waitForPageLoad(driver);
        return notary.isDisplayed();
    }

    // Notarty Both

    public boolean isNotaryOptionClickable() {

        waitForPageLoad(driver);
        return isClickable(By.name("serviceargs[notary]"), driver);
    }

    public void selectNotaryBoth() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(notaryBoth).build();
        mouseOverHome.perform();
        if (notaryBoth.isSelected() != true) {
            clickElement(notaryBoth);
        }

    }

    public void deselectNotaryBoth() {

        waitForPageLoad(driver);
        if (notaryBoth.isSelected() == true) {
            clickElement(notaryBoth);
        }
    }

    public boolean isNotaryBothOptionSelected() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(notaryBoth).build();
        mouseOverHome.perform();
        return notaryBoth.isSelected();
    }

    public boolean isNotaryBothOptionEnabled() {

        waitForPageLoad(driver);
        return notaryBoth.isEnabled();
    }

    public boolean isNotaryBothOptionDisplayed() {

        waitForPageLoad(driver);
        return notaryBoth.isDisplayed();
    }

    // Mailing

    public boolean isNotaryBothOptionClickable() {

        waitForPageLoad(driver);
        return isClickable(By.name("service_option[notary]"), driver);
    }

    public void selectMailing() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(mailing).build();
        mouseOverHome.perform();
        if (mailing.isSelected() != true) {
            clickElement(mailing);
        }

    }

    public void deselectMailing() {

        waitForPageLoad(driver);
        if (mailing.isSelected() == true) {
            clickElement(mailing);
        }
    }

    public boolean isMailingOptionSelected() {

        waitForPageLoad(driver);
        return mailing.isSelected();
    }

    public boolean isMailingOptionDisplayed() {

        waitForPageLoad(driver);
        return mailing.isDisplayed();
    }

    public boolean isMailingOptionEnabled() {

        waitForPageLoad(driver);
        return mailing.isEnabled();
    }


    // Mailing Both

    public boolean isMailingClickable() {

        waitForPageLoad(driver);
        return isClickable(By.name("serviceargs[mailed]"), driver);
    }

    public void selectMailingBoth() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(mailingBoth).build();
        mouseOverHome.perform();
        if (mailingBoth.isSelected() != true) {
            clickElement(mailingBoth);
        }

    }

    public void deselectMailingBoth() {

        waitForPageLoad(driver);
        if (mailing.isSelected() == true) {
            clickElement(mailingBoth);
        }
    }

    public boolean isMailingBothOptionSelected() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(mailingBoth).build();
        mouseOverHome.perform();
        return mailingBoth.isSelected();
    }

    public boolean isMailingBothOptionDisplayed() {

        waitForPageLoad(driver);

        return mailingBoth.isDisplayed();
    }

    public boolean isMailingBothOptionEnabled() {

        waitForPageLoad(driver);
        return mailingBoth.isEnabled();
    }

    // TimeCode

    public boolean isMailingBothClickable() {

        waitForPageLoad(driver);
        return isClickable(By.name("service_option[mailed]"), driver);
    }


    public void selectTimeCode() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(timecode).build();
        mouseOverHome.perform();
        if (timecode.isSelected() != true) {
            clickElement(timecode);
        }

    }

    public void deselectTimeCode() {

        waitForPageLoad(driver);
        if (timecode.isSelected() == true) {
            clickElement(timecode);
        }
    }

    public boolean isTimeCodeSelected() {

        waitForPageLoad(driver);
        return timecode.isSelected();
    }

    public boolean isTimeCodeEnabled() {

        waitForPageLoad(driver);
        return timecode.isEnabled();
    }

    public boolean isTimeCodeDisplayed() {

        waitForPageLoad(driver);
        return timecode.isDisplayed();
    }

    // TimeCode Both

    public boolean isTimeCodeClickable() {

        waitForPageLoad(driver);
        return isClickable(By.name("serviceargs[timecode]"), driver);
    }

    public void selectTimeCodeBoth() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(timecodeBoth).build();
        mouseOverHome.perform();
        if (timecodeBoth.isSelected() != true) {
            clickElement(timecodeBoth);
        }

    }

    public void deselectTimeCodeBoth() {

        waitForPageLoad(driver);
        if (timecodeBoth.isSelected() == true) {
            clickElement(timecodeBoth);
        }
    }

    public boolean isTimeCodeBothSelected() {

        waitForPageLoad(driver);
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(timecodeBoth).build();
        mouseOverHome.perform();
        return timecodeBoth.isSelected();
    }

    public boolean isTimeCodeBothEnabled() {

        waitForPageLoad(driver);
        return timecodeBoth.isEnabled();
    }

    public boolean isTimeCodeBothDisplayed() {

        waitForPageLoad(driver);
        return timecodeBoth.isDisplayed();
    }

    // Verbatim

    public boolean isTimeCodeBothClickable() {

        waitForPageLoad(driver);
        return isClickable(By.name("service_option[timecode]"), driver);
    }

    public void selectVerbatim() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(verbatim).build();
        mouseOverHome.perform();
        if (verbatim.isSelected() != true) {
            clickElement(verbatim);
        }

    }

    public void deselectVerbatim() {

        waitForPageLoad(driver);
        if (verbatim.isSelected() == true) {
            clickElement(verbatim);
        }
    }

    public boolean isVerbatimSelected() {

        waitForPageLoad(driver);
        return verbatim.isSelected();
    }

    public boolean isVerbatimEnabled() {

        waitForPageLoad(driver);
        return verbatim.isEnabled();
    }

    public boolean isVerbatimDisplayed() {

        waitForPageLoad(driver);
        return verbatim.isDisplayed();
    }

    // Verbatim Both

    public boolean isVerbatimClickable() {

        waitForPageLoad(driver);
        return isClickable(By.name("serviceargs[verbatim]"), driver);
    }

    public void selectVerbatimBoth() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(verbatimBoth).build();
        mouseOverHome.perform();
        if (verbatimBoth.isSelected() != true) {
            clickElement(verbatimBoth);
        }

    }

    public void deselectVerbatimBoth() {

        waitForPageLoad(driver);
        if (verbatimBoth.isSelected() == true) {
            clickElement(verbatimBoth);
        }
    }

    public boolean isVerbatimBothSelected() {

        waitForPageLoad(driver);
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(verbatimBoth).build();
        mouseOverHome.perform();
        return verbatimBoth.isSelected();
    }

    public boolean isVerbatimBothEnabled() {

        waitForPageLoad(driver);
        return verbatimBoth.isEnabled();
    }

    public boolean isVerbatimBothDisplayed() {

        waitForPageLoad(driver);
        return verbatimBoth.isDisplayed();
    }

    // Certificate

    public boolean isVerbatimBothClickable() {

        waitForPageLoad(driver);
        return isClickable(By.name("service_option[verbatim]"), driver);
    }

    public void selectCertificate() {

        waitForPageLoad(driver);
        if (certificate.isSelected() != true) {
            clickElement(certificate);
        }

    }

    public void deselectCertificate() {

        waitForPageLoad(driver);
        if (certificate.isSelected() == true) {
            clickElement(certificate);
        }
    }

    public boolean isCertificateSelected() {

        waitForPageLoad(driver);
        return certificate.isSelected();
    }

    public boolean isCertificateDisplayed() {

        waitForPageLoad(driver);
        return certificate.isDisplayed();
    }

    public boolean isCertificateEnabled() {

        waitForPageLoad(driver);
        return certificate.isEnabled();
    }

    // QC

    public boolean isCertificateClickable() {

        waitForPageLoad(driver);
        return isClickable(By.name("serviceargs[certificate]"), driver);
    }

    public void selectQc() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(qc).build();
        mouseOverHome.perform();
        if (qc.isSelected() != true) {
            clickElement(qc);
        }

    }

    public void deselectQc() {

        waitForPageLoad(driver);
        if (qc.isSelected() == true) {
            clickElement(qc);
        }
    }

    public boolean isQCSelected() {

        waitForPageLoad(driver);
        return qc.isSelected();
    }

    public boolean isQCDisplayed() {

        waitForPageLoad(driver);
        return qc.isDisplayed();
    }

    public boolean isQCEnabled() {

        waitForPageLoad(driver);
        return qc.isEnabled();
    }

    // QC Both

    public boolean isQCClickable() {

        waitForPageLoad(driver);
        return isClickable(By.name("serviceargs[qc]"), driver);
    }

    public void selectQcBoth() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(qcBoth).build();
        mouseOverHome.perform();
        if (qcBoth.isSelected() != true) {
            clickElement(qcBoth);
        }

    }

    public void deselectQcBoth() {

        waitForPageLoad(driver);
        if (qcBoth.isSelected() == true) {
            clickElement(qcBoth);
        }
    }

    public boolean isQCBothClickable() {

        waitForPageLoad(driver);
        return isClickable(By.name("service_option[qc]"), driver);
    }

    public boolean isQCBothSelected() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(qcBoth).build();
        mouseOverHome.perform();
        return qcBoth.isSelected();
    }

    public boolean isQCBothDisplayed() {

        waitForPageLoad(driver);
        return qcBoth.isDisplayed();
    }

    // Discount

    public boolean isQCBothEnabled() {

        waitForPageLoad(driver);
        return qcBoth.isEnabled();
    }

    public void selectDiscount(String discount) {

        waitForPageLoad(driver);
        selectDropDown(discountCategory, discount);
    }

    public String getDiscountNameFromTable() {

        waitForPageLoad(driver);
        return getElementValues(discountTableRow);
    }

    public void selectDiscountPercent(String discountPercent) {

        waitForPageLoad(driver);
        selectDropDown(discountPercentage, discountPercent);
    }

    public void enterDiscountValues(String discountValue) {

        enterTestBoxValues(discountValues, discountValue);
    }

    public void enterQualityCheckValues(String qcValue) {

        enterTestBoxValues(qcFee, qcValue);
    }

    public void selectExpedite(String serviceType) {

        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(expedite).build();
            mouseOverHome.perform();
            selectDropDown(expedite, serviceType);

        } catch (Exception e) {
            System.out.println("Unable to select expedite " + e);
        }
    }

    public boolean isExpediteDisplayed() {

        return expedite.isDisplayed();
    }

    // Native

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

    public boolean isNativeSelected() {

        waitForPageLoad(driver);
        return nativeElement.isSelected();
    }

    public boolean isNativeDisplayed() {

        waitForPageLoad(driver);
        return nativeElement.isDisplayed();
    }

    public boolean isNativeEnabled() {

        waitForPageLoad(driver);
        return nativeElement.isEnabled();
    }


    // Native Both

    public boolean isNativeClickable() {

        return isClickable(By.name("invoice_details[invoice][native]"), driver);
    }

    public void selectNativeBoth() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(nativeBoth).build();
        mouseOverHome.perform();
        if (nativeBoth.isSelected() != true) {
            clickElement(nativeBoth);
        }

    }

    public void deselectNativeBoth() {

        if (nativeBoth.isSelected() == true) {
            clickElement(nativeBoth);
        }
    }

    public boolean isNativeBothSelected() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(nativeBoth).build();
        mouseOverHome.perform();
        return nativeBoth.isSelected();
    }

    public boolean isNativeBothDisplayed() {

        waitForPageLoad(driver);
        return nativeBoth.isDisplayed();
    }

    public boolean isNativeBothEnabled() {

        waitForPageLoad(driver);
        return nativeBoth.isEnabled();
    }

    // Expedite Fee  Both

    public boolean isNativeBothClickable() {

        return isClickable(By.name("invoice_details[invoice][native]"), driver);
    }

    public void selectExpediateBoth() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(expediteFeeBoth).build();
        mouseOverHome.perform();
        if (expediteFeeBoth.isSelected() != true) {
            clickElement(expediteFeeBoth);
        }

    }

    public void deselectExpediateBoth() {

        if (expediteFeeBoth.isSelected() == true) {
            clickElement(expediteFeeBoth);
        }
    }

    public boolean isExpediateBothSelected() {

        waitForPageLoad(driver);
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(expediteFeeBoth).build();
        mouseOverHome.perform();
        return expediteFeeBoth.isSelected();
    }

    public boolean isExpediateBothDisplayed() {

        return expediteFeeBoth.isDisplayed();
    }

    public boolean isExpediateeBothEnabled() {

        return expediteFeeBoth.isEnabled();
    }

    public boolean isExpediateBothClickable() {

        return isClickable(By.name("service_option[rush]"), driver);
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

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(notarizationElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(notarizationElement, notarizationValues);
    }

    public void enterCertificationValues(String certificateValues) {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(certificateElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(certificateElement, certificateValues);
    }

    public void enterServiceFeeValues(String serviceFeeValues) {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(serviceFeeElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(serviceFeeElement, serviceFeeValues);
    }

    public void enterMailingValues(String mailingValues) {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(mailingElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(mailingElement, mailingValues);
    }

    public void enterTimeCodeValues(String timecodeValues) {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(timeCodeElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(timeCodeElement, timecodeValues);
    }

    public void enterVerbatimValues(String verbatimValues) {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(verbatimElement).build();
        mouseOverHome.perform();
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

    public String getExpediates() {

        waitForPageLoad(driver);
        return getAttributeValues(expediateElement, "value");
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

    public void clickPopUpCloseButton() {

        waitForPageLoad(driver);
        try {
            if (popUpCloseButtonElement.isDisplayed()) {
                js.executeScript("$('.close').click();");
            } else {
                System.out.println("Element not displayed");
            }

        } catch (Exception ex) {
            System.out.println("Unable to close pop window : " + ex);
        }
        waitForPageLoad(driver);
    }

    public void clickPreviewEmail() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(preMailButton).build();
        mouseOverHome.perform();
        clickElement(preMailButton);
        if (preMailButton.isDisplayed()) {
            js.executeScript("$('#pre-mail_btn').click();");
        } else {
            System.out.println("Element not displayed");
        }
        waitForPageLoad(driver);
    }

    public void clickUpdateQuoteInfo() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(updateQuoteInfoButton).build();
        mouseOverHome.perform();
        clickElement(updateQuoteInfoButton);
        waitForPageLoad(driver);
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
        waitForPageLoad(driver);
        double totalCost = 0;
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 1; i <= elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/a/span"));
            //System.out.println("Entering)))))))" + element.getText());
            if (element.getAttribute("title").contains(fileName)) {
                if (element.getAttribute("title").contains(fileName)) {
                    System.out.println("Tool tip message is correct "
                            + element.getAttribute("title"));
                } else {
                    System.out.println("Tool tip message is wrong "
                            + element.getAttribute("title"));
                }
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//input[@name='quote_details[file_selected" +
                                "][]']"));
                builder = new Actions(driver);
                mouseOverHome = builder.moveToElement(element).build();
                mouseOverHome.perform();
                if (element.isSelected() != true) {
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
                if (minutes) {
                    if (!mp.equals("")) {
                        int min = 0;
                        String[] lengths = mp.split("-");
                        //System.out.println("Entering)))))))$");
                        if (!lengths[0].equals("00")) {
                            min = (int) Math.round(Integer.parseInt(lengths[0]) * 60);
                            min += Integer.parseInt(lengths[1]);
                        } else {
                            min = Integer.parseInt(lengths[1]);
                        }
                        if (!lengths[2].equals("00")) {
                            min += 1;
                        }

                        int second = 0;
                        int sec = 0;
                        if (Integer.parseInt(lengths[2]) > 0) {
                            second = 1;
                        } else {
                            second = 0;

                        }
                        if (Integer.parseInt(lengths[2]) <= 59) {
                            sec = Integer.parseInt(lengths[2]);
                        }

                        min += second;
                        totalCost = (double) (min * Integer.parseInt(cost));
                        System.out.println("%%%%%%%%%%%%%%%%%@@@@@@@@" + totalCost);
                        enterTestBoxValues(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//input[@name='quote_details[page_length_job_hr][]']")
                        ), min + "");
                        //System.out.println("Entering)))))))%");
                        enterTestBoxValues(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//input[@name='quote_details[page_length_job_min][]']")
                        ), sec + "");
                    }


                } else {
                    if (!mp.equals("")) {
                        //System.out.println("Entering)))))))^");
                        enterTestBoxValues(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//input[@name='quote_details[page_length_job_hr][]']")
                        ), mp);
                    }
                }
                //System.out.println("Entering)))))))&");
                enterTestBoxValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//input[@name='quote_details[cost][]']")
                ), cost);

                if (minutes) {
                    if (mp.contains("-")) {

                    }
                }

                if (service.equals("Voice Over")) {

                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*//input[@name='quote_details[total_cost][]']")
                    ), totalCost + "");
                }

                //System.out.println("Entering)))))))*");
                enterTestBoxValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//textarea[@id='quote_details[comments][]']")
                        ), misComment);
                break;
            }
        }
        waitForPageLoad(driver);
    }

    public void getSourceLanguageDetails(String fileName) throws IOException {

        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        //System.out.println("@@@@@@@@@@@@@" +elements.size());
        for (int i = 1; i <= elements.size(); i++) {
            //System.out.println("########" + i);
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/a/span"));
            //System.out.println("Entering)))))))" + element.getText());
            if (element.getAttribute("title").contains(fileName)) {
                if (element.getAttribute("title").contains(fileName)) {
                    System.out.println("Tool tip message is correct "
                            + element.getAttribute("title"));
                } else {
                    System.out.println("Tool tip message is wrong "
                            + element.getAttribute("title"));
                }
                getSelectedDropDownValue(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//select[@id='quote_details[source_lang][]']")));
                break;
            }
        }
    }

    public void getTargetLanguageDetails(String fileName) throws IOException {

        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        //System.out.println("@@@@@@@@@@@@@" +elements.size());
        for (int i = 1; i <= elements.size(); i++) {
            //System.out.println("########" + i);
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/a/span"));
            //System.out.println("Entering)))))))" + element.getText());
            if (element.getAttribute("title").contains(fileName)) {
                if (element.getAttribute("title").contains(fileName)) {
                    System.out.println("Tool tip message is correct "
                            + element.getAttribute("title"));
                } else {
                    System.out.println("Tool tip message is wrong "
                            + element.getAttribute("title"));
                }
                getSelectedDropDownValue(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//select[@id='quote_details[target_lang][]']")));
                break;
            }
        }
    }

    public void getServiceDetails(String fileName) throws IOException {

        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        //System.out.println("@@@@@@@@@@@@@" +elements.size());
        for (int i = 1; i <= elements.size(); i++) {
            //System.out.println("########" + i);
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/a/span"));
            //System.out.println("Entering)))))))" + element.getText());
            if (element.getAttribute("title").contains(fileName)) {
                if (element.getAttribute("title").contains(fileName)) {
                    System.out.println("Tool tip message is correct "
                            + element.getAttribute("title"));
                } else {
                    System.out.println("Tool tip message is wrong "
                            + element.getAttribute("title"));
                }
                //System.out.println("Entering)))))))!="+ fileName);

                //System.out.println("Entering)))))))!");
                getSelectedDropDownValue(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//select[@id='quote_details[service_id][]']")));
                break;
            }
        }
    }

    public void clickFile(String fileName) throws IOException {

        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 1; i <= elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/a/span"));
            //System.out.println("Entering)))))))" + element.getText());
            if (element.getText().contains(fileName)) {
                if (element.getAttribute("title").contains(fileName)) {
                    System.out.println("Tool tip message is correct "
                            + element.getAttribute("title"));
                } else {
                    System.out.println("Tool tip message is wrong "
                            + element.getAttribute("title"));
                }
                clickElement(element);
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

        waitForPageLoad(driver);
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 1; i <= elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/a/span"));

            if (element.getAttribute("title").contains(fileName)) {
                clickElement(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//input[@class='btn btn-primary btn-md " +
                        "add-btn setroughquote']")));
                break;
            }
        }
    }

    public boolean isDiscountTBDisabled() {

        waitForPageLoad(driver);
        return (!customerNODDiscount.isEnabled());
    }

    public boolean isDiscountTypeDisabled() {

        waitForPageLoad(driver);
        return (!driver.findElement(By.name
                ("invoice_details[invoice][discout_type]")
        ).isEnabled());
    }

    public String getOrderNo() {

        return driver.findElement(By.xpath("//li[2]/span")).getText();
    }


    public String getFileName() {

        return driver.findElement(By.xpath("//li[3]/span")).getText();
    }

    public boolean checkQuoteEmailContent(String[] fields, String fileName) {

        waitForPageLoad(driver);
        String elementPath = "//div[@id='mail-content']/table[2]/tbody/tr[3]/td/table/tbody/tr";
        List<WebElement> rowSize = driver
                .findElements(By.xpath(elementPath));
        boolean value = false;
        WebElement eachElement;
        List<WebElement> columns;
        int count = 0;
        for (int i = 4; i <= rowSize.size(); i++) {
            try {
                //System.out.println("size : = : " + rowSize.size());
                columns = driver.findElements(By.xpath(
                        elementPath + "[" + i + "]/td"));
                if (columns.size() != 0 || columns.size() != 2) {
                    //System.out.println("G csize : = : " + columns.size());
                    if (columns.size() == 8) {
                        //System.out.println("4 ccsize : = : " + columns.size());
                        eachElement = driver.findElement(By.xpath(elementPath + "[" + i
                                + "]/td[1]/p"));
                        if (eachElement.getText().contains(fileName)) {
                            for (int j = 2; j <= columns.size(); j++) {
                                eachElement = driver.findElement(By.xpath(elementPath + "[" + i
                                        + "]/td[" + j + "]/p"));
                               // System.out.println(" : = : =UI " + eachElement.getText().trim() + "-Back " + fields[j - 2]);
                                if (eachElement.getText().trim().contains(
                                        fields[j - 2])) {
                                    count++;
                                }

                                if (count == 7) {
                                    value = true;
                                    break;
                                }
                            }
                        }
                        if (columns.size() == 2) {
                            continue;
                        }
                    }
                }
            } catch (Exception ex) {
                continue;
            }
        }
        waitForPageLoad(driver);
        return value;
    }

    public void scrollUp() {
        waitForPageLoad(driver);
        js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
    }

    public boolean checkQuoteEmailPriceDetails(String field, String message) {

        waitForPageLoad(driver);
        String elementPath = "//div[@id='mail-content']/table[2]/tbody/tr[3]/td/table/tbody/tr";
        List<WebElement> elements = driver
                .findElements(By.xpath(elementPath));
        boolean value = false;
        WebElement eachElement;
        List<WebElement> columns;
        int count = 0;
        String content = "";
        for (int i = 4; i <= elements.size(); i++) {
            try {
                //System.out.println("size : = : " + elements.size());
                columns = driver.findElements(By.xpath(
                        elementPath + "[" + i + "]/td"));

                //System.out.println("G csize : = : " + columns.size());
                if (columns.size() != 0 && columns.size() != 8) {
                    if (columns.size() == 2) {
                        //System.out.println("4 ccsize : = : " + columns.size());
                        for (int j = 1; j <= columns.size(); j++) {
                            eachElement = driver.findElement(By.xpath(elementPath + "[" + i
                                    + "]/td[" + j + "]/p"));
                            /*System.out.println(" cvaluesize : = : " +
                                    eachElement.getText());*/
                            if (eachElement.getText().contains(field)) {
                                eachElement = driver.findElement(By.xpath(elementPath + "[" + i
                                        + "]/td[" + (j+1) + "]/p"));
                                if (eachElement.getText().contains(",")) {
                                    content = eachElement.getText().trim().replace(",", "");
                                    content = content.replace("$", "");
                                } else {
                                    content = eachElement.getText();
                                    content = content.replace("$", "");
                                }
                                if(content.contains(message)) {
                                    value = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                continue;
            }
        }
        waitForPageLoad(driver);
        return value;
    }

    public void clickPreviewMailPopUpCloseButton() {

        waitForPageLoad(driver);
        try {
            if (mailPreviewCloseButtonElement.isDisplayed()) {
                js.executeScript("$('.close').click();");
            } else {
                System.out.println("Element not displayed");
            }

        } catch (Exception ex) {
            System.out.println("Unable to close pop window : " + ex);
        }
        waitForPageLoad(driver);
    }
}

