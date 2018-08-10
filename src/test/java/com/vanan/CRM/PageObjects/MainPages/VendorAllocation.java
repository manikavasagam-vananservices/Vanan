package com.vanan.CRM.PageObjects.MainPages;

import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VendorAllocation extends AccessingElement {

	private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

	public VendorAllocation(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
	}

    @FindBy(id = "yet_assign_totalLength")
    private WebElement totalFileLenYetToAssign;

    @FindBy(id = "yet_assign_totalPages")
    private WebElement totalNoPagesYetToAssign;

    @FindBy(id = "totalLength")
    private WebElement totalFileLenAssigned;

    @FindBy(id = "totalPages")
    private WebElement totalNoPagesAssigned;

    @FindBy(id = "selected_file_len")
    private WebElement selectedFileLenPages;

    @FindBy(id = "selected_vid_len")
    private WebElement selectedFileLen;

    @FindBy(xpath = "//td[@id='vendorCostPercentage']/b")
    private WebElement vendorCost;

    @FindBy(id = "etatVal")
    private WebElement tatest;

    @FindBy(id = "search-dropdown-sel")
    private WebElement filter;

    @FindBy(linkText = "Payment Made Private Note")
    private WebElement paymentMadePrivateNoteElement;

    @FindBy(xpath="//table[@id='process_list']/tbody/tr")
    private List<WebElement> tableRows;

    @FindBy(id="allocate_btn")
    private WebElement allocateButton;

    public void clickAllocateFile() {

        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(allocateButton).build();
        mouseOverHome.perform();
        if (allocateButton.isDisplayed()) {
            js.executeScript("$('#allocate_btn').click();");
            waitForPageLoad(driver);
        } else {
            System.out.println("Element not displayed");
        }
        waitForPageLoad(driver);
    }

    public int getCurrentRowNumber(String fileName) {

        WebElement fileElement;
        int rowNumber = 0;
        for (int i=1; i< getTableRowSize(); i++) {
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

	private int getTableRowSize() {

		return  tableRows.size();
	}

    public void allocateFileIntoVendor(String fileName, String mp,
                                       boolean minutes, String cost, String etat, String ftat,
                                       String message, String qty, String opfileFormat,
                                       String assignedTo) {

        waitForPageLoad(driver);
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 0; i < elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/span/a"));
            //System.out.println("Entering)))))))" + element.getText());
            if(element.getText().contains(fileName)) {
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//input[contains(@name, '[file_check][]')]"));
                if(element.isSelected()!=true) {
                    clickElement(element);
                } else {
                    System.out.println("Already checkbox is selected");
                }

                /*if(minutes) {

                    String hr = mp.substring(0,mp.indexOf("-"));
                    String mn = mp.substring(mp.indexOf("-")+1,mp.lastIndexOf("-"));
                    String ss = mp.substring(mp.lastIndexOf("-")+1,mp.length());
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*
                            //input[contains(@name, '[cost_per_pages][]')]")
                            ), hr);
                    System.out.println("Entering)))))))%");
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*
                            //input[contains(@name, '[page_length_min][]')]")
                            ), mn);
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*
                            //input[contains(@name, '[page_length_sec][]')]")
                            ), ss);

                } else {
                    System.out.println("Entering)))))))^");
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*
                            //input[contains(@name, '[page_length_min][]')]")
                            ), mp);
                }*/
                //System.out.println("Entering)))))))&");
                if (!cost.equals("")) {
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*//input[contains(@name,'[cost_per_pages][]')]")
                    ), cost);
                }
                waitForPageLoad(driver);

                String acost = getElementValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//input[contains(@id, '[actual_cost][]')]")));
                //System.out.println("########" + acost);
                if (!etat.equals("")) {
                    driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*" +
                                    "//input[contains(@id,'[expect_turn_time][]')]")
                    ).click();
                    element = driver.findElement(By.xpath
                            ("//div[@class='datepicker-days']//../td[@data-day='" + etat +
                                    "']"));
                    element.click();
                }

                if (!ftat.equals("")) {
                    driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*" +
                                    "//input[contains(@id,'[follow_up_date][]')]")
                    ).click();
                    element = driver.findElement(By.xpath
                            ("//div[@class='datepicker-days']//../td[@data-day='" +
                                    ftat + "']"));
                    element.click();
                }

                if (!qty.equals("")) {
                    selectDropDown(driver.findElement(By.xpath
                                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                            "]//*//select[contains(@name, '[quality][]')]")),
                            qty);
                }
                if (!opfileFormat.equals("")) {
                    selectDropDown(driver.findElement(By.xpath
                                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                            "]//*//select[contains(@name, '[output_file_format][]')]")),
                            opfileFormat);
                }
                if (!assignedTo.equals("")) {
                    selectDropDown(driver.findElement(By.xpath
                                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                            "]//*//select[contains(@name, '[allocated_user_id][]')]")),
                            assignedTo);
                }
                if (!message.equals("")) {
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*//textarea[contains(@id, '[va_comment][]')]")
                    ), message);
                }
                //System.out.println("Entering)))))))*");
                break;
            }
        }
    }


    public void allocateFileIntoVendor(String fileName, String service, String srcLang, String tarLang, String mp,
                                       boolean minutes, String cost, String etat, String ftat,
                                       String message, String qty, String opfileFormat,
                                       String assignedTo) {

        waitForPageLoad(driver);
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 0; i < elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/span/a"));
            if (element.getText().contains(fileName)) {
                //System.out.println("Entering)))))))" + element.getText());
                String lang[] = driver.findElement(By.xpath("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]/td[2]/p")).getText().split("-");
                lang[0] = lang[0].replace("(", "").replace(" ", "");
                lang[1] = lang[1].replace(")", "").replace(" ", "");
                if (element.getText().contains(fileName) && driver.findElement(By.xpath("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]/td[2]/span[@class='text-green']")).getText().contains(service) &&
                        lang[0].equalsIgnoreCase(srcLang) && lang[1].equalsIgnoreCase(tarLang)) {
                    waitForPageLoad(driver);
                    element = driver.findElement(By.xpath(
                            "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*//input[contains(@name, '[file_check][]')]"));
                    builder = new Actions(driver);
                    mouseOverHome = builder.moveToElement(element).build();
                    mouseOverHome.perform();
                    waitForPageLoad(driver);
                    if (element.isSelected() != true) {
                        clickElement(element);
                    } else {
                        System.out.println("Already checkbox is selected");
                    }

                /*if(minutes) {

                    String hr = mp.substring(0,mp.indexOf("-"));
                    String mn = mp.substring(mp.indexOf("-")+1,mp.lastIndexOf("-"));
                    String ss = mp.substring(mp.lastIndexOf("-")+1,mp.length());
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*
                            //input[contains(@name, '[cost_per_pages][]')]")
                            ), hr);
                    System.out.println("Entering)))))))%");
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*
                            //input[contains(@name, '[page_length_min][]')]")
                            ), mn);
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*
                            //input[contains(@name, '[page_length_sec][]')]")
                            ), ss);

                } else {
                    System.out.println("Entering)))))))^");
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*
                            //input[contains(@name, '[page_length_min][]')]")
                            ), mp);
                }*/
                    //System.out.println("Entering)))))))&");
                    if (!cost.equals("")) {
                        enterTestBoxValues(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//input[contains(@name,'[cost_per_pages][]')]")
                        ), cost);
                    }
                    waitForPageLoad(driver);

                    String acost = getElementValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*//input[contains(@id, '[actual_cost][]')]")));
                    //System.out.println("########" + acost);
                    if (!etat.equals("")) {
                        try {
                            //System.out.println("########" + fileName+i);
                            element = driver.findElement(By.xpath
                                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                            "]//*" +
                                            "//input[contains(@id,'[expect_turn_time][]')]")
                            );
                            element.click();
                            element = driver.findElement(By.xpath
                                    ("//div[@class='datepicker-days']//../td[@data-day='" + etat +
                                            "']"));
                            element.click();

                        } catch (Exception ex) {
                            js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
                            waitForPageLoad(driver);
                            element = driver.findElement(By.xpath
                                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                            "]//*" +
                                            "//input[contains(@id,'[expect_turn_time][]')]")
                            );
                            element.click();

                            waitForPageLoad(driver);
                            element = driver.findElement(By.xpath
                                    ("//div[@class='datepicker-days']//../td[@data-day='" + etat +
                                            "']"));
                            element.click();
                        }
                        waitForPageLoad(driver);
                    }

                    if (!ftat.equals("")) {
                        try {
                            //System.out.println("@@@@@@@@@@@@@" + fileName+i);
                            element = driver.findElement(By.xpath
                                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                            "]//*" +
                                            "//input[contains(@id,'[follow_up_date][]')]")
                            );
                            builder = new Actions(driver);
                            mouseOverHome = builder.moveToElement(element).build();
                            mouseOverHome.perform();
                            element.click();
                            waitForPageLoad(driver);
                            element = driver.findElement(By.xpath
                                    ("//div[@class='datepicker-days']//../td[@data-day='" +
                                            ftat + "']"));
                            element.click();

                        } catch (Exception ex) {
                            js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
                            waitForPageLoad(driver);
                            element = driver.findElement(By.xpath
                                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                            "]//*" +
                                            "//input[contains(@id,'[follow_up_date][]')]")
                            );
                            element.click();
                            element = driver.findElement(By.xpath
                                    ("//div[@class='datepicker-days']//../td[@data-day='" +
                                            ftat + "']"));
                            element.click();

                        }
                        waitForPageLoad(driver);
                    }

                    if (!qty.equals("")) {
                        selectDropDown(driver.findElement(By.xpath
                                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                                "]//*//select[contains(@name, '[quality][]')]")),
                                qty);
                    }
                    if (!opfileFormat.equals("")) {
                        selectDropDown(driver.findElement(By.xpath
                                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                                "]//*//select[contains(@name, '[output_file_format][]')]")),
                                opfileFormat);
                    }
                    if (!assignedTo.equals("")) {
                        selectDropDown(driver.findElement(By.xpath
                                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                                "]//*//select[contains(@name, '[allocated_user_id][]')]")),
                                assignedTo);
                    }
                    if (!message.equals("")) {
                        enterTestBoxValues(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//textarea[contains(@id, '[va_comment][]')]")
                        ), message);
                    }
                    //System.out.println("Entering)))))))*");
                    break;
                }
            }
        }
	}

    public void waitForProcessCompletion(int waitTime) {
        try {
            TimeUnit.SECONDS.sleep(waitTime);
        } catch (Exception e) {
        }
    }

    public String getTotalFileLengthYetToAssign() {
        waitForPageLoad(driver);
        return totalFileLenYetToAssign.getText();
    }

    public String getTotalFileLengthAssigned() {
        waitForPageLoad(driver);
        return totalFileLenAssigned.getText();
    }

    public int getSelectedFileLenPages() {
        waitForPageLoad(driver);
        return Integer.parseInt(selectedFileLenPages.getText());
    }

    public int getTotalPagesYetToAssign() {
        waitForPageLoad(driver);
        return Integer.parseInt(totalNoPagesYetToAssign.getText());
    }

    public int getTotalPagesAssigned() {
        waitForPageLoad(driver);
        return Integer.parseInt(totalNoPagesAssigned.getText());
    }

    public String getSelectedFileLength() {
        waitForPageLoad(driver);
        return selectedFileLen.getText();
    }

    public double getVendorCost() {

        waitForPageLoad(driver);
        return Double.parseDouble(vendorCost.getText());
    }


    public String getTat() {
        waitForPageLoad(driver);
        return tatest.getText().trim();
    }

    public void deleteFileAllocation(String fileName) {

        waitForPageLoad(driver);
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 0; i < elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/span/a"));
            if (element.getText().contains(fileName)) {
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//input[contains(@name, '[file_check][]')]"));
                builder = new Actions(driver);
                mouseOverHome = builder.moveToElement(element).build();
                mouseOverHome.perform();
                waitForPageLoad(driver);
                if (element.isSelected() == true) {
                    element = driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]/td[11]/input[@title='Cancel']"));
                    clickElement(element);
                    waitForPageLoad(driver);
                    element = driver.findElement(By.xpath("//div[@class='sa-button-container']//" +
                            "..//button[@class='confirm']"));
                    if (element.isDisplayed()) {
                        clickElement(element);
                        break;
                    }
                }
            }
        }
    }


    /*public String getPagesAndLengths(String fileName, boolean minute) {

        String value = "";
        waitForPageLoad(driver);
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 0; i < elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/span/a"));
            if (element.getText().contains(fileName)) {
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*
                                //input[contains(@name, '[file_check][]')]"));
                builder = new Actions(driver);
                mouseOverHome = builder.moveToElement(element).build();
                mouseOverHome.perform();
                waitForPageLoad(driver);
                if (element.isSelected() == true) {
                    if (minute) {


                        value = getElementValues(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*
                                        //input[contains(@name, '[page_length_min][]')]")
                        ));
                        value += getElementValues(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*
                                        //input[contains(@name, '[page_length_sec][]')]")));
                    } else {

                        value = getElementValues(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*
                                        //input[contains(@name, '[page_length_min][]')]")));
                    /*}

                    break;
                }
            }
        }
        return value;
    }*/

    /*public double getCostPerMinuteAndPages(String fileName) {

        double value = 0;
        waitForPageLoad(driver);
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 0; i < elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/span/a"));
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(element).build();
            mouseOverHome.perform();
            if (element.getText().contains(fileName)) {
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*
                                //input[contains(@name, '[file_check][]')]"));

                /*waitForPageLoad(driver);
                if (element.isSelected() == true) {

                    value = Double.parseDouble(getElementValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*
                                    //input[contains(@id, '[actual_cost][]')]"))));
                    break;
                }
            }
        }
        return value;
    }

    public String getFileQuality(String fileName) {

        String value = "";
        waitForPageLoad(driver);
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 0; i < elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/span/a"));
            if (element.getText().contains(fileName)) {
                element = driver.findElement(By.xpath(
                        "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*
                                //input[contains(@name, '[file_check][]')]"));
                builder = new Actions(driver);
                mouseOverHome = builder.moveToElement(element).build();
                mouseOverHome.perform();
                waitForPageLoad(driver);
                if (element.isSelected() == true) {

                    value =  getSelectedDropDownValue(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*
                                    //select[contains(@name, '[quality][]')]")));
                    break;
                }
            }
        }
        return value;
    }

    public void scrollUp() {
        waitForPageLoad(driver);
        js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
    }*/

    public List<String> allocateFileToVendor(String fileName, String service,
                                             String srcLang, String tarLang, String mp, String cost, String etat,
                                             String ftat, String message, String qty, String opfileFormat,
                                             String assignedTo) {

        List<String> results = new ArrayList<>();
        String temp = "";
        waitForPageLoad(driver);
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 0; i < elements.size(); i++) {
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/span/a"));
            if (element.getText().contains(fileName)) {
                //System.out.println("Entering)))))))" + element.getText());
                String lang[] = driver.findElement(By.xpath("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]/td[2]/p")).getText().split("-");
                lang[0] = lang[0].replace("(", "").replace(" ", "");
                lang[1] = lang[1].replace(")", "").replace(" ", "");
                if (element.getText().contains(fileName) && driver.findElement(By.xpath("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]/td[2]/span[@class='text-green']")).getText().contains(service) &&
                        lang[0].equalsIgnoreCase(srcLang) && lang[1].equalsIgnoreCase(tarLang)) {
                    DecimalFormat decimalFormat = new DecimalFormat(".##");
                    waitForPageLoad(driver);
                    element = driver.findElement(By.xpath(
                            "//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                    "]//*//input[contains(@name, '[file_check][]')]"));
                    builder = new Actions(driver);
                    mouseOverHome = builder.moveToElement(element).build();
                    mouseOverHome.perform();
                    waitForPageLoad(driver);
                    if (element.isSelected() != true) {
                        clickElement(element);
                    } else {
                        System.out.println("Already checkbox is selected");
                    }

                    //System.out.println("Entering)))))))&");
                    if (!cost.equals("")) {
                        enterTestBoxValues(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//input[contains(@name,'[cost_per_pages][]')]")
                        ), cost);
                    }

                    double minPaCost = 0;
                    if (mp.contains("-")) {

                        String[] length = mp.split("-");
                        double scost = Double.parseDouble(cost) / 3600;
                        int seconds = (Integer.parseInt(length[0]) * 60 * 60)
                                + (Integer.parseInt(length[1]) * 60) + Integer.parseInt(length[2]);
                        minPaCost = (double) seconds * scost;

                        results.add(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//input[contains(@name, '[page_length_min][]')]")).getAttribute("value") +
                                ":" + driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//input[contains(@name, '[page_length_sec][]')]")).getAttribute("value"));

                    } else {
                        //System.out.println("Entering)))))))^");
                        results.add(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//input[contains(@name, '[page_length_min][]')]")).getAttribute("value"));
                        minPaCost = Double.parseDouble(mp) * Double.parseDouble(cost);
                    }
                    //waitForPageLoad(driver);
                    //waitForProcessCompletion(10);
                    temp = driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*//input[contains(@id, '[actual_cost][]')]")).getAttribute("value");
                    if (!temp.equals("") || !temp.equals(null)) {
                        double acost = Double.parseDouble(temp);

                        if (acost ==  Double.parseDouble(decimalFormat.format(minPaCost))) {
                            results.add("#Pass");
                        } else {
                            results.add("#Fail \n Expected result: " + Double.parseDouble(decimalFormat.format(minPaCost))
                                    + ", Actual result :" + acost);
                        }
                    }
                    //System.out.println("########" + acost);
                    if (!etat.equals("")) {

                        try {
                            //System.out.println("########" + fileName+i);
                            element = driver.findElement(By.xpath
                                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                            "]//*" +
                                            "//input[contains(@id,'[expect_turn_time][]')]")
                            );
                            waitForPageLoad(driver);
                            element.click();

                            element = driver.findElement(By.xpath
                                    ("//div[@class='datepicker-days']//../td[@data-day='" + etat +
                                            "']"));
                            waitForPageLoad(driver);
                            element.click();

                        } catch (Exception ex) {
                            js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");

                            waitForPageLoad(driver);
                            element = driver.findElement(By.xpath
                                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                            "]//*" +
                                            "//input[contains(@id,'[expect_turn_time][]')]")
                            );
                            waitForPageLoad(driver);
                            element.click();

                            element = driver.findElement(By.xpath
                                    ("//div[@class='datepicker-days']//../td[@data-day='" + etat +
                                            "']"));

                            waitForPageLoad(driver);
                            element.click();
                        }
                        waitForPageLoad(driver);
                    }

                    if (!ftat.equals("")) {
                        try {
                            //System.out.println("@@@@@@@@@@@@@" + fileName+i);
                            element = driver.findElement(By.xpath
                                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                            "]//*" +
                                            "//input[contains(@id,'[follow_up_date][]')]")
                            );
                            builder = new Actions(driver);
                            mouseOverHome = builder.moveToElement(element).build();
                            mouseOverHome.perform();
                            element.click();
                            waitForPageLoad(driver);
                            element = driver.findElement(By.xpath
                                    ("//div[@class='datepicker-days']//../td[@data-day='" +
                                            ftat + "']"));
                            element.click();

                        } catch (Exception ex) {
                            js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
                            waitForPageLoad(driver);
                            element = driver.findElement(By.xpath
                                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                            "]//*" +
                                            "//input[contains(@id,'[follow_up_date][]')]")
                            );
                            element.click();
                            element = driver.findElement(By.xpath
                                    ("//div[@class='datepicker-days']//../td[@data-day='" +
                                            ftat + "']"));
                            element.click();

                        }
                        waitForPageLoad(driver);
                    }

                    if (!qty.equals("")) {
                        element = driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//select[contains(@name, '[quality][]')]"));
                        Select ele = new Select(element);

                        temp = ele.getFirstSelectedOption().getText();
                        if (temp.contains(qty)) {
                            results.add("!Pass");
                        } else {
                            results.add("!Fail \n Expected result: " + qty
                                    + ", Actual result :" + temp);
                        }
                    }
                    if (!opfileFormat.equals("")) {
                        selectDropDown(driver.findElement(By.xpath
                                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                                "]//*//select[contains(@name, '[output_file_format][]')]")),
                                opfileFormat);
                    }
                    if (!assignedTo.equals("")) {
                        selectDropDown(driver.findElement(By.xpath
                                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                                "]//*//select[contains(@name, '[allocated_user_id][]')]")),
                                assignedTo);
                    }
                    if (!message.equals("")) {
                        enterTestBoxValues(driver.findElement(By.xpath
                                ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                        "]//*//textarea[contains(@id, '[va_comment][]')]")
                        ), message);
                    }
                    //System.out.println("Entering)))))))*");
                    break;
                }
            }
        }
        return results;
    }

}
