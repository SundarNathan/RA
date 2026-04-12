package com.example.RA.model;

import lombok.Data;

import java.util.List;

@Data
public class AnalyzeResponse {
//    String message;
//    int resumeLength;
//    int jobDescriptionLength;
    private int matchPercentage;
    private List<String> missingSkills;
    private List<String> partial;
    private String message;
    private List<String> questions;
}
