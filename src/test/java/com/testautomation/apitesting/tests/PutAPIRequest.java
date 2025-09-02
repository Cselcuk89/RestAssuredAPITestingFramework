package com.testautomation.apitesting.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.testautomation.apitesting.pojos.Auth;
import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import com.testautomation.apitesting.utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PutAPIRequest extends BaseTest {

    @Test
    public void putAPIRequest() {
        // Create a new booking to update
        BookingDates bookingDates = new BookingDates("2023-04-01", "2023-04-05");
        Booking booking = new Booking("original", "user", "lunch", 150, true, bookingDates);

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

        // Prepare the updated request body
        BookingDates updatedBookingDates = new BookingDates("2023-04-01", "2023-04-10");
        Booking updatedBooking = new Booking("updated", "user", "dinner", 200, false, updatedBookingDates);

        // Perform the PUT request
        RestAssured
            .given()
                .body(updatedBooking)
                .header("Cookie", "token=" + token)
            .when()
                .put("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", Matchers.equalTo("updated"))
                .body("totalprice", Matchers.equalTo(200))
                .body("bookingdates.checkout", Matchers.equalTo("2023-04-10"));
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
