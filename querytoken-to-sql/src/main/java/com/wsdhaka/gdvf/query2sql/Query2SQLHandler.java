package com.wsdhaka.gdvf.query2sql;

import com.wsdhaka.gdvf.query2sql.dataconfig.DataConfigHandler;
import com.wsdhaka.gdvf.query2sql.dataconfig.DataEntityRelation;
import com.wsdhaka.gdvf.query2sql.dataconfig.DataEntityStructure;
import com.wsdhaka.gdvf.query2sql.dataconfig.DataKeyStructure;
import com.wsdhaka.gdvf.query2sql.soa.querytokenizer.QueryTokenizerResponse;
import com.wsdhaka.gdvf.query2sql.soa.querytokenizer.QueryTokenizerSOA;
import com.wsdhaka.gdvf.utils.JSONUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Query2SQLHandler {

    public static final Query2SQLResponse EMPTY_RESPONSE = new Query2SQLResponse("");

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

        // Filter out all unknown tokens and add the meaningFulTokens.
        List<String> meaningFulTokens = new ArrayList<>();
        token.getSelect()
                .stream()
                .map((e) -> e.toLowerCase())
                .forEach((e) -> meaningFulTokens.addAll(DataConfigHandler.getInstance().getAliases(e)));

        // Split the list into (a) keys and (b) entity names
        Set<String> userSelectedKeys = meaningFulTokens
                .stream()
                .filter((e) -> DataConfigHandler.getInstance().isKey(e))
                .collect(Collectors.toSet());

        Set<String> userSelectedEntities = meaningFulTokens
                .stream()
                .filter((e) -> DataConfigHandler.getInstance().isEntity(e))
                .collect(Collectors.toSet());

        // Find the entities (the bazar) where we can get the result for user's keys.
        Set<String> entitiesStore = userSelectedKeys
                .stream()
                .filter((e) -> DataConfigHandler.getInstance().getStoreEntity(e) != null)
                .map((e) -> DataConfigHandler.getInstance().getStoreEntity(e))
                .collect(Collectors.toSet());

        // Add user's entities (if existent).
        entitiesStore.addAll(userSelectedEntities
                        .stream()
                        .filter((e) -> DataConfigHandler.getInstance().getStoreEntity(e) != null)
                        .map((e) -> DataConfigHandler.getInstance().getStoreEntity(e))
                        .collect(Collectors.toSet())
        );

        DataEntityRelation relationToSearchIn = getRelationToSearchIn(entitiesStore, meaningFulTokens);

        // Entities of the selected relationship.
        List<String> candidateEntities = new ArrayList<>();
        DataConfigHandler.getInstance().findEntitiesOfRelation(candidateEntities, relationToSearchIn);

        // A relationship has many entites. Of them, find which ones we were looking.
        List<DataEntityStructure> entityDetailsToSelect = new ArrayList<>();
        candidateEntities.forEach((e) -> {
            DataEntityStructure entityDetails = DataConfigHandler.getInstance().getEntityDetails(e);
            if (entityDetails == null) {
                return;
            }

            List<DataKeyStructure> intersectedKeys = entityDetails.getKeys()
                    .stream()
                    .filter((k) -> userSelectedKeys.contains(k.getKeyAlias())).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(intersectedKeys)) {
                entityDetailsToSelect.add(new DataEntityStructure(
                        entityDetails.getEntityNameAlias(), entityDetails.getEntityNameActual(), intersectedKeys
                ));
            } else {
                if (userSelectedEntities.contains(entityDetails.getEntityNameAlias())) {
                    entityDetailsToSelect.add(entityDetails);
                }
            }
        });

        if (CollectionUtils.isEmpty(entityDetailsToSelect)) {
            // This will be logical bug.
            return EMPTY_RESPONSE;
        }

        String selectSpec = getSelectSpec(entityDetailsToSelect);
        if (StringUtils.isEmpty(selectSpec)) {
            return EMPTY_RESPONSE;
        }

        return new Query2SQLResponse(
                " SELECT " + selectSpec + " FROM " + relationToSearchIn.getFromSpec() + " LIMIT 20"
        );
    }

    private String getSelectSpec(List<DataEntityStructure> entityDetailsToSelect) {
        return entityDetailsToSelect
                .stream()
                .map((e) -> e.getKeys().stream().map((k) -> getColumnSelect(e, k)).collect(Collectors.joining(", ")))
                .filter((e) -> StringUtils.isNotEmpty(e))
                .collect(Collectors.joining(", "));
    }

    private String getColumnSelect(DataEntityStructure e, DataKeyStructure k) {
        return e.getEntityNameActual() + "." + k.getKeyActual();
    }

    private DataEntityRelation getRelationToSearchIn(Collection<String> entitiesStore, Collection<String> allTokenAlias) {
        return DataConfigHandler.getInstance().getEntityRelationships()
                .stream()
                .filter((r) -> DataConfigHandler.getInstance().canUseRelation(r.getName(), allTokenAlias))
                .map((r) -> getRelationshipMatchCount(r, entitiesStore))
                .max((c1, c2) -> c1.matchedNodeCount == c2.matchedNodeCount ? c2.totalNodeCount - c1.totalNodeCount : c1.matchedNodeCount - c2.matchedNodeCount)
                .get()
                .relation;
    }

    private RelationshipMatchCount getRelationshipMatchCount(DataEntityRelation relation, Collection<String> entitiesStore) {
        RelationshipMatchCount count = new RelationshipMatchCount(relation);
        entitiesStore.forEach((e) -> {
            if (DataConfigHandler.getInstance().isEntityExistsInRelation(e, relation)) {
                count.incMatchedNodeCount();
            }
        });
        return count;
    }

    private class RelationshipMatchCount {
        private DataEntityRelation relation;
        private int matchedNodeCount;
        private int totalNodeCount;

        public RelationshipMatchCount(DataEntityRelation relation) {
            this.relation = relation;
            this.totalNodeCount = relation.getNodeCount();
            this.matchedNodeCount = 0;
        }

        public void incMatchedNodeCount() {
            this.matchedNodeCount++;
        }
    }
}
