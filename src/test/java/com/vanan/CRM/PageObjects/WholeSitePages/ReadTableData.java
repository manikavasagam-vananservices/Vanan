package com.vanan.CRM.PageObjects.WholeSitePages;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


import com.vanancrm.PageObjects.MainPages.AccessingElement;
import org.openqa.selenium.*;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ReadTableData extends AccessingElement {

	private WebDriver driver;

	public ReadTableData(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id='process_lists_wrapper']/table/tbody/tr")
	private List<WebElement> tableRows;

	private int getTableRowSize() {

		return tableRows.size();
	}

	public int getTicketCount() {

        int size = getTableRowSize();
        String count;
        if (size == 50) {
            count = driver.findElement(By.xpath(
                    "//div[@id='process_lists_wrapper']/table/tbody/tr[50]/td[3" +
                            "]")).getText();
            size = Integer.parseInt(count);
        } else {
            List<WebElement> columnSize = driver.findElements(
                By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                    + size + "]/td"));
            if (columnSize.size() == 1) {
                String message = "No data available in table";
                if (driver.findElement(
                    By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                        + size + "]/td[1]")).getText().equals(message)) {
                    size = 0;

                } else {
                    System.out.println("Somethink went wrong please check " +
                        "manually");
                }

            } else {
                count = driver.findElement(
                        By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                        + size + "]/td[2]")).getText();
                size = Integer.parseInt(count);
            }
        }
        return size;
    }

    public void isNoDataMessageDisplayed() {
        int size = getTableRowSize();
        List<WebElement> columnSize = driver.findElements(
            By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                + size + "]/td"));
        if (columnSize.size() == 1) {
            String message = "No data available in table";
            if (driver.findElement(
                By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                    + size + "]/td[1]")).getText().equals(message)) {
                size = 0;
                System.out.println(message + " message is displayed");
            } else {
                System.out.println("Somethink went wrong please check " +
                    "manually");
            }
        }
    }

    public void isNoDataMessageDisplayedOrderList() {
        int size = getTableRowSize();
        List<WebElement> columnSize = driver.findElements(
            By.xpath("//div[@id='order_lists_wrapper']/table/tbody/tr["
                + size + "]/td"));
        if (columnSize.size() == 1) {
            String message = "No data available in table";
            if (driver.findElement(
                By.xpath("//div[@id='order_lists_wrapper']/table/tbody/tr["
                    + size + "]/td[1]")).getText().equals(message)) {
                size = 0;
                System.out.println(message + " message is displayed");
            } else {
                System.out.println("Somethink went wrong please check " +
                    "manually");
            }
        }
    }

    public int getUnrespondTicketDetails() {
        int count = 0;
        for (int i = 1; i <= getTableRowSize(); i++) {
            String value = driver.findElement(
                By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                    + i + "]/td[12]/i")).getCssValue("color");

					//+ i + "]/td[11]/i")).getCssValue("color");

            if (value.contains("rgba(255, 0, 0, 1)")) {
                count = count + 1;
            }
        }
        return count;
    }

    public int getLastTicketNumber() throws IOException {

        String value = driver.findElement(
                By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                + getTableRowSize() + "]/td[3]")).getText();
        return Integer.parseInt(value);
    }

    public int getLastTicketNumberFromTAT() throws IOException {

        List<WebElement> elements = driver.findElements(By.xpath
                ("//div[@id='order_lists_wrapper']/table/tbody/tr"));
        WebElement element = driver.findElement(
                By.xpath("//div[@id='order_lists_wrapper']/table/tbody/tr["
                + elements.size() + "]/td[3]"));
        String value = "";
        if (element.isDisplayed()) {
            value = element.getText();
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }

    public List<String> getAllocatedFileDetails(String fileName, String srcLan,
            String tarLang) {

        waitForPageLoad(driver);
        String elementPath = "//table[@id='order_lists_allocator']/tbody/tr";
        List<WebElement> elements = driver.findElements(By.xpath
                (elementPath));
        List<String> datas = new ArrayList<>();
        String language = (srcLan + " - " + tarLang).toLowerCase();
        for(int i=0; i<elements.size(); i++) {
            System.out.println(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[4]"))
                    .getText()+" ===" + driver.findElement(By.xpath(
                    elementPath +"[" + (i+1) +"]/td[5]")).getText() + "####" + (driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[4]"))
                    .getText().contains(fileName) && (driver.findElement(By.xpath(
                    elementPath +"[" + (i+1) +"]/td[5]")).getText().toLowerCase()).contains(
                    language)));
            if(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[4]"))
                    .getText().contains(fileName) && (driver.findElement(By.xpath(
                    elementPath +"[" + (i+1) +"]/td[5]")).getText().toLowerCase()).contains(
                    language)) {

                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[2]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(
                        elementPath +"[" + (i+1) +"]/td[3]")).getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[4]"))
                        .getText());
                datas.add( driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[5]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[6]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[7]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[8]/span"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[9]"))
                        .getText());
                break;
            }
        }
        return datas;
    }


    public int getLastTicketNumberForRefundRequest() throws IOException {

        List<WebElement> elements = driver.findElements(By.xpath
                ("//table[@id='process_lists']/tbody/tr"));
        WebElement element = driver.findElement(
                By.xpath("//table[@id='process_lists']/tbody/tr["
                        + elements.size() + "]/td[3]"));
        String value = "";
        if (element.isDisplayed()) {
            value = element.getText();
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }

    public int getTicketCountWithCheckBox() {

        int size = getTableRowSize();
        String count;
        if (size == 50) {
            count = driver.findElement(By.xpath(
                    "//div[@id='process_lists_wrapper']/table/tbody/tr[50]/td[3" +
                            "]"))
                    .getText();
            size = Integer.parseInt(count);
        } else {
            count = driver.findElement(
                    By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                            + size + "]/td[3]"))
                    .getText();
            size = Integer.parseInt(count);
        }
        return size;
    }

	public List<String> readTableRows() {

		List<String> rowValues = new ArrayList<String>();
		for (int i = 0; i < getTableRowSize(); i++) {
			rowValues.add(tableRows.get(i).getText());
			// System.out.println("i==" + tableRows.get(i).getText());
		}
		return rowValues;
	}

	public ViewTicketDetails clickOrderNo(String orderNo) {

		WebElement orderNos = driver.findElement(By.linkText(orderNo));
		orderNos.click();
		ViewTicketDetails viewTicketDetails = new ViewTicketDetails(driver);
		return viewTicketDetails;
		/*
		 * for (int i=1; i<= getTableRowSize(); i++) {
		 * 
		 * orderNos = driver.findElement(By.xpath(
		 * "//div[@id='process_lists_wrapper']/table/tbody/tr[" + i + "]/td[2]"));
		 * if(orderNos.getText().equals(orderNo)) {
		 * 
		 * } }
		 */
	}

    public ViewTicketDetails clickService(String serviceName, int row) {
        WebElement service;

        try {
            waitForPageLoad();
            service = driver.findElement(
                    By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                            + row + "]/td[5]"));

            if (service.getText().equals(serviceName)) {
                service.click();

                if (isAlertPresent()) {
                    Alert alert = driver.switchTo().alert();
                    System.out.println(alert.getText());
                    alert.accept();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        ViewTicketDetails viewTicketDetails = new ViewTicketDetails(driver);
        return viewTicketDetails;
    }

    public void clickParticularTicket(String service, String mailId,
           String channel, String ticketId) {

        List<String> tickets = readTableRows();

        for (int i = 0; i < tickets.size(); i++) {

            if (tickets.get(i).contains(service)) {

                ViewTicketDetails viewTicketDetails = new ViewTicketDetails
                        (driver);
                waitForProcessCompletion(20);
                viewTicketDetails = clickService(service,
                        (i + 1));
                waitForProcessCompletion(20);
                System.out.println("Channel " + viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel"));
                if (viewTicketDetails.getRunTimeTicketFieldValues("Email")
                        .contains(mailId) && viewTicketDetails
                        .getRunTimeTicketFieldValues("Channel")
                        .contains(channel)&& viewTicketDetails
                        .getStaticTicketFieldValues("Order No").contains
                        (ticketId)) {

                    System.out.println("Entering Ticket details" );
                    break;
                } else {
                    System.out.println("Ticket is not found / searching");
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

    public ViewTicketDetails clickOldTableService(String serviceName, int row) {
        WebElement service;

        try {
            waitForPageLoad();
            service = driver.findElement(
                    By.xpath("//div[@id='process_lists_wrapper']/table/tbody/tr["
                            + row + "]/td[4]"));
            if (service.getText().equals(serviceName)) {
                if (isAlertPresent()) {
                    Alert alert = driver.switchTo().alert();
                    System.out.println(alert.getText());
                    alert.accept();
                }
                service.click();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

		ViewTicketDetails viewTicketDetails = new ViewTicketDetails(driver);
		return viewTicketDetails;
	}

	public void handleAlert() {

		if (isAlertPresent()) {
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			alert.accept();
		}
	}

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException ex) {
            return false;
        }
    }

    public void waitForPageLoad() {
        waitTime();
        for(int i = 1; i <= 12; i++) {
            try {
                if(driver.findElement(By.xpath("//div[@class='loading_img']"))
                        .isDisplayed()) {
                    if(driver.findElement(By.xpath("//div[@class='loading_img']"))
                            .getCssValue("display").contains("block")) {
                        waitTime();
                    } else if(driver.findElement(By.xpath("//div[@class='loading_img']"))
                            .getCssValue("display").contains("none")){
                        break;
                    }

                } else {
                    continue;
                }
            } catch (Exception ex) {

            }
        }
    }

    private void waitTime() {
        waitForProcessCompletion(5);
    }
	
    public List<String> getAllocatedDetails(String fileName, String srcLan,
            String tarLang) {

        waitForPageLoad(driver);
        String elementPath = "//div[@id='order_lists_wrapper']/table/tbody/tr";
        List<WebElement> elements = driver.findElements(By.xpath
                (elementPath));
        List<String> datas = new ArrayList<>();
        String language = (srcLan + " - " + tarLang).toLowerCase();
        for(int i=0; i<elements.size(); i++) {

            if(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[6]"))
                    .getText().contains(fileName) && (driver.findElement(By.xpath(
                    elementPath +"[" + (i+1) +"]/td[7]")).getText().toLowerCase()).contains(
                    language)) {

                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[3]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[4]"))
                        .getText());
                datas.add( driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[5]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[6]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[7]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[8]"))
                        .getText());

                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[9]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[10]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[11]"))
                        .getText());
                break;
            }
        }
        return datas;
    }
	
    public List<String> getAllocatedCompletedFileDetails(String fileName, String srcLan,
            String tarLang) {

        waitForPageLoad(driver);
        String elementPath = "//div[@id='order_lists_wrapper']/table/tbody/tr";
        List<WebElement> elements = driver.findElements(By.xpath
                (elementPath));
        List<String> datas = new ArrayList<>();
        String language = (srcLan + " - " + tarLang).toLowerCase();
        for(int i=0; i<elements.size(); i++) {
            if(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[3]"))
                    .getText().contains(fileName) && (driver.findElement(By.xpath(
                    elementPath +"[" + (i+1) +"]/td[4]")).getText().toLowerCase()).contains(
                    language)) {

                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[2]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(
                        elementPath +"[" + (i+1) +"]/td[3]")).getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[4]"))
                        .getText());
                datas.add( driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[5]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[6]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[7]"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[8]/span"))
                        .getText());
                datas.add(driver.findElement(By.xpath(elementPath +"[" + (i+1) +"]/td[9]"))
                        .getText());
                break;
            }
        }
        return datas;
    }
}
