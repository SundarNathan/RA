    package com.example.RA.service;

    import com.example.RA.model.AnalyzeRequest;
    import com.example.RA.model.AnalyzeResponse;
    import com.fasterxml.jackson.databind.JsonNode;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.http.HttpEntity;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;

    import java.util.List;
    import java.util.Map;

    @Service
    public class GeminiService {

        @Value("${gemini.api.key}")
        private String apiKey;

        private final RestTemplate restTemplate = new RestTemplate();

        public AnalyzeResponse analyzeResume(AnalyzeRequest resume) {

            String url = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key=" + apiKey;

            String prompt = "You are an expert ATS (Applicant Tracking System).\n\n"
                    + "Job Description:\n" + resume.getJobDescription() + "\n\n"
                    + "Resume:\n" + resume.getResume() + "\n\n"
                    + "Analyze and return ONLY JSON (no markdown, no explanation):\n"
                    + "{\n"
                    + "  \"matchPercentage\": number (0-100),\n"
                    + "  \"score\": number (0-10),\n"
                    + "  \"skills\": [],\n"
                    + "  \"missingSkills\": [],\n"
                    + "  \"suggestions\": []\n"
                    + "}";

            Map<String, Object> requestBody = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "parts", List.of(
                                            Map.of("text", prompt)
                                    )
                            )
                    )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);


            try {
                ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

                ObjectMapper mapper = new ObjectMapper();

                JsonNode root = mapper.readTree(response.getBody());

                String text = root.path("candidates")
                        .get(0)
                        .path("content")
                        .path("parts")
                        .get(0)
                        .path("text")
                        .asText();

                text = text.replace("```json", "")
                        .replace("```", "")
                        .trim();

                return mapper.readValue(text, AnalyzeResponse.class);

            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to parse Gemini response: " + e.getMessage());
            }
        }
    }