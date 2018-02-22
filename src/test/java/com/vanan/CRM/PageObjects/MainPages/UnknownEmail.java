package com.vanan.CRM.PageObjects.MainPages;

import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UnknownEmail extends AccessingElement {

    private WebDriver driver;

    public UnknownEmail(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@class='sidebar-menu']/li[@class='treeview scroll']/a")
    private WebElement allprocess;

    private void clickDropdownArrow() {

        WebElement eachElement = driver.findElement(
            By.xpath("//span[@class='caret']"));
        clickElement(eachElement);
    }

    private void selectFields(String field) {

        String elementParentPath = "//ul[@id='moveto_option']";
        switch (field) {

            case "Folder":
                clickElement(driver.findElement(By.xpath(elementParentPath +
                    "/li[1]")));
                break;

            case "Order No":
                clickElement(driver.findElement(By.xpath(elementParentPath +
                    "/li[2]")));
                break;

        }
    }

    private void moveMailTo(String option) {
        selectDropDown(driver.findElement(By.id("mail_folder_list")), option);

    }

    public void changeUnknownMailStaus(String field, String emailtab) {
        clickDropdownArrow();
        try{
            TimeUnit.SECONDS.sleep(5);} catch (Exception ex) {}
        selectFields(field);
        try{
            TimeUnit.SECONDS.sleep(5);} catch (Exception ex) {}
        clickElement(driver.findElement(By.xpath("//span[@class=" +
                "'select2-selection select2-selection--single']/span[@id=" +
                "'select2-mail_folder_list-container']")));
        moveMailTo(emailtab);
        try{
            TimeUnit.SECONDS.sleep(5);} catch (Exception ex) {}
        clickElement(driver.findElement(By.id("ticket_move_btn")));
    }

    public void clickEmail(String serviceDetails) {

        List<WebElement> tableRows = driver.findElements(By.xpath
            ("//div[@class='box box-primary']//..//table/tbody/tr"));
        WebElement eachElement;
        for (int i = 1; i <= tableRows.size(); i++) {

            eachElement = driver.findElement(
                By.xpath("//div[@class='box box-primary']//..//table/tbody/tr[" + i + "]/td[@class='mailbox-subject']"));
            //System.out.println(eachElement.getText() + "))))))))))");

            if (eachElement.getText().contains(serviceDetails)) {
                eachElement.click();
                break;
            }

        }
    }

    public String getEmailId() {

        WebElement element = driver.findElement(By.xpath
                ("//div[@class='post-content']/b"));
        String value = "";
        if (element.isDisplayed()) {
            value = element.getText();
        }
        return value;
    }

    public String getCustomerName() {

        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='vstabbody']/tbody/tr[1]/td[2]/p"));

        String value = "";
        if (elements.get(0).isDisplayed()) {
            value = elements.get(0).getText();
        }
        return value;
    }

    public void clickTodayEmail() {
        clickElement(driver.findElement(By.xpath
                ("//div[@class='col-md-3']/div/div[2]/ul/li[1]/a")));

    }


    public int getServiceCount(String service) {

        List<WebElement> elements = driver.findElements(By.xpath
                ("//td[@class='mailbox-subject' and contains(text(), '"
                        + service + "')]"));
        return elements.size();

    }

    public void selectParticularEmail(String name, String service) {

        List<WebElement> tableRows = driver.findElements(By.xpath
                ("//div[@class='box box-primary']//..//table/tbody/tr"));
        WebElement eachElement;
        for (int i = 1; i <= tableRows.size(); i++) {
            eachElement = driver.findElement(By.xpath("//div[@class="
                    + "'box box-primary']//..//table/tbody/tr[" + i +
                    "]/td[@class='mailbox-subject']"));

            if (eachElement.getText().contains(service)) {
                eachElement.click();
                if (getCustomerName().contains(name)) {
                    break;
                } else {
                    clickTodayEmail();
                }
            }
        }

        /*List<WebElement> elements = driver.findElements(By.xpath("//td[@class="
                + "'mailbox-subject' and contains(text(), '" + service + "')]"));
        for (int i = 1; i <= elements.size(); i++) {

        }*/
    }

    public void selectParticularPaymentGatewayEmail(String name, String
            service) {

        List<WebElement> tableRows = driver.findElements(By.xpath
                ("//div[@class='box box-primary']//..//table/tbody/tr"));
        WebElement eachElement;
        for (int i = 1; i <= tableRows.size(); i++) {
            eachElement = driver.findElement(By.xpath("//div[@class="
                    + "'box box-primary']//..//table/tbody/tr[" + i +
                    "]/td[@class='mailbox-subject']"));

            if (eachElement.getText().contains(service)) {
                eachElement.click();
                if (getCustomerName().contains(name)) {
                    break;
                } else {
                    clickTodayEmail();
                }
            }
        }

        /*List<WebElement> elements = driver.findElements(By.xpath("//td[@class="
                + "'mailbox-subject' and contains(text(), '" + service + "')]"));
        for (int i = 1; i <= elements.size(); i++) {

        }*/
    }

}
