package steps;

import context.Context;
import helpers.CommentHelper;
import helpers.PostHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.Comment;
import pojo.Post;
import pojo.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static constants.Constants.*;
import static constants.ContextConstants.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinition {

	CommentHelper commentHelper = new CommentHelper();
	PostHelper postHelper = new PostHelper();

	Context context;

	public StepDefinition(Context context) {
		this.context = context;
	}

	@When("^I send GET users request$")
	public void getRawResponse() {
		Response response = given()
				.when()
				.contentType(ContentType.JSON)
				.get(USERS_URL);
		context.setContext(RAW_RESPONSE, response);
	}

	@Then("Status code is {int}")
	public void statusCodeIs(int statusCode) {
		Response response = (Response) context.getContext(RAW_RESPONSE);
		assertEquals(response.getStatusCode(), statusCode);
	}

	@And("Unique user {string} is found")
	public void uniqueUserIsFound(String username) {
		Response response = (Response) context.getContext(RAW_RESPONSE);
		User[] users = response
				.getBody().as(User[].class);
		assertEquals(1, (int) Arrays.stream(users)
				.filter(user -> user.getUsername().equals(username)).count());
	}

	@And("I collect users ID")
	public void iCollectUsersID() {
		Response getDelphine = given()
				.when()
				.contentType(ContentType.JSON)
				.get(USERS_URL + USER_NAME + "Delphine");
		User[] users = getDelphine
				.getBody().as(User[].class);
		int rawUserIdResponse = users[0].getId();
		context.setContext(RAW_USER_ID_RESPONSE, rawUserIdResponse);
	}

	@And("I get posts of user by his ID")
	public void iGetPostsOfUserByHisID() {
		int rawUserIdResponse = (int) context.getContext(RAW_USER_ID_RESPONSE);
		Response rawPostResponse = given()
				.when()
				.contentType(ContentType.JSON)
				.get(POSTS_URL + USER_ID + rawUserIdResponse);
		context.setContext(RAW_POST_RESPONSE, rawPostResponse);
	}

	@And("All posts have correct user id")
	public void iGetResponseBodyForAllPosts() {
		Response rawPostBodyResponse = (Response) context.getContext(RAW_POST_RESPONSE);
		Post[] posts = rawPostBodyResponse
				.getBody().as(Post[].class);
		context.setContext(RAW_POST_BODY_RESPONSE, posts);
		assertTrue(Arrays.stream(posts)
				.allMatch(post -> post.getUserId() == (int) context.getContext(RAW_USER_ID_RESPONSE)));
	}

	@And("I create a list of posts")
	public void iCreateAListOfPosts() {
		Post[] postsList = (Post[]) context.getContext(RAW_POST_BODY_RESPONSE);
		ArrayList<Integer> postIds = new ArrayList<>();
		postHelper.createPostIdsList(postIds, postsList);
		context.setContext(RAW_POST_ID_RESPONSE, postIds);
	}

	@When("I create a list of all comments")
	public void iCreateAListOfAllComments() {
		ArrayList<Integer> postIds = (ArrayList<Integer>) context.getContext(RAW_POST_ID_RESPONSE);
		List<Comment> rawFoundCommentsResponse = new ArrayList<>();
		commentHelper.getCommentsList(postIds, rawFoundCommentsResponse);
		context.setContext(RAW_FOUND_COMMENTS_RESPONSE, rawFoundCommentsResponse);
	}

	@And("I received list of emails")
	public void iReceivedListOfEmails() {
		List<Comment> rawFoundCommentsResponse = (List<Comment>) context.getContext(RAW_FOUND_COMMENTS_RESPONSE);
		ArrayList<String> emails = new ArrayList<>();
		commentHelper.getEmails(emails, rawFoundCommentsResponse);
		context.setContext(RAW_GET_EMAIL_RESPONSE, emails);
	}

	@Then("All comments have valid emails")
	public void iValidateEmails() {
		ArrayList<String> emails = (ArrayList<String>) context.getContext(RAW_GET_EMAIL_RESPONSE);
		commentHelper.checkEmailFormat(emails);
	}

}