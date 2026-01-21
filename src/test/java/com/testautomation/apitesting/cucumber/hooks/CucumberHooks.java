package com.testautomation.apitesting.cucumber.hooks;

import com.testautomation.apitesting.cucumber.context.TestContext;
import com.testautomation.apitesting.utils.PropertyUtils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Cucumber Hooks for setup and teardown operations.
 * Follows industry best practices for BDD test lifecycle management.
 */
public class CucumberHooks {

    private static final Logger logger = LogManager.getLogger(CucumberHooks.class);
    private final TestContext testContext;

    public CucumberHooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp(Scenario scenario) {
        logger.info("Starting scenario: {}", scenario.getName());
        
        // Initialize RestAssured base configuration
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(PropertyUtils.getProperty("base.uri"))
                .addFilter(new AllureRestAssured())
                .setContentType(ContentType.JSON)
                .build();

        // Clear any previous context
        testContext.clearContext();
        
        // Add scenario name to Allure report
        Allure.epic("API Testing");
        Allure.feature("Booking API");
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Finished scenario: {} - Status: {}", scenario.getName(), scenario.getStatus());
        
        if (scenario.isFailed()) {
            // Log response details for debugging failed scenarios
            if (testContext.getResponse() != null) {
                String responseBody = testContext.getResponse().getBody().asPrettyString();
                logger.error("Last response body: {}", responseBody);
                Allure.addAttachment("Last Response", "application/json", responseBody, ".json");
            }
        }

        // Cleanup: Delete booking if it was created during the test
        if (testContext.getBookingId() != null && testContext.getAuthToken() != null) {
            try {
                RestAssured.given()
                        .header("Cookie", "token=" + testContext.getAuthToken())
                        .when()
                        .delete("/booking/{id}", testContext.getBookingId())
                        .then()
                        .statusCode(201);
                logger.info("Cleaned up booking with ID: {}", testContext.getBookingId());
            } catch (Exception e) {
                logger.warn("Failed to cleanup booking: {}", e.getMessage());
            }
        }
    }
}
