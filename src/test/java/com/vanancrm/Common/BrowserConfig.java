package com.vanancrm.Common;

public interface BrowserConfig {

    String CHROME = "chrome";
    String CHROME_PROPERTY = "webdriver.chrome.driver";
    String CHROME_OS_WINDOWS = System.getProperty("user.dir") + "//src//test//resources//WeBDrivers//chromedriver.exe";
    String CHROME_OS_LINUX = System.getProperty("user.dir") + "//src//test//resources//WeBDrivers//chromedriverlinux";
    String CHROME_OS_MAC = System.getProperty("user.dir") + "//src//test//resources//WeBDrivers//chromedrivermac";

    String FIREFOX = "firefox";
    String FIREFOX_PROPERTY = "webdriver.firefox.marionette";
    String FIREFOX_OS_WINDOWS = System.getProperty("user.dir") + "\\src\\test\\resources\\WeBDrivers\\geckodriver.exe";
    String FIREFOX_OS_LINUX = System.getProperty("user.dir") + "//src//test//resources//WeBDrivers//geckodriverlinux";
    String FIREFOX_OS_MAC = System.getProperty("user.dir") + "//src//test//resources//WeBDrivers//geckodrivermac";

    String IE = "ie";
    String IE_PROPERTY = "webdriver.ie.driver";
    String IE_OS_WINDOWS = System.getProperty("user.dir") + "\\src\\test\\resources\\WeBDrivers\\IEDriverServer.exe ";

    String SAFARI = "safari";

    int IMPLICIT_WAITING_TIME = 20;
}
