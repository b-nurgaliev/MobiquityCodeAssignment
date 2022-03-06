package steps;

import context.Context;
import helpers.CommentHelper;
import helpers.PostHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.Post;
import pojo.User;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static constants.Constants.*;
import static constants.ContextConstants.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinition {

	Context context;

	public StepDefinition(Context context) {
		this.context = context;
	}

	@When("I send GET users request")
	public void getUsersResponse() {
		Response response = given()
				.filter(new AllureRestAssured())
				.when()
				.contentType(ContentType.JSON)
				.get(USERS_URL);
		context.setContext(RESPONSE, response);
	}

	@Then("Status code is {int}")
	public void statusCodeIs(int statusCode) {
		Response response = (Response) context.getContext(RESPONSE);
		assertEquals(response.getStatusCode(), statusCode);
	}

	@And("Unique user {string} is found")
	public void uniqueUserIsFound(String username) {
		Response response = (Response) context.getContext(RESPONSE);
		User[] users = response
				.getBody().as(User[].class);
		List<User> userResult = Arrays.stream(users)
				.filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
		assertEquals(1, userResult.size());
		context.setContext(USER_ID, userResult.get(0).getId());
	}

	@And("I send GET posts request")
	public void getPostsByUserId() {
		int userId = (Integer) context.getContext(USER_ID);
		Response userPostsResponse = given()
				.filter(new AllureRestAssured())
				.when()
				.contentType(ContentType.JSON)
				.get(POSTS_URL + POSTS_USER_ID_QUERY + userId);
		context.setContext(RESPONSE, userPostsResponse);
	}

	@And("All posts have correct user id")
	public void checkPostsUserId() {
		Response postsResponse = (Response) context.getContext(RESPONSE);
		Post[] posts = postsResponse
				.getBody().as(Post[].class);
		assertTrue(Arrays.stream(posts)
				.allMatch(post -> post.getUserId() == (Integer) context.getContext(USER_ID)));
		context.setContext(POSTS, posts);
	}

	@Then("All comments have valid emails")
	public void checkEmails() {
		Post[] postsList = (Post[]) context.getContext(POSTS);
		CommentHelper.checkEmailFormat(PostHelper.getEmailList(postsList));
	}

	@When("I request user by username {string}")
	public void getUserByUsername(String username) {
		Response response = given()
				.filter(new AllureRestAssured())
				.when()
				.contentType(ContentType.JSON)
				.get(USERS_URL + USER_NAME_QUERY + username);
		context.setContext(RESPONSE, response);
	}

	@When("I request user by id {string}")
	public void getUserById(String userid) {
		Response response = given()
				.filter(new AllureRestAssured())
				.when()
				.contentType(ContentType.JSON)
				.get(USERS_URL + USER_ID_QUERY + userid);
		context.setContext(RESPONSE, response);
	}
}