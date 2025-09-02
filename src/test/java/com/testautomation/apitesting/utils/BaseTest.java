package com.testautomation.apitesting.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

public class BaseTest {
	
	private static final Logger logger = LogManager.getLogger(BaseTest.class);
	
	@BeforeMethod
	public void beforeMethod() {
		//RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.requestSpecification = new RequestSpecBuilder()
				.setBaseUri(PropertyUtils.getProperty("base.uri"))
				.addFilter(new AllureRestAssured())
				.setContentType(ContentType.JSON)
				.build();
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			Throwable t = result.getThrowable();
			StringWriter error = new StringWriter();
			t.printStackTrace(new PrintWriter(error));
			logger.info(error.toString());
		}
		
	}
	
}
