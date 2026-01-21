package com.testautomation.apitesting.client;

import com.testautomation.apitesting.listener.RestAssuredListener;
import com.testautomation.apitesting.utils.PropertyUtils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * API Client abstraction layer following industry best practices.
 * Provides centralized configuration and request/response handling for API testing.
 */
public class ApiClient {

    private static final String BASE_URI = PropertyUtils.getProperty("base.uri");
    
    private final RequestSpecification requestSpec;
    private final ResponseSpecification responseSpec;

    private ApiClient(Builder builder) {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder()
                .setBaseUri(builder.baseUri != null ? builder.baseUri : BASE_URI)
                .setContentType(builder.contentType)
                .addFilter(new AllureRestAssured())
                .addFilter(new RestAssuredListener());

        if (builder.authToken != null) {
            reqBuilder.addHeader("Cookie", "token=" + builder.authToken);
        }

        this.requestSpec = reqBuilder.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        if (builder.expectedStatusCode != null) {
            resBuilder.expectStatusCode(builder.expectedStatusCode);
        }
        this.responseSpec = resBuilder.build();
    }

    public Response get(String path, Object... pathParams) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .get(path, pathParams)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    public Response post(String path, Object body) {
        return RestAssured.given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(path)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    public Response put(String path, Object body, Object... pathParams) {
        return RestAssured.given()
                .spec(requestSpec)
                .body(body)
                .when()
                .put(path, pathParams)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    public Response patch(String path, Object body, Object... pathParams) {
        return RestAssured.given()
                .spec(requestSpec)
                .body(body)
                .when()
                .patch(path, pathParams)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    public Response delete(String path, Object... pathParams) {
        return RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete(path, pathParams)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    /**
     * Builder pattern for flexible API client construction.
     */
    public static class Builder {
        private String baseUri;
        private ContentType contentType = ContentType.JSON;
        private String authToken;
        private Integer expectedStatusCode;

        public Builder baseUri(String baseUri) {
            this.baseUri = baseUri;
            return this;
        }

        public Builder contentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder authToken(String authToken) {
            this.authToken = authToken;
            return this;
        }

        public Builder expectedStatusCode(int statusCode) {
            this.expectedStatusCode = statusCode;
            return this;
        }

        public ApiClient build() {
            return new ApiClient(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
