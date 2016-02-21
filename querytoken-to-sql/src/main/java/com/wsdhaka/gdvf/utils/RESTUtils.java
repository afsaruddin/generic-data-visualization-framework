package com.wsdhaka.gdvf.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RESTUtils {
    public static String doGet(String theUrl) throws IOException {
        HttpGet aHttpGetReq = new HttpGet(theUrl);

        try (CloseableHttpClient aHttpClient = HttpClients.createDefault()) {
            return getResponse(aHttpClient.execute(aHttpGetReq));
        }
    }

    public static String doPost(String theUrl, String theRequestBody) throws IOException {
        StringEntity anEntity = new StringEntity(theRequestBody);
        anEntity.setContentType("application/json;charset=UTF-8");

        HttpPost aHttpPostReq = new HttpPost(theUrl);
        aHttpPostReq.setEntity(anEntity);

        try (CloseableHttpClient aHttpClient = HttpClients.createDefault()) {
            return getResponse(aHttpClient.execute(aHttpPostReq));
        }
    }

    public static HttpResponse doOptions(String theUrl) throws IOException {
        HttpOptions aHttpOptionsReq = new HttpOptions(theUrl);

        try (CloseableHttpClient aHttpClient = HttpClients.createDefault()) {
            HttpResponse aHttpResponse = aHttpClient.execute(aHttpOptionsReq);
            if (aHttpResponse.getStatusLine().getStatusCode() != 200) {
                throw new IOException(aHttpResponse.getStatusLine().toString());
            }

            return aHttpResponse;
        }
    }

    private static String getResponse(HttpResponse aHttpResponse) throws IOException {
        HttpEntity aHttpResponseEntity = aHttpResponse.getEntity();
        String aStrResponse = aHttpResponseEntity != null ? EntityUtils.toString(aHttpResponseEntity) : null;

        if (aHttpResponse.getStatusLine().getStatusCode() != 200) {
            throw new IOException("Status: " + aHttpResponse.getStatusLine().toString() + ". Request body: " + aStrResponse);
        }
        return aStrResponse;
    }

    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
