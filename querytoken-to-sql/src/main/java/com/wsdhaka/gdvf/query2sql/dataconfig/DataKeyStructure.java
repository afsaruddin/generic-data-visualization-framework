package com.wsdhaka.gdvf.query2sql.dataconfig;

public class DataKeyStructure {
    private String keyAlias;
    private String keyActual;

    public DataKeyStructure(String keyAlias, String keyActual) {
        setKeyAlias(keyAlias);
        setKeyActual(keyActual);
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyActual() {
        return keyActual;
    }

    public void setKeyActual(String keyActual) {
        this.keyActual = keyActual;
    }
}
