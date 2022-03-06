package helpers;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonValidatorHelper {

	private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users";
	private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
	private static final String COMMENTS_URL = "https://jsonplaceholder.typicode.com/comments";
	private static final String USERS_JSON_PATH = "users.json";
	private static final String POSTS_JSON_PATH = "posts.json";
	private static final String COMMENTS_JSON_PATH = "comments.json";

	public static void healthCheck() {
		validateJsonSchema(POSTS_URL, POSTS_JSON_PATH);
		validateJsonSchema(USERS_URL, USERS_JSON_PATH);
		validateJsonSchema(COMMENTS_URL, COMMENTS_JSON_PATH);
	}

	private static void validateJsonSchema(String url, String jsonUrl) {
		given()
				.when()
				.get(url)
				.then()
				.assertThat()
				.body(matchesJsonSchemaInClasspath(jsonUrl));
	}
}
