@booking @api
Feature: Booking API Operations
  As an API consumer
  I want to be able to manage hotel bookings
  So that I can create, read, update, and delete reservations

  Background:
    Given I am authenticated as admin user

  @smoke @create
  Scenario: Create a new booking successfully
    Given I have a booking with firstname "John" and lastname "Doe"
    When I create the booking
    Then the response status code should be 200
    And the response should contain a booking ID
    And the response should contain firstname "John"
    And the response should contain lastname "Doe"

  @smoke @read
  Scenario: Retrieve a booking by ID
    Given I have a booking with firstname "Jane" and lastname "Smith"
    When I create the booking
    And I retrieve the booking by ID
    Then the response status code should be 200
    And the response should contain firstname "Jane"
    And the response should contain lastname "Smith"

  @smoke @read
  Scenario: Get all bookings
    When I retrieve all bookings
    Then the response status code should be 200
    And the response should contain multiple bookings

  @regression @update
  Scenario: Update an existing booking
    Given I have a booking with firstname "Original" and lastname "Name"
    When I create the booking
    And I update the booking with firstname "Updated"
    Then the response status code should be 200
    And the response should contain firstname "Updated"

  @regression @patch
  Scenario: Partially update a booking
    Given I have a booking with firstname "Partial" and lastname "Update"
    When I create the booking
    And I partially update the booking with firstname "Patched"
    Then the response status code should be 200
    And the response should contain firstname "Patched"

  @regression @delete
  Scenario: Delete a booking
    Given I have a booking with firstname "ToDelete" and lastname "User"
    When I create the booking
    And I delete the booking
    Then the response status code should be 201

  @e2e @endtoend
  Scenario: End to end booking workflow
    Given I have a booking with firstname "E2E" and lastname "Test"
    When I create the booking
    Then the response status code should be 200
    When I retrieve the booking by ID
    Then the response should contain firstname "E2E"
    When I update the booking with firstname "Modified"
    Then the response status code should be 200
    When I partially update the booking with firstname "Final"
    Then the response status code should be 200
    And the response should contain firstname "Final"
    When I delete the booking
    Then the response status code should be 201
