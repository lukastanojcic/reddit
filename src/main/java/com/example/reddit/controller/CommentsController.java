package com.example.reddit.controller;

import com.example.reddit.dto.CommentDto;
import com.example.reddit.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentsController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto){
		commentService.save(commentDto);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId){
		List<CommentDto> comments = commentService.getAllCommentsForPost(postId);
		return new ResponseEntity<List<CommentDto>>(comments, HttpStatus.OK);
	}

	@GetMapping("by-user/{username}")
	public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable String username){
		List<CommentDto> comments = commentService.getAllCommentsForUser(username);
		return new ResponseEntity<List<CommentDto>>(comments, HttpStatus.OK);
	}
}
