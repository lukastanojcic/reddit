package com.example.reddit.service;

import com.example.reddit.dto.PostRequest;
import com.example.reddit.dto.PostResponse;
import com.example.reddit.exception.PostNotFoundException;
import com.example.reddit.exception.SubredditNotFoundException;
import com.example.reddit.mapper.PostMapper;
import com.example.reddit.model.Post;
import com.example.reddit.model.Subreddit;
import com.example.reddit.model.User;
import com.example.reddit.repository.PostRepository;
import com.example.reddit.repository.SubredditRepository;
import com.example.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public void save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		User currentUser = authService.getCurrentUser();
		System.out.println("SUBREDDIT: " + subreddit.getName() + ", " + subreddit.getDescription());
		System.out.println("USER: " + currentUser.getUsername() + ", " + currentUser.getPassword());
		Post post = postMapper.map(postRequest, subreddit, currentUser);
		System.out.println("MAPPED POST: " + post.getPostName() + ", " + post.getUser().getUsername() + ", " + post.getSubreddit().getName());
		postRepository.save(post);
	}
	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}
	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll()
				.stream()
				.map(postMapper::mapToDto)
				.collect(Collectors.toList());
	}
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		Subreddit subreddit = subredditRepository.findById(subredditId)
				.orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
		List<Post> posts = postRepository.findAllBySubreddit(subreddit);
		return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
	}
	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return postRepository.findByUser(user)
				.stream()
				.map(postMapper::mapToDto)
				.collect(Collectors.toList());
	}
}
