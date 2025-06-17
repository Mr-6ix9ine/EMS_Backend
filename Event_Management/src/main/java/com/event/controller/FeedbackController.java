package com.event.controller;

import com.event.entity.Feedback;
import com.event.service.FeedbackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")

public class FeedbackController {

    @Autowired
    private FeedbackServiceImpl feedbackService;

    @PostMapping
    public Feedback submitFeedback(@RequestParam Long userId, @RequestParam Long eventId,
                                   @RequestParam int rating, @RequestParam String comments) {
        return feedbackService.submitFeedback(userId, eventId, rating, comments);
    }

    @GetMapping("/event/{eventId}")
    public List<Feedback> getEventFeedback(@PathVariable Long eventId) {
        return feedbackService.getEventFeedback(eventId);
    }

    @GetMapping("/user/{userId}")
    public List<Feedback> getUserFeedback(@PathVariable Long userId) {
        return feedbackService.getUserFeedback(userId);
    }

    @GetMapping("/event-rating/{eventId}")
    public double getAverageRating(@PathVariable Long eventId) {
        return feedbackService.calculateAverageRating(eventId);
    }
}
