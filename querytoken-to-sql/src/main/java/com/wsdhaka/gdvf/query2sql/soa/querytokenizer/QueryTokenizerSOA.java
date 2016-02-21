package com.wsdhaka.gdvf.query2sql.soa.querytokenizer;

import com.wsdhaka.gdvf.utils.JSONUtils;
import com.wsdhaka.gdvf.utils.RESTUtils;

import java.io.IOException;

public class QueryTokenizerSOA {
    //private static final String TOKENIZER_SERVICE_URL = "http://192.168.1.50:9091/querytotoken";
    private static final String TOKENIZER_SERVICE_URL = "http://169.44.61.115:9091/querytotoken";

    private static QueryTokenizerSOA instance = new QueryTokenizerSOA();

    private QueryTokenizerSOA() {
    }

    public static QueryTokenizerSOA getInstance() {
        return instance;
    }

    public QueryTokenizerResponse tokenizeQuery(String q) throws IOException {
        try {
            return JSONUtils.fromJson(RESTUtils.doGet(TOKENIZER_SERVICE_URL + "?q=" + q), QueryTokenizerResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Unable to tokenize the query");
        }
    }
}
