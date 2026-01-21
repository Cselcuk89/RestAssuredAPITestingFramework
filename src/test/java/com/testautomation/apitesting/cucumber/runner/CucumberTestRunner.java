package com.testautomation.apitesting.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Cucumber TestNG runner for executing BDD scenarios.
 * This follows industry best practices for organizing Cucumber tests with TestNG.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.testautomation.apitesting.cucumber.steps",
                "com.testautomation.apitesting.cucumber.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        tags = "not @ignore"
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
