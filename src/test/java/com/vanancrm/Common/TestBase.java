package com.vanancrm.Common;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;


public class TestBase implements FilePaths, BrowserConfig, AppData, CredentialData {
	
	public String liveUrl = "https://secure-dt.com/crm/user/login";
	public String stagingUrl = "http://texasmutliservices.com/crm/user/login";
	
	public String liveAccess = System.getProperty("user.dir") + "/src/test/resources/CRM.txt";
	public String stagingAccess = System.getProperty("user.dir") + "/src/test/resources/Staging.txt";
	
	public String driverLocation = "/tmp/chromedriver";
	
	public static File file;
	public static FileInputStream fileInput;
	public FileOutputStream fileOutput;
	
	public static HSSFWorkbook workbook;
	public static HSSFSheet sheet;
	public static HSSFCell cell;
	 
	public static SetData setData = new SetData();
	
	public static List<String> readUrls(String filePath) throws IOException {
		
		file = new File(filePath);
		fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		List<String> urls = new ArrayList<String>();
		for (int i=1; i <= properties.size() ; i++) {
			urls.add(properties.getProperty("url"+i));
		}
		fileInput.close();
		return urls;
	}
	
	public void fullScreen(WebDriver driver) {
		
		try {
			/*Robot robot = new Robot();
			robot.keyPress(java.awt.event.KeyEvent.VK_F11);
			robot.keyRelease(java.awt.event.KeyEvent.VK_F11);
			TimeUnit.SECONDS.sleep(10);*/
			driver.manage().window().maximize();
		} catch (Exception e) {

			//driver.manage().window().maximize();
		}
	}

