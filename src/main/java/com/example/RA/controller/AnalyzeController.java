package com.example.RA.controller;

import com.example.RA.model.AnalyzeRequest;
import com.example.RA.model.AnalyzeResponse;
import com.example.RA.service.AnalyzeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyzeController {

    private final AnalyzeService analyzeService;
    public AnalyzeController(AnalyzeService analyzeService)
    {
        this.analyzeService= analyzeService;
    }
    @PostMapping("/analyze")
    public AnalyzeResponse analyze(@RequestBody AnalyzeRequest analyzeRequest)
    {
        return analyzeService.analyze(analyzeRequest);
    }
}
