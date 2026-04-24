package com.example.RA.controller;

import com.example.RA.model.AnalyzeRequest;
import com.example.RA.model.AnalyzeResponse;
import com.example.RA.service.GeminiService;
import com.example.RA.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ResumeController {


    private GeminiService geminiService;
    private final PdfService pdfService;

    public ResumeController(GeminiService geminiService, PdfService pdfService) {
        this.geminiService = geminiService;
        this.pdfService = pdfService;
    }

    @PostMapping(value = "/analyze-resume", consumes = "multipart/form-data")
    public AnalyzeResponse analyze(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobDescription") String jobDescription
    )
    {
        String resumeText = pdfService.extractText(file);

        AnalyzeRequest request = new AnalyzeRequest();
        request.setResume(resumeText);
        request.setJobDescription(jobDescription);

        return geminiService.analyzeResume(request);
    }
}