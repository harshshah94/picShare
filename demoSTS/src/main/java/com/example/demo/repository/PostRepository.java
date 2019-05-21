package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.pojo.Post;
import com.example.demo.pojo.User;

public interface PostRepository extends CrudRepository<Post, Long> {
	
	public Post findByPostId(Long postId);
	public List<Post> findAll();
	public List<Post> findByUser(User user);
	public Post findByPostImageLink(String imageLink);
}
