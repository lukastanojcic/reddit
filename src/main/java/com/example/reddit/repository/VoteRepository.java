package com.example.reddit.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.reddit.model.Post;
import com.example.reddit.model.User;
import com.example.reddit.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{
	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentuser);
}
