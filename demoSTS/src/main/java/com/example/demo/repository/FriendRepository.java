package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.pojo.Friend;
import com.example.demo.pojo.User;

@Repository
public interface FriendRepository extends CrudRepository<Friend, String>{
	List<Friend> findByUser(User user);
}
