package steps;

import context.Context;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.User;

import java.util.Arrays;
import java.util.stream.Collectors;

import static constants.Constants.URL;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinition {

	Context context;

	public StepDefinition(Context context) {
		this.context = context;
	}

	@When("^I send GET users request$")
	public void getRawResponse() {
		Response response = given()
				.when()
				.contentType(ContentType.JSON)
				.get(URL + "/users");
		context.setContext("rawResponse", response);
	}

	@Then("Status code is {int}")
	public void statusCodeIs(int statusCode) {
		Response response = (Response) context.getContext("rawResponse");
		assertEquals(response.getStatusCode(), statusCode);
	}

	@And("Unique user {string} is found")
	public void uniqueUserIsFound(String username) {
		Response response = (Response) context.getContext("rawResponse");
		User[] users = response
				.getBody().as(User[].class);
		assertEquals(1, (int) Arrays.stream(users)
				.filter(user -> user.getUsername().equals(username)).count());
	}
}
