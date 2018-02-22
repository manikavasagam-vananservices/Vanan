package com.vanancrm.TestCases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;

import com.vanancrm.Common.TestBase;

public class QuoteInfoProcess extends TestBase {

    private  QuoteInfoForCaptioning quoteInfoForCaptioning;

    @BeforeClass
    public void precondition() throws InterruptedException, AWTException, IOException {
        quoteInfoForCaptioning = new
                QuoteInfoForCaptioning();
        quoteInfoForCaptioning.beforeClass();
        quoteInfoForCaptioning.testCaptioningServices();
        quoteInfoForCaptioning.afterClass();

    }

    @Test(priority = 1)
    public void testQuoteInfoProcess() throws InterruptedException, AWTException,
            IOException {

        System.out.println("Evaluation #1: " + quoteInfoForCaptioning.getTicketId
                ());
    }

    @Test(priority = 2)
    public void testQuoteInfoProcess1() throws InterruptedException, AWTException,
            IOException {

        System.out.println("Evaluation #2: " + quoteInfoForCaptioning.getTicketId
                ());
    }
}
