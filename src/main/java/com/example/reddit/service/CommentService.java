package com.example.reddit.service;

import com.example.reddit.dto.CommentDto;
import com.example.reddit.exception.PostNotFoundException;
import com.example.reddit.mapper.CommentMapper;
import com.example.reddit.model.Comment;
import com.example.reddit.model.NotificationEmail;
import com.example.reddit.model.Post;
import com.example.reddit.model.User;
import com.example.reddit.repository.CommentRepository;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
	private static final String POST_URL = "";
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;

	public void save(CommentDto commentDto){
		Post post = postRepository.findById(commentDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
		Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
		commentRepository.save(comment);

		String message = mailContentBuilder.build(authService.getCurrentUser() + " posted a comment on your post." + POST_URL);
		sendCommentNotification(message, post.getUser());
	}

	public void sendCommentNotification(String message, User user) {
		mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));
	}

	public List<CommentDto> getAllCommentsForPost(Long postId){
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException(postId.toString()));
		return commentRepository.findByPost(post)
				.stream()
				.map(commentMapper::mapToDto)
				.collect(Collectors.toList());
	}

	public List<CommentDto> getAllCommentsForUser(String username){
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return commentRepository.findByUser(user)
				.stream()
				.map(commentMapper::mapToDto)
				.collect(Collectors.toList());
	}
}
