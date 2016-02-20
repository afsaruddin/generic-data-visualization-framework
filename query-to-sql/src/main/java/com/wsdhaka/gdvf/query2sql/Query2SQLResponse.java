package com.wsdhaka.gdvf.query2sql;

public class Query2SQLResponse {
    private String sql;

    public Query2SQLResponse(String sql) {
        setSql(sql);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
