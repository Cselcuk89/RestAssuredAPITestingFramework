package com.testautomation.apitesting.cucumber.steps;

import com.testautomation.apitesting.cucumber.context.TestContext;
import com.testautomation.apitesting.pojos.Auth;
import com.testautomation.apitesting.pojos.Booking;
import com.testautomation.apitesting.pojos.BookingDates;
import com.testautomation.apitesting.utils.PropertyUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Booking API operations.
 * Follows industry best practices for BDD step implementations.
 */
public class BookingSteps {

    private static final Logger logger = LogManager.getLogger(BookingSteps.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    private final TestContext testContext;

    public BookingSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Generate dynamic check-in date (today + 7 days).
     */
    private String getCheckInDate() {
        return LocalDate.now().plusDays(7).format(DATE_FORMATTER);
    }

    /**
     * Generate dynamic check-out date (today + 14 days).
     */
    private String getCheckOutDate() {
        return LocalDate.now().plusDays(14).format(DATE_FORMATTER);
    }

    @Given("I am authenticated as admin user")
    public void iAmAuthenticatedAsAdminUser() {
        String username = PropertyUtils.getProperty("auth.username");
        String password = PropertyUtils.getProperty("auth.password");
        Auth auth = new Auth(username, password);
        
        Response authResponse = RestAssured.given()
                .body(auth)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String token = authResponse.path("token");
        testContext.setAuthToken(token);
        logger.info("Successfully authenticated, token obtained");
    }

    @Given("I have a booking with firstname {string} and lastname {string}")
    public void iHaveABookingWithFirstnameAndLastname(String firstname, String lastname) {
        BookingDates bookingDates = new BookingDates(getCheckInDate(), getCheckOutDate());
        Booking booking = new Booking(firstname, lastname, "breakfast", 150, true, bookingDates);
        testContext.setScenarioContext("booking", booking);
        logger.info("Created booking object with name: {} {}", firstname, lastname);
    }

    @When("I create the booking")
    public void iCreateTheBooking() {
        Booking booking = testContext.getScenarioContext("booking");
        
        Response response = RestAssured.given()
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        
        if (response.getStatusCode() == 200) {
            Integer bookingId = response.path("bookingid");
            testContext.setBookingId(bookingId);
            logger.info("Booking created with ID: {}", bookingId);
        }
    }

    @When("I retrieve the booking by ID")
    public void iRetrieveTheBookingById() {
        Integer bookingId = testContext.getBookingId();
        Assert.assertNotNull(bookingId, "Booking ID should not be null");

        Response response = RestAssured.given()
                .when()
                .get("/booking/{id}", bookingId)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Retrieved booking with ID: {}", bookingId);
    }

    @When("I retrieve all bookings")
    public void iRetrieveAllBookings() {
        Response response = RestAssured.given()
                .when()
                .get("/booking")
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Retrieved all bookings");
    }

    @When("I update the booking with firstname {string}")
    public void iUpdateTheBookingWithFirstname(String newFirstname) {
        Integer bookingId = testContext.getBookingId();
        String token = testContext.getAuthToken();
        
        Assert.assertNotNull(bookingId, "Booking ID should not be null");
        Assert.assertNotNull(token, "Auth token should not be null");

        Booking originalBooking = testContext.getScenarioContext("booking");
        BookingDates bookingDates = new BookingDates(getCheckInDate(), getCheckOutDate());
        Booking updatedBooking = new Booking(
                newFirstname,
                originalBooking.getLastname(),
                originalBooking.getAdditionalneeds(),
                originalBooking.getTotalprice(),
                originalBooking.isDepositpaid(),
                bookingDates
        );

        Response response = RestAssured.given()
                .header("Cookie", "token=" + token)
                .body(updatedBooking)
                .when()
                .put("/booking/{id}", bookingId)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Updated booking {} with new firstname: {}", bookingId, newFirstname);
    }

    @When("I partially update the booking with firstname {string}")
    public void iPartiallyUpdateTheBookingWithFirstname(String newFirstname) {
        Integer bookingId = testContext.getBookingId();
        String token = testContext.getAuthToken();
        
        Assert.assertNotNull(bookingId, "Booking ID should not be null");
        Assert.assertNotNull(token, "Auth token should not be null");

        Map<String, Object> patchBody = new HashMap<>();
        patchBody.put("firstname", newFirstname);

        Response response = RestAssured.given()
                .header("Cookie", "token=" + token)
                .body(patchBody)
                .when()
                .patch("/booking/{id}", bookingId)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        logger.info("Patched booking {} with new firstname: {}", bookingId, newFirstname);
    }

    @When("I delete the booking")
    public void iDeleteTheBooking() {
        Integer bookingId = testContext.getBookingId();
        String token = testContext.getAuthToken();
        
        Assert.assertNotNull(bookingId, "Booking ID should not be null");
        Assert.assertNotNull(token, "Auth token should not be null");

        Response response = RestAssured.given()
                .header("Cookie", "token=" + token)
                .when()
                .delete("/booking/{id}", bookingId)
                .then()
                .extract()
                .response();

        testContext.setResponse(response);
        // Clear booking ID since it's deleted
        testContext.setBookingId(null);
        logger.info("Deleted booking with ID: {}", bookingId);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Response response = testContext.getResponse();
        Assert.assertNotNull(response, "Response should not be null");
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
                "Status code mismatch");
        logger.info("Verified status code: {}", expectedStatusCode);
    }

    @Then("the response should contain firstname {string}")
    public void theResponseShouldContainFirstname(String expectedFirstname) {
        Response response = testContext.getResponse();
        String actualFirstname = response.path("firstname");
        
        if (actualFirstname == null) {
            actualFirstname = response.path("booking.firstname");
        }
        
        Assert.assertEquals(actualFirstname, expectedFirstname,
                "Firstname mismatch in response");
        logger.info("Verified firstname: {}", expectedFirstname);
    }

    @Then("the response should contain lastname {string}")
    public void theResponseShouldContainLastname(String expectedLastname) {
        Response response = testContext.getResponse();
        String actualLastname = response.path("lastname");
        
        if (actualLastname == null) {
            actualLastname = response.path("booking.lastname");
        }
        
        Assert.assertEquals(actualLastname, expectedLastname,
                "Lastname mismatch in response");
        logger.info("Verified lastname: {}", expectedLastname);
    }

    @And("the response should contain a booking ID")
    public void theResponseShouldContainABookingId() {
        Response response = testContext.getResponse();
        Integer bookingId = response.path("bookingid");
        Assert.assertNotNull(bookingId, "Booking ID should not be null in response");
        logger.info("Verified booking ID exists: {}", bookingId);
    }

    @Then("the response should contain multiple bookings")
    public void theResponseShouldContainMultipleBookings() {
        Response response = testContext.getResponse();
        String body = response.getBody().asString();
        Assert.assertTrue(body.contains("bookingid"), 
                "Response should contain booking IDs");
        logger.info("Verified response contains bookings");
    }
}
