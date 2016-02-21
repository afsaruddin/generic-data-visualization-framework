package com.wsdhaka.gdvf.query2sql.dataconfig;

public class DataEntityRelation {
    private String name;
    private String fromSpec;

    public DataEntityRelation(String name, String fromSpec) {
        setName(name);
        setFromSpec(fromSpec);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromSpec() {
        return fromSpec;
    }

    public void setFromSpec(String fromSpec) {
        this.fromSpec = fromSpec;
    }

    public int getNodeCount() {
        return 1;
    }
}
