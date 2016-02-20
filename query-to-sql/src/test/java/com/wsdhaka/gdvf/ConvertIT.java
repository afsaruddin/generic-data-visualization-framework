package com.wsdhaka.gdvf;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spark.Spark;

import java.io.IOException;

public class ConvertIT extends BaseIT {
	@Before
	public void setUp() {
		super.setUp();

		Main.main(null);
	}
	
	@After
	public void tearDown() {
		Spark.stop();
	}
	
	@Test
	public void testOk() throws IOException {
		System.out.println(doGet(HTTP_QUERY_TO_SQL_HOST + "/hello"));
	}
}
