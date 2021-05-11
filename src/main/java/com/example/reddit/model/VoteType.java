package com.example.reddit.model;

public enum VoteType {
	
	UPVOTE(1), DOWNVOTE(-1),
	;
	
	private VoteType(int direction) {
	}
}
