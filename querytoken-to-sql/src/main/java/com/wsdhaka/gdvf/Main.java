package com.wsdhaka.gdvf;

import com.wsdhaka.gdvf.query2sql.Query2SQLHandler;
import com.wsdhaka.gdvf.query2sql.dataconfig.DataConfigHandler;
import com.wsdhaka.gdvf.requestfilters.CORSFilter;
import com.wsdhaka.gdvf.requestfilters.JsonResponseFilter;
import com.wsdhaka.gdvf.requestfilters.JsonResponseTransformer;
import com.wsdhaka.gdvf.requestfilters.RequestDebugFilter;
import org.apache.http.HttpStatus;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        RequestDebugFilter.apply();
        CORSFilter.apply();
        JsonResponseFilter.apply();

        init();

        Spark.post("/querytosql", "application/json", (req, res) -> new Query2SQLHandler().doGet(req, res), new JsonResponseTransformer());

        Spark.exception(IllegalArgumentException.class, (e, request, response) -> {
            response.status(HttpStatus.SC_BAD_REQUEST);
            response.body(new ErrorResponse(ErrorResponse.ERROR_CODE_API_SPEC_VIOLATION, e.getMessage()).toJson());
        });
    }

    private static void init() {
        DataConfigHandler.getInstance().loadConfig();
    }
}
