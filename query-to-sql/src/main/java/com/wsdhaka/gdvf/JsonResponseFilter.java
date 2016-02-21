package com.wsdhaka.gdvf;

import spark.Spark;

public final class JsonResponseFilter {
    public final static void apply() {
        Spark.after((request, response) -> response.type("application/json"));
    }
}