@health @api
Feature: Health Check API
  As an API consumer
  I want to verify the API is healthy
  So that I can confirm the service is available

  @smoke @ping
  Scenario: Verify API health check endpoint
    When I call the health check endpoint
    Then the response status code should be 201
    And the response body should be "Created"
