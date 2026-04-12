package com.example.RA.controller;

import com.example.RA.model.AnalyzeRequest;
import com.example.RA.model.InterviewResponse;
import com.example.RA.service.InterviewService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterviewController {
    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService)
    {
        this.interviewService = interviewService;
    }

    @PostMapping("/interview")
    public InterviewResponse interview(@RequestBody AnalyzeRequest request)
    {
        return interviewService.generateQuestions(request);
    }

}
