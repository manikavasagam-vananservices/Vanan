package com.vanancrm.Task;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.vanancrm.Common.TestBase;

public class browser extends TestBase{

    /*private static Selenium selenium;

    protected void setUp() {

        if (selenium == null) {
            selenium = new DefaultSelenium("localhost", 4444, "*chrome",
                    "https://vananservices.com/");
            selenium.start();
            selenium.windowMaximize();
        }
    }

    public Selenium getSelenium() {
        return selenium;
    }


    protected void tearDown() {
        if (selenium != null) {
            selenium.stop();
            selenium = null;
        }
    }*/

    public static void main(String args[]) throws InterruptedException {

        /*browser br=new browser();
        br.setUp();
        br.getSelenium();
        br.tearDown();*/
        int seleniumPort = Integer.parseInt( System.getProperty( "selenium" +
                ".port", findFreePort()+"" ) );
        String browser = System.getProperty( "seleniumBrowser", "*firefox" );
        String serverUrl = System.getProperty( "serverUrl", "http://localhost:9090/" );

        DefaultSelenium s = new DefaultSelenium( "localhost", seleniumPort, browser, serverUrl );
        s.start(  );
        s.open( "https://vananservices.com/" );
        //s.type( "who", "foo" );
        //s.click( "send-btn" );
        // wait a bit ajax response
        //Thread.sleep( 1000 );
        //String text = s.getText( "response" );
        //assertEquals( "Hello foo", text );

    }
}
