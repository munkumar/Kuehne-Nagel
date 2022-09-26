package com.task.controlller;


import com.task.exception.CryptoException;
import com.task.service.CryptoInfoService;
import com.task.service.CryptoInfoServiceImpl;
import com.task.util.DateUtil;
import com.task.util.URLBuilder;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * This is implemented class of @{@link CryptoCurrencyRestClient}  provide the functionality to call the web APIs with given the input and return the desire result.
 */
public class CryptoCurrencyRestClientImpl implements CryptoCurrencyRestClient {

    /** The Constant LOG. */
    static final Logger LOG = Logger.getLogger("RestClient");

    private CryptoInfoService cryptoInfoService;

    /**
     * default constructor initialization with @{@link CryptoInfoService} object
     * to inject dependency
     */
    public CryptoCurrencyRestClientImpl()
    {
        this.cryptoInfoService = new CryptoInfoServiceImpl();
    }

    /**
     * The method used to create the HTTP web connection for given url and http request type.
     * it accept application/json as respone in RequestProperty
     * it may throw an Exception while connecting to web url .
     * @param apiUrl
     * @param requestMethodType
     * @return {@link HttpURLConnection}
     * @throws {@link Exception}
     */
    private HttpURLConnection getRestConnection(String apiUrl, String requestMethodType) throws Exception {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethodType);
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new CryptoException("Failed : HTTP Error code : ", conn.getResponseCode());
            }
            return conn;
    }

    /**
     * The method used to close the HTTP web connection.
     */
    private void closeWebConnection(HttpURLConnection conn){
        if(conn !=null) conn.disconnect();
    }


    /**
     * The method takes an argument as currency value .
     * it call REST API @currentPriceAPiUrl with given parameter and return information of bitcoin prices and details.
     * it returns current bitcoin rate after convert the http response to jsonresponse as inputstream and further pass to corresponding service method which provide desire output
     * it handle all exception and return gracefully message
     * @param  currency
     * @return String
     */
    public String fetchCurrentBitcoinRate(String currency){
        URLBuilder urlBuilder = new URLBuilder(currentPriceAPiUrl);
        urlBuilder.updateURLWithExtension(currency);
        String url = urlBuilder.getURL();
        HttpURLConnection connection = null;
        String rate = "";
        try {
            connection = getRestConnection(url,RequestType.GET.getValue());
            rate = cryptoInfoService.getCurrentBitcoinRate(connection.getInputStream(),currency);
        } catch (CryptoException  e) {
            if(e.getErrorCode()==404)
                return "Sorry, your requested currency "+currency+" is not supported or is invalid to get current bitcoin rate";
            else
                return "some web connection error occurred for currency "+ currency +" "+e.getMessage();
        }
        catch (Exception e){
            e.printStackTrace();
            LOG.severe("Some exception occurred "+e.getMessage());
        }
        finally {
            closeWebConnection(connection);
        }
        return rate;
    }


    /**
     * The method takes three argument startDate,endDate and currency
     * it call REST API @historicPriceAPiUrl with given parameter and return information of bitcoin prices and details.
     * it convert the http response to jsonresponse as inputstream and further pass to corresponding service method which provide desire output
     * the response contain bitcoin rates  as String in ascending order of last 90 days in given currency value
     * it handle all exception and return gracefully message
     * @param  currency
     * @param  StartDate
     * @param  endDate
     * @return {@link java.lang.reflect.Array @java.lang.String}
     */
    @Override
    public String[] fetchHighAndLowBitcoinRate(String currency, String StartDate, String endDate) {
        if(currency==null || currency.isEmpty())
            return new String[]{"Sorry, your requested currency "+currency+" is not supported or is invalid to get historical bitcoin rate"};
        URLBuilder urlBuilder = new URLBuilder(historicPriceAPiUrl);
        urlBuilder.addParameter("start",StartDate);
        urlBuilder.addParameter("end",endDate);
        urlBuilder.addParameter("currency",currency);
        String url = urlBuilder.getURL();
        HttpURLConnection connection = null;
        String[] rates = new String[2];
        try {
            connection = getRestConnection(url,RequestType.GET.getValue());
            double[] rateValues = cryptoInfoService.getHighAndLowBitcoinRate(connection.getInputStream(),currency);
            if(rateValues !=null)
                rates = Arrays.stream(rateValues).mapToObj(rate -> String.valueOf(rate)).toArray(String[]::new);
        } catch (CryptoException  e)
        {
            if(e.getErrorCode()==404)
                 rates[0] = "Sorry, your requested currency "+currency+" is not supported or is invalid to get historical bitcoin rate";
            else rates[0]= "some web connection error occurred for currency "+ currency +" "+e.getMessage();
        }
        catch (Exception e){
            e.printStackTrace();
            LOG.severe("Some exception occurred "+e.getMessage());
        }
        finally {
            closeWebConnection(connection);
        }
        return rates;
    }


    /**
     * The method currency argument and provide lowest and highest value of bitcoin in last 90 days
     * it call @fetchHighAndLowBitcoinRate with given parameter date of 90 before from today's as startDate and currentDate as endDate and return information of bitcoin prices and details.
     * it handle all exception and return gracefully message
     * @param  currency
     * @return {@link java.lang.reflect.Array @java.lang.String}
     */
    @Override
    public String[] fetchHighAndLowBitcoinRateInLast90Days(String currency) {
        if(currency==null || currency.isEmpty())
            return new String[]{"Sorry, your requested currency "+currency+" is not supported or is invalid to get historical bitcoin rate"};
        String currentDate = DateUtil.getCurrentDateAsString();
        String before90days = DateUtil.getDateBefore90daysAsString();
        return fetchHighAndLowBitcoinRate(currency,before90days,currentDate);
    }


    /**
     * The method currency argument and display lowest and highest value of bitcoin in last 90 days along with current bitcoin rate.
     * it call @fetchHighAndLowBitcoinRate  and @fetchCurrentBitcoinRate function with given parameter date of 90 before from today's as startDate and currentDate as endDate and return information of bitcoin prices and details.
     * it handle all exception and return gracefully message
     * @param  currency
     */
    @Override
    public void getBitcoinRateInformation(String currency) {
        if(currency==null || currency.trim().isEmpty())
            System.out.println("currency code can not be empty, please provide a valid currency code value");
        else {
            String currentRate = fetchCurrentBitcoinRate(currency);
            if (!currentRate.isEmpty() && !currentRate.contains(currency)) {
                // Just to display information on console added println method here if we demonstrate API through main method.
                System.out.println("The current bitcoin rate for currency " + currency + " is " + currentRate);
                String[] historicRate = fetchHighAndLowBitcoinRateInLast90Days(currency);
                if (!historicRate[1].isEmpty()) {
                    System.out.println("The lowest Bitcoin rate in the last 90 days, in the requested currency " + currency + " is " + historicRate[0]);
                    System.out.println("The highest Bitcoin rate in the last 90 days, in the requested currency " + currency + " is " + historicRate[1]);
                } else
                    System.out.println(historicRate[0]);
            } else System.out.println(currentRate);
        }
    }
}

