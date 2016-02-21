package com.wsdhaka.gdvf.requestfilters;

import spark.Spark;

public final class RequestDebugFilter {
    public final static void apply() {
        Spark.before((request, response) -> {
            System.out.println("Request: " + request.url() + ". Params: " + request.params());

            if ("POST".equalsIgnoreCase(request.requestMethod())) {
                System.out.println(request.body());
            }
        });
    }
}