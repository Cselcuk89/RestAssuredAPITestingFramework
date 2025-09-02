package com.testautomation.apitesting.tests;

import org.testng.annotations.Test;

import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.PropertyUtils;

import io.restassured.RestAssured;

public class BasicAuth extends BaseTest {

    @Test
    public void basicAuthAPITest() {
        RestAssured
            .given()
                .auth().basic("postman", "password")
                .baseUri(PropertyUtils.getProperty("postman.base.uri"))
            .when()
                .get("/basic-auth")
            .then()
                .assertThat()
                .statusCode(200);
    }
}
