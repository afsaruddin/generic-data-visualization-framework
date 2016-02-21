package com.wsdhaka.gdvf;

import spark.Spark;

public final class RequestDebugFilter {
    public final static void apply() {
        Spark.before((request, response) -> {
            System.out.println("Request Parameters: " + request.params());
            System.out.println("Request Body:\n" + request.body());
        });
    }
}