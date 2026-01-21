package com.testautomation.apitesting.exceptions;

/**
 * Custom exception for API-related errors.
 * Provides detailed context about API failures for better debugging.
 */
public class ApiException extends RuntimeException {

    private final int statusCode;
    private final String responseBody;
    private final String endpoint;

    public ApiException(String message) {
        super(message);
        this.statusCode = 0;
        this.responseBody = null;
        this.endpoint = null;
    }

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = null;
        this.endpoint = null;
    }

    public ApiException(String message, int statusCode, String responseBody, String endpoint) {
        super(String.format("%s | Endpoint: %s | Status: %d | Response: %s", 
                message, endpoint, statusCode, responseBody));
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.endpoint = endpoint;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 0;
        this.responseBody = null;
        this.endpoint = null;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
