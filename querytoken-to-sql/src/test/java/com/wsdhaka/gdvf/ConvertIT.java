package com.wsdhaka.gdvf;

import com.wsdhaka.gdvf.utils.RESTUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
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

    @Test
    public void testCORS() throws IOException {
        HttpResponse httpResponse = RESTUtils.doOptions(HTTP_QUERY_TO_SQL_HOST + "/submitquery");
        Assert.assertEquals("Access-Control-Allow-Origin: *", StringUtils.join(httpResponse.getHeaders("Access-Control-Allow-Origin"), ","));
        Assert.assertEquals("Access-Control-Allow-Methods: GET,PUT,POST,DELETE,OPTIONS", StringUtils.join(httpResponse.getHeaders("Access-Control-Allow-Methods"), ","));
        Assert.assertEquals("Access-Control-Allow-Headers: Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin", StringUtils.join(httpResponse.getHeaders("Access-Control-Allow-Headers"), ","));
        Assert.assertEquals("Access-Control-Allow-Credentials: true", StringUtils.join(httpResponse.getHeaders("Access-Control-Allow-Credentials"), ","));
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
