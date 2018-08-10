package com.vanancrm.Task;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class GMail {
    private static String username = "";
    private static String password = "";
    public static void main(String[] args) throws InterruptedException, IOException {
        getEmailCreadential();
        // TODO Auto-generated method stub
        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS);
        driver.get("https://accounts.google.com");
        // gmail login
        driver.findElement(By.id("identifierId")).sendKeys(username);
        driver.findElement(By.id("identifierNext")).click();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("passwordNext")).click();

        // some optional actions for reaching gmail inbox
        driver.findElement(By.xpath("//a[@aria-label='Google apps']")).click();
        driver.findElement(By.id("gb23")).click();
        driver.findElement(By.xpath("//table/tbody/tr/td/div//../div/span/b[contains(text(),'Translation Price Quote')]")).click();
        System.out.println(driver.findElement(By.xpath("//table[@id=contains(text(),'paysummary')]")).getAttribute("id"));
        //System.out.println(getQuoteInfo(driver, "Source language", false));
        // clicks compose
        //driver.findElement(By.cssSelector(".T-I.J-J5-Ji.T-I-KE.L3")).click();
        // types message in body without hampering signature
        //driver.findElement(By.id(":pg")).sendKeys("This is an auto-generated mail");;

    }

    private static void getEmailCreadential() throws IOException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir")
                + "/src/test/resources/gmail.txt");
        Properties properties = new Properties();
        properties.load(fileReader);
        username = properties.getProperty("USERNAME");
        password = properties.getProperty("PASSWORD");
    }

    private static String getQuoteInfo(WebDriver driver, String field, boolean
            checkFile) {
        List<WebElement> elements = driver
                .findElements(By.xpath("//table[@id=contains(text(),'paysummary')]/tbody/tr"));
        String value = "";
        WebElement eachElement;
        List<WebElement> columns;
        int count = 0;
        for (int i = 1; i <= elements.size(); i++) {
            try {
                System.out.println("size : = : " + elements.size());
                columns = driver.findElements(By.xpath(
                        "//table[@id=contains(text(),'paysummary')]/tbody/tr[" + i + "]/td"));

                System.out.println("G csize : = : " + columns.size());
                if (columns.size() != 0) {
                    if (columns.size() >= 4) {
                        columns = driver.findElements(By.xpath(
                                "//table[@id=contains(text(),'paysummary')]/tbody/tr[" + i +
                                        "]/td/p"));
                        System.out.println("4 ccsize : = : " + columns.size());
                        for (int j = 1; j <= columns.size(); j++) {
                            eachElement = driver.findElement(
                                    By.xpath("//table[@id=contains(text(),'paysummary')]/tbody/tr[" + i
                                            + "]/td[" + j + "]/p"));
                            System.out.println(" cvaluesize : = : " +
                                    eachElement.getText());
                            if (((eachElement.getText()).trim()).equals(field)) {
                                System.out.println("+++++++++++"+eachElement
                                 .getText() +
                                 "+++++++++++++");
                                value = driver.findElement(
                                        By.xpath("//table[@id=contains(text(),'paysummary')]/tbody/tr["
                                                + (i + 1) + "]/td[" + j + "]/p"))
                                        .getText();
                               System.out.println("4 cvalue : = : " + value);
                                count = 1;
                                break;
                            }
                        }
                    } else if (columns.size() == 2) {
                        if (checkFile) {
                            for (int j = 1; j <= columns.size(); j++) {
                                eachElement = driver.findElement(
                                        By.xpath("//table[@id=contains(text(),'paysummary')]/tbody/tr[" + i
                                                + "]/td[" + j + "]"));
                                System.out.println("2cvaluesize : = : " +  eachElement
                                .getText());
                                if (eachElement.getText().contains(field)) {
                                    value = driver.findElement(
                                            By.xpath("//table[@id=contains(text(),'paysummary')]/tbody/tr["
                                                    + (i + 1) + "]/td[" + j + "]"))
                                            .getText();
                                    System.out.println("2 cvalue : = : " +
                                     value);
                                    count = 1;
                                    break;
                                }
                            }
                        } else {
                            eachElement = driver.findElement(
                                    By.xpath("//table[@id=contains(text(),'paysummary')]/tbody/tr[" + i
                                            + "]/td[1]/p"));
                             System.out.println("2 test : = : " + eachElement
                               .getText());
                            if (eachElement.getText().contains(field)) {
                                value = driver.findElement(
                                        By.xpath("//table[@id=contains(text(),'paysummary')]/tbody/tr["
                                                + i + "]/td[2]/p"))
                                        .getText();
                                System.out.println("2 va : = : " +value);
                                break;
                            }
                        }
                    } else if (columns.size() == 1) {
                        value = driver.findElement(
                                By.xpath("//table[@id=contains(text(),'paysummary')]/tbody/tr["
                                        + i + "]/td[1]"))
                                .getText();
                        if (value.contains(field)) {
                            System.out.println("1 va : = : " +value);
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
}
