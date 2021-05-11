package com.example.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.reddit.model.Post;
import com.example.reddit.model.Subreddit;
import com.example.reddit.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findByUser(User user);
	List<Post> findAllBySubreddit(Subreddit subreddit);
}
