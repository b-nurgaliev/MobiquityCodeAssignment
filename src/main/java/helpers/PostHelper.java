package helpers;

import pojo.Post;

import java.util.ArrayList;

public class PostHelper {

	public static void createPostIdsList(ArrayList<Integer> postIds, Post[] posts) {
		for (Post post : posts) {
			postIds.add(post.getId());
		}
	}
}
