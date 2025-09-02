package com.testautomation.apitesting.tests;

import java.util.Map;

import org.testng.annotations.Test;

import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.DataProviderUtils;

import io.restassured.RestAssured;

public class DataDrivenTestingUsingCSVFile extends BaseTest {

    @Test(dataProvider = "getCsvTestData", dataProviderClass = DataProviderUtils.class)
    public void dataDrivenTesting(Map<String, String> testData) {

        int totalprice = Integer.parseInt(testData.get("totalprice"));

        BookingDates bookingDates = new BookingDates("2023-03-25", "2023-03-30");
        Booking booking = new Booking(testData.get("firstname"), testData.get("lastname"), "breakfast", totalprice, true, bookingDates);

        RestAssured
            .given()
                .body(booking)
            .when()
                .post("/booking")
            .then()
                .assertThat()
                .statusCode(200);
    }
}
