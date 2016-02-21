package com.wsdhaka.gdvf.query2sql.soa.querytokenizer;

import java.util.List;

public class QueryTokenizerResponse {
    private String query;

    private List<String> select;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getSelect() {
        return select;
    }

    public void setSelect(List<String> select) {
        this.select = select;
    }
}
