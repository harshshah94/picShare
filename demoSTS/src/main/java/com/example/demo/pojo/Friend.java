package com.example.demo.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(UserFriendId.class)
public class Friend implements Serializable{

	@Id
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@Id
	private String friendId;

	public Friend() {
		// TODO Auto-generated constructor stub
	}

	public Friend(User user, String friendId) {
		super();
		this.user = user;
		this.friendId = friendId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	@Override
	public String toString() {
		return "Friend [friendId=" + friendId + "]";
	}
	
	
}
