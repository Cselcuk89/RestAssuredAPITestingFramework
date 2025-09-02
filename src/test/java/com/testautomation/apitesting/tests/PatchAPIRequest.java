package com.testautomation.apitesting.tests;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.testautomation.apitesting.pojos.Auth;
import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import com.testautomation.apitesting.utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PatchAPIRequest extends BaseTest {

    @Test
    public void patchAPIRequest() {
        // Create a new booking to update
        BookingDates bookingDates = new BookingDates("2023-05-01", "2023-05-05");
        Booking booking = new Booking("patch", "user", "breakfast", 250, true, bookingDates);

        Response createResponse = RestAssured
                .given()
                    .body(booking)
                .when()
                    .post("/booking")
                .then()
                    .assertThat()
                    .statusCode(200)
                .extract()
                    .response();

        int bookingId = createResponse.path("bookingid");

        // Get auth token
        String token = getAuthToken();

        // Prepare the patch request body
        Map<String, Object> patchBody = new HashMap<>();
        patchBody.put("firstname", "Testers Talk");
        patchBody.put("totalprice", 300);

        // Perform the PATCH request
        RestAssured
            .given()
                .body(patchBody)
                .header("Cookie", "token=" + token)
            .when()
                .patch("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", Matchers.equalTo("Testers Talk"))
                .body("totalprice", Matchers.equalTo(300));
    }

    private String getAuthToken() {
        Auth auth = new Auth("admin", "password123");

        Response response = RestAssured
                .given()
                    .body(auth)
                .when()
                    .post("/auth")
                .then()
                    .assertThat()
                    .statusCode(200)
                .extract()
                    .response();

        return response.path("token");
    }
}
