package com.task.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.bean.CryptoPriceInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * This is implemented class of @{@link CryptoInfoService}  which provide the functionality to transform the given input and provide the output in desire result.
 * this is invoked by {@link com.task.controlller.CryptoCurrencyRestClientImpl}
 */
public class CryptoInfoServiceImpl implements CryptoInfoService{

    private ObjectMapper objectMapper;

    /**
     * default constructor initialization with @{@link ObjectMapper} object
     * to inject dependency
     */
    public CryptoInfoServiceImpl(){
        objectMapper = new ObjectMapper();
    }

    /**
     * The method takes two argument the jsonResponse as inputstream which should have all the required information of bitcoin prices and details when web api called and the other parameter is currency value .
     * it returns current bitcoin rate after converting json into bean object and extract the rate value from map.
     * May throws IOException while converting json inputStream to bean object
     * @InputStream jsonResponseSrc
     * @String currency
     * @return String
     * @Throws @{@link IOException}
     */
    @Override
    public String getCurrentBitcoinRate(InputStream  jsonResponseSrc, String currency) throws IOException {
         if(currency!=null && jsonResponseSrc!=null) {
             CryptoPriceInfo cryptoPriceInfo = objectMapper.readValue(jsonResponseSrc, CryptoPriceInfo.class);
             Map bitcoinPriceListInVariousCurrency = cryptoPriceInfo.getBpi();
             Object obj = bitcoinPriceListInVariousCurrency.get(currency.toUpperCase());
             if (obj != null) {
                 String rate = ((Map<String, String>) obj).get("rate");
                 return rate;
             } else return "Bitcoin rate is not available for " + currency;
         }
         else return null;
    }

    /**
     * The method takes two argument the jsonResponse as inputstream which should have all the required information of bitcoin prices and details when web api called . the jsonresponse shoul contain bitcoin rate of last 90 days.
     * and the other parameter is currency value .
     * it returns current bitcoin rate after converting json into bean object and extract the rate value from map.
     * it sort the all values and return the rates as double in ascending order
     *  May throws IOException while converting json inputStream to bean object
     * @InputStream jsonResponseSrc
     * @String currency
     * @return {@link java.lang.reflect.Array @java.lang.Double}
     * @Throws @{@link IOException}
     */
    @Override
    public double[] getHistoricalBitcoinRate(InputStream jsonResponseSrc, String currency) throws IOException {
        if(currency!=null && jsonResponseSrc!=null) {
            CryptoPriceInfo cryptoPriceInfo = objectMapper.readValue(jsonResponseSrc, CryptoPriceInfo.class);
            Map<String, Object> bitcoinPriceListInVariousCurrency = cryptoPriceInfo.getBpi();
            if (bitcoinPriceListInVariousCurrency!=null) {
                double[] prices = bitcoinPriceListInVariousCurrency.values().stream().mapToDouble(rate -> Double.parseDouble(rate.toString())).sorted().toArray();
                return prices;
            }
        }
        return null;
    }

    /**
     * The method takes two argument the jsonResponse as inputstream which should have all the required information of bitcoin prices and details when web api called . the jsonresponse shoul contain bitcoin rate of last 90 days.
     * and the other parameter is currency value .
     * it returns current bitcoin rate after converting json into bean object and extract the rate value from map.
     * it returns the lowest and highest rate of bitcoin as double in last 90 days
     * May throws IOException while converting json inputStream to bean object
     * @InputStream jsonResponseSrc
     * @String currency
     * @return {@link java.lang.reflect.Array @java.lang.Double}
     * @Throws @{@link IOException}
     */
    @Override
    public double[] getHighAndLowBitcoinRate(InputStream jsonResponseSrc, String currency) throws IOException {
        double[] prices = getHistoricalBitcoinRate(jsonResponseSrc, currency);
        double[] lowAndHigh = new double[2];
        if(prices !=null && prices.length>1) {
            lowAndHigh[0] = prices[0];
            lowAndHigh[1] = prices[prices.length - 1];
            return  lowAndHigh;
        }
        return  null;
    }
}
