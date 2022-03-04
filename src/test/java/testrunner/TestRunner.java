package testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features",
		glue = {"steps"},
		plugin = {"pretty", "html:target/cucumber", "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"},
		stepNotifications = true)
public class TestRunner {
}