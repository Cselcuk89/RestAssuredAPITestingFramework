package com.testautomation.apitesting.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import com.testautomation.apitesting.utils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PostAPIRequest extends BaseTest {

    @Test
    public void createBooking() {
        // Prepare request body using POJOs
        BookingDates bookingDates = new BookingDates("2023-03-25", "2023-03-30");
        Booking booking = new Booking("api testing", "tutorial", "breakfast", 1000, true, bookingDates);

        Response response =
            RestAssured
                .given()
                    .body(booking)
                .when()
                    .post("/booking")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .body("booking.firstname", Matchers.equalTo("api testing"))
                    .body("booking.totalprice", Matchers.equalTo(1000))
                    .body("booking.bookingdates.checkin", Matchers.equalTo("2023-03-25"))
                .extract()
                    .response();

        int bookingId = response.path("bookingid");

        RestAssured
            .given()
                .pathParam("bookingID", bookingId)
            .when()
                .get("/booking/{bookingID}")
            .then()
                .assertThat()
                .statusCode(200)
                .body("firstname", Matchers.equalTo("api testing"))
                .body("lastname", Matchers.equalTo("tutorial"));
    }
}
