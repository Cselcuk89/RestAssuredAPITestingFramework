package com.testautomation.apitesting.tests;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.testautomation.apitesting.listener.RestAssuredListener;
import com.testautomation.apitesting.pojos.Auth;
import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import com.testautomation.apitesting.utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EndToEndAPITest extends BaseTest {

    private int bookingId;
    private String token;

    @BeforeClass
    public void setup() {
        // Create a booking to be used by all tests in this class
        BookingDates bookingDates = new BookingDates("2023-06-01", "2023-06-10");
        Booking booking = new Booking("e2e", "tester", "breakfast", 300, true, bookingDates);

        Response response = RestAssured.given()
                .filter(new RestAssuredListener())
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
                .filter(new RestAssuredListener())
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
                .filter(new RestAssuredListener())
            .when()
                .get("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "getBookingTest")
    public void updateBookingTest() {
        BookingDates updatedBookingDates = new BookingDates("2023-06-01", "2023-06-15");
        Booking updatedBooking = new Booking("e2e-updated", "tester", "lunch", 350, false, updatedBookingDates);

        RestAssured
            .given()
                .filter(new RestAssuredListener())
                .body(updatedBooking)
                .header("Cookie", "token=" + token)
            .when()
                .put("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", Matchers.equalTo("e2e-updated"))
                .body("totalprice", Matchers.equalTo(350));
    }

    @Test(dependsOnMethods = "updateBookingTest")
    public void patchBookingTest() {
        Map<String, Object> patchBody = new HashMap<>();
        patchBody.put("firstname", "e2e-patched");

        RestAssured
            .given()
                .filter(new RestAssuredListener())
                .body(patchBody)
                .header("Cookie", "token=" + token)
            .when()
                .patch("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", Matchers.equalTo("e2e-patched"));
    }

    @AfterClass
    public void cleanup() {
        // Delete the booking after all tests are done
        RestAssured
            .given()
                .filter(new RestAssuredListener())
                .header("Cookie", "token=" + token)
            .when()
                .delete("/booking/{bookingId}", bookingId)
            .then()
                .assertThat()
                .statusCode(201);
    }
}
