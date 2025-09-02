package com.testautomation.apitesting.tests;

import java.util.Map;

import org.testng.annotations.Test;

import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import com.testautomation.apitesting.utils.BaseTest;
import com.testautomation.apitesting.utils.DataProviderUtils;

import io.restassured.RestAssured;

public class DataDrivenTestingUsingExcelFile extends BaseTest {

    @Test(dataProvider = "getExcelTestData", dataProviderClass = DataProviderUtils.class)
    public void dataDrivenTesting(Map<String, String> testData) {

        int totalprice = Integer.parseInt(testData.get("TotalPrice"));

        BookingDates bookingDates = new BookingDates("2023-03-25", "2023-03-30");
        Booking booking = new Booking(testData.get("FirstName"), testData.get("LastName"), "breakfast", totalprice, true, bookingDates);

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
