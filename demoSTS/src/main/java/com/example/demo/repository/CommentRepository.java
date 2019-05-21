package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Post;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	public List<Comment> findByPost(Post post);
}
