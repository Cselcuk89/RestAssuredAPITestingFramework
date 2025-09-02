package com.testautomation.apitesting.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.PropertyUtils;

import io.restassured.RestAssured;

public class Cookies extends BaseTest {

    @Test
    public void cookiesAPITest() {
        Map<String, String> cookies = new HashMap<>();
        cookies.put("skill1", "rest assured by testers talk");
        cookies.put("skill2", "postman by testers talk");
        cookies.put("skill3", "specflow by testers talk");

        RestAssured
            .given()
                .cookies(cookies)
                .baseUri(PropertyUtils.getProperty("postman.base.uri"))
                .log().all()
            .when()
                .get("/cookies")
            .then()
                .assertThat()
                .statusCode(200);
    }
}
