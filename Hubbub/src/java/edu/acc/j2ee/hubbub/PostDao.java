package edu.acc.j2ee.hubbub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PostDao {
    private final List<Post> posts = new ArrayList<>();
    
    public PostDao() {}
    
    public Post post(Post post) {
        posts.add(post);
        return post;
    }
    
    public List<Post> getAllPosts(int offset, int length) {
        Collections.sort(posts,
            (a,b) -> b.getPosted().compareTo(a.getPosted()));
        return posts
                .stream()
                .skip(offset)
                .limit(length)
                .collect(Collectors.toList());
    }

}
