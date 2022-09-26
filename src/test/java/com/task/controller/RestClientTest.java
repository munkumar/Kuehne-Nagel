package com.task.controller;

import com.task.controlller.CryptoCurrencyRestClient;
import com.task.controlller.CryptoCurrencyRestClientImpl;
import com.task.service.CryptoInfoService;
import org.hamcrest.core.IsEqual;
import org.junit.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.*;

public class RestClientTest {
    private static CryptoInfoService cryptoInfoService;
    private static CryptoCurrencyRestClient cryptoCurrencyRestClient;
    private static String historicPriceAPiUrl = " https://api.coindesk.com/v1/bpi/historical/close.json?";

    private static String currentPriceAPiUrl = "https://api.coindesk.com/v1/bpi/currentprice/";
    private URL url ;
    private HttpURLConnection conn;
    private String currency = "";

    @BeforeClass
    public static void init() {
        cryptoCurrencyRestClient = new CryptoCurrencyRestClientImpl();
    }


    @Before
    public void beforeEachTest() {
        currency = "eur";
    }

    @After
    public void afterEachTest() {
        if(conn!=null) conn.disconnect();
    }

    @Test
    public void givenCurrencyDoesNotExists_whenBitcoinInfoIsRetrieved_then404IsReceived()
            throws IOException {

        // Given
        currency = "rup";
        url = new URL(currentPriceAPiUrl+currency+".json");

        // When
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");


        // Then
        assertThat(conn.getResponseCode(), IsEqual.equalTo(404));
    }



    @Test
    public void
    givenRequestWithNoAcceptHeader_whenRequestIsExecuted_thenDefaultResponseContentTypeIsJson()
            throws  IOException {

        // Given
        String jsonMimeType = "application/javascript";
        // Given
        url = new URL(currentPriceAPiUrl+currency+".json");

        // When
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");


        // Then
        String mimeType = conn.getContentType();
        assertEquals( jsonMimeType, mimeType );
    }

    @Test
    public void
    givenInformationExists_whenCurrentBitcoinInformationIsRetrieved_thenRetrievedResourceIsCorrect() {


        // When
        String rate = cryptoCurrencyRestClient.fetchCurrentBitcoinRate(currency);

        // Then
        assertTrue(isNumeric(rate));
    }

    //Need to mock the object and test
    @Test
    public void givenInformationNotExists_whenCurrentBitcoinInformationIsRetrieved_thenRetrievedResourceDisplayMessage() {


        // Given
        currency = "rup";
        // When
        cryptoCurrencyRestClient.getBitcoinRateInformation(currency);
    }




    @Test
    public void
    givenInformationExists_whenHistoricBitcoinInformationIsRetrieved_thenRetrievedResourceIsCorrect() {


        // When
        String[] rates = cryptoCurrencyRestClient.fetchHighAndLowBitcoinRate(currency, "2013-09-01", "2013-09-05");

        // Then
        assertTrue(rates.length>0);
        assertTrue(isNumeric(rates[0]));
        assertTrue(isNumeric(rates[1]));
    }

    @Test
    public void
    givenInformationExists_whenHistoricBitcoinInformationOf90DaysIsRetrieved_thenRetrievedResourceIsCorrect() {


        // When
        String[] rates = cryptoCurrencyRestClient.fetchHighAndLowBitcoinRateInLast90Days(null);

        // Then
        assertTrue(rates.length>0);
        assertTrue(!isNumeric(rates[0]));
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
