package com.task.bean;

import java.util.Map;

/**
 * The CryptoPriceInfo class is a pojo class , which will map to json response from coindesk api .
 * This will bind the json string response to a java object.
 */
public class CryptoPriceInfo {

    /**
     * A Map which are having string as key and object as value . The value could be a string or Map
     */
    private Map<String, Object> bpi;
    /**
     * A String to keep comment or additional info about crypto price details.
     */
    private String disclaimer;

    /**
     * A {@link Time} instance used here to provide date & time details info about crypto and its price.
     */
    private Time time;

    public Map<String, Object> getBpi() {
        return bpi;
    }

    public void setBpi(Map<String, Object> bpi) {
        this.bpi = bpi;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
