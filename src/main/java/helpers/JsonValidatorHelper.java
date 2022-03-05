package helpers;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonValidatorHelper {
	public void validateJsonSchema(String url, String jsonUrl) {
		given()
				.when()
				.get(url)
				.then()
				.assertThat()
				.body(matchesJsonSchemaInClasspath(jsonUrl));
	}
}
