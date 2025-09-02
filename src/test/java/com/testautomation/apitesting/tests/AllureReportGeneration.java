package com.testautomation.apitesting.tests;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.testautomation.apitesting.pojos.Auth;
import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import com.testautomation.apitesting.utils.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@Epic("Epic-01")
@Feature("Create Update Delete Booking")
public class AllureReportGeneration extends BaseTest {

    private int bookingId;
    private String token;

    @BeforeClass
    public void setupE2ETest() {
        // Create a booking for the e2e test
        BookingDates bookingDates = new BookingDates("2023-07-01", "2023-07-10");
        Booking booking = new Booking("allure", "tester", "breakfast", 400, true, bookingDates);

        Response response = RestAssured.given()
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .assertThat().statusCode(200)
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

    @Story("Story 1: End-to-End Flow")
    @Test(description = "Get booking details", priority = 1)
    @Description("Verify that the created booking can be retrieved")
    @Severity(SeverityLevel.NORMAL)
    public void getBookingTest() {
        RestAssured
            .given()
            .when()
                .get("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(200);
    }

    @Story("Story 1: End-to-End Flow")
    @Test(description = "Update booking details", priority = 2)
    @Description("Verify that the booking can be updated with a PUT request")
    @Severity(SeverityLevel.CRITICAL)
    public void updateBookingTest() {
        BookingDates updatedBookingDates = new BookingDates("2023-07-01", "2023-07-15");
        Booking updatedBooking = new Booking("allure-updated", "tester", "lunch", 450, false, updatedBookingDates);

        RestAssured
            .given()
                .body(updatedBooking)
                .header("Cookie", "token=" + token)
            .when()
                .put("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", Matchers.equalTo("allure-updated"))
                .body("totalprice", Matchers.equalTo(450));
    }

    @Story("Story 1: End-to-End Flow")
    @Test(description = "Partially update booking details", priority = 3)
    @Description("Verify that the booking can be partially updated with a PATCH request")
    @Severity(SeverityLevel.CRITICAL)
    public void patchBookingTest() {
        Map<String, Object> patchBody = new HashMap<>();
        patchBody.put("firstname", "allure-patched");

        RestAssured
            .given()
                .body(patchBody)
                .header("Cookie", "token=" + token)
            .when()
                .patch("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", Matchers.equalTo("allure-patched"));
    }

    @AfterClass
    public void cleanup() {
        // Delete the booking after all tests are done
        RestAssured
            .given()
                .header("Cookie", "token=" + token)
            .when()
                .delete("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(201);
    }

    @Story("Story 2: Create and Verify")
    @Test(description = "Create and verify a new booking")
    @Description("A simplified test to create a booking and verify its creation")
    @Severity(SeverityLevel.BLOCKER)
    public void createAndVerifyBookingTest() {
        // Create a booking
        BookingDates bookingDates = new BookingDates("2023-08-01", "2023-08-05");
        Booking booking = new Booking("simple", "test", "none", 100, true, bookingDates);

        Response response = RestAssured.given()
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .assertThat().statusCode(200)
                .extract().response();
        int newBookingId = response.path("bookingid");

        // Verify the booking
        RestAssured
            .given()
            .when()
                .get("/booking/{bookingId}", newBookingId)
            .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", Matchers.equalTo("simple"));
    }
}
