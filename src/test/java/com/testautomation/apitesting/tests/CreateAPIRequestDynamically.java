package com.testautomation.apitesting.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.testautomation.apitesting.pojos.Auth;
import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import com.testautomation.apitesting.utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreateAPIRequestDynamically extends BaseTest {

    private int bookingId;
    private String token;

    @BeforeClass
    public void setupDynamicTest() {
        // Dynamically create a booking
        String firstName = "postman by";
        String lastName = "testers talk";
        String additionalNeeds = "chicken";

        BookingDates bookingDates = new BookingDates("2023-08-01", "2023-08-10");
        Booking booking = new Booking(firstName, lastName, additionalNeeds, 500, true, bookingDates);

        Response response = RestAssured.given()
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .assertThat().statusCode(200)
                .body("booking.firstname", Matchers.equalTo(firstName))
                .extract().response();
        bookingId = response.path("bookingid");

        // Get auth token
        Auth auth = new Auth("admin", "password123");
        Response authResponse = RestAssured.given()
                .body(auth)
                .when()
                .post("/auth")
                .then()
                .assertThat().statusCode(200)
                .extract().response();
        token = authResponse.path("token");
    }

    @Test
    public void getBookingTest() {
        RestAssured
            .given()
            .when()
                .get("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(200);
    }

    @AfterClass
    public void cleanup() {
        // Delete the booking after the test
        RestAssured
            .given()
                .header("Cookie", "token=" + token)
            .when()
                .delete("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(201);
    }
}
