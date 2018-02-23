package com.vanancrm.Task;

import com.vanan.CRM.PageObjects.MainPages.DashBoardPage;
import com.vanan.CRM.PageObjects.MainPages.EmailConversation;
import com.vanan.CRM.PageObjects.WholeSitePages.Login;
import com.vanan.CRM.PageObjects.WholeSitePages.Menus;
import com.vanan.CRM.PageObjects.WholeSitePages.ReadTableData;
import com.vanancrm.Common.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class EmailConversationTask extends TestBase {

    private static String username = "";
    private static String password = "";
    private WebDriver driver;

    private static void getCRMCreadential() {
        try {
            FileReader fileReader = new FileReader(System.getProperty("user.dir")
                    + "/src/test/resources/CRM.txt");
            Properties properties = new Properties();
            properties.load(fileReader);
            username = properties.getProperty("USERNAME");
            password = properties.getProperty("PASSWORD");
        } catch (IOException ex) {
            System.out.println("Exception occured in :=) " + ex);
        }
    }

    @Test
    public void testAllAvailableTest() {
        driver.get("https://secure-dt.com/crm/user/login");
        Login login = new Login(driver);
        DashBoardPage dashBoardPage = login.signIn(username, password);
        Menus menus = dashBoardPage.clickAllProcess();
        menus.clickOthersMenu();
        ReadTableData readTableData = new ReadTableData(driver);
        readTableData.readTableRows();
        readTableData.clickOrderNo("VS00324737");
        String parentWindow = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        for (String windowHandle : handles) {
            if (!windowHandle.equals(parentWindow)) {
                driver.switchTo().window(windowHandle);
                waitForProcessCompletion(10);
                menus.clickEmailConversation();
                waitForProcessCompletion(10);
                EmailConversation emailConversation = new EmailConversation
                        (driver);
                System.out.println(emailConversation
                        .getParticularHeadingDetailsFromAllMsg("Transcription Email Quote")) ;
                driver.close();
                driver.switchTo().window(parentWindow);
            }
        }
    }
    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "/tmp/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        fullScreen(driver);
        getCRMCreadential();
    }

    @AfterClass
    public void afterClass() {

    }

}

