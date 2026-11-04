package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

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

public class CreateJobAPITest {
	
	
	
	@Test
	public void createJobAPITest() throws IOException {
		
		Customer customer = new Customer("Priyanka", "Parida", "7044056746", "", "priyankaparida@gmail.com", "");
		CustomerAddress customerAddress=new CustomerAddress("3C", "SunRise", "Godi", "ReshmiStore", "BBSR", "753002", "India", "Odisha");
		CustomerProduct customerProduct = new CustomerProduct("2025-10-21T18:30:00.000Z", "19914000037473", "19914000037473", "19914000037473", "2025-10-21T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(2, "Loading Issue");
		Problems[] problemsArray = new Problems[1];
		problemsArray[0]=problems;
		CreateJobPayload createJobPayload = 
				new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsArray);
		
		
		given()
		.spec(SpecUtil.requestSpecWithAuthRole(Role.FD, createJobPayload))
		.and()
		.header("Authorization",AuthTokenProvider.getToken(Role.FD))
		.contentType(ContentType.JSON)
		.body(createJobPayload)
		.log().all()
		.when().post("/job/create")
		.then().spec(SpecUtil.responseSpec_OK());
	}
}
