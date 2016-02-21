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
        testQueryToSQL("{\"select\": [ \"location\" ], \"something\": \"else\"}", "SELECT name FROM location");
        testQueryToSQL("{\"select\": [ \"name\" ]}", "SELECT name FROM profession");
        testQueryToSQL("{\"select\": [ \"tour\", \"schedule\" ]}", "SELECT startTime, endTime, costPerPerson FROM tour");
        testQueryToSQL("{\"select\": [ \"tour\", \"cost\" ]}", "SELECT startTime, endTime, costPerPerson FROM tour");
    }

    @Test
    public void testFailed() throws IOException {
        testFailedFor400("");
        testFailedFor400("abcdef");
        testFailedFor400("{}");
        testFailedFor400("true");
        testFailedFor400("null");
        testFailedFor400("false");
        testFailedFor400("{\"something\": 10}");
    }

    private void testQueryToSQL(String humanQuery, String expectedSQL) throws IOException {
        JSONObject response = new JSONObject(RESTUtils.doPost(HTTP_QUERY_TO_SQL_HOST + "/querytosql", humanQuery));
        Assert.assertNotNull(response);
        Assert.assertEquals(expectedSQL, response.getString("sql"));
    }

    private void testFailedFor400(String dataToSend) {
        try {
            RESTUtils.doPost(HTTP_QUERY_TO_SQL_HOST + "/querytosql", dataToSend);
            Assert.fail("REST call should not be okay.");
        } catch (IOException e) {
            Assert.assertTrue(e.getMessage().contains("400 Bad Request"));
        }
    }
}
