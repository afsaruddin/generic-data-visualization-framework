package com.wsdhaka.gdvf;

import com.wsdhaka.gdvf.utils.JSONUtils;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {
    public String render(Object model) throws Exception {
        return JSONUtils.toJson(model);
    }
}
