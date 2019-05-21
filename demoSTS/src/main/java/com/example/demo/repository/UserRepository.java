package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.User;
import java.lang.String;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
	public User findByUserId(String userid);
	public List<User> findAll();
	public boolean existsByUserId(String userid);
}
