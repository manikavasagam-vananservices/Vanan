package com.vanan.CRM.PageObjects.MainPages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

public class EmailConversation extends AccessingElement {

    private WebDriver driver;
    private Actions builder;
    private Action mouseOverHome;
    private EmailConversation emailConversation;
    private JavascriptExecutor js;

	public EmailConversation(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        builder = new Actions(driver);
        js = (JavascriptExecutor) driver;

	}

    @FindBy(xpath = "//*[@title='View']")
    private WebElement viewMenu;

    @FindBy(linkText = "Read more")
    private WebElement readMoreButton;

	// By noActionButton = By.linkText("No Action");
	@FindBy(linkText = "No Action")
	private WebElement noActionButton;

	@FindBy(linkText = "Close")
	private WebElement closeButton;

    @FindBy(linkText = "Upload")
    private WebElement fileUploadButtonElement;

    @FindBy(xpath = "//a[contains(text(),'Compose Email')]")
    private WebElement composeEmailElement;

    @FindBy(xpath = "//button[@id='upload']")
    private WebElement sendButtonElement;

    @FindBy(xpath = "//input[@name='cc_address[]']")
    private WebElement ccElement;

    public void clickReadMore() {

        clickElement(readMoreButton);
    }

    public void clickComposeEmail() {

        clickElement(composeEmailElement);
    }

    public void enterCCEmail(String mailid) {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(ccElement).build();
        mouseOverHome.perform();
        enterTestBoxValues(ccElement, mailid);
    }

    public void clickSendEmail() {
        builder = new Actions(driver);
        mouseOverHome = builder.moveToElement(sendButtonElement).build();
        mouseOverHome.perform();
        clickElement(sendButtonElement);
        waitForPageLoad(driver);
    }

    public void enterEmailBodyContent(String message) {
        int size = driver.findElements(By.tagName("iframe")).size();
        //System.out.println("Frame Size : " + size);
        driver.switchTo().frame(0);
        List<WebElement> elements = driver.findElements(By.xpath("//p"));
        //System.out.println("P tag Size : " + elements.size());
        js.executeScript("arguments[0].innerText = '" + message + "'",
                elements.get(0));
        driver.switchTo().defaultContent();
    }



    public void clickReadMore(String message) {
        List<WebElement> elements = driver.findElements(By.xpath("//li[contains" +
                "(@id,'mid')]"));
        WebElement element;
        for(int i=0; i<elements.size();i++) {
            element = driver.findElement(By.xpath("//li[contains(@id,'"
                    + elements.get(i).getAttribute("id")+"')]/div/h3"));
            //System.out.println("###############" + element.getText());
            if(element.getText().contains(message)) {
                clickElement(driver.findElement(By.xpath("//li[contains(@id,'"
                        + elements.get(i).getAttribute("id")+"')]//" +
                        "*//a[contains(text(),'Read more')]")));
                break;
            }
        }

    }
	
    public void clickFileUpload() {
        try {
            builder = new Actions(driver);
            mouseOverHome = builder.moveToElement(fileUploadButtonElement).build();
            mouseOverHome.perform();
            clickElement(fileUploadButtonElement);
        } catch (Exception ex) {

        }

    }
    public void clickFirstReadMore() {

        clickElement(readMoreButton);
    }

    public void clickView() {

        clickElement(viewMenu);
    }

	public EmailConversation clickNoActionButton() {

		mouseOverHome = builder.moveToElement(noActionButton).build();
		mouseOverHome.perform();
		clickElement(noActionButton);
		emailConversation = new EmailConversation(driver);
		return emailConversation;
	}

	public EmailConversation clickCloseButton() {
		clickElement(closeButton);
		return emailConversation;
	}

    public String getServiceDetails() {
        WebElement element = driver.findElement(
                By.xpath("//table[@id='quotemailhead']/tbody/tr/td/h1"));
        return element.getText();
    }

