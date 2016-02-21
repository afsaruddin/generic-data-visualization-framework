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
        testQueryToSQL("{\"select\": [ \"location\" ]}", "SELECT name FROM location");
        testQueryToSQL("{\"select\": [ \"name\" ]}", "SELECT name FROM profession");
        testQueryToSQL("{\"select\": [ \"tour\", \"schedule\" ]}", "SELECT startTime, endTime, costPerPerson FROM tour");
        testQueryToSQL("{\"select\": [ \"tour\", \"cost\" ]}", "SELECT startTime, endTime, costPerPerson FROM tour");
    }

    private void testQueryToSQL(String humanQuery, String expectedSQL) throws IOException {
        JSONObject response = new JSONObject(RESTUtils.doPost(HTTP_QUERY_TO_SQL_HOST + "/querytosql", humanQuery));
        Assert.assertNotNull(response);
        Assert.assertEquals(expectedSQL, response.getString("sql"));
    }
}
