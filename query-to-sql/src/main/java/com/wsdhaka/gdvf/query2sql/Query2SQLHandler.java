package com.wsdhaka.gdvf.query2sql;

import spark.Request;
import spark.Response;

public class Query2SQLHandler {
    public Query2SQLResponse doGet(Request req, Response res) {
        return new Query2SQLResponse("SELECT name FROM profession");
    }
}
