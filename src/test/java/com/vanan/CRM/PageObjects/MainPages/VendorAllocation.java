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

	@FindBy(linkText = "Payment Made Private Note")
	private WebElement paymentMadePrivateNoteElement;

    @FindBy(xpath="//table[@id='process_list']/tbody/tr")
    private List<WebElement> tableRows;

    @FindBy(id="allocate_btn")
    private WebElement allocateButton;

    public  void clickAllocateFile() {
	    clickElement(allocateButton);
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
            boolean minutes, String cost, String etat, String message, String
            qty, String opfileFormat, String assignedTo) {

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

                enterTestBoxValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//input[contains(@name,'[cost_per_pages][]')]")
                        ), cost);
                waitForProcessCompletion(5);
                String acost = getElementValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//input[contains(@id, '[actual_cost][]')]")));
                //System.out.println("########" + acost);
                enterTestBoxValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//input[contains(@id,'[expect_turn_time][]')]")
                        ), etat);

                selectDropDown(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                       "]//*//select[contains(@name, '[quality][]')]")),
                        qty);

                selectDropDown(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//select[contains(@name, '[output_file_format][]')]")),
                        opfileFormat);

                selectDropDown(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                       "]//*//select[contains(@name, '[allocated_user_id][]')]")),
                        assignedTo);
                enterTestBoxValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//textarea[contains(@id, '[va_comment][]')]")
                        ), message);
                //System.out.println("Entering)))))))*");
                break;
            }
        }
	}

    public void waitForProcessCompletion(int waitTime) {
        try {
            TimeUnit.SECONDS.sleep(waitTime);
        } catch (Exception e) {
        }
    }
}
