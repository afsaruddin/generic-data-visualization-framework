package com.wsdhaka.gdvf;

import com.wsdhaka.gdvf.query2sql.Query2SQLHandler;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        Spark.get("/querytosql", "application/json", (req, res) -> new Query2SQLHandler().doGet(req, res), new JsonTransformer());
        Spark.get("/submitquery", "application/json", (req, res) -> getDummyDataForResult());
    }

    private static String getDummyDataForResult() {
        return " [" +
                "{'age': 20, 'population': 7040659}," +
                "{'age': 25, 'population': 7704659 }," +
                "{'age': 30, 'population': 27046599 }," +
                "{'age': 35, 'population': 2804659 }," +
                "{'age': 40, 'population' : 2704659 }" +
                "]";
    }
}
