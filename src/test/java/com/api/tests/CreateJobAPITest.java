package com.api.tests;

import static io.restassured.RestAssured.given;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class CreateJobAPITest {
	
	
	
	@Test
	public void createJobAPITest() throws IOException {
		
		Customer customer = new Customer("Priyanka", "Parida", "7044056746", "", "priyankaparida@gmail.com", "");
		//System.out.println(customer.first_name());
		CustomerAddress customerAddress=new CustomerAddress("3C", "SunRise", "Godi", "ReshmiStore", "BBSR", "753002", "India", "Odisha");
		CustomerProduct customerProduct = new CustomerProduct("2025-10-21T18:30:00.000Z", "90884000037434", "90884000037434", "90884000037434", "2025-10-21T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(2, "Loading Issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		CreateJobPayload createJobPayload = 
				new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsList);
		
		
		given()
		.spec(SpecUtil.requestSpecWithAuthRole(Role.FD, createJobPayload))
		.and()
		.header("Authorization",AuthTokenProvider.getToken(Role.FD))
		.contentType(ContentType.JSON)
		.body(createJobPayload)
		.log().all()
		.when().post("/job/create")
		.then().spec(SpecUtil.responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema/CreateAPIResponseSchema.json"))
		.body("message", equalTo("Job created successfully. "))
		.body("data.mst_service_location_id",equalTo(1))
		.body("data.job_number", startsWith("JOB_"));
	}
}
