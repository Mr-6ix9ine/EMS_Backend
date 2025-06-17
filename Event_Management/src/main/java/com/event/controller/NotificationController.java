package com.event.controller;

import com.event.entity.Notification;
import com.event.service.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationServiceImpl notificationService;

    @PostMapping
    public Notification createNotification(@RequestParam Long userId, @RequestParam Long eventId, @RequestParam String message) {
        return notificationService.sendNotification(userId, eventId, message);
    }

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId);
    }
}
