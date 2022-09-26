package com.task.service;
import org.junit.*;

import java.io.IOException;
import java.io.InputStream;

public class CryptoInfoServiceTest {

    private static  CryptoInfoService cryptoInfoService;
    private static String currency ;

    private static InputStream currentJsonResponse ;

    private static InputStream historyJsonResponse ;

    private  static String currentJsonResponsePath= "currentRate.json";

    private static String historyJsonResponsePath = "historicalRate.json";
    private static String currentJsonResponsePathWithException = "currentRateAnother.json";

    private ClassLoader classLoader ;

    @BeforeClass
    public static void init() {
        cryptoInfoService = new CryptoInfoServiceImpl();
        currency = "eur";
    }


    @Before
    public void beforeEachTest() {
        classLoader = getClass().getClassLoader();
        currentJsonResponse = classLoader.getResourceAsStream(currentJsonResponsePath);
        historyJsonResponse = classLoader.getResourceAsStream(historyJsonResponsePath);
    }

    @After
    public void afterEachTest() {
        classLoader =null;
        currentJsonResponse = null;
    }



    @Test
    public void givenCurrencyDoesNotExists_whenBitcoinRateIsRetrieved()
            throws IOException {
        //given
        currency = "rup";
        // When
        String rate = cryptoInfoService.getCurrentBitcoinRate(currentJsonResponse,currency);
        // Then
        Assert.assertTrue(!rate.isEmpty());
        Assert.assertTrue(!isNumeric(rate));
        Assert.assertEquals("Bitcoin rate is not available for "+ currency,rate);
    }

    @Test
    public void givenCurrencyExists_whenBitcoinRateIsRetrieved()
            throws IOException {
        // When
        String rate = cryptoInfoService.getCurrentBitcoinRate(currentJsonResponse,currency);
        // Then
        Assert.assertTrue(!rate.isEmpty());
        Assert.assertTrue(isNumeric(rate));
    }

    @Test
    public void givenCurrencyIsNull_whenBitcoinRateIsRetrieved()
            throws IOException {

        // When
        String rate = cryptoInfoService.getCurrentBitcoinRate(currentJsonResponse,null);
        // Then
        Assert.assertNull(rate);
    }

    @Test
    public void givenjsonResponseIsNull_whenBitcoinRateIsRetrieved()
            throws IOException {
        // When
        String rate = cryptoInfoService.getCurrentBitcoinRate(null,currency);
        // Then
        Assert.assertNull(rate);
    }

    @Test
    public void givenCurrencyExists_whenHighAndLowlBitcoinRateIsRetrieved()
            throws IOException {

        // When
        double[] rates = cryptoInfoService.getHighAndLowBitcoinRate(historyJsonResponse,currency);
        // Then
        Assert.assertTrue(rates.length==2);
        Assert.assertTrue(rates[0] > 0.0);
        Assert.assertTrue(rates[1] > 0.0);
    }


    @Test
    public void givenCurrencyIsNull_whenHighAndLowlBitcoinRateIsRetrieved()
            throws IOException {

        // When
        double[] rates = cryptoInfoService.getHighAndLowBitcoinRate(historyJsonResponse,null);
        // Then
        Assert.assertNull(rates);
    }


    @Test
    public void givenjsonResponseIsNull_whenHighAndLowlBitcoinRateIsRetrieved()
            throws IOException {
        // When
        double[] rates = cryptoInfoService.getHighAndLowBitcoinRate(null,currency);
        // Then
        Assert.assertNull(rates);
    }

    @Test(expected = IOException.class)
    public void testDivisionException() throws Exception {
        currentJsonResponse = classLoader.getResourceAsStream(currentJsonResponsePathWithException);
        cryptoInfoService.getCurrentBitcoinRate(currentJsonResponse,"eur");
    }


    @Ignore
    public static boolean isNumeric(String strNum) {
        if (strNum == null || strNum.isEmpty()) {
            return false;
        }
        try {
            strNum = strNum.replace(",","");
            double value = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
