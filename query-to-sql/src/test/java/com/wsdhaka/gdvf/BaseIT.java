package com.wsdhaka.gdvf;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Before;

import java.io.IOException;
import java.util.TimeZone;

public class BaseIT {
    protected static final String HTTP_QUERY_TO_SQL_HOST = "http://localhost:4567";

    @Before
    protected void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    protected String doGet(String theUrl) throws IOException {
        HttpGet aHttpGetReq = new HttpGet(theUrl);

        try (CloseableHttpClient aHttpClient = HttpClients.createDefault()) {
            HttpResponse aHttpResponse = aHttpClient.execute(aHttpGetReq);

            // We need the "200 OK".
            if (aHttpResponse.getStatusLine().getStatusCode() != 200) {
                throw new IOException(aHttpResponse.getStatusLine().toString());
            }

            return EntityUtils.toString(aHttpResponse.getEntity());
        }
    }

    protected String doPost(String theUrl, String theRequestBody) throws IOException {
        StringEntity anEntity = new StringEntity(theRequestBody);
        anEntity.setContentType("application/json;charset=UTF-8");

        HttpPost aHttpPostReq = new HttpPost(theUrl);
        aHttpPostReq.setEntity(anEntity);

        try (CloseableHttpClient aHttpClient = HttpClients.createDefault()) {
            HttpResponse aHttpResponse = aHttpClient.execute(aHttpPostReq);
            if (aHttpResponse.getStatusLine().getStatusCode() != 200) {
                throw new IOException(aHttpResponse.getStatusLine().toString());
            }

            return EntityUtils.toString(aHttpResponse.getEntity());
        }
    }
}
