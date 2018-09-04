package com.vanan.CRM.PageObjects.MainPages;

import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Review extends AccessingElement {

    private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private EmailConversation emailConversation;
    private JavascriptExecutor js;

    public Review(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        builder = new Actions(driver);
        js = (JavascriptExecutor) driver;

    }


    public List<String> getTicketDetails() {

        waitForPageLoad(driver);
        String elementPath = "//table[@class='table table-bordered table-responsive reviewclass']/tbody/tr";
        List<WebElement> elements = driver.findElements(By.xpath(elementPath));
        List<String> status = new ArrayList<>();
        for (int i = 1; i <= elements.size(); i++) {
            System.out.print("@@@@@@@@"+driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[1]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[2]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[3]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[4]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[5]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[6]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[7]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[8]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[9]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[10]")).getText());
            status.add(driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[1]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[2]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[3]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[4]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[5]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[6]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[7]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[8]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[9]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[10]")).getText());
        }
        return status;
    }

    public List<String> getFileAllocationDetails() {

        waitForPageLoad(driver);
        String elementPath = "//table[@class='table table-bordered reviewclass']/tbody/tr";
        List<WebElement> elements = driver.findElements(By.xpath(elementPath));
        List<String> status = new ArrayList<>();
        for (int i = 1; i <= elements.size(); i++) {
            status.add(driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[2]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[3]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[4]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[4]/span")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[5]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[6]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[7]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[8]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[9]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[10]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[11]/span")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[12]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[13]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[14]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[15]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[16]")).getText() + "#" + driver.findElement(By.xpath
                    (elementPath + "[" + i + "]/td[17]")).getText());
        }
        return status;
    }

}
