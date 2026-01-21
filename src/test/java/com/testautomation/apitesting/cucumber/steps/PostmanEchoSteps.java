package com.testautomation.apitesting.cucumber.steps;

import com.testautomation.apitesting.cucumber.context.TestContext;
import com.testautomation.apitesting.utils.Endpoints;
import com.testautomation.apitesting.utils.PropertyUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Postman Echo API operations.
 */
public class PostmanEchoSteps {

    private static final Logger logger = LogManager.getLogger(PostmanEchoSteps.class);
    private static final String POSTMAN_BASE_URI = PropertyUtils.getProperty("postman.base.uri");
    private static final String CTX_JSON_PAYLOAD = "jsonPayload";
    
    private final TestContext testContext;

    public PostmanEchoSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I have a JSON payload with key {string} and value {string}")
    public void iHaveAJsonPayloadWithKeyAndValue(String key, String value) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(key, value);
        testContext.setScenarioContext(CTX_JSON_PAYLOAD, payload);
        logger.info("Created JSON payload: {}={}", key, value);
    }

    @When("I send a GET request to echo endpoint")
    public void iSendAGetRequestToEchoEndpoint() {
        Response response = RestAssured.given()
                .baseUri(POSTMAN_BASE_URI)
                .when()
                .get(Endpoints.PostmanEcho.GET)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Sent GET request to echo endpoint");
    }

    @When("I send a POST request to echo endpoint")
    public void iSendAPostRequestToEchoEndpoint() {
        Map<String, Object> payload = testContext.getScenarioContext(CTX_JSON_PAYLOAD);
        
        Response response = RestAssured.given()
                .baseUri(POSTMAN_BASE_URI)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(Endpoints.PostmanEcho.POST)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Sent POST request to echo endpoint");
    }

    @When("I send a PUT request to echo endpoint")
    public void iSendAPutRequestToEchoEndpoint() {
        Map<String, Object> payload = testContext.getScenarioContext(CTX_JSON_PAYLOAD);
        
        Response response = RestAssured.given()
                .baseUri(POSTMAN_BASE_URI)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put(Endpoints.PostmanEcho.PUT)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Sent PUT request to echo endpoint");
    }

    @When("I send a PATCH request to echo endpoint")
    public void iSendAPatchRequestToEchoEndpoint() {
        Map<String, Object> payload = testContext.getScenarioContext(CTX_JSON_PAYLOAD);
        
        Response response = RestAssured.given()
                .baseUri(POSTMAN_BASE_URI)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch(Endpoints.PostmanEcho.PATCH)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Sent PATCH request to echo endpoint");
    }

    @When("I send a DELETE request to echo endpoint")
    public void iSendADeleteRequestToEchoEndpoint() {
        Response response = RestAssured.given()
                .baseUri(POSTMAN_BASE_URI)
                .when()
                .delete(Endpoints.PostmanEcho.DELETE)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Sent DELETE request to echo endpoint");
    }

    @When("I send a request to headers endpoint")
    public void iSendARequestToHeadersEndpoint() {
        Response response = RestAssured.given()
                .baseUri(POSTMAN_BASE_URI)
                .header("X-Custom-Header", "TestValue")
                .when()
                .get(Endpoints.PostmanEcho.HEADERS)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Sent request to headers endpoint");
    }

    @When("I send a request to cookies endpoint with cookie {string} and value {string}")
    public void iSendARequestToCookiesEndpointWithCookie(String cookieName, String cookieValue) {
        Response response = RestAssured.given()
                .baseUri(POSTMAN_BASE_URI)
                .cookie(cookieName, cookieValue)
                .when()
                .get(Endpoints.PostmanEcho.COOKIES)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Sent request to cookies endpoint with cookie: {}={}", cookieName, cookieValue);
    }

    @When("I request status code {int}")
    public void iRequestStatusCode(int statusCode) {
        Response response = RestAssured.given()
                .baseUri(POSTMAN_BASE_URI)
                .when()
                .get(Endpoints.withStatusCode(statusCode))
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Requested status code: {}", statusCode);
    }

    @When("I send a request with basic auth to echo endpoint")
    public void iSendARequestWithBasicAuthToEchoEndpoint() {
        Response response = RestAssured.given()
                .baseUri(POSTMAN_BASE_URI)
                .auth().basic("postman", "password")
                .when()
                .get(Endpoints.PostmanEcho.BASIC_AUTH)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Sent request with basic auth to echo endpoint");
    }

    @Then("the echo response status code should be {int}")
    public void theEchoResponseStatusCodeShouldBe(int expectedStatusCode) {
        Response response = testContext.getResponse();
        Assert.assertNotNull(response, "Response should not be null");
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
                "Status code mismatch");
        logger.info("Verified echo response status code: {}", expectedStatusCode);
    }

    @Then("the echo response should contain the request URL")
    public void theEchoResponseShouldContainTheRequestUrl() {
        Response response = testContext.getResponse();
        String url = response.jsonPath().getString("url");
        Assert.assertNotNull(url, "URL should not be null in response");
        Assert.assertTrue(url.contains(POSTMAN_BASE_URI.replace("http://", "").replace("https://", "")), 
                "URL should contain the Postman Echo domain");
        logger.info("Verified response contains URL: {}", url);
    }

    @Then("the echo response should contain the posted data")
    public void theEchoResponseShouldContainThePostedData() {
        Response response = testContext.getResponse();
        Object json = response.jsonPath().get("json");
        Assert.assertNotNull(json, "Posted JSON data should be in response");
        logger.info("Verified response contains posted data");
    }

    @Then("the response should contain host header")
    public void theResponseShouldContainHostHeader() {
        Response response = testContext.getResponse();
        String host = response.jsonPath().getString("headers.host");
        Assert.assertNotNull(host, "Host header should not be null");
        logger.info("Verified response contains host header: {}", host);
    }

    @Then("the response should contain the cookie {string}")
    public void theResponseShouldContainTheCookie(String cookieName) {
        Response response = testContext.getResponse();
        String cookieValue = response.jsonPath().getString("cookies." + cookieName);
        Assert.assertNotNull(cookieValue, "Cookie " + cookieName + " should be in response");
        logger.info("Verified response contains cookie: {}", cookieName);
    }

    @Then("the response should confirm authentication")
    public void theResponseShouldConfirmAuthentication() {
        Response response = testContext.getResponse();
        Boolean authenticated = response.jsonPath().getBoolean("authenticated");
        Assert.assertTrue(authenticated, "Authentication should be confirmed");
        logger.info("Verified authentication confirmed");
    }
}
