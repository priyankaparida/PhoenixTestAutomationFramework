package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CountAPITest {

	@Test
	public void countAPIResponse() throws IOException {
		
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.header("Authorization",getToken(FD))
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(200)
		.and()
		.time(lessThan(1000L))
		.body("message",equalTo("Success"))
		.body("data", notNullValue())
		.body("data.size()", equalTo(3))
		.body("data.count", everyItem(greaterThanOrEqualTo(0)))
		.body("data.label", everyItem(not(blankOrNullString())))
		.body("data.key", containsInAnyOrder("created_today","pending_fst_assignment","pending_for_delivery"))
		.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));
	}
	
	@Test
	public void countAPITest_MissingAuthToken() throws IOException {
		
		given()
		.baseUri(getProperty("BASE_URI"))
		.log().uri()
		.log().method()
		.log().headers()
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(401);
		
	}
}
