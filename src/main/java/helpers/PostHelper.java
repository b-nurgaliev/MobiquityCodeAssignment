package helpers;

import pojo.Comment;
import pojo.Post;

import java.util.ArrayList;
import java.util.List;

public class PostHelper {

	public static ArrayList<String> getEmailList(Post[] posts) {
		return getEmails(getComments(getUserPostsIds(posts)));
	}

	private static ArrayList<Integer> getUserPostsIds(Post[] postList) {
		ArrayList<Integer> postIds = new ArrayList<>();
		for (Post post : postList) {
			postIds.add(post.getId());
		}
		return postIds;
	}

	private static List<Comment> getComments(ArrayList<Integer> postIds) {
		List<Comment> comments = new ArrayList<>();
		CommentHelper.getCommentsList(postIds, comments);
		return comments;
	}

	private static ArrayList<String> getEmails(List<Comment> comments) {
		ArrayList<String> emails = new ArrayList<>();
		CommentHelper.getEmails(emails, comments);
		return emails;
	}
}
