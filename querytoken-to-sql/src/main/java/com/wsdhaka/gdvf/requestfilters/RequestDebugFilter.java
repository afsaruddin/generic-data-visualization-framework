package com.wsdhaka.gdvf.requestfilters;

import spark.Spark;

public final class RequestDebugFilter {
    public final static void apply() {
        Spark.before((request, response) -> {
            System.out.println("Request: " + request.url() + "?" + request.params());

            if ("POST".equalsIgnoreCase(request.requestMethod())) {
                System.out.println("\n" + request.body());
            }
        });
    }
}