package com.testautomation.apitesting.cucumber.steps;

import com.testautomation.apitesting.cucumber.context.TestContext;
import com.testautomation.apitesting.utils.Endpoints;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

/**
 * Step definitions for Health Check API operations.
 */
public class HealthCheckSteps {

    private static final Logger logger = LogManager.getLogger(HealthCheckSteps.class);
    private final TestContext testContext;

    public HealthCheckSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("I call the health check endpoint")
    public void iCallTheHealthCheckEndpoint() {
        Response response = RestAssured.given()
                .when()
                .get(Endpoints.PING)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Called health check endpoint, status: {}", response.getStatusCode());
    }

    @Then("the response body should be {string}")
    public void theResponseBodyShouldBe(String expectedBody) {
        Response response = testContext.getResponse();
        String actualBody = response.getBody().asString();
        Assert.assertEquals(actualBody, expectedBody,
                "Response body mismatch");
        logger.info("Verified response body: {}", expectedBody);
    }
}
