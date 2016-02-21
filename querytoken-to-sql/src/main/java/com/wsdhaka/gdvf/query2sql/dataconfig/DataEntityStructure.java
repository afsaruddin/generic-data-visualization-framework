package com.wsdhaka.gdvf.query2sql.dataconfig;

import java.util.ArrayList;
import java.util.List;

public class DataEntityStructure {
    private String entityNameAlias;
    private String entityNameActual;
    private List<DataKeyStructure> keys = new ArrayList();

    public DataEntityStructure(String entityNameAlias, String entityNameActual, List<DataKeyStructure> keys) {
        setEntityNameAlias(entityNameAlias);
        setEntityNameActual(entityNameActual);
        setKeys(keys);
    }

    public String getEntityNameAlias() {
        return entityNameAlias;
    }

    public void setEntityNameAlias(String entityNameAlias) {
        this.entityNameAlias = entityNameAlias;
    }

    public String getEntityNameActual() {
        return entityNameActual;
    }

    public void setEntityNameActual(String entityNameActual) {
        this.entityNameActual = entityNameActual;
    }

    public List<DataKeyStructure> getKeys() {
        return keys;
    }

    public void setKeys(List<DataKeyStructure> keys) {
        this.keys = keys;
    }
}
