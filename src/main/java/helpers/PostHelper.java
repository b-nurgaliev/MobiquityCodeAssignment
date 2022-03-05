package helpers;

import pojo.Post;

import java.util.ArrayList;

public class PostHelper extends JsonValidatorHelper {

	public ArrayList<Integer> createPostIdsList(ArrayList<Integer> postIds, Post[] posts) {
		for (Post post : posts) {
			postIds.add(post.getId());
		}
		return postIds;
	}
}
