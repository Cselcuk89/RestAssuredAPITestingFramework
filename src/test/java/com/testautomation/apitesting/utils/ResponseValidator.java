package com.testautomation.apitesting.utils;

import com.testautomation.apitesting.exceptions.ApiException;

import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matcher;
import org.testng.Assert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Response validation utility class.
 * Provides fluent API for validating API responses following industry best practices.
 */
public class ResponseValidator {

    private static final Logger logger = LogManager.getLogger(ResponseValidator.class);
    private final Response response;

    private ResponseValidator(Response response) {
        if (response == null) {
            throw new ApiException("Response cannot be null");
        }
        this.response = response;
    }

    /**
     * Create a new ResponseValidator instance.
     */
    public static ResponseValidator forResponse(Response response) {
        return new ResponseValidator(response);
    }

    /**
     * Validate status code equals expected value.
     */
    public ResponseValidator statusCode(int expectedCode) {
        int actualCode = response.getStatusCode();
        if (actualCode != expectedCode) {
            String message = String.format("Expected status code %d but got %d", expectedCode, actualCode);
            logger.error("{} - Response body: {}", message, response.getBody().asPrettyString());
            throw new ApiException(message, actualCode, response.getBody().asString(), "");
        }
        logger.info("Validated status code: {}", expectedCode);
        return this;
    }

    /**
     * Validate status code is successful (2xx).
     */
    public ResponseValidator isSuccessful() {
        int code = response.getStatusCode();
        if (code < 200 || code >= 300) {
            throw new ApiException("Expected successful status code (2xx) but got " + code, 
                    code, response.getBody().asString(), "");
        }
        return this;
    }

    /**
     * Validate JSON path contains expected value.
     */
    public ResponseValidator jsonPath(String path, Object expectedValue) {
        Object actualValue = response.jsonPath().get(path);
        Assert.assertEquals(actualValue, expectedValue, 
                String.format("JSON path '%s' mismatch", path));
        logger.info("Validated JSON path '{}' = {}", path, expectedValue);
        return this;
    }

    /**
     * Validate JSON path matches using Hamcrest matcher.
     */
    public <T> ResponseValidator jsonPath(String path, Matcher<T> matcher) {
        T actualValue = response.jsonPath().get(path);
        assertThat(String.format("JSON path '%s' validation", path), actualValue, matcher);
        logger.info("Validated JSON path '{}' matches expected condition", path);
        return this;
    }

    /**
     * Validate JSON path exists.
     */
    public ResponseValidator hasJsonPath(String path) {
        Object value = response.jsonPath().get(path);
        Assert.assertNotNull(value, String.format("JSON path '%s' should exist", path));
        logger.info("Validated JSON path '{}' exists", path);
        return this;
    }

    /**
     * Validate JSON path is not null.
     */
    public ResponseValidator jsonPathNotNull(String path) {
        return hasJsonPath(path);
    }

    /**
     * Validate response header.
     */
    public ResponseValidator header(String name, String expectedValue) {
        String actualValue = response.getHeader(name);
        Assert.assertEquals(actualValue, expectedValue,
                String.format("Header '%s' mismatch", name));
        logger.info("Validated header '{}' = {}", name, expectedValue);
        return this;
    }

    /**
     * Validate response header contains value.
     */
    public ResponseValidator headerContains(String name, String expectedSubstring) {
        String actualValue = response.getHeader(name);
        Assert.assertTrue(actualValue != null && actualValue.contains(expectedSubstring),
                String.format("Header '%s' should contain '%s'", name, expectedSubstring));
        return this;
    }

    /**
     * Validate content type.
     */
    public ResponseValidator contentType(String expectedContentType) {
        String actualContentType = response.getContentType();
        Assert.assertTrue(actualContentType != null && actualContentType.contains(expectedContentType),
                String.format("Expected content type '%s' but got '%s'", expectedContentType, actualContentType));
        return this;
    }

    /**
     * Validate response time is under specified duration.
     */
    public ResponseValidator responseTimeUnder(long duration, TimeUnit unit) {
        long responseTimeMs = response.getTime();
        long thresholdMs = unit.toMillis(duration);
        Assert.assertTrue(responseTimeMs < thresholdMs,
                String.format("Response time %dms exceeds threshold %dms", responseTimeMs, thresholdMs));
        logger.info("Validated response time {}ms is under threshold {}ms", responseTimeMs, thresholdMs);
        return this;
    }

    /**
     * Validate body contains text.
     */
    public ResponseValidator bodyContains(String text) {
        String body = response.getBody().asString();
        Assert.assertTrue(body.contains(text),
                String.format("Response body should contain '%s'", text));
        return this;
    }

    /**
     * Validate body is not empty.
     */
    public ResponseValidator bodyNotEmpty() {
        String body = response.getBody().asString();
        Assert.assertFalse(body == null || body.trim().isEmpty(),
                "Response body should not be empty");
        return this;
    }

    /**
     * Extract value from JSON path.
     */
    public <T> T extract(String path) {
        return response.jsonPath().get(path);
    }

    /**
     * Extract list from JSON path.
     */
    public <T> List<T> extractList(String path) {
        return response.jsonPath().getList(path);
    }

    /**
     * Extract as Map.
     */
    public Map<String, Object> extractAsMap(String path) {
        return response.jsonPath().getMap(path);
    }

    /**
     * Get the underlying response.
     */
    public Response getResponse() {
        return response;
    }
}
