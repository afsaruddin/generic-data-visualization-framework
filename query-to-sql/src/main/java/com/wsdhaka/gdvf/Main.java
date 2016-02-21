package com.wsdhaka.gdvf;

import com.wsdhaka.gdvf.query2sql.Query2SQLHandler;
import org.apache.http.HttpStatus;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        CORSFilter.apply();
        JsonResponseFilter.apply();

        Spark.post("/querytosql", "application/json", (req, res) -> new Query2SQLHandler().doGet(req, res), new JsonResponseTransformer());
        Spark.post("/submitquery", "application/json", (req, res) -> new ResultHandler().getDummyDataForResult(req, res));

        Spark.exception(IllegalArgumentException.class, (e, request, response) -> {
            response.status(HttpStatus.SC_BAD_REQUEST);
            response.body(new ErrorResponse(ErrorResponse.ERROR_CODE_API_SPEC_VIOLATION, e.getMessage()).toJson());
        });
    }
}
