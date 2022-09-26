package com.task.service;

import java.io.IOException;
import java.io.InputStream;

/**
 * The Interface provide the functionality to transform the input and give the ouput in desire result.
 * this interface is implemented by @{@link CryptoInfoServiceImpl}
 */
public interface CryptoInfoService {

     /**
      * The method takes two argument the jsonResponse as inputstream which should have all the required information of bitcoin prices and details when web api called and the other parameter is currency value .
      * it returns current bitcoin rate after converting json into bean object and extract the rate value from map.
      * May throws IOException while converting json inputStream to bean object
      * @InputStream jsonResponseSrc
      * @String currency
      * @return String
      * @Throws @{@link IOException}
      */
     String getCurrentBitcoinRate(InputStream jsonResponseSrc, String currency) throws IOException;

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
     double[] getHistoricalBitcoinRate(InputStream jsonResponseSrc, String currency) throws IOException;

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
     double[] getHighAndLowBitcoinRate(InputStream jsonResponseSrc, String currency) throws IOException;

}
