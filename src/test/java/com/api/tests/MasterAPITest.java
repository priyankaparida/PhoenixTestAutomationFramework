package com.api.tests;

import static  org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constants.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class MasterAPITest {
	
	
	@Test
	public void masterAPITest() throws IOException {
		
		given()
		.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
		.post("master")//default content type application/url-formencoded
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.body("data", hasKey("mst_oem"))
		.body("data", hasKey("mst_model"))
		.body("$", hasKey("message"))
		.body("$", hasKey("data"))
		.body("data.mst_oem.size()", greaterThan(0))
		.body("data.mst_model.size()", greaterThan(0))
		.body("data.mst_oem.id", everyItem(notNullValue()))
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
		
		
		
	}
	
	@Test
	public void invalidAuthMasterAPITest() throws IOException {
		
		given()
		.spec(SpecUtil.requestSpec())
		.when()
		.post("master")//default content type application/url-formencoded
		.then()
		.spec(SpecUtil.responseSpec_TEXT(401));
		
	}

}
