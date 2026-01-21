package com.testautomation.apitesting.pojos;

import java.util.Map;

/**
 * POJO for Postman Echo headers response.
 */
public class HeadersResponse {
    private Map<String, String> headers;

    public HeadersResponse() {
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
