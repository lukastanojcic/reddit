package com.example.reddit.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.reddit.model.User;
import com.example.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opt = userRepository.findByUsername(username);
		User user = opt
				.orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
		return new org.springframework.security
				.core.userdetails.User(user.getUsername(), user.getPassword(), 
						user.isEnabled(), true, true, true, getAuthorities("USER"));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role){
		return singletonList(new SimpleGrantedAuthority(role));
	}
}
