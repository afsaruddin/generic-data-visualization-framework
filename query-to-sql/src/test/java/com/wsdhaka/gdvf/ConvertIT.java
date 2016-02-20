package com.wsdhaka.gdvf;

import com.wsdhaka.gdvf.utils.RESTUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import spark.Spark;

import java.io.IOException;

public class ConvertIT extends BaseIT {
    @Before
    public void setUp() {
        super.setUp();

        Main.main(null);
    }

    @After
    public void tearDown() {
        Spark.stop();
    }

    @Test
    public void testOk() throws IOException {
        JSONObject response = new JSONObject(RESTUtils.doGet(HTTP_QUERY_TO_SQL_HOST + "/querytosql"));
        Assert.assertNotNull(response);
        Assert.assertEquals("SELECT name FROM profession", response.getString("sql"));
    }
}
