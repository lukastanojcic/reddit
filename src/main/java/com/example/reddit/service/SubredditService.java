package com.example.reddit.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.reddit.dto.SubredditDto;
import com.example.reddit.exception.SpringRedditException;
import com.example.reddit.mapper.SubredditMapper;
import com.example.reddit.model.Subreddit;
import com.example.reddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;
	
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
		subredditDto.setId(save.getId());
		subredditRepository.save(save);
		return subredditDto;
	}
	
	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll()
				.stream()
				.map(subredditMapper::mapSubredditToDto)
				.collect(Collectors.toList());
	}

	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
		return subredditMapper.mapSubredditToDto(subreddit);
	}
	
	
	
}
