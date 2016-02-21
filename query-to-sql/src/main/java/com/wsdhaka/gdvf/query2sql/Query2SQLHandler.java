package com.wsdhaka.gdvf.query2sql;

import com.wsdhaka.gdvf.utils.JSONUtils;
import spark.Request;
import spark.Response;

public class Query2SQLHandler {
    public Query2SQLResponse doGet(Request req, Response res) {
        return convert(JSONUtils.fromJson(req.body(), Query2SQLRequest.class));
    }

    protected Query2SQLResponse convert(Query2SQLRequest q) {
        if (q == null) {
            throw new IllegalArgumentException("API request structure violated. Please contact the API.");
        }

        return new Query2SQLResponse("SELECT name FROM profession");
    }
}
