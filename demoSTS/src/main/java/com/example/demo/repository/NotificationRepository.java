package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.pojo.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

	public List<Notification> findByRecepientId(String recepientId);

	public Notification findByNotificationId(Long notificationId);
}
