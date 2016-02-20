package com.wsdhaka.gdvf;

import org.junit.Before;

import java.util.TimeZone;

public class BaseIT {
    protected static final String HTTP_QUERY_TO_SQL_HOST = "http://localhost:4567";

    @Before
    protected void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
