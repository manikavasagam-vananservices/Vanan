package com.vanan.CRM.PageObjects.WholeSitePages;

import com.vanan.CRM.PageObjects.MainPages.*;
import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

import java.util.concurrent.TimeUnit;

public class Menus extends AccessingElement {

    private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

    public Menus(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(xpath = "//ul[@class='sidebar-menu']/li[@class='treeview scroll']/a")
    private WebElement allprocess;

    @FindBy(linkText = "Myqueue")
    private WebElement myQueue;

    @FindBy(id = "auto_TrialFlow")
    private WebElement trialFlow;

    @FindBy(linkText = "Quality Issue")
    private WebElement qualityIssue;

    @FindBy(id = "auto_New")
    private WebElement newMenu;

    @FindBy(id = "auto_PrivateNoteAdded")
    private WebElement privateNoteAdded;

    @FindBy(linkText = "Opportunity")
    private WebElement opportunity;

    @FindBy(linkText = "Order Quote sent")
    private WebElement orderQuoteSent;

    @FindBy(linkText = "Quote Enquiry Clarification")
    private WebElement quoteEnquiryClarification;

    @FindBy(linkText = "Order Invoice sent")
    private WebElement orderInvoicesent;

    @FindBy(linkText = "Order Approved")
    private WebElement orderApproved;

    @FindBy(id = "auto_Paymentmade")
    private WebElement paymentmade;

    @FindBy(linkText = "Pending Payment")
    private WebElement pendingPayment;

    @FindBy(linkText = "Check Clearance")
    private WebElement checkClearance;

    @FindBy(linkText = "Assigned work")
    private WebElement assignedwork;

    @FindBy(linkText = "Job Accepted")
    private WebElement jobAccepted;

    @FindBy(linkText = "Files For Approval")
    private WebElement filesForApproval;

    @FindBy(linkText = "Job Delivered")
    private WebElement jobDelivered;

    @FindBy(linkText = "Waiting For Files")
    private WebElement waitingForFiles;

    @FindBy(linkText = "Mailing/Notary")
    private WebElement mailingNotary;

    @FindBy(linkText = "Opportunity Failed")
    private WebElement opportunityFailed;

    @FindBy(linkText = "Order failed")
    private WebElement orderFailed;

    @FindBy(linkText = "Order Rejected")
    private WebElement orderRejected;

    @FindBy(linkText = "Payment failed")
    private WebElement paymentFailed;

    @FindBy(linkText = "Job Rejected")
    private WebElement jobRejected;

    @FindBy(linkText = "Refund Issued")
    private WebElement refundIssued;

    @FindBy(linkText = "Trial Fail")
    private WebElement trialFail;

    @FindBy(linkText = "Refund Request")
    private WebElement refundRequest;

    @FindBy(linkText = "Refund Denied")
    private WebElement refundDenied;

    @FindBy(linkText = "Order Fail Addressed")
    private WebElement orderFailAddressed;

    @FindBy(linkText = "Over Due")
    private WebElement overDue;

    @FindBy(linkText = "Today")
    private WebElement today;

    @FindBy(linkText = "Follow Up")
    private WebElement followUp;

    @FindBy(linkText = "Quality Issue Resolved")
    private WebElement qualityIssueResolved;

    @FindBy(linkText = "Trial delivered")
    private WebElement trialdelivered;

    @FindBy(linkText = "Trial Success")
    private WebElement trialSuccess;

    @FindBy(id = "auto_Others")
    private WebElement others;

    @FindBy(linkText = "No Followup")
    private WebElement noFollowup;

    @FindBy(linkText = "Quote Not submitted")
    private WebElement quoteNotSubmitted;

    @FindBy(linkText = "Quote Not Submitted Failed")
    private WebElement quoteNotSubmittedFailed;

    @FindBy(linkText = "DUP_ORDER")
    private WebElement dupOrder;

    @FindBy(linkText = "Employment")
    private WebElement employment;

    @FindBy(linkText = "Critical Tickets")
    private WebElement criticalTickets;

    @FindBy(linkText = "Unresponded Tickets")
    private WebElement unrespondedTickets;

    @FindBy(linkText = "Followup (All)")
    private WebElement followupAll;

    @FindBy(linkText = "Unresponded Allocator")
    private WebElement unrespondedAllocator;

    @FindBy(linkText = "Quote Info")
    private WebElement quoteInfo;

    @FindBy(id = "auto_OrderDelivered")
    private WebElement orderDelivered;

    @FindBy(xpath = "//*[@title='Email Conversation']")
    private WebElement emailConversation;

    @FindBy(xpath = "//*[@title='Edit']")
    private WebElement editMenu;

    @FindBy(xpath = "//*[@title='Vendor Allocation']")
    private WebElement vendorAllocationMenu;

    @FindBy(xpath = "//*[@title='Private Note']")
    private WebElement privateNoteMenu;

    @FindBy(xpath = "//*[@title='View']")
    private WebElement viewMenu;

    @FindBy(xpath = "//div[@class='box-header-box']//..//a[contains(text(),'Quick Edit')]")
    private WebElement quickEditElement;

    @FindBy(xpath = "//*[@title='File Info']")
    private WebElement fileInfoMenu;

    @FindBy(xpath = "//*[@title='Quote Info']")
    private WebElement quoteInfoMenu;

    @FindBy(xpath = "//*[@title='Delivery']")
    private WebElement deliveryMenu;

    @FindBy(xpath = "//span[contains(text(), 'Unknown Email')]")
    private WebElement unknownMail;

    @FindBy(xpath = "//button[@title='Add Ticket']")
    private WebElement addNewTicket;

    @FindBy(xpath = "//span[contains(text(), 'ALLOCATOR DASHBOARD')]")
    private WebElement allocatorDashboard;

    @FindBy(id = "auto_Allocator_Completed")
    private WebElement completedElement;

    @FindBy(name = "search_mail")
    private WebElement searchTicketId;

    @FindBy(xpath = "//button[@title='Search']")
    private WebElement searchButtonElement;

    @FindBy(name = "quick_service_id")
    private WebElement status;

    @FindBy(id="quick-edit-btn")
    private WebElement submitButton;
	
    @FindBy(id = "auto_Allocator_Allocated")
    private WebElement alloctedElement;

    @FindBy(id = "auto_Allocator_Rejected")
    private WebElement rejectElement;

    @FindBy(id = "auto_Allocator_Cancelled")
    private WebElement cancelElement;
	
    public ReadTableData clickVendorAll() {

        waitForPageLoad(driver);
        js.executeScript("$('#auto_ALL').click();");
        ReadTableData readTableData = new ReadTableData(driver);
        return readTableData;
    }

    public ReadTableData clickVendorNewFiles() {

        waitForPageLoad(driver);
        js.executeScript("$('#auto_Allocator_NewFiles').click();");
        ReadTableData readTableData = new ReadTableData(driver);
        return readTableData;
    }

    public ReadTableData clickVendorAccepted() {

        waitForPageLoad(driver);
        js.executeScript("$('#auto_Allocator_Accepted').click();");
        ReadTableData readTableData = new ReadTableData(driver);
        return readTableData;
    }

    public ReadTableData clickVendorInprogress() {

        waitForPageLoad(driver);
        js.executeScript("$('#auto_Allocator_Inprocess').click();");
        ReadTableData readTableData = new ReadTableData(driver);
        return readTableData;
    }

    public ReadTableData clickVendorCompleted() {

        waitForPageLoad(driver);
        js.executeScript("$('#auto_Allocator_Completed').click();");
        ReadTableData readTableData = new ReadTableData(driver);
        return readTableData;
    }

    public ReadTableData clickNewMenu() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(newMenu).build();
        mouseOverHome.perform();
        //System.out.println("\t New menu Clicked");
        js.executeScript("$('#auto_New').click();");
        ReadTableData readTableData = new ReadTableData(driver);
        return readTableData;
    }

