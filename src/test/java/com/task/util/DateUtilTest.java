package com.task.util;

import org.junit.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtilTest {

    private static DateUtil dateUtil;
    private static  DateFormat dtf ;

    @BeforeClass
    public static void init() {
        dateUtil = new DateUtil();
        dtf = new SimpleDateFormat("yyyy-MM-dd");
    }

    @AfterClass
    public static void destroy() {
        dateUtil = null;
        dtf = null;
    }


    @Test
    public void test_getCurrentDateAsString() throws ParseException {
        String dateInString = dateUtil.getCurrentDateAsString();
        Date date = dtf.parse(dateInString);
        Assert.assertEquals(date, dtf.parse(dtf.format(new Date())));
    }

    @Test
    public void whenGettingDateUsingCalendar_thenReturnDateOf90Prior() throws ParseException {
        String dateInString = dateUtil.getDateBefore90daysAsString();
        LocalDate currentDate = LocalDate.now();
        LocalDate currentDateMinus3Months = currentDate.minusDays(90);
        Assert.assertEquals(dateInString,currentDateMinus3Months.toString());
    }




}
