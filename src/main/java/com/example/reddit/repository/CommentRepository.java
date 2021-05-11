package com.example.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reddit.model.Comment;
import com.example.reddit.model.Post;
import com.example.reddit.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByPost(Post post);
	List<Comment> findByUser(User user);
}
