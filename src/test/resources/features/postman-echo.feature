@echo @api
Feature: Postman Echo API Operations
  As an API tester
  I want to test various HTTP methods using Postman Echo
  So that I can validate request/response handling

  @smoke @get
  Scenario: Test GET request
    When I send a GET request to echo endpoint
    Then the echo response status code should be 200
    And the echo response should contain the request URL

  @smoke @post
  Scenario: Test POST request with JSON body
    Given I have a JSON payload with key "name" and value "TestUser"
    When I send a POST request to echo endpoint
    Then the echo response status code should be 200
    And the echo response should contain the posted data

  @regression @put
  Scenario: Test PUT request
    Given I have a JSON payload with key "updated" and value "true"
    When I send a PUT request to echo endpoint
    Then the echo response status code should be 200
    And the echo response should contain the posted data

  @regression @patch
  Scenario: Test PATCH request
    Given I have a JSON payload with key "patched" and value "true"
    When I send a PATCH request to echo endpoint
    Then the echo response status code should be 200
    And the echo response should contain the posted data

  @regression @delete
  Scenario: Test DELETE request
    When I send a DELETE request to echo endpoint
    Then the echo response status code should be 200

  @regression @headers
  Scenario: Test headers endpoint
    When I send a request to headers endpoint
    Then the echo response status code should be 200
    And the response should contain host header

  @regression @cookies
  Scenario: Test cookies endpoint
    When I send a request to cookies endpoint with cookie "session" and value "abc123"
    Then the echo response status code should be 200
    And the response should contain the cookie "session"

  @regression @status
  Scenario Outline: Test status code endpoint
    When I request status code <statusCode>
    Then the echo response status code should be <statusCode>

    Examples:
      | statusCode |
      | 200        |
      | 201        |
      | 400        |
      | 404        |
      | 500        |

  @regression @basicauth
  Scenario: Test basic authentication
    When I send a request with basic auth to echo endpoint
    Then the echo response status code should be 200
    And the response should confirm authentication
