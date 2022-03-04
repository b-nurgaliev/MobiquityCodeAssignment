package helpers;

import io.restassured.response.Response;
import pojo.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static constants.Constants.COMMENTS_URL;
import static constants.Constants.POSTID_QUERY;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CommentHelper {

	private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


	public Response getPostComments(int postId) {
		return given()
				.when()
				.get(COMMENTS_URL + POSTID_QUERY + postId);
	}

	public List<Comment> getCommentsList(ArrayList<Integer> postIds, List<Comment> foundComments) {
		for (int postId : postIds) {
			foundComments.addAll(Arrays.asList(getPostComments(postId).getBody().as(Comment[].class)));
		}
		return foundComments;
	}

	public void checkEmailFormat(ArrayList<String> emails) {
		for (String value : emails) {
			assertTrue(EMAIL_REGEX.matcher(value).matches());
		}
	}

	public ArrayList<String> getEmails(ArrayList<String> emails, List<Comment> foundComments) {
		for (int i = 0; i < foundComments.size(); i++) {
			emails.add(foundComments.get(i).getEmail());
		}
		return emails;
	}
}
