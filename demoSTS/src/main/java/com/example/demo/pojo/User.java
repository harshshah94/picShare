package com.example.demo.pojo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User implements Serializable {
	
	@Id
	private String userId;
	private String name;
	private String profilePicLink;
	private String description;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "user", cascade = CascadeType.ALL)
	private List<Friend> friends;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Post> posts;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Notification> notifications;
	
	public User(){
		
	}

	

	public User(String userId, String name, String profilePicLink, String description, List<Friend> friends,
			List<Post> posts, List<Notification> notifications) {
		super();
		this.userId = userId;
		this.name = name;
		this.profilePicLink = profilePicLink;
		this.description = description;
		this.friends = friends;
		this.posts = posts;
		this.notifications = notifications;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getProfilePicLink() {
		return profilePicLink;
	}



	public void setProfilePicLink(String profilePicLink) {
		this.profilePicLink = profilePicLink;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public List<Friend> getFriends() {
		return friends;
	}



	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}



	public List<Post> getPosts() {
		return posts;
	}



	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}



	public List<Notification> getNotifications() {
		return notifications;
	}



	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}



	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", profilePicLink=" + profilePicLink + ", description="
				+ description + ", friends=" + friends + ", posts=" + posts + ", notifications=" + notifications + "]";
	}



	
	
	
	}
