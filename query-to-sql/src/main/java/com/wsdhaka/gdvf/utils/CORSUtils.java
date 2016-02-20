package com.wsdhaka.gdvf.utils;

import spark.Response;

public class CORSUtils {
    public static void enableCORS(Response response) {
        response.header("Access-Control-Allow-Origin", "*");
        response.header("Access-Control-Request-Method", "*");
        response.header("Access-Control-Allow-Headers", "*");
    }
}
