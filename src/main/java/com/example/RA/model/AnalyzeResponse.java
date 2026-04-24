package com.example.RA.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnalyzeResponse {
//    String message;
//    int resumeLength;
//    int jobDescriptionLength;
    private int matchPercentage;
    private int score;
    private List<String> skills;
    private List<String> missingSkills;
    private List<String> partial;
    private String message;
    private List<String> questions;

    // 👇 This will hold all extra fields
    private Map<String, Object> additional = new HashMap<>();

    @JsonAnySetter
    public void setAdditional(String key, Object value) {
        additional.put(key, value);
    }
}
