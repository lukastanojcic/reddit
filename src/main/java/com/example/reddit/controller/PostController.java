package com.example.reddit.controller;

import com.example.reddit.dto.PostRequest;
import com.example.reddit.dto.PostResponse;
import com.example.reddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
		postService.save(postRequest);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
		return new ResponseEntity<PostResponse>(postService.getPost(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPosts(){
		return new ResponseEntity<List<PostResponse>>(postService.getAllPosts(), HttpStatus.OK);
	}
	@GetMapping("/by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long id){
		return new ResponseEntity<List<PostResponse>>(postService.getPostsBySubreddit(id), HttpStatus.OK);
	}
	@GetMapping("/by-user/{name}")
	public ResponseEntity<List<PostResponse>> getPostsByUsername(String username){
		return new ResponseEntity<List<PostResponse>>(postService.getPostsByUsername(username), HttpStatus.OK);
	}
}
