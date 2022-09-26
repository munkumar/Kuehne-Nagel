package com.task.controlller;


import java.io.IOException;

/**
 * The Interface provide the functionality to call the web APIs with given the input and return the desire result.
 * this interface is implemented by @{@link CryptoCurrencyRestClientImpl}
 * This interface can expose to user or API who want to get relevant information about bitcoin prices.
 */
public interface CryptoCurrencyRestClient {


    /**
     * An enum consist of http requestType
     */
    enum RequestType {
        GET;
        String getValue(){
            return GET.toString();
        }
    }

    /**
     * A String object which hold api url to get historic bitcoin details.
     */
     String historicPriceAPiUrl = " https://api.coindesk.com/v1/bpi/historical/close.json?";

    /**
     * A String object which hold api url to get current bitcoin details.
     */
     String currentPriceAPiUrl = "https://api.coindesk.com/v1/bpi/currentprice/";



    /**
     * The method takes an argument as currency value .
     * it call REST API @currentPriceAPiUrl with given parameter and return information of bitcoin prices and details.
     * it returns current bitcoin rate after convert the http response to jsonresponse as inputstream and further pass to corresponding service method which provide desire output
     * it handle all exception and return gracefully message
     * @param  currency
     * @return String
     */
     String fetchCurrentBitcoinRate(String currency);

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
     String[] fetchHighAndLowBitcoinRate(String currency, String StartDate, String endDate);

    /**
     * The method currency argument and provide lowest and highest value of bitcoin in last 90 days
     * it call @fetchHighAndLowBitcoinRate with given parameter date of 90 before from today's as startDate and currentDate as endDate and return information of bitcoin prices and details.
     * it handle all exception and return gracefully message
     * @param  currency
     * @return {@link java.lang.reflect.Array @java.lang.String}
     */
     String[] fetchHighAndLowBitcoinRateInLast90Days(String currency);

    /**
     * The method currency argument and display lowest and highest value of bitcoin in last 90 days along with current bitcoin rate.
     * it call @fetchHighAndLowBitcoinRate  and @fetchCurrentBitcoinRate function with given parameter date of 90 before from today's as startDate and currentDate as endDate and return information of bitcoin prices and details.
     * it handle all exception and return gracefully message
     * @param  currency
     */
     void getBitcoinRateInformation(String currency);


}
