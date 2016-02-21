package com.wsdhaka.gdvf.requestfilters;

import com.wsdhaka.gdvf.utils.JSONUtils;
import spark.ResponseTransformer;

public class JsonResponseTransformer implements ResponseTransformer {
    public String render(Object model) throws Exception {
        String responseJson = JSONUtils.toJson(model);
        System.out.println("Response:\n" + responseJson);
        return responseJson;
    }
}
