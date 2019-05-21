package com.example.demo.pojo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserFriendId implements Serializable {



	
	private User user;
	
	
	private String friendId;
}
