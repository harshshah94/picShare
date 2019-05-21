package com.example.demo.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Post implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long postId;
    private String postImageLink;
    private String postText;
    private String postAudio;
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments;
    
    @ManyToOne
	@JoinColumn(name = "userId")
	private User user;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date postTime;
    
    private boolean isDeleted;

    public Post() {
	}
    
    public Post(Long postId) {
    	this.postId = postId;
	}
    
	public Post( String postImageLink, String postText, String postAudio, List<Comment> comments, User user,
			Date postTime, boolean isDeleted) {
		super();
		this.postImageLink = postImageLink;
		this.postText = postText;
		this.postAudio = postAudio;
		this.comments = comments;
		this.user = user;
		this.postTime = postTime;
		this.isDeleted = isDeleted;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getPostImageLink() {
		return postImageLink;
	}

	public void setPostImageLink(String postImageLink) {
		this.postImageLink = postImageLink;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public String getPostAudio() {
		return postAudio;
	}

	public void setPostAudio(String postAudio) {
		this.postAudio = postAudio;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", postImageLink=" + postImageLink + ", postText=" + postText + ", postAudio="
				+ postAudio + ", comments=" + comments + ", user=" + user + ", postTime=" + postTime + ", isDeleted="
				+ isDeleted + "]";
	} 
    
	
    
}
