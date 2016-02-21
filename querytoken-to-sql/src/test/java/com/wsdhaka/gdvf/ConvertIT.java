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
        testQueryToSQL("{\"text\": \"tour traveller\"}", "SELECT tour.startTime, tour.endTime, tour.costPerPerson, traveller.name, traveller.gender, traveller.age" +
                        " FROM tm.tourtraveller tourtraveller" +
                        " INNER JOIN tm.tour tour ON (tourtraveller.tourId = tour.tourId)" +
                        " INNER JOIN tm.employee employee ON (tour.planOwnerId = employee.employeeId)" +
                        " INNER JOIN tm.traveller traveller ON (tourtraveller.travellerId = traveller.travellerId)" +
                        " INNER JOIN tm.profession profession ON (traveller.professionId = profession.professionId)"
        );

        testQueryToSQL("{\"text\": \"traveller profession\"}", "SELECT traveller.name, traveller.gender, traveller.age, profession.name" +
                        " FROM tm.traveller traveller" +
                        " INNER JOIN tm.profession profession ON (traveller.professionId = profession.professionId)"
        );

        testQueryToSQL("{\"text\": \"location\"}", "SELECT location.name FROM tm.location location");
        testQueryToSQL("{\"text\": \"location\", \"something\": \"else\"}", "SELECT location.name FROM tm.location location");
        testQueryToSQL("{\"text\": \"tell me name\" }", "SELECT traveller.name FROM tm.traveller traveller");
        //testQueryToSQL("{\"text\": \"what is the tour schedule\" }", "SELECT tour.startTime, tour.endTime, tour.costPerPerson FROM tm.tour tour");
        testQueryToSQL("{\"text\": \"what is the tour cost\" }", "SELECT tour.costPerPerson FROM tm.tour tour");
        testQueryToSQL("{\"text\": \"what is the cost\" }", "SELECT tour.costPerPerson FROM tm.tour tour");
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
        Assert.assertEquals(expectedSQL, response.getString("sql").trim());
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
