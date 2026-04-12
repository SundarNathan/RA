package com.example.RA.service;

import com.example.RA.model.AnalyzeRequest;
import com.example.RA.model.InterviewResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InterviewService {

    Map<String, List<String>> questionBank = new HashMap<>();

    Set<String> stopWords = new HashSet<>(Arrays.asList(
            "the", "and", "is", "a", "to", "for", "with", "in", "on", "of"
    ));
    List<String> skills = Arrays.asList(
            "java",
            "spring",
            "spring boot",
            "mysql",
            "docker",
            "kubernetes",
            "aws"
    );

    public InterviewResponse generateQuestions(AnalyzeRequest request){


        InterviewResponse response = new InterviewResponse();

        List<String> questions = new ArrayList<>();

        skills.sort((a,b)-> b.length()-a.length());
        String job = request.getJobDescription().toLowerCase();

        for (String skill : skills) {
            if (job.contains(skill) && questionBank.containsKey(skill)) {
                questions.addAll(questionBank.get(skill));
            }
        }

//        response.setQuestions(Arrays.asList(
//                "Tell me about your experience in " + request.getResume(),
//                "What do you know about " + request.getJobDescription() + "?",
//                "Explain REST APIs",
//                "What is dependency injection?",
//                "How do you handle exceptions?"
//        ));

        return response;
    }
}
