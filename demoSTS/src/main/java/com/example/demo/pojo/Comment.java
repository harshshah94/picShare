package com.example.demo.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Comment implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long commentId;
	
	private String userId;
	
	private String userName;
	
	private String senderId;
	
    private String commentText;

    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date commentTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;
    
    public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Comment(String userId,String userName,String senderId, String commentText, Date commentTime, Post post) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.commentText = commentText;
		this.commentTime = commentTime;
		this.post = post;
		this.senderId = senderId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
	
    
}
