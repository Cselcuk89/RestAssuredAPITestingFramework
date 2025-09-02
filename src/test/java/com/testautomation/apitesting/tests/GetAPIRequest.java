package com.testautomation.apitesting.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.testautomation.apitesting.utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetAPIRequest extends BaseTest {
	
	@Test
	public void getAllBookings() {
		
		Response response =
		RestAssured
				.given()
				.when()
					.get("/booking")
				.then()
					.assertThat()
					.statusCode(200)
					.statusLine("HTTP/1.1 200 OK")
					.header("Content-Type", "application/json; charset=utf-8")
				.extract()
					.response();
		
		Assert.assertTrue(response.getBody().asString().contains("bookingid"));
		
	}

}
