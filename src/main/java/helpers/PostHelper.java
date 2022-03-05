package helpers;

import pojo.Post;

import java.util.ArrayList;

public class PostHelper extends JsonValidatorHelper{

	public ArrayList<Integer> createPostIdsList (ArrayList<Integer> postIds, Post[] posts){
		for (int i = 0; i < posts.length; i++) {
			postIds.add(posts[i].getId());
		}
		return postIds;
	}
}