	public void takeScreenshot(WebDriver driver, String screenshotname,
			String location) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.body.style.zoom='50%'");
			screenshot(driver, screenshotname, location);
			TimeUnit.SECONDS.sleep(50);
			js.executeScript("document.body.style.zoom='100%'");
			TimeUnit.SECONDS.sleep(50);
		} catch (Exception e) {

		}
		/*
		 * Screenshot screenshot = new
		 * AShot().shootingStrategy(ShootingStrategies.viewportPasting(10000)).
		 * takeScreenshot(driver); ImageIO.write(screenshot.getImage(),"PNG",new
		 * File(System.getProperty("user.dir")+"/screenshot/"+screenshotname+".png"));
		 * 
		 * 
		 * 
		 * Screenshot screenshot = new AShot().takeScreenshot(driver);
		 * ImageIO.write(screenshot.getImage(),"PNG",new
		 * File(System.getProperty("user.dir")+"/screenshot/"+screenshotname+".png"));
		 */

	}

	public void screenshot(WebDriver driver, String screenshotname,
			String location) throws IOException {

		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(location + screenshotname + ".png");
		FileUtils.copyFile(SrcFile, DestFile);
	}

	public void screenshot(WebDriver driver, String screenshotname) {
		try {
			TakesScreenshot scrShot = ((TakesScreenshot) driver);
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile = new File(System.getProperty("user.dir")
					+ "/src/test/resources/" + screenshotname + ".png");
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException ex) {
			System.out.println("Unable to take a screen shot");
		}
	}
	
	
	public static void readData() throws IOException {
		
		file =new File("src/test/resources/input.xls");
		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		sheet = workbook.getSheetAt(0);
		 for(int i=1; i <=sheet.getLastRowNum(); i++) {
			 cell = sheet.getRow(i).getCell(1);
			 setData.setFileType(cell.getStringCellValue());
			 cell = sheet.getRow(i).getCell(2);
			 setData.setPage(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(4);
			 setData.setSourceLanguage(cell.getStringCellValue());
			 cell = sheet.getRow(i).getCell(5);
			 setData.setTransactionLanguage(cell.getStringCellValue());
			 cell = sheet.getRow(i).getCell(6);
			 setData.setCategory(cell.getStringCellValue());
			 cell = sheet.getRow(i).getCell(7);
			 setData.setTat(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(8);
			 setData.setAmount(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(9);
			 setData.setTotal(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(10);
			 setData.setExpedite(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(11);
			 setData.setSubTotal(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(12);
			 setData.setTransaction(cell.getNumericCellValue());
			 cell = sheet.getRow(i).getCell(13);
			 setData.setGrandtotals(cell.getNumericCellValue());
		 }
	}	
	
	public static void readNewTranslateData() throws IOException {

		file = new File("src/test/resources/NewTranslation.xls");
		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		sheet = workbook.getSheetAt(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			cell = sheet.getRow(i).getCell(1);
			setData.setFileType(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(2);
			setData.setPage(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(4);
			setData.setSourceLanguage(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(5);
			setData.setTransactionLanguage(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(6);
			setData.setTat(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(7);
			setData.setAmount(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(8);
			setData.setTotal(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(9);
			setData.setExpedite(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(10);
			setData.setSubTotal(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(11);
			setData.setTransaction(cell.getNumericCellValue());
			cell = sheet.getRow(i).getCell(12);
			setData.setGrandtotals(cell.getNumericCellValue());
		}
	}

	public void readFile(String filePath) throws IOException {
		
		file =new File(filePath);

		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		sheet = workbook.getSheetAt(0);
		for(int i=1; i <=sheet.getLastRowNum(); i++) {
			System.out.println("i=="+i);
		}
	}

	public static void readSiteUrl() throws IOException {

		file = new File("src/test/resources/urls.xls");
		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		sheet = workbook.getSheetAt(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			cell = sheet.getRow(i).getCell(0);
			setData.setUrls(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(1);
			setData.setTitles(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(2);
			setData.setSiteNames(cell.getStringCellValue());
		}
	}
	
	public static void readAllSiteUrl() throws IOException {

		file = new File("src/test/resources/AllSite.xls");
		fileInput = new FileInputStream(file);
		workbook = new HSSFWorkbook(fileInput);
		sheet = workbook.getSheetAt(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			cell = sheet.getRow(i).getCell(1);
			setData.setUrls(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(2);
			setData.setLocators(cell.getStringCellValue());
			cell = sheet.getRow(i).getCell(3);
			setData.setUIElements(cell.getStringCellValue());
		}
	}

	public void waitForProcessCompletion(int waitTime) {
		try {
			TimeUnit.SECONDS.sleep(waitTime);
		} catch (Exception e) {
		}
	}

	public int generateName() {

        Random r = new Random();
        return Math.abs(r.nextInt()) % 10000;
    }

	public static WebDriver driver;


	private static String browserName = "";
	private static String operatingSystem = "";

	private static void getDriverDetails() {
		//browserName = System.getProperty("browserName").toLowerCase();
		//operatingSystem = System.getProperty("operatingSystem").toLowerCase();
		browserName = "chrome";
		operatingSystem = detectOS();
	}

	public static void setDriver() {
		getDriverDetails();
		String browserOS = "";
		switch (browserName) {
			case CHROME:

				if (operatingSystem.indexOf("win")>=0) {
					browserOS = CHROME_OS_WINDOWS;
				}
				if (operatingSystem.indexOf("nux")>=0||operatingSystem.indexOf("nix")>=0||operatingSystem.indexOf("aix")>=0) {
					browserOS = CHROME_OS_LINUX;
				}
				if (operatingSystem.indexOf("mac")>=0||operatingSystem.indexOf("darwin")>=0) {
					browserOS = CHROME_OS_MAC;
				}
				System.setProperty(CHROME_PROPERTY, browserOS);
				driver = new ChromeDriver();
				break;

			case FIREFOX:

				if (operatingSystem.indexOf("win")>=0) {
					browserOS = FIREFOX_OS_WINDOWS;
				}
				if (operatingSystem.indexOf("nux")>=0||operatingSystem.indexOf("nix")>=0||operatingSystem.indexOf("aix")>=0) {
					browserOS = FIREFOX_OS_LINUX;
				}
				if (operatingSystem.indexOf("mac")>=0||operatingSystem.indexOf("darwin")>=0) {
					browserOS = FIREFOX_OS_MAC;
				}
				System.setProperty(FIREFOX_PROPERTY, browserOS);
				driver = new FirefoxDriver();
				break;

			case IE:

				System.setProperty(IE_PROPERTY, IE_OS_WINDOWS);
				driver = new InternetExplorerDriver();
				break;

			case SAFARI:
				driver = new SafariDriver();
				break;
		}
		setImplicitWaitingTime();
		setBrowserSizeMaximum();
	}

	private static void setImplicitWaitingTime() {

		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAITING_TIME, TimeUnit.SECONDS);
	}

	private static void setBrowserSizeMaximum() {

		driver.manage().window().maximize();
	}

	public void takeSnapShot(WebDriver webdriver, String fileName) {

		try {
			System.out.println(fileName+"=================>");
			TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			File DestFile = new File(screenshotParentPath + fileName);
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void tearDown() {

		driver.quit();
	}

	public void waitingTime(int wait) {

		try{TimeUnit.SECONDS.sleep(wait);} catch (InterruptedException ex) {ex.printStackTrace();}
	}

	private static String detectOS() {
		return System.getProperty("os.name","generic").toLowerCase(Locale.ENGLISH);
	}



	public double roundValues(double data) {
		return (double)Math.round(data*100)/100;
	}
}
