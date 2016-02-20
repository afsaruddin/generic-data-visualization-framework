package com.wsdhaka.gdvf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public String render(Object model) throws Exception {
        return mapper.writeValueAsString(model);
    }
}
