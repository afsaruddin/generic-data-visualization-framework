package com.wsdhaka.gdvf.query2sql.dataconfig;

import com.wsdhaka.gdvf.utils.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class DataConfigHandler {
    private static DataConfigHandler instance = new DataConfigHandler();

    private DataConfigHandler() {
    }

    @Deprecated /* Should be in tokenizer side */
    private Map<String, List<String>> tokenAliases = new HashMap<>(); /* "token" => [ "alias1", "alias2" ] */

    private Map<String, String> tokenStoreEntity = new HashMap<>(); /* "token" => "entity-name" */

    /**
     * Entity structure contains alias and actual name. In all places (where we refer, compare etc),
     * we will use the alias.
     */
    private Map<String, DataEntityStructure> entityDetails = new HashMap<>(); /* "entity-name" => [ "key1", "key2", ... ] */

    private List<DataEntityRelation> entityRelationships = new ArrayList<>(); /* Graph of all JOIN-ed relationships */

    public static DataConfigHandler getInstance() {
        return instance;
    }

    public void loadConfig() {
        loadDummyData(); // TODO: Hard-coded now.
    }

    @Deprecated /* Should be in tokenizer side */
    public List<String> getAliases(String token) {
        return tokenAliases.containsKey(token) ? tokenAliases.get(token) : Arrays.asList(token);
    }

    public boolean isKey(String token) {
        return entityDetails.values()
                .stream()
                .anyMatch((e) -> e.getKeys().stream().anyMatch(
                        (k) -> StringUtils.equals(k.getKeyAlias(), token)
                ));
    }

    public boolean isEntity(String token) {
        return entityDetails.keySet().contains(token);
    }

    public String getStoreEntity(String token) {
        return tokenStoreEntity.get(token);
    }

    public DataEntityStructure getEntityDetails(String entityAlias) {
        return entityDetails.containsKey(entityAlias) ? entityDetails.get(entityAlias) : null;
    }

    public List<DataEntityRelation> getEntityRelationships() {
        return entityRelationships;
    }

    public boolean isEntityExistsInRelation(String entityAlias, DataEntityRelation relation) {
        if (StringUtils.equals(entityAlias, relation.getName())) {
            return true;
        }

        if (relation instanceof DataEntityRelationNode) {
            for (DataEntityRelation containedRelation : ((DataEntityRelationNode) relation).getContainedChildren()) {
                if (isEntityExistsInRelation(entityAlias, containedRelation)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void findEntitiesOfRelation(List<String> entityAliases, DataEntityRelation relation) {
        entityAliases.add(relation.getName());

        if (relation instanceof DataEntityRelationNode) {
            for (DataEntityRelation containedRelation : ((DataEntityRelationNode) relation).getContainedChildren()) {
                findEntitiesOfRelation(entityAliases, containedRelation);
            }
        }
    }

    /*************************************************************************/

    @Deprecated
    private void loadDummyData() {
        tokenAliases = MapUtils.getStaticMap(
                MapUtils.entry("cost", Arrays.asList("costperperson")),
                MapUtils.entry("schedule", Arrays.asList("starttime", "endtime")),
                MapUtils.entry("start", Arrays.asList("starttime")),
                MapUtils.entry("end", Arrays.asList("endtime")),
                MapUtils.entry("person", Arrays.asList("traveller")),
                MapUtils.entry("human", Arrays.asList("traveller")),
                MapUtils.entry("people", Arrays.asList("traveller")),
                MapUtils.entry("comment", Arrays.asList("feedback")),
                MapUtils.entry("path", Arrays.asList("tourpath")),
                MapUtils.entry("traveller", Arrays.asList("traveller", "tourtraveller")),
                MapUtils.entry("feedback", Arrays.asList("travellerfeedback"))
        );

        tokenStoreEntity = MapUtils.getStaticMap(
                MapUtils.entry("name", "traveller"),
                MapUtils.entry("gender", "traveller"),
                MapUtils.entry("age", "traveller"),
                MapUtils.entry("starttime", "tour"),
                MapUtils.entry("endtime", "tour"),
                MapUtils.entry("costperperson", "tour"),
                MapUtils.entry("feedback", "travellerfeedback"),
                MapUtils.entry("profession", "profession"),
                MapUtils.entry("location", "location"),
                MapUtils.entry("traveller", "traveller"),
                MapUtils.entry("employee", "employee"),
                MapUtils.entry("tour", "tour"),
                MapUtils.entry("tourpath", "tourpath"),
                MapUtils.entry("tourtraveller", "tourtraveller"),
                MapUtils.entry("travellerfeedback", "travellerfeedback")
        );

        entityDetails = MapUtils.getStaticMap(
                MapUtils.entry("profession", new DataEntityStructure("profession", "profession", Arrays.asList(
                        new DataKeyStructure("name", "name")
                ))),

                MapUtils.entry("location", new DataEntityStructure("location", "location", Arrays.asList(
                        new DataKeyStructure("name", "name")
                ))),

                MapUtils.entry("traveller", new DataEntityStructure("traveller", "traveller", Arrays.asList(
                        new DataKeyStructure("name", "name"), new DataKeyStructure("gender", "gender"), new DataKeyStructure("age", "age")
                ))),

                MapUtils.entry("employee", new DataEntityStructure("employee", "employee", Arrays.asList(
                        new DataKeyStructure("name", "name"), new DataKeyStructure("gender", "gender"), new DataKeyStructure("age", "age")
                ))),

                MapUtils.entry("tour", new DataEntityStructure("tour", "tour", Arrays.asList(
                        new DataKeyStructure("starttime", "startTime"), new DataKeyStructure("endtime", "endTime"), new DataKeyStructure("costperperson", "costPerPerson")
                ))),

                MapUtils.entry("tourpath", new DataEntityStructure("tourpath", "tourpath", Arrays.asList(
                        new DataKeyStructure("starttime", "startTime"), new DataKeyStructure("endtime", "endTime")
                ))),

                MapUtils.entry("travellerfeedback", new DataEntityStructure("travellerfeedback", "travellerfeedback", Arrays.asList(
                        new DataKeyStructure("feedback", "feedback")
                )))
        );

        entityRelationships = Arrays.asList(
                getSingleRelationship("profession"),
                getSingleRelationship("location"),
                getSingleRelationship("traveller"),
                getSingleRelationship("employee"),
                getSingleRelationship("tour"),
                getSingleRelationship("tourpath"),
                getSingleRelationship("tourtraveller"),
                getSingleRelationship("travellerfeedback"),
                getTourRelation(),
                getTravellerRelation(),
                getTourPathRelationship(),
                getTourTravellerRelationship(),
                getTravellerFeedbackRelationship()
        );
    }

    private DataEntityRelation getSingleRelationship(String entityAlias) {
        return new DataEntityRelation(entityAlias, "tm." + entityAlias + " " + entityAlias);
    }

    private DataEntityRelation getTourRelation() {
        DataEntityRelationNode tourRelation = new DataEntityRelationNode(
                "tour", "tm.tour tour" +
                " INNER JOIN tm.employee employee ON (tour.planOwnerId = employee.employeeId)"
        );
        tourRelation.addContainedChildren(getSingleRelationship("employee"));
        return tourRelation;
    }

    private DataEntityRelation getTravellerRelation() {
        DataEntityRelationNode travellerRelation = new DataEntityRelationNode(
                "traveller", "tm.traveller traveller" +
                " INNER JOIN tm.profession profession ON (traveller.professionId = profession.professionId)"
        );
        travellerRelation.addContainedChildren(getSingleRelationship("profession"));
        return travellerRelation;
    }

    private DataEntityRelation getTourPathRelationship() {
        DataEntityRelationNode rootRelation = new DataEntityRelationNode(
                "tourpath", "tm.tourpath tourpath" +
                " INNER JOIN tm.tour tour ON (tourpath.tourId = tour.tourId)" +
                " INNER JOIN tm.employee employee ON (tour.planOwnerId = employee.employeeId)" +
                " INNER JOIN tm.location location ON (tourpath.locationId = location.locationId)"
        );
        rootRelation.addContainedChildren(getTourRelation());
        rootRelation.addContainedChildren(getSingleRelationship("location"));

        return rootRelation;
    }

    private DataEntityRelation getTourTravellerRelationship() {
        DataEntityRelationNode rootRelation = new DataEntityRelationNode(
                "tourtraveller", "tm.tourtraveller tourtraveller" +
                " INNER JOIN tm.tour tour ON (tourtraveller.tourId = tour.tourId)" +
                " INNER JOIN tm.employee employee ON (tour.planOwnerId = employee.employeeId)" +
                " INNER JOIN tm.traveller traveller ON (tourtraveller.travellerId = traveller.travellerId)" +
                " INNER JOIN tm.profession profession ON (traveller.professionId = profession.professionId)"
        );
        rootRelation.addContainedChildren(getTourRelation());
        rootRelation.addContainedChildren(getTravellerRelation());

        return rootRelation;
    }

    private DataEntityRelation getTravellerFeedbackRelationship() {
        DataEntityRelationNode rootRelation = new DataEntityRelationNode(
                "travellerfeedback", "tm.travellerfeedback travellerfeedback" +
                " INNER JOIN tm.tour tour ON (travellerfeedback.tourId = tour.tourId)" +
                " INNER JOIN tm.employee employee ON (tour.planOwnerId = employee.employeeId)" +
                " INNER JOIN tm.traveller traveller ON (travellerfeedback.travellerId = traveller.travellerId)" +
                " INNER JOIN tm.profession profession ON (traveller.professionId = profession.professionId)");
        rootRelation.addContainedChildren(getTourRelation());
        rootRelation.addContainedChildren(getTravellerRelation());

        return rootRelation;
    }

    /*************************************************************************/
}
