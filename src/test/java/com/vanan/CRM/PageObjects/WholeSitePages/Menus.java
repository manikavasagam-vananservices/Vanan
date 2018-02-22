package com.vanan.CRM.PageObjects.WholeSitePages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanan.CRM.PageObjects.MainPages.Edit;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

public class Menus extends AccessingElement {

    private WebDriver driver;

    public Menus(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@class='sidebar-menu']/li[@class='treeview scroll']/a")
    private WebElement allprocess;

    @FindBy(linkText = "Myqueue")
    private WebElement myQueue;

    @FindBy(linkText = "Trial Flow")
    private WebElement trialFlow;

    @FindBy(linkText = "Quality Issue")
    private WebElement qualityIssue;

    @FindBy(id = "auto_New")
    private WebElement newMenu;

    @FindBy(linkText = "Private Note Added")
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

    @FindBy(linkText = "Payment made")
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

    @FindBy(linkText = "Order Delivered")
    private WebElement orderDelivered;

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

    @FindBy(id = "mail_discussion_btn")
    private WebElement emailConversation;

    @FindBy(xpath = "//*[@title='Edit']")
    private WebElement editMenu;

    @FindBy(xpath = "//*[@title='View']")
    private WebElement viewMenu;

    @FindBy(xpath = "//*[@title='File Info']")
    private WebElement fileInfoMenu;

    @FindBy(xpath = "//*[@title='Quote Info']")
    private WebElement quoteInfoMenu;

    @FindBy(xpath = "//span[contains(text(), 'Unknown Email')]")
    private WebElement unknownMail;

    @FindBy(xpath = "//button[@title='Add Ticket']")
    private WebElement addNewTicket;

    @FindBy(xpath = "//span[contains(text(), 'ALLOCATOR DASHBOARD')]")
    private WebElement allocatorDashboard;

    @FindBy(name = "search_mail")
    private WebElement searchTicketId;

    @FindBy(xpath = "//button[@title='Search']")
    private WebElement searchButtonElement;

    private Actions builder;
    private Action mouseOverHome;

    public ReadTableData clickNewMenu() {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(newMenu).build();
        mouseOverHome.perform();
        clickElement(newMenu);
        ReadTableData readTableData = new ReadTableData(driver);
        return readTableData;
    }

    public ReadTableData clickOthersMenu() {
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

        clickElement(trialFlow);
    }

    public void clickPaymentMade() {

        clickElement(paymentmade);
    }

    public void enterCustomerDetails(String customerDetails) {
        try {
            enterTestBoxValues(searchTicketId, customerDetails);
        } catch (Exception e) {
            System.out.println("Unable to enter a customer details " + e);
        }
    }

    public void clickSearchButton() {

        clickElement(searchButtonElement);
    }

    public void searchCustomerDetails(String customerDetails) {

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

        clickElement(emailConversation);
        EmailConversation emailCon = new EmailConversation(driver);
        return emailCon;
    }

    public void clickView() {

        clickElement(viewMenu);
    }

    public Edit clickEdit() {

        clickElement(editMenu);
        Edit edit = new Edit(driver);
        return edit;
    }

    public void clickFileInfo() {

        clickElement(fileInfoMenu);
    }

    public QuoteInfo clickQuoteInfo() {

        clickElement(quoteInfoMenu);
        QuoteInfo quoteInfo = new QuoteInfo(driver);
        return quoteInfo;
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

    public void clickAllocatorDashboard() {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(allocatorDashboard).build();
        mouseOverHome.perform();
        clickElement(allocatorDashboard);
    }

    public void clickUnknownMail() {

        clickElement(unknownMail);
    }

    public void clickAllProcess() {

        clickElement(allprocess);
    }

    public void clickSignOut() {

        WebElement element = driver.findElement(By.className("user-image"));
        element.click();
        element = driver.findElement(By.linkText("Sign out"));
        element.click();
    }
}
