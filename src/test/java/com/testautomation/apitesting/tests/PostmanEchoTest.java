package com.testautomation.apitesting.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.Endpoints;
import com.testautomation.apitesting.utils.PropertyUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * Test class for Postman Echo API endpoints.
 * Demonstrates testing various HTTP methods and response handling.
 */
public class PostmanEchoTest extends BaseTest {

    private static final int[] TEST_STATUS_CODES = {200, 201, 400, 404, 500};
    private String postmanBaseUri;

    @BeforeClass
    public void setupPostmanBase() {
        postmanBaseUri = PropertyUtils.getProperty("postman.base.uri");
        Assert.assertNotNull(postmanBaseUri, 
                "postman.base.uri must be configured in config.properties");
        Assert.assertFalse(postmanBaseUri.isEmpty(),
                "postman.base.uri cannot be empty");
    }

    @Test(description = "Test GET request to echo endpoint")
    public void testGetRequest() {
        Response response = RestAssured.given()
                .baseUri(postmanBaseUri)
                .when()
                .get(Endpoints.PostmanEcho.GET)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.jsonPath().getString("url"));
    }

    @Test(description = "Test POST request with JSON body")
    public void testPostRequest() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "TestUser");
        payload.put("email", "test@example.com");

        Response response = RestAssured.given()
                .baseUri(postmanBaseUri)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(Endpoints.PostmanEcho.POST)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.jsonPath().get("json"));
    }

    @Test(description = "Test PUT request")
    public void testPutRequest() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("updated", "true");

        Response response = RestAssured.given()
                .baseUri(postmanBaseUri)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put(Endpoints.PostmanEcho.PUT)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "Test PATCH request")
    public void testPatchRequest() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("patched", "true");

        Response response = RestAssured.given()
                .baseUri(postmanBaseUri)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch(Endpoints.PostmanEcho.PATCH)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "Test DELETE request")
    public void testDeleteRequest() {
        Response response = RestAssured.given()
                .baseUri(postmanBaseUri)
                .when()
                .delete(Endpoints.PostmanEcho.DELETE)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "Test headers endpoint")
    public void testHeadersEndpoint() {
        Response response = RestAssured.given()
                .baseUri(postmanBaseUri)
                .header("X-Custom-Header", "CustomValue")
                .when()
                .get(Endpoints.PostmanEcho.HEADERS)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.jsonPath().getString("headers.host"));
        Assert.assertEquals(response.jsonPath().getString("headers.x-custom-header"), "CustomValue");
    }

    @Test(description = "Test cookies endpoint")
    public void testCookiesEndpoint() {
        Response response = RestAssured.given()
                .baseUri(postmanBaseUri)
                .cookie("session", "abc123")
                .when()
                .get(Endpoints.PostmanEcho.COOKIES)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("cookies.session"), "abc123");
    }

    @Test(description = "Test status code endpoint")
    public void testStatusCodeEndpoint() {
        for (int expectedStatus : TEST_STATUS_CODES) {
            Response response = RestAssured.given()
                    .baseUri(postmanBaseUri)
                    .when()
                    .get(Endpoints.withStatusCode(expectedStatus))
                    .then()
                    .extract()
                    .response();

            Assert.assertEquals(response.getStatusCode(), expectedStatus,
                    "Expected status code " + expectedStatus);
        }
    }

    @Test(description = "Test basic authentication")
    public void testBasicAuth() {
        Response response = RestAssured.given()
                .baseUri(postmanBaseUri)
                .auth().basic("postman", "password")
                .when()
                .get(Endpoints.PostmanEcho.BASIC_AUTH)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.jsonPath().getBoolean("authenticated"));
    }

    @Test(description = "Test IP endpoint")
    public void testIpEndpoint() {
        Response response = RestAssured.given()
                .baseUri(postmanBaseUri)
                .when()
                .get(Endpoints.PostmanEcho.IP)
                .then()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.jsonPath().getString("ip"));
    }
}
