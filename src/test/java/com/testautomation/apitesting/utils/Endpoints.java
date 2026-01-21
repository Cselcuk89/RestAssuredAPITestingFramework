package com.testautomation.apitesting.utils;

/**
 * Centralized API endpoint constants.
 * This follows industry best practices for maintaining API endpoints in a single location.
 */
public final class Endpoints {

    private Endpoints() {
        // Private constructor to prevent instantiation
    }

    // ==================== Restful Booker API ====================
    
    // Authentication
    public static final String AUTH = "/auth";

    // Booking endpoints
    public static final String BOOKING = "/booking";
    public static final String BOOKING_BY_ID = "/booking/{id}";

    // Health check
    public static final String PING = "/ping";

    // ==================== Postman Echo API ====================
    
    /**
     * Postman Echo endpoints for testing HTTP methods
     */
    public static class PostmanEcho {
        private PostmanEcho() {}
        
        // HTTP Methods
        public static final String GET = "/get";
        public static final String POST = "/post";
        public static final String PUT = "/put";
        public static final String PATCH = "/patch";
        public static final String DELETE = "/delete";
        
        // Headers and Cookies
        public static final String HEADERS = "/headers";
        public static final String COOKIES = "/cookies";
        public static final String COOKIES_SET = "/cookies/set";
        public static final String COOKIES_DELETE = "/cookies/delete";
        
        // Response Inspection
        public static final String RESPONSE_HEADERS = "/response-headers";
        
        // Utilities
        public static final String STATUS = "/status/{code}";
        public static final String DELAY = "/delay/{seconds}";
        public static final String TIME_OBJECT = "/time/object";
        public static final String TIME_NOW = "/time/now";
        
        // Authentication
        public static final String BASIC_AUTH = "/basic-auth";
        public static final String DIGEST_AUTH = "/digest-auth";
        
        // Data formats
        public static final String GZIP = "/gzip";
        public static final String DEFLATE = "/deflate";
        public static final String IP = "/ip";
    }

    /**
     * Helper method to build endpoint with path parameter.
     */
    public static String withId(String endpoint, Object id) {
        return endpoint.replace("{id}", String.valueOf(id));
    }
    
    /**
     * Helper method to build status endpoint with status code.
     */
    public static String withStatusCode(int code) {
        return PostmanEcho.STATUS.replace("{code}", String.valueOf(code));
    }
    
    /**
     * Helper method to build delay endpoint with seconds.
     */
    public static String withDelay(int seconds) {
        return PostmanEcho.DELAY.replace("{seconds}", String.valueOf(seconds));
    }
}
