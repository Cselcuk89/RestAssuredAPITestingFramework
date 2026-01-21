package com.testautomation.apitesting.utils;

/**
 * Centralized API endpoint constants.
 * This follows industry best practices for maintaining API endpoints in a single location.
 */
public final class Endpoints {

    private Endpoints() {
        // Private constructor to prevent instantiation
    }

    // Authentication
    public static final String AUTH = "/auth";

    // Booking endpoints
    public static final String BOOKING = "/booking";
    public static final String BOOKING_BY_ID = "/booking/{id}";

    // Health check
    public static final String PING = "/ping";

    /**
     * Helper method to build endpoint with path parameter.
     */
    public static String withId(String endpoint, Object id) {
        return endpoint.replace("{id}", String.valueOf(id));
    }
}
