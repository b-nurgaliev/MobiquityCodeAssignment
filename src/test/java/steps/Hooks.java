package steps;

import helpers.JsonValidatorHelper;
import io.cucumber.java.BeforeAll;

public class Hooks {

	@BeforeAll
	public static void beforeTest() {
		JsonValidatorHelper.healthCheck();
	}
}
