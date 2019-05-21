package com.example.demo.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Notification {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long notificationId;
	
	private boolean isRead;
	
	private String recepientId;
	
	private String senderId;
	
	private String notificationUrl;
	
	private Long postId;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
	private Date notificationTime;
	
	public Notification() {
		// TODO Auto-generated constructor stub
	}

	public Notification(boolean isRead, String recepientId,Long postId, String senderId, String notificationUrl,
			Date notificationTime) {
		super();
		this.isRead = isRead;
		this.recepientId = recepientId;
		this.senderId = senderId;
		this.postId = postId;
		this.notificationUrl = notificationUrl;
		this.notificationTime = notificationTime;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getRecepientId() {
		return recepientId;
	}

	public void setRecepientId(String recepientId) {
		this.recepientId = recepientId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getNotificationUrl() {
		return notificationUrl;
	}

	public void setNotificationUrl(String notificationUrl) {
		this.notificationUrl = notificationUrl;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Date getNotificationTime() {
		return notificationTime;
	}

	public void setNotificationTime(Date notificationTime) {
		this.notificationTime = notificationTime;
	}

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	@Override
	public String toString() {
		return "Notification [notificationId=" + notificationId + ", isRead=" + isRead + ", recepientId=" + recepientId
				+ ", senderId=" + senderId + ", notificationUrl=" + notificationUrl + ", postId=" + postId
				+ ", notificationTime=" + notificationTime + "]";
	}

	
	
	
	
	

}
