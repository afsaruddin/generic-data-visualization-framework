package com.wsdhaka.gdvf.query2sql;

import com.wsdhaka.gdvf.utils.MapUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Query2SQLTokenHandler {
    private static Map<String, List<String>> tableTokens = MapUtils.getStaticMap(
            MapUtils.entry("profession", Arrays.asList("name")),
            MapUtils.entry("location", Arrays.asList("name")),
            MapUtils.entry("traveller", Arrays.asList("name", "gender", "age")),
            MapUtils.entry("employee", Arrays.asList("name", "gender", "age")),
            MapUtils.entry("tour", Arrays.asList("startTime", "endTime", "costPerPerson")),
            MapUtils.entry("tourpath", Arrays.asList("startTime", "endTime")),
            MapUtils.entry("travellerfeedback", Arrays.asList("feedback"))
    );

    private static Map<String, List<String>> columnTokens = MapUtils.getStaticMap(
            MapUtils.entry("name", Arrays.asList("profession", "location", "traveller", "employee")),
            MapUtils.entry("gender", Arrays.asList("traveller", "employee")),
            MapUtils.entry("age", Arrays.asList("traveller", "employee")),
            MapUtils.entry("startTime", Arrays.asList("tour", "tourpath")),
            MapUtils.entry("endTime", Arrays.asList("tour", "tourpath")),
            MapUtils.entry("costPerPerson", Arrays.asList("tour")),
            MapUtils.entry("feedback", Arrays.asList("travellerfeedback"))
    );

    private static Query2SQLTokenHandler instance = new Query2SQLTokenHandler();

    private Query2SQLTokenHandler() {
    }

    public static Query2SQLTokenHandler getInstance() {
        return instance;
    }

    public List<String> getColumnNames(String tableName) {
        return tableTokens.get(tableName);
    }

    public List<String> getTableNames(String columnName) {
        return columnTokens.get(columnName);
    }
}
