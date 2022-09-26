package com.task.util;

import org.junit.*;

import java.text.ParseException;

public class URLBuilderTest {

    private static URLBuilder urlBuilder;

    private static String historicPriceAPiUrl = "https://api.coindesk.com/v1/bpi/historical/close.json?";

    private static String currentPriceAPiUrl = "https://api.coindesk.com/v1/bpi/currentprice/";

    private String currency = "eur";

    @Before
    public  void init() {
        urlBuilder = new URLBuilder(currentPriceAPiUrl);
    }

    @After
    public  void destroy() {
        urlBuilder  = null;
    }


    @Test
    public void test_updateURLWithExtension() {
        urlBuilder.updateURLWithExtension(currency);
        Assert.assertEquals("https://api.coindesk.com/v1/bpi/currentprice/eur.json", urlBuilder.getURL());
    }

    @Test
    public void test_addParameter() throws ParseException {
        urlBuilder = new URLBuilder(historicPriceAPiUrl);
        urlBuilder.addParameter("start","2022-09-25");
        urlBuilder.addParameter("end","2022-09-29");
        urlBuilder.addParameter("currency",currency);
        Assert.assertEquals("https://api.coindesk.com/v1/bpi/historical/close.json?start=2022-09-25&end=2022-09-29&currency=eur",urlBuilder.getURL());
    }

    @Test
    public void test_addParameter_WithNullKeyAndValue() {
        urlBuilder = new URLBuilder(historicPriceAPiUrl);
        urlBuilder.addParameter(null,"2022-09-25");
        urlBuilder.addParameter("start",null);
        Assert.assertEquals("https://api.coindesk.com/v1/bpi/historical/close.json?",urlBuilder.getURL());
    }

    @Test
    public void test_addParameter_WithEmptyKeyAndValue() {
        urlBuilder = new URLBuilder(historicPriceAPiUrl);
        urlBuilder.addParameter("","");
        Assert.assertEquals("https://api.coindesk.com/v1/bpi/historical/close.json?=",urlBuilder.getURL());
    }
}


