package com.springmvc.repo;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.springmvc.pojo.Spitter;

public interface ChatRepository {
	
	// ajax request for user list.
	List<String> checkoutUserlist(); 
	
	@CachePut("smackCache")
	Spitter save(Spitter spitter);

	@Cacheable("smackCache")
	Spitter findByUsername(String username);

	String findPassByUsername(String username);
	
	int getItemSum();
	
	List<Spitter> findSpitters(int limit, int offset);
	
	@CacheEvict("smackCache") // highlight line.

	int delete(String username);
	
	
}
