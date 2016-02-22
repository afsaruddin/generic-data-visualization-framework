package com.wsdhaka.gdvf.query2sql.dataconfig;

import java.util.ArrayList;
import java.util.List;

public class DataEntityRelationNode extends DataEntityRelation {
    private List<DataEntityRelation> containedChildren = new ArrayList<>();

    public DataEntityRelationNode(String name, String fromSpec) {
        super(name, fromSpec);
    }

    public List<DataEntityRelation> getContainedChildren() {
        return containedChildren;
    }

    public void addContainedChildren(DataEntityRelation containedChildren) {
        this.containedChildren.add(containedChildren);
    }

    @Override
    public int getNodeCount() {
        return 1 + getContainedChildren().stream().mapToInt(DataEntityRelation::getNodeCount).sum();
    }
}
