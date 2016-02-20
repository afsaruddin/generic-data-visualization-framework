package com.wsdhaka.gdvf;

import spark.Spark;

public class Main {
    public static void main(String[] args) {
        Spark.get("/hello", (req, res) -> "Hello World");
    }
}
