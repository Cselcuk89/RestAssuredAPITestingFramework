package com.testautomation.apitesting.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.Endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * Test class for Health Check (Ping) API endpoint.
 */
public class HealthCheckTest extends BaseTest {

    @Test(description = "Verify ping endpoint returns 201 Created")
    public void testHealthCheckEndpoint() {
        Response response = RestAssured.given()
                .when()
                .get(Endpoints.PING)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 201,
                "Health check should return 201 Created");
        Assert.assertEquals(response.getBody().asString(), "Created",
                "Response body should be 'Created'");
    }
}