    public boolean getServiceDetailsFromEmailHeading(String service) {

        List<WebElement> elements = driver.findElements(By.xpath(
                "//div[@class='timeline-item']/h3"));
        boolean status = false;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getText().contains(service)) {

                status = true;
                break;
            }
        }
        return status;
    }

    public String getServiceDetailsFromEmailHead(String service) {

        List<WebElement> elements = driver.findElements(By.xpath(
                "//div[@class='timeline-item']/h3"));
        String status = "";
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getText().contains(service)) {

                status = elements.get(i).getText();
                break;
            }
        }
        return status;
    }

    public boolean getParticularHeadingDetailsFromAllMsg(String message) {

        List<WebElement> elements = driver.findElements(By.xpath("//li[contains" +
                "(@id,'mid')]"));
        boolean status = false;
        WebElement element;
        for(int i=0; i<elements.size();i++) {
            element = driver.findElement(By.xpath("//li[contains(@id,'"
                    + elements.get(i).getAttribute("id")+"')]/div/h3"));
            //System.out.println("###############" + element.getText());
            if(element.getText().contains(message)) {
                status =true;
                break;
            }
        }
        return status;
    }

    public String getTicketFieldValuesFromPayment(String field, boolean
            checkFile) {

        List<WebElement> elements = driver
                .findElements(By.xpath("//table[@id='paysummary']/tbody/tr"));
        String value = "";
        WebElement eachElement;
        List<WebElement> columns;
        int count = 0;
        for (int i = 1; i <= elements.size(); i++) {
            try {
                //System.out.println("size : = : " + elements.size());
                columns = driver.findElements(By.xpath(
                        "//table[@id='paysummary']/tbody/tr[" + i + "]/td"));

                //System.out.println("G csize : = : " + columns.size());
                if (columns.size() != 0) {
                    if (columns.size() >= 4) {
                        columns = driver.findElements(By.xpath(
                                "//table[@id='paysummary']/tbody/tr[" + i +
                                        "]/td/p"));
                        //System.out.println("4 ccsize : = : " + columns.size());
                        for (int j = 1; j <= columns.size(); j++) {
                            eachElement = driver.findElement(
                                    By.xpath("//table[@id='paysummary']/tbody/tr[" + i
                                            + "]/td[" + j + "]/p"));
                            /*System.out.println(" cvaluesize : = : " +
                                    eachElement.getText());*/
                            if (((eachElement.getText()).trim()).equals(field)) {
                                /**System.out.println("+++++++++++"+eachElement
                                        .getText() +
                                        "+++++++++++++");**/
                                value = driver.findElement(
                                        By.xpath("//table[@id='paysummary']/tbody/tr["
                                                + (i + 1) + "]/td[" + j + "]/p"))
                                        .getText();
                                //System.out.println("4 cvalue : = : " + value);
                                count = 1;
                                break;
                            }
                        }
                    } else if (columns.size() == 2) {
                        if (checkFile) {
                            for (int j = 1; j <= columns.size(); j++) {
                                eachElement = driver.findElement(
                                        By.xpath("//table[@id='paysummary']/tbody/tr[" + i
                                                + "]/td[" + j + "]"));
                                //System.out.println("2cvaluesize : = : " +
                                       // eachElement
                                        //.getText());
                                if (eachElement.getText().contains(field)) {
                                    value = driver.findElement(
                                            By.xpath("//table[@id='paysummary']/tbody/tr["
                                                    + (i + 1) + "]/td[" + j + "]"))
                                            .getText();
                                    //System.out.println("2 cvalue : = : " +
                                           // value);
                                    count = 1;
                                    break;
                                }
                            }
                        } else {
                            eachElement = driver.findElement(
                                    By.xpath("//table[@id='paysummary']/tbody/tr[" + i
                                            + "]/td[1]/p"));
                           // System.out.println("2 test : = : " + eachElement
                                 //   .getText());
                            if (eachElement.getText().contains(field)) {
                                value = driver.findElement(
                                        By.xpath("//table[@id='paysummary']/tbody/tr["
                                                + i + "]/td[2]/p"))
                                        .getText();
                                //System.out.println("2 va : = : " +value);
                                break;
                            }
                        }
                    } else if (columns.size() == 1) {
                        value = driver.findElement(
                                By.xpath("//table[@id='paysummary']/tbody/tr["
                                        + i + "]/td[1]"))
                                .getText();
                        if (value.contains(field)) {
                            //System.out.println("1 va : = : " +value);
                            break;
                        }
                    }
                    if (count == 1) {
                        break;
                    }
                }
            } catch (Exception ex) {
                continue;
            }
        }
        return value;
    }

    public String getTicketValuesFromPayment(String field, boolean checkFile) {

        List<WebElement> elements = driver
                .findElements(By.xpath("//table[@id='paysummary']/tbody/tr"));
        String value = "";
        WebElement eachElement;
        List<WebElement> columns;
        int count = 0;
        for (int i = 1; i <= elements.size(); i++) {
            try {

                columns = driver.findElements(By.xpath(
                        "//table[@id='paysummary']/tbody/tr[" + i + "]/td"));

                if (columns.size() != 0) {
                    if (columns.size() == 2) {
                        if (checkFile) {
                            for (int j = 1; j <= columns.size(); j++) {
                                eachElement = driver.findElement(
                                        By.xpath("//table[@id='paysummary']/tbody/tr[" + i
                                                + "]/td[" + j + "]/b"));
                                if (eachElement.getText().contains(field)) {
                                    value = driver.findElement(
                                            By.xpath("//table[@id='paysummary']/tbody/tr["
                                                    + (i + 1) + "]/td[" + j +
                                                    "]"))
                                            .getText();
                                    count = 1;
                                    break;
                                }
                            }
                        } else {
                            eachElement = driver.findElement(
                                    By.xpath("//table[@id='paysummary']/tbody/tr[" + i
                                            + "]/td[1]/b"));
                            if (eachElement.getText().contains(field)) {
                                value = driver.findElement(
                                        By.xpath("//table[@id='paysummary']/tbody/tr["
                                                + i + "]/td[2]"))
                                        .getText();
                                break;
                            }
                        }
                    } else if (columns.size() == 1) {
                        value = driver.findElement(
                                By.xpath("//table[@id='paysummary']/tbody/tr["
                                        + i + "]/td[1]"))
                                .getText();
                        if (value.contains(field)) {
                            break;
                        }
                    }
                    if (count == 1) {
                        break;
                    }
                }
            } catch (Exception ex) {
                continue;
            }
        }
        return value;
    }

	public String getTicketFieldValues(String field) {

		List<WebElement> elements = driver
				.findElements(By.xpath("//table[@id='quotemailbody']/tbody/tr"));
		String value = "";
		WebElement eachElement;
		List<WebElement> columns;
		for (int i = 1; i <= elements.size(); i++) {
			try {

				columns = driver.findElements(By.xpath(
						"//table[@id='quotemailbody']/tbody/tr[" + i + "]/td"));
				if (columns.size() == 2) {
					eachElement = driver.findElement(
							By.xpath("//table[@id='quotemailbody']/tbody/tr[" + i
									+ "]/td[1]/p"));
					if (eachElement.getText().contains(field)) {
						value = driver.findElement(
								By.xpath("//table[@id='quotemailbody']/tbody/tr["
										+ i + "]/td[2]/p"))
								.getText();
						break;
					}
				}

			} catch (Exception ex) {
				continue;
			}
		}
		return value;
	}

	public void clickSignOut() {

        WebElement element = driver.findElement(By.className("user-image"));
        element.click();
        element = driver.findElement(By.linkText("Sign out"));
        element.click();
    }

    public void openAllEmailContents() {

        builder.keyDown(Keys.SHIFT).sendKeys("e").keyUp(Keys.SHIFT).perform();
    }


    public String readAllEmailContentForFileInfo(String fileName) {
        List<WebElement> elements = driver
                .findElements(By.xpath("//li[contains(@id,'mid')]//..//div[@class='timeline-body']/div"));
        String temp = "";
        for (int i = 0; i <elements.size(); i++) {
            if(elements.get(i).getText().contains(fileName)) {

                temp = elements.get(i).getText();
                System.out.println("++++\n"+temp+"++++\n");
                break;
            }
        }
        return temp.trim();
    }
}
