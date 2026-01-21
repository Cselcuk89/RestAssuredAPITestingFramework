package com.testautomation.apitesting.cucumber.context;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

/**
 * TestContext class for sharing state between Cucumber step definitions.
 * This follows the industry best practice of using a context object for BDD testing.
 */
public class TestContext {

    private RequestSpecification requestSpecification;
    private Response response;
    private String authToken;
    private Integer bookingId;
    private final Map<String, Object> scenarioContext;

    public TestContext() {
        this.scenarioContext = new HashMap<>();
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public void setScenarioContext(String key, Object value) {
        scenarioContext.put(key, value);
    }

    /**
     * Get value from scenario context with type safety.
     * @param key The context key
     * @param <T> The expected type
     * @return The value cast to the expected type, or null if not found
     * @throws ClassCastException if the value cannot be cast to the expected type
     */
    @SuppressWarnings("unchecked")
    public <T> T getScenarioContext(String key) {
        Object value = scenarioContext.get(key);
        if (value == null) {
            return null;
        }
        try {
            return (T) value;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    String.format("Cannot cast value for key '%s' to expected type. Actual type: %s",
                            key, value.getClass().getName()));
        }
    }

    public boolean containsKey(String key) {
        return scenarioContext.containsKey(key);
    }

    public void clearContext() {
        scenarioContext.clear();
        requestSpecification = null;
        response = null;
        authToken = null;
        bookingId = null;
    }
}
