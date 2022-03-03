package steps;

import context.Context;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import io.restassured.response.Response;
import pojo.Comment;
import pojo.Post;
import pojo.User;

import java.util.Arrays;


import static constants.Constants.URL;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

	@And("I collect users ID")
	public void iCollectUsersID() {
//		Response response = (Response) context.getContext("rawResponse");
		Response getDelphine = given()
				.when()
				.contentType(ContentType.JSON)
				.get(URL + "/users/?username=Delphine");
		User[] users = getDelphine
				.getBody().as(User[].class);
		int userId = users[0].getId();
		context.setContext("userId", userId);
	}

	@And("I get posts of user by his ID")
	public void iGetPostsOfUserByHisID() {
		int userId = (int) context.getContext("userId");
		Response getPosts = given()
				.when()
				.contentType(ContentType.JSON)
				.get(URL + "/posts/?userId=" + userId);
		context.setContext("getPosts", getPosts);
	}

	@And("I get response body for all posts")
	public void iGetResponseBodyForAllPosts() {
		Response postBody = (Response) context.getContext("getPosts");
		Post[] posts = postBody
				.getBody().as(Post[].class);
		context.setContext("postBody", posts);
	}

	@When("I create a list of all comments")
	public void iCreateAListOfAllComments() {
		Response commentBody = (Response) context.getContext("postBody");
		Comment[] comments = commentBody
				.getBody().as(Comment[].class);
		context.setContext("commentBody", comments);

		int i = 0;
	}
}
