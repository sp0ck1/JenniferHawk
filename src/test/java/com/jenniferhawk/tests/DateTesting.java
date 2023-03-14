package com.jenniferhawk.tests;

import org.junit.jupiter.api.Test;;

import java.sql.Timestamp;

import java.util.Date;

public class DateTesting {

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Test
    public void testDateTimeOutput() {
        System.out.println(timestamp.toString());

    }
}
