package com.vanan.CRM.PageObjects.WholeSitePages;

import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;

public class FileInfo extends AccessingElement {

    private WebDriver driver;
    @FindBy(id = "getquote")
    private WebElement getQuote;

    @FindBy(id = "command")
    private WebElement commentElement;

    @FindBy(id = "file_info_btn")
    private WebElement updateFileInfoButtonElement;

    @FindBy(id = "totalSelectedPages")
    private WebElement totalSelectedPages;

    @FindBy(id = "totalPages")
    private WebElement totalPages;

    @FindBy(id = "totalSelectedLength")
    private WebElement totalSelectedLength;

    @FindBy(id = "totalLength")
    private WebElement totalLength;

    private Actions builder;
    private Action mouseOverHome;
    private JavascriptExecutor js;

    public FileInfo(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setFileDetails(String fileName, String fileType, String mp,
            String qty, String qc, String scnt, String misComment) throws
            IOException {

        waitForPageLoad(driver);
        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_list']/tbody/tr"));
        WebElement element;
        for (int i = 0; i < elements.size(); i++) {
            //System.out.println("Entering");
            element = driver.findElement(By.xpath
                    ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]/td[2]/span"));
            //System.out.println("Entering)))))))" + element.getText());
            if (element.getText().contains(fileName)) {
                builder = new Actions(driver);
                mouseOverHome = builder.moveToElement(driver.findElement(By.xpath("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//input[@name='file_selected']"))).build();
                mouseOverHome.perform();
                clickElement(driver.findElement(By.xpath("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                                "]//*//input[@name='file_selected']")));
                selectDropDown(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//select[@id='upload_details[file_type][]']")),
                        fileType);
                //System.out.println("Entering)))))))!");
                if(fileType.equals("Audio/Video")) {
                    String hr = mp.substring(0,mp.indexOf("-"));
                    String mn = mp.substring(mp.indexOf("-")+1,mp.lastIndexOf("-"));
                    String ss = mp.substring(mp.lastIndexOf("-")+1,mp.length());
                    //System.out.println("Entering)))))))@");
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*//input[@name='upload_details[page_length_hr" +
                                    "][]']")
                            ), hr);
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*//input[@name='upload_details[page_length_min][]']")
                            ), mn);
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*//input[@name='upload_details[page_length_sec][]']")
                            ), ss);
                } else {
                    //System.out.println("Entering)))))))$");
                    enterTestBoxValues(driver.findElement(By.xpath
                            ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                            "]//*//input[@name='upload_details[page_length_hr][]']")
                            ), mp);
                }
                //System.out.println("Entering)))))))#");
                selectDropDown(driver.findElement(By.xpath
                           ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                           "]//*//select[@id='upload_details[quality" +
                                   "][]']")),
                           qty);
                //System.out.println("Entering)))))))%");
                selectDropDown(driver.findElement(By.xpath
                           ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                           "]//*//select[@id='upload_details[quality_category][]']")),
                           qc);
                //System.out.println("Entering)))))))^");
                selectDropDown(driver.findElement(By.xpath
                           ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                           "]//*//select[@id='upload_details[speakers_cnt" +
                           "][]']")), scnt);
                //System.out.println("Entering)))))))&");
                enterTestBoxValues(driver.findElement(By.xpath
                        ("//table[@id='process_list']/tbody/tr[" + (i + 1) +
                        "]//*//textarea[@id='upload_details[mis_comment][]']")
                        ), misComment);
                break;
            }
        }
    }

    public void clickUpdateFileInfo() {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(updateFileInfoButtonElement).build();
        mouseOverHome.perform();
        clickElement(updateFileInfoButtonElement);
    }


    public String getTotalNoOfPages() {

        /*builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(totalSelectedPages).build();
        mouseOverHome.perform();*/
        return totalSelectedPages.getText();
    }

    public String getTotalNoOfLength() {

        /*builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(totalSelectedLength).build();
        mouseOverHome.perform();*/
        return totalSelectedLength.getText();
    }

    public String getSelectedTotalNoOfPages() {

       /* builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(totalPages).build();
        mouseOverHome.perform();*/
        return totalPages.getText();
    }

    public String getSelectedTotalNoOfLength() {

        /*builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(totalLength).build();
        mouseOverHome.perform();*/
        return totalLength.getText();
    }


}
