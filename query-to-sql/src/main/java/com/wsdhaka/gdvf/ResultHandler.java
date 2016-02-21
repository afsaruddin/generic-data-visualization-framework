package com.wsdhaka.gdvf;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

@Deprecated
public class ResultHandler {
    public String getDummyDataForResult(Request req, Response res) {
        String q = req.params("q");
        if (StringUtils.isNotEmpty(q)) {
            new JSONObject(q).getJSONArray("select");
        }
        //new Query2SQLHandler().;

        return "[" +
                "{\"age\": 20, \"population\": 7040659}," +
                "{\"age\": 25, \"population\": 7704659 }," +
                "{\"age\": 30, \"population\": 27046599 }," +
                "{\"age\": 35, \"population\": 2804659 }," +
                "{\"age\": 40, \"population\" : 2704659 }" +
                "]";
    }
}
