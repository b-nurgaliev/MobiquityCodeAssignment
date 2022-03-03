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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


import static constants.Constants.*;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinition {

	private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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
				.get(USERS_URL + "/?username=Delphine");
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
				.get(POSTS_URL + "/?userId=" + userId);
		context.setContext("getPosts", getPosts);
	}

	@And("I get response body for all posts")
	public void iGetResponseBodyForAllPosts() {
		Response postBody = (Response) context.getContext("getPosts");
		Post[] posts = postBody
				.getBody().as(Post[].class);
		context.setContext("postBody", posts);
		assertTrue(Arrays.stream(posts)
				.allMatch(post -> post.getUserId() == (int) context.getContext("userId")));
	}

	public ArrayList<Integer> createUserId (ArrayList<Integer> postUserIds, Post[] posts){
		for (int i = 0; i < posts.length; i++) {
			postUserIds.add(posts[i].getUserId());
		}
		return postUserIds;
	}

	public void checkUserId (ArrayList<Integer> postUserIds){
		for (int e : postUserIds){
			assertEquals(e, context.getContext("userId"));
		}
	}

	public ArrayList<Integer> createPostIdsList (ArrayList<Integer> postIds, Post[] posts){
		for (int i = 0; i < posts.length; i++) {
			postIds.add(posts[i].getId());
		}
		return postIds;
	}

	@And("I create a list of posts")
	public void iCreateAListOfPosts() {
		Post[] postsList = (Post[]) context.getContext("postBody");
		ArrayList<Integer> postIds = new ArrayList<>();
		createPostIdsList(postIds, postsList);
		context.setContext("posts_ids", postIds);
	}

	@When("I create a list of all comments")
	public void iCreateAListOfAllComments() {
		ArrayList<Integer> postIds = (ArrayList<Integer>) context.getContext("posts_ids");
		List<Comment> foundComments = new ArrayList<>();
		getCommentsList(postIds, foundComments);
		context.setContext("found_comments", foundComments);
	}

	public Response getPostComments(int postId) {
		return given()
				.when()
				.get(COMMENTS_URL + POSTID_QUERY + postId);
	}

	public List<Comment> getCommentsList(ArrayList<Integer> postIds, List<Comment> foundComments){
		for (int postId : postIds){
			foundComments.addAll(Arrays.asList(getPostComments(postId).getBody().as(Comment[].class)));
		}
		return foundComments;
	}

	public ArrayList<String> getEmails(ArrayList<String> emails, List<Comment> foundComments) {
		for (int i = 0; i < foundComments.size(); i++) {
			emails.add(foundComments.get(i).getEmail());
		}
		return emails;
	}

	@And("I received list of emails")
	public void iReceivedListOfEmails() {
		List<Comment> foundComments = (List<Comment>) context.getContext("found_comments");
		ArrayList<String> emails = new ArrayList<>();
		getEmails(emails, foundComments);
		context.setContext("get_emails", emails);
	}

	public void checkEmailFormat(ArrayList<String> emails) {
		for (String value : emails) {
			assertTrue(EMAIL_REGEX.matcher(value).matches());
		}
	}

	@Then("I validate emails")
	public void iValidateEmails() {
		ArrayList<String> emails = (ArrayList<String>) context.getContext("get_emails");
		checkEmailFormat(emails);
	}

}
