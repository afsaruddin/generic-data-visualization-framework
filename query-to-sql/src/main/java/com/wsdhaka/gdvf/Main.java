package com.wsdhaka.gdvf;

import com.wsdhaka.gdvf.query2sql.Query2SQLHandler;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        Spark.get("/querytosql", "application/json", (req, res) -> new Query2SQLHandler().doGet(req, res), new JsonTransformer());
    }
}
