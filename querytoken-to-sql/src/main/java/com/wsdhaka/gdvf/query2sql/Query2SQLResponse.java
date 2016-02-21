package com.wsdhaka.gdvf.query2sql;

import java.util.List;

public class Query2SQLResponse {
    private List<String> columnNames;
    private String sql;

    public Query2SQLResponse(List<String> columnNames, String sql) {
        setColumnNames(columnNames);
        setSql(sql);
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
