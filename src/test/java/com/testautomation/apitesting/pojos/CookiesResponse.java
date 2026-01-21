package com.testautomation.apitesting.pojos;

import java.util.Map;

/**
 * POJO for Postman Echo cookies response.
 */
public class CookiesResponse {
    private Map<String, String> cookies;

    public CookiesResponse() {
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }
}
