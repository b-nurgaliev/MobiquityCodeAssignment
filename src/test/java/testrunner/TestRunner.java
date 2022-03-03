package testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features",
		glue = {"steps"},
		plugin={"pretty"},
		stepNotifications=true)
public class TestRunner {
}