#### Rest Assured API Automation Testing Framework

## Framework Features

### Industry Best Practices Implemented
- **Cucumber BDD Layer**: Feature files with Gherkin syntax for readable test scenarios
- **Builder Pattern**: `ApiClient` class for flexible request configuration
- **Page Object Model equivalent**: Centralized API endpoints in `Endpoints` class
- **Environment Configuration**: Multi-environment support (dev, staging, prod)
- **Custom Exceptions**: `ApiException` for better error handling and debugging
- **Response Validation**: Fluent `ResponseValidator` utility for clean assertions
- **Test Context**: Shared state management for Cucumber scenarios
- **Allure Reporting**: Integrated reporting for both TestNG and Cucumber tests
- **Retry Mechanism**: Built-in test retry for flaky tests

### Supported APIs

#### Restful Booker API (https://restful-booker.herokuapp.com)
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/auth` | POST | Create authentication token |
| `/booking` | GET | Get all booking IDs |
| `/booking` | POST | Create a new booking |
| `/booking/{id}` | GET | Get booking by ID |
| `/booking/{id}` | PUT | Update booking |
| `/booking/{id}` | PATCH | Partial update booking |
| `/booking/{id}` | DELETE | Delete booking |
| `/ping` | GET | Health check endpoint |

#### Postman Echo API (http://postman-echo.com)
| Endpoint | Method | Description |
|----------|--------|-------------|
| `/get` | GET | Echo GET request |
| `/post` | POST | Echo POST request with body |
| `/put` | PUT | Echo PUT request |
| `/patch` | PATCH | Echo PATCH request |
| `/delete` | DELETE | Echo DELETE request |
| `/headers` | GET | Get request headers |
| `/cookies` | GET | Get request cookies |
| `/cookies/set` | GET | Set cookies |
| `/status/{code}` | GET | Return specific status code |
| `/basic-auth` | GET | Test basic authentication |
| `/ip` | GET | Get client IP |

### Project Structure
```
src/test/java/com/testautomation/apitesting/
├── client/              # API client abstraction
│   └── ApiClient.java   # Builder pattern for API requests
├── cucumber/            # Cucumber BDD layer
│   ├── context/         # Test context for state management
│   ├── hooks/           # Setup/teardown hooks
│   ├── runner/          # TestNG Cucumber runner
│   └── steps/           # Step definitions
│       ├── BookingSteps.java      # Booking API steps
│       ├── HealthCheckSteps.java  # Health check steps
│       └── PostmanEchoSteps.java  # Postman Echo steps
├── exceptions/          # Custom exceptions
├── listener/            # TestNG listeners
├── pojos/               # Request/Response models
│   ├── Auth.java
│   ├── Booking.java
│   ├── BookingDates.java
│   ├── EchoResponse.java
│   ├── CookiesResponse.java
│   ├── HeadersResponse.java
│   └── IpResponse.java
├── tests/               # TestNG test classes
│   ├── HealthCheckTest.java
│   ├── PostmanEchoTest.java
│   └── ...
└── utils/               # Utilities and helpers

src/test/resources/
├── features/            # Cucumber feature files
│   ├── booking.feature
│   ├── healthcheck.feature
│   └── postman-echo.feature
├── config.properties    # Configuration properties
└── ...                  # Test data files
```

### Running Tests

#### Run All Tests
```bash
mvn test -Dsuitefilename=suites/all-tests-suite.xml
```

#### Run Cucumber BDD Tests
```bash
mvn test -Dsuitefilename=suites/cucumber-suite.xml
```

#### Run Postman Echo Tests
```bash
mvn test -Dsuitefilename=suites/postman-echo-suite.xml
```

#### Run Health Check Tests
```bash
mvn test -Dsuitefilename=suites/healthcheck-suite.xml
```

#### Run with specific tags
```bash
mvn test -Dcucumber.filter.tags="@smoke" -Dsuitefilename=suites/cucumber-suite.xml
```

#### Run with specific environment
```bash
mvn test -Denv=staging -Dsuitefilename=suites/cucumber-suite.xml
```

#### Run TestNG Tests
```bash
mvn test -Dsuitefilename=testng.xml
```

---

* ## API Automation Testing Using Rest Assured Full Course Part-01 - https://www.youtube.com/watch?v=o9KJhGHl49M&list=PLUeDIlio4THGL7lQXQwxsV9re_i0U2b0Q&index=3
* ## API Automation Testing Using Rest Assured Full Course Part-02 - https://www.youtube.com/watch?v=kay86__5eTg&list=PLUeDIlio4THGL7lQXQwxsV9re_i0U2b0Q&index=4

*   ### How to Run Tests Based on Yes or No Flag from Excel File in Automation Testing Chapter-12 - https://www.youtube.com/watch?v=0eTq3QJ0Xt8&list=PLUeDIlio4THGL7lQXQwxsV9re_i0U2b0Q&index=5
*   ### How to Retry ReRun Steps in Automation Testing Framework Chapter-13 - https://www.youtube.com/watch?v=MwY4VP3lQ7I&list=PLUeDIlio4THGL7lQXQwxsV9re_i0U2b0Q&index=6
*   ### How to Create Dynamic API Request Body in API Automation Testing Framework Chapter-14 - https://www.youtube.com/watch?v=cR3m3Ua7nII&list=PLUeDIlio4THGL7lQXQwxsV9re_i0U2b0Q&index=7
*   ###  How to Run Multiple TestNG Suite Files at One Click Chapter-15  - https://www.youtube.com/watch?v=2QfqIjrhiOw&list=PLUeDIlio4THGL7lQXQwxsV9re_i0U2b0Q&index=8
*   ###  How to ReRun Retry Failed Test Programmatically in Automation Testing Framework Chapter 16 - https://www.youtube.com/watch?v=iCoWZDLCkRw&list=PLUeDIlio4THGL7lQXQwxsV9re_i0U2b0Q&index=9
*   ### How to Upload File in Rest Assured API Automation Testing Tutorial Chapter-17 - https://www.youtube.com/watch?v=mN-tr73ZNtQ&list=PLUeDIlio4THGL7lQXQwxsV9re_i0U2b0Q&index=10
*   ### Cookies in Rest Assured API Automation Testing Tutorial Chapter-18 - https://www.youtube.com/watch?v=A-np5dftKnI&list=PLUeDIlio4THGL7lQXQwxsV9re_i0U2b0Q&index=11
*   ###  Basic Auth in Rest Assured API Automation Testing Tutorial Chapter-19  - https://www.youtube.com/watch?v=Q7CV5_y9kEE&list=PLUeDIlio4THGL7lQXQwxsV9re_i0U2b0Q&index=12 
=======================================================================

* #### API Testing Using Postman Full Course - https://www.youtube.com/watch?v=QKBa8lt5Wfo&list=PLUeDIlio4THGcgNP3_Ocb_I_l4ITai5QT&index=1
* #### Postman Collections by Testers Talk - https://www.youtube.com/watch?v=HNtgl4KHW7k&list=PLUeDIlio4THGcgNP3_Ocb_I_l4ITai5QT&index=10

=======================================================================
* #### How to Learn API Testing? Here is a quick guide - https://www.youtube.com/watch?v=ABqR45MLw5c&list=PLUeDIlio4THGcgNP3_Ocb_I_l4ITai5QT&index=16
=======================================================================
