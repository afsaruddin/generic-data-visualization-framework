package com.wsdhaka.gdvf;

import com.wsdhaka.gdvf.query2sql.Query2SQLHandler;
import com.wsdhaka.gdvf.query2sql.ResultHandler;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        CORSFilter.apply();

        Spark.post("/querytosql", "application/json", (req, res) -> new Query2SQLHandler().doGet(req, res), new JsonTransformer());
        Spark.get("/submitquery", "application/json", (req, res) -> new ResultHandler().getDummyDataForResult(req, res));
    }
}
