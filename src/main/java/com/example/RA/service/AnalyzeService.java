package com.example.RA.service;

import com.example.RA.model.AnalyzeRequest;
import com.example.RA.model.AnalyzeResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyzeService {

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
    Map<String, List<String>> questionBank = new HashMap<>();

    public AnalyzeResponse analyze(AnalyzeRequest request)
    {

        questionBank.put("java", Arrays.asList(
                "What is JVM?",
                "Explain OOP concepts",
                "What is multithreading?"
        ));

        questionBank.put("spring", Arrays.asList(
                "What is Dependency Injection?",
                "What is Spring Boot?"
        ));

        questionBank.put("docker", Arrays.asList(
                "What is Docker?",
                "Difference between VM and container?"
        ));

        AnalyzeResponse response = new AnalyzeResponse();

        // Convert to lowercase for comparison
        String resume = request.getResume().toLowerCase();
        String job = request.getJobDescription().toLowerCase();

        // Convert JD into words
//        Set<String> jobWords = new HashSet<>(Arrays.asList(job.split(" ")));

        int matchCount = 0;
        List<String> missing = new ArrayList<>();
        List<String> partialMatches = new ArrayList<>();
        List<String> questions = new ArrayList<>();

        skills.sort((a,b)-> b.length()-a.length());
        for (String skill : skills) {
            if(job.contains(skill))
            {
                if (questionBank.containsKey(skill)) {
                    questions.addAll(questionBank.get(skill));
                }

                if (resume.contains(skill)) {
                    matchCount++;
                }
                else if (resume.contains(skill.split(" ")[0])) {
                    partialMatches.add(skill);
                }
                else {
                    missing.add(skill);
                }
            }
        }
        int totalSkills = matchCount + missing.size() + partialMatches.size();
        int matchPercentage = totalSkills==0? 0 :
                ((matchCount * 100) + (partialMatches.size() * 50)) / totalSkills;

        StringBuilder message = new StringBuilder();
        message.append("AI Analysis:\n");

        if (matchPercentage > 80) {
            message.append("Strong match. High chances of shortlist.\n");
        } else if (matchPercentage > 50) {
            message.append("Moderate match. Improve missing skills.\n");
        } else {
            message.append("Low match. Significant improvement needed.\n");
        }

        if (!missing.isEmpty()) {
            message.append("Focus on: ")
                    .append(String.join(", ", missing))
                    .append("\n");
        }

        if (!partialMatches.isEmpty()) {
            message.append("Partial knowledge in: ")
                    .append(String.join(", ", partialMatches))
                    .append("\n");
        }

        response.setMessage(message.toString());

        response.setMatchPercentage(matchPercentage);
        response.setMissingSkills(missing);
        response.setPartial(partialMatches);
        response.setQuestions(questions);
        return response;

//      AnalyzeResponse analyzeResponse = new AnalyzeResponse();
//      analyzeResponse.setMessage("Ai is analyzing "+request.getResume()+" for "+request.getJobDescription());
//      analyzeResponse.setResumeLength(request.getResume().length());
//      analyzeResponse.setJobDescriptionLength((request.getJobDescription().length()));
//      return analyzeResponse;
    }
}
