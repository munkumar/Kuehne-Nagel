package com.task.bean;

import java.util.Date;

/**
 * The Time class is a pojo class , which will map to value of time in json response from coindesk api .
 * This will contain the information about the updated time stamp of any currency value for a crypto .
 */
public class Time {

    /**
     * A String object which hold updated time stamp.
     */
    private String updated;
    /**
     * A Date object which contain updated time stamp in ISO format.
     */
    private Date updatedISO;
    /**
     * A String object which hold updated time stamp.
     */
    private String updateduk;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Date getUpdatedISO() {
        return updatedISO;
    }

    public void setUpdatedISO(Date updatedISO) {
        this.updatedISO = updatedISO;
    }

    public String getUpdateduk() {
        return updateduk;
    }

    public void setUpdateduk(String updateduk) {
        this.updateduk = updateduk;
    }
}
