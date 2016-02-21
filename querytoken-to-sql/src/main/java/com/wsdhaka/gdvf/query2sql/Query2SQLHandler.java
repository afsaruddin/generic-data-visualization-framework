package com.wsdhaka.gdvf.query2sql;

import com.wsdhaka.gdvf.query2sql.soa.querytokenizer.QueryTokenizerResponse;
import com.wsdhaka.gdvf.query2sql.soa.querytokenizer.QueryTokenizerSOA;
import com.wsdhaka.gdvf.utils.JSONUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Query2SQLHandler {

    public static final Query2SQLResponse EMPTY_RESPONSE = new Query2SQLResponse(Collections.emptyList(), "");

    public Query2SQLResponse doGet(Request req, Response res) throws IOException {
        return convert(JSONUtils.fromJson(req.body(), Query2SQLRequest.class));
    }

    protected Query2SQLResponse convert(Query2SQLRequest q) throws IOException {
        if (q == null || StringUtils.isEmpty(q.getText())) {
            throw new IllegalArgumentException("API request structure violated. Please contact the API.");
        }

        QueryTokenizerResponse token = QueryTokenizerSOA.getInstance().tokenizeQuery(q.getText());
        if (token == null || CollectionUtils.isEmpty(token.getSelect())) {
            return EMPTY_RESPONSE;
        }

        // Split the list into (a) columns names and (b) table names
        Set<String> selectItemsForColumns = token.getSelect()
                .stream()
                .filter((e) -> Query2SQLTokenHandler.getInstance().getTableNames(e) != null)
                .collect(Collectors.toSet());

        Set<String> selectItemsForTable = token.getSelect()
                .stream()
                .filter((e) -> Query2SQLTokenHandler.getInstance().getColumnNames(e) != null)
                .collect(Collectors.toSet());

        String aTableName = determineTableNameToSelect(selectItemsForColumns, selectItemsForTable);
        if (StringUtils.isEmpty(aTableName)) {
            return EMPTY_RESPONSE;
        }

        List<String> selectingColumnNames = determineColumnsNamesToSelect(selectItemsForColumns, aTableName);

        return new Query2SQLResponse(
                selectingColumnNames,
                "SELECT " + StringUtils.join(selectingColumnNames, ", ") + " FROM tm." + aTableName
        );
    }

    private String determineTableNameToSelect(Set<String> selectItemsForColumns, Set<String> selectItemsForTable) {
        // Gather all table names for the searching column names.
        Set<String> lookupTableNames = new HashSet();
        selectItemsForColumns.forEach((e) -> lookupTableNames.addAll(Query2SQLTokenHandler.getInstance().getTableNames(e)));

        // Find those table names that we can find (from column name) and that user specified.
        if (CollectionUtils.isEmpty(selectItemsForTable)) {
            if (CollectionUtils.isNotEmpty(lookupTableNames)) {
                return lookupTableNames.iterator().next();
            }
        } else {
            if (CollectionUtils.isNotEmpty(lookupTableNames)) {
                List<String> intersectedTables = selectItemsForTable.stream().filter(lookupTableNames::contains).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(intersectedTables)) {
                    return intersectedTables.get(0);
                }
            } else {
                return selectItemsForTable.iterator().next();
            }
        }

        return null;
    }

    private List<String> determineColumnsNamesToSelect(Set<String> selectItemsForColumns, String aTableName) {
        List<String> allColumnNames = Query2SQLTokenHandler.getInstance().getColumnNames(aTableName);
        if (CollectionUtils.isEmpty(selectItemsForColumns)) {
            return allColumnNames;
        }

        return allColumnNames
                .stream()
                .filter(selectItemsForColumns::contains)
                .collect(Collectors.toList());
    }
}