    public ReadTableData clickOthersMenu() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(others).build();
        mouseOverHome.perform();
        clickElement(others);
        ReadTableData readTableData = new ReadTableData(driver);
        return readTableData;
    }

    public void clickMyDashBoard() {

        clickElement(allprocess);
    }

    public void clickMyQueue() {

        clickElement(myQueue);
    }

    public void clickTrialFlow() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(trialFlow).build();
        mouseOverHome.perform();
        //System.out.println("\t New menu Clicked");
        js.executeScript("$('#auto_TrialFlow').click();");
    }

    public void clickPrivateNoteAdded() {

        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(privateNoteAdded).build();
        mouseOverHome.perform();
        js.executeScript("$('#auto_PrivateNoteAdded').click();");
    }

    public void clickPaymentMade() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(paymentmade).build();
        mouseOverHome.perform();
        clickElement(paymentmade);
    }

    public void clickOrderDelivered() {
        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(orderDelivered).build();
        mouseOverHome.perform();
        clickElement(orderDelivered);
    }

    public void enterCustomerDetails(String customerDetails) {
        try {
            enterTestBoxValues(searchTicketId, customerDetails);
        } catch (Exception e) {
            System.out.println("Unable to enter a customer details " + e);
        }
    }

    public void clickSearchButton() {

        //System.out.println("\t Search button Clicked");
        clickElement(searchButtonElement);
    }

    public void searchCustomerDetails(String customerDetails) {

        waitForPageLoad(driver);
        enterCustomerDetails(customerDetails);
        clickSearchButton();
    }

	/*
     * public QuoteInfo clickQuoteInfo() {
	 * 
	 * clickElement(quoteInfo); QuoteInfo quoteInfo = new QuoteInfo(driver);
	 * return quoteInfo; }
	 */

    public EmailConversation clickEmailConversation() {

        waitForPageLoad(driver);
        //System.out.println("\t Email Conversation menu Clicked");
        clickElement(emailConversation);
        EmailConversation emailCon = new EmailConversation(driver);
        return emailCon;
    }

    public void clickView() {

        waitForPageLoad(driver);
        //System.out.println("\t View menu Clicked");
        clickElement(viewMenu);
        Edit edit = new Edit(driver);
        waitForPageLoad(driver);
    }

    public void clickQuickEditButton() {

        waitForPageLoad(driver);
        //System.out.println("\t Quick Edit button clicked");
        clickElement(quickEditElement);
        Edit edit = new Edit(driver);
        waitForPageLoad(driver);
    }

    public Edit clickEdit() {
	
        waitForPageLoad(driver);
        //System.out.println("\t Edit menu Clicked");
        clickElement(editMenu);
        Edit edit = new Edit(driver);
        waitForPageLoad(driver);
        return edit;
    }

    public void selectPopupStatus(String option) {
        try {
            selectDropDown(status, option);
        } catch (Exception e) {
            System.out.println("Unable to select status " + e);
        }
    }

    public void clickSubmitButtonFromPopup() {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(submitButton).build();
            mouseOverHome.perform();
            clickElement(submitButton);
            waitForPageLoad(driver);
        } catch (Exception e) {
            System.out.println("Unable to click submit button " + e);
        }
    }

    public PrivateNote clickPrivateNote() {

        waitForPageLoad(driver);
        //System.out.println("\t Private note menu Clicked");
        clickElement(privateNoteMenu);
        PrivateNote privateNote = new PrivateNote(driver);
        return privateNote;
    }
    public VendorAllocation clickVendorAllocation() {

        waitForPageLoad(driver);
        //System.out.println("\t Vendor allocation menu Clicked");
        clickElement(vendorAllocationMenu);
        VendorAllocation vendorAllocation = new VendorAllocation(driver);
        waitForPageLoad(driver);
        return vendorAllocation;
    }
    public void clickFileInfo() {

        waitForPageLoad(driver);
       // System.out.println("\t File Info menu Clicked");
        clickElement(fileInfoMenu);
    }

    public QuoteInfo clickQuoteInfo() {

        waitForPageLoad(driver);
        //System.out.println("\t Quote Info menu Clicked");
        clickElement(quoteInfoMenu);
        QuoteInfo quoteInfo = new QuoteInfo(driver);
        return quoteInfo;
    }

    public Delivery clickDelivery() {

        waitForPageLoad(driver);
        //System.out.println("\t Delivery menu Clicked");
        clickElement(deliveryMenu);
        Delivery delivery = new Delivery(driver);
        return delivery;
    }

    public void clickAddNewTicket() {

        clickElement(addNewTicket);
    }

    public void selectServices(String... services) {

        for (String service : services) {
            WebElement element = driver
                .findElement(By.xpath("//input[@type='checkbox' and @id='"
                    + getServiceElementId(service) + "']"));
            clickElement(element);
        }
    }

    private String getServiceElementId(String service) {
        String elementId = "";
        WebElement serviceElement;
        for (int i = 1; i <= 9; i++) {

            serviceElement = driver.findElement(
                By.xpath("//label[@for='service_id-" + i + "']"));
            if (serviceElement.getText().contains(service)) {
                elementId = "service_id-" + i;
                break;
            }

        }
        return elementId;
    }

    public void clickViewTicket() {

        clickElement(viewMenu);
    }

    public AllocatorDashboard  clickAllocatorDashboard() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(allocatorDashboard).build();
        mouseOverHome.perform();
        //System.out.println("\t Allocator Dashboard menu Clicked");
        clickElement(allocatorDashboard);
        AllocatorDashboard allocatorDashboard = new AllocatorDashboard(driver);
        return allocatorDashboard;
    }

    public void clickCompleted() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(completedElement).build();
        mouseOverHome.perform();
        js.executeScript("$('#auto_Allocator_Completed').click();");
    }

    public void clickUnknownMail() {

        clickElement(unknownMail);
    }

    public void clickAllProcess() {

        clickElement(allprocess);
    }

    public void clickSignOut() {

        waitForPageLoad(driver);
        WebElement element = driver.findElement(By.className("user-image"));
        element.click();
        element = driver.findElement(By.linkText("Sign out"));
        element.click();
        waitForProcessCompletion(15);
    }

    public void waitForProcessCompletion(int waitTime) {
        try {
            TimeUnit.SECONDS.sleep(waitTime);
        } catch (Exception e) {
        }
    }
	
    public void clickAllocated() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(alloctedElement).build();
        js.executeScript("$('#auto_Allocator_Allocated').click();");
        mouseOverHome.perform();
    }


    public void clickReject() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(rejectElement).build();
        js.executeScript("$('#auto_Allocator_Rejected').click();");
        mouseOverHome.perform();
    }

    public void clickCancelled() {

        waitForPageLoad(driver);
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(cancelElement).build();
        js.executeScript("$('#auto_Allocator_Cancelled').click();");
        mouseOverHome.perform();
    }


}
